package com.ezequielc.firebaselab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by student on 12/9/16.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {
    TextView mUser, mMessage;
    public MessageViewHolder(View itemView) {
        super(itemView);

        mUser = (TextView)itemView.findViewById(R.id.username);
        mMessage = (TextView)itemView.findViewById(R.id.message);
    }
}
