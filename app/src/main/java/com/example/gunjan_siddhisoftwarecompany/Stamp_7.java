package com.example.gunjan_siddhisoftwarecompany;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
public class Stamp_7 extends AppCompatActivity {
    ImageView imgTransLine, imgTransValue;
    TextView txtTransparencyValue;
    ImageView stamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stamp_07_in_figma_design);
        imgTransLine = findViewById(R.id.imgTransLine);
        imgTransValue = findViewById(R.id.imgTransValue);
        txtTransparencyValue = findViewById(R.id.txtTransparencyValue);
        stamp = findViewById(R.id.stamp);

        imgTransLine.post(() -> {

            int lineWidth = imgTransLine.getWidth();
            int thumbWidth = imgTransValue.getWidth();

            imgTransLine.setOnTouchListener((v, event) -> {

                if (event.getAction() == MotionEvent.ACTION_DOWN ||
                        event.getAction() == MotionEvent.ACTION_MOVE) {

                    float x = event.getX();

                    // Clamp value
                    if (x < 0) x = 0;
                    if (x > lineWidth) x = lineWidth;

                    // Percentage
                    int percent = Math.round((x / lineWidth) * 100);

                    // Update text
                    txtTransparencyValue.setText(percent + " %");

                    // Center the thumb on touch
                    float thumbX = x - (thumbWidth / 2f);
                    imgTransValue.setTranslationX(thumbX);

                    // Apply transparency to stamp
                    stamp.setAlpha(percent / 100f);

                    return true;
                }
                return false;
            });
        });
    }
}