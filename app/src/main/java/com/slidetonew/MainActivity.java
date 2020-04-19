package com.slidetonew;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    NewsViewModel viewModel =  ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(NewsViewModel.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Spinner spinner = findViewById(R.id.spinner);

        @SuppressLint("ResourceType")
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.spinnerItem, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Log.d("type", viewModel.getType().getValue());
                        break;
                    case 1:
                        viewModel.type.setValue("BA10TA81wangning");
                        Log.d("type", viewModel.getType().getValue());
                        break;
                    default:
                        viewModel.type.setValue("BA8E6OEOwangning");
                        Log.d("type", viewModel.getType().getValue());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
