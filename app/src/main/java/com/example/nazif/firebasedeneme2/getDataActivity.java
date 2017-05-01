package com.example.nazif.firebasedeneme2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class getDataActivity extends AppCompatActivity {

    ListView list;
    Button btnVeriCek;

    String userid;


    ProgressDialog progressDialogVeriGetir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userid = MainActivity.firebaseAuth.getCurrentUser().getUid();


        progressDialogVeriGetir=new ProgressDialog(this);

        btnVeriCek=(Button)findViewById(R.id.btnGetir);
        list=(ListView)findViewById(R.id.listViewVeri);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(userid);

        myRef.child("name").setValue("Furkan");
        myRef.child("score").setValue(123);


    }

}
