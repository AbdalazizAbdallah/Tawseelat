package com.abdalazizabdallah.tawseelat.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentVerifySignUpBinding;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelper;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
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
    private boolean countDownTimeFinish = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        makeMCallbacks();
        sendCode();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentVerifySignUpBinding = FragmentVerifySignUpBinding.inflate(inflater, container, false);

        return fragmentVerifySignUpBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        countDown();

        fragmentVerifySignUpBinding.verifyCode.setOnClickListener(v -> {
            VerifySignUpFragment.this.code = fragmentVerifySignUpBinding.pinView.getText().toString();

            if (!PublicHelper.isEmptyFields(VerifySignUpFragment.this.code)) {
                fragmentVerifySignUpBinding.parentProgressCircular.setVisibility(View.VISIBLE);
                fragmentVerifySignUpBinding.progressCircular.setVisibility(View.VISIBLE);
                fragmentVerifySignUpBinding.progressCircular.show();

                credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                signInWithPhoneAuthCredential(credential);
            } else {
                PublicHelper.showMessageSnackbar(requireActivity().findViewById(android.R.id.content),
                        "Code is " + getString(R.string.required)
                );
            }

        });

        fragmentVerifySignUpBinding.resendCode.setOnClickListener(v -> {
            if (isAllowed) {
                isAllowed = false;
                sendCode();
                countDown();
            }
        });

    }

    private void makeMCallbacks() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
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
            public void onVerificationFailed(@NonNull FirebaseException e) {
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
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = task.getResult().getUser();
                        // Update UI
                        PublicHelper.showMessageSnackbar(requireActivity().findViewById(android.R.id.content),
                                "Successful"
                        );

                        countDownTimeFinish = true;
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid

                            PublicHelper.showMessageSnackbar(requireActivity().findViewById(android.R.id.content),
                                    "failure"
                            );

                        }
                    }
                    fragmentVerifySignUpBinding.progressCircular.hide();
                    fragmentVerifySignUpBinding.progressCircular.setVisibility(View.GONE);
                    fragmentVerifySignUpBinding.parentProgressCircular.setVisibility(View.GONE);
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

                String s = getString(R.string.didnT_receive_tap_to_send_message) + " " +
                        (millisUntilFinished / 1000);
                fragmentVerifySignUpBinding.resendCode.setText(s);

                if (countDownTimeFinish) {
                    onFinish();
                }
            }

            public void onFinish() {
                fragmentVerifySignUpBinding.resendCode.setText(
                        getString(R.string.didnT_receive_tap_to_send_message));

                isAllowed = true;
                countDownTimer.cancel();
            }
        }.start();
    }

}