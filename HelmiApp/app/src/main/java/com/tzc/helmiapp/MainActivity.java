package com.tzc.helmiapp;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            switchToInformationFragment();
        }

    }

    public void switchPage(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("username", getIntent().getStringExtra("username"));

        InformationFragment informationFragment = new InformationFragment();
        NavigationFragment navigationFragment = new NavigationFragment();
        SettingsFragment settingsFragment = new SettingsFragment();
        IdentificationFragment identificationFragment = new IdentificationFragment();

        informationFragment.setArguments(bundle);

        int id = view.getId();

        if (id == R.id.btn_information) {
            fragmentTransaction.replace(R.id.pageContainer, informationFragment);
        } else if (id == R.id.btn_navigation) {
            fragmentTransaction.replace(R.id.pageContainer, navigationFragment);
        } else if (id == R.id.btn_settings) {
            fragmentTransaction.replace(R.id.pageContainer, settingsFragment);
        } else {
            fragmentTransaction.replace(R.id.pageContainer, identificationFragment);
        }

        fragmentTransaction.commit();
    }

    private void switchToInformationFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("username", getIntent().getStringExtra("username"));

        InformationFragment informationFragment = new InformationFragment();
        informationFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.pageContainer, informationFragment);
        fragmentTransaction.commit();
    }

    public void updateInformation(View view) {

    }
}