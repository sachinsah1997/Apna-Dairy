package com.xdeveloperart.apnadairy.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.xdeveloperart.apnadairy.adapter.SalesmanAdapter;

public class SalesmanFragment extends Fragment {

    private static final String databaseName = "salesmanInfo"; 
    EditText salesmanName,salesmanNumber;
    TextView fragmentTitle;
    Button addSalesmanButton,deleteSalesmanButton;
    private DatabaseReference mDatabaseRef;
    private String key;
    Fragment fragment;

    public SalesmanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_salesman, container, false);

        salesmanName = layout.findViewById(R.id.sname);
        salesmanNumber = layout.findViewById(R.id.snumber);
        addSalesmanButton = layout.findViewById(R.id.addSalesman);
        deleteSalesmanButton = layout.findViewById(R.id.deleteSalesman);
        fragmentTitle = layout.findViewById(R.id.fragmentTitle);

        Bundle passkey = this.getArguments();
        try {
            key = passkey.getString("text");
        }catch (Exception e){
            System.out.println("null error");
        }

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(databaseName);

        if (key != null) {
            salesmanName.setText(key);
            fragmentTitle.setText("Update Salesman");
            deleteSalesmanButton.setVisibility(View.VISIBLE);
            addSalesmanButton.setText("Click here to Update");

            Query pendingTasks = mDatabaseRef.orderByChild("salesmanName").equalTo(key);

            pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot tasksSnapshot) {
                    for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                        SalesmanAdapter salesmanAdapter = snapshot.getValue(SalesmanAdapter.class);
                        salesmanName.setText(salesmanAdapter.getSalesmanName());
                        salesmanNumber.setText(salesmanAdapter.getSalesmanNumber());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });

        }

        addSalesmanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (key != null) {

                    Query pendingTasks = mDatabaseRef.orderByChild("salesmanName").equalTo(key);

                    pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {
                            for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                snapshot.getRef().child("salesmanName").setValue(salesmanName.getText().toString());
                                snapshot.getRef().child("salesmanNumber").setValue(salesmanNumber.getText().toString());
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
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Salesman updated.", Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    String uploadId = mDatabaseRef.push().getKey();
                    SalesmanAdapter salesmanAdapter = new SalesmanAdapter(salesmanName.getText().toString(), salesmanNumber.getText().toString());
                    mDatabaseRef.child(uploadId).setValue(salesmanAdapter);
                    Toast.makeText(getActivity(), "Salesman added Successfully", Toast.LENGTH_SHORT).show();
                }
            }

        });


        deleteSalesmanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (key != null){

                    Query pendingTasks = mDatabaseRef.orderByChild("salesmanName").equalTo(key);

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}


