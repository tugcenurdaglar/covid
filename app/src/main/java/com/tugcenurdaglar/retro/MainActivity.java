package com.tugcenurdaglar.retro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.tugcenurdaglar.retro.adapter.CovidAdapter;
import com.tugcenurdaglar.retro.models.CountryModel;
import com.tugcenurdaglar.retro.retrofit.ApiUtils;
import com.tugcenurdaglar.retro.retrofit.CovidInterface;
import com.tugcenurdaglar.retro.models.CovidModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    private Spinner spinner;

    private ArrayAdapter<String> veriAdaptoru;

    private RecyclerView rv;

    private ArrayList<CovidModel> covidModelArrayList;

    private List<CountryModel> countryModelArrayList;

    private CovidAdapter adapter;

    CovidInterface covidInterFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Covid-19 Vaka Sayısı");
        setSupportActionBar(toolbar);

        spinner = findViewById(R.id.spinner);


        loadCountryData();

        //loadData("turkey");

        covidInterFace = ApiUtils.getCovidInterface();
        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem item = menu.findItem(R.id.action_Search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }


    List<CovidModel> covidModelList;

    public void loadData(String slug) {


        ApiUtils.getCovidInterface().callList(slug).enqueue(new Callback<List<CovidModel>>() {
            @Override
            public void onResponse(Call<List<CovidModel>> call, Response<List<CovidModel>> response) {
                if (response.body() != null) {
                    covidModelList = response.body();

                    for (int i = 0; i < covidModelList.size(); i++) {
                        CovidModel covidModel = covidModelList.get(i);

                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
                        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        Date date = null;
                        try {
                            if (covidModel.getDate() != null)
                                date = inputFormat.parse(covidModel.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String formattedDate = outputFormat.format(date);
                        covidModel.setDate(formattedDate);
                    }
                    adapter = new CovidAdapter(MainActivity.this, covidModelList, covidInterFace);
                    rv.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "Servis bakımda", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<CovidModel>> call, Throwable t) {

            }
        });


    }

    List<CountryModel> countryModelList;

    public void loadCountryData() {
        ApiUtils.getCovidInterface().getCountries().enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                countryModelArrayList = response.body();
                List<String> countryList = new ArrayList<>();
                for (int i = 0; i < response.body().size(); i++) {

                    countryList.add(response.body().get(i).getCountry());

                }
                Collections.sort(countryList, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });
                Collections.sort(countryModelArrayList, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel o1, CountryModel o2) {
                        String s1 = o1.getCountry();
                        String s2 = o2.getCountry();
                        return s1.compareToIgnoreCase(s2);
                    }
                });
                veriAdaptoru = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, countryList);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        loadData(countryModelArrayList.get(position).getSlug());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinner.setAdapter(veriAdaptoru);
            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {

            }
        });


    }

    public void filter(String text) {
        List<CovidModel> temp = new ArrayList();
        for (CovidModel d : covidModelList) {
            if (d.getDate().toLowerCase().contains(text.toLowerCase())) {
                temp.add(d);
            }
        }
        adapter.updateList(temp);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filter(query);
        Log.e("gönderilen arama", query);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText);
        return false;

    }


}