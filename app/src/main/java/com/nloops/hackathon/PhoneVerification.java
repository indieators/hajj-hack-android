package com.nloops.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {


  @BindView(R.id.editTextCode)
  EditText mEditCode;
  @BindView(R.id.editTextPhone)
  EditText mEditPhone;

  FirebaseAuth mAuth;
  String codeSent;
  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

    @Override
    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
      Log.i("FESA", "onVerificationCompleted: " + phoneAuthCredential.getSmsCode());
    }

    @Override
    public void onVerificationFailed(FirebaseException e) {
      Log.i("FESA", "onVerificationFailed: " + e.getMessage().toString());
    }

    @Override
    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
      super.onCodeSent(s, forceResendingToken);

      codeSent = s;
      Log.i("FESA", "onCodeSent: " + codeSent);
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_phone_verification);
    ButterKnife.bind(this);

    mAuth = FirebaseAuth.getInstance();

  }

  public void verifySignInCode(View view) {
    String code = mEditCode.getText().toString();
    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
    signInWithPhoneAuthCredential(credential);
  }

  private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              //here you can open new activity
              String currentUser = task.getResult().getUser().getUid();
              SharedPreferences preferences = PreferenceManager
                  .getDefaultSharedPreferences(PhoneVerification.this);
              preferences.edit().putString(getString(R.string.key_user_name), currentUser).commit();
              Intent intent = new Intent(PhoneVerification.this, MainActivity.class);
              startActivity(intent);
            } else {
              if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(getApplicationContext(),
                    "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
              }
            }
          }
        });
  }

  public void sendVerificationCode(View view) {

    String phone = mEditPhone.getText().toString();

    if (phone.isEmpty()) {
      mEditPhone.setError("Phone number is required");
      mEditPhone.requestFocus();
      return;
    }

    if (phone.length() < 10) {
      mEditPhone.setError("Please enter a valid phone");
      mEditPhone.requestFocus();
      return;
    }
    Log.i("FESA", "sendVerificationCode: ");
    PhoneAuthProvider.getInstance().verifyPhoneNumber(
        phone,        // Phone number to verify
        60,                 // Timeout duration
        TimeUnit.SECONDS,   // Unit of timeout
        this,               // Activity (for callback binding)
        mCallbacks);        // OnVerificationStateChangedCallbacks
  }
}
