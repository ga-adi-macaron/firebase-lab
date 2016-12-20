package com.jonathanlieblich.firebaselabapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<ChatPost> mChatPosts;

    private Button mSubmit;
    private EditText mChatContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSubmit = (Button) findViewById(R.id.submit);
        mChatContent = (EditText) findViewById(R.id.message_edit);

        mRecyclerView = (RecyclerView) findViewById(R.id.some_recycler_id);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference reference = db.getReference("messages");

        mChatPosts = new ArrayList<>();

        mAdapter = new FirebaseRecyclerAdapter<ChatPost, FirebaseRecyclerChatHolder>(ChatPost.class, R.layout.chat_post_layout,
                FirebaseRecyclerChatHolder.class, reference) {
            @Override
            protected void populateViewHolder(FirebaseRecyclerChatHolder viewHolder, ChatPost model, int position) {
                viewHolder.mChatName.setText(model.getUsername());
                viewHolder.mChatMessage.setText(model.getMessage());
            }
        };

        mRecyclerView.setAdapter(mAdapter);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = "User"+Math.round(Math.random()*1000);
                ChatPost post = new ChatPost();
                post.setMessage(mChatContent.getText().toString());
                post.setUsername(username);
                reference.push().setValue(post);
            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mChatPosts.add(dataSnapshot.getValue(ChatPost.class));
                mAdapter.notifyItemInserted(mChatPosts.size()-1);
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
    }
}
