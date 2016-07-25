package com.myfabpics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myfabpics.CommonHelper.Validator;
import com.myfabpics.DataClass.Subcribe;
import com.myfabpics.DatabaseHandler.SubscribeHelper;

import java.security.spec.ECField;

public class SubcribeActivity extends Activity {

    private Button subscribeButton;
    private EditText subscribeEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcribe);
        if(checkAppSubscribed()) {
            Intent intent = new Intent("com.myfabpics.WallActivity");
            startActivity(intent);
        } else {
            this.showSubscribeForm();
        }
    }

    public void hideSubscribeLoader() {
        LinearLayout subscribeLoader = (LinearLayout)this.findViewById(R.id.subscribe_loader);
        subscribeLoader.setVisibility(View.GONE);
    }

    public void showSubscribeForm() {
        this.hideSubscribeLoader();
        LinearLayout subscribeContainer = (LinearLayout)this.findViewById(R.id.subscribe_form);
        subscribeContainer.setVisibility(View.VISIBLE);
        Button subscribeButton = (Button)this.findViewById(R.id.subscribeButton);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText subscribeEmail = (EditText)SubcribeActivity.this.findViewById(R.id.subscribeEmail);
                Validator validator = new Validator();
                if(validator.isValidEmail(subscribeEmail.getText().toString())) {
                    SubscribeHelper db = new SubscribeHelper(SubcribeActivity.this);
                    db.addSubscribe(new Subcribe(subscribeEmail.getText().toString()));
                    Intent intent = new Intent("com.myfabpics.WallActivity");
                    startActivity(intent);
                } else {
                    Toast.makeText(SubcribeActivity.this, getResources().getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    public boolean checkAppSubscribed() {
        SubscribeHelper db = new SubscribeHelper(this);
        if (db.getSubscribeCount()==0){
            return false;
        }
        return true;
    }
}
