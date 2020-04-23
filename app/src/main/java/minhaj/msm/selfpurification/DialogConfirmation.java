package minhaj.msm.selfpurification;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import minhaj.msm.selfpurification.models.DateModel;


public class DialogConfirmation extends AppCompatDialogFragment {

    String msg;
    String title;
    DialogResult sender;
    Object data;

    public DialogConfirmation(String title, String message, DateModel date, DialogResult sender) {
        this.msg = message;
        this.title = title;
        this.sender = sender;
        this.data = date;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sender.setDate((DateModel) data);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }

    public interface DialogResult {
        void setDate(DateModel date);
    }
}
