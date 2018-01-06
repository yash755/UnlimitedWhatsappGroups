package com.pappydevelopers.groupsforwhatsapp;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

import dmax.dialog.SpotsDialog;

import static com.pappydevelopers.groupsforwhatsapp.WhatsappListAdapter.context;

public class WhatsappHomeScreen extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    AlertDialog dialog;
    private AdView mAdView;
    String[] list_name = {"all","friend","education","news","sports","18plus","health","search"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Join Whatsapp Groups");

        dataBaseHelper = new DataBaseHelper(this);

        Cursor cr = dataBaseHelper.getReward();

        if(cr.getCount() <= 0) {
            dataBaseHelper.insertReward(6);
        }


        mAdView = findViewById(R.id.adView);
        MobileAds.initialize(getApplicationContext(),
                getString(R.string.ad_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                System.out.println("Hello I am ad");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                System.out.println("Hello I amwklwjwqwqjljwqjwqjw ad");
            }
        });


        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new GridAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               Toast.makeText(Home.this, "Clicked letter "+ names[position], Toast.LENGTH_SHORT).show();
                if(position != 7) {
                    Intent intent = new Intent(WhatsappHomeScreen.this, DetailedGroups.class);
                    intent.putExtra("title", list_name[position]);
                    startActivity(intent);
                } else if (position == 7) {
                    Intent intent = new Intent(WhatsappHomeScreen.this, SearchActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Find more than 10K active Whatsapp Groups.Very effective and amazing app.\n\n";
                shareBody = shareBody + "https://play.google.com/store/apps/details?id=com.pappydevelopers.groupsforwhatsapp \n\n";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Unlimited Whatsapp Groups List");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                return true;
            case R.id.rate:
                Uri uri = Uri.parse("market://details?id=com.pappydevelopers.groupsforwhatsapp");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=com.pappydevelopers.groupsforwhatsapp")));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
