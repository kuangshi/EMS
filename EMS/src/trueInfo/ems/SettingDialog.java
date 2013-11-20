package trueInfo.ems;

import java.io.File;

import trueInfo.util.FileManager;
import trueInfo.util.SharedPrefsUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingDialog extends Activity {
	// private MyDialog dialog;
	private LinearLayout layout;

	boolean autoload = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_dialog);
		// dialog=new MyDialog(this);
		layout = (LinearLayout) findViewById(R.id.main_dialog_layout);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "��ʾ����������ⲿ�رմ��ڣ�",
						Toast.LENGTH_SHORT).show();
			}
		});

		// *******************�Զ���½����*****************************
		TextView tvAutoload = (TextView) findViewById(R.id.btnAutoLoad);
		if (SharedPrefsUtil.getValue(this, "autologin", false)) {
			autoload = true;
			tvAutoload.setText("ȡ���Զ���½");

		} else {
			autoload = false;
			tvAutoload.setText("�����Զ���½");

		}

		tvAutoload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (autoload) {
					SharedPrefsUtil.putValue(getApplicationContext(),
							"autologin", false);
				} else {
					SharedPrefsUtil.putValue(getApplicationContext(),
							"autologin", true);
				}

				Toast.makeText(getApplicationContext(), "��ʾ�����óɹ���",
						Toast.LENGTH_SHORT).show();

				finish();
			}
		});

		// *******************����������*****************************
		TextView tvCheckUpdate = (TextView) findViewById(R.id.btnCheckUpdate);

		tvCheckUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub

				trueInfo.appManage.UpdateManager manager = new trueInfo.appManage.UpdateManager(
						SettingDialog.this);
				// ����������
				manager.beginCheckUpdate();

			}
		});

		// *******************�������ļ�*****************************
		TextView tvClearFile = (TextView) findViewById(R.id.btnClearFiles);

		tvClearFile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub

				File file = new File(Environment.getExternalStorageDirectory()
						+ "/moa");

				FileManager.deleteFile(file);

				Toast.makeText(getApplicationContext(), "��ʾ������ɹ���",
						Toast.LENGTH_SHORT).show();

				finish();
			}
		});

		// *******************�˳�ϵͳ*****************************

		TextView tvExit = (TextView) findViewById(R.id.btnExitApp);

		tvExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SettingDialog.this, Exit.class);
				startActivity(intent);

				finish();
			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

}