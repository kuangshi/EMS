package trueInfo.ems;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingActivity extends Activity {

	ImageView iv;
	TranslateAnimation bb;
	TextView tv;
	int distance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 取消状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		this.setContentView(R.layout.loading);
		iv = (ImageView) this.findViewById(R.id.lodingImg);
		tv = (TextView) this.findViewById(R.id.welcomeMsg);

		AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
		aa.setDuration(2000);

		distance = dip2px(this, 210);

		bb = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE,
				0, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -distance / 2);
		bb.setFillAfter(true);
		bb.setDuration(800);

		iv.startAnimation(aa);

		aa.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

				iv.startAnimation(bb);

				TranslateAnimation cc = new TranslateAnimation(
						Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0,
						Animation.ABSOLUTE, 0, Animation.ABSOLUTE,
						-distance / 2);
				cc.setFillAfter(true);
				cc.setDuration(800);
				tv.startAnimation(cc);
				// tv.setVisibility(View.GONE);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

		});

		bb.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

				Intent it = new Intent(LoadingActivity.this, LoginActivity.class);
				startActivity(it);
				overridePendingTransition(R.anim.in, R.anim.out);
				// overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
				finish();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

		});

	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
