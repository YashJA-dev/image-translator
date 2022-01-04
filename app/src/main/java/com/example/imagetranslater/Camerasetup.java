package com.example.imagetranslater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

public class Camerasetup {
    private Context context;
    private Activity activity;
    private Camera camera;
    private Preview preview;
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private Button captureButton;
    private BottomSheetBehavior sheetBehavior;
    private BottomSheetDialog sheet;
    private ViewPager viewPager;
    private ListenableFuture<ProcessCameraProvider> futureCameraProvider;
    private  FragmentManager supportFragmentManager;
    Camerasetup(Context context, Activity activity, BottomSheetBehavior sheetBehavior) {
        this.context = context;
        this.activity = activity;
        this.sheetBehavior = sheetBehavior;
        captureButton = activity.findViewById(R.id.capture_img_btn);
        previewView = activity.findViewById(R.id.camera_view);
    }

    Camerasetup(Context context, Activity activity, BottomSheetDialog sheet) {
        this.context = context;
        this.activity = activity;
        this.sheet=sheet;
        captureButton = activity.findViewById(R.id.capture_img_btn);
        previewView = activity.findViewById(R.id.camera_view);
    }

    public Camerasetup(Context context, Activity activity, ViewPager viewPager, FragmentManager supportFragmentManager) {
        this.context = context;
        this.activity = activity;
        this.viewPager=viewPager;
        this.supportFragmentManager=supportFragmentManager;
        captureButton = activity.findViewById(R.id.capture_img_btn);
        previewView = activity.findViewById(R.id.camera_view);
    }

    @SuppressLint("RestrictedApi")
    public void initCamera() {
        String path=context.getExternalCacheDir() + File.separator + "image.png";
        File fdelete = new File(path);
        if (fdelete. exists()){
            if(fdelete.delete()){
                Log.d("image_deletetion",fdelete.getName()+" "+fdelete.delete());
            }else{
                Log.d("notdeleted","not");

            }
        }
        if(!CameraX.isInitialized()) {
            startCamera();
        }
    }

    public void makeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void startCamera() {
   futureCameraProvider = ProcessCameraProvider.getInstance(context);
        futureCameraProvider.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = futureCameraProvider.get();
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
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {

                Translator translator = new Translator(activity,context);
//                Bundle bundle = new Bundle();
//                bundle.putByteArray("image",byteArray);
//                translator.setArguments(bundle);
                String path=context.getExternalCacheDir() + File.separator + "image.png";
                Log.d("path_",path);
                ImageCapture.OutputFileOptions outputFileOptions =
                        new ImageCapture.OutputFileOptions.Builder(new File(path)).build();
                imageCapture.takePicture(outputFileOptions, CameraXExecutors.mainThreadExecutor() ,
                        new ImageCapture.OnImageSavedCallback() {
                            @Override
                            public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
                               Bundle bundle=new Bundle();
                               bundle.putString("imagePath",path);
                                translator.setArguments(bundle);
                                supportFragmentManager.beginTransaction().
                                        replace(R.id.translator_frag_parent,translator).addToBackStack("frag").commit();
                                viewPager.setCurrentItem(1,true);
                            }
                            @Override
                            public void onError(ImageCaptureException error) {
                                makeToast(context,error.getMessage());
                            }
                        }
                );

            }
        });
    }
    private Bitmap getBitmap(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        buffer.rewind();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes);
        byte[] clonedBytes = bytes.clone();
        return BitmapFactory.decodeByteArray(clonedBytes, 0, clonedBytes.length);
    }
    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) context, cameraSelector, preview,imageCapture);
        preview.setSurfaceProvider(previewView.createSurfaceProvider(camera.getCameraInfo()));
    }
}
