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

public class MFragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        TextView textView = new TextView(container.getContext());
//        textView.setText("Container 1");
//        textView.setBackgroundColor(Color.GRAY);
//
//        return textView;

        View view = inflater.inflate(R.layout.activity_db, null);
        ListView listView = (ListView) view.findViewById(R.id.lvdb);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, new String[]{"a", "b", "c"});

        listView.setAdapter(arrayAdapter);

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
