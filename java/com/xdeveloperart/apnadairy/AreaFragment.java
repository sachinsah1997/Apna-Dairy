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

public class AreaFragment extends Fragment {

    private static final String databaseName = "areaInfo"; 
    EditText areaName;
    Button addAreaButton;
    private DatabaseReference mDatabaseRef;
    private String key;

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

        areaName = (EditText)layout.findViewById(R.id.name);
	    addAreaButton = (Button)layout.findViewById(R.id.addArea);
       
        addAreaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef = FirebaseDatabase.getInstance().getReference(databaseName);
	        	String uploadId = mDatabaseRef.push().getKey();
                AreaAdapter areaAdapter = new AreaAdapter(areaName.getText().toString());
		mDatabaseRef.child(uploadId).setValue(areaAdapter);
                Toast.makeText(getActivity(), "Area added Successfully", Toast.LENGTH_SHORT).show();
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


