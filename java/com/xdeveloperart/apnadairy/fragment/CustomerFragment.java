package com.xdeveloperart.apnadairy.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.xdeveloperart.apnadairy.R;
import com.xdeveloperart.apnadairy.adapter.CustomerAdapter;
import java.util.ArrayList;
import java.util.List;

public class CustomerFragment extends Fragment {

    private static final String databaseName = "customerInfo";
    EditText customerName, customerNumber;
    Spinner area, salesmanAssociated;
    TextView fragmentTitle;
    Button addCustomerButton, deleteCustomerButton;
    private DatabaseReference mDatabaseRef, mDatabaseRefArea, mDatabaseRefSalesman;
    String key;
    Fragment fragment;

    public CustomerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_customer, container, false);

        customerName = layout.findViewById(R.id.cname);
        customerNumber = layout.findViewById(R.id.cnumber);
        salesmanAssociated = layout.findViewById(R.id.sassociated);
        area = layout.findViewById(R.id.area);
        deleteCustomerButton = layout.findViewById(R.id.deleteCustomer);
        fragmentTitle = layout.findViewById(R.id.fragmentTitle);

        addCustomerButton = layout.findViewById(R.id.addCustomer);

        mDatabaseRefArea = FirebaseDatabase.getInstance().getReference("areaInfo");

        mDatabaseRefArea.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> areaList = new ArrayList<>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("name").getValue(String.class);
                    areaList.add(areaName);
                }
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areaList);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                area.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseRefSalesman = FirebaseDatabase.getInstance().getReference("salesmanInfo");
        mDatabaseRefSalesman.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> salesmanList = new ArrayList<>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String salesmanName = areaSnapshot.child("salesmanName").getValue(String.class);
                    salesmanList.add(salesmanName);
                }

                ArrayAdapter<String> salesmanAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, salesmanList);
                salesmanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                salesmanAssociated.setAdapter(salesmanAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Bundle passkey = this.getArguments();
        try {
            key = passkey.getString("text");
        }catch (Exception e){
            System.out.println("null error");
        }
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(databaseName);

        if (key != null) {
            customerName.setText(key);
            fragmentTitle.setText("Update Customer");
            deleteCustomerButton.setVisibility(View.VISIBLE);
            addCustomerButton.setText("Click here to Update");

            Query pendingTasks = mDatabaseRef.orderByChild("customerName").equalTo(key);

            pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot tasksSnapshot) {
                    for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                        CustomerAdapter customerAdapter = snapshot.getValue(CustomerAdapter.class);
                        customerName.setText(customerAdapter.getCustomerName());
                        customerNumber.setText(customerAdapter.getCustomerNumber());

                        for (int i = 0; i < salesmanAssociated.getCount(); i++) {
                            if (salesmanAssociated.getItemAtPosition(i).toString().equalsIgnoreCase(customerAdapter.getSalesmanAssociated())) {
                                salesmanAssociated.setSelection(i);
                            }
                        }
                        for (int i = 0; i < area.getCount(); i++) {
                            if (area.getItemAtPosition(i).toString().equalsIgnoreCase(customerAdapter.getArea())) {
                                area.setSelection(i);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }

            addCustomerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (key != null) {

                        Query pendingTasks = mDatabaseRef.orderByChild("customerName").equalTo(key);

                        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot tasksSnapshot) {
                                for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                    snapshot.getRef().child("customerName").setValue(customerName.getText().toString());
                                    snapshot.getRef().child("customerNumber").setValue(customerNumber.getText().toString());
                                    snapshot.getRef().child("salesmanAssociated").setValue(salesmanAssociated.getSelectedItem().toString());
                                    snapshot.getRef().child("area").setValue(area.getSelectedItem().toString());
                                    fragment = new HomeFragment();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.container_body, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Customers updated.", Toast.LENGTH_SHORT);
                        toast.show();

                    } else {
                        mDatabaseRef = FirebaseDatabase.getInstance().getReference(databaseName);
                        String uploadId = mDatabaseRef.push().getKey();
                        CustomerAdapter customerAdapter = new CustomerAdapter(customerName.getText().toString(), customerNumber.getText().toString(), salesmanAssociated.getSelectedItem().toString(), area.getSelectedItem().toString());
                        mDatabaseRef.child(uploadId).setValue(customerAdapter);
                        Toast.makeText(getActivity(), "Customer added Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        deleteCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (key != null){

                    Query pendingTasks = mDatabaseRef.orderByChild("customerName").equalTo(key);

                    pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {
                            for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                                snapshot.getRef().removeValue();
                                fragment=new HomeFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.container_body, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }

                    });
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),"Delete Successfully.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
            return layout;
        }

        @Override
        public void onAttach (Activity activity){
            super.onAttach(activity);
        }

        @Override
        public void onDetach () {
            super.onDetach();
        }
    }

