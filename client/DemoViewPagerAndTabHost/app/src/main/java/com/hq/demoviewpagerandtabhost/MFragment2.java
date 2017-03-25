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

/**
 * Created by PC14-01 on 10/11/2016.
 */

public class MFragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_more, null);
        ListView listView = (ListView) view.findViewById(R.id.lvmore);

        String mang [] = new String[]{"Me", "Add"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, mang);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String nhan = (String) view.getTag();
                ChuyenTrang(position);
                Toast.makeText(getActivity(), nhan , Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }
    public void ChuyenTrang(int pos) {
        if (pos == 0){
            Intent intent = new Intent(getActivity(), AboutmeActivity.class);
            getActivity().startActivity(intent);
        }
        if (pos == 1){
            Intent intent = new Intent(getActivity(), AddActivity.class);
            getActivity().startActivity(intent);
        }

    }
}
