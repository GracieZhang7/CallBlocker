package com.example.administrator.icall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ShowListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        ListView listView=(ListView)findViewById(R.id.lBlockList);
        BlockListAdapter adapter = new BlockListAdapter(this,Bean.lstBlock, R.layout.block_list, new String[]{"phone_no"}, new int[]{R.id.text_phone_no});
        listView.setAdapter(adapter);
    }
}
