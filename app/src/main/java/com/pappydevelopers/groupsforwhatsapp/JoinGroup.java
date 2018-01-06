package com.pappydevelopers.groupsforwhatsapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class JoinGroup extends AppCompatActivity implements RewardedVideoAdListener {

    String name,link,icon,category;
    ImageView group_image;
    TextView nam,categ,rewards;
    Integer reward;
    public ImageLoader imageLoader;
    private RewardedVideoAd mRewardedVideoAd;
    DataBaseHelper dataBaseHelper;
    Button getreward,getintadd;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        MobileAds.initialize(this, getString(R.string.ad_id));
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.int_ad));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        dataBaseHelper = new DataBaseHelper(this);

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
        rewards = (TextView)findViewById(R.id.reward);
        getreward = (Button)findViewById(R.id.getreward);

        getreward.setVisibility(View.INVISIBLE);


        Button button = (Button)findViewById(R.id.button);
        getintadd = (Button)findViewById(R.id.getintadd);
        getintadd.setVisibility(View.INVISIBLE);

        loadRewardedVideoAd();

        nam.setText(name);
        categ.setText(category);

        Cursor cr = dataBaseHelper.getReward();
        if (cr.getCount() > 0) {
            cr.moveToFirst();
            while (!cr.isAfterLast()) {
                reward = cr.getInt(cr.getColumnIndex("reward_points"));
                cr.moveToNext();
            }
            cr.close();
        }

        rewards.setText(" You have " + reward.toString() + " reward points.");

        if (category.equals("Friendship & Love")) {
            group_image.setImageResource(R.drawable.friendship);
        } else if (category.equals("Education")) {
            group_image.setImageResource(R.drawable.education);
        } else if (category.equals("News")) {
            group_image.setImageResource(R.drawable.news);
        } else if (category.equals("Sports")) {
            group_image.setImageResource(R.drawable.sports);
        } else if (category.equals("Adult 18+")) {
            group_image.setImageResource(R.drawable.adult);
        } else if (category.equals("Health & Fitness")) {
            group_image.setImageResource(R.drawable.clubs);
        } else if (category.equals("All Groups")) {
            group_image.setImageResource(R.drawable.all);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reward > 0) {
                    reward = reward -1;
                    dataBaseHelper.insertReward(reward);
                    Uri uri = Uri.parse(link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Get more reward points.", Toast.LENGTH_LONG).show();
                    /*mRewardedVideoAd.show();*/
                }

            }
        });

            getreward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mRewardedVideoAd.isLoaded()) {
                        mRewardedVideoAd.show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Ad is loading wait for few seconds.", Toast.LENGTH_LONG).show();
                    }


                }
            });


        getintadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                } else {
                        Toast.makeText(getApplicationContext(), "Ad is loading wait for few seconds.", Toast.LENGTH_LONG).show();
                    }
            }
        });


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                getintadd.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                if (reward <= 0) {
                    dataBaseHelper.insertReward(3);
                } else if (reward > 0) {
                    reward = reward + 3;
                    dataBaseHelper.insertReward(reward);
                }


                Cursor cr = dataBaseHelper.getReward();
                if (cr.getCount() > 0) {
                    cr.moveToFirst();
                    while (!cr.isAfterLast()) {
                        reward = cr.getInt(cr.getColumnIndex("reward_points"));
                        cr.moveToNext();
                    }
                    cr.close();
                }

                rewards.setText(" You have " + reward.toString() + " reward points.");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                getintadd.setVisibility(View.VISIBLE);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdClosed() {
                getintadd.setVisibility(View.VISIBLE);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });


    }




    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.reward_ad),
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        System.out.println("add loaded");
        getreward.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRewardedVideoAdOpened() {


    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        getreward.setVisibility(View.INVISIBLE);
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        System.out.println(rewardItem + "dffdkdf");

        if (reward <= 0) {
            dataBaseHelper.insertReward(15);
        } else if (reward > 0) {
            reward = reward + 15;
            dataBaseHelper.insertReward(reward);
        }


        Cursor cr = dataBaseHelper.getReward();
        if (cr.getCount() > 0) {
            cr.moveToFirst();
            while (!cr.isAfterLast()) {
                reward = cr.getInt(cr.getColumnIndex("reward_points"));
                cr.moveToNext();
            }
            cr.close();
        }

        rewards.setText(" You have " + reward.toString() + " reward points.");

        getreward.setVisibility(View.INVISIBLE);
        loadRewardedVideoAd();


    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        getreward.setVisibility(View.INVISIBLE);
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        loadRewardedVideoAd();
    }




    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
}
