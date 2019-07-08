package com.adielreuven.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Brick
{
    public static final int BRICK_HEIGHT = 50;

    // this attributes represent point oft left upper point of brick
    private float locationX;
    private float locationY;
    private int brickWidth;

    private Paint pen;
    private int color;

    public  Brick(float locationX, float locationY , int brickWidth)
    {
        this.locationX = locationX;
        this.locationY = locationY;
        this.brickWidth = brickWidth;
        this.pen = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public float getLocationX() {
        return locationX;
    }

    public void setLocationX(float locationX) {
        this.locationX = locationX;
    }

    public float getLocationY() {
        return locationY;
    }

    public void setLocationY(float locationY) {
        this.locationY = locationY;
    }

    public int getBrickWidth() {
        return brickWidth;
    }

    public int getColor()
    {
        return this.color;
    }
    public void setColor(int color)
    {
        this.color = color;
    }

    public void drawBrick(Canvas canvas, int color)
    {
        pen.setColor(color);
        canvas.drawRect(locationX, locationY, locationX + brickWidth, locationY + BRICK_HEIGHT,  pen);
    }

}// end Brick class