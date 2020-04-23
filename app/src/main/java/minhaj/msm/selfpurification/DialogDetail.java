package minhaj.msm.selfpurification;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import minhaj.msm.selfpurification.models.DailyReport;

public class DialogDetail extends AppCompatDialogFragment {
    private DailyReport data;

    private TextView tvA01;
    private TextView tvA02;
    private TextView tvA03;
    private TextView tvA04;
    private TextView tvA05;
    private TextView tvA06;
    private TextView tvA07;
    private TextView tvA08;
    private TextView tvA09;
    private TextView tvA10;

    public DialogDetail(DailyReport data){
        this.data=data;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_detail,null);

        tvA01=view.findViewById(R.id.tvA01);
        tvA02=view.findViewById(R.id.tvA02);
        tvA03=view.findViewById(R.id.tvA03);
        tvA04=view.findViewById(R.id.tvA04);
        tvA05=view.findViewById(R.id.tvA05);
        tvA06=view.findViewById(R.id.tvA06);
        tvA07=view.findViewById(R.id.tvA07);
        tvA08=view.findViewById(R.id.tvA08);
        tvA09=view.findViewById(R.id.tvA09);
        tvA10=view.findViewById(R.id.tvA10);

        tvA01.setText(data.isQ01()?"Yes":"No");
        tvA02.setText(data.isQ02()?"Yes":"No");
        tvA03.setText(data.isQ03()?"Yes":"No");
        tvA04.setText(data.isQ04()?"Yes":"No");
        tvA05.setText(data.isQ05()?"Yes":"No");
        tvA06.setText(data.isQ06()?"Yes":"No");
        tvA07.setText(data.isQ07()?"Yes":"No");
        tvA08.setText(data.isQ08()?"Yes":"No");
        tvA09.setText(data.isQ09()?"Yes":"No");
        tvA10.setText(data.isQ10()?"Yes":"No");

        tvA01.setTextColor(this.getResources().getColor(data.isQ01()?R.color.green:R.color.orange));
        tvA02.setTextColor(this.getResources().getColor(data.isQ02()?R.color.green:R.color.orange));
        tvA03.setTextColor(this.getResources().getColor(data.isQ03()?R.color.green:R.color.orange));
        tvA04.setTextColor(this.getResources().getColor(data.isQ04()?R.color.green:R.color.orange));
        tvA05.setTextColor(this.getResources().getColor(data.isQ05()?R.color.green:R.color.orange));
        tvA06.setTextColor(this.getResources().getColor(data.isQ06()?R.color.green:R.color.orange));
        tvA07.setTextColor(this.getResources().getColor(data.isQ07()?R.color.green:R.color.orange));
        tvA08.setTextColor(this.getResources().getColor(data.isQ08()?R.color.green:R.color.orange));
        tvA09.setTextColor(this.getResources().getColor(data.isQ09()?R.color.green:R.color.orange));
        tvA10.setTextColor(this.getResources().getColor(data.isQ10()?R.color.green:R.color.orange));

        builder.setView(view)
                .setTitle("Detail")
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }
}
