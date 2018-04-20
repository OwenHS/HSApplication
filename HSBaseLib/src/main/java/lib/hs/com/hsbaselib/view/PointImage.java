package lib.hs.com.hsbaselib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import lib.hs.com.hsbaselib.R;

/**
 * Created by huangshuo on 17/9/13.
 */

public class PointImage extends ImageView{
    //要显示的数量数量
    private int num = 0;
    //圆圈的半径
    private float radius;
    //圆圈的颜色
    private int pointColor;
    //圆圈内数字的半径
    private float textSize;
    //圆圈内数字的颜色
    private int textColor;
    //右边和上边内边距
    private int paddingRight;
    private int paddingTop;
    //是否显示红点
    private boolean isShow = true;

    public PointImage(Context context) {
        super(context);
    }

    public PointImage(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PointImageStyle);

        radius = array.getDimension(R.styleable.PointImageStyle_circle_radius, 0);
        textColor = array.getColor(R.styleable.PointImageStyle_text_color, Color.WHITE);
        pointColor = array.getColor(R.styleable.PointImageStyle_circle_color, Color.RED);


    }

    public PointImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置显示的数量
    public void setNum(int num) {
        this.num = num;
        //重新绘制画布
        invalidate();
    }

    public void showRedPoint(boolean isShow) {
        this.isShow = isShow;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShow) {
            //初始化边距
            paddingRight = getPaddingRight();
            paddingTop = getPaddingTop();
            //初始化画笔
            Paint paint = new Paint();
            //设置抗锯齿
            paint.setAntiAlias(true);
            //设置颜色为红色
            paint.setColor(pointColor);
            //设置填充样式为充满
            paint.setStyle(Paint.Style.FILL);
            //画圆
            canvas.drawCircle(getWidth() - radius, radius, radius, paint);
            //设置颜色为白色
            paint.setColor(textColor);
            //设置字体大小
            paint.setTextSize(radius);
            //画数字
            if (num > 0) {
                canvas.drawText("" + (num < 99 ? num : 99),
                        num < 10 ? getWidth() - radius - textSize / 4
                                : getWidth() - radius - textSize / 2,
                        radius + textSize / 3 + paddingTop / 2, paint);
            }
        }

    }
}
