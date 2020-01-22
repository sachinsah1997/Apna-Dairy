package com.xdeveloperart.apnadairy.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.xdeveloperart.apnadairy.adapter.AreaAdapter;
import com.xdeveloperart.apnadairy.adapter.CustomerAdapter;
import com.xdeveloperart.apnadairy.adapter.ProductAdapter;
import com.xdeveloperart.apnadairy.adapter.SalesmanAdapter;
import com.xdeveloperart.apnadairy.adapter.TransactionAdapter;
import com.xdeveloperart.apnadairy.helper.AreaFirebase;
import com.xdeveloperart.apnadairy.helper.CustomerFirebase;
import com.xdeveloperart.apnadairy.helper.ProductFirebase;
import com.xdeveloperart.apnadairy.helper.SalesmanFirebase;
import com.xdeveloperart.apnadairy.helper.TransactionFirebase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecycleListViewFragment extends Fragment {

    private DatabaseReference mDatabaseRef;
    private List<CustomerAdapter> customerList  = new ArrayList<>();
    private List<SalesmanAdapter> salesmanList = new ArrayList<>();
    private List<ProductAdapter> productList = new ArrayList<>();
    private List<AreaAdapter> areaList = new ArrayList<>();
    private List<TransactionAdapter> transcationList = new ArrayList<>();
    private ListView lv;
    private CustomerFirebase customerFirebaseObj;
    private ProductFirebase productFirebaseObj;
    private AreaFirebase areaFirebaseObj;
    private SalesmanFirebase salesmanFirebaseObj;
    private ProgressDialog progressDialog;
    private Fragment fragment;
    String subtitle,database,stock;
    private ImageButton openForm;
    private boolean dataExisted;
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

        final Bundle bundle = this.getArguments();

        try {
            database= bundle.getString("database");
            stock=bundle.getString("stock");

            if (database == null){
                database = "";
            }

            if (stock != null){
                openForm.setVisibility(View.GONE);
            } else {
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
                    if(stock == null ) {
                        productFirebaseObj = new ProductFirebase(getActivity(), R.layout.structure_listview_item, productList);
                    } else if (stock.equals("visibility.gone")) {
                        productFirebaseObj = new ProductFirebase(getActivity(), R.layout.structure_listview_item, productList);
                    } else {
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

        }else if (database.equals("transactionInfo")) {

            mDatabaseRef = FirebaseDatabase.getInstance().getReference(database);
            mDatabaseRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TransactionAdapter transactionData = snapshot.getValue(TransactionAdapter.class);
                        transcationList.add(transactionData);
                    }

                    TransactionFirebase transactionFirebaseObj = new TransactionFirebase(getActivity(), R.layout.structure_listview_stock_item,transcationList);
                    lv.setAdapter(transactionFirebaseObj);
                }

                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String customerName = null;
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
                        passkey.putString("customerName", key.getText().toString());
                        passkey.putString("stock","visibility.gone");
                    }
                    fragment.setArguments(passkey);
                    fragmentMethodCall(fragment);
                }

                else if(database.equals("productInfo")){

                    try{
                       customerName = bundle.getString("customerName");
                    }catch (Exception e){
                        System.out.println("no customer pass");
                    }
                    if(customerName == null){
                        fragment = new ProductFragment();
                        passkey.putString("text", key.getText().toString());
                        fragment.setArguments(passkey);
                        fragmentMethodCall(fragment);
                    }else{
                        openForm.setVisibility(View.GONE);
                        final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        mDatabaseRef = FirebaseDatabase.getInstance().getReference("transactionInfo").child(currentDate);

                        dataExisted = findRecordExistInDatabase(mDatabaseRef,customerName);
                        if(!dataExisted) {
                            insertTransactionRecord(mDatabaseRef,currentDate,customerName,view);
                        }else {
                            boolean home= false;
                           try {
                              home = bundle.getBoolean("home");
                           }catch (Exception E){
                               Log.d("scs","home is null");
                           }
                            showDialogStockUpdate(mDatabaseRef,currentDate,key.getText().toString(),customerName,home,"add");
                        }
                    }

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


    boolean findRecordExistInDatabase(DatabaseReference mDatabaseRef, String customerName){

        mDatabaseRef.orderByChild("customerName").equalTo(customerName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {

                if (tasksSnapshot.getValue() == null) {
                    dataExisted = false;
                }else{
                    dataExisted  = true;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return dataExisted;
    }

    //method for inserting new child for transaction/date/child
    protected void insertTransactionRecord(DatabaseReference mDatabaseRef,String currentDate,String customerName, View view){
        String uploadId = mDatabaseRef.push().getKey();
        TransactionAdapter transactionAdapter = new TransactionAdapter(currentDate,customerName,0,0,0,0,0);
        mDatabaseRef.child(uploadId).setValue(transactionAdapter);
        for (int i = 0; i < lv.getCount(); i++) {
            view = lv.getChildAt(i);
            TextView text = view.findViewById(R.id.textView);
            mDatabaseRef.child(uploadId).child("product").child(text.getText().toString()).setValue("0");
        }
        Toast.makeText(getActivity(), "વર્તમાન તારીખ માટે રેકોર્ડ ઉમેર્યો", Toast.LENGTH_SHORT).show();
    }

    //method for updating product qty for transaction/date/child/product --> qty
    protected void showDialogStockUpdate(final DatabaseReference mDatabaseRef,final String currentDate, final String productName, final String customerName, final boolean home,final String operation) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);

        View view = getActivity().getLayoutInflater().inflate(R.layout.stock_update_dialog_box, null);
        dialog.setContentView(view);

        final EditText valueEditTextQty = view.findViewById(R.id.sValue);
        Button operationButton = view.findViewById(R.id.stockOperationButton);


        operationButton.setOnClickListener(new View.OnClickListener() {
             Integer total,previoustotal;
            String totalString;
            @Override
            public void onClick(View v) {

                Query pendingTasks = mDatabaseRef.orderByChild("date").equalTo(currentDate);
                pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot tasksSnapshot) {
                        for (final DataSnapshot snapshot : tasksSnapshot.getChildren()) {

                            if((snapshot.child("customerName").getValue(String.class)).equals(customerName)) {
                                final String previousQty = snapshot.child("product").child(productName).getValue(String.class);
                                final String previoustotal = snapshot.child("total").getValue(String.class);
                               final String enteredQty = valueEditTextQty.getText().toString().trim();
                               if (home == false){
                                   final Integer totalQty = Integer.parseInt(previousQty) + Integer.parseInt(enteredQty);
                                   snapshot.getRef().child(productName).setValue(Integer.toString(totalQty));
                               }else {
                                   snapshot.getRef().child(productName).setValue(previousQty+enteredQty);
                               }

                                      DatabaseReference mDatabaseRefProduct = FirebaseDatabase.getInstance().getReference("productInfo");
                                      Query pendingTasks = mDatabaseRefProduct.orderByChild("productName").equalTo(productName);
                                      pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {

                                          public void onDataChange(DataSnapshot tasksSnapshot) {
                                              for (final DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                                  String salesPrice = snapshot.child("salesPrice").getValue(String.class);
                                                  if (salesPrice != null) {
                                                      total = Integer.parseInt(enteredQty) * Integer.parseInt(salesPrice) + Integer.parseInt(previoustotal);

                                                  } else {
                                                      total = 0;
                                                  }
                                              }
                                               totalString = Integer.toString(total);
                                              snapshot.getRef().child("total").setValue(totalString);
                                          }

                                          public void onCancelled(@NonNull DatabaseError databaseError) {

                                          }
                                      });

                            }

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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
