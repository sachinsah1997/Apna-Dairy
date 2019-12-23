package com.xdeveloperart.apnadairy;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerFragment extends Fragment {

    private static final String databaseName = "customerInfo"; 
    EditText customerName,customerNumber,area,salesmanAssociated;
    Button addCustomerButton;
    private DatabaseReference mDatabaseRef;
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
	    salesmanAssociated = (EditText)layout.findViewById(R.id.sassociated);
        area = (EditText)layout.findViewById(R.id.area);
        addCustomerButton = (Button)layout.findViewById(R.id.addCustomer);

        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

	    mDatabaseRef = FirebaseDatabase.getInstance().getReference(databaseName);
		String uploadId = mDatabaseRef.push().getKey();
	    CustomerAdapter customerAdapter = new CustomerAdapter(customerName.getText().toString(),customerNumber.getText().toString(),salesmanAssociated.getText().toString(),area.getText().toString());
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


