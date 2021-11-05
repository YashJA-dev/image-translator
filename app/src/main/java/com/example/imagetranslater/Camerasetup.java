package com.example.imagetranslater;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class Camerasetup {
    private BottomSheetDialog sheet;
    private Context context;
    private Activity activity;
    private Camera camera;
    private Preview preview;
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private Button captureButton;
    Camerasetup(BottomSheetDialog sheet,Context context,Activity activity){
        this.sheet=sheet;
        this.context=context;
        this.activity=activity;
    }
    public void initCamera(){
        captureButton=sheet.findViewById(R.id.capture_img_btn);
        previewView=sheet.findViewById(R.id.camera_view);
        startCamera();
    }
    public void makeToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> futureCameraProvider = ProcessCameraProvider.getInstance(context);
        futureCameraProvider.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider=futureCameraProvider.get();
                    bindPreview(cameraProvider);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(context));
        clicksOfCamera();
    }

    private void clicksOfCamera() {
        captureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeToast(context,"clciked");
                }
            });
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)context, cameraSelector, preview);
        preview.setSurfaceProvider(previewView.createSurfaceProvider(camera.getCameraInfo()));
    }
}
