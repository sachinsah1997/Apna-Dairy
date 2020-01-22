package com.xdeveloperart.apnadairy.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.xdeveloperart.apnadairy.R;

public class HomeFragment extends Fragment {

    Fragment fragment;
    ImageView customerImageView,salesmaImageView,areaImageView,productImageView,updateImageView,updateHomeImageView;
    Bundle bundle = new Bundle();
    String subtitle;

        public HomeFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);

            customerImageView = rootView.findViewById(R.id.customerImageView);
            salesmaImageView = rootView.findViewById(R.id.salesmanImageView);
            productImageView=  rootView.findViewById(R.id.productImageView);
            areaImageView=  rootView.findViewById(R.id.areaImageView);
            updateImageView = rootView.findViewById(R.id.updateImageView);
            updateHomeImageView = rootView.findViewById(R.id.updateHomeImageView);

            customerImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    fragment=new RecycleListViewFragment();
                    bundle.putString("database", "customerInfo");
                    bundle.putString("stock","updateStock");
                    fragment.setArguments(bundle);
                    subtitle = getString(R.string.title_customer);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(subtitle);
                    fragmentMethodCall(fragment);
                }
            });

            salesmaImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    fragment=new RecycleListViewFragment();
                    bundle.putString("database", "salesmanInfo");
                    fragment.setArguments(bundle);
                    subtitle = getString(R.string.title_salesman);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(subtitle);
                    fragmentMethodCall(fragment);
                }
            });

            productImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    fragment=new RecycleListViewFragment();
                    bundle.putString("database", "productInfo");
                    fragment.setArguments(bundle);
                    subtitle = getString(R.string.title_product);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(subtitle);
                    fragmentMethodCall(fragment);
                }
            });

            areaImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    fragment=new RecycleListViewFragment();
                    bundle.putString("database", "areaInfo");
                    fragment.setArguments(bundle);
                    subtitle = getString(R.string.title_area);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(subtitle);
                    fragmentMethodCall(fragment);
                }
            });

            updateImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    fragment=new RecycleListViewFragment();
                    bundle.putString("database", "customerInfo");
                    bundle.putString("stock","stockUpdate");
                    fragment.setArguments(bundle);
                    subtitle = getString(R.string.update_record);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(subtitle);
                    fragmentMethodCall(fragment);
                }
            });

            updateHomeImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
//                    fragment=new RecycleListViewFragment();
//                    bundle.putString("database", "customerInfo");
//                    bundle.putString("stock","stockUpdate");
//                    bundle.putString("boolean","sdf");
//                    bundle.putBoolean("home",true);
//                    fragment.setArguments(bundle);
//                    subtitle = getString(R.string.update_record);
//                    ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(subtitle);
//                    fragmentMethodCall(fragment);
                }
            });

               return rootView;
        }

        void fragmentMethodCall(Fragment fragment){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }
}