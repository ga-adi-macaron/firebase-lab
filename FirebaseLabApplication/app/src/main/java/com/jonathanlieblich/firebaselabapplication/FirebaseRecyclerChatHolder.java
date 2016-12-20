package com.jonathanlieblich.firebaselabapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by jonlieblich on 12/9/16.
 */

public class FirebaseRecyclerChatHolder extends RecyclerView.ViewHolder {
    public TextView mChatMessage;
    public TextView mChatName;

    public FirebaseRecyclerChatHolder(View itemView) {
        super(itemView);

        mChatMessage = (TextView)itemView.findViewById(R.id.post_content);
        mChatName = (TextView)itemView.findViewById(R.id.post_username);
    }
}
