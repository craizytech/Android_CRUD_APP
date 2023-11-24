package com.example.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCourseActivity extends AppCompatActivity {

    private TextInputEditText courseNameEdt, coursePriceEdt, courseSuitedForEdt, courseImgEdt, courseLinkEdt, courseDescEdt;
    private Button addCourseBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String courseID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        courseNameEdt = findViewById(R.id.editCourseName);
        coursePriceEdt = findViewById(R.id.editCoursePrice);
        courseSuitedForEdt = findViewById(R.id.editCourseSuitedFor);
        courseImgEdt = findViewById(R.id.editCourseImageLink);
        courseLinkEdt = findViewById(R.id.editCourseLink);
        courseDescEdt = findViewById(R.id.editCourseDescription);
        addCourseBtn = findViewById(R.id.addCourseBtn);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = courseNameEdt.getText().toString();
                String coursePrice = coursePriceEdt.getText().toString();
                String suitedFor =courseSuitedForEdt.getText().toString();
                String courseImg = courseImgEdt.getText().toString();
                String courseLink = courseDescEdt.getText().toString();
                String courseDesc = courseDescEdt.getText().toString();
                courseID = courseName;
                CourseRVModel courseRVModel = new CourseRVModel(courseName,courseDesc,coursePrice,suitedFor,courseImg,courseLink,courseID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //set the data in our firebase db
                        databaseReference.child(courseID).setValue(courseRVModel);
                        //displaying the toast message
                        Toast.makeText(AddCourseActivity.this, "Course Added..", Toast.LENGTH_SHORT).show();
                        //start the main Activity
                        startActivity(new Intent(AddCourseActivity.this, MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //display the failure message on the line below
                        Toast.makeText(AddCourseActivity.this, "Failed to add Course", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}