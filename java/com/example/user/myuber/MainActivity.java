package com.example.user.myuber;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String TAG="MainActivity";
    Button signin,register;

    EditText email,password;
    private String name,password1;

    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        signin=(Button)findViewById(R.id.signin);
        register=(Button)findViewById(R.id.register);
        email=(EditText) findViewById(R.id.editText);
       password=(EditText) findViewById(R.id.editText2);

       signin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               name=email.getText().toString();
               password1=password.getText().toString();
               if (!(name.matches("")) && !(password1.matches("")))
               {mAuth.signInWithEmailAndPassword(name,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful())
                       {
                           Intent in=new Intent(MainActivity.this, Main2Activity.class);
                           String strName = mAuth.getUid().toString();
                           in.putExtra("id", strName);

                           startActivity(in);

                       }
                       else {
                           Toast.makeText(MainActivity.this, "Somewthing went wrong", Toast.LENGTH_SHORT).show();


                       }

               }
           });
       }else {Toast.makeText(MainActivity.this,"Enter email or password",Toast.LENGTH_SHORT).show();}

           }

       });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = email.getText().toString();
                password1 = password.getText().toString();
                if (!(name.matches("")) && !(password1.matches(""))) {
                    mAuth.createUserWithEmailAndPassword(name, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                DatabaseReference myRef = database.getReference(mAuth.getUid().toString());
                                myRef.child("email").setValue(name);
                                myRef.child("password").setValue(password1);
                                myRef.child("id").setValue(mAuth.getUid().toString());
                            } else {
                                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();


                            }
                        }
                    });
                }
                else { Toast.makeText(MainActivity.this,"Enter email or password",Toast.LENGTH_SHORT).show();}
            } }


                );
    }



}
