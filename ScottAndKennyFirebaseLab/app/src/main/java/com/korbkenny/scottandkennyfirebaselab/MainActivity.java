package com.korbkenny.scottandkennyfirebaselab;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.ui.ResultCodes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String WEB_CLIENT_ID = "223532779792-vkt6l2578jf8shnu24bge4vqliqnqqj8.apps.googleusercontent.com";
    public static final String CHATROOM_INTENT_KEY = "chatroom key";
    private static final int RC_SIGN_IN = 12345;
    private DatabaseReference mRef;
    private CardView mColorSelectButton;
    private CardView mAddChatroomButton;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter mAdapter;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int mUserColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_CLIENT_ID)
                .build();

        GoogleApiClient googleAPIClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();


        if (mAuth.getCurrentUser() != null) {
            SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
            getSupportActionBar().setTitle("Logged in as "+sharedPref.getString("user_name","Guest"));
        } else{
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleAPIClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference("chatrooms");

        setUpAuthStateListener();
        setUpAddChatroomButton();
        setUpRecycler(mRef);
        setUpColorButton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithCredential", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(this, "Google Sign in Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "SIGN IN CANCELLED", Toast.LENGTH_SHORT).show();
            return;
        }
        if (resultCode == ResultCodes.RESULT_NO_NETWORK) {
            Toast.makeText(this, "NO INTERNET!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void setUpAuthStateListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    SharedPreferences.Editor prefEditor =
                            getSharedPreferences("user", MODE_PRIVATE).edit()
                            .putString("user_name", user.getDisplayName());
                    prefEditor.commit();
                }
            }
        };
    }


    public void setUpRecycler(DatabaseReference ref){
        mRecyclerView = (RecyclerView)findViewById(R.id.chatroom_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FirebaseRecyclerAdapter<Chatroom, ChatroomViewHolder>
                (Chatroom.class, R.layout.chatroom_recycler_item, ChatroomViewHolder.class, ref) {
            @Override
            protected void populateViewHolder(ChatroomViewHolder viewHolder, final Chatroom model, int position) {
                viewHolder.mChatroomName.setText(model.getName());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        intent.putExtra(CHATROOM_INTENT_KEY, model.getName());
                        Log.d(TAG, "onClick: "+model.getName());
                        startActivity(intent);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setUpColorButton(){
        mColorSelectButton = (CardView)findViewById(R.id.select_color_button);
        mColorSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchColorDialog();
            }
        });
    }
    public void setUpAddChatroomButton(){
        mAddChatroomButton = (CardView)findViewById(R.id.add_chatroom_button);
        mAddChatroomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNewChatroomDialog();
            }
        });
    }

    public void launchNewChatroomDialog(){
        LayoutInflater inflator = getLayoutInflater();
        View layoutView = inflator.inflate(R.layout.chatroom_dialog, null);
        final EditText mNameEdit = (EditText)layoutView.findViewById(R.id.chatroom_name_edit);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(layoutView)
                .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Chatroom chatroom = new Chatroom();
                        chatroom.setName(mNameEdit.getText().toString());
                        Log.d(TAG, "onClick: "+chatroom.getName());
                        mRef.push().setValue(chatroom);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        dialog.show();
    }

    public void launchColorDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View layoutView = inflater.inflate(R.layout.color_dialog, null);

        final ColorPickerView cp = (ColorPickerView)layoutView.findViewById(R.id.color_picker_view);
        cp.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {
            @Override
            public void onColorChanged(int i) {
                mUserColor = cp.getColor();
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(layoutView)
                .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor prefEditor =
                                getSharedPreferences("color", MODE_PRIVATE).edit();
                        Log.d(TAG, "onClick: "+mUserColor);
                        prefEditor.putInt("user_color", mUserColor);
                        prefEditor.commit();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public static class ChatroomViewHolder extends RecyclerView.ViewHolder {
        public TextView mChatroomName;
        public ChatroomViewHolder(View itemView) {
            super(itemView);
            mChatroomName = (TextView)itemView.findViewById(R.id.chatroom_name);
        }
    }

}
