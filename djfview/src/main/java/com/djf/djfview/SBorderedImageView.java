package com.djf.djfview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by djf on 2018/5/23.
 */

public class SBorderedImageView extends AppCompatImageView {
    private int borderColor;
    private float borderWidth = 2;
    private Drawable drawable;
    private int mWidth;
    private int mHeight;
    private Path mPath;
    private Bitmap bitmap;
    private int shap = 1;
    private float[] radiusArray;
    private float leftTopCorner;
    private float rightTopCorner;
    private float rightBottomCornet;
    private float leftBottomCorner;
    private float corner;
    private RectF rect;
    private int scaleType = 1;

    public SBorderedImageView(Context context) {
        super(context);
        initView(context, null);
    }

    public SBorderedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SBorderedImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private Paint paint, borderPaint;

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SBorderedImageView);
            borderWidth = array.getDimension(R.styleable.SBorderedImageView_cborderWidth, 2);
            borderColor = array.getColor(R.styleable.SBorderedImageView_cborderColor, Color.GREEN);
            shap = array.getInt(R.styleable.SBorderedImageView_cshap, 1);
            leftTopCorner = array.getDimension(R.styleable.SBorderedImageView_cleftTopCorner, -1);
            leftBottomCorner = array.getDimension(R.styleable.SBorderedImageView_cleftBottomCorner, -1);
            rightTopCorner = array.getDimension(R.styleable.SBorderedImageView_crightTopCorner, -1);
            rightBottomCornet = array.getDimension(R.styleable.SBorderedImageView_crightBottomCornet, -1);
            corner = array.getDimension(R.styleable.SBorderedImageView_ccorner, 0);
            scaleType = array.getInt(R.styleable.SBorderedImageView_scaleType, 1);

            if (shap == 2) {
                leftBottomCorner=leftBottomCorner<0?corner:leftBottomCorner;
                leftTopCorner=leftTopCorner<0?corner:leftTopCorner;
                rightBottomCornet=rightBottomCornet<0?corner:rightBottomCornet;
                rightTopCorner=rightTopCorner<0?corner:rightTopCorner;

                radiusArray = new float[]{leftTopCorner, leftTopCorner, rightTopCorner,
                        rightTopCorner, rightBottomCornet, rightBottomCornet,
                        leftBottomCorner, leftBottomCorner};
            }

        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.TRANSPARENT);
        if (drawable == null) {
            drawable = getDrawable();
        }
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
    }


    /**
     * 绘制圆形图片
     *
     * @author caizhiming
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        if (bitmap != null) {
            bitmap = ((BitmapDrawable) getDrawable()).getBitmap();

            int bw = bitmap.getWidth();
            int bh = bitmap.getHeight();
            int dx = bw - mWidth;
            int dy = bh - mHeight;

            Log.d("onDraw", "onDraw: " + dx + "  " + dy);
            if (scaleType == 1) {
                dx = 0;
                dy = 0;
            } else {
                if (dx <= 0 && dy >= 0) {
                    dy = dy - dx;
                    dx = 0;
                }
                if (dy <= 0 && dx >= 0) {
                    dx = dx - dy;
                    dy = 0;
                }
                if (dx <= 0 && dy <= 0) {
                    dx = 0;
                    dy = 0;
                }
                if (dx >= 0 && dy >= 0) {

                }
            }

            int left = dx / 2;
            int top = dy / 2;
            int right = bw - dx / 2;
            int bottom = bh - dy / 2;

            Rect src = new Rect(left, top, right, bottom);
            RectF dsc = new RectF(0, 0, mWidth, mHeight);

            paint.reset();
            mPath.reset();
            if (shap == 2) {
                mPath.addRoundRect(rect, radiusArray, Path.Direction.CW);
            } else {
                mPath.addOval(rect, Path.Direction.CW);
//        mPath.addCircle( mWidth/2, mHeight/2,mWidth/2, Path.Direction.CW);
            }
            mPath.close();
            Bitmap finalBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);// 一张白纸位图
            Canvas mCanvas = new Canvas(finalBitmap);// 用指定的位图构造一个画布来绘制
            mCanvas.setDrawFilter(new PaintFlagsDrawFilter(0,
                    Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
            mCanvas.drawPath(mPath, paint);// 绘制Dst
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置转换模式（显示Scr与Dst交接的区域）
            mCanvas.drawBitmap(bitmap, src, dsc, paint);// 绘制Src
            canvas.drawBitmap(finalBitmap, 0, 0, null);
        } else {
            super.onDraw(canvas);
        }

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (borderWidth <= 0) {
            borderPaint.setColor(Color.TRANSPARENT);
        }
        canvas.drawPath(mPath, borderPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        rect = new RectF(borderWidth / 2, borderWidth / 2,
                mWidth - borderWidth / 2, mHeight - borderWidth / 2);
    }
}