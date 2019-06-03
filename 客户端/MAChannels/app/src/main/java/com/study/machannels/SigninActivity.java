package com.study.machannels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SigninActivity extends AppCompatActivity {

    private Button signin;
    private EditText username;
    private EditText password1;
    private EditText password2;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signin=findViewById(R.id.signin);
        username=findViewById(R.id.username);
        password1=findViewById(R.id.password1);
        password2=findViewById(R.id.password2);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name= String.valueOf(username.getText());
                final String pass1= String.valueOf(password1.getText());
                final String pass2= String.valueOf(password2.getText());

                if (pass1 != pass2)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SigninActivity.this,"再次输入的新密码不一致",Toast.LENGTH_SHORT).show();
                        }
                    });
                }if(pass1==null||pass2==null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SigninActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                        }
                    });
                }if(username==null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SigninActivity.this,"请输入账号",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {

                    String url = "http://192.168.137.1:5000/user";//替换成自己的服务器地址
                    SendMessage(url, name, pass1);
                    client = new OkHttpClient();
                    new Thread() {
                        @Override
                        public void run() {
                            //String string = edit.getText().toString();
                            Request request = new Request.Builder()
                                    .url("http://192.168.137.1:5000/user?username=" + name)
                                    .get()
                                    .build();

                            try {
                                Response response = client.newCall(request).execute();
                                //System.out.println(response.body().string());
                                final String res = response.body().string();
                                if (res.equals("1")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SigninActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SigninActivity.this, "成功", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });

                                }


                            } catch (Exception e) {
                                Log.i("json------", e.getMessage() + "/" + e.getCause());
                            }
                        }
                    }.start();
                }
            }
        });
    }

    private void SendMessage(String url,final String username, String pass1)
    {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("username", username);
        formBuilder.add("password", pass1);
        Request request = new Request.Builder().url(url).post(formBuilder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SigninActivity.this,"服务器错误",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {

            }
        });

    }
}


