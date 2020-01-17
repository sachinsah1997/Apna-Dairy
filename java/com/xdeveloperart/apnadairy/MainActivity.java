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
import com.xdeveloperart.apnadairy.fragment.AreaFragment;
import com.xdeveloperart.apnadairy.fragment.HomeFragment;
import com.xdeveloperart.apnadairy.fragment.ProductFragment;
import com.xdeveloperart.apnadairy.fragment.RecycleListViewFragment;
import com.xdeveloperart.apnadairy.fragment.DevelopPhase;
import com.xdeveloperart.apnadairy.fragment.ViewProfileFragment;
import com.xdeveloperart.apnadairy.navigationdrawer.FragmentDrawer;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();
    FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

        //botom navigation
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
                fragment = new HomeFragment();
               subtitle = getString(R.string.title_home);
                // set the toolbar title
                getSupportActionBar().setSubtitle(subtitle);
                break;

           case 1:
               fragment = new RecycleListViewFragment();
               bundle.putString("database", "productInfo");
               fragment.setArguments(bundle);
                 subtitle = getString(R.string.title_product);
                getSupportActionBar().setSubtitle(subtitle);
                break;

                case 2:
                    fragment = new RecycleListViewFragment();
                    bundle.putString("database", "customerInfo");
                    fragment.setArguments(bundle);
                    subtitle = getString(R.string.title_customer);
                    getSupportActionBar().setSubtitle(subtitle);
                break;

            case 3:
                fragment = new RecycleListViewFragment();
                bundle.putString("database", "salesmanInfo");
                fragment.setArguments(bundle);
                subtitle = getString(R.string.title_salesman);
                getSupportActionBar().setSubtitle(subtitle);
                break;

            case 4:
                fragment = new RecycleListViewFragment();
                bundle.putString("database", "areaInfo");
                fragment.setArguments(bundle);
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
                    fragment = new HomeFragment();
                    loadFragment(fragment);

                    subtitle = getString(R.string.title_shop);
                    getSupportActionBar().setSubtitle(subtitle);

                    return true;
                case R.id.navigation_gifts:

                    fragment = new RecycleListViewFragment();
                    loadFragment(fragment);
                    subtitle = "સવર્તમાન રેકોર્ડ ડેટાs";
                    bundle.putString("database", "stockUpdateInfo");
                    fragment.setArguments(bundle);
                    getSupportActionBar().setSubtitle(subtitle);

                    return true;
                case R.id.navigation_cart:

                    fragment = new RecycleListViewFragment();
                    loadFragment(fragment);
                    subtitle = "સ્ટોક";
                    bundle.putString("database", "productInfo");
                    bundle.putString("stock","updateStock");
                    fragment.setArguments(bundle);
                    getSupportActionBar().setSubtitle(subtitle);

                    return true;
                case R.id.navigation_profile:

                    fragment = new ViewProfileFragment();
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

