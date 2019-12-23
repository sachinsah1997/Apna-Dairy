package com.xdeveloperart.apnadairy;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();
    FirebaseAuth mAuth;
    private Toolbar mToolbar;

    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

//        //botom navigation
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
                    mAuth.signOut();
                    startActivity(new Intent(getApplicationContext(),AuthenticationActivity.class));
                    finish();
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        String subtitle;

        // set the toolbar title
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new CustomerFragment();
               subtitle = getString(R.string.title_home);
                // set the toolbar title
                getSupportActionBar().setSubtitle(subtitle);
                break;

           case 1:
                fragment = new ProductFragment();
                 subtitle = getString(R.string.title_product);
                getSupportActionBar().setSubtitle(subtitle);
                break;

                case 2:
                    fragment = new CustomerFragment();
                    subtitle = getString(R.string.title_customer);
                    getSupportActionBar().setSubtitle(subtitle);
                break;

            case 3:
                fragment = new SalesmanFragment();
                subtitle = getString(R.string.title_salesman);
                getSupportActionBar().setSubtitle(subtitle);
                break;

            case 4:
                fragment = new AreaFragment();
                subtitle= getString(R.string.title_area);
                getSupportActionBar().setSubtitle(subtitle);

            default:
                break;
        }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String subtitle;

                Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    fragment = new DevelopPhase();
                    loadFragment(fragment);

                    subtitle = getString(R.string.title_shop);
                    getSupportActionBar().setSubtitle(subtitle);

                    return true;
                case R.id.navigation_gifts:

                    fragment = new DevelopPhase();
                    loadFragment(fragment);
                    subtitle = getString(R.string.title_gifts);
                    getSupportActionBar().setSubtitle(subtitle);

                    return true;
                case R.id.navigation_cart:

                    fragment = new DevelopPhase();
                    loadFragment(fragment);
                    subtitle = getString(R.string.title_cart);
                    getSupportActionBar().setSubtitle(subtitle);

                    return true;
                case R.id.navigation_profile:

                    fragment = new AreaFragment();
                    loadFragment(fragment);
                    subtitle = getString(R.string.title_profile);
                    getSupportActionBar().setSubtitle(subtitle);

                    return true;
            }
                return true;
            }};

           private void loadFragment (Fragment fragment){
            // load fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_body, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

