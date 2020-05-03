package com.slidetonew;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
        if (BaseApplication.getDarkModeStatus()) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_main);
//        //左上角导航工具
//        navController = Navigation.findNavController(findViewById(R.id.fragment));
//        NavigationUI.setupActionBarWithNavController(this, navController);
    }
//    //实现左上角导航回上一个fragment功能
//    @Override
//    public boolean onSupportNavigateUp() {
//        navController.navigateUp();
//        return super.onSupportNavigateUp();
//    }

}
