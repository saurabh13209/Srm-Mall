package com.saurabh.srmmall;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public abstract class NameDialogBox {
    NameDialogBox(Context context , LayoutInflater layoutInflater){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view =  layoutInflater.inflate(R.layout.name_dialog, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        final EditText editText = view.findViewById(R.id.Edit_Name);
        Button next = view.findViewById(R.id.NextButton);
        new EditBoxChange(editText, context);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                DoNext(name);
                dialog.dismiss();
            }
        });
    }


    public abstract void DoNext(String Name);



}
