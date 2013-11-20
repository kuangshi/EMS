package trueInfo.ems;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import trueInfo.services.LoginServices;
import trueInfo.services.WebServices;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class User_ManageActivity extends Activity {
	
	private Button btn_ok,btn_tel,btn_qq,btn_clear,reback_btn;
	private EditText txtID;
	private TextView txtName,txtTel,txtQQ;
	private LinearLayout personInfo;
	
	private ProgressDialog pd;
	private List<String[]> list = new ArrayList<String[]>();
	
	private String zjhm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.user_manage_activity);
		
		getInfo();
		initActivity();
	}

	private void initActivity() {
		// TODO Auto-generated method stub
		btn_ok = (Button)findViewById(R.id.btn_ok);
		btn_tel = (Button)findViewById(R.id.btn_tel);
		btn_qq = (Button)findViewById(R.id.btn_qq);
		reback_btn = (Button)findViewById(R.id.reback_btn);
		
		txtID = (EditText)findViewById(R.id.txtId);
		
		txtName = (TextView)findViewById(R.id.txtName);
		txtTel = (TextView) findViewById(R.id.txtTel);
		txtQQ = (TextView) findViewById(R.id.txtQQ);
		
		personInfo = (LinearLayout)this.findViewById(R.id.personInfo);
		personInfo.setVisibility(View.INVISIBLE);
		
	}

	private void getInfo() {
		// TODO Auto-generated method stub
		
	}
	
	private void btn_back(View v) {
		finish();
	}
	
	public void btn_click(View v){
		
		switch(v.getId())  {
		
		/*通过号码查询数据*/
		case R.id.btn_ok:
			/*获得证件号码*/
			zjhm = txtID.getText().toString();
			//
			getData();
			
			break;
			
			/*修改添加tel*/
		case R.id.btn_tel:
			Intent intent = new Intent();
			intent.putExtra("NBBM", list.get(0)[0]);
			intent.putExtra("BMMC", "JWGL_XSGL_XSXX");
			intent.putExtra("XGZD", "LXDH");

			intent.setClass(User_ManageActivity.this, DocDealLXFSActivity.class);
			// startActivity(intent);
			startActivityForResult(intent, R.layout.doc_deal);
			break;
			
			/*修改添加qq*/
		case R.id.btn_qq:
			Intent intent_qq = new Intent();
			intent_qq.putExtra("NBBM", list.get(0)[0]);
			intent_qq.putExtra("BMMC", "JWGL_XSGL_XSXX");
			intent_qq.putExtra("XGZD", "QQ");

			intent_qq.setClass(User_ManageActivity.this, DocDealLXFSActivity.class);
			// startActivity(intent);
			startActivityForResult(intent_qq, R.layout.doc_deal);
			break;
			/*清除用户SIM数据*/
		case R.id.btn_clear:
			String code = "0202";
			String info = "{\"root\":[{\"TableName\":\"JWGL_XSGL_XSXX\",\"NBBM\":\""
					+ list.get(0)[0]
					+ "\",\"SJXL\":\"\"}]}";
			String sNum = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
			String yhnm = "ED5B2CD1643A40CFB05AE2FB1F1CEC9D";
			try{
				pd = ProgressDialog.show(this, "请稍候", "正在处理...");
				new WebServices().mend_data(code, info, sNum, yhnm);
				myHander.sendEmptyMessage(3);
			}catch (Exception e){
				System.out.println("失败");
				System.out.println(e);
				e.printStackTrace();
				myHander.sendEmptyMessage(97);
			}
			
			break;
		default :
			break;
		}
	}
	
	private void initContent() {
		// TODO Auto-generated method stub
		personInfo.setVisibility(View.VISIBLE);
		
		if(list.get(0)[2].length() == 0) {
			btn_tel.setText("添  加");
		}else {
			btn_tel.setText("修  改");
		}
		if(list.get(0)[3].length() == 0) {
			btn_qq.setText("添  加");
		}else {
			btn_qq.setText("修  改");
		}
		txtName.setText(list.get(0)[1]);
		txtTel.setText(list.get(0)[2]);
		txtQQ.setText(list.get(0)[3]);
	}
	


	private void getData() {
		// TODO Auto-generated method stub
		pd = ProgressDialog.show(this, "请稍候", "正在加载...");

		pd.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
					pd.dismiss();
				}
				// TODO Auto-generated method stub
				return false;
			}

		});
		new Thread() {
			@Override
			public void run() {
				list.clear();
				try {
					String code = "0205";
					String info = "{\"root\":[{\"TableName\":\"JWGL_XSGL_XSXX\",\"PNum\":\"1\",\"PRows\":\"10\",\"ZJHM\":\""
							+ zjhm + "\"}]}";
					Log.i("User_ManageActivity", "------------------" + info);
					String sNum = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
					String yhnm = LoginServices.GetNBBM();
					List<JSONObject> list_ = new WebServices().getObject(code,
							info, sNum, yhnm);
					if(list_ == null) {
						myHander.sendEmptyMessage(98);
					}else {
					//for (int i = 0; i < list_.size(); i++) {
						JSONObject object = list_.get(0);
						String[] str = new String[] {
								object.getString("NBBM").toString(),
								object.getString("XM").toString(),
								object.getString("LXDH").toString(),
								object.getString("QQ").toString()
						};
						list.add(str);
						myHander.sendEmptyMessage(1);
					}
				} catch (Exception e) {
					System.out.print(e);
					e.getStackTrace();
					myHander.sendEmptyMessage(99);

				}
			}
		}.start();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case R.layout.doc_deal:
			if (resultCode == 20) {
				Toast.makeText(User_ManageActivity.this, "处理成功", Toast.LENGTH_SHORT)
						.show();
				// IniDoc();
				getData();
			} else if (resultCode == 21) {
				Toast.makeText(User_ManageActivity.this, "处理失败，请稍候再试或联系管理员！",
						Toast.LENGTH_SHORT).show();

			}

			break;

		default:
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
	
	Handler myHander = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				pd.dismiss();
				initContent();
				break;
			case 2:
				pd.dismiss();
				break;
				
			case 3://清除用户SIM成功
				pd.dismiss();
				Toast.makeText(User_ManageActivity.this, "清除成功！",
						Toast.LENGTH_SHORT).show();
				break;
			case 97://清除用户SIM失败
				pd.dismiss();
				Toast.makeText(User_ManageActivity.this, "处理失败！",
						Toast.LENGTH_SHORT).show();
				break;
			case 98:
				pd.dismiss();
				Toast.makeText(User_ManageActivity.this, "身份证号不正确或无此用户！",
						Toast.LENGTH_SHORT).show();
				break;
			case 99:
				pd.dismiss();
				Toast.makeText(User_ManageActivity.this, "数据下载失败！",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}

		
	};

}
