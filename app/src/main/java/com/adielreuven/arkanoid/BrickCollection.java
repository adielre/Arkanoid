package com.adielreuven.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;

import static com.adielreuven.arkanoid.Brick.BRICK_HEIGHT;

public class BrickCollection
{
    private static int INTERVAL = 6;

    private ArrayList<Brick> bricks;
    private int rows;
    private int cols;
    private int brickWidth;

    private int counter;

    public BrickCollection(int rows, int cols, int canvasWidth)
    {
        counter = 0;

        int locationX = INTERVAL, locationY = 180;

        this.rows = rows;
        this.cols = cols;

        bricks = new ArrayList<>();
        brickWidth = (canvasWidth / cols) - INTERVAL;

        // setting bricks coordinates approach to screen
        bricks.add( new Brick(locationX, locationY, brickWidth));
        for (int i = 2; i <= rows * cols; i++)
        {
            if( i % 7 == 1)
            {
                locationX = INTERVAL;
                locationY += INTERVAL + BRICK_HEIGHT;
            }
            else
            {
                locationX = locationX + brickWidth + INTERVAL;
            }
            bricks.add(new Brick(locationX, locationY, brickWidth));
        }
    }

    public void draw(Canvas canvas)
    {
        if(counter == 0)
        {
            int color = 0;
            for (int i = 0; i < bricks.size(); i++)
            {
                if( i < 7 )
                {
                    color = Color.BLACK;
                }
                else if( i >= 7 && i < 14)
                {
                    color = Color.RED;
                }
                else if( i >= 14 && i < 21)
                {
                    color = Color.YELLOW;
                }
                else if( i >= 21 && i < 28)
                {
                    color = Color.BLUE;
                }
                else if( i >= 28 && i < 35)
                {
                    color = Color.GREEN;
                }

                bricks.get(i).setColor(color);
            }
            counter++;
        }

        for (int i = 0; i < bricks.size(); i++)
        {
            bricks.get(i).drawBrick(canvas, bricks.get(i).getColor());
        }
    }

    public boolean isCollideWithBrick(Ball ball)
    {
        for (int i = 0; i < bricks.size(); i++)
        {
            Brick cuurentBrick = bricks.get(i);

            if (ball.collideWith(cuurentBrick) == true) {
                bricks.remove(i);
                return true;
            }
        }
        return false;
    }

    public int getSize()
    {
        return bricks.size();
    }

}// end BrickCollection class
