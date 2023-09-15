package com.example.furniture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class JugView extends View {
    private Paint paint;
    private int jugWidth = 150;
    private int jugHeight = 200;
    private int jugSpacing = 50;
    private int jug1Level = 50;
    private int jug2Level = 25;

    public JugView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        // Draw the first jug
        int jug1X = (width - jugWidth * 2 - jugSpacing) / 2;
        int jug1Y = (height - jugHeight) / 2;
        drawJug(canvas, jug1X, jug1Y, jugWidth, jugHeight, jug1Level);

        // Draw the second jug
        int jug2X = jug1X + jugWidth + jugSpacing;
        int jug2Y = jug1Y;
        drawJug(canvas, jug2X, jug2Y, jugWidth, jugHeight, jug2Level);
    }

    private void drawJug(Canvas canvas, int x, int y, int width, int height, int level) {
        // Draw the outer jug shape
        canvas.drawRect(x, y, x + width, y + height, paint);
        canvas.drawLine(x, y + height, x + width, y + height, paint);

        // Draw the inner jug shape based on the current water level
        int waterHeight = height * level / 100;
        RectF innerRect = new RectF(x + 4, y + 4, x + width - 4, y + waterHeight - 4);
        canvas.drawRect(innerRect, paint);
        canvas.drawLine(x + 4, y + waterHeight - 4, x + width - 4, y + waterHeight - 4, paint);
    }

    public void setJug1Level(int level) {
        jug1Level = level;
        invalidate();
    }

    public void setJug2Level(int level) {
        jug2Level = level;
        invalidate();
    }
}

