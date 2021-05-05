package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private Button btnRequest;
    private Button btn_login;
    private EditText email;
    private EditText editpassword;
    private String username;
    private String password;


    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://free-learn.net/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        btn_login = (Button) findViewById(R.id.btn_login);
        email = (EditText) findViewById(R.id.email);
        editpassword = (EditText) findViewById(R.id.password);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login();
                startMain();

            }});


        /*btnRequest = (Button) findViewById(R.id.buttonRequest);

        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              sendAndRequestResponse();

                                          }
                                      }

        );*/

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void startMain(){
        Intent i = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(){

        //first getting the values
         username = email.getText().toString();
         password = editpassword.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(username)) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editpassword.setError("Please enter your password");
            editpassword.requestFocus();
            return;
        }
        sendAndRequestResponse();
        //startMain();

    }

    public void sendAndRequestResponse() {

        Log.e(TAG, "Successfully sendAndRequestResponse ");

        //Toast.makeText(getApplicationContext(), "Response :" + "toast", Toast.LENGTH_LONG).show();//display the response on screen
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(LoginActivity.this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e(TAG, "Successfully signed in : " + response.toString());
                //Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                Log.e("anyText",response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String msg = jsonObject.getString("msg");
                    JSONArray data = jsonObject.getJSONArray("data");

                    Log.e("Result",result);
                    User user = null;

                    for (int i = 0; i < data.length(); i++)
                    {
                        //String post_id = c("post_id");
                        JSONObject role= data.getJSONObject(i).getJSONObject("role");
                        String name = role.getString("name");
                        user = new User(
                                data.getJSONObject(i).getInt("id"),
                                name,
                                data.getJSONObject(i).getString("email"),
                                data.getJSONObject(i).getString("api_token"),
                                data.getJSONObject(i).getInt("role_id")

                        );
                    }
                    //storing the user in shared preferences
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                    if(msg.equals("User Login Successfully")){
                        Toast.makeText(getApplicationContext(),"Logged In  Success",Toast.LENGTH_LONG).show();

                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Registration Error !1"+e,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();

                params.put("email",username);
                params.put("password",password);

                return params;
            }
        };





        mRequestQueue.add(mStringRequest);
    }
}