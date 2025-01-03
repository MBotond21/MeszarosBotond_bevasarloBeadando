package com.example.bevasarlobeadando;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private List<Termek> products;
    private ListView lw_products;
    private Button btn_back;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);
        loadProducts(apiService);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(ListActivity.this, MainActivity.class);

                startActivity(newIntent);

                finish();
            }
        });

        lw_products.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newIntent = new Intent(ListActivity.this, TermekActivity.class);
                newIntent.putExtra("id", products.get(position).getId());
                startActivity(newIntent);

                finish();
                return false;
            }
        });

    }

    private void init(){
        this.products = new ArrayList<>();
        this.lw_products = findViewById(R.id.lw_products);
        this.btn_back = findViewById(R.id.btn_back);
        this.customAdapter = new CustomAdapter(products, this);
        lw_products.setAdapter(customAdapter);
    }

    private void loadProducts(RetrofitApiService apiService){
        apiService.getProducts().enqueue(new Callback<List<Termek>>() {
            @Override
            public void onResponse(Call<List<Termek>> call, Response<List<Termek>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Termek> filteredProducts = new ArrayList<>();

                    for (Termek termek : response.body()) {
                        Termek filteredTermek = new Termek();
                        filteredTermek.setId(termek.getId());
                        filteredTermek.setNev(termek.getNev());
                        filteredTermek.setEgyseg_ar(termek.getEgyseg_ar());
                        filteredTermek.setMennyiseg(termek.getMennyiseg());
                        filteredTermek.setMertekegyseg(termek.getMertekegyseg());
                        filteredTermek.setBrutto_ar();
                        filteredProducts.add(filteredTermek);
                    }

                    products.clear();
                    products.addAll(filteredProducts);
                    customAdapter.notifyDataSetChanged();
                    System.out.println("ListActivity loaded with products");
                } else {
                    Toast.makeText(ListActivity.this, "Ez most nem jött össze kicsi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Termek>> call, Throwable t) {
                Toast.makeText(ListActivity.this, "Ez most nem jött össze kicsi!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}