package com.example.imagetranslater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.mlkit.vision.text.Text;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;


public class Translator extends Fragment {
    Activity activity;
    Context context;
    Bitmap bmp;
    EditText transLatedtext;
    File file;
    View v;
    BottomSheetDialog bottumSheet;
    BottomSheetBehavior sheetBehavior;
    String path;
    ImageView sheetclosebtn;
    ImageView imgTranslation;
    FragmentManager supportFragmentManager;
    FloatingActionButton translatorBtn;
    public Translator(Activity activity, Context context){

    }
    @SuppressLint("ValidFragment")
    public Translator(Activity activity, Context context, FragmentManager supportFragmentManager) {
        this.activity=activity;
        this.context=context;
        this.supportFragmentManager=supportFragmentManager;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_translator, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.context=getContext();
        this.activity=getActivity();
        imgTranslation=activity.findViewById(R.id.TranslatorImage);
        translatorBtn=activity.findViewById(R.id.translator_btn);
        imgTranslation.setImageResource(R.drawable.img_to_jpg);
//        sheetclosebtn=bottumSheet.findViewById(R.id.botoom_sheet_closer_btn);
        Bundle bundle=getArguments();
        imageSetUp(bundle,imgTranslation);
        bottumSheet=setupBottumSheet();
        translatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchText(imgTranslation);
            }
        });

    }



    public BottomSheetDialog setupBottumSheet(){
        View v= LayoutInflater.from(context).inflate(R.layout.bottumsheet_translator,activity.findViewById(R.id.sheetparent));
        bottumSheet=new BottomSheetDialog(context,R.style.bottomsheetTheme);
        bottumSheet.setContentView(v);
        setBehavioursOfBottumSheet(v);
        return bottumSheet;
    }
    private void setBehavioursOfBottumSheet(View v) {
        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) v.getParent());
        mBehavior.setState(mBehavior.STATE_EXPANDED);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });

    }
    private void imageSetUp(Bundle bundle,ImageView imageHolder) {
        boolean defaultImageisThere=true;
        if(imageExist(bundle)){
            defaultImageisThere=false;
            setUpImageWithNoCatche(0,bundle,imageHolder);
        }
        boolean finalDefaultImageisThere = defaultImageisThere;
        imageHolder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(finalDefaultImageisThere)return false;
                fetchText(imageHolder);

                return true;
            }
        });
    }

    private void fetchText(ImageView imageHolder) {
        bottumSheet.show();
        BitmapDrawable drawable = (BitmapDrawable) imageHolder.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        DetectText detectText=new DetectText(bitmap,activity,context,bottumSheet);
        TextView extractedTextHolder=bottumSheet.findViewById(R.id.extracted_text);
        extractedTextHolder.setText("coming");
        detectText.setText(extractedTextHolder);
//        makeToast(context,"new");
    }

    public void makeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public void setUpImageWithNoCatche(int drawable,Bundle bundle,ImageView imageHolder){
         if(drawable==0){
             path=bundle.getString("imagePath");
             File file=new File(path);
             Picasso.get().load(file).memoryPolicy(MemoryPolicy.NO_CACHE).into(imageHolder);
        }else{
             Picasso.get().load(R.drawable.img_to_jpg).memoryPolicy(MemoryPolicy.NO_CACHE).into(imageHolder);

        }
    }
    private boolean imageExist(Bundle bundle){
        if((bundle!=null)){
            path=bundle.getString("imagePath");
            return path!=null&&path.length()>0&&new File(path).exists();
        }else
            return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        Picasso.get().load(R.drawable.img_to_jpg).into(imgTranslation);
    }

}