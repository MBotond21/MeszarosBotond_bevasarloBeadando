package com.example.bevasarlobeadando;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText i_name;
    private EditText i_price;
    private EditText i_quantity;
    private EditText i_mesure;
    private Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correctInputs()){
                    Toast.makeText(MainActivity.this, "Ügyi vagy!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isEmpty(EditText field){
        return field.getText().toString().isEmpty();
    }

    private boolean isInt(EditText field){
        try{
            Integer.parseInt(field.getText().toString());
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private boolean isDouble(EditText field){
        try{
            Double.parseDouble(field.getText().toString());
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private boolean correctInputs(){

        if(isEmpty(i_name) || isEmpty(i_price) || isEmpty(i_quantity) || isEmpty(i_mesure)){
            Toast.makeText(MainActivity.this, "Minden mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isInt(i_price)) {
            Toast.makeText(MainActivity.this, "Az ár mindenképpen egy egész szám kell legyen!", Toast.LENGTH_SHORT).show();
            return false;
        }  else if (!isDouble(i_quantity)) {
            Toast.makeText(MainActivity.this, "Az mennyiségnek mindenképpen egy szám kell legyen!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void init(){
        this.i_name = findViewById(R.id.i_name);
        this.i_price = findViewById(R.id.i_price);
        this.i_quantity = findViewById(R.id.i_quantity);
        this.i_mesure = findViewById(R.id.i_mesure);
        this.btn_add = findViewById(R.id.btn_add);
    }
}