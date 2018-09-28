package cn.com.megait.imoocbusiness.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * @author TimeW
 * @function
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
