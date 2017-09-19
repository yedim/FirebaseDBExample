package com.gmail.yelimi0416.firebasedbexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mNameTextView;
    private TextView mGithubTextView;
    private ImageView mProfileImageView;

    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameTextView = (TextView) findViewById(R.id.profile_name);
        mGithubTextView = (TextView) findViewById(R.id.profile_github);
        mProfileImageView = (ImageView) findViewById(R.id.profile_image);

        Picasso.with(this)
                .load("http://i.imgur.com/DvpvklR.png")
                .centerCrop()
                .resize(100, 100)
                .into(mProfileImageView);


        mDatabase = FirebaseDatabase.getInstance();

        DatabaseReference profileNameRef = mDatabase.getReference("profile/name");
        ValueEventListener profileNameListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                mNameTextView.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        profileNameRef.addValueEventListener(profileNameListener);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile_github:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse( mGithubTextView.getText().toString() );
                intent.setData(uri);
                startActivity(intent);
                break;
        }
    }
}
