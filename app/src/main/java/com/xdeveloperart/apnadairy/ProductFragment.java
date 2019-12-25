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

public class ProductFragment extends Fragment {

    private static final String databaseName = "productInfo"; 
    EditText productName,piecesPerCarat,costPrice,salesPrice;
    Button addProductButton;
    private DatabaseReference mDatabaseRef;
    private String key;

    public ProductFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    
        View layout = inflater.inflate(R.layout.fragment_product, container, false);

        productName = (EditText)layout.findViewById(R.id.pname);
        piecesPerCarat = (EditText)layout.findViewById(R.id.ppc);
        costPrice = (EditText)layout.findViewById(R.id.costprice);
	salesPrice = (EditText)layout.findViewById(R.id.salesprice);
        addProductButton = (Button)layout.findViewById(R.id.addProduct);

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

	  	mDatabaseRef = FirebaseDatabase.getInstance().getReference(databaseName);
		String uploadId = mDatabaseRef.push().getKey();
                ProductAdapter productAdapter = new ProductAdapter(productName.getText().toString(),piecesPerCarat.getText().toString(),costPrice.getText().toString(),salesPrice.getText().toString());
		mDatabaseRef.child(uploadId).setValue(productAdapter);
                Toast.makeText(getActivity(), "Product added Successfully", Toast.LENGTH_SHORT).show();
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


