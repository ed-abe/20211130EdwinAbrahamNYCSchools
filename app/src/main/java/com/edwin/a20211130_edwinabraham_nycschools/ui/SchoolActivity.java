package com.edwin.a20211130_edwinabraham_nycschools.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.edwin.a20211130_edwinabraham_nycschools.R;
import com.edwin.a20211130_edwinabraham_nycschools.pojo.SATScores;
import com.edwin.a20211130_edwinabraham_nycschools.model.SATScoresViewModel;
import com.edwin.a20211130_edwinabraham_nycschools.pojo.School;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class SchoolActivity extends AppCompatActivity {

    private School school;
    private SATScoresViewModel satScoresViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_school);
        Toolbar toolbar = findViewById(R.id.school_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //School object to setup the page
        school = getIntent().getParcelableExtra("School");

        ImageButton directionsButton = findViewById(R.id.directionsButton);
        directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"+school.getLatitude()+","+school.getLongitude()+"?q="+school.getSchool_name());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        ImageButton websiteBUtton = findViewById(R.id.websiteButton);
        websiteBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri websiteIntentUri = Uri.parse(school.getWebsite());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, websiteIntentUri);
                v.getContext().startActivity(Intent.createChooser(websiteIntent, "Browse with"));
            }
        });
        ImageButton callButton = findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + school.getPhone_number()));
                startActivity(intent);
            }
        });

        ImageButton emailButton = findViewById(R.id.emailButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"+school.getSchool_email()));
                v.getContext().startActivity(Intent.createChooser(intent, "Email School "));
            }
        });

        // Setup UI with School object from intent
        TextView schoolNameTextView = findViewById(R.id.schoolNameText);
        TextView descTextView = findViewById(R.id.descriptionText);
        schoolNameTextView.setText(school.getSchool_name());
        schoolNameTextView.setSingleLine(false);
        schoolNameTextView.setHorizontallyScrolling(false);
        descTextView.setText(school.getOverview_paragraph());
        descTextView.setSingleLine(false);
        descTextView.setHorizontallyScrolling(false);

        // Loads SAT Scores for the School
        satScoresViewModel = new ViewModelProvider(this).get(SATScoresViewModel.class);
        LiveData<SATScores> score = satScoresViewModel.getScoresForSchool(school.getDbn());
        score.observe(this, new Observer<SATScores>() {
            @Override
            public void onChanged(SATScores satScores) {
                satScoresUpdated(satScores);
            }
        });
    }

    /**
     *  Updates the SAT Scores once available from DB
     * @param score
     */
    private void satScoresUpdated(SATScores score) {
        if(score!=null){
            TextView satScoresTextView = findViewById(R.id.satScoresTextView);
            satScoresTextView.setText(score.toString());
        }
    }


}