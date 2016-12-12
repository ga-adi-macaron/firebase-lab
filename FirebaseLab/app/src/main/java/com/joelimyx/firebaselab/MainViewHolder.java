package com.joelimyx.firebaselab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Joe on 12/9/16.
 */

public class MainViewHolder extends RecyclerView.ViewHolder {
    TextView mUserName, mChatMessage;

    public MainViewHolder(View itemView) {
        super(itemView);
        mUserName = (TextView) itemView.findViewById(android.R.id.text1);
        mChatMessage = (TextView) itemView.findViewById(android.R.id.text2);
    }
}
