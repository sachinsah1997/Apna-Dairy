package com.xdeveloperart.apnadairy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shuhart.stepview.StepView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.xdeveloperart.apnadairy.adapter.ProfileApdater;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AuthenticationActivity extends AppCompatActivity {

    private int currentStep = 0;
    LinearLayout layout1, layout2, layout3;
    RelativeLayout layout4;
    StepView stepView;
    AlertDialog dialog_verifying, profile_dialog;
    private static String uniqueIdentifier = null;
    private static final String UNIQUE_ID = "UNIQUE_ID";
    private static final long ONE_HOUR_MILLI = 60 * 60 * 1000;
    private static final String TAG = "FirebasePhoneNumAuth";
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String phoneNumber;
    private Button sendCodeButton;
    private Button verifyCodeButton;
    private Button signOutButton;
    private Button button3;
    private Button buttonlate;
    Bitmap bm;
    private EditText phoneNum;
    private PinView verifyCodeET;
    private TextView phonenumberText;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    public String downloadUrlString;
    public static final String FB_STORAGE_PATH = "profileimage/";
    public static final String FB_DATABASE_PATH = "profiledetail";
    public static final int REQUEST_CODE = 1;
    private Uri imgUri, cropUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ImageView imageView;
    private EditText pname, paddress, pnumber;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(AuthenticationActivity.this, MainActivity.class));
            finish();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);
        mStorageRef = FirebaseStorage.getInstance().getReference();


        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);

        sendCodeButton = findViewById(R.id.submit1);
        verifyCodeButton = findViewById(R.id.submit2);
        button3 = findViewById(R.id.submit3);
        phoneNum = findViewById(R.id.phonenumber);
        verifyCodeET = findViewById(R.id.pinView);
        phonenumberText = findViewById(R.id.phonenumberText);

        stepView = findViewById(R.id.step_view);
        stepView.setStepsNumber(3);
        stepView.go(0, true);
        layout1.setVisibility(View.VISIBLE);

        imageView = (ImageView) findViewById(R.id.image);
        pname = (EditText) findViewById(R.id.pname);
        paddress = (EditText) findViewById(R.id.paddress);
        pnumber = (EditText) findViewById(R.id.pnumber);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup1);

        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber = phoneNum.getText().toString();
                phonenumberText.setText(phoneNumber);

                if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 10) {
                    phoneNum.setError("Enter a Phone Number");
                    phoneNum.requestFocus();
                } else {
                    if (currentStep < stepView.getStepCount() - 1) {
                        currentStep++;
                        stepView.go(currentStep, true);
                    } else {
                        stepView.done(true);
                    }
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);

                    String phoneNumber2 = "+91" + phoneNum.getText().toString();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber2,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            AuthenticationActivity.this,               // Activity (for callback binding)
                            mCallbacks);        // OnVerificationStateChangedCallbacks
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.processing_dialog, null);
                AlertDialog.Builder show = new AlertDialog.Builder(AuthenticationActivity.this);

                show.setView(alertLayout);
                show.setCancelable(false);
                dialog_verifying = show.create();
                dialog_verifying.show();
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };

        verifyCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verificationCode = verifyCodeET.getText().toString();
                if (verificationCode.isEmpty()) {
                    Toast.makeText(AuthenticationActivity.this, "Enter verification code", Toast.LENGTH_SHORT).show();
                } else {

                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.processing_dialog, null);
                    AlertDialog.Builder show = new AlertDialog.Builder(AuthenticationActivity.this);

                    show.setView(alertLayout);
                    show.setCancelable(false);
                    dialog_verifying = show.create();
                    dialog_verifying.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStep < stepView.getStepCount() - 1) {
                    currentStep++;
                    stepView.go(currentStep, true);
                } else {
                    stepView.done(true);
                }
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.profile_create_dialog, null);
                AlertDialog.Builder show = new AlertDialog.Builder(AuthenticationActivity.this);
                show.setView(alertLayout);
                show.setCancelable(false);

                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.GONE);
                layout4.setVisibility(View.VISIBLE);
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "signInWithCredential:success");
                            dialog_verifying.dismiss();

                            layout1.setVisibility(View.GONE);
                            layout2.setVisibility(View.GONE);
                            layout3.setVisibility(View.VISIBLE);

                            FirebaseUser currentFirebaseUser = mAuth.getInstance().getCurrentUser();
                            String phone = currentFirebaseUser.getPhoneNumber();
                            pnumber.setText(phone);
                        } else {
                            dialog_verifying.dismiss();
                            Toast.makeText(AuthenticationActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });
    }

    @SuppressWarnings("VisibleForTests")
    public void Upload_Click(View v) {

            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Setting up your profile");
            dialog.show();

            // get selected radio button from radioGroup
            int selectedId = radioSexGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            radioSexButton = (RadioButton) findViewById(selectedId);

            //Display success toast msg
            Toast.makeText(getApplicationContext(), "Profile Updated.", Toast.LENGTH_SHORT).show();
            ProfileApdater profiledetail = new ProfileApdater(pname.getText().toString(), paddress.getText().toString(), pnumber.getText().toString(), radioSexButton.getText().toString());

            //Save image info in to firebase database
            String uploadId = mDatabaseRef.push().getKey();
            mDatabaseRef.child(uploadId).setValue(profiledetail);
            Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
            startActivity(intent);
    }
}


