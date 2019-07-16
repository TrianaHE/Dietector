package com.the.dietector.dietector;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    FragmentManager ftBottomNavigation = getSupportFragmentManager();
    Fragment fragment = VitaminFragment.newInstance();
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_vitamin:
                    fragment = VitaminFragment.newInstance();
                    break;
                case R.id.navigation_consult:
                    fragment = ConsultFragment.newInstance();
                    break;
                case R.id.navigation_check:
                    fragment = CheckFragment.newInstance();
                    break;
                case R.id.navigation_about:
                    fragment = AboutFragment.newInstance();
                    break;
            }

            if (ftBottomNavigation.getBackStackEntryCount() == 0) {
                ftBottomNavigation.beginTransaction().replace(R.id.content, fragment)
                        .addToBackStack(fragment.getTag())
                        .commit();
            } else {
                ftBottomNavigation.beginTransaction().replace(R.id.content, fragment)
                        .commit();
            }

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ftBottomNavigation.beginTransaction().replace(R.id.content, fragment)
                .commit();
    }

}
