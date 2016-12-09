package com.ezequielc.firebaselab;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private Button mButton;
    private String mUsername;
    private FirebaseRecyclerAdapter mAdapter;
    private ArrayList<ChatMessage> mRoomChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random rng = new Random();
        mRoomChat = new ArrayList<>();

        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if(android_id.equals( "669ec9813b4c140")){
            mUsername = "User"+ rng.nextInt(499);
        }else if (android_id.equals("53c3af768086e15e")){
            mUsername = "User"+ (rng.nextInt(499)+500);
        }else{
            mUsername = "User"+ (rng.nextInt(499)+1000);
        }

        Log.d("User ID", "onCreate: "+mUsername);

        findViews();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference();

        mAdapter = new FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder>(ChatMessage.class, R.layout.user_messages_list, MessageViewHolder.class, reference) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, ChatMessage model, int position) {
                viewHolder.mUser.setText(model.getmUser());
                viewHolder.mMessage.setText(model.getmMessage());
            }
        };

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                mRoomChat.add(message);
                mAdapter.notifyItemInserted(mRoomChat.size()-1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMessage postedMessage = new ChatMessage(mUsername,mEditText.getText().toString());
                mEditText.setText("");
                reference.push().setValue(postedMessage);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);


    }
    public void findViews(){
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mEditText = (EditText)findViewById(R.id.edit_query);
        mButton = (Button)findViewById(R.id.submit);
    }
}
