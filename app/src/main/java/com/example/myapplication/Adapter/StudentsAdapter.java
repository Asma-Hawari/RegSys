package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DisplayStudents;
import com.example.myapplication.Model.StudentModel;
import com.example.myapplication.R;
import com.example.myapplication.Utils.DatabaseHandler;

import java.util.List;
import java.util.ArrayList;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder> implements Filterable {

    private List<StudentModel> studentModel;
    private DatabaseHandler db;
    private DisplayStudents displayStudentsActivity;
    private List<StudentModel> mFilteredList;
    public MyFilter mFilter;



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
        this.mFilteredList = new ArrayList<StudentModel>();
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

    @Override
    public Filter getFilter() {
        if (mFilter == null){
            mFilteredList.clear();
            mFilteredList.addAll(this.studentModel);
            mFilter = new StudentsAdapter.MyFilter(this,mFilteredList);
        }
        return mFilter;
    }
    private static class MyFilter extends Filter {

        private final StudentsAdapter myAdapter;
        private final List<StudentModel> originalList;
        private final List<StudentModel> filteredList;

        private MyFilter(StudentsAdapter myAdapter, List<StudentModel> originalList) {
            this.myAdapter = myAdapter;
            this.originalList = originalList;
            this.filteredList = new ArrayList<StudentModel>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0){
                filteredList.addAll(originalList);
            }else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for ( StudentModel student : originalList){
                    String fullName = student.getFirst_name().toLowerCase() + " " + student.getLast_name().toLowerCase();
                    if (fullName.contains(filterPattern)){
                        filteredList.add(student);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            myAdapter.studentModel.clear();
            myAdapter.studentModel.addAll((ArrayList<StudentModel>)filterResults.values);
            myAdapter.notifyDataSetChanged();

        }
    }

}
