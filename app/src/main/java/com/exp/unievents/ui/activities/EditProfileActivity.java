package com.exp.unievents.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.exp.unievents.R;
import com.exp.unievents.constant.AppConstant;
import com.exp.unievents.model.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.exp.unievents.constant.AppConstant.STUDENTS;

public class EditProfileActivity extends BaseActivity {
  TextView tvStName;
  AppCompatEditText etRegId,etName,etEmail,etDob,etCourse,etGender,etInterest,etContactNo;
  private Student student = null;
  private DatabaseReference databaseReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    tvStName = findViewById(R.id.tvStName);
    etRegId = findViewById(R.id.etRegId);
    etName = findViewById(R.id.etName);
    etEmail = findViewById(R.id.etEmailId);
    etContactNo = findViewById(R.id.etContact);
    etDob = findViewById(R.id.etDoB);
    etGender = findViewById(R.id.etGender);
    etCourse = findViewById(R.id.etCourse);
    etInterest = findViewById(R.id.etInterest);
    databaseReference = FirebaseDatabase.getInstance().getReference();
    student = getPrefObject(AppConstant.STUDENT, Student.class);
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle("");
      if (student != null)
        tvStName.setText(student.s_name);
    }
    dataInitiate();
  }

  private void dataInitiate() {
    if (student!= null){
      etRegId.setText(student.s_reg_id);
      etName.setText(student.s_name);
      etEmail.setText(student.s_email);
      etContactNo.setText(student.s_contact);
      etDob.setText(student.dob);
      etGender.setText(student.gender);
      etCourse.setText(student.course);
      etInterest.setText(student.interest);
    }
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == android.R.id.home){
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  public void updateStudent(View view) {
    Student student = new Student();
    student.s_reg_id = etRegId.getText().toString();
    student.s_name = etName.getText().toString();
    student.gender = etGender.getText().toString();
    student.s_contact = etContactNo.getText().toString();
    student.dob = etDob.getText().toString();
    student.s_email = etEmail.getText().toString();
    student.course = etCourse.getText().toString();
    student.interest = etInterest.getText().toString();
    student.password = this.student.password;
    storeOrUpdateObject(AppConstant.STUDENT,student);
    updateStudentList(student);
    databaseReference.child(AppConstant.STUDENTS).child(this.student.s_reg_id).setValue(student);
  }

  private void updateStudentList(Student student) {
    List<Student> students = (List<Student>) getPrefObject(AppConstant.ALL_STUDENTS,new TypeToken<List<Student>>(){}.getType());
    for (Student st : students){
      if (st.s_reg_id.equalsIgnoreCase(this.student.s_reg_id)){
        students.remove(st);
        students.add(student);
        break;
      }
    }
  }
}
