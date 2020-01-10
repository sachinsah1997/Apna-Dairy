package com.xdeveloperart.apnadairy.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xdeveloperart.apnadairy.R;
import com.xdeveloperart.apnadairy.adapter.AreaAdapter;
import com.xdeveloperart.apnadairy.adapter.CustomerAdapter;
import com.xdeveloperart.apnadairy.adapter.ProductAdapter;
import com.xdeveloperart.apnadairy.adapter.SalesmanAdapter;
import com.xdeveloperart.apnadairy.helper.AreaFirebase;
import com.xdeveloperart.apnadairy.helper.CustomerFirebase;
import com.xdeveloperart.apnadairy.helper.ProductFirebase;
import com.xdeveloperart.apnadairy.helper.SalesmanFirebase;
import java.util.ArrayList;
import java.util.List;

public class RecycleListViewFragment extends Fragment {

    private DatabaseReference mDatabaseRef;
    private List<CustomerAdapter> customerList  = new ArrayList<>();
    private List<SalesmanAdapter> salesmanList = new ArrayList<>();
    private List<ProductAdapter> productList = new ArrayList<>();
    private List<AreaAdapter> areaList = new ArrayList<>();
    private ListView lv;
    private CustomerFirebase customerFirebaseObj;
    private ProductFirebase productFirebaseObj;
    private AreaFirebase areaFirebaseObj;
    private SalesmanFirebase salesmanFirebaseObj;
    private ProgressDialog progressDialog;
    private Fragment fragment;
    String subtitle,database,stock;
    private ImageButton openForm;
    Bundle passkey = new Bundle();

    public RecycleListViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        {
            super.onCreate(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_listview, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(subtitle);

        lv = rootView.findViewById(R.id.listView);
        openForm = rootView.findViewById(R.id.openForm);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("ધૈર્ય રાખો.....");
        progressDialog.show();

        Bundle bundle = this.getArguments();

        try {
            database= bundle.getString("database");
            stock=bundle.getString("stock");

            if (stock != null){
                openForm.setVisibility(View.GONE);
            }
            else{
                openForm.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            System.out.println("not update");
        }

        if(database.equals("customerInfo")) {

            mDatabaseRef = FirebaseDatabase.getInstance().getReference(database);
            mDatabaseRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        CustomerAdapter customerData = snapshot.getValue(CustomerAdapter.class);
                        customerList.add(customerData);
                    }
                    customerFirebaseObj = new CustomerFirebase(getActivity(),R.layout.structure_listview_item,customerList);
                    lv.setAdapter(customerFirebaseObj);
                }

                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
        } else if(database.equals("productInfo")){

            mDatabaseRef = FirebaseDatabase.getInstance().getReference(database);
            mDatabaseRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ProductAdapter productData = snapshot.getValue(ProductAdapter.class);
                        productList.add(productData);
                    }
                    if(stock == null || stock == "stockUpdate") {
                        productFirebaseObj = new ProductFirebase(getActivity(), R.layout.structure_listview_item, productList);
                    } else{
                        productFirebaseObj = new ProductFirebase(getActivity(), R.layout.structure_listview_stock_update, productList);
                    }
                    lv.setAdapter(productFirebaseObj);
                }

                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });

        } else if (database.equals("areaInfo")){

            mDatabaseRef = FirebaseDatabase.getInstance().getReference(database);
            mDatabaseRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AreaAdapter areaData = snapshot.getValue(AreaAdapter.class);
                        areaList.add(areaData);
                    }
                    areaFirebaseObj = new AreaFirebase(getActivity(), R.layout.structure_listview_item, areaList);
                    lv.setAdapter(areaFirebaseObj);
                }

                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });

        } else if (database.equals("salesmanInfo")) {

            mDatabaseRef = FirebaseDatabase.getInstance().getReference(database);
            mDatabaseRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        SalesmanAdapter salesmanData = snapshot.getValue(SalesmanAdapter.class);
                        salesmanList.add(salesmanData);
                    }
                    salesmanFirebaseObj = new SalesmanFirebase(getActivity(), R.layout.structure_listview_item, salesmanList);
                    lv.setAdapter(salesmanFirebaseObj);
                }

                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final TextView key = view.findViewById(R.id.textView);
                if(database.equals("customerInfo")) {

                    if (stock == null){
                        fragment = new CustomerFragment();
                        passkey.putString("text", key.getText().toString());
                    }else{
                        fragment = new RecycleListViewFragment();
                        passkey.putString("database", "productInfo");
                        passkey.putString("stock","stockUpdate");
                    }
                    fragment.setArguments(passkey);
                    fragmentMethodCall(fragment);
                }
                else if(database.equals("productInfo")){
                    fragment = new ProductFragment();
                    passkey.putString("text", key.getText().toString());
                    fragment.setArguments(passkey);
                    fragmentMethodCall(fragment);

                } else if (database.equals("areaInfo")){
                    fragment = new AreaFragment();
                    String keyValue = key.getText().toString();
                    passkey.putString("text", keyValue);
                    fragment.setArguments(passkey);
                    fragmentMethodCall(fragment);
                } else if (database.equals("salesmanInfo")){
                   fragment = new SalesmanFragment();
                    String keyValue = key.getText().toString();
                    passkey.putString("text", keyValue);
                    fragment.setArguments(passkey);
                   fragmentMethodCall(fragment);
                }
            }
        });

        openForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (database.equals("customerInfo")) {
                    fragment = new CustomerFragment();
                    fragmentMethodCall(fragment);
                } else if (database.equals("productInfo")) {
                    fragment = new ProductFragment();
                    fragmentMethodCall(fragment);
                } else if (database.equals("areaInfo")) {
                    fragment = new AreaFragment();
                    fragmentMethodCall(fragment);
                } else if (database.equals("salesmanInfo")) {
                    fragment = new SalesmanFragment();
                    fragmentMethodCall(fragment);
                }
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
