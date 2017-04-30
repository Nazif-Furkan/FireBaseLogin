package com.example.nazif.firebasedeneme2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private Button buttonRegister;
    private Button buttonCreateAccount;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignedIn;

    static public FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog =new ProgressDialog(this);

        buttonRegister=(Button) findViewById(R.id.buttonRegister);
        buttonCreateAccount=(Button) findViewById(R.id.buttonCreateUser);
        editTextEmail=(EditText)findViewById(R.id.editTextMail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        textViewSignedIn=(TextView)findViewById(R.id.textViewSignin);
        //Eğer zaten oturum açıksa direk diğer sayfaya gider :)
        if(firebaseAuth.getCurrentUser().getEmail()!=null){
            Intent intent = new Intent(MainActivity.this, getDataActivity.class);
            MainActivity.this.startActivity(intent);
            finish();
        }
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network

                    createUser();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "İnternet bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network

                    registerUser();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "İnternet bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                }

            }


        });
    }

    private void createUser(){
        progressDialog.setMessage("Üye olunuyo");
        progressDialog.show();
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        Log.d("Kullanici olustur", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }

    private void registerUser() {

        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Lütfen bir mail adresi giriniz.",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Lütfen şifreyi giriniz.",Toast.LENGTH_SHORT).show();
            return;
        }
        //geçerli mail olduğunda.

        progressDialog.setMessage("Giriş deneniyor");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Giriş başarılı",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, getDataActivity.class);
                    MainActivity.this.startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this,"Başarısız. Tekrar deneyin.",Toast.LENGTH_SHORT).show();
                }

            }
        });

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
}
