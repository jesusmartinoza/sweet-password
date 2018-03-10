package mx.jesusmartinoza.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jesusmartinoza on 10/03/18.
 */

public class EyeView extends View {

    // UI
    private Paint paint;
    private Path lashline;
    private Path waterLine;

    // Draw stuff
    private static final int EYE_HEIGHT = 58;
    private static final int INITIAL_Y = 25;
    private float anchorX; // Anchor for control points of paths
    private float ECR; // Eye Closed Ratio
    private int irisColor;
    private int eyeColor;

    /**
     * Mandatory constructor
     * @param context Context
     */
    public EyeView(Context context) {
        super(context);
        init();
    }

    /**
     * Mandatory constructor
     * @param context Context
     */
    public EyeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Mandatory constructor
     * @param context Context
     */
    public EyeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * Init component with a closed eye. And set some constant data of {paint}
     */
    private void init(){
        paint = new Paint();
        ECR = 1.0f;
        anchorX = 20;

        paint.setAntiAlias(true);
        paint.setStrokeWidth(8);
    }

    /**
     * First draw lash line and waterline using the same color.
     *
     * A bezier curve with a dynamic control points is used to achieve
     * the open/close animation of eye.
     *
     * To draw we are using a Bezier curve, reference:
     * http://blogs.sitepointstatic.com/examples/tech/canvas-curves/bezier-curve.html
     *
     * TODO: Preallocate and reuse paths
     * @param canvas Canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        lashline = new Path();
        waterLine = new Path();

        paint.setColor(eyeColor);
        paint.setStyle(Paint.Style.STROKE);

        // Lashline
        lashline.moveTo(anchorX, canvas.getHeight() / 2);
        lashline.cubicTo(canvas.getWidth() / 2 - anchorX, INITIAL_Y + EYE_HEIGHT * ECR,
                canvas.getWidth() / 2 + anchorX,
                INITIAL_Y + EYE_HEIGHT * ECR, canvas.getWidth() - anchorX, canvas.getHeight() / 2);
        canvas.drawPath(lashline, paint);

        // Waterline
        // subtract 2 units in Y axis to overlap paths
        waterLine.moveTo(anchorX, canvas.getHeight() / 2 - 2);
        waterLine.cubicTo(canvas.getWidth() / 2 - anchorX,canvas.getHeight() - INITIAL_Y,
                canvas.getWidth() / 2 + anchorX,
                canvas.getHeight() - INITIAL_Y, canvas.getWidth() - anchorX, canvas.getHeight() / 2 - 2);
        canvas.drawPath(waterLine, paint);

        // Iris
        // start draw only when eye is half open
        if(ECR < 0.5) {
            paint.setColor(irisColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 10 * (1.0f - ECR), paint);
        }
    }

    /*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
                        G  E  T  T  E  R  S    &    S  E  T  T  E  R  S
     =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*/
    /**
     * Set Eye Closed Ratio.
     * 0.0 - Fully opened eye
     * 1.0 - Closed eye
     * @param eyeOpenRatio new Open Ratio
     */
    public void setEOR(float eyeOpenRatio) {
        ECR = eyeOpenRatio;
    }

    /**
     * Set iris color
     * @param color new color
     */
    public void setIrisColor(int color) {
        irisColor = color;
    }

    /**
     * Set border color of eye
     * @param color new color
     */
    public void setEyeColor(int color) {
        eyeColor = color;
    }
}