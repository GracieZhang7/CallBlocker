package com.example.administrator.icall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_number);
    }
    public void saveNumber(View view){
        EditText tNumber=(EditText)findViewById(R.id.tNumber);
        String phoneNo=tNumber.getText().toString();
       // if(Bean.isNumberInContactList(this,phoneNo))
        //    ILog.ShowMsg(this," in phone list");
        //else
       //     ILog.ShowMsg(this," not in ");
        if(phoneNo.length()<=1){
            ILog.ShowMsg(this,"Please enter the right phone no.");
            return;
        }
        try {
            boolean b=Bean.addNumber(phoneNo);
            if(b){
                ILog.ShowMsg(this,"success save new block no "+phoneNo+".");
                tNumber.setText("");
            }else{
                ILog.ShowMsg(this,"the no "+phoneNo+" has exist.");
            }
        } catch (Exception e) {
            ILog.ShowMsg(this,"error");
        }


    }
}
