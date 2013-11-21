package trueInfo.ems;

import android.app.ProgressDialog;
import android.app.TabActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import trueInfo.util.CustomTabHost;
import trueInfo.util.SharedPrefsUtil;


public class MainActivity extends TabActivity implements OnTabChangeListener,OnGestureListener{

	public static MainActivity instance = null;
	
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	
	private TabWidget tabWidget;
	
	int currentView = 0;
	private static int maxTabIndex = 4;

	public CustomTabHost mth;
	
	static final int MENU_SEARCH = 0;
	static final int MENU_EXIT = 1; // static is invisible

	String uno = null;

	private TextView tvTitle;

	public RadioGroup mainbtGroup;
	ProgressDialog pd;

	public void init() {
		Log.d("maintab", "maintab_MainActivity------init");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("maintab", "maintab_MainActivity------onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("maintab", "maintab_MainActivity------onStop");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("maintab", "maintab_MainActivity------onStart");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		instance=this;
		
		Intent intent = getIntent();
		uno=SharedPrefsUtil.getValue(this, "CommunityID", "");

//		setTheme(R.style.CustomTheme);
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		this.setContentView(R.layout.container);
		
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
//				R.layout.title_bar);

			
		mth = (CustomTabHost) findViewById(android.R.id.tabhost);
		tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mth.setOnTabChangedListener(this);
		
		createTab1();
		createTab2();
		createTab3();
		createTab4();
		
		gestureDetector = new GestureDetector(this);
		new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		};
		
	}
	
	
	public void createTab1() {
		TabHost.TabSpec localTabSpec = mth.newTabSpec("0");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator, null);
		ImageView localImageView = (ImageView) localView.findViewById(R.id.icon);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localImageView.setImageResource(R.drawable.icon_1_n);
		localTextView.setText("首页");
		Intent localIntent = new Intent(this, Func_Selector.class);
		localIntent.putExtra("uno", uno);
		localTabSpec.setIndicator(localView).setContent(localIntent);
		mth.addTab(localTabSpec);
	}
	
	private void createTab2() {
		TabHost.TabSpec localTabSpec1 = mth.newTabSpec("1");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator, null);
		ImageView localImageView = (ImageView) localView.findViewById(R.id.icon);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localImageView.setImageResource(R.drawable.icon_3_n);
		localTextView.setText("学生管理");
		TabHost.TabSpec localTabSpec2 = localTabSpec1.setIndicator(localView);
		Intent localIntent = new Intent(this, Func_XsglActivity.class);
		localIntent.putExtra("uno", uno);
		localTabSpec2.setContent(localIntent);
		mth.addTab(localTabSpec1);
		
	}

	private void createTab3() {
		TabHost.TabSpec localTabSpec1 = mth.newTabSpec("2");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator, null);
		ImageView localImageView = (ImageView) localView.findViewById(R.id.icon);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localImageView.setImageResource(R.drawable.icon_2_n);
		localTextView.setText("综合管理");
		TabHost.TabSpec localTabSpec2 = localTabSpec1.setIndicator(localView);
		Intent localIntent = new Intent(this, Func_ZhglActivity2.class);
		localIntent.putExtra("uno", uno);
		localTabSpec2.setContent(localIntent);
		mth.addTab(localTabSpec1);
	}

	private void createTab4() {
		TabHost.TabSpec localTabSpec1 = mth.newTabSpec("3");
		View localView = getLayoutInflater().inflate(R.layout.tab_indicator, null);
		ImageView localImageView = (ImageView) localView
				.findViewById(R.id.icon);
		TextView localTextView = (TextView) localView.findViewById(R.id.title);
		localImageView.setImageResource(R.drawable.icon_6_n);
		localTextView.setText("设置");
		TabHost.TabSpec localTabSpec2 = localTabSpec1.setIndicator(localView);
		Intent localIntent = new Intent(this, Func_ShezActivity.class);
		localIntent.putExtra("uno", uno);
		localTabSpec2.setContent(localIntent);
		mth.addTab(localTabSpec1);
	}


	@Override
	public void onTabChanged(String tabId) {
		//tabId值为要切换到的tab页的索引位置
		int tabID = Integer.valueOf(tabId);
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			if (i == tabID) {
				tabWidget.getChildAt(Integer.valueOf(i));
			} else {
				tabWidget.getChildAt(Integer.valueOf(i));
			}
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event)) {
			event.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(event);
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		Log.i("maintab", "maintab_MainActivity------onDown");
		return false;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("maintab", "maintab_MainActivity------onDestroy");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("maintab", "maintab_MainActivity------onResume");

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		try {

			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
				return false;

			// right to left swipe
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

				
				currentView = mth.getCurrentTab() + 1;
				if (currentView >= mth.getTabCount()) {
					currentView = 0;
				}
				
				mth.setCurrentTab(currentView);

			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			
				currentView = mth.getCurrentTab() - 1;
				if (currentView < 0) {
					currentView = mth.getTabCount() - 1;
				}
				
				mth.setCurrentTab(currentView);

				
			}
		} catch (Exception e) {

			Log.i("moa", "moa:错误出现了：" + e.toString());

		}
		return false;
	}
	
	@Override
	public void onLongPress(MotionEvent e) {
		
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY) {
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
	
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			
//			pressAgainExit();
			Intent intent = new Intent();
        	intent.setClass(MainActivity.this,Exit.class);
        	startActivity(intent);
	
			
			return false;
		}
		return super.dispatchKeyEvent(event);
	}
	
	/** 
	 * 再按一次退出程序。 
	 */  
//	Exit exit = new Exit();
//	private void pressAgainExit() {  
//	    if (exit.isExit()) {  
//	        finish();  
//	    } else {  
//	        Toast.makeText(getApplicationContext(), "再按一次退出程序",  
//	                1000).show();  
//	        exit.doExitInOneSecond();  
//	    }  
//	}  

	
	
	//设置标题栏右侧按钮的作用
	public void btnmainright(View v) {  
		
		Intent intent = new Intent (MainActivity.this,SettingDialog.class);			
		startActivity(intent);	
		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
      }  	
}
