package com.example.mikhail.exercise2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;



public class AboutActivity extends AppCompatActivity {
    private String message;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton vk = findViewById(R.id.vkButton);
        vk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             openWebPage("https://vk.com/timetosit");
            }
        });

    ImageButton inst = findViewById(R.id.instaButton);
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             openWebPage("https://www.instagram.com/foundpart");
            }
        });
           ImageButton telegram = findViewById(R.id.telegramButton);
        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             openWebPage("https://t.me/chopyourbrain");
            }
        });
           ImageButton github = findViewById(R.id.githubButton);
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             openWebPage("https://github.com/chopyourbrain");
            }
        });
        Button toEmail = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        toEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    message = editText.getText().toString();
                    composeEmail("Hello",message); }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), R.string.noEmail, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void openWebPage(String url) {
        try {
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText(this,R.string.activity_not_found_ex,Toast.LENGTH_LONG).show();
        }
    }
    public void composeEmail(String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:qtd130@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
