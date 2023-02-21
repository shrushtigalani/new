package com.example.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//https://github.com/shrushtigalani/Registration-form.git
public class viewdata extends AppCompatActivity {


    private List<com.example.user.userModelClass> userarraylist;
    private com.example.user.DatabaseHelperClass databaseHelperClass;
    private RecyclerView recyclerView;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdata);
        logout=findViewById(R.id.logout);
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("Bool");
                editor.clear();
                editor.commit();
                Intent intent = new Intent(viewdata.this, com.example.user.register.class);
                startActivity(intent);
                finish();
            }
        });

        userarraylist = new ArrayList<>();
        databaseHelperClass = new com.example.user.DatabaseHelperClass(viewdata.this);
        userarraylist = databaseHelperClass.readusers();
        com.example.user.UserRVAdapter userRVAdapter = new com.example.user.UserRVAdapter(userarraylist, viewdata.this);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(viewdata.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userRVAdapter);

    }
}