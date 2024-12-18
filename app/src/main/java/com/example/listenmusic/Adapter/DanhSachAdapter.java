package com.example.listenmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listenmusic.Models.danhsach;
import com.example.listenmusic.R;

import java.util.List;

public class DanhSachAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<danhsach> danhsachList;

    public DanhSachAdapter(int layout, Context context, List<danhsach> danhsachList) {
        this.layout = layout;
        this.context = context;
        this.danhsachList = danhsachList;
    }

    @Override
    public int getCount() {
        return danhsachList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        ImageView imgHinh;
        TextView txtTenBaiHat, txtTacGia;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder holder;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder = new ViewHolder();
            //ánh xạ view
            holder.txtTenBaiHat=(TextView) view.findViewById(R.id.tv_tenbai);
            holder.txtTacGia=(TextView) view.findViewById(R.id.tv_tentacgia);
            holder.imgHinh=(ImageView) view.findViewById(R.id.img_hinh);
            view.setTag(holder);
        }else
            holder = (ViewHolder) view.getTag();
        //gán giá trị
        danhsach danhsach= danhsachList.get(i);
        holder.txtTenBaiHat.setText(danhsach.getTenbaihat());
        holder.txtTacGia.setText(danhsach.getCasi());
        holder.imgHinh.setImageResource(danhsach.getHinh());
        return view;
    }
}
