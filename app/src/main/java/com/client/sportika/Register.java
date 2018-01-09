package com.client.sportika;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.client.sportika.Interface.Registerview;
import com.client.sportika.Presenter.RegisterPresenter;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by HP on 1/1/2018.
 */

public class Register extends AppCompatActivity implements Registerview{

Button button;

    private EditText name,phone,email;

    private Context context = Register.this;

    private RegisterPresenter registerPresenter;

    private SweetAlertDialog pDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registerscreen);

        button = (Button) findViewById(R.id.log);

        name = (EditText) findViewById(R.id.businessname);

        phone = (EditText) findViewById(R.id.phoneno);

        email = (EditText) findViewById(R.id.email);

        registerPresenter = new RegisterPresenter(this);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#CD282F"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  if(name.getText().toString().equals("")){

                    Toast.makeText(context,"Enter your business name",Toast.LENGTH_SHORT).show();
                }
                else {

                    if(phone.getText().toString().equals("")){

                        Toast.makeText(context,"Enter your phone number",Toast.LENGTH_SHORT).show();
                    }

                    else {

                        if(email.getText().toString().equals("")){

                            Toast.makeText(context,"Enter your phone number",Toast.LENGTH_SHORT).show();
                        }

                        else {

                            registerPresenter.getReg(name.getText().toString(),phone.getText().toString(),email.getText().toString());

                        }


                    }



                }
*/
                Intent intent  = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    public void showRErrorMeassage() {


        Toast.makeText(context,"server error",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showRLoginSuccessMsg(int success) {

        if(success == 1){

           Toast.makeText(context,"Business register successfully",Toast.LENGTH_SHORT).show();

            Intent intent  = new Intent(Register.this,MainActivity.class);
            startActivity(intent);
            finish();

        }
        else {

            Toast.makeText(context,"Business already register",Toast.LENGTH_SHORT).show();


        }


    }

    @Override
    public void showprogress() {

        pDialog.show();
    }

    @Override
    public void hideprogress() {

        pDialog.dismiss();
    }

}
