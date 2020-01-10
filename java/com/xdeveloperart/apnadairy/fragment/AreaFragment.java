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
import com.xdeveloperart.apnadairy.adapter.AreaAdapter;
import com.xdeveloperart.apnadairy.R;

public class AreaFragment extends Fragment {

    private static final String databaseName = "areaInfo"; 
    EditText areaName;
    Button addAreaButton,deleteAreaButton;
    TextView fragmentTitle;
    private DatabaseReference mDatabaseRef;
    private String key;
    private  Fragment fragment;


    public AreaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    
        View layout = inflater.inflate(R.layout.fragment_area, container, false);

        areaName = layout.findViewById(R.id.name);
	    addAreaButton = layout.findViewById(R.id.addArea);
        deleteAreaButton = layout.findViewById(R.id.deleteArea);
        fragmentTitle = layout.findViewById(R.id.fragmentTitle);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(databaseName);

        Bundle passkey = this.getArguments();
        try {
            key = passkey.getString("text");
        }catch (Exception e){
            System.out.println("null error");
        }
        if (key != null) {
            areaName.setText(key);
            fragmentTitle.setText("Update Area");
            deleteAreaButton.setVisibility(View.VISIBLE);
        }

	    addAreaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (key != null){

                    Query pendingTasks = mDatabaseRef.orderByChild("name").equalTo(key);

                    pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {
                            for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                                snapshot.getRef().child("name").setValue(areaName.getText().toString());
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
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),"Area updated.", Toast.LENGTH_SHORT);
                    toast.show();

                } else {
                    String uploadId = mDatabaseRef.push().getKey();
                    AreaAdapter areaAdapter = new AreaAdapter(areaName.getText().toString());
                    mDatabaseRef.child(uploadId).setValue(areaAdapter);
                    Toast.makeText(getActivity(), "Area added Successfully", Toast.LENGTH_SHORT).show();
                }
                }
        });

        deleteAreaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (key != null){

                    Query pendingTasks = mDatabaseRef.orderByChild("name").equalTo(key);

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


