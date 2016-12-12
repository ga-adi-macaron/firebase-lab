package com.colinbradley.firebaselab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    FirebaseRecyclerAdapter mAdapter;
    RecyclerView mRV;
    String mUsername;
    Button mSendButton;
    EditText mEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mSendButton = (Button)findViewById(R.id.submit);

        Random random = new Random();
        mUsername = "User" +  random.nextInt(999);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");

        mRV = (RecyclerView)findViewById(R.id.recycler);
        mRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new FirebaseRecyclerAdapter<ChatMessage, Holder>(ChatMessage.class, R.layout.item_layout, Holder.class, myRef) {

            @Override
            protected void populateViewHolder(Holder viewHolder, ChatMessage model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setBody(model.getBody());
            }
        };
        mRV.setAdapter(mAdapter);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEditText.equals("")) {
                    myRef.push().setValue(new ChatMessage(mUsername, mEditText.getText().toString()));
                    mEditText.setText("");
                }else {
                    Toast.makeText(MainActivity.this, "Please Enter a Message", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
