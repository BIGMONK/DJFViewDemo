# DJFView

#1.0.4  
修复TrapTextView文字被覆盖的异常

#1.0.3

##TrapTextView
修复自定义背景和原背景冲突，环线偏移调整

#1.0.2  

增加一个自定义TextView，内部可添加闭环线条，并可在环线内添加背景  

##TrapTextView  

![Image text](https://github.com/BIGMONK/DJFViewDemo/blob/master/002.jpg)


#1.0.1
##GifImageView
自定义gif图片显示控件

支持gif图缩放属性

app:GifScaleType="fixXY"  拉伸填充

app:GifScaleType="center"   居中缩小

app:GifScaleType="centerCrop"   居中拉伸裁切

###控件

        <com.djf.djfview.GifImageView
            android:id="@+id/giv"
            android:layout_width="200dp"
            android:layout_height="200dp"
            android:background="@color/colorAccent"
            app:GifScaleType="fixXY"
            app:gif_src="@drawable/meiiz" />

        <com.djf.djfview.GifImageView
            android:id="@+id/giv2"
            android:layout_width="200dp"
            android:layout_height="200dp"
            android:background="@color/colorPrimaryDark"
            app:GifScaleType="center"
            app:gif_src="@drawable/meiiz" />

        <com.djf.djfview.GifImageView
            android:id="@+id/giv3"
            android:layout_width="200dp"
            android:layout_height="200dp"
            android:background="@color/colorAccent"
            app:GifScaleType="centerCrop"
            app:gif_src="@drawable/meiiz" />

        <com.djf.djfview.GifImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@color/colorPrimaryDark"
            app:gif_src="@drawable/meiiz" />
            
###效果图

![Image text](https://github.com/BIGMONK/DJFViewDemo/blob/master/001.gif)






##BorderedImageView和SBorderedImageView


两个带边框的ImageView，支持自定义形状：圆形和矩形（支持设置圆角），支持设置边框颜色和宽度
1.BorderedImageView  没有抗锯齿效果
2.SBorderedImageView  有抗锯齿效果

###控件设置

###效果图同上
