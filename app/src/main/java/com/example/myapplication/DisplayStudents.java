package com.example.myapplication;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.example.myapplication.Adapter.StudentsAdapter;
import com.example.myapplication.Model.StudentModel;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.Adapter.StudentsAdapter;
import com.example.myapplication.Model.StudentModel;
import com.example.myapplication.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;

import android.util.Log;
import android.view.View;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayStudents extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView studentsRecyclerView;
    private StudentsAdapter studentsAdapter;
    private static List<StudentModel> studentModelList;
    SearchView searchView;
    String std_url = "http://free-learn.net/api/student";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private DatabaseHandler db;
    private FloatingActionButton fab ;



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

        db = new DatabaseHandler(this);
        db.openDatabase();
        studentModelList = new ArrayList<>();
        fab = findViewById(R.id.addStudents);
        studentsRecyclerView = findViewById(R.id.students);
        searchView = (SearchView) findViewById(R.id.search_view);
        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentsAdapter = new StudentsAdapter(db,this  );
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




        StudentModel studentModel = new StudentModel();
        studentModelList.add(studentModel);
        studentModelList.add(studentModel);
        studentModelList.add(studentModel);
        studentModelList.add(studentModel);

        studentsAdapter.setItems(studentModelList);
        //getData1();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddStudent.newInstance().show(getSupportFragmentManager(), AddStudent.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        studentModelList = db.getAllTasks();
        Collections.reverse(studentModelList);
        studentsAdapter.setItems(studentModelList);
        studentsAdapter.notifyDataSetChanged();
    }

    private void getData() {

        mStringRequest = new StringRequest(Request.Method.GET, URLs.GET_STUDENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("anyText", String.valueOf(response));
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    Log.e("anyText",msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonObject = response.getJSONObject(i);

                        StudentModel studentModel = new StudentModel();
                        studentModel.setId(jsonObject.getInt("user_id"));
                        studentModel.setFirst_name(jsonObject.getString("first_name"));
                        studentModel.setLast_name(jsonObject.getString("last_name"));
                        studentModel.setReg_year(jsonObject.getInt("reg_year"));
                        studentModel.setGender(jsonObject.getString("gender"));
                        studentModel.setMobile(jsonObject.getString("mobile"));
                        studentModel.setAddress(jsonObject.getString("address"));

                        studentModelList.add(studentModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                studentsAdapter.setItems(studentModelList);*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();

                params.put("Authorization", "bearer " +"9b3e754fe677309b8378d28235f21c93839c9bec52789a263ada89ce3d667356");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(mStringRequest);
    }

    private void getData1() {
        //String url = URLs.GET_STUDENTS;
        String url ="https://simplifiedcoding.net/demos/view-flipper/heroes.php";
        Log.e("URLS", url);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Respnose", response);
                try {

                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);

                    //we have the array named hero inside the object
                    //so here we are getting that json array
                    JSONArray heroArray = obj.getJSONArray("heroes");

                    //now looping through all the elements of the json array
                    for (int i = 0; i < heroArray.length(); i++) {
                        //getting the json object of the particular index inside the array
                        JSONObject heroObject = heroArray.getJSONObject(i);

                       Log.e("hero",heroObject.getString("name"));
                    }

                    //JSONObject jsonObject=new JSONObject(response);
                   // JSONArray  array=jsonObject.getJSONArray("data");
                    /*for (int i=0; i<array.length(); i++){
                        JSONObject obj = array.getJSONObject(i);
                        StudentModel studentModel = new StudentModel();
                        studentModel.setId(obj.getInt("user_id"));
                        studentModel.setFirst_name(obj.getString("first_name"));
                        studentModel.setLast_name(obj.getString("last_name"));
                        studentModel.setReg_year(obj.getInt("reg_year"));
                        studentModel.setGender(obj.getString("gender"));
                        studentModel.setMobile(obj.getString("mobile"));
                        studentModel.setAddress(obj.getString("address"));

                        //studentModelList.add(studentModel);
                    }*/
                    //studentsAdapter.setItems(studentModelList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG,"Error :" + error.toString());
            }
        });/*   {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();

                params.put("Authorization", "bearer " +"9b3e754fe677309b8378d28235f21c93839c9bec52789a263ada89ce3d667356");

                return params;
            }
        };*/
        RequestQueue requestQueue= Volley.newRequestQueue(DisplayStudents.this);
        requestQueue.add(stringRequest);
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