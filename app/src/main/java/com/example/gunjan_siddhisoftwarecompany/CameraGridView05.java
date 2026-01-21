package com.example.gunjan_siddhisoftwarecompany;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CameraGridView05 extends View {
    private Paint paint = new Paint();
    private String gridType = "none";

    public CameraGridView05(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(150); // Semi-transparent lines
    }

    public void setGridType(String type) {
        this.gridType = type;
        invalidate(); // Redraw the view
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        if (gridType.equals("3x3")) {
            // Draw 2 vertical lines
            canvas.drawLine(width / 3f, 0, width / 3f, height, paint);
            canvas.drawLine(2 * width / 3f, 0, 2 * width / 3f, height, paint);
            // Draw 2 horizontal lines
            canvas.drawLine(0, height / 3f, width, height / 3f, paint);
            canvas.drawLine(0, 2 * height / 3f, width, 2 * height / 3f, paint);
        } else if (gridType.equals("4x2")) {
            // 4 columns, 2 rows
            canvas.drawLine(width / 4f, 0, width / 4f, height, paint);
            canvas.drawLine(width / 2f, 0, width / 2f, height, paint);
            canvas.drawLine(3 * width / 4f, 0, 3 * width / 4f, height, paint);
            canvas.drawLine(0, height / 2f, width, height / 2f, paint);
        } else if (gridType.equals("phi")) {
            // Golden Ratio (Phi) lines (approx 0.38 and 0.62)
            float phi = 0.382f;
            canvas.drawLine(width * phi, 0, width * phi, height, paint);
            canvas.drawLine(width * (1 - phi), 0, width * (1 - phi), height, paint);
            canvas.drawLine(0, height * phi, width, height * phi, paint);
            canvas.drawLine(0, height * (1 - phi), width, height * (1 - phi), paint);
        }
    }
}