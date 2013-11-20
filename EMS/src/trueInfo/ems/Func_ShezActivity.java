package trueInfo.ems;

import trueInfo.util.SharedPrefsUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Func_ShezActivity extends Activity {

	TableRow more_page_row6;
	TableRow more_page_row4;
	TableRow more_page_row0;
	TableRow more_page_cpwd;
	TableRow more_page_row7;
	TableRow more_page_autolg;
	TextView more_page_autolg_text;
	private int newVerCode = 0;
	private String newVerName = "";
	private String VersionDescription = "";
	public ProgressDialog pBar;
	private Handler handler = new Handler();
	String uno;
	String pwd;
	CharSequence str;
	boolean isautoLoad;

	ProgressDialog pd = null; // ProgressDialog对象引用

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Intent intent = getIntent(); // 获得启动该Activity的Intent
		uno = SharedPrefsUtil.getValue(this, "CommunityID", "");

		setContentView(R.layout.main_more);

		isautoLoad = SharedPrefsUtil.getValue(getApplicationContext(),
				"autologin", false);

		more_page_autolg = (TableRow) findViewById(R.id.more_page_autolg);
		more_page_autolg_text = (TextView) findViewById(R.id.more_page_autolg_text);
		if (isautoLoad) {
			str = getString(R.string.autologin_isno);
			more_page_autolg_text.setText(str);
		} else {
			str = getString(R.string.autologin_isok);
			more_page_autolg_text.setText(str);
		}
		more_page_autolg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isautoLoad) {
					SharedPrefsUtil.putValue(getApplicationContext(),
							"autologin", false);
					str = getString(R.string.autologin_isok);
					more_page_autolg_text.setText(str);
					Toast.makeText(Func_ShezActivity.this, "取消自动登录修改成功",
							Toast.LENGTH_SHORT).show();// 输出提示消息
					isautoLoad = false;
				} else {
					SharedPrefsUtil.putValue(getApplicationContext(),
							"autologin", true);
					str = getString(R.string.autologin_isno);
					more_page_autolg_text.setText(str);
					Toast.makeText(Func_ShezActivity.this, "自动登录修改成功",
							Toast.LENGTH_SHORT).show();// 输出提示消息
					isautoLoad = true;
				}
			}
		});

		more_page_cpwd = (TableRow) findViewById(R.id.more_page_cpwd);
		more_page_cpwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Func_ShezActivity.this,
						ChangePWDActivity.class);
				intent.putExtra("uno", uno);

				startActivity(intent);
			}
		});

		more_page_row7 = (TableRow) findViewById(R.id.more_page_row7);
		more_page_row7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Func_ShezActivity.this,
						AboutActivity.class);

				startActivity(intent);

			}
		});
		more_page_row6 = (TableRow) findViewById(R.id.more_page_row6);
		more_page_row6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.i("mcm", "---------软件更新");
				// TODO Auto-generated method stub
				trueInfo.appManage.UpdateManager manager = new trueInfo.appManage.UpdateManager(
						Func_ShezActivity.this);
				// 检查软件更新
				manager.beginCheckUpdate();
			}
		});
		

	}

}
