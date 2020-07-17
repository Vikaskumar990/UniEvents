package com.exp.unievents.ui.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.exp.unievents.R;
import com.exp.unievents.model.Event;
import com.exp.unievents.ui.activities.DashboardActivity;
import com.exp.unievents.ui.activities.EventDetailActivity;
import com.google.gson.Gson;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
  private List<Event> events;
  private Activity activity;
  public EventAdapter(Activity activity, List<Event> events) {
    this.activity = activity;
    this.events = events;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_event,parent,false);
    return new ViewHolder(view);
  }

  @SuppressLint("SetTextI18n")
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
   if (events.size()>0) {
     Event singleEvent = events.get(position);
     holder.tvEventName.setText(singleEvent.e_name);
     holder.tvEventTime.setText(singleEvent.e_start_date+" "+singleEvent.e_start_time+"-"+singleEvent.e_end_date+" "+singleEvent.e_end_time);
     holder.tvEventLocation.setText(singleEvent.e_venue);
   }

  }

  @Override
  public int getItemCount() {
    return events.size();
    //return 5;
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    final TextView tvEventName,tvEventTime,tvEventLocation;
    final AppCompatButton btEventView;
    ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvEventName = itemView.findViewById(R.id.tvEventName);
      tvEventTime = itemView.findViewById(R.id.tvEventTime);
      tvEventLocation = itemView.findViewById(R.id.tvEventLocation);
      btEventView = itemView.findViewById(R.id.btEventView);
      itemView.setOnClickListener(this);
      btEventView.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
      Intent intent = new Intent(activity, EventDetailActivity.class);
      intent.putExtra(EventDetailActivity.DATA , new Gson().toJson(events.get(getAdapterPosition())));
      activity.startActivity(intent);
    }
  }
}
