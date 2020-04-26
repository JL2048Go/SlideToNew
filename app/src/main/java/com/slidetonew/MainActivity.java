package com.slidetonew;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "Main";
    private NewsViewModel viewModel =  ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(NewsViewModel.class);
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        //左上角导航工具
        navController = Navigation.findNavController(findViewById(R.id.fragment));
        NavigationUI.setupActionBarWithNavController(this, navController);
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
                        if (!Objects.equals(viewModel.getType().getValue(), "BBM54PGAwangning")){
                            viewModel._type.setValue("BBM54PGAwangning");
                        }
                        break;
                    case 1:
                        viewModel._type.setValue("BA10TA81wangning");
                        Log.d(TAG, "点击之后的类型为：" + viewModel.getType().getValue());
                        break;
                    default:
                        viewModel._type.setValue("BA8E6OEOwangning");
                        Log.d(TAG, "点击之后的类型为：" + viewModel.getType().getValue());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewModel.getType().observe(this, s -> {
            Log.d(TAG, "onCreate: 类型改变为" + viewModel.getType().getValue());
            viewModel.isNewQuery = true;
            viewModel.resetType();
        });
    }
    //实现左上角导航回上一个fragment功能
    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }

}
