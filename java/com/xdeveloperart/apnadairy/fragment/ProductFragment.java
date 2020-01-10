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
import com.xdeveloperart.apnadairy.adapter.ProductAdapter;
import com.xdeveloperart.apnadairy.R;

public class ProductFragment extends Fragment {

    private static final String databaseName = "productInfo"; 
    EditText productName,piecesPerCarat,costPrice,salesPrice;
    Button addProductButton,deleteProductButton;
    TextView fragmentTitle;
    private DatabaseReference mDatabaseRef;
    private String key;
    Fragment fragment;

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

        productName = layout.findViewById(R.id.pname);
        piecesPerCarat = layout.findViewById(R.id.ppc);
        costPrice = layout.findViewById(R.id.costprice);
	    salesPrice = layout.findViewById(R.id.salesprice);
        addProductButton = layout.findViewById(R.id.addProduct);
        deleteProductButton = layout.findViewById(R.id.deleteProduct);
        fragmentTitle = layout.findViewById(R.id.fragmentTitle);

        Bundle passkey = this.getArguments();
        try {
            key = passkey.getString("text");

        }catch (Exception e){
            System.out.println("null error");
        }

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(databaseName);

        if (key != null) {
            productName.setText(key);
            fragmentTitle.setText("Update Product");
            deleteProductButton.setVisibility(View.VISIBLE);
            addProductButton.setText("Click here to Update");

            Query pendingTasks = mDatabaseRef.orderByChild("productName").equalTo(key);

            pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot tasksSnapshot) {
                    for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                        ProductAdapter productAdapter = snapshot.getValue(ProductAdapter.class);
                        productName.setText(productAdapter.getProductName());
                        piecesPerCarat.setText(productAdapter.getPiecesPerCarat());
                        costPrice.setText(productAdapter.getCostPrice());
                        salesPrice.setText(productAdapter.getSalesPrice());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (key != null) {

                    Query pendingTasks = mDatabaseRef.orderByChild("productName").equalTo(key);
                    pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {
                            for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                snapshot.getRef().child("productName").setValue(productName.getText().toString());
                                snapshot.getRef().child("piecesPerCarat").setValue(piecesPerCarat.getText().toString());
                                snapshot.getRef().child("costPrice").setValue(costPrice.getText().toString());
                                snapshot.getRef().child("salesPrice").setValue(salesPrice.getText().toString());

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
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Product updated.", Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference(databaseName);
                    String uploadId = mDatabaseRef.push().getKey();
                    ProductAdapter productAdapter = new ProductAdapter(productName.getText().toString(), piecesPerCarat.getText().toString(), costPrice.getText().toString(), salesPrice.getText().toString(),"0");
                    mDatabaseRef.child(uploadId).setValue(productAdapter);
                    Toast.makeText(getActivity(), "Product added Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });


        deleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (key != null){

                    Query pendingTasks = mDatabaseRef.orderByChild("productName").equalTo(key);

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

