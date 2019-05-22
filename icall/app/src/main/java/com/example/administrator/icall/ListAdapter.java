package com.example.administrator.icall;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.SimpleAdapter;

public class ListAdapter extends SimpleAdapter{
    protected final Context context;
    protected int[] to;
    protected List<HashMap<String,?>> data=new ArrayList();
    protected LayoutInflater mInflater;
    protected String[] from;
    protected int resource;
    public List<HashMap<String, ?>> getData() {
        return data;
    }
    public int  getSize() {
        return data==null?0:data.size();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        return  super.getView(position, convertView, parent);

    }
    public ListAdapter(Context context, List<HashMap<String,?>> data,int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mInflater = LayoutInflater.from(context);//(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data==null?new ArrayList():data;
        this.from=from;
        this.to=to;
        this.resource=resource;
        this.context=context;
    }
    public void updateData(List<HashMap<String, ?>> items) {
        this.data.clear();
        this.data.addAll(items);
        this.notifyDataSetChanged();
    }
    public void appendData(List<HashMap<String, ?>> items) {
        this.data.addAll(items);
        this.notifyDataSetChanged();
    }
    public void deleteData(int position) {
        this.data.remove(position);
        this.notifyDataSetChanged();
    }
}
