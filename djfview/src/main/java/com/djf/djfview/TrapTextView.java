package com.djf.djfview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by djf on 2018/5/31.
 */

public class TrapTextView extends android.support.v7.widget.AppCompatTextView {


    private float strokWidth = 1;
    private int strokColor = Color.RED;
//    private int solidColor = Color.BLUE;
    private float leftTopXPadding = 0;
    private float leftTopYPadding = 0;

    private float rightTopXPadding = 0;
    private float rightTopYPadding = 0;

    private float leftBottomXPadding =0;
    private float leftBottomYPadding = 0;

    private float rightBottomXPadding = 0;
    private float rightBottomYPadding = 0;

    private Path clipPath;
    private Paint paintStrok, paintSolid;
    private int viewWidth;
    private int viewHeigh;

    private Drawable innerBackground;


    public TrapTextView(Context context) {
        super(context);
        initView(context, null);
    }

    public TrapTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewWidth = getWidth();
        viewHeigh = getHeight();
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TrapTextView);
            strokWidth = array.getDimension(R.styleable.TrapTextView_ttv_swrok_width, strokWidth);
            strokColor = array.getColor(R.styleable.TrapTextView_ttv_strok_color, strokColor);
            innerBackground = array.getDrawable(R.styleable.TrapTextView_ttv_innerBackground);
            leftBottomXPadding=array.getDimension(R.styleable.TrapTextView_ttv_leftBottomXPadding,0);
            leftBottomYPadding=array.getDimension(R.styleable.TrapTextView_ttv_leftBottomYPadding,0);
            leftTopXPadding=array.getDimension(R.styleable.TrapTextView_ttv_leftTopXPadding,0);
            leftTopYPadding=array.getDimension(R.styleable.TrapTextView_ttv_leftTopYPadding,0);
             rightBottomXPadding=array.getDimension(R.styleable.TrapTextView_ttv_rightBottomXPadding,0);
            rightBottomYPadding=array.getDimension(R.styleable.TrapTextView_ttv_rightBottomYPadding,0);
            rightTopXPadding=array.getDimension(R.styleable.TrapTextView_ttv_rightTopXPadding,0);
            rightTopYPadding=array.getDimension(R.styleable.TrapTextView_ttv_rightTopYPadding,0);

        }

        paintStrok = new Paint();
        paintStrok.setStrokeWidth(strokWidth);
        paintStrok.setColor(strokColor);
        paintStrok.setStyle(Paint.Style.STROKE);
        paintStrok.setAntiAlias(true);
        paintStrok.setFilterBitmap(true);
        paintSolid = new Paint();
//        paintSolid.setColor(solidColor);
        paintSolid.setStyle(Paint.Style.FILL);
        paintSolid.setAntiAlias(true);
        paintSolid.setFilterBitmap(true);
        clipPath = new Path();

        strokWidth = 0f;//好像不该添加偏移
    }

    @Override
    protected void onDraw(Canvas canvas) {
        clipPath.reset();
        clipPath.moveTo(leftTopXPadding + strokWidth, leftTopYPadding + strokWidth);
        clipPath.lineTo(viewWidth - rightTopXPadding - strokWidth, rightTopYPadding + strokWidth);
        clipPath.lineTo(viewWidth - rightBottomXPadding - strokWidth, viewHeigh - rightBottomYPadding - strokWidth);
        clipPath.lineTo(leftBottomXPadding + strokWidth, viewHeigh - leftBottomYPadding - strokWidth);
        clipPath.close();
        if (innerBackground instanceof ColorDrawable) {
            paintSolid.setColor(((ColorDrawable) innerBackground).getColor());
            canvas.drawPath(clipPath, paintSolid);
        } else if (innerBackground instanceof BitmapDrawable) {
            canvas.clipPath(clipPath);
            Bitmap bitmap = ((BitmapDrawable) innerBackground).getBitmap();
            Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF dsc = new RectF(0, 0, viewWidth, viewHeigh);
            canvas.drawBitmap(bitmap, src, dsc, paintSolid);
        }
//        else {
//            canvas.drawPath(clipPath, paintSolid);
//        }

        canvas.drawPath(clipPath, paintStrok);

//        canvas.drawLine(leftTopXPadding + strokWidth, leftTopYPadding + strokWidth
//                , viewWidth - rightTopXPadding - strokWidth, rightTopYPadding + strokWidth
//                , paintStrok
//        );
//        canvas.drawLine(viewWidth - rightTopXPadding - strokWidth, rightTopYPadding + strokWidth
//                , viewWidth - rightBottomXPadding - strokWidth, viewHeigh - rightBottomYPadding - +strokWidth
//                , paintStrok
//        );
//        canvas.drawLine(viewWidth - rightBottomXPadding - strokWidth, viewHeigh - rightBottomYPadding - +strokWidth,
//                leftBottomXPadding + strokWidth, viewHeigh - leftBottomYPadding - strokWidth
//                , paintStrok
//        );
//        canvas.drawLine(leftBottomXPadding + strokWidth, viewHeigh - leftBottomYPadding - strokWidth,
//                leftTopXPadding + strokWidth, leftTopYPadding + strokWidth,
//                paintStrok
//        );
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);
    }

//    private Drawable background;

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
    }

    @Override
    public boolean onPreDraw() {
        return super.onPreDraw();
    }
}