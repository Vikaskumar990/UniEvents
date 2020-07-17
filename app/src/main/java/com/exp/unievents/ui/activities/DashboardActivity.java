package com.exp.unievents.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exp.unievents.R;
import com.exp.unievents.constant.AppConstant;
import com.exp.unievents.model.Event;
import com.exp.unievents.model.Student;
import com.exp.unievents.ui.activities.menu.About;
import com.exp.unievents.ui.activities.menu.Calender;
import com.exp.unievents.ui.activities.menu.Certification;
import com.exp.unievents.ui.activities.menu.Feedback;
import com.exp.unievents.ui.activities.menu.Help;
import com.exp.unievents.ui.activities.menu.History;
import com.exp.unievents.ui.activities.menu.Inbox;
import com.exp.unievents.ui.activities.menu.OtherApp;
import com.exp.unievents.ui.activities.menu.Terms;
import com.exp.unievents.ui.adapters.EventAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.exp.unievents.constant.AppConstant.IS_SIGNED;

/**
 * @DashboardActivity class as firebase value changer
 */
public class DashboardActivity extends BaseActivity implements
      NavigationView.OnNavigationItemSelectedListener, ValueEventListener {
  private RecyclerView rvEvent;
  private List<Event> events = new ArrayList<>();
  private DrawerLayout drawer;
  private DatabaseReference databaseReference; //firebase access reference declareed
  private EventAdapter eventAdapter;
  private Student student;
  private TextView tvStName;
  private TextView tvStEmail;
  private boolean doubleBackToExitPressedOnce = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_uni_menu);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    student = getPrefObject(AppConstant.STUDENT, Student.class);
    Log.i(getClass().getSimpleName(), "onCreate:DASH " + student);
    databaseReference = FirebaseDatabase.getInstance().getReference();
    databaseReference.addValueEventListener(this);
    drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
          this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    rvEvent = findViewById(R.id.rvEvent);
    tvStName = navigationView.getHeaderView(0).findViewById(R.id.tvStName);
    tvStEmail = navigationView.getHeaderView(0).findViewById(R.id.tvStEmail);
    if (student != null) {
      tvStName.setText(student.s_name);
      tvStEmail.setText(student.s_email);
    }
    eventAdapter = new EventAdapter(this, events);
    setAdapter();
//    FloatingActionButton fab = findViewById(R.id.fab);
//    fab.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        Snackbar.make(view, "Ho Gya Mail...ha ha ha", Snackbar.LENGTH_LONG)
//              .setAction("Action", null).show();
//      }
//    });

  }

  private void setAdapter() {
    rvEvent.setHasFixedSize(false);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    rvEvent.setLayoutManager(layoutManager);
    rvEvent.setAdapter(eventAdapter);
  }

  @Override
  public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.uni_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    Intent intent = null;
    int id = item.getItemId();
    switch (id) {
      case R.id.history:
        intent = new Intent(DashboardActivity.this, History.class);
        break;
      case R.id.notification:
        intent = new Intent(DashboardActivity.this, Inbox.class);
        break;
      case R.id.certificate:
        intent = new Intent(DashboardActivity.this, Certification.class);
        break;
      case R.id.calender:
        intent = new Intent(DashboardActivity.this, Calender.class);
        break;
      case R.id.otherapp:
        intent = new Intent(DashboardActivity.this, OtherApp.class);
        break;
      case R.id.about:
        intent = new Intent(DashboardActivity.this, About.class);
        break;
      case R.id.feedback:
        intent = new Intent(DashboardActivity.this, Feedback.class);
        break;
      case R.id.tc:
        intent = new Intent(DashboardActivity.this, Terms.class);
        break;
      case R.id.help:
        intent = new Intent(DashboardActivity.this, Help.class);
        break;
      case R.id.inbox:
        intent = new Intent(DashboardActivity.this, Inbox.class);
        break;
      case R.id.lo:
        storeOrUpdateBoolean(IS_SIGNED, false);
        intent = new Intent(DashboardActivity.this, LoginActivity.class);
        finish();
        break;
    }
    if (intent != null) startActivity(intent);
    drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }


  @Override
  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

    for (DataSnapshot snapshot : dataSnapshot.child("events_tbl").getChildren()) {
      try {
        Log.i("TAGGA", "onDataChange: "+snapshot.getValue());
        Event event = snapshot.getValue(Event.class);
        event.setId(snapshot.getKey());
        events.add(event);
      } catch (Error | Exception e) {
        e.printStackTrace();
      }
    }
    eventAdapter.notifyDataSetChanged();

  }

  @Override
  public void onCancelled(@NonNull DatabaseError databaseError) {

  }

  public void editProfile(View view) {
    if (view.getId() == R.id.btEditProfile) {
      Intent intent = new Intent(this, EditProfileActivity.class);
      startActivity(intent);
      drawer = findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
    }
  }

}




