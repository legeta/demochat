package com.hq.demoviewpagerandtabhost;

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

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by PC14-01 on 10/11/2016.
 */

public class MFragment3 extends Fragment {

    ListView listView;
//    ArrayList<String> mangUsernames;
    private Socket mSocket;
    private Button btnRefesh;
    private int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MSocket ms = (MSocket) getActivity().getApplication();
        mSocket = ms.getSocket();
        View view = inflater.inflate(R.layout.activity_online, null);
        listView = (ListView) view.findViewById(R.id.lvonline);
        btnRefesh = (Button) view.findViewById(R.id.refeshOnline);

        Intent intent = getActivity().getIntent();
        final String username = intent.getStringExtra("username");
        Log.d("hquserM2",username);

//        mangUsernames = new ArrayList<String>();
        final List<DBUser> mangUsernames = new ArrayList<DBUser>();

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line, mangUsernames);

        mSocket.on("LIST_ONLINE_USER", new Emitter.Listener() {
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
                            mangUsernames.add(new DBUser(mang[i].toString().trim()));
                        }
                    }
                });
            }
        });
        listView.setAdapter(new CustomListAdapter(this.getContext(), mangUsernames));

//        Intent intent = getActivity().getIntent();
//        final String username = intent.getStringExtra("manguseronline");
//        ArrayList<String> manguseronl = intent.getStringArrayListExtra("manguseronline");

        Log.d("hquseronline", String.valueOf(mangUsernames));

//        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                IFragment1 fragment1 = (IFragment1) getActivity();
//                fragment1.onItemClick(position);

                String ten = mangUsernames.get(position).toString();
                ChuyenTrang(ten);
                Toast.makeText(getActivity(), ten, Toast.LENGTH_SHORT).show();
            }
        });

        JSONObject data = null;
        try {
            data = new JSONObject("{\"userSoc\": \"" + username + "\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("hqusercheckfriend", String.valueOf(data));
        final JSONObject finalData = data;
        btnRefesh.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v)
            {
                mSocket.emit("CHECK_ONLINE", finalData);

                mSocket.on("LIST_ONLINE_USER", new Emitter.Listener() {
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
                                    mangUsernames.set(i, new DBUser(mang[i].toString().trim()));
                                }
                            }
                        });
                    }
                });
                listView.setAdapter(new CustomListAdapter(getView().getContext(), mangUsernames));
            }
        });

        return view;
    }

    public void ChuyenTrang(String ten) {
        Intent intent = new Intent(getActivity(), FramechatActivity.class);
        intent.putExtra("chatto",ten);
        getActivity().startActivity(intent);
    }
}
