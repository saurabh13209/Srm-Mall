package com.saurabh.srmmall;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

abstract public class TextDialogBox {

    TextDialogBox(Context context , LayoutInflater layoutInflater) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = layoutInflater.inflate(R.layout.blocked_layout, null);

        TextView TextHead = view.findViewById(R.id.TextDialogHead);
        TextView TextHead2 = view.findViewById(R.id.TextDialogHead2);
        TextView TextHead3 = view.findViewById(R.id.TextDialogHead3);

        TextHead.setText(setHead1());
        TextHead2.setText(setHead2());
        TextHead3.setText(setHead3());

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        Button ok = view.findViewById(R.id.BlockButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                AfterClick();
            }
        });
    }

    abstract void AfterClick();
    abstract String setHead1();
    abstract String setHead2();
    abstract String setHead3();
}
