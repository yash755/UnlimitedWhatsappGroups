package com.gappydevelopers.unlimitedwhatsappgroups;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class SearchActivity extends AppCompatActivity {

    SearchView search;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    ArrayList<WhatsappModel> whatsappModelArrayList;
    private RecyclerView recyclerView;
    WhatsappListAdapter whatsappListAdapter;
    String group_category;
    String query_string;
    CardView card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        card = (CardView)findViewById(R.id.card_view);
        card.setVisibility(View.GONE);

        setTitle("");


        recyclerView =(RecyclerView)findViewById(R.id.whatsappgplist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        checkpermission();


        search = (SearchView) findViewById(R.id.mySearchView);
        search.setQueryHint("SearchView");

        //*** setOnQueryTextFocusChangeListener ***
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                try {
                    readFile1(query,getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                return false;
            }
        });

    }



    public void checkpermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);


        } else {
            /*try {
                readFile1("",getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /*try {
                        readFile1("",getApplicationContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                } else {
                  /*  try {
                        readFile1("",getApplicationContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
                return;
            }
        }
    }


    private void readFile1(String query,final Context context) throws IOException {

        card.setVisibility(View.GONE);

        whatsappModelArrayList = new ArrayList<>();

        AssetManager am = context.getAssets();
        InputStream is = am.open("whatsapp.txt");

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;

        System.out.println("Query"+ query);

        while ((line = br.readLine()) != null) {
            String[] result = line.split("====");
            if(result[0].toLowerCase().contains(query.toLowerCase())){
                System.out.println("Query" + query);
                String[] url = result[1].split("/");
                String group_url = "https://chat.whatsapp.com/invite/icon/" + url[url.length - 1];
                whatsappModelArrayList.add(new WhatsappModel(result[0], result[1], "Join Group" , group_url));
            }
        }
        br.close();

        if(whatsappModelArrayList.size() == 0) {
            card.setVisibility(View.VISIBLE);
        }

            Collections.shuffle(whatsappModelArrayList);
            whatsappListAdapter = new WhatsappListAdapter(whatsappModelArrayList, context);
            recyclerView.setAdapter(whatsappListAdapter);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        Intent intent = new Intent(SearchActivity.this, WhatsappHomeScreen.class);
        startActivity(intent);

        return true;
    }

}
