package com.hq.demoviewpagerandtabhost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by PC14-01 on 10/11/2016.
 */

public class MFragment3 extends Fragment {

    ListView listView;
    ArrayList<String> mangUsernames;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.56.1:3000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.activity_online, null);
        listView = (ListView) view.findViewById(R.id.lvonline);

        mangUsernames = new ArrayList<String>();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, mangUsernames);
//        mangUsernames.add("hahh");
//        mangUsernames.add("sss");
        listView.setAdapter(arrayAdapter);

        mSocket.connect();
        mSocket.on("LIST_ONLINE_USER", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        JSONArray manguser;
                        try {
                            manguser = data.getJSONArray("danhsach");
                            for (int i = 0; i < manguser.length(); i++){
                                mangUsernames.add(manguser.get(i).toString());
                            }
                        }
                        catch (JSONException e){
                            return;
                        }

                    }
                });
            }

        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                IFragment1 fragment1 = (IFragment1) getActivity();
//                fragment1.onItemClick(position);

                String ten = (String) view.getTag();
                ChuyenTrang(position);
                Toast.makeText(getActivity(), ten, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void ChuyenTrang(int pos) {
        Intent intent = new Intent(getActivity(), FramechatActivity.class);
        getActivity().startActivity(intent);
    }
}
