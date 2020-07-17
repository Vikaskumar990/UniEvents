package com.exp.unievents.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Student {
  public String s_reg_id = "";
  public String s_name = "";
  public String course = "";
  public String s_email = "";
  public String s_contact = "";
  public String dob = "";
  public String gender = "";
  public String interest = "";
  public String password = "";

  @Override
  public String toString() {
    return "Student{" +
        "s_reg_id='" + s_reg_id + '\'' +
        ", s_name='" + s_name + '\'' +
        ", course='" + course + '\'' +
        ", s_email='" + s_email + '\'' +
        ", s_contact='" + s_contact + '\'' +
        ", dob='" + dob + '\'' +
        ", gender='" + gender + '\'' +
        ", interest='" + interest + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
