package com.example.administrator.icall;

import java.util.HashMap;
import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class BlockListAdapter extends ListAdapter{
	//private DicActivity mActivity;
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		if(convertView!=null){
			holder=(Holder) convertView.getTag();  
		}
		else{
			holder=new Holder();  
			convertView=mInflater.inflate(R.layout.block_list, null);
			holder.bEdit =(Button) convertView.findViewById(R.id.button_edit_phone);
			holder.bDel =(Button) convertView.findViewById(R.id.button_del_phone);
			holder.tPhoneNo =(EditText) convertView.findViewById(R.id.text_phone_no);
			convertView.setTag(holder);
			holder.bEdit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(holder.tPhoneNo.isEnabled()){
						//save edit
						//holder.tPhoneNo.setEnabled(false);
						HashMap<String,String> map=(HashMap<String,String>)data.get(position);
						String phoneNo=holder.tPhoneNo.getText().toString();
						try {
							boolean b = Bean.editNumber(map.get("id").toString(),phoneNo);
							if(!b){
								ILog.ShowMsg(context,"the no "+phoneNo+" has exist.");
							}else{
								map.put("phone_no",phoneNo);
								holder.tPhoneNo.setEnabled(false);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}



					}else{
						//enter edit
						holder.tPhoneNo.setEnabled(true);
					}

					//mActivity.showObjectList(map);
				}
			});
			holder.bDel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					HashMap<String,String> map=(HashMap<String,String>)data.get(position);
					Bean.delNumber(map.get("id").toString());
					deleteData(position);
				}
			});
		}
		HashMap<String,String> map=(HashMap<String,String>)data.get(position);
		holder.tPhoneNo.setText(map.get("phone_no"));
		return convertView;  
	}
	public BlockListAdapter(Context context, List<HashMap<String,?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		//this.mActivity=context;
	}
	class Holder{  
		public EditText tPhoneNo;
		public Button bEdit;
		public Button bDel;
	}
}
