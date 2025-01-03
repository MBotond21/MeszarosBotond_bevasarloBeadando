package com.example.bevasarlobeadando;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermekActivity extends AppCompatActivity {

    private EditText m_name;
    private EditText m_price;
    private EditText m_quantity;
    private EditText m_mesure;
    private Button btn_modify;
    private Button btn_delete;
    private Button btn_back;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_termek);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);

        init();

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correctInputs()){
                    patchProduct(apiService);
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDelete(apiService, id);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(TermekActivity.this, ListActivity.class);

                startActivity(newIntent);

                finish();
            }
        });
    }

    private boolean isEmpty(EditText field) {
        return field.getText().toString().isEmpty();
    }

    private boolean isInt(EditText field) {
        try {
            Integer.parseInt(field.getText().toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isDouble(EditText field) {
        try {
            Double.parseDouble(field.getText().toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean correctInputs() {

        if (isEmpty(m_name) && isEmpty(m_price) && isEmpty(m_quantity) && isEmpty(m_mesure)) {
            Toast.makeText(TermekActivity.this, "Legalább egy mező kitöltése kötelező!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isEmpty(m_price) && !isInt(m_price)) {
            Toast.makeText(TermekActivity.this, "Az ár mindenképpen egy egész szám kell legyen!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isEmpty(m_quantity) && !isDouble(m_quantity)) {
            Toast.makeText(TermekActivity.this, "Az mennyiségnek mindenképpen egy szám kell legyen!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void init() {
        this.m_name = findViewById(R.id.m_name);
        this.m_price = findViewById(R.id.m_price);
        this.m_quantity = findViewById(R.id.m_quantity);
        this.m_mesure = findViewById(R.id.m_mesure);
        this.btn_modify = findViewById(R.id.btn_modify);
        this.btn_delete = findViewById(R.id.btn_delete);
        this.btn_back = findViewById(R.id.btn_back);
        Bundle bundle = getIntent().getExtras();
        this.id = bundle.getInt("id");
    }

    private void deleteProduct(RetrofitApiService apiService, int id) {
        apiService.deleteProduct(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TermekActivity.this, "Sikeresen törölte a terméket!", Toast.LENGTH_SHORT).show();
                    Intent newIntent = new Intent(TermekActivity.this, ListActivity.class);

                    startActivity(newIntent);

                    finish();
                } else {
                    Toast.makeText(TermekActivity.this, "Something went wrong! v1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TermekActivity.this, "Something went wrong! v2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ConfirmDelete(RetrofitApiService apiService, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Action");
        builder.setMessage("Are you sure you want to proceed?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProduct(apiService, id);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void patchProduct(RetrofitApiService apiService){

        Map<String, Object> updates = new HashMap<>();
        if (!isEmpty(m_name)) updates.put("nev", m_name.getText().toString());
        if (!isEmpty(m_price)) updates.put("egyseg_ar", Integer.parseInt(m_price.getText().toString()));
        if (!isEmpty(m_quantity)) updates.put("mennyiseg", Double.parseDouble(m_quantity.getText().toString()));
        if (!isEmpty(m_mesure)) updates.put("mertekegyseg", m_mesure.getText().toString());

        apiService.patchProduct(id, updates).enqueue(new Callback<Termek>() {
            @Override
            public void onResponse(Call<Termek> call, Response<Termek> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TermekActivity.this, "Sikeres adatmódosítás!", Toast.LENGTH_SHORT).show();
                    Intent ujIntent = new Intent(TermekActivity.this, ListActivity.class);

                    startActivity(ujIntent);

                    finish();
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
}