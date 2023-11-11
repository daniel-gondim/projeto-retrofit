package com.ads.atividadehttpretrofit2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ads.atividadehttpretrofit2.model.Fabricante;
import com.ads.atividadehttpretrofit2.services.ServiceFabricante;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtNome;
    EditText edtResposta;

    Button btnPost;
    Button btnGet;
    ListView listView;
    List<Fabricante> listaFabricante = new ArrayList<>();
    Context context;

    private Retrofit retrofit;
    ServiceFabricante service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNome = findViewById(R.id.edtTxtNomeFabricante);
        edtResposta = findViewById(R.id.edtResposta);
        listView = findViewById(R.id.listViewFabricante);
        btnPost = findViewById(R.id.btnInserirFabricante);
        btnPost.setOnClickListener(this);
        btnGet = findViewById(R.id.btnConsultarFabricante);
        btnGet.setOnClickListener(this);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:8082/fabricante/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ServiceFabricante.class);

        context = this;

    }

    @Override
    public void onClick(View view) {
        if (view == btnGet) {
            Call<List<Fabricante>> call = service.getFabricante();
            call.enqueue(new Callback<List<Fabricante>>() {
                @Override
                public void onResponse(Call<List<Fabricante>> call,
                                       Response<List<Fabricante>> response) {
                    if (response.isSuccessful()) {
                        listaFabricante = response.body();
                        edtResposta.setText(response.body().toString());
                        ListAdapter listAdapter = new ArrayAdapter<Fabricante>(context,
                                android.R.layout.simple_list_item_1, listaFabricante);
                        listView.setAdapter(listAdapter);
                    }

                }

                @Override
                public void onFailure(Call call, Throwable throwable) {
                    Toast.makeText(MainActivity.this, "Ocorreu um erro na requisição!",
                            Toast.LENGTH_LONG).show();
                    Log.e("Erro", throwable.getMessage());
                }
            });
        } else if (view == btnPost) {
            Fabricante fabricante = new Fabricante(edtNome.getText().toString());
            Call<Fabricante> call = service.postFabricante(fabricante);
            call.enqueue(new Callback<Fabricante>() {
                @Override
                public void onResponse(Call<Fabricante> call, Response<Fabricante> response) {
                    if (response.isSuccessful()) {
                        Fabricante fabricanteSalvo = response.body();
                        String string = "Id: " + fabricanteSalvo.getId() + " "
                                + "Nome: " + fabricanteSalvo.getName();
                        edtResposta.setText(string);
                        Toast.makeText(MainActivity.this, "Fabricante inserido com sucesso",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Erro", response.toString());
                    }
                }

                @Override
                public void onFailure(Call<Fabricante> call, Throwable throwable) {
                    Toast.makeText(MainActivity.this, "Ocorreu um erro na requisição!",
                            Toast.LENGTH_LONG).show();
                    Log.e("Erro", throwable.getMessage());

                }
            });

        }
    }
}