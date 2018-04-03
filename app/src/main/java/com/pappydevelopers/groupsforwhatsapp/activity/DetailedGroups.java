package com.pappydevelopers.groupsforwhatsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.pappydevelopers.groupsforwhatsapp.R;
import com.pappydevelopers.groupsforwhatsapp.adapter.WhatsappListAdapter;
import com.pappydevelopers.groupsforwhatsapp.model.WhatsappModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class DetailedGroups extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    ArrayList<WhatsappModel> whatsappModelArrayList;
    String group_name;
    private AdView mAdView;
    private RecyclerView recyclerView;
    WhatsappListAdapter whatsappListAdapter;
    String group_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_groups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if(getIntent().hasExtra("title"))
            group_name = getIntent().getStringExtra("title");



        if (group_name.equals("friend")) {
            this.group_category = "Friendship & Love";
        } else if (group_name.equals("education")) {
            this.group_category = "Education";
        } else if (group_name.equals("news")) {
            this.group_category = "News";
        } else if (group_name.equals("sports")) {
            this.group_category = "Sports";
        } else if (group_name.equals("18plus")) {
            this.group_category = "Adult 18+";
        } else if (group_name.equals("health")) {
            this.group_category = "Health & Fitness";
        } else if (group_name.equals("all")) {
            this.group_category = "All Groups";
        }

        whatsappModelArrayList = new ArrayList<>();

        recyclerView =(RecyclerView)findViewById(R.id.whatsappgplist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        try {
            readFile1(this);
        } catch (IOException e) {
            e.printStackTrace();
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




    }

    private void readFile1(final Context context) throws IOException {
        AssetManager am = context.getAssets();
        InputStream is = am.open("whatsapp.txt");

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        if (group_name.equals("all")) {

            String line = null;
            while ((line = br.readLine()) != null) {
                String[] result = line.split("====");

                String[] url = result[1].split("/");
                String group_url = "https://chat.whatsapp.com/invite/icon/" + url[url.length - 1];
                whatsappModelArrayList.add(new WhatsappModel(result[0], result[1], "All Groups", group_url));

                setTitle("All groups (" + whatsappModelArrayList.size() + ")");
            }
            br.close();
            Collections.shuffle(whatsappModelArrayList);
            whatsappListAdapter = new WhatsappListAdapter(whatsappModelArrayList, context);
            recyclerView.setAdapter(whatsappListAdapter);
        } else {
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] result = line.split("====");
                if (result.length == 3){
                    if(result[2].equals(group_name)){
                        String[] url = result[1].split("/");
                        String group_url = "https://chat.whatsapp.com/invite/icon/" + url[url.length - 1];
                        whatsappModelArrayList.add(new WhatsappModel(result[0], result[1], this.group_category , group_url));
                    }
                }
            }
            br.close();


            if (group_name.equals("friend")) {
                setTitle("Friendship & Love (" + whatsappModelArrayList.size() + ")");
            } else if (group_name.equals("education")) {
                setTitle("Educational (" + whatsappModelArrayList.size() + ")");
            } else if (group_name.equals("news")) {
                setTitle("News (" + whatsappModelArrayList.size() + ")");
            } else if (group_name.equals("sports")) {
                setTitle("Sports (" + whatsappModelArrayList.size() + ")");
            } else if (group_name.equals("18plus")) {
                setTitle("Adult 18+ (" + whatsappModelArrayList.size() + ")");
            } else if (group_name.equals("health")) {
                setTitle("Health & Fitness (" + whatsappModelArrayList.size() + ")");
            }


            Collections.shuffle(whatsappModelArrayList);
            whatsappListAdapter = new WhatsappListAdapter(whatsappModelArrayList, context);
            recyclerView.setAdapter(whatsappListAdapter);



        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        Intent intent = new Intent(DetailedGroups.this, WhatsappHomeScreen.class);
        startActivity(intent);

        return true;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(DetailedGroups.this, WhatsappHomeScreen.class);
        intent.putExtra("title", "all");
        startActivity(intent);
    }

}

