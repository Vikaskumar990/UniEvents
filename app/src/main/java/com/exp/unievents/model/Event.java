package com.exp.unievents.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.annotations.NotNull;

@IgnoreExtraProperties
public class Event {
//Public accessor for FireBase Model
  //e_contact=123456789, e_name=exam, e_end_time=6, e_start_time=2, e_email_id=abc@gmail.com, e_venue=lpu, e_desc=exam  Viva...  , e_end_date=5.11.19, e_start_date=2.11.19
  public String e_id;
  public String e_name;
  public String e_contact;
  public String e_start_date;
  public String e_end_date;
  public String e_start_time;
  public String e_end_time;
  public String e_email_id;
  public String e_venue;
  public String o_name;//organization name
  public String e_description;
  public String e_banner;
  private String id;

  public Event() {
  }

  @Override
  public String toString() {
    return "Event{" +
          "e_id='" + e_id + '\'' +
          ", e_name='" + e_name + '\'' +
          ", e_contact='" + e_contact + '\'' +
          ", e_start_date='" + e_start_date + '\'' +
          ", e_end_date='" + e_end_date + '\'' +
          ", e_start_time='" + e_start_time + '\'' +
          ", e_end_time='" + e_end_time + '\'' +
          ", e_email_id='" + e_email_id + '\'' +
          ", e_venue='" + e_venue + '\'' +
          ", o_name='" + o_name + '\'' +
          ", e_description='" + e_description + '\'' +
          ", e_banner='" + e_banner + '\'' +
          ", id='" + id + '\'' +
          '}';
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
