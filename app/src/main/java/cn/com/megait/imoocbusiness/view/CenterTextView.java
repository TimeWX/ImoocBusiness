package cn.com.megait.imoocbusiness.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * @author TimeW
 * @function 换行居中显示的TextView
 * @date 2018/9/28
 */
public class CenterTextView extends AppCompatTextView {

    private StaticLayout staticLayout;
    private TextPaint textPaint;

    public CenterTextView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    private void init() {
        textPaint=new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(getTextSize());
        textPaint.setColor(getCurrentTextColor());
        staticLayout=new StaticLayout(getText(), textPaint, getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        staticLayout.draw(canvas);
    }
}
