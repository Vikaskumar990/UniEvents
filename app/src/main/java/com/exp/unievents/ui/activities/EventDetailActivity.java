package com.exp.unievents.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.exp.unievents.R;
import com.exp.unievents.constant.AppConstant;
import com.exp.unievents.constant.FirebaseData;
import com.exp.unievents.model.Event;
import com.exp.unievents.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.exp.unievents.constant.AppConstant.BOOK_EVENTS;
import static com.exp.unievents.constant.AppConstant.STUDENTS;

public class EventDetailActivity extends BaseActivity {

  public static final String DATA = "event_data";
  private Event event;
  private DatabaseReference mReference;
  private List<String> ids = new ArrayList<>();
  private String sID = "";
  private AppCompatButton btnBookEvent;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event_detail);
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      event = new Gson().fromJson(bundle.getString(DATA), Event.class);
    }
    sID = getPrefObject(AppConstant.STUDENT, Student.class).s_reg_id;
    mReference = FirebaseDatabase.getInstance().getReference();
    if (getSupportActionBar() != null)
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    initiateView();
    mReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.child(STUDENTS).child(sID).hasChild(BOOK_EVENTS))
          ids = (List<String>) dataSnapshot.child(STUDENTS).child(sID).child(BOOK_EVENTS).getValue();
        btnBookEvent.setEnabled(!ids.contains(event.getId()));
        btnBookEvent.setClickable(!ids.contains(event.getId()));
        if (ids.contains(event.getId())){
          btnBookEvent.setText("Booked");
          btnBookEvent.setTextColor(getResources().getColor(R.color.black));
          btnBookEvent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_button_outline));
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  @SuppressLint("SetTextI18n")
  private void initiateView() {
    AppCompatTextView tvEventName = findViewById(R.id.tvEventName);
    AppCompatTextView tvEventDateTime = findViewById(R.id.tvEStartDate);
    AppCompatTextView tvEventContact = findViewById(R.id.tvEventContact);
    AppCompatTextView tvEventEmail = findViewById(R.id.tvEventEmail);
    AppCompatTextView tvEventAddress = findViewById(R.id.tvEventAddress);
    AppCompatTextView tvEventDesc = findViewById(R.id.tvEventDesc);
    btnBookEvent = findViewById(R.id.btEventBook);
    btnBookEvent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        bookEvent();
      }
    });
    if (event!= null){
      tvEventName.setText(event.e_name);
      tvEventDateTime.setText(event.e_start_date+" "+event.e_start_time+" - "+event.e_end_date+" "+event.e_end_time);
      tvEventContact.setText(event.e_contact);
      tvEventEmail.setText(event.e_email_id);
      tvEventAddress.setText(event.e_venue);
      tvEventDesc.setText(event.e_description);
    }
  }

  private void bookEvent() {
    if (!ids.contains(event.getId())){
      ids.add(event.getId());
    }
    mReference.child(STUDENTS).child(sID).child(BOOK_EVENTS).setValue(ids);
    mReference.child(STUDENTS).child(sID).addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Log.i("TFFF", "bookEvent: "+dataSnapshot.child(BOOK_EVENTS).getValue());
        Toast.makeText(EventDetailActivity.this,"Event Booked",Toast.LENGTH_SHORT).show();
        onBackPressed();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == android.R.id.home){
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }
}
