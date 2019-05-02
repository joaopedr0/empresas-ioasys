package com.example.empresas;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class EmpresaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_empresa);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String photo = intent.getStringExtra("photo");
        String description = intent.getStringExtra("description");

        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(name);

        ImageView image = findViewById(R.id.imageView_empresa);

        if(photo != null){
            Glide.with(this)
                    .load("http://empresas.ioasys.com.br" + photo)
                    .into(image);
        }

        TextView textViewDescription = findViewById(R.id.textView_description);
        textViewDescription.setText(description);


    }
}
