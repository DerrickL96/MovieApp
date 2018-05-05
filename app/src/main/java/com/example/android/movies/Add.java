package com.example.android.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by android on 4/10/18.
 */

public class Add extends AppCompatActivity {
   // Intent data = new Intent();
    private String movieTitle;
    private String movieId;
    private TextView title;
    private TextView id;
    private EditText titleEdit2;
    private EditText idEdit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmovie_view);

        title = findViewById(R.id.addtitle);
        id = findViewById(R.id.addid);
        titleEdit2 = findViewById(R.id.titleEdit);
        idEdit2 = findViewById(R.id.idEdit);

        movieTitle = titleEdit2.getText().toString();
        movieId = idEdit2.getText().toString();

    }

    public void buttonPressed(View v) {
        Intent data = new Intent();
        movieTitle = titleEdit2.getText().toString();
        movieId = idEdit2.getText().toString();
        data.putExtra("Movie Title:", movieTitle);
        data.putExtra("Movie ID:", movieId);
        setResult(RESULT_OK, data);
        finish();

    }
}

