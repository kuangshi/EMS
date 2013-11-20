package trueInfo.util;

import android.content.Context;

import android.text.TextPaint;

import android.util.AttributeSet;
import android.view.Display;

import android.view.KeyEvent;

import android.view.WindowManager;

import android.widget.EditText;

public class MyEditor extends EditText {

	int screenWidth = 0;

	int screenHeight = 0;

	int currentHeight = 0;

	Context context = null;

	public MyEditor(Context context, AttributeSet attrs) {

		super(context, attrs);

		this.context = context;

		currentHeight = getHeight();

		WindowManager windowManager = (WindowManager) this.context

		.getSystemService(Context.WINDOW_SERVICE);

		Display display = windowManager.getDefaultDisplay();

		// 取得屏幕宽度和高度

		screenWidth = display.getWidth();

		screenHeight = display.getHeight();

		//Log.i("moa-width",String.valueOf(screenWidth));
		//Log.i("moa-height",String.valueOf(screenHeight));
		
		
		setScrollBarStyle(DRAWING_CACHE_QUALITY_AUTO);

		/*
		 * Rect rect = new Rect();
		 * 
		 * Paint p = new Paint();
		 * 
		 * p.setTypeface(getTypeface());
		 * 
		 * p.getTextBounds("A", 0, 1, rect);
		 * 
		 * fontWidth = rect.width();
		 */

	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		TextPaint paint = getPaint();

		float len = paint.measureText(getText().toString());

		// 计算得到当前应该有几行

		int line = ((int) len / screenWidth + 1);

		getEllipsize();

		setFrame(0, 0, screenWidth, line * 60);

		// setHeight(line*60) ;

		// setMarqueeRepeatLimit(line) ;

		// setMaxHeight(line*60) ;

		// setLines(line) ;

		// setBackgroundColor(Color.WHITE) ;

		return false;

	}

}
