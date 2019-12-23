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

public class SalesmanFragment extends Fragment {

    private static final String databaseName = "salesmanInfo"; 
    EditText salesmanName,salesmanNumber;
    Button addSalesmanButton;
    private DatabaseReference mDatabaseRef;
    private String key;

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

        salesmanName = (EditText)layout.findViewById(R.id.sname);
        salesmanNumber = (EditText)layout.findViewById(R.id.snumber);
        addSalesmanButton = (Button)layout.findViewById(R.id.addSalesman);

        addSalesmanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

	  mDatabaseRef = FirebaseDatabase.getInstance().getReference(databaseName);
		String uploadId = mDatabaseRef.push().getKey();
                SalesmanAdapter salesmanAdapter = new SalesmanAdapter(salesmanName.getText().toString(),salesmanNumber.getText().toString());
		mDatabaseRef.child(uploadId).setValue(salesmanAdapter);
                Toast.makeText(getActivity(), "Salesman added Successfully", Toast.LENGTH_SHORT).show();
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


