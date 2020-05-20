package com.ezen.wannaseeamovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    ImageView background, logo_main;
    EditText search_bar;
    String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        background = findViewById(R.id.background);
        logo_main = findViewById(R.id.logo_main);
        search_bar = findViewById(R.id.search_bar);

        search_bar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent intent = new Intent(MainActivity.this, SearchResults.class);
                    search_bar = findViewById(R.id.search_bar);
                    search = search_bar.getText().toString();
                    intent.putExtra("searchingText", search);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
