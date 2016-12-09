package com.justinwells.firebasechat;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button sendButton;
    private RecyclerView recyclerView;
    private String userName;
    EditText enterUserNameEditText;
    Boolean  isFirstTime;
    List<ChatItem> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        editText = (EditText) findViewById(R.id.edit_text);
        sendButton = (Button) findViewById(R.id.send_button);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = db.getReference("chatroom");


        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ChatItem,
                TextRecyclerViewHolder>(ChatItem.class, android.R.layout.simple_list_item_2,
                TextRecyclerViewHolder.class, myRef) {
            @Override
            protected void populateViewHolder(TextRecyclerViewHolder viewHolder, ChatItem model, int position) {
                viewHolder.mUserName.setText(model.getUserName());
                viewHolder.mText.setText(model.getText());
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

        final Dialog enterUserName = new Dialog(this);
        enterUserName.setContentView(R.layout.user_name_dialog);
        enterUserNameEditText = (EditText) enterUserName.findViewById(R.id.user_name_edit_text);
        Button userNameButton = (Button) enterUserName.findViewById(R.id.user_name_button);


        isFirstTime = true;
        
        if (isFirstTime) {
            enterUserName.show();
            userNameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userName = enterUserNameEditText.getText().toString();
                    isFirstTime = false;
                    getSupportActionBar().setTitle(userName);
                    enterUserName.dismiss();

                }
            });

        } else {
            //do nothing
        }



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                ChatItem item = new ChatItem(userName,message);
                myRef.push().setValue(item);
            }
        });

    }
}
