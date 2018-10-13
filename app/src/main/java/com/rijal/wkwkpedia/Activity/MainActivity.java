package com.rijal.wkwkpedia.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rijal.wkwkpedia.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    findViewById(R.id.searchbar_main).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent search = new Intent(MainActivity.this,SearchActivity.class);
            startActivity(search);
        }
    });

    findViewById(R.id.btn_LoginLink).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent login = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(login);
        }
    });
    }
}
