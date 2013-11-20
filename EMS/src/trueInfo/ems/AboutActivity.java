package trueInfo.ems;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutActivity extends Activity {

	LinearLayout iv;
	TranslateAnimation bb;
	AlphaAnimation aa;
	Button btnBack;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 取消状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		this.setContentView(R.layout.about);
		iv = (LinearLayout) this.findViewById(R.id.welcomeBox);
		btnBack=(Button)findViewById(R.id.btnBack);

		aa = new AlphaAnimation(0.1f, 1.0f);
		aa.setDuration(800);

		bb = new TranslateAnimation(0, 0, 200, -120);
		bb.setFillAfter(true);
		bb.setDuration(3000);

//		iv.startAnimation(aa);
		iv.startAnimation(bb);
		
		
		bb.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

				
				btnBack.setVisibility(View.VISIBLE);
//				TranslateAnimation cc=new TranslateAnimation(0, 0, 0, -150);
//				cc.setFillAfter(true);
//				cc.setDuration(1500);
				btnBack.startAnimation(aa);
//				tv.setVisibility(View.GONE);

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
		
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Log.i("mcm", "---about---");
				finish();
			}
		});

	}

}
