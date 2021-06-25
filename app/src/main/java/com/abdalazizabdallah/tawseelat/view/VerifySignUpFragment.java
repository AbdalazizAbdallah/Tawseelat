package com.abdalazizabdallah.tawseelat.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentVerifySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifySignUpFragment extends Fragment {

    private static final String TAG = "VerifySignUpFragment";
    FragmentVerifySignUpBinding fragmentVerifySignUpBinding;
    PhoneAuthCredential credential;
    //--------------Firebase---------------------
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private String code;
    ///-------------------------------
    private boolean isAllowed = false;
    private CountDownTimer countDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        makeMCallbacks();
        sendCode();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentVerifySignUpBinding = FragmentVerifySignUpBinding.inflate(inflater, container, false);

        return fragmentVerifySignUpBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        countDown();

        fragmentVerifySignUpBinding.verifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentVerifySignUpBinding.parentProgressCircular.setVisibility(View.VISIBLE);
                fragmentVerifySignUpBinding.progressCircular.setVisibility(View.VISIBLE);
                fragmentVerifySignUpBinding.progressCircular.show();


                VerifySignUpFragment.this.code = fragmentVerifySignUpBinding.pinView.getText().toString();
                credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                signInWithPhoneAuthCredential(credential);

            }
        });

        fragmentVerifySignUpBinding.resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllowed) {
                    isAllowed = false;
                    sendCode();
                    countDown();
                }
            }
        });

    }

    private void makeMCallbacks() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, ":" + credential);

                // signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                VerifySignUpFragment.this.mVerificationId = verificationId;
                // mResendToken = token;
                //FirebaseAuthSettings firebaseAuthSettings = mAuth.getFirebaseAuthSettings();

                // Configure faking the auto-retrieval with the whitelisted numbers.
                //firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber("+970592639219",verificationId);
            }
        };

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            // Update UI

                            Toast.makeText(requireContext(), "isSuccessful", Toast.LENGTH_SHORT).show();

                            countDownTimer.onFinish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                                Toast.makeText(requireContext(), "failure", Toast.LENGTH_SHORT).show();

                            }
                        }
                        fragmentVerifySignUpBinding.progressCircular.hide();
                        fragmentVerifySignUpBinding.progressCircular.setVisibility(View.GONE);
                        fragmentVerifySignUpBinding.parentProgressCircular.setVisibility(View.GONE);
                    }
                });
    }

    private void sendCode() {
        FirebaseAuthSettings firebaseAuthSettings = mAuth.getFirebaseAuthSettings();

// Configure faking the auto-retrieval with the whitelisted numbers.
        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber("+970592639219", "789456");

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+970567349666")       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())            // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .requireSmsValidation(true)

                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void countDown() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                fragmentVerifySignUpBinding.resendCode.setText(
                        getString(R.string.didnT_receive_tap_to_send_message) + " " +
                                millisUntilFinished / 1000);
            }

            public void onFinish() {
                fragmentVerifySignUpBinding.resendCode.setText(
                        getString(R.string.didnT_receive_tap_to_send_message));

                isAllowed = true;
            }
        }.start();
    }

}