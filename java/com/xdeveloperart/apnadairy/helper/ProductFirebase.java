package com.xdeveloperart.apnadairy.helper;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.xdeveloperart.apnadairy.R;
import com.xdeveloperart.apnadairy.adapter.ProductAdapter;
import com.xdeveloperart.apnadairy.fragment.HomeFragment;

import java.util.List;


    public class ProductFirebase extends ArrayAdapter<ProductAdapter> {
        private FragmentActivity context;
        private int resource;
        private List<ProductAdapter> list;

        public ProductFirebase(@NonNull FragmentActivity context, @LayoutRes int resource, @NonNull List<ProductAdapter> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            list = objects;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public ProductAdapter getItem(int position) {

            return list.get(getCount() - position - 1);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            View v = inflater.inflate(resource, null);
            final TextView productName = v.findViewById(R.id.textView);
            productName.setText(list.get(position).getProductName());

            try {
                ImageView additionImage = v.findViewById(R.id.additionImage);
                ImageView subtractionImage = v.findViewById(R.id.subtractionImage);
                final TextView stock = v.findViewById(R.id.stockTextView);
                stock.setText(list.get(position).getStock());

                additionImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(productName.getText().toString(), stock.getText().toString(),"add");
                    }
                });

                subtractionImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(productName.getText().toString(), stock.getText().toString(),"sub");
                    }
                });
            } catch (Exception e) {
                System.out.println("error to fetch");
            }

            return v;
        }

        protected void showDialog(final String productName, final String previousStock, final String operation) {

            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(true);

            View view = context.getLayoutInflater().inflate(R.layout.stock_update_dialog_box, null);
            dialog.setContentView(view);

            final EditText valueEditText = view.findViewById(R.id.sValue);
            Button operationButton = view.findViewById(R.id.stockOperationButton);

            final DatabaseReference mDatabaseRef;
            String databaseName = "productInfo";
            mDatabaseRef = FirebaseDatabase.getInstance().getReference(databaseName);

            operationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(valueEditText.getText().toString());

                    int updatedStockValue =0;
                   if(operation.equals("add")) {
                       updatedStockValue = Integer.parseInt(previousStock) + value;
                   }else{
                       updatedStockValue = Integer.parseInt(previousStock) - value;
                   }
                    final String updatedstockValueString = Integer.toString(updatedStockValue);
                    Query pendingTasks = mDatabaseRef.orderByChild("productName").equalTo(productName);
                    pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {
                            for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                snapshot.getRef().child("stock").setValue(updatedstockValueString);

                                Bundle bundle = new Bundle();
                                Fragment fragment = new HomeFragment();
                                bundle.putString("database", "productInfo");
                                bundle.putString("stock", "updateStock");
                                fragment.setArguments(bundle);
                                FragmentManager fragmentManager = context.getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.container_body, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            });

            dialog.show();

        }


    }




