package com.justinwells.firebasechat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by justinwells on 12/9/16.
 */

public class TextRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView mUserName;
        TextView mText;
        public TextRecyclerViewHolder(View itemView) {
            super(itemView);
            mUserName = (TextView)itemView.findViewById(android.R.id.text1);
            mText = (TextView)itemView.findViewById(android.R.id.text2);
        }
}


