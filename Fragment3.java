package com.example.studyDB;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Date;

public class Fragment3 extends Fragment {
    private static final String TAG = "Fragment3";
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    to_do_listAdapter adapter;
    PlanDatabase database;

    public static PlanDatabase planDatabase = null;
    to_do_list item;
    //int mMode = AppConstants.MODE_INSERT;
    //int _id = -1;

    //int planIndex = 0;
    //int timeIndex = 0;

    //Plan_dialog.CustomDialogListener

    Context context;
    OnTabItemSelectedListener listener;
    //OnRequestListener requestListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment3,container,false);
        initUI(rootView);
        openDatabase();

        // check current location
        /*
        if (requestListener != null) {
            requestListener.onRequest("getCurrentLocation");
        }
         */
        loadNoteListData();

        swipeRefreshLayout = rootView.findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNoteListData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //applyItem();
        //loadPlanListData();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        if (context instanceof OnTabItemSelectedListener) {
            listener = (OnTabItemSelectedListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (context != null) {
            context = null;
            listener = null;
        }
    }

    private void initUI(ViewGroup rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new to_do_listAdapter();

        Button todoWriteButton = rootView.findViewById(R.id.todoWriteButton);
        todoWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnTodoItemClickListener() {
            @Override
            public void onItemClick(to_do_listAdapter.ViewHolder holder, View view, int position) {
                to_do_list item = adapter.getItem(position);

            }
        });
    }
    public int loadNoteListData(){

        //데이터를 가져오는 sql문 select... (id의 역순으로 정렬)
        String loadSql = "select _id, TODO from " + PlanDatabase.TABLE_PLAN + " order by _id desc";

        int recordCount = -1;
        database = PlanDatabase.getInstance(context);

        if(database != null){
            //cursor를 객체화하여 rawQuery문 저장
            Cursor outCursor = database.rawQuery(loadSql);

            recordCount = outCursor.getCount();

            //_id, TODO가 담겨질 배열 생성
            ArrayList<to_do_list> items = new ArrayList<>();

            //for문을 통해 하나하나 추가
            for(int i = 0; i < recordCount; i++){
                outCursor.moveToNext();

                int _id = outCursor.getInt(0);
                String todo = outCursor.getString(1);
                items.add(new to_do_list(_id,todo));
            }
            outCursor.close();

            //어댑터에 연결 및 데이터셋 변경
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }

        return recordCount;
    }


    private void openDialog(){
        Plan_dialog dialogFragment = Plan_dialog.newInstance(new Plan_dialog.CustomDialogListener() {
            @Override
            public void onSaveClicked(String plan) {
            }
        });
        //dialogFragment.setTargetFragment(this, 0);

        dialogFragment.show(getFragmentManager(), "show");
    }

    public void openDatabase() {
        // open database
        if (planDatabase != null) {
            planDatabase.close();
            planDatabase = null;
        }

        planDatabase = PlanDatabase.getInstance(context);
        boolean isOpen = planDatabase.open();
        if (isOpen) {
            Log.d(TAG, "database is open.");
        } else {
            Log.d(TAG, "database is not open.");
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (planDatabase != null) {
            //planDatabase.close();
            planDatabase = null;
        }
    }


}