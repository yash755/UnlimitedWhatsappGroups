package com.pappydevelopers.groupsforwhatsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.view.View;

import com.pappydevelopers.groupsforwhatsapp.R;
import com.pappydevelopers.groupsforwhatsapp.adapter.WhatsappListAdapter;
import com.pappydevelopers.groupsforwhatsapp.model.WhatsappModel;

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
    ArrayList<WhatsappModel> temporary;
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



    }

    public void fetchdata() {
        try {
            readFile1(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void readFile1(final Context context) throws IOException {

        card.setVisibility(View.GONE);

        whatsappModelArrayList = new ArrayList<>();
        temporary = new ArrayList<>();
        final ArrayList<String> group_name = new ArrayList<>();

        AssetManager am = context.getAssets();
        InputStream is = am.open("whatsapp.txt");

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;


        while ((line = br.readLine()) != null) {
            String[] result = line.split("====");/*
            if(result[0].toLowerCase().contains(query.toLowerCase())){*/
                String[] url = result[1].split("/");
                String group_url = "https://chat.whatsapp.com/invite/icon/" + url[url.length - 1];
                group_name.add(result[0]);
                whatsappModelArrayList.add(new WhatsappModel(result[0], result[1], "Join Group" , group_url));
            /*}*/
        }
        br.close();

        int l = 0;
        while (l < whatsappModelArrayList.size()) {
            temporary.add(whatsappModelArrayList.get(l));
            l = l + 1;
        }

        Collections.shuffle(whatsappModelArrayList);
        whatsappListAdapter = new WhatsappListAdapter(temporary, context);
        recyclerView.setAdapter(whatsappListAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getApplicationContext(),android.R.layout.select_dialog_item,group_name);

        final AutoCompleteTextView actv= (AutoCompleteTextView)findViewById(R.id.search_story);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            // Do something for lollipop and above versions
            actv.setDropDownBackgroundResource(R.color.colorWhite);

        }


        actv.setThreshold(2);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                hideKeyboard();
                actv.setText("");

                String values = parent.getItemAtPosition(pos).toString();
                String[] values_array = values.split(" ");

                if (temporary.size() > 0)
                    temporary.clear();
                int k = 0;


                while (k < values_array.length) {
                    System.out.println(values_array[k]);
                    int i = 0;
                    while (i < whatsappModelArrayList.size()) {
                        if ((whatsappModelArrayList.get(i).getGp_name()).contains(values_array[k].toString())) {
                            temporary.add(whatsappModelArrayList.get(i));
                        }
                        i = i + 1;
                    }
                    k = k + 1;
                }
                whatsappListAdapter = new WhatsappListAdapter(temporary, getApplicationContext());
                recyclerView.setAdapter(whatsappListAdapter);

            }
        });

    }

    public void hideKeyboard() {
        InputMethodManager inputManager =
                (InputMethodManager) getApplicationContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        Intent intent = new Intent(SearchActivity.this, WhatsappHomeScreen.class);
        startActivity(intent);

        return true;
    }



    @Override
    public void onResume() {
        super.onResume();
        fetchdata();
    }

}
