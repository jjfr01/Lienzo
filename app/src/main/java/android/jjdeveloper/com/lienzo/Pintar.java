package android.jjdeveloper.com.lienzo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Pintar extends View {

    public static final int COLOR = Color.BLACK;
    private static final float TOLERANCIA = 4;
    private ArrayList<Pincel> trazadas = new ArrayList<>();
    private float mX, mY;
    private Path mPath;
    private Paint paint;
    private int currentColor;
    private int fondoColor;
    private int ancho_pincel;
    private boolean blur;
    private MaskFilter mBlur;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    public Pintar(Context context) {
        this(context, null);
    }

    public Pintar(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(COLOR);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setXfermode(null);
        paint.setAlpha(0xff);

        mBlur = new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);
    }

    public void init(DisplayMetrics metrics, int SIZE_PINCEL, int DEFAULT_COLOR) {
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = DEFAULT_COLOR;
        ancho_pincel = SIZE_PINCEL;
        invalidate();
    }

    public void pincel_normal() {
        blur = false;
    }

    public void pincel_blur() {
        blur = true;
    }

    public void clear() {
        trazadas.clear();
        fondoColor = Color.WHITE;
        pincel_normal();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mCanvas.drawColor(fondoColor);
        for (Pincel pp : trazadas) {
            paint.setStrokeWidth(pp.strokeWidth);
            paint.setMaskFilter(null);
            paint.setColor(pp.color);

            if (pp.blur) {
                paint.setMaskFilter(mBlur);
            }

            mCanvas.drawPath(pp.path, paint);

        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y) {
        mPath = new Path();
        Pincel pincel = new Pincel(currentColor, blur, ancho_pincel, mPath);
        trazadas.add(pincel);

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOLERANCIA || dy >= TOLERANCIA) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                touchUp();
                invalidate();
                break;
        }

        return true;
    }
}
