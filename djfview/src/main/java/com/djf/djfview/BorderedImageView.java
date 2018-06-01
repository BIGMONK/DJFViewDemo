package com.djf.djfview;

/**
 * Created by djf on 2016/8/29.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class BorderedImageView extends AppCompatImageView {

    private float borderWidth = 2, corner, rightBottomCornet,
            rightTopCorner, leftBottomCorner, leftTopCorner;
    private int borderColor = Color.WHITE;
    private Path clipPath;
    private TypedArray typedArray;
    private int w;
    private int h;
    private Paint borderPaint;
    private int shap = 1;
    private float[] radiusArray;
    private RectF rect;

    public BorderedImageView(Context context) {
        super(context, null);
    }

    public BorderedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.BorderedImageView);
            borderWidth =2* typedArray.getDimension(R.styleable.BorderedImageView_borderWidth, 0);
            borderColor = typedArray.getColor(R.styleable.BorderedImageView_borderColor, Color.WHITE);
            shap = typedArray.getInteger(R.styleable.BorderedImageView_shap, 1);
            leftTopCorner = typedArray.getDimension(R.styleable.BorderedImageView_leftTopCorner, -1);
            leftBottomCorner = typedArray.getDimension(R.styleable.BorderedImageView_leftBottomCorner, -1);
            rightTopCorner = typedArray.getDimension(R.styleable.BorderedImageView_rightTopCorner, -1);
            rightBottomCornet = typedArray.getDimension(R.styleable.BorderedImageView_rightBottomCornet, -1);
            corner = typedArray.getDimension(R.styleable.BorderedImageView_corner, 0);

            if (shap == 2) {
                leftBottomCorner=leftBottomCorner<0?corner:leftBottomCorner;
                leftTopCorner=leftTopCorner<0?corner:leftTopCorner;
                rightBottomCornet=rightBottomCornet<0?corner:rightBottomCornet;
                rightTopCorner=rightTopCorner<0?corner:rightTopCorner;

                radiusArray = new float[]{leftTopCorner, leftTopCorner, rightTopCorner,
                        rightTopCorner, rightBottomCornet, rightBottomCornet,
                        leftBottomCorner, leftBottomCorner};
            }

            typedArray.recycle();
        }

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        clipPath = new Path();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        w = this.getWidth();
        h = this.getHeight();
              rect = new RectF(borderWidth , borderWidth ,
                w - borderWidth , h - borderWidth );
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        clipPath.reset();
        if (shap == 2) {
            if (clipPath != null) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                clipPath.addRoundRect(rect, radiusArray, Path.Direction.CW);
//                } else {
//                    clipPath.addRect(0, 0, w, h, Path.Direction.CW);
//                }
                clipPath.close();
                canvas.clipPath(clipPath);
            }
        } else {
            if (clipPath != null) {
                clipPath.addOval(rect, Path.Direction.CW);
                clipPath.close();
                canvas.clipPath(clipPath);
            }
        }

        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (borderWidth<=0){
            borderPaint.setColor(Color.TRANSPARENT);
        }
        canvas.drawPath(clipPath, borderPaint);

    }
}
