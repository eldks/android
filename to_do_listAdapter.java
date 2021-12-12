package com.example.studyDB;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class to_do_listAdapter  extends RecyclerView.Adapter<to_do_listAdapter.ViewHolder>
        implements OnTodoItemClickListener {
    private static final String TAG = "TodoListAdapter";
    ArrayList<to_do_list> items = new ArrayList<to_do_list>();

    OnTodoItemClickListener listener;

    int layoutType = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.to_do_item, viewGroup, false);

        return new ViewHolder(itemView, this, layoutType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        to_do_list item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.setLayoutType(layoutType);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(to_do_list item) {
        items.add(item);
    }

    public void setItems(ArrayList<to_do_list> items) {
        this.items = items;
    }

    public to_do_list getItem(int position) {
        return items.get(position);
    }

    public void setOnItemClickListener(OnTodoItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout1;
        CheckBox checkBox;
        TextView todoTextView;
        Button deleteBtn;

        public ViewHolder(View itemView, final OnTodoItemClickListener listener, int layoutType) {
            super(itemView);

            layout1 = itemView.findViewById(R.id.todo_layout);
            checkBox = itemView.findViewById(R.id.todo_checkBox);
            todoTextView = itemView.findViewById(R.id.todo_textView);
            deleteBtn = itemView.findViewById(R.id.todo_delete);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((CheckBox)view).isChecked()){
                        String TODO = (String) todoTextView.getText();
                        finishToDo(TODO);
                        Toast.makeText(view.getContext(), "완료", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                }

                Context context;

                protected void finishToDo(String TODO){
                    String deleteSql = "delete from " + PlanDatabase.TABLE_PLAN + " where " + "  TODO = '" + TODO+"'";
                    PlanDatabase database = PlanDatabase.getInstance(context);
                    database.execSQL(deleteSql);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String TODO = (String) todoTextView.getText();
                    deleteToDo(TODO);
                    Toast.makeText(view.getContext(), "삭제", Toast.LENGTH_SHORT).show();
                }

                Context context;
                protected void deleteToDo(String TODO){
                    String deleteSql = "delete from " + PlanDatabase.TABLE_PLAN + " where " + "  TODO = '" + TODO+"'";
                    PlanDatabase database = PlanDatabase.getInstance(context);
                    database.execSQL(deleteSql);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });

            setLayoutType(layoutType);
        }

        public void setItem(to_do_list item){
            todoTextView.setText(item.getContents());
        };

        public void setLayoutType(int layoutType) {
            if (layoutType == 0) {
                layout1.setVisibility(View.VISIBLE);
            } else if (layoutType == 1) {
                layout1.setVisibility(View.VISIBLE);
            }
        }
    }
}
