package minhaj.msm.selfpurification;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import minhaj.msm.selfpurification.models.DailyReport;
import minhaj.msm.selfpurification.models.DateModel;
import minhaj.msm.selfpurification.ui.main.SectionsPagerAdapter;

import static android.content.Context.MODE_PRIVATE;

public class FragmentNew extends Fragment implements DialogConfirmation.DialogResult {
    private TextView mDisplayDate,tvInst;
    private Button btnSave;
    private CheckBox cbQ01, cbQ02, cbQ03, cbQ04, cbQ05, cbQ06, cbQ07, cbQ08, cbQ09, cbQ10;
    private DateModel date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    private static final String FILE_NAME = "uvznxomar";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDisplayDate = view.findViewById(R.id.tvDate);
        tvInst = view.findViewById(R.id.tvInst);

        btnSave = view.findViewById(R.id.btnSave);

        cbQ01 = view.findViewById(R.id.cbQ01);
        cbQ02 = view.findViewById(R.id.cbQ02);
        cbQ03 = view.findViewById(R.id.cbQ03);
        cbQ04 = view.findViewById(R.id.cbQ04);
        cbQ05 = view.findViewById(R.id.cbQ05);
        cbQ06 = view.findViewById(R.id.cbQ06);
        cbQ07 = view.findViewById(R.id.cbQ07);
        cbQ08 = view.findViewById(R.id.cbQ08);
        cbQ09 = view.findViewById(R.id.cbQ09);
        cbQ10 = view.findViewById(R.id.cbQ10);

        tvInst.setText(Html.fromHtml("<u>Instructions</u>"));
        tvInst.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            new DialogInstructions().show(getFragmentManager(),"instruction dialog");
                        }}
        );

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date == null) {
                    Toast.makeText(getActivity(), "Pleas Select Date First",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                boolean isQ01 = cbQ01.isChecked();
                boolean isQ02 = cbQ02.isChecked();
                boolean isQ03 = cbQ03.isChecked();
                boolean isQ04 = cbQ04.isChecked();
                boolean isQ05 = cbQ05.isChecked();
                boolean isQ06 = cbQ06.isChecked();
                boolean isQ07 = cbQ07.isChecked();
                boolean isQ08 = cbQ08.isChecked();
                boolean isQ09 = cbQ09.isChecked();
                boolean isQ10 = cbQ10.isChecked();

                DailyReport dr = new DailyReport(isQ01, isQ02, isQ03, isQ04, isQ05, isQ06, isQ07, isQ08, isQ09, isQ10);
                dr.setDate(date);
                if (SectionsPagerAdapter.history == null) {
                    SectionsPagerAdapter.history = new ArrayList<DailyReport>();
                }
                addOrUpdateEntry(dr);
                clearChecks(false);
                //Log.i("myTest", "btnSave Click" + dr.getScore() + " " + dr.getId() + " " + dr.getDate());
            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        FragmentNew.this.getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = new DateModel(year, month, day);
                if (getIndexById(date.getId()) > -1) {
                    ShowDialog();
                } else {
                    mDisplayDate.setText(date.getDate());
                    clearChecks(true);
                }
            }
        };
    }

    private void addOrUpdateEntry(DailyReport dr) {
        try{
            boolean inserted=false, updated=false;
            int index = getIndexById(dr.getId());
            if (index > -1) {
                SectionsPagerAdapter.history.remove(index);
                SectionsPagerAdapter.history.add(index, dr);
                updated=true;
            } else {
                SectionsPagerAdapter.history.add(dr);

                //order by id descending
                Collections.sort(SectionsPagerAdapter.history, new Comparator<DailyReport>(){
                    public int compare(DailyReport obj1, DailyReport obj2) {
                         return Integer.valueOf( obj2.getId()).compareTo(Integer.valueOf( obj1.getId()));
                    }
                });
                while (SectionsPagerAdapter.history.size()>100){
                    SectionsPagerAdapter.history.remove(100);
                }
                inserted=true;
            }
            String json = new Gson().toJson(SectionsPagerAdapter.history);
            Log.i("myJSON", json);
            saveData(json);
            //to notify change
            Intent intent = new Intent(FragmentHistory.RADIO_DATASET_CHANGED);
            getActivity().getApplicationContext().sendBroadcast(intent);

            if(inserted){ new DialogSuccess("Data saved successfully!","Thank you").show(getFragmentManager(),"insert success dialog"); }
            else if(updated){new DialogSuccess("Data updated successfully!","Thank you").show(getFragmentManager(),"update success dialog");}
        }
        catch (Exception e){new DialogSuccess(e.getMessage(),"Error").show(getFragmentManager(),"insert success dialog");}
    }

    private void saveData(String data) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SectionsPagerAdapter.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SectionsPagerAdapter.HISTORY_DATA, data);

        editor.apply();

        //Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }


    private void ShowDialog() {
        new DialogConfirmation("Are you sure!", "You want to update record for " + date.getDate(), date, this).show(getFragmentManager(), "dialog");
    }

    private int getIndexById(int id) {
        if (SectionsPagerAdapter.history != null) {
            for (DailyReport r : SectionsPagerAdapter.history) {
                if (r.getId() == id) {
                    return SectionsPagerAdapter.history.indexOf(r);
                }
            }
        }
        return -1;
    }

    private void clearChecks(boolean clearCheckOnly) {
        cbQ01.setChecked(false);
        cbQ02.setChecked(false);
        cbQ03.setChecked(false);
        cbQ04.setChecked(false);
        cbQ05.setChecked(false);
        cbQ06.setChecked(false);
        cbQ07.setChecked(false);
        cbQ08.setChecked(false);
        cbQ09.setChecked(false);
        cbQ10.setChecked(false);
        if (!clearCheckOnly) {
            mDisplayDate.setText("Select date");
            date = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new, container, false);
    }

    @Override
    public void setDate(DateModel date) {
        mDisplayDate.setText(date.getDate());
        try{
            DailyReport dr = SectionsPagerAdapter.history.get(getIndexById(date.getId()));
            cbQ01.setChecked(dr.isQ01());
            cbQ02.setChecked(dr.isQ02());
            cbQ03.setChecked(dr.isQ03());
            cbQ04.setChecked(dr.isQ04());
            cbQ05.setChecked(dr.isQ05());
            cbQ06.setChecked(dr.isQ06());
            cbQ07.setChecked(dr.isQ07());
            cbQ08.setChecked(dr.isQ08());
            cbQ09.setChecked(dr.isQ09());
            cbQ10.setChecked(dr.isQ10());
        }
        catch(Exception e){

        }
    }

}
