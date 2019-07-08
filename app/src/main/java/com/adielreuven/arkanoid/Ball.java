package com.adielreuven.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static com.adielreuven.arkanoid.Brick.BRICK_HEIGHT;
import static com.adielreuven.arkanoid.Paddle.PADDLE_HEIGHT;
import static com.adielreuven.arkanoid.Paddle.PADDLE_WIDTH;

public class Ball
{
    private float dx,dy;
    private float cx;
    private float cy;
    private float radius;
    private boolean lostLive;
    private Paint pen;

    public Ball(float cx, float cy, float radius)
    {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.dx = 0;
        this.dy = 0;
        this.pen = new Paint(Paint.ANTI_ALIAS_FLAG);
        lostLive = false;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public void draw(Canvas canvas)
    {
        pen.setColor(Color.WHITE);
        canvas.drawCircle(cx,cy,radius,pen);
    }

    public void move(int w, int h)
    {
        this.cx+= dx;
        this.cy+= dy;

        // check if ball out of left or right side
        if((cx-radius) <= 0 || (cx+radius) >= w)
        {
            dx = -dx;
        }

        // check if ball out of up side
        if( (cy-radius) <= 0 )
        {
            dy = -dy;
        }

        if( (cy + radius) >= h )
        {
            lostLive = true;
        }
    }

    public boolean isLostLives()
    {
        if(lostLive == true )
        {
            lostLive = false;
            return true;
        }
        return false;
    }

    public boolean collideWith(Paddle paddle)
    {
        float x1,y1, x2,y2;
        x1 = paddle.getStartX();
        y1 = paddle.getStartY();

        x2 =  paddle.getStartX() + PADDLE_WIDTH;
        y2 = paddle.getStartY() + PADDLE_HEIGHT;

        // from up
        if( (cx >= x1 && cx <= x2) && (cy + radius) == y1)
        {
            dy = -dy;
            return true;
        }
        //from right
        if( cx-radius == x2 && (cy >= y1 && cy <= y2))
        {
            dx=-dx;
        }
        //from left
        if( cx+radius == x1 && (cy >= y1 && cy <= y2) )
        {
            dx=-dx;
        }
        return false;
    }

    public boolean collideWith(Brick brick)
    {
        float x1,y1, x2,y2;
        x1 = brick.getLocationX();
        y1 = brick.getLocationY();

        x2 = brick.getLocationX() + brick.getBrickWidth();
        y2 = brick.getLocationY() + BRICK_HEIGHT;

        // from down
        if(cy-radius <= (y2) && cy+radius > y1 && (cx-radius >= x1 && cx+radius <= x2))
        {
            dy = -dy;
            return true;
        }
        //from top
        if(cy+radius >= y1 && cy+radius < y2 && (cx-radius >= x1 && cx+radius <= x2))
        {
            dy = -dy;
            return true;
        }
        //left
        if(cx-radius <= x2 && cx-radius > x1 && (cy-radius >= y1 && cy+radius <= y2))
        {
            dx = -dx;
            return true;
        }
        //right
        if(cx+radius >= x1 && cx+radius < x2 && (cy-radius >= y1 && cy+radius <= y2))
        {
            dx = -dx;
            return true;
        }
        return  false;
    }

    public float getRadius() {
        return radius;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

}// end Ball class