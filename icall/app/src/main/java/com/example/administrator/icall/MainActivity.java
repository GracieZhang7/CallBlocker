package com.example.administrator.icall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioButton rbBlockAll;
    private RadioButton rbBlockUnsaved;
    private RadioButton rbBlockList;
    private RadioButton rbBlockNone;
    private Intent endCallIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        requestContactPermission();

        //init db
        String dbPath =  "/data"+Environment.getDataDirectory().getAbsolutePath() + "/"+this.getPackageName()+"/block.db";
        Database.instance(dbPath);
        Bean.createTables();
        Bean.loadList();

        //start service
        Intent  intent = new Intent(this, InterceptService.class);
        startService(intent);


        RadioGroup  radiogroup = (RadioGroup)findViewById(R.id.rgBlock);
        rbBlockAll = (RadioButton)findViewById(R.id.rbBlockAll);
        rbBlockUnsaved = (RadioButton)findViewById(R.id.rbBlockUnsaved);
        rbBlockList = (RadioButton)findViewById(R.id.rbBlockList);
        rbBlockNone = (RadioButton)findViewById(R.id.rbBlockNone);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(rbBlockAll.getId() == checkedId){
                    Bean.blockMode=Bean.BLOCK_ALL;
                    Toast.makeText(getApplicationContext(), "block all", Toast.LENGTH_SHORT).show();
                }
                else if(rbBlockUnsaved.getId() == checkedId){
                    Bean.blockMode=Bean.BLOCK_UNSAVED;
                    Toast.makeText(MainActivity.this, "block from unsaved", Toast.LENGTH_SHORT).show();
                }
                else if(rbBlockList.getId() == checkedId){
                    Bean.blockMode=Bean.BLOCK_LIST;
                    Toast.makeText(MainActivity.this, "block from list", Toast.LENGTH_SHORT).show();
                }
                else if(rbBlockNone.getId() == checkedId){
                    Bean.blockMode=Bean.BLOCK_NONE;
                    Toast.makeText(MainActivity.this, "stop block", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void requestContactPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_CONTACTS}, 0);
        }
    }
    public void addNumber(View view){
        Intent intent=new Intent(this,AddNumberActivity.class);
        startActivity(intent);
    }
    public void showList(View view){
        Intent intent=new Intent(this,ShowListActivity.class);
        startActivity(intent);
    }
}
