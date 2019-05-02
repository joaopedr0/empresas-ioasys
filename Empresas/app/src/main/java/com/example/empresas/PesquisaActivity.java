package com.example.empresas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PesquisaActivity extends AppCompatActivity implements Filterable {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Empresa> listaEmpresas;
    private List<Empresa> listaEmpresasCompleta;
    private Retrofit retrofit;
    private EmpresaApi empresaApi;
    private String access_token;
    private String client;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_arrow_back));  // back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://empresas.ioasys.com.br/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        empresaApi = retrofit.create(EmpresaApi.class);

        listaEmpresas = new ArrayList<>();
        listaEmpresasCompleta = new ArrayList<>();
        recuperarEmpresas();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(listaEmpresas, PesquisaActivity.this);
        recyclerView.setAdapter(adapter);

        EditText pesquisa = findViewById(R.id.editText_pesquisa);
        pesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                int pos = viewHolder.getAdapterPosition();
                Empresa empresa = listaEmpresas.get(pos);
                Intent intent = new Intent(PesquisaActivity.this, EmpresaActivity.class);
                intent.putExtra("name", empresa.getName());
                intent.putExtra("photo", empresa.getPhoto());
                intent.putExtra("description", empresa.getDescription());
                startActivity(intent);
            }
        });

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Empresa> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listaEmpresasCompleta);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Empresa item : listaEmpresasCompleta) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    } else if (item.getCity().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    } else if (item.getCountry().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    } else if (item.getTipoEmpresa().getTipoEmpresaString().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results.values != null) {
                listaEmpresas.clear();
                listaEmpresas.addAll((List) results.values);
                adapter.notifyDataSetChanged();
            }
        }
    };

    private void recuperarEmpresas(){
        Intent intent = getIntent();
        access_token = intent.getStringExtra("access-token");
        client = intent.getStringExtra("client");
        uid = intent.getStringExtra("uid");
        Call<JsonObject> call = empresaApi.pesquisaEmpresas(access_token, client, uid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                JsonObject jsonObject = response.body();
                JsonArray listaEmpresas = jsonObject.getAsJsonArray("enterprises");
                Gson gson = new Gson();
                String jsonOutput = listaEmpresas.toString();
                Type listType = new TypeToken<List<Empresa>>(){}.getType();
                List<Empresa> empresas = gson.fromJson(jsonOutput, listType);
                for (int i = 0; i < listaEmpresas.size(); i++) {
                    JsonObject object = listaEmpresas.get(i).getAsJsonObject();
                    int id = Integer.valueOf(object.get("enterprise_type").getAsJsonObject().get("id").toString());
                    String tipoEmpresa = object.get("enterprise_type").getAsJsonObject().get("enterprise_type_name").toString();
                    empresas.get(i).setTipoEmpresa(new TipoEmpresa(id, tipoEmpresa));
                }
                listaEmpresasCompleta.addAll(empresas);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
}
