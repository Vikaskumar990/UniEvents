package com.exp.unievents.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.exp.unievents.R;
import com.exp.unievents.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.exp.unievents.constant.AppConstant.ALL_STUDENTS;
import static com.exp.unievents.constant.AppConstant.IS_SIGNED;
import static com.exp.unievents.constant.FirebaseData.STUDENTS;


public class SplashActivity extends BaseActivity {
  private ProgressBar pbLoad;
  private List<Student> studentList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_splash);
    pbLoad = findViewById(R.id.pbLoad);
    getAllStudent();
  }

  private void getAllStudent() {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();//initilized
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        //all data read section
        if (dataSnapshot.child(STUDENTS.toString()).hasChildren()) {
          studentList = new ArrayList<>();
          for (DataSnapshot dataSnapshot1 : dataSnapshot.child(STUDENTS.toString()).getChildren()) {
            Student student = dataSnapshot1.getValue(Student.class);// casting into student model
            studentList.add(student);
            storeOrUpdateObject(ALL_STUDENTS, studentList);
            goProcess();
          }
        } else goProcess();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  private void goProcess() {
    int SPLASH_TIME_OUT = 1000;
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        pbLoad.setVisibility(View.GONE);
        Intent homeintent = new Intent(SplashActivity.this,
              getPrefBoolean(IS_SIGNED) ? DashboardActivity.class : LoginActivity.class);
        startActivity(homeintent);
        finish();
      }

    }, SPLASH_TIME_OUT);
  }
}

