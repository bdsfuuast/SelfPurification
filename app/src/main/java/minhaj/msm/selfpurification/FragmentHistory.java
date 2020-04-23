package minhaj.msm.selfpurification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import minhaj.msm.selfpurification.models.DailyReport;
import minhaj.msm.selfpurification.ui.main.SectionsPagerAdapter;

import static android.content.Context.MODE_PRIVATE;

public class FragmentHistory extends Fragment implements HistoryAdapter.OnHistoryListener {
    public static final String RADIO_DATASET_CHANGED = "minhaj.msm.selfpurification.RADIO_DATASET_CHANGED";

    private Radio radio;

    RelativeLayout cardMax, cardAvg;

    TextView tvMax,tvAvg;
    RecyclerView rv_history;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radio = new Radio();
        view = view;
        rv_history = view.findViewById(R.id.rv_history);
        tvAvg = view.findViewById(R.id.tvAvg);
        tvMax = view.findViewById(R.id.tvMax);

        cardMax = view.findViewById(R.id.cardMax);
        cardAvg = view.findViewById(R.id.cardAvg);

        cardMax.setBackgroundColor(this.getResources().getColor(R.color.lightOrange));
        cardAvg.setBackgroundColor(this.getResources().getColor(R.color.lightBlue));

        Log.i("myBeforeCall", "Before Call ");
        //new BDSApiHelper().getDonationDetail(view,this);
        loadData();
        updateRecyclerView(view);
        refreshCalcs();
    }

    public void updateRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_history);
        HistoryAdapter adapter = new HistoryAdapter(this.getContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("myonResume", "onResume");
        IntentFilter filter = new IntentFilter();
        filter.addAction(RADIO_DATASET_CHANGED);
        getActivity().getApplicationContext().registerReceiver(radio, filter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            getActivity().getApplicationContext().unregisterReceiver(radio);
        }catch (Exception e){
            //Cannot unregister receiver
        }

    }
    public void loadData() {
        try {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SectionsPagerAdapter.SHARED_PREFS, MODE_PRIVATE);
            String json = sharedPreferences.getString(SectionsPagerAdapter.HISTORY_DATA, "");

            Log.i("myTest", "data" + json);
            if (!json.equals("")) {
                Type collectionType = new TypeToken<List<DailyReport>>() {
                }.getType();
                SectionsPagerAdapter.history = new Gson().fromJson(json, collectionType);

            } else SectionsPagerAdapter.history = new ArrayList<DailyReport>();
        } catch (Exception e) {
            SectionsPagerAdapter.history = new ArrayList<DailyReport>();
        }
    }

    void refreshCalcs() {
        if(SectionsPagerAdapter.history==null||SectionsPagerAdapter.history.size()<1){
            return;
        }
        int max = 0;
        float sum = 0;
        for (DailyReport dr : SectionsPagerAdapter.history) {
            max = dr.getScore() > max ? dr.getScore() : max;
            sum += dr.getScore();
        }
        int size = SectionsPagerAdapter.history.size();
        float avg = sum / size;
        tvAvg.setText(String.format("%.2f", avg));
        tvMax.setText(max+"");
    }
    @Override
    public void onHistoryClick(int position) {
        new DialogDetail(SectionsPagerAdapter.history.get(position)).show(getFragmentManager(),"request dialog");
    }

    private class Radio extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RADIO_DATASET_CHANGED)) {
                rv_history.getAdapter().notifyDataSetChanged();
                refreshCalcs();
            }
        }
    }
}
/*
1.	Tahajud Prayer

2.	Recitation of Holy Quran

3.	Ishraq Prayer

4.	Chasht Prayer

5.	Awabeen Prayers

6.	Lecture Shykh ul Islam Dr. M. Tahir Ul Qadri

7.	Daily Tasbihat

8.	Five times Fard Parayer

9.	Reading of HSI’ Book

10.	1-2 Hadiths Al-Minhaj Al Sawi

*/