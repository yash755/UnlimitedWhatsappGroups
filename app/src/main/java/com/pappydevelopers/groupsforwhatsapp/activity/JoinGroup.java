package com.pappydevelopers.groupsforwhatsapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.pappydevelopers.groupsforwhatsapp.R;

public class JoinGroup extends AppCompatActivity{

    String name,link,icon,category;
    ImageView group_image;
    TextView nam,categ;
    Button joingroup;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        MobileAds.initialize(this, getString(R.string.ad_id));

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.int_ad));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

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

        joingroup = (Button)findViewById(R.id.button);



        nam.setText(name);
        categ.setText(category);


        joingroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Uri uri = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

            }
        });
    }


    @Override
    public void onBackPressed()
    {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                    Intent intent = new Intent(JoinGroup.this, DetailedGroups.class);
                    intent.putExtra("title", "all");
                    startActivity(intent);
                }
            });
        }else{
            super.onBackPressed();
            Intent intent = new Intent(JoinGroup.this, DetailedGroups.class);
            intent.putExtra("title", "all");
            startActivity(intent);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
