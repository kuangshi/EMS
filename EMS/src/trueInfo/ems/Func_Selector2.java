package trueInfo.ems;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import trueInfo.services.LoginServices;
import trueInfo.util.BadgeView;
import trueInfo.util.PublicClass;
import trueInfo.util.SharedPrefsUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Func_Selector2 extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.func_selector2);

		// 初始化功能菜单
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub

	}

	public void click(View v) {
		switch (v.getId()) {
		case R.id.ggtz:
			Intent intent_ggtz = new Intent();
			intent_ggtz.setClass(Func_Selector2.this, NoticeListActivity.class);
			// i.putExtra("uno", uno); // 设置Intent的Extra字段
			startActivity(intent_ggtz);
			break;
		case R.id.grxx:
			Toast.makeText(this, "暂时无法查看", Toast.LENGTH_SHORT).show();
			Intent intent_grxx = new Intent();
			//intent_grxx.setClass(Func_Selector2.this, Person_InfoActivity.class);
			//startActivity(intent_grxx);
			break;
		case R.id.yhgl:
			Intent intent_yhgl = new Intent();
			intent_yhgl.setClass(Func_Selector2.this, User_ManageActivity.class);
			// i.putExtra("uno", uno); // 设置Intent的Extra字段
			startActivity(intent_yhgl);
			break;
		case R.id.sjpf:
			Intent intent_sjpf = new Intent();
			intent_sjpf.setClass(Func_Selector2.this, SJPF_ManageActivity.class);
			// i.putExtra("uno", uno); // 设置Intent的Extra字段
			startActivity(intent_sjpf);
			break;
		}
	}

}