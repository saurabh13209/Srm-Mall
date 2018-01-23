package com.saurabh.srmmall;


import android.content.Context;
import android.view.View;
import android.widget.EditText;

public class EditBoxChange {
    EditBoxChange(final EditText editText , final Context context){
        
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.isFocused()){
                    editText.setBackground(context.getResources().getDrawable(R.drawable.account_edittext_box_clicked));
                }else {
                    editText.setBackground(context.getResources().getDrawable(R.drawable.account_edittext_box));
                }
            }
        });
    }
}
