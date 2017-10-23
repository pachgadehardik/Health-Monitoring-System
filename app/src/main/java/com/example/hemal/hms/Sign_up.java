package com.example.hemal.hms;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Sign_up extends AppCompatActivity {
    DBHelper myDB;
    EditText name,mail,pass,sex,weight,height,age;
    Button registerButton,viewButton;
    Sign_up thisInstance = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        myDB = new DBHelper(this);

        registerButton = (Button) findViewById(R.id.bRegister);
        viewButton =(Button) findViewById(R.id.bView);
        name = (EditText) findViewById(R.id.etFullName);
        mail = (EditText) findViewById(R.id.etEmail);
        pass = (EditText) findViewById(R.id.etPassword);
        sex = (EditText) findViewById(R.id.etSex);
        weight = (EditText) findViewById(R.id.etWeight);
        height = (EditText) findViewById(R.id.etHeight);
        age = (EditText) findViewById(R.id.etAge);
        addData();
        viewAll();
        //registerButton.setOnClickListener(new View.OnClickListener() {
         //   public void onClick(View v) {
            //    startActivity(new Intent(Sign_up.this, HomePage.class));
          //  }
       // });

    }
    public void addData(){
        registerButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isInserted = myDB.insertData(
                                thisInstance,
                                name.getText().toString(),
                                mail.getText().toString(),
                                pass.getText().toString(),
                                age.getText().toString(),
                                sex.getText().toString(),
                                weight.getText().toString(),
                                height.getText().toString()
                        );
                        if(isInserted) {
                            Toast.makeText(Sign_up.this, "Registered", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(Sign_up.this, "Not Registered", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    public void viewAll(){
        viewButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Cursor res = myDB.getAllData();
                        if(res.getCount()==0) {
                            //Toast.makeText(Sign_up.this, "lol", Toast.LENGTH_SHORT).show();
                            showMessage("Error","Nothing Found");
                            //Toast.makeText(Sign_up.this, "lol", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        StringBuffer b =new StringBuffer(200);
                        while(res.moveToNext()){
                            b.append("ID"+res.getString(0)+"\n");
                            b.append("Name"+res.getString(1)+"\n");
                            b.append("Mail"+res.getString(2)+"\n");
                            b.append("Age"+res.getString(3)+"\n\n");
                        }
                        showMessage("Data",b.toString());
                        //Toast.makeText(Sign_up.this, "Here", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Toast.makeText(Sign_up.this, "Here", Toast.LENGTH_SHORT).show();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
