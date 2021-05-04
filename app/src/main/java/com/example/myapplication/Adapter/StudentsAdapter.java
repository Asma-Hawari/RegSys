package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DisplayStudents;
import com.example.myapplication.Model.StudentModel;
import com.example.myapplication.R;
import com.example.myapplication.Utils.DatabaseHandler;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder> {

    private List<StudentModel> studentModel;
    private DatabaseHandler db;
    private DisplayStudents displayStudentsActivity;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.studentItem);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public StudentsAdapter(
            DatabaseHandler db,
            DisplayStudents displayStudentsActivity) {
        this.db = db;
        this.displayStudentsActivity = displayStudentsActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_student_detail,parent,false);
        return new ViewHolder(itemView);
    }

    public void setItems(List<StudentModel> studentModel){
        this.studentModel = studentModel;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentModel studentItem =  studentModel.get(position);
        String firstName = studentItem.getFirst_name();
        String lastName = studentItem.getLast_name();
        holder.getTextView().setText(firstName +" "+lastName );
    }

    @Override
    public int getItemCount() {
        return studentModel.size();
    }

}
