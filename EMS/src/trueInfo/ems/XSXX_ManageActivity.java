package trueInfo.ems;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import trueInfo.services.LoginServices;
import trueInfo.services.WebServices;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class XSXX_ManageActivity extends Activity {
	
	public static final String TAG = "XSXX_TAG";
	
	ProgressDialog pd;
	
	private String zjhm = "";
	
	List<String[]> list = new ArrayList<String[]>();
	// 学籍
	List<String[]> list_xj = new ArrayList<String[]>();
	// 学历和政治面貌
	List<String[]> list_xl = new ArrayList<String[]>();
	List<String[]> list_zzmm = new ArrayList<String[]>();
	// 学习类型，学习层次，学校，专业
	List<String[]> list_xj_lx = new ArrayList<String[]>();
	List<String[]> list_xj_cc = new ArrayList<String[]>();
	List<String[]> list_xj_xx = new ArrayList<String[]>();
	List<String[]> list_xj_zy = new ArrayList<String[]>();
	
	EditText txtZjhm ;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.xsxx_manage_activity);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return super.onCreateDialog(id);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	
	public void btn_click(View v){
		switch(v.getId()) {
		case R.id.reback_btn:
			finish();
			break;
		case R.id.btn_ok:
			//证件号码
			txtZjhm = (EditText) findViewById(R.id.txtId);
			zjhm = txtZjhm.getText().toString();
			if(zjhm.length() == 0){
				Toast.makeText(XSXX_ManageActivity.this, "请输入证件号", Toast.LENGTH_SHORT).show();
			}else {
				getData();
			}
			break;
		}
	}
	
	private void getData() {
		// TODO Auto-generated method stub
		pd = ProgressDialog.show(this, "请稍候", "正在加载...",true,true);
		new Thread() {
			@Override
			public void run() {
				list.clear();
				try {
					String code = "0205";
					String info = "{\"root\":[{\"TableName\":\"JWGL_XSGL_XSXX\",\"PNum\":\"1\",\"PRows\":\"10\",\"ZJHM\":\""
							+ zjhm + "\"}]}";
					Log.i(TAG, "------------------" + info);
					String sNum = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
					String yhnm = LoginServices.GetNBBM();
					List<JSONObject> list_ = new WebServices().getObject(code,
							info, sNum, yhnm);
					for (int i = 0; i < list_.size(); i++) {
						JSONObject object = list_.get(i);
						String[] str = new String[] {
								object.getString("NBBM").toString(),//0
								object.getString("XM").toString(),
								object.getString("XB").toString(),
								object.getString("MZ").toString(),
								object.getString("JG").toString(),
								object.getString("ZJLX").toString(),//5
								object.getString("ZJHM").toString(),
								object.getString("ZZMM").toString(),
								object.getString("XL").toString(),
								object.getString("LXDH").toString(),
								object.getString("JTDH").toString(),//10
								object.getString("LXDZ").toString(),
								object.getString("QQ").toString(),
								object.getString("DZYJ").toString(),
								object.getString("YB").toString(),
								object.getString("BYXX").toString(),
								object.getString("BYZY").toString(),
								object.getString("BZXX").toString() };
						list.add(str);
						// myHander.sendEmptyMessage(1);

					}
				} catch (Exception e) {
					System.out.print(e);
					e.getStackTrace();
					myHander.sendEmptyMessage(98);

				}
				/*
				 * 学籍信息表中的 学校，专业，学习类型，学习层次 （内码）
				 */
				list_xj.clear();
				try {
					String code = "0205";
					String info = "{\"root\":[{\"TableName\":\"JWGL_XSGL_XJXX\",\"PNum\":\"1\",\"PRows\":\"10\",\"XSNM\":\""
							+ list.get(0)[0] + "\"}]}";
					Log.i(TAG, "------------------" + info);
					String sNum = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
					String yhnm = LoginServices.GetNBBM();
					List<JSONObject> list_ = new WebServices().getObject(code,
							info, sNum, yhnm);
					// for (int i = 0; i < list_.size(); i++) {
					if (list_.size() > 0) {
						JSONObject object = list_.get(0);
						String[] str = new String[] {
								object.getString("NBBM").toString(),
								object.getString("XXLX").toString(),
								object.getString("XXCC").toString(),
								object.getString("RJXX").toString(),
								object.getString("RJZY").toString() };
						list_xj.add(str);
					} else {
						list_xj.add(new String[] { "", "" });
					}
				} catch (Exception e) {
					System.out.print(e);
					e.getStackTrace();
					myHander.sendEmptyMessage(99);

				}
				/*
				 * 
				 * 入籍学校 xtwh_jcsj_csmc
				 */
				list_xj_xx.clear();
				try {
					String code = "0205";
					String info = "{\"root\":[{\"TableName\":\"JWGL_JCSJ_XSXX\",\"PNum\":\"1\",\"PRows\":\"10\",\"NBBM\":\""
							+ list_xj.get(0)[3] + "\"}]}";
					Log.i(TAG, "------------------" + info);
					String sNum = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
					String yhnm = LoginServices.GetNBBM();
					List<JSONObject> list_ = new WebServices().getObject(code,
							info, sNum, yhnm);
					if (list_.size() > 0) {
						JSONObject object = list_.get(0);
						String[] str = new String[] {
								object.getString("NBBM").toString(),
								object.getString("XXMC").toString() };
						list_xj_xx.add(str);
					} else {
						list_xj_xx.add(new String[] { "", "" });
					}
				} catch (Exception e) {
					System.out.print(e);
					e.getStackTrace();
					myHander.sendEmptyMessage(99);

				}
				/*
				 * 入籍专业 xtwh_jcsj_csmc
				 */
				list_xj_zy.clear();
				Log.i("www--wwww", "list_xj.get(0)[4]");
				try {
					String code = "0205";
					String info = "{\"root\":[{\"TableName\":\"JWGL_JCSJ_ZYXX\",\"PNum\":\"1\",\"PRows\":\"10\",\"NBBM\":\""
							+ list_xj.get(0)[4] + "\"}]}";
					Log.i(TAG, "------------------" + info);
					String sNum = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
					String yhnm = LoginServices.GetNBBM();
					List<JSONObject> list_ = new WebServices().getObject(code,
							info, sNum, yhnm);
					if (list_.size() > 0) {
						JSONObject object = list_.get(0);
						String[] str = new String[] {
								object.getString("NBBM").toString(),
								object.getString("ZYMC").toString() };
						list_xj_zy.add(str);
					} else {
						list_xj_zy.add(new String[] { "", "" });
					}
				} catch (Exception e) {
					System.out.print(e);
					e.getStackTrace();
					myHander.sendEmptyMessage(99);
				}
				/*
				 * 学习类型 xtwh_jcsj_csmc
				 */
				list_xj_lx.clear();
				try {
					String code = "0205";
					String info = "{\"root\":[{\"TableName\":\"XTWH_JCSJ_CSMC\",\"PNum\":\"1\",\"PRows\":\"10\",\"NBBM\":\""
							+ list_xj.get(0)[1] + "\"}]}";
					Log.i(TAG, "------------------" + info);
					String sNum = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
					String yhnm = LoginServices.GetNBBM();
					List<JSONObject> list_ = new WebServices().getObject(code,
							info, sNum, yhnm);
					if (list_.size() > 0) {
						JSONObject object = list_.get(0);
						String[] str = new String[] {
								object.getString("NBBM").toString(),
								object.getString("CSMC").toString() };
						list_xj_lx.add(str);
					} else {
						list_xj_lx.add(new String[] { "", "" });
					}
				} catch (Exception e) {
					System.out.print(e);
					e.getStackTrace();
					myHander.sendEmptyMessage(99);
				}
				/*
				 * 学习层次 xtwh_jcsj_csmc
				 */
				list_xj_cc.clear();
				try {
					String code = "0205";
					String info = "{\"root\":[{\"TableName\":\"XTWH_JCSJ_CSMC\",\"PNum\":\"1\",\"PRows\":\"10\",\"NBBM\":\""
							+ list_xj.get(0)[2] + "\"}]}";
					Log.i(TAG, "------------------" + info);
					String sNum = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
					String yhnm =LoginServices.GetNBBM();
					List<JSONObject> list_ = new WebServices().getObject(code,
							info, sNum, yhnm);
					if (list_.size() > 0) {
						JSONObject object = list_.get(0);
						String[] str = new String[] {
								object.getString("NBBM").toString(),
								object.getString("CSMC").toString() };
						list_xj_cc.add(str);
					} else {
						list_xj_cc.add(new String[] { "", "" });
					}
				} catch (Exception e) {
					System.out.print(e);
					e.getStackTrace();
					myHander.sendEmptyMessage(99);
				}
				/*
				 * 政治面貌 xtwh_jcsj_csmc
				 */
				list_zzmm.clear();
				try {
					String code = "0205";
					String info = "{\"root\":[{\"TableName\":\"XTWH_JCSJ_CSMC\",\"PNum\":\"1\",\"PRows\":\"10\",\"NBBM\":\""
							+ list.get(0)[7] + "\"}]}";
					Log.i(TAG, "------------------" + info);
					String sNum = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
					String yhnm = LoginServices.GetNBBM();
					List<JSONObject> list_ = new WebServices().getObject(code,
							info, sNum, yhnm);
					if (list_.size() > 0) {
						JSONObject object = list_.get(0);
						String[] str = new String[] {
								object.getString("NBBM").toString(),
								object.getString("CSMC").toString() };
						list_zzmm.add(str);
					} else {
						list_zzmm.add(new String[] { "", "" });
					}
				} catch (Exception e) {
					System.out.print(e);
					e.getStackTrace();
					myHander.sendEmptyMessage(99);
				}
				/*
				 * 学历
				 */
				list_xl.clear();
				try {
					String code = "0205";
					String info = "{\"root\":[{\"TableName\":\"XTWH_JCSJ_CSMC\",\"PNum\":\"1\",\"PRows\":\"10\",\"NBBM\":\""
							+ list.get(0)[8] + "\"}]}";
					Log.i(TAG, "------------------" + info);
					String sNum = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
					String yhnm = LoginServices.GetNBBM();
					List<JSONObject> list_ = new WebServices().getObject(code,
							info, sNum, yhnm);
					// for (int i = 0; i < list_.size(); i++) {
					if (list_.size() > 0) {
						JSONObject object = list_.get(0);
						String[] str = new String[] {
								object.getString("NBBM").toString(),
								object.getString("CSMC").toString() };
						list_xl.add(str);
					} else {
						list_xl.add(new String[] { "", "" });
					}
					myHander.sendEmptyMessage(1);
				} catch (Exception e) {
					System.out.print(e);
					e.getStackTrace();
					myHander.sendEmptyMessage(99);
				}
				
			}
		}.start();
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
			case 98:
				pd.dismiss();
				Toast.makeText(XSXX_ManageActivity.this, "错误！",
						Toast.LENGTH_SHORT).show();
				break;
			case 99:
				pd.dismiss();
				Toast.makeText(XSXX_ManageActivity.this, "部分数据下载失败！",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
	
	private void initContent() {
		// TODO Auto-generated method stub
		LinearLayout layout_personInfo = (LinearLayout) findViewById(R.id.personInfo);
		layout_personInfo.setVisibility(View.VISIBLE);
		
		TextView txtxm = (TextView) findViewById(R.id.txtxm);
		TextView txtxb = (TextView) findViewById(R.id.txtxb);
		TextView txtmz = (TextView) findViewById(R.id.txtmz);
		TextView txtjg = (TextView) findViewById(R.id.txtjg);
		TextView txtzjhm = (TextView) findViewById(R.id.txtzjhm);
		TextView txtxxlx = (TextView) findViewById(R.id.txtxxlx);
		TextView txtxxcc = (TextView) findViewById(R.id.txtxxcc);
		TextView txtbkxx = (TextView) findViewById(R.id.txtbkxx);
		TextView txtbkzy = (TextView) findViewById(R.id.txtbkzy);
		TextView txtzzmm = (TextView) findViewById(R.id.txtzzmm);
		TextView txtxl = (TextView) findViewById(R.id.txtxl);
		TextView txtlxdh = (TextView) findViewById(R.id.txtlxdh);
		TextView txtjtdh = (TextView) findViewById(R.id.txtjtdh);
		TextView txtlxdz = (TextView) findViewById(R.id.txtlxdz);
		TextView txtqq = (TextView) findViewById(R.id.txtqq);
		TextView txtyb = (TextView) findViewById(R.id.txtyb);
		TextView txtbyxx = (TextView) findViewById(R.id.txtbyxx);
		TextView txtbyzy = (TextView) findViewById(R.id.txtbyzy);
		TextView txtbzxx = (TextView) findViewById(R.id.txtbzxx);
		

		/*
		 * 学历 政治面貌 。。
		 */
		if (list.size() > 0) {
			txtxm.setText(list.get(0)[1]);
			txtxb.setText(list.get(0)[2]);
			txtmz.setText(list.get(0)[3]);
			txtjg.setText(list.get(0)[4]);
			txtzjhm.setText(list.get(0)[6]);
			txtlxdh.setText(list.get(0)[9]);
			txtjtdh.setText(list.get(0)[10]);
			txtlxdz.setText(list.get(0)[11]);
			txtqq.setText(list.get(0)[12]);
			txtyb.setText(list.get(0)[14]);
			txtbyxx.setText(list.get(0)[15]);
			txtbyzy.setText(list.get(0)[16]);
			txtbzxx.setText(list.get(0)[17]);
		}
		if (list_zzmm.size() > 0) {
			txtzzmm.setText(list_zzmm.get(0)[1]);
		}
		if (list_xl.size() > 0) {
			txtxl.setText(list_xl.get(0)[1]);
		}
		if (list_xj_lx.size() > 0) {
			txtxxlx.setText(list_xj_lx.get(0)[1]);
		}
		if (list_xj_cc.size() > 0) {
			txtxxcc.setText(list_xj_cc.get(0)[1]);
		}
		if (list_xj_xx.size() > 0) {
			txtbkxx.setText(list_xj_xx.get(0)[1]);
		}
		if (list_xj_zy.size() > 0) {
			txtbkzy.setText(list_xj_zy.get(0)[1]);
		}

	}
	
}
