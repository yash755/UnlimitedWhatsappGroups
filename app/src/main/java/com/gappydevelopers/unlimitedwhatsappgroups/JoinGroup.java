package com.gappydevelopers.unlimitedwhatsappgroups;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class JoinGroup extends AppCompatActivity {

    String name,link,icon,category;
    ImageView group_image;
    TextView nam,categ;
    public ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        imageLoader = new ImageLoader(this);


        if(getIntent().hasExtra("link")) {
            System.out.println(getIntent().getStringExtra("link"));
            link = getIntent().getStringExtra("link");
            name = getIntent().getStringExtra("name");
            icon = getIntent().getStringExtra("image");
            category = getIntent().getStringExtra("category");
        }


        setTitle("Join " + name);

        nam = (TextView)findViewById(R.id.name);
        categ = (TextView)findViewById(R.id.category);
        group_image = (ImageView)findViewById(R.id.group_image);

        Button button = (Button)findViewById(R.id.button);



        nam.setText(name);
        categ.setText(category);
        imageLoader.DisplayImage(icon, group_image);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
    }

}
