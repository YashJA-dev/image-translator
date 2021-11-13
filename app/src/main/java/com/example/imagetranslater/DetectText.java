package com.example.imagetranslater;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;

public class DetectText {
    Bitmap imageBitmap;
    Context context;
    Activity activity;
    BottomSheetDialog shhet;

    DetectText(Bitmap bitmap, Activity activity, Context context, BottomSheetDialog sheet){
        imageBitmap=bitmap;
        this.activity=activity;
        this.context=context;
        this.shhet=sheet;
    }
    public void setText(TextView fetchedtextstoreTV){
        InputImage inputImage=InputImage.fromBitmap(imageBitmap,0);
        TextTranslator textTranslator=new TextTranslator(activity,context);
        TextView translatedTextTesxtView=shhet.findViewById(R.id.traslatedText);
        StringBuilder fetchedTextLines=new StringBuilder();
        int english= FirebaseTranslateLanguage.EN;
        int hindi=FirebaseTranslateLanguage.HI;
        StringBuilder traslated_text=new StringBuilder();
        TextRecognizer recognizer= TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        Task<Text> result=recognizer.process(inputImage).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(@NonNull Text text) {

                for(Text.TextBlock block:text.getTextBlocks()){
                    String blockText= block.getText();
                    Point[] blockCornerPoint=block.getCornerPoints();
                    Rect blockFrame=block.getBoundingBox();
                    for(Text.Line line:block.getLines()){
                        String lineText=line.getText();
                        Point[] linecornerpoint=line.getCornerPoints();
                        Rect lineRect=line.getBoundingBox();
                        StringBuilder fetchedTextwords=new StringBuilder();
                        for(Text.Element element:line.getElements()){
                            String elementText=element.getText();
                            fetchedTextwords.append(elementText+" ");
                        }

                       fetchedTextLines.append(fetchedTextwords.toString()+"\n");
                    }
                }
                fetchedtextstoreTV.setText(fetchedTextLines.toString());
                String fromText=fetchedtextstoreTV.getText().toString();

                textTranslator.traslateText(english,hindi,fromText,translatedTextTesxtView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT);
            }
        });
    }
}
