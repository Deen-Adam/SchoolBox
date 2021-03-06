package com.deenysoft.schoolbox.dashboard.addbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.dashboard.DashboardActivity;
import com.deenysoft.schoolbox.dashboard.DashboardAssignmentFragment;
import com.deenysoft.schoolbox.dashboard.DashboardCourseFragment;
import com.deenysoft.schoolbox.dashboard.DashboardExamFragment;
import com.deenysoft.schoolbox.dashboard.DashboardNoteFragment;
import com.deenysoft.schoolbox.dashboard.DashboardPresentationFragment;
import com.deenysoft.schoolbox.dashboard.DashboardQuizFragment;
import com.deenysoft.schoolbox.dashboard.DashboardSchoolFragment;
import com.deenysoft.schoolbox.dashboard.DashboardTestFragment;
import com.deenysoft.schoolbox.dashboard.database.SchoolBoxDBManager;
import com.deenysoft.schoolbox.dashboard.model.AssignmentBoxItem;
import com.deenysoft.schoolbox.nest.ui.SettingsActivity;
import com.deenysoft.schoolbox.nest.ui.base.BaseActivity;
import com.deenysoft.schoolbox.widget.ThemeUtil;

/**
 * Created by shamsadam on 24/06/16.
 */
public class AddAssignment extends BaseActivity {

    private ImageView mImageButton;
    private TextInputEditText inputAssignTitle, inputAssignNo, inputAssignDueDate, inputAssignSubVenue;
    private TextInputLayout inputLayoutAssignTitle, inputLayoutAssignNo, inputLayoutAssignDueDate, inputLayoutAssignSubVenue;
    private AppCompatRadioButton mRadioButtonSubmitted, mRadioButtonNotYet;
    private Button mCommitButton;

    private AssignmentBoxItem mAssignmentBoxItem;
    private SchoolBoxDBManager mSchoolBoxDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.onActivityCreateSetTheme(this);

        setContentView(R.layout.add_assignment_activity);
        setupToolbar();

        // Init
        mRadioButtonSubmitted = (AppCompatRadioButton) findViewById(R.id.radio_submitted);
        mRadioButtonNotYet = (AppCompatRadioButton) findViewById(R.id.radio_not_yet);

        inputLayoutAssignNo = (TextInputLayout) findViewById(R.id.input_layout_assign_no);
        inputLayoutAssignTitle = (TextInputLayout) findViewById(R.id.input_layout_assign_title);
        inputLayoutAssignDueDate = (TextInputLayout) findViewById(R.id.input_layout_assign_date);
        inputLayoutAssignSubVenue = (TextInputLayout) findViewById(R.id.input_layout_assign_venue);

        inputAssignNo = (TextInputEditText) findViewById(R.id.input_assign_no);
        inputAssignTitle = (TextInputEditText) findViewById(R.id.input_assign_title);
        inputAssignSubVenue = (TextInputEditText) findViewById(R.id.input_assign_venue);
        inputAssignDueDate = (TextInputEditText) findViewById(R.id.input_assign_date);


        // Objects Init
        mAssignmentBoxItem = new AssignmentBoxItem();
        mSchoolBoxDBManager = new SchoolBoxDBManager();

        mCommitButton = (Button) findViewById(R.id.commitButton);
        mCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Assignment Info
                String mInputAssignNo = inputAssignNo.getText().toString().trim();
                String mInputAssignTitle = inputAssignTitle.getText().toString().trim();
                String mInputAssignSubVenue = inputAssignSubVenue.getText().toString().trim();
                String mInputAssignDueDate = inputAssignDueDate.getText().toString().trim();
                if (!mInputAssignNo.isEmpty() && !mInputAssignTitle.isEmpty() && !mInputAssignSubVenue.isEmpty() && !mInputAssignDueDate.isEmpty()) {
                    // Pass to AssignmentBoxItem Class
                    mAssignmentBoxItem.setAssignmentID(mInputAssignNo);
                    mAssignmentBoxItem.setAssignmentTitle(mInputAssignTitle);
                    mAssignmentBoxItem.setAssignmentSubVenue(mInputAssignSubVenue);
                    mAssignmentBoxItem.setAssignmentDueDate(mInputAssignDueDate);
                    // Push to SchoolBoxDBManager SQLite Database
                    mSchoolBoxDBManager.addAssignmentItem(mAssignmentBoxItem); // Stored
                    //Toast.makeText(AddAssignment.this, ""+mSchoolBoxDBManager.getAssignmentItem(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddAssignment.this, "Added Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddAssignment.this, DashboardActivity.class));
                } else {
                    Toast.makeText(AddAssignment.this, "Error! Text field should not be left blank", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //mRadioButtonStudent.setVisibility(View.VISIBLE);
        mRadioButtonSubmitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((AppCompatRadioButton) v).isChecked();
                switch (v.getId()) {
                    case R.id.radio_submitted:
                        if (checked) {
                            String mStatus = mRadioButtonSubmitted.getText().toString().trim();
                            // Pass to AssignmentBoxItem Class
                            mAssignmentBoxItem.setAssignmentStatus(mStatus);
                            Toast.makeText(AddAssignment.this, mAssignmentBoxItem.getAssignmentStatus(), Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        //mRadioButtonStudent.setVisibility(View.VISIBLE);
        mRadioButtonNotYet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((AppCompatRadioButton) v).isChecked();
                switch (v.getId()) {
                    case R.id.radio_not_yet:
                        if (checked) {
                            String mStatus = mRadioButtonNotYet.getText().toString().trim();
                            // Pass to AssignmentBoxItem Class
                            mAssignmentBoxItem.setAssignmentStatus(mStatus);
                            Toast.makeText(AddAssignment.this, mAssignmentBoxItem.getAssignmentStatus(), Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });


        mImageButton = (ImageView) findViewById(R.id.share);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // Add data to the intent, the receiving app will decide what to do with it.
                intent.putExtra(Intent.EXTRA_SUBJECT, "Get SchoolBox App for Android");
                intent.putExtra(Intent.EXTRA_TEXT, "Hey, try SchoolBox App for Android. It provides great educational contents. Get it on Google Play - https://play.google.com/store/apps/developer?id=Deenysoft+Inc");

                startActivity(Intent.createChooser(intent, "How do you want to share?"));
                finish();
            }
        });
    }


    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_school:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_course:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_quiz:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_test:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_note:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_exam:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_assignment:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            case R.id.action_presentation:
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

}
