package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Adapter.StudentsAdapter;
import com.example.myapplication.Model.StudentModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayStudents extends AppCompatActivity {

    private RecyclerView studentsRecyclerView;
    private StudentsAdapter studentsAdapter;
    private List<StudentModel> studentModelList;


    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_students);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //getSupportActionBar().hide();

        studentModelList = new ArrayList<>();
        studentsRecyclerView = findViewById(R.id.students);
        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentsAdapter = new StudentsAdapter(this , studentModelList );
        studentsRecyclerView.setAdapter(studentsAdapter);

        StudentModel studentModel = new StudentModel();
        studentModelList.add(studentModel);
        studentModelList.add(studentModel);
        studentModelList.add(studentModel);
        studentModelList.add(studentModel);

        studentsAdapter.setItems(studentModelList);
    }
}