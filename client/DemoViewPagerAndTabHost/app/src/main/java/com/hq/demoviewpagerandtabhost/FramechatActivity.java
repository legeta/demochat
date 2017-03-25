package com.hq.demoviewpagerandtabhost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by My-PC on 3/25/2017.
 */

public class FramechatActivity extends AppCompatActivity {
    TextView txtFramechat;
    EditText edtChat;
    Button btnSend;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatform);
        String name = "a";


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChatform);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
//        toolbar.setSubtitle("Sign in");

        txtFramechat = (TextView) findViewById(R.id.txtFramechat);
        edtChat = (EditText) findViewById(R.id.edtChat);
        btnSend = (Button) findViewById(R.id.btnSendChat);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFramechat.setText(txtFramechat.getText()+"\n"+"Me: "+edtChat.getText());
                edtChat.setText("");
//                txtFramechat.setGravity(1);
            }
        });

    }
}
