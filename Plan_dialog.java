package com.example.studyDB;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class Plan_dialog extends DialogFragment {
    private static final String TAG = "PlanDialog";
    EditText editPlan;
    CheckBox setAlarm;
    CalendarView plan_cal;
    TimePicker timePicker;
    Context context;
    String todo;

    CustomDialogListener customDialogListener;

    /*
    public Plan_dialog(){
        //super(context);
        //this.context = context;
    }
     */

    public static Plan_dialog newInstance(CustomDialogListener listener){
        Plan_dialog dialog = new Plan_dialog();
        dialog.customDialogListener = listener;
        return dialog;
    }

    public interface CustomDialogListener{
        void onSaveClicked(String plan);
        //void onCancelClicked();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.plan_dialog, null);

        //EditText editplan = new EditText(getContext());
        //editPlan = view.findViewById(R.id.edit_Plan);
        setAlarm = view.findViewById(R.id.alarmCheck);
        plan_cal = view.findViewById(R.id.planCal);
        timePicker = view.findViewById(R.id.timePicker);

        builder.setView(view)
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editPlan = view.findViewById(R.id.edit_Plan);
                        saveToDo();
                        Toast.makeText(getContext(),"?????????????????????.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("??????", null);

        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }

    private void saveToDo(){
        //EditText??? ?????? ?????? ????????????
        todo = editPlan.getText().toString();

        //???????????? ?????? ???????????? sql?????? insert...
        String sqlSave = "insert into " + PlanDatabase.TABLE_PLAN + " (TODO) values (" +
                "'" + todo + "')";

        //sql??? ??????
        PlanDatabase database = PlanDatabase.getInstance(context);
        database.execSQL(sqlSave);

        editPlan.setText("");
    }

    //builder.setView(editPlan);
        /*
        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox)view).isChecked();
                if (checked){
                    plan_cal.setVisibility(View.VISIBLE);
                    timePicker.setVisibility(View.VISIBLE);
                } else{

                }
            }
        });

         */

}
