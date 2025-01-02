package com.example.bevasarlobeadando;

import android.content.Intent;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);

        init();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correctInputs()){
                    Toast.makeText(MainActivity.this, "Sikeres adatfelvétel!", Toast.LENGTH_SHORT).show();

                    postProduct(apiService);

                    Intent ujIntent = new Intent(MainActivity.this, ListActivity.class);

                    startActivity(ujIntent);

                    finish();
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

    private void postProduct(RetrofitApiService apiService){
        String nev = i_name.getText().toString();
        int egyseg_ar = Integer.parseInt(i_price.getText().toString());
        double mennyiseg = Double.parseDouble(i_quantity.getText().toString());
        String mertekegyseg = i_mesure.getText().toString();
        Termek product = new Termek(nev, egyseg_ar, mennyiseg, mertekegyseg);

        apiService.createProduct(product).enqueue(new Callback<Termek>() {
            @Override
            public void onResponse(Call<Termek> call, Response<Termek> response) {
                if (response.isSuccessful()) {
                    System.out.println("Product created: " + response.body());
                } else {
                    System.err.println("Failed to create product. Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Termek> call, Throwable t) {
                System.err.println("Error creating product: " + t.getMessage());
            }
        });
    }

    private void init(){
        this.i_name = findViewById(R.id.i_name);
        this.i_price = findViewById(R.id.i_price);
        this.i_quantity = findViewById(R.id.i_quantity);
        this.i_mesure = findViewById(R.id.i_mesure);
        this.btn_add = findViewById(R.id.btn_add);
    }
}