package com.edmodo.rangebar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

/**
 * Created by jeff on 11/27/17.
 */

public class PointIndicator {

    private final Paint mPaint;
    private float mX;
    private final float mY;
    private Bitmap bitmap = null;
    private boolean mShow = false;
    private float topPressed = 0;
    private float leftPressed = 0;

    private float mSize;

    private static final int DEFAULT_THUMB_RADIUS_DP = 16;

    PointIndicator(
            Context ctx,
            boolean show,
            int imageRes,
            float x, float y, int color) {
        this.mShow = show;

        final Resources res = ctx.getResources();

        mSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_THUMB_RADIUS_DP,
                res.getDisplayMetrics());

        // Initialize the paint, set values
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        mX = x;
        mY = y;

        Bitmap bitmapTemp = BitmapFactory.decodeResource(res, imageRes);
        if (bitmapTemp != null) {
            bitmap = Bitmap.createScaledBitmap(bitmapTemp, (int) mSize, (int) mSize, true);
            float mHalfWidthNormal = bitmap.getWidth() / 2f;
            float mHalfHeightNormal = bitmap.getHeight() / 2f;

            topPressed = mY - mHalfWidthNormal;
            leftPressed = mX - mHalfHeightNormal;
        } else { // try shape drawable
            bitmap = Bitmap.createScaledBitmap(getBitmap(res, imageRes), (int) mSize, (int) mSize, true);
            float mHalfWidthNormal = bitmap.getWidth() / 2f;
            float mHalfHeightNormal = bitmap.getHeight() / 2f;

            topPressed = mY - mHalfWidthNormal;
            leftPressed = mX - mHalfHeightNormal;
        }
    }

    private Bitmap getBitmap(Resources resources, int drawableRes) {
        Drawable drawable = resources.getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap((int) mSize, (int) mSize, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, (int) mSize, (int) mSize);
        drawable.draw(canvas);

        return bitmap;
    }

    public float getX() {
        return leftPressed;
    }

    /**
     * Draw the connecting line between the two thumbs.
     *
     * @param canvas the Canvas to draw to
     */
    void draw(Canvas canvas) {
        if (mShow) {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, leftPressed, topPressed, null);
            } else {
                canvas.drawCircle(mX, mY, mSize, mPaint);
            }
        }
    }
}