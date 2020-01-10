package com.xdeveloperart.apnadairy.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.xdeveloperart.apnadairy.R;
import com.xdeveloperart.apnadairy.adapter.ProfileApdater;

public class ViewProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    String currentuserphone;
    private ProgressDialog progressDialog;
    protected EditText pname, paddress, pnumber;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton1, radioSexButton2, radioSexButton;
    Button updatebutton;
    DatabaseReference rootRef;

    public ViewProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_profile, container, false);


        //Show progress dialog during list image loading
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Profile...");
        progressDialog.show();

        pname = rootView.findViewById(R.id.pnamev);
        paddress = rootView.findViewById(R.id.paddressv);
        pnumber = rootView.findViewById(R.id.pnumberv);
        radioSexGroup = rootView.findViewById(R.id.radioGroup1v);

        FirebaseUser currentFirebaseUser = mAuth.getInstance().getCurrentUser();
        currentuserphone = currentFirebaseUser.getPhoneNumber();

        updatebutton = rootView.findViewById(R.id.updateprofile);

        radioSexButton1 = rootView.findViewById(R.id.radio0v);
        radioSexButton2 = rootView.findViewById(R.id.radio1v);

        rootRef = FirebaseDatabase.getInstance().getReference("profiledetail");
        Query pendingTasks = rootRef.orderByChild("pnumber").equalTo(currentuserphone);
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ProfileApdater profileAdapter = snapshot.getValue(ProfileApdater.class);
                    pnumber.setText(profileAdapter.getPnumber());
                    pname.setText(profileAdapter.getPname());
                    paddress.setText(profileAdapter.getPaddress());
                    String s = profileAdapter.getPgender();

                    if (s.equals("male")) {
                        radioSexGroup.check(R.id.radio0v);
                    } else {
                        radioSexGroup.check(R.id.radio1v);
                    }

                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Loaded Successfully", Toast.LENGTH_SHORT);
                    toast.show();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        updatebutton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        return rootView;
    }


}




