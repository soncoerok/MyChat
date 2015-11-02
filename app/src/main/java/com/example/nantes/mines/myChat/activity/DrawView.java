package com.example.nantes.mines.myChat.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Surface view allowing to draw
 */
public class DrawView extends SurfaceView {

    /** List of colors available for circle */
    private static int[] COLORS = {Color.BLACK, Color.RED, Color.YELLOW, Color.MAGENTA, Color.BLUE};
    /** the x coordinate of the circle */
    private float coordX;
    /** the y coordinate of the circle */
    private float coordY;
    /** the radius of the circle */
    private float radius;
    /** the paint object */
    private Paint paint = new Paint();
    /** the list of circle */
    private List<Circle> circles = new ArrayList<>();

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Draws on a canvas
     * @param canvas    the canvas
     */
    protected void onDraw(Canvas canvas) {
        // Draw the circle in the list
        for (Circle c : circles){
            paint.setColor(c.color);
            canvas.drawCircle(c.x, c.y, c.r, paint);
        }
        canvas.drawCircle(coordX, coordY, 15, paint);
    }

    /**
     *
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        // Get the actual coordinates
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                // Add a new circle
                circles.add(new Circle(x, y, radius, COLORS[new Random().nextInt(5)]));
                break;
            case MotionEvent.ACTION_DOWN:
                // Save the coordinate during the down action
                coordX = x;
                coordY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // Save the radius of the circle & add it to the list
                radius = (float) Math.sqrt((coordX - x)*(coordX - x) + (coordY - y)*(coordY - y));
                circles.add(new Circle(x, y, radius, COLORS[new Random().nextInt(5)]));
                break;
        }
        // Paint
        invalidate();
        return true;
    }

    /**
     * Private class for an object Circle
     */
    private class Circle {
        /** The x coordinates */
        float x;
        /** The y coordinates */
        float y;
        /** The radius */
        float r;
        /** The color */
        int color;

        private Circle(float x, float y, float r, int color) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.color = color;
        }
    }

}
