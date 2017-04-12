package com.hq.demoviewpagerandtabhost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by PC14-01 on 10/11/2016.
 */

public class MFragment1 extends Fragment {

    private Socket mSocket;
    private Button btnRefesh;
    private int count = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MSocket ms = (MSocket) getActivity().getApplication();
        mSocket = ms.getSocket();
        View view = inflater.inflate(R.layout.activity_db, null);
        final ListView listView = (ListView) view.findViewById(R.id.lvdb);
        btnRefesh = (Button) view.findViewById(R.id.btnRefesh);

        Intent intent = getActivity().getIntent();
        final String username = intent.getStringExtra("username");
        Log.d("hquserM2",username);


        final List<DBUser> mangUser = new ArrayList<DBUser>();


        JSONObject data = null;
        try {
            data = new JSONObject("{\"userSoc\": \"" + username + "\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("hqusercheckfriend", String.valueOf(data));



        mSocket.emit("CHECK_FRIENDS", data);

        mSocket.on("SERVER_RETURN_FRIENDS", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String chuoi =  args[0].toString();
                        Log.d("hqusermangfriend", chuoi);
                        String mang[] = chuoi.split(",");
//                        Log.d("hquserchuoifriend",)
                        for (int i = 0; i < mang.length; i++) {
                            mangUser.add(new DBUser(mang[i].toString().trim()));
                        }
                    }
                });
            }
        });



//        final List<DBUser> mangUser = list;
        listView.setAdapter(new CustomListAdapter(this.getContext(), mangUser));

        final JSONObject finalData = data;
        btnRefesh.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v)
            {
                mSocket.emit("CHECK_FRIENDS", finalData);

                mSocket.on("SERVER_RETURN_FRIENDS", new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String chuoi =  args[0].toString();
                                Log.d("hqusermangfriend", chuoi);
                                String mang[] = chuoi.split(",");
//                        Log.d("hquserchuoifriend",)
                                for (int i = 0; i < mang.length; i++) {
                                    mangUser.add(new DBUser(mang[i].toString().trim()));
                                }
                            }
                        });
                    }
                });
                listView.setAdapter(new CustomListAdapter(getView().getContext(), mangUser));
            }
        });

        mSocket.on("RECEIVE_NEW_MESSAGE", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String usersend, message;
                        try {
                            message = data.getString("mess");
                            usersend = data.getString("usersend");
//
                            Log.d("hqusermess", data.getString("mess"));
                            Log.d("hqUsersend", data.getString("usersend"));
                        } catch (JSONException e) {
                            return;
                        }
//                        if (mangUser.contains())

                    }
                });
            }

        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String ten = mangUser.get(position).getUsername();
                ChuyenTrang(ten);
                Toast.makeText(getActivity(), ten, Toast.LENGTH_SHORT).show();
            }
        });
        return view;


    }

    private List<DBUser> getListData() {
        List<DBUser> list = new ArrayList<DBUser>();
        DBUser vietnam = new DBUser("aaaa", "vn", count);
        DBUser usa = new DBUser("bbbb", "us", 320000000);
        DBUser russia = new DBUser("cccc", "ru", 142000000);


        list.add(vietnam);
        list.add(usa);
        list.add(russia);

        return list;
    }

    public void ChuyenTrang(String ten) {
        Intent intent = new Intent(getActivity(), FramechatActivity.class);
        intent.putExtra("chatto",ten);
        getActivity().startActivity(intent);
    }
}
