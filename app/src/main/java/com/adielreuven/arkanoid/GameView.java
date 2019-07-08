package com.adielreuven.arkanoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View
{
    //finals
    private static final int ROWS = 5;
    private static final int COLS = 7;

    // enums states
    private enum State {GET_READY, PLAYING, GAME_OVER};

    // objects
    private Ball ball;
    private Paddle paddle;
    private BrickCollection brickCollection;

    private float fx; // for finger touch location
    private boolean isPaddleMove;

    private int canvasWidth;
    private int canvasHeight;
    private Paint penInfo, penMsg;
    private int lives, score;

    // current state
    private State state;

    private MediaPlayer mp1, mp2;

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mp1 = MediaPlayer.create(getContext(), R.raw.collision);
        mp2 = MediaPlayer.create(getContext(), R.raw.jumping);
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawColor(Color.DKGRAY);// paint background
        drawInformation(canvas); // score and lives

        brickCollection.draw(canvas);
        paddle.draw(canvas);
        ball.draw(canvas);

        // checking game states
        switch (state)
        {
            case GET_READY:

                canvas.drawText("Click to PLAY!", canvasWidth/2, canvasHeight/2, penMsg);

                if( lives > 0)
                    init_Ball_Paddle(); // return ball and paddle to center of screen

                else if( lives == 0) // indicate to loss
                    initGame();

                break;

            case PLAYING:

                if(isPaddleMove == true)
                {
                    if( fx > canvasWidth/2 )
                        paddle.moveRight(canvasWidth);
                    else
                        paddle.moveLeft();
                }

                // move the jumpingBall
               ball.move(canvasWidth, canvasHeight);

                // check collision with paddle
               if( ball.collideWith(paddle) == true )
                   mp2.start();

               if( ball.isLostLives() == true )
                {
                    lives--;
                    if( lives == 0)
                    {
                        state = State.GAME_OVER; // loss
                    }
                    else
                        state = State.GET_READY;
                    break;
                }

                // check collision with any brick
                if(brickCollection.isCollideWithBrick(ball) == true)
                {
                    if(mp1.isPlaying() == false)
                        mp1.start();
                    score += 5*lives;
                }

                // check if no more bricks to hit, so indicate to win.
                if(brickCollection.getSize() == 0)
                    state = State.GAME_OVER; // player win
                break;

            case GAME_OVER:

                if( lives == 0 )
                    canvas.drawText("GAME OVER - You Loss!", canvasWidth/2, (canvasHeight/2)+20, penMsg);
                else if( brickCollection.getSize() == 0)
                    canvas.drawText("GAME OVER - You Win!", canvasWidth/2, (canvasHeight/2)+20, penMsg);
                break;
        }
        invalidate();
    }

    private void init_Ball_Paddle()
    {
        paddle.setStartX(canvasWidth/2 - Paddle.PADDLE_WIDTH/2);
        paddle.setStartY(canvasHeight - 50);

        ball.setCx(canvasWidth/2);
        ball.setCy(paddle.getStartY() - ball.getRadius() );

        ball.setDx(7);
        ball.setDy(-7);
    }

    private void drawInformation(Canvas canvas) // score and lives
    {
        penInfo.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Lives: " + lives , canvasWidth-40, 60, penInfo);

        penInfo.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Score: " + score, 20, 60, penInfo);
    }

    private void initGame()
    {
        state = State.GET_READY;

        lives = 3;
        score = 0;

        isPaddleMove = false;

        brickCollection = new BrickCollection(ROWS, COLS, canvasWidth);

        // create paddle on the bottom center of screen
        paddle = new Paddle(canvasWidth/2 - Paddle.PADDLE_WIDTH/2, canvasHeight - 50);

        // create jumping ball on the paddle in center of screen
        ball = new Ball(canvasWidth/2, paddle.getStartY()-20, 20);

        // init directions
        paddle.setDx(30);

        ball.setDx(7);
        ball.setDy(-7);

        // paint for info text
        penInfo = new Paint(Paint.ANTI_ALIAS_FLAG);
        penInfo.setColor(Color.YELLOW);
        penInfo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        penInfo.setTextSize(45);

        // paint for messages text
        penMsg = new Paint(Paint.ANTI_ALIAS_FLAG);
        penMsg.setTextAlign(Paint.Align.CENTER);
        penMsg.setColor(Color.GREEN);
        penMsg.setStyle(Paint.Style.STROKE);
        penMsg.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        penMsg.setTextSize(55);
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        fx = event.getX();
        switch (event.getAction())
        {
             case MotionEvent.ACTION_DOWN:
                if(state == State.GET_READY)
                    state = State.PLAYING;
                else
                {
                    if(state == State.PLAYING)
                        isPaddleMove = true;

                    else // indicate to game over
                    {
                        state = State.GAME_OVER;
                        initGame();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if( fx > canvasWidth/2 )
                    paddle.moveRight(canvasWidth);
                else
                    paddle.moveLeft();
                break;
            case MotionEvent.ACTION_UP:
                isPaddleMove = false;
                break;
        }
        invalidate();
        return true;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasWidth = w;
        canvasHeight = h;
        initGame();
    }

}// end GameView class
