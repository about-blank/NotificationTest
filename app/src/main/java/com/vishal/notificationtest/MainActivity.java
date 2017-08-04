package com.vishal.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    EditText contentTitleField, contentTextField, tickerField, bigTextField;
    CheckBox bigStyleCheckbox;
    Button notificationActionBtn;

    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentTextField = (EditText) findViewById(R.id.contentTextField);
        contentTitleField = (EditText) findViewById(R.id.contentTitleField);
        tickerField = (EditText) findViewById(R.id.tickerField);
        bigTextField = (EditText) findViewById(R.id.bigTextField);

        bigStyleCheckbox = (CheckBox) findViewById(R.id.checkBox);
        bigStyleCheckbox.setOnCheckedChangeListener(this);

        notificationActionBtn = (Button) findViewById(R.id.notifyBtn);
        notificationActionBtn.setOnClickListener(this);


        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private String getTextFromField(EditText view) {

        return view.getText().toString().trim();
    }

    private Notification createNotification() {

        Notification notification = null;

        String contentTitle = getTextFromField(contentTitleField);
        String contentText = getTextFromField(contentTextField);
        String ticker = getTextFromField(tickerField);
        String bigText = getTextFromField(bigTextField);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(contentTitle);
        builder.setContentText(contentText);
        builder.setTicker(ticker);

        builder.setSmallIcon(R.mipmap.ic_launcher);

        if(bigStyleCheckbox.isChecked()) {
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(bigText).setBigContentTitle("big-content-title").setSummaryText("summary-text"));
            builder.addAction(android.R.drawable.ic_menu_add, "Add", null);
            builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "Close", null);

        }
        builder.setPriority(NotificationCompat.PRIORITY_MAX); //This will make sure big view notification shows up ... especially if there are already lots of notifications.

        notification = builder.build();

        return notification;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            case R.id.notifyBtn:
                Notification notification = createNotification();
                notificationManager.notify(1001, notification);
                break;
        }
    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if(compoundButton.getId() == R.id.checkBox) {
            if(bigStyleCheckbox.isChecked()) {
                bigTextField.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) bigStyleCheckbox.getLayoutParams();

                p.addRule(RelativeLayout.BELOW, R.id.bigTextField);

                bigStyleCheckbox.setLayoutParams(p);


            } else {
                bigTextField.setVisibility(View.GONE);

                RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) bigStyleCheckbox.getLayoutParams();

                p.addRule(RelativeLayout.BELOW, R.id.tickerField);

                bigStyleCheckbox.setLayoutParams(p);
            }
        }
    }
}
