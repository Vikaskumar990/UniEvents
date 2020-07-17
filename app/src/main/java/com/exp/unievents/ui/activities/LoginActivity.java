package com.exp.unievents.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.exp.unievents.R;
import com.exp.unievents.model.Student;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.exp.unievents.constant.AppConstant.ALL_STUDENTS;
import static com.exp.unievents.constant.AppConstant.IS_SIGNED;
import static com.exp.unievents.constant.AppConstant.STUDENT;

public class LoginActivity extends BaseActivity {

  private List<Student> studentList = new ArrayList<>();
  private boolean doubleBackToExitPressedOnce = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();//initilized
    Type type = new TypeToken<List<Student>>() {
    }.getType();
    studentList = (List<Student>) getPrefObject(ALL_STUDENTS, type);
    setContentView(R.layout.activity_login);
  }

  public void btn_register(View view) {
    startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
  }

  public void login(View view) {
//    showLoadingProgress(null);
    EditText etUserID = findViewById(R.id.et_reg_id);
    EditText etPass = findViewById(R.id.et_password);
    if (!etUserID.getText().toString().trim().isEmpty()) {
      if (!etPass.getText().toString().trim().isEmpty()) {
//        hideProgressDialog();
        for (Student student : studentList) {
          if (student.s_reg_id.equalsIgnoreCase(etUserID.getText().toString())) {
            if (etPass.getText().toString().equalsIgnoreCase(student.password)) {
              storeOrUpdateObject(STUDENT,student);
              storeOrUpdateBoolean(IS_SIGNED,true);
              Toast.makeText(this, "Login successfully!", Toast.LENGTH_SHORT).show();
              startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
              finish();
            } else {
              etPass.setText("");
              Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
            }
            break;
          }
        }
      } else
        Toast.makeText(this, "Password can't be blank", Toast.LENGTH_SHORT).show();
    } else
      Toast.makeText(this, "User Id can't be blank", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onBackPressed() {
    if (doubleBackToExitPressedOnce) {
      super.onBackPressed();
      return;
    }

    this.doubleBackToExitPressedOnce = true;
    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {
        doubleBackToExitPressedOnce = false;
      }
    }, 2000);
  }
  
}
