package com.korbkenny.scottandkennyfirebaselab;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    public static final String CHATROOM_INTENT_KEY = "chatroom key";
    private CardView mColorSelectButton;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter mAdapter;
    private int mUserRed, mUserGreen, mUserBlue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("chatrooms");

        setUpRecycler(ref);

        setUpColorButton();

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

    public void launchColorDialog(){
        setSeekBarListeners();

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor prefEditor =
                                getSharedPreferences("color", MODE_PRIVATE).edit();
                        prefEditor.putInt("user_color", Color.rgb(mUserRed, mUserGreen, mUserBlue));
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

    public void setSeekBarListeners(){
        LayoutInflater inflater = getLayoutInflater();
        View layoutView = inflater.inflate(R.layout.color_dialog, null);
        SeekBar redBar = (SeekBar)layoutView.findViewById(R.id.red_bar);
        SeekBar greenBar = (SeekBar)layoutView.findViewById(R.id.green_bar);
        SeekBar blueBar = (SeekBar)layoutView.findViewById(R.id.blue_bar);
        final ImageView preview = (ImageView)layoutView.findViewById(R.id.color_preview);

        SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (seekBar.getId()){
                    case R.id.red_bar:
                        mUserRed = i;
                        break;
                    case R.id.green_bar:
                        mUserGreen = i;
                        break;
                    case R.id.blue_bar:
                        mUserBlue = i;
                        break;
                }
                preview.setBackgroundColor(Color.rgb(mUserRed, mUserGreen, mUserBlue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        redBar.setOnSeekBarChangeListener(seekListener);
        greenBar.setOnSeekBarChangeListener(seekListener);
        blueBar.setOnSeekBarChangeListener(seekListener);
    }


    public static class ChatroomViewHolder extends RecyclerView.ViewHolder {
        public TextView mChatroomName;
        public ChatroomViewHolder(View itemView) {
            super(itemView);
            mChatroomName = (TextView)itemView.findViewById(R.id.chatroom_name);
        }
    }

}
