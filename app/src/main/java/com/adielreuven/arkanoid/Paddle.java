package com.adielreuven.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Paddle
{
    // this point represent the left upper point of paddle
    private float startX;
    private float startY;

    // Finals that represent the size of paddle
    public static final int PADDLE_HEIGHT = 25;
    public static final int PADDLE_WIDTH = 400;

    private float dx;
    private Paint pen;

    public Paddle(float startX, float startY)
    {
        this.startX = startX;
        this.startY = startY;
        this.pen = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void draw(Canvas canvas)
    {
        pen.setColor(Color.BLUE);
        canvas.drawRect(startX, startY, startX + PADDLE_WIDTH, startY + PADDLE_HEIGHT, pen);
    }

    public void setLocation(float cx)
    {
        setStartX(cx);
    }

    public void moveRight(int w)
    {
        // check if paddle out of right side
        if( (startX + PADDLE_WIDTH ) >= w )
            return;
        this.startX += dx;
    }

    public void moveLeft()
    {
        // check if paddle out of left  side
        if( startX <= 0 )
            return;
        this.startX -= dx;
    }

}// end Paddle class