package minhaj.msm.selfpurification;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import minhaj.msm.selfpurification.ui.main.SectionsPagerAdapter;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    //List<HistoryModel> history;
    Context context;
    OnHistoryListener onHistoryListener;
    public HistoryAdapter(Context c,  OnHistoryListener onHistoryListener) {
        //history = n;
        context = c;
        this.onHistoryListener=onHistoryListener;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_history, parent, false);
        HistoryAdapter.MyViewHolder viewHolder = new HistoryAdapter.MyViewHolder(view,onHistoryListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {

        holder.date.setText("On "+SectionsPagerAdapter.history.get(position).getDate());

        holder.score.setText("You performed "+SectionsPagerAdapter.history.get(position).getScore()+" out of total 10 activities");
    }

    @Override
    public int getItemCount() {
        return SectionsPagerAdapter.history.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView date;
        TextView score;
        OnHistoryListener onHistoryListener;
        public MyViewHolder(@NonNull View itemView,OnHistoryListener onHistoryListener) {
            super(itemView);
            date = itemView.findViewById(R.id.tvDate);
            score = itemView.findViewById(R.id.tvScore);
            this.onHistoryListener=onHistoryListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.onHistoryListener.onHistoryClick(getAdapterPosition());
        }
    }

    public interface OnHistoryListener{
        void onHistoryClick(int position);
    }
}
