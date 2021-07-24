package com.abdalazizabdallah.tawseelat.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.abdalazizabdallah.tawseelat.NavGraphDirections;
import com.abdalazizabdallah.tawseelat.R;
import com.abdalazizabdallah.tawseelat.databinding.FragmentScanQRCodeForHireEmployeeBinding;
import com.abdalazizabdallah.tawseelat.heplers.PermissionsHelper;
import com.abdalazizabdallah.tawseelat.heplers.PublicHelper;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.zxing.BarcodeFormat.QR_CODE;

public class ScanQRCodeForHireEmployeeFragment extends Fragment {
    private static final String TAG = "ScanQRCodeForHireEmploy";
    private CodeScanner mCodeScanner;
    private FragmentScanQRCodeForHireEmployeeBinding fragmentScanQRCodeForHireEmployeeBinding;
    private NavController navController;
    private ActivityResultLauncher<String> mRequestPermissionCamera;
    private ActivityResultLauncher<String> mRequestPermissionWriteExternal;
    private ActivityResultLauncher<String> mGetContent;
    private BarcodeScanner barcodeScanner;
    private boolean isSuccessfulScanBarcode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestPermissionCamera = registerForActivityResultRequestPermissionCamera();
        mRequestPermissionWriteExternal = registerForActivityResultRequestPermissionWriteExternal();
        mGetContent = registerForActivityResultGetContent();
        barcodeScanner = setupBarcodeScannerForImage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentScanQRCodeForHireEmployeeBinding = FragmentScanQRCodeForHireEmployeeBinding.inflate(inflater, container, false);
        return fragmentScanQRCodeForHireEmployeeBinding.getRoot();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated: ", null);
        super.onViewCreated(view, savedInstanceState);

        WindowManager.LayoutParams layout = requireActivity().getWindow().getAttributes();
        layout.screenBrightness = 1F;
        requireActivity().getWindow().setAttributes(layout);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(
                fragmentScanQRCodeForHireEmployeeBinding.toolbar, navController, appBarConfiguration);

        configurationScannerView();
        checkPermissionCameraAndStartPreviewScanner();

        fragmentScanQRCodeForHireEmployeeBinding.scannerView.setOnClickListener(view1 ->
                checkPermissionCameraAndStartPreviewScanner()
        );

        fragmentScanQRCodeForHireEmployeeBinding.openPickImageButton.setOnClickListener(v -> {
            PermissionsHelper.checkPermissionStorageAndAction(requireActivity(),
                    () -> mGetContent.launch("image/*")
                    , mRequestPermissionWriteExternal);
        });

    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void configurationScannerView() {
        mCodeScanner = new CodeScanner(requireActivity(), fragmentScanQRCodeForHireEmployeeBinding.scannerView);
        mCodeScanner.setFormats(Collections.singletonList(QR_CODE));
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO decode QR code

                        ProgressDialog progressDialog = new ProgressDialog(requireContext());
                        progressDialog.setMessage(getString(R.string.please_wait));

                        new CountDownTimer(5000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                progressDialog.show();
                            }

                            @Override
                            public void onFinish() {
                                progressDialog.dismiss();
                                showMessageFragmentDialog(new OnClickMyButtonDialogListener() {
                                    @Override
                                    public void onClickMyButtonDialogListener(DialogFragment dialogFragment) {
                                        dialogFragment.dismiss();
                                    }
                                }, result.getText() + "   //    " + result.getBarcodeFormat());
                            }
                        }.start();

                        Toast.makeText(requireActivity(), result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private ActivityResultLauncher<String> registerForActivityResultRequestPermissionCamera() {
        return registerForActivityResult(new ActivityResultContracts.RequestPermission()
                , isGranted -> {
                    if (isGranted) {
                        Log.e(TAG, "requestPermission: isGranted Camera:" + true, null);
                        mCodeScanner.startPreview();
                    } else {
                        showMessageFragmentDialog(new OnClickMyButtonDialogListener() {
                            @Override
                            public void onClickMyButtonDialogListener(DialogFragment dialogFragment) {
                                mRequestPermissionCamera.launch(Manifest.permission.CAMERA);
                                dialogFragment.dismiss();
                            }
                        }, getString(R.string.message_for_permission));
                        Log.e(TAG, "requestPermission: Denied isGranted Camera:" + false, null);
                    }
                });
    }

    private ActivityResultLauncher<String> registerForActivityResultRequestPermissionWriteExternal() {
        return registerForActivityResult(new ActivityResultContracts.RequestPermission()
                , isGranted -> {
                    if (isGranted) {
                        Log.e(TAG, "requestPermission: isGranted WriteExternal:" + true, null);
                        mGetContent.launch("image/*");
                    } else {
                        showMessageFragmentDialog(new OnClickMyButtonDialogListener() {
                            @Override
                            public void onClickMyButtonDialogListener(DialogFragment dialogFragment) {
                                mRequestPermissionWriteExternal.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                dialogFragment.dismiss();
                            }
                        }, getString(R.string.message_for_permission));
                        Log.e(TAG, "requestPermission: Denied isGranted WriteExternal:" + false, null);
                    }
                });
    }

    private ActivityResultLauncher<String> registerForActivityResultGetContent() {
        // TODO Handle the returned Uri
        //TODO handle with barcode
        // connect employee to Manger company IN successScanBarcode
        return registerForActivityResult(new ActivityResultContracts.GetContent(),
                this::handleWithUriImage);
    }

    private void handleWithUriImage(Uri uri) {
        if (uri != null) {
            Log.e("TAG", "onActivityResult: " + uri.toString(), null);

            InputImage image = null;
            try {
                image = InputImage.fromFilePath(requireContext(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (image != null) {
                Log.e(TAG, "handleWithUriImage: " + image.getFormat(), null);
                Task<List<Barcode>> result = barcodeScanner.process(image)
                        .addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                Log.e(TAG, "onCanceled: ", null);
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                            @Override
                            public void onSuccess(List<Barcode> barcodes) {
                                // Task completed successfully
                                isSuccessfulScanBarcode = true;
                                successScanBarcode(barcodes);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                                PublicHelper.showMessageSnackbar(requireActivity().findViewById(android.R.id.content)
                                        , getString(R.string.no_qr_code_message)
                                );
                            }
                        });

                if (!isSuccessfulScanBarcode) {
                    PublicHelper.showMessageSnackbar(requireActivity().findViewById(android.R.id.content)
                            , getString(R.string.no_qr_code_message)
                    );
                }
            }
        }
    }

    private void showMessageFragmentDialog(OnClickMyButtonDialogListener onClickMyButtonDialogListener, String message) {
        navController.navigate(NavGraphDirections.actionGlobalToMessageFragmentDialog(
                onClickMyButtonDialogListener, message));
    }

    private void checkPermissionCameraAndStartPreviewScanner() {
        PermissionsHelper.checkPermissionAndAction(requireActivity(),
                Manifest.permission.CAMERA,
                () -> mCodeScanner.startPreview()
                , mRequestPermissionCamera);
    }

    private BarcodeScanner setupBarcodeScannerForImage() {
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_QR_CODE)
                        .build();
        return BarcodeScanning.getClient(options);
    }

    private void successScanBarcode(List<Barcode> barcodes) {
        for (Barcode barcode : barcodes) {

            Rect bounds = barcode.getBoundingBox();
            Point[] corners = barcode.getCornerPoints();
            String rawValue = barcode.getRawValue();
            int valueType = barcode.getValueType();

            Log.e(TAG, "onSuccess: " + bounds, null);
            Log.e(TAG, "onSuccess: " + Arrays.deepToString(corners), null);

            // See API reference for complete list of supported types
            if (valueType == Barcode.TYPE_TEXT) {
                ProgressDialog progressDialog = new ProgressDialog(requireContext());
                progressDialog.setMessage(getString(R.string.please_wait));

                new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        progressDialog.show();
                    }

                    @Override
                    public void onFinish() {
                        progressDialog.dismiss();
                        showMessageFragmentDialog(new OnClickMyButtonDialogListener() {
                            @Override
                            public void onClickMyButtonDialogListener(DialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                            }
                        }, barcode.getRawValue() + "   //  QR CODE");
                    }
                }.start();
            } else {
                showMessageFragmentDialog(new OnClickMyButtonDialogListener() {
                    @Override
                    public void onClickMyButtonDialogListener(DialogFragment dialogFragment) {
                        dialogFragment.dismiss();
                    }
                }, getString(R.string.not_matching_qr_code));
            }
            checkPermissionCameraAndStartPreviewScanner();
        }
    }

}