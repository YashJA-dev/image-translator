package com.example.imagetranslater;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private boolean permissionGranted;
    Context context;
    Activity activity;
    private Button captureButton;
    private Camera camera;
    private Preview preview;
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private BottomSheetDialog bottomSheetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        activity=this;
        setContentView(R.layout.activity_main);
//        captureButton=activity.findViewById(R.id.capture_img_btn);
//        permissionGranted=permissionsGranted();
//        if(permissionGranted==false){
//            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, 0);
//        }else{
////            startCamera();
//
//
//        }
        setupBottumSheet();
        Camerasetup cameraSetup=new Camerasetup(bottomSheetDialog,context,activity);
        cameraSetup.initCamera();

    }
    public void setupBottumSheet(){
        View v= LayoutInflater.from(MainActivity.this).inflate(R.layout.camera_main,findViewById(R.id.sheetparent));
        bottomSheetDialog=new BottomSheetDialog(MainActivity.this,R.style.bottomsheetTheme);
        bottomSheetDialog.setContentView(v);
        bottomSheetDialog.setOwnerActivity(this);
        setBehavioursOfBottumSheet(v);
        bottomSheetDialog.show();
        if(bottomSheetDialog.getWindow() != null)bottomSheetDialog.getWindow().setDimAmount(0);

    }

    private void setBehavioursOfBottumSheet(View v) {
        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) v.getParent());
        bottomSheetDialog.setCancelable(false);
        mBehavior.setPeekHeight(150);
        mBehavior.setState(BottomSheetBehavior.STATE_DRAGGING);

    }

    public boolean permissionsGranted(){
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

//   super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(!permissionGranted){
////            makeToast(context,"Pls give the required permissions other wise you will not be able to use the features associated with them");
//        }else{
////            startCamera();
//        }
    }



    public void makeToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}