package com.xdeveloperart.apnadairy;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerFragment extends Fragment {

    private static final String databaseName = "customerInfo"; 
    EditText customerName,customerNumber;
    Spinner area,salesmanAssociated;
    Button addCustomerButton;
    private DatabaseReference mDatabaseRef,mDatabaseRefArea,mDatabaseRefSalesman;
    private String key;

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

        customerName = (EditText)layout.findViewById(R.id.cname);
        customerNumber = (EditText)layout.findViewById(R.id.cnumber);
	    salesmanAssociated = (Spinner)layout.findViewById(R.id.sassociated);
        area = (Spinner) layout.findViewById(R.id.area);

        addCustomerButton = (Button)layout.findViewById(R.id.addCustomer);

        mDatabaseRefArea = FirebaseDatabase.getInstance().getReference("areaInfo");
        mDatabaseRefArea.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> areaList = new ArrayList<>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("name").getValue(String.class);
                    areaList.add(areaName);
                }
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, areaList);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                area.setAdapter(areasAdapter);
            }      @Override

            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabaseRefSalesman = FirebaseDatabase.getInstance().getReference("salesmanInfo");
        mDatabaseRefSalesman.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Is better to use a List, because you don't know the size
                    // of the iterator returned by dataSnapshot.getChildren() to
                    // initialize the array
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


        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

	    mDatabaseRef = FirebaseDatabase.getInstance().getReference(databaseName);
		String uploadId = mDatabaseRef.push().getKey();
	    CustomerAdapter customerAdapter = new CustomerAdapter(customerName.getText().toString(),customerNumber.getText().toString(),salesmanAssociated.toString(),area.toString());
		mDatabaseRef.child(uploadId).setValue(customerAdapter);
		Toast.makeText(getActivity(), "Customer added Successfully", Toast.LENGTH_SHORT).show();
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


