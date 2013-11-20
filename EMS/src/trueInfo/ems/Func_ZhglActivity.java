package trueInfo.ems;

import java.util.ArrayList;
import java.util.HashMap;

import trueInfo.util.SharedPrefsUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Func_ZhglActivity extends Activity {

	

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);


		setContentView(R.layout.func_zhgl_activity);

		
	}
	
	public void imgClick(View v) {
		switch(v.getId()) {
		case R.id.zhgl_img1:
			Toast.makeText(this, "YOU click me", Toast.LENGTH_LONG).show();
			break;
		case R.id.zhgl_img2:
			Toast.makeText(this, "YOU click me", Toast.LENGTH_LONG).show();
			break;
		case R.id.zhgl_img3:
			Toast.makeText(this, "YOU click me", Toast.LENGTH_LONG).show();
			break;
		case R.id.zhgl_img4:
			Toast.makeText(this, "YOU click me", Toast.LENGTH_LONG).show();
			break;
		case R.id.zhgl_img5:
			Toast.makeText(this, "YOU click me", Toast.LENGTH_LONG).show();
			break;
			
		}
		
	}

}
