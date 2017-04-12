package com.hq.demoviewpagerandtabhost;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import java.util.List;

/**
 * Created by My-PC on 4/7/2017.
 */

public class CustomListAdapter extends BaseAdapter {

    private List<DBUser> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context aContext,  List<DBUser> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.one_item_user, null);
            holder = new ViewHolder();
            holder.username = (TextView) convertView.findViewById(R.id.viewname);
            holder.message = (TextView) convertView.findViewById(R.id.viewmess);
            holder.countmess = (TextView) convertView.findViewById(R.id.countmess);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DBUser user = this.listData.get(position);
        holder.username.setText(user.getUsername());
        holder.message.setText(user.getMessage());

//        int imageId = this.getMipmapResIdByName(country.getFlagName());

        String count = user.getCountmess() + "";
        if (user.getCountmess() == 0){
            count = "";
        }
        holder.countmess.setText(count);


        return convertView;
    }

    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
//    public int getMipmapResIdByName(String resName)  {
//        String pkgName = context.getPackageName();
//
//        // Trả về 0 nếu không tìm thấy.
//        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
//        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
//        return resID;
//    }

    static class ViewHolder {
        TextView username;
        TextView message;
        TextView countmess;
    }
}
