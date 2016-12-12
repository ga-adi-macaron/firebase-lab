package com.colinbradley.firebaselab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by colinbradley on 12/9/16.
 */

public class Holder extends RecyclerView.ViewHolder {
    TextView mUserName, mMessage;

    public Holder(View itemView) {
        super(itemView);

        mUserName =(TextView)itemView.findViewById(R.id.username);
        mMessage = (TextView)itemView.findViewById(R.id.message);
    }
    public void setName(String name){
        mUserName.setText(name);
    }
    public void setBody(String body){
        mMessage.setText(body);
    }
}
