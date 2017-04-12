package com.hq.demoviewpagerandtabhost;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
                                    mangUser.set(i, new DBUser(mang[i].toString().trim()));
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
                ChuyenTrang(ten, username);
                Toast.makeText(getActivity(), ten, Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                b.setTitle("DELETE FRIEND");
                final String thisfriend = mangUser.get(position).getUsername();
                b.setMessage("Are you sure you want to delete "+thisfriend+"?");
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONObject userdelete = null;
                        try {
                            userdelete = new JSONObject("{\"username1\": \"" + username + "\", \"username2\": \"" + thisfriend + "\"}");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("hqusercheckfriend", String.valueOf(userdelete));
                        mSocket.emit("DELETE_FRIENDS", userdelete);
                        mSocket.on("SERVER_DELETE_FRIEND", new Emitter.Listener() {
                            @Override
                            public void call(final Object... args) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        JSONObject data = (JSONObject) args[0];
                                        String user;
                                        try {
                                            user = data.getString("username2");
                                            Log.d("hqUsernamedelete", user);
                                        } catch (JSONException e) {
                                            return;
                                        }
                                        Toast.makeText(getActivity(), "You and " + user + " aren't friend now", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        mSocket.on("SERVER_ERR", new Emitter.Listener() {
                            @Override
                            public void call(final Object... args) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String err;
                                        err = args[0].toString();
                                        Toast.makeText(getActivity(), "Error: " + err, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
                b.create().show();
                return false;
            }
        });


        return view;
    }

    public void ChuyenTrang(String nguoinhan, String nguoigui) {
        Intent intent = new Intent(getActivity(), FramechatActivity.class);
        intent.putExtra("chatto",nguoinhan);
        intent.putExtra("myuser",nguoigui);
        getActivity().startActivity(intent);
    }
}
