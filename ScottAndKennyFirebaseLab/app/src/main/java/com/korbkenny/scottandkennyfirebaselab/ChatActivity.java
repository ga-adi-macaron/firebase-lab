package com.korbkenny.scottandkennyfirebaselab;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private EditText mMessageEdit;
    private CardView mSendButton;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter mAdapter;
    private List<Message> mMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent receivedIntent = getIntent();
        String chatroomKey = receivedIntent.getStringExtra(MainActivity.CHATROOM_INTENT_KEY);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(chatroomKey);

        setUpRecyclerView(ref);

        setUpViews(ref);
    }

    private void setUpRecyclerView(DatabaseReference ref){
        mRecyclerView = (RecyclerView)findViewById(R.id.chat_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new FirebaseRecyclerAdapter<Message, ChatViewHolder>
                (Message.class, R.layout.message_recycler_item, ChatViewHolder.class, ref) {
            @Override
            protected void populateViewHolder(ChatViewHolder viewHolder, Message model, int position) {
                viewHolder.mUserName.setText(model.getUser().getUsername());
                viewHolder.mMessage.setText(model.getText());
                viewHolder.itemView.setBackgroundColor(model.getUser().getColor());
            }
        };
    }

    private void setUpViews(final DatabaseReference ref){
        mSendButton = (CardView) findViewById(R.id.send_button);
        mMessageEdit = (EditText) findViewById(R.id.message_edit);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                int color = Color.BLUE;

                User user = new User(color, firebaseUser.getDisplayName());
                Message message = new Message (user, mMessageEdit.getText().toString());

                ref.push().setValue(message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder{
        public TextView mUserName;
        public TextView mMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);
            mUserName = (TextView)itemView.findViewById(R.id.user_name);
            mMessage = (TextView)itemView.findViewById(R.id.message_text);
        }
    }
}
