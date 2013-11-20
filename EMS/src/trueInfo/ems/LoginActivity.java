package trueInfo.ems;

import trueInfo.services.LoginServices;
import trueInfo.util.SharedPrefsUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends Activity {

	TextView tv;
	ProgressDialog pd;

	ImageView logoimg;
	Button btn_login;
	Button btnReg;

	EditText ed_username;
	EditText ed_password;
	CheckBox ckb_rempwd;
	CheckBox ckb_autologin;
	String username;
	String password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 初始化页面状态�?—设置窗体无标题栏，设置窗体全屏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.login);

		// 初始化view
		IntiView();

		// 自动登录
		if (ckb_autologin.isChecked()) {
			// 显示进度度			
			pd = ProgressDialog.show(LoginActivity.this, "请稍侯", "正在连接服务...",
					true, true);
			LoginAction();
		}

		// 注册按钮事件

		btn_login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 显示进度			
				pd = ProgressDialog.show(LoginActivity.this, "请稍侯", "正在连接服务...",
						true, true);
				LoginAction();
			}
		});

		btnReg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				exitApp();
			}
		});

	}

	/**
	 * �?��应用程序
	 */
	private void exitApp() {
		new AlertDialog.Builder(LoginActivity.this)
				.setTitle("提示")
				.setMessage("确认退出么？")
				.setIcon(R.drawable.alert_icon)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						android.os.Process.killProcess(android.os.Process
								.myPid()); // 结束进程，�?出程�?					
						}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).show();
	}

	/**
	 * 初始化View状�?
	 */
	private void IntiView() {

		// 获取控件
		btn_login = (Button) findViewById(R.id.btnLogin);
		btnReg = (Button) findViewById(R.id.btnReg);
		ed_username = (EditText) findViewById(R.id.etUid);
		ed_password = (EditText) findViewById(R.id.etPwd);
		ckb_rempwd = (CheckBox) findViewById(R.id.cbRemember);
		ckb_autologin = (CheckBox) findViewById(R.id.cbAutoLoad);

		// 获取存储的用户名和密码	
		username = SharedPrefsUtil.getValue(this, "username", "");
		password = SharedPrefsUtil.getValue(this, "password", "");

		// 获取保存密码和自动登录设置		
		ckb_rempwd.setChecked(SharedPrefsUtil.getValue(this, "rempwd", true));
		ckb_autologin.setChecked(SharedPrefsUtil.getValue(this, "autologin",false));

		// 填充存储的用户名和密码	
		ed_username.setText(username);
		if (ckb_rempwd.isChecked()) {
			ed_password.setText(password);
		}

	}

	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:

				pd.dismiss();

				// Toast.makeText(LoginActivity.this, "请输入帐号或密码!",
				// Toast.LENGTH_SHORT).show();// 输出提示消息

				new AlertDialog.Builder(LoginActivity.this)
						.setIcon(
								getResources().getDrawable(
										R.drawable.login_error_icon))
						.setTitle("登录错误").setMessage("用户名或者密码不能为空，\n请输入后再登录！")
						.create().show();

				break;

			case 1:

				pd.dismiss();

				new AlertDialog.Builder(LoginActivity.this)
						.setIcon(
								getResources().getDrawable(
										R.drawable.login_error_icon))
						.setTitle("登录错误").setMessage("用户名或者密码不正确，\n请检查后重新输入")
						.create().show();

				// Toast.makeText(LoginActivity.this, "用户名或密码不正�?,
				// Toast.LENGTH_SHORT).show();// 输出提示消息

				break;
			case 2:

				pd.dismiss();
				
				SharedPrefsUtil.putValue(getApplicationContext(),
						"CommunityID", LoginServices.GetCommunityID());
				
				SharedPrefsUtil.putValue(getApplicationContext(),
						"OPID", LoginServices.GetNBBM());
				
				// 转到功能面板
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				// intent.putExtra("uno", username);
				intent.putExtra("uno", "0001000100010001000100010001");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();

			}
			super.handleMessage(msg);
		}
	};

	/**
	 * 登录操作
	 */
	private void LoginAction() {

		// 优先�?��网络，无网络直接给出提示�?		
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			// do nothing
		} else {

			pd.dismiss();

			new AlertDialog.Builder(LoginActivity.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("网络错误").setMessage("亲，好像无互联网连接哦！").create()
					.show();

			return;
		}

		username = ed_username.getText().toString();
		password = ed_password.getText().toString();

		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				try {

					if ("".equals(username) || "".equals(password)) { // 判断输入是否为空
						/*
						 * 测试 原数据为0 �?myHandler.sendEmptyMessage(0); return;
						 */
						myHandler.sendEmptyMessage(0);

						return;
					}

					// LoginServices loginService = new LoginServices();

					if (LoginServices.LoginCheck(username, password)) {

						// 通过验证后保存用户名和密码，用户不�?记住密码就清除密码记�?						
						SharedPrefsUtil.putValue(getApplicationContext(),
								"username", username);
						if (ckb_rempwd.isChecked()) {
							SharedPrefsUtil.putValue(getApplicationContext(),
									"password", password);
							SharedPrefsUtil.putValue(getApplicationContext(),
									"rempwd", true);
						} else {
							SharedPrefsUtil.removeValue(
									getApplicationContext(), "password");
							SharedPrefsUtil.putValue(getApplicationContext(),
									"rempwd", false);
						}
						// 保存自动登录设置
						if (ckb_autologin.isChecked()) {
							SharedPrefsUtil.putValue(getApplicationContext(),
									"autologin", true);
						} else {
							SharedPrefsUtil.putValue(getApplicationContext(),
									"autologin", false);
						}

						myHandler.sendEmptyMessage(2);

					} else{
						myHandler.sendEmptyMessage(1);
					}

					Looper.loop();
					Looper.myLooper().quit();
					return;

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
