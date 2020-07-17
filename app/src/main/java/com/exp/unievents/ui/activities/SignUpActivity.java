package com.exp.unievents.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.exp.unievents.R;
import com.exp.unievents.model.Student;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SignUpActivity extends BaseActivity {

  private DatabaseReference myRef;
  private EditText etCourse;
  private EditText etDOB;
  private EditText etGender;
  private EditText etInterest;
  private EditText etContact;
  private EditText etEmail;
  private EditText etName;
  private EditText etRegId;
  private EditText etPass;
  private TextInputLayout textInputLayoutReg;
  private TextInputLayout textInputLayoutPass;
  private List<View> requireFields = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_signup);
    initiateView();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    myRef = database.getReference();
    Log.i("TAGAG", "onCreate: " + myRef);
  }

  private void initiateView() {
    etCourse = findViewById(R.id.o_Course);
    etDOB = findViewById(R.id.o_Dob);//it should be a text
    etGender = findViewById(R.id.o_Gen);
    etInterest = findViewById(R.id.o_Interest);
    etContact = findViewById(R.id.phone);
    etEmail = findViewById(R.id.tvEmail);
    etName = findViewById(R.id.Name);
    etRegId = findViewById(R.id.o_Id);
    etPass = findViewById(R.id.password);
    textInputLayoutReg = findViewById(R.id.tilRegId);
    textInputLayoutPass = findViewById(R.id.tilPass);
    requireFields.add(etRegId);
    requireFields.add(etPass);
    requireFields.add(etRegId);
  }

  public void btn_register(View view) {
    if (fieldsValidated())
      writeData();
  }

  private boolean fieldsValidated() {
    boolean isValidate = false;
    if (hasNoEmptyField())
      if (TextUtils.isEmpty(etEmail.getText().toString()))
        isValidate = true;
      else if (emailValidator(etEmail.getText().toString()))
        isValidate = true;
    return isValidate;
  }

  private boolean hasNoEmptyField() {
    int noOfRequireField = 0;
    for (View editText : requireFields) {
      if (editText instanceof EditText && !TextUtils.isEmpty(((EditText) editText).getText()))
        noOfRequireField++;
      else {
        if (editText.getParent() instanceof TextInputLayout)
          ((TextInputLayout) editText.getParent()).setError("Field can't be blank");
      }
    }
    return noOfRequireField == requireFields.size();
  }

  private void writeData() {
    Student student = new Student();
    student.s_name = etName.getText().toString();
    student.s_email = etEmail.getText().toString();
    student.s_contact = etContact.getText().toString();
    student.password = etPass.getText().toString();
    student.s_reg_id = etRegId.getText().toString();
    student.course = etCourse.getText().toString();
    student.dob = etDOB.getText().toString();
    student.gender = etGender.getText().toString();
    student.interest = etInterest.getText().toString();
    if (myRef != null) {
      myRef.child("students").child(student.s_reg_id).setValue(student);
    }
  }

  private boolean emailValidator(String email) {
    final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$";
    return Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE).matcher(email).matches();
  }

  public void login(View view) {
    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    finish();
  }

  /**
   * Take care of popping the fragment back stack or finishing the activity
   * as appropriate.
   */
  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }
}
