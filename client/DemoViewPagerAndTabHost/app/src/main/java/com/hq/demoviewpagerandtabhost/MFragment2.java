package com.hq.demoviewpagerandtabhost;

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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import io.socket.client.Socket;

/**
 * Created by PC14-01 on 10/11/2016.
 */

public class MFragment2 extends Fragment {

    private Socket mSocket;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_more, null);
        ListView listView = (ListView) view.findViewById(R.id.lvmore);

        MSocket ms = (MSocket) getActivity().getApplication();
        mSocket = ms.getSocket();

//        String mang [] = new String[]{"Me", "Add"};
        final ArrayList<String> mang = new ArrayList<String>();
        mang.add("About me");
        mang.add("Add friends");
        mang.add("Sign out");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, mang);

        listView.setAdapter(arrayAdapter);

        Intent intent = getActivity().getIntent();
        final String username = intent.getStringExtra("username");
        Log.d("hquserM2", username);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                String nhan = (String) view.getTag();
                String nhan = mang.get(position).toString();
                ChuyenTrang(position, username);


            }
        });
        return view;
    }

    public void ChuyenTrang(int pos, final String nhan) {
        if (pos == 0) {
            Toast.makeText(getActivity(), nhan, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), AboutmeActivity.class);
            intent.putExtra("username", nhan);
            getActivity().startActivity(intent);
        }
        if (pos == 1) {
            Toast.makeText(getActivity(), nhan, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), AddActivity.class);
            intent.putExtra("username", nhan);
            getActivity().startActivity(intent);
        }
        if (pos == 2) {
            AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
            b.setTitle("Sign out");
            b.setMessage("Are you sure you want to sign out?");
            b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mSocket.disconnect();
                    Toast.makeText(getActivity(), nhan, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), SigninActivity.class);
                    getActivity().startActivity(intent);
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
        }

    }
}
