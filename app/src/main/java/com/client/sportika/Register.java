package com.client.sportika;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by HP on 1/1/2018.
 */

public class Register extends AppCompatActivity {

Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registerscreen);

        button = (Button) findViewById(R.id.log);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
