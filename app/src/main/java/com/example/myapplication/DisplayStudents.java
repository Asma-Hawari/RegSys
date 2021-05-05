package com.example.myapplication;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.example.myapplication.Adapter.StudentsAdapter;
import com.example.myapplication.Model.StudentModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;

import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayStudents extends AppCompatActivity {

    private RecyclerView studentsRecyclerView;
    private StudentsAdapter studentsAdapter;
    private static List<StudentModel> studentModelList;
    SearchView searchView;
    String std_url = "http://10.0.2.2:8000/api/student";

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
        searchView = (SearchView) findViewById(R.id.search_view);
        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentsAdapter = new StudentsAdapter(this , studentModelList );
        studentsRecyclerView.setAdapter(studentsAdapter);
        getStudents();
//        StudentModel studentModel = new StudentModel();
//        studentModelList.add(studentModel);
//        studentModelList.add(studentModel);
//        studentModelList.add(studentModel);
//        studentModelList.add(studentModel);

//        StudentModel studentModel = new StudentModel(
//                1, "Sara", "Altaji", 2020, "female", "099999999", "Amuhajreen"
//        );
//        StudentModel studentModel2 = new StudentModel(
//                1, "Lara", "Altaji", 2020, "female", "099999999", "Amuhajreen"
//        );
//        StudentModel studentModel3 = new StudentModel(
//                1, "Amal", "Altaji", 2020, "female", "099999999", "Amuhajreen"
//        );
//        StudentModel studentModel4 = new StudentModel(
//                1, "Asma", "Altaji", 2020, "female", "099999999", "Amuhajreen"
//        );
//        studentModelList.add(studentModel);
//        studentModelList.add(studentModel2);
//        studentModelList.add(studentModel3);
//        studentModelList.add(studentModel4);


        System.out.println("TOTO " + studentModelList);
//        studentsAdapter.setItems(studentModelList);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String str) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String str) {

                if(studentsAdapter != null){
                    studentsAdapter.getFilter().filter(str);
                    System.out.println("Not Null Adapter " );
                }
                else {
                    System.out.println("Null Adapter");
                }
                return true;
            }
        });


    }

    private void getStudents() {

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, std_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("anyText", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Boolean result = jsonObject.getBoolean("result");
                            if (result == true) {
                                JSONArray data = jsonObject.getJSONArray("data");
                                JSONArray firstElementData = (JSONArray) data.get(0);
                                System.out.println("Sara .. " + firstElementData.get(0));
                                for(int i=0; i<firstElementData.length();i++){
                                    JSONObject jo = (JSONObject) firstElementData.get(i);
                                    StudentModel stdModel = new StudentModel(
                                            jo.getInt("id"), jo.getString("first_name"), jo.getString("last_name"),
                                            jo.getString("gender"), jo.getString("mobile"),jo.getString("address")

                                    );
                                    studentModelList.add(stdModel);
                                    System.out.println("TITI " + studentModelList.size() );
                                }
                                Toast.makeText(getApplicationContext(), "Students", Toast.LENGTH_LONG).show();
                                studentsAdapter.setItems(studentModelList);
//                                JSONObject login = data.getJSONObject(0);
//                                String role= data.getJSONObject(0).getJSONObject("role").getString("name");

                                //finish();
                                return;
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Registration Error !1" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Registration Error !2" + error, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "*/*");
                headers.put("Authorization", "Bearer 9b3e754fe677309b8378d28235f21c93839c9bec52789a263ada89ce3d667356");
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}