package trueInfo.ems;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import trueInfo.services.LoginServices;
import trueInfo.services.WebServices;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SJPF_ManageActivity extends Activity {



	private TextView xmxy, xsxm, xszy,txtkgtf, txt_tm1, txt_tm2, txt_tm3, txt_ckda1,
			txt_ckda2, txt_ckda3, txt_xsda1, txt_xsda2, txt_xsda3;
	private EditText txtId;
	private Button btn_ok;

	private String personId;
	private String xsnm = "";
	private ScrollView scrollView;
	private LinearLayout layout_spinner;
	private LinearLayout layout_daan;
	private LinearLayout layout_deal;
	private Spinner spinner;

	private List<String[]> list_sjcj = new ArrayList<String[]>();
	private List<String> list_spinner = new ArrayList<String>();
	private List<String[]> list_jstm = new ArrayList<String[]>();
	private List<String[]> list_jstm_ckda = new ArrayList<String[]>();
	private List<String[]> list_wdtm = new ArrayList<String[]>();
	private List<String[]> list_wdtm_ckda = new ArrayList<String[]>();

	private ArrayAdapter adapter;

	private ProgressDialog pd;
	private int position;
	
	private int count = 0;//�����ж��Ƿ�����Ŀ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.sjpf_manage_activity);
		getInfo();
		initActivity();

	}

	

	private void initActivity() {
		// TODO Auto-generated method stub
		txtId = (EditText) this.findViewById(R.id.txtId);
		btn_ok = (Button) this.findViewById(R.id.btn_ok);
		scrollView = (ScrollView) this.findViewById(R.id.scrollview);
		layout_spinner = (LinearLayout) this.findViewById(R.id.layout_spinner);
		layout_daan = (LinearLayout) this.findViewById(R.id.layout_daan);
		layout_deal = (LinearLayout) this.findViewById(R.id.layout_deal);
		spinner = (Spinner) this.findViewById(R.id.spinner);
		xsxm = (TextView) this.findViewById(R.id.xsxm);
		xszy = (TextView) this.findViewById(R.id.xszy);
		txtkgtf =(TextView) this.findViewById(R.id.txtkgtf);

		// tm1
		txt_tm1 = (TextView) this.findViewById(R.id.txttm1);
		txt_ckda1 = (TextView) this.findViewById(R.id.txtckda1);
		txt_xsda1 = (TextView) this.findViewById(R.id.txtxsda1);
		// tm2
		txt_tm2 = (TextView) this.findViewById(R.id.txttm2);
		txt_ckda2 = (TextView) this.findViewById(R.id.txtckda2);
		txt_xsda2 = (TextView) this.findViewById(R.id.txtxsda2);
		// tm3
		txt_tm3 = (TextView) this.findViewById(R.id.txttm3);
		txt_ckda3 = (TextView) this.findViewById(R.id.txtckda3);
		txt_xsda3 = (TextView) this.findViewById(R.id.txtxsda3);

	}

	private void getInfo() {
		// TODO Auto-generated method stub

	}

	public void btn_click(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			pd = ProgressDialog.show(SJPF_ManageActivity.this, "���Ժ�",
					"���ڼ���...", true, true, null);
			new Thread() {
				public void run() {
					Looper.prepare();
					getSJKHInfo();
					// layout_spinner.setVisibility(View.VISIBLE);
					// layout_daan.setVisibility(View.VISIBLE);
					myHandler.sendEmptyMessage(0);
				}
			}.start();
			
			break;

		case R.id.reback_btn:
			this.finish();
			break;

		case R.id.btn_pf:
			Intent intent = new Intent(SJPF_ManageActivity.this,DocDealActivity.class);
			intent.putExtra("NBBM", list_sjcj.get(position)[0]);
			intent.putExtra("BMMC", "JWGL_KSGL_SJCJ");
			intent.putExtra("XGZD", "KSCJ");
			intent.putExtra("KGTF", list_sjcj.get(position)[5]);
			startActivityForResult(intent,R.layout.doc_deal);
			break;

		}

	}

	/* ���ʵ��������Ϣ */
	private void getSJKHInfo() {
		// TODO Auto-generated method stub

		/* ���ѧ������ */
		personId = txtId.getText().toString();
		String code = "0205";
		String info = "{\"root\":[{\"TableName\":\"JWGL_XSGL_XJXX\",\"PNum\":\"1\",\"PRows\":\"10\",\"XJBH\":\""
				+ personId + "\"}]}";
		String sNum = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
		String yhnm = LoginServices.GetNBBM();
		List<JSONObject> list = new WebServices().getObject(code, info, sNum,
				yhnm);
		if (list != null) {
			JSONObject object = list.get(0);
			try {
				xsnm = object.getString("XSNM");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			xsnm = "";
			Toast.makeText(SJPF_ManageActivity.this, "ѧ����Ŵ���",
					Toast.LENGTH_SHORT).show();
		}

		/* ���ʵ���ɼ�------ */
		list_sjcj.clear();
		personId = txtId.getText().toString();
		String code_xsnm = "0205";
		String info_xsnm = "{\"root\":[{\"TableName\":\"JWGL_KSGL_SJCJ\",\"PNum\":\"1\",\"PRows\":\"10\",\"XSNM\":\""
				+ xsnm + "\"}]}";
		String sNum_xsnm = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
		String yhnm_xsnm = LoginServices.GetNBBM();
		if (xsnm != "") {
			List<JSONObject> list_xsnm = new WebServices().getObject(code_xsnm,
					info_xsnm, sNum_xsnm, yhnm_xsnm);
			if (list_xsnm != null) {
				for (int i = 0; i < list_xsnm.size(); i++) {
					JSONObject object = list_xsnm.get(i);
					try {
						String[] str = new String[] {
								object.getString("NBBM").toString(),
								object.getString("XM").toString(),
								object.getString("ZYMC").toString(),
								object.getString("KCNM").toString(),
								object.getString("KCMC").toString(),
								object.getString("CJ").toString(),
								object.getString("KCCJ").toString()
						};
						list_sjcj.add(str);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				Toast.makeText(SJPF_ManageActivity.this, "�����ݣ�",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	protected void initXsxx() {
		// TODO Auto-generated method stub
		xsxm.setText(list_sjcj.get(0)[1]);
		xszy.setText(list_sjcj.get(0)[2]);
	}

	private void initSpinner() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		list_spinner.clear();
		for (int i = 0; i < list_sjcj.size(); i++) {
			list_spinner.add(list_sjcj.get(i)[4]);
		}

		// �ڶ�����Ϊ�����б���һ����������������õ���ǰ�涨���list��
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list_spinner);
		// ��������Ϊ���������������б�����ʱ�Ĳ˵���ʽ��
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ���Ĳ�������������ӵ������б���
		spinner.setAdapter(adapter);
		// ���岽��Ϊ�����б����ø����¼�����Ӧ���������Ӧ�˵���ѡ��
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				TextView tv = (TextView) view;
				tv.setTextColor(getResources().getColor(R.color.black)); // ������ɫ

				tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL); // ���þ���
				
				SJPF_ManageActivity.this.position = arg2;
				// ����Ϣ
				count = 0;
				initDaAn(arg2);
				// ��mySpinner ��ʾ
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				// myTextView.setText("NONE");
				arg0.setVisibility(View.VISIBLE);
			}
		});
		// �����˵�����������ѡ����¼�����
		spinner.setOnTouchListener(new Spinner.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// ��mySpinner ���أ�������Ҳ���ԣ����Լ�����
				// v.setVisibility(View.INVISIBLE);
				return false;
			}
		});
		// �����˵�����������ѡ���ı��¼�����
		spinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				v.setVisibility(View.VISIBLE);
			}
		});
	}

	protected void initDaAn(final int position) {
		// TODO Auto-generated method stub
		
		pd = ProgressDialog.show(this, "���Ժ�", "���ڼ���...", true, true);
		new Thread() {
			public void run() {
				Looper.prepare();
				list_jstm.clear();
				// ͨ���ɼ�����ȡ��JWGL_KSGL_SJKS�еĽ�����Ŀ����Ŀ�����ѧ����
				String code_jstm = "0205";
				String info_jstm = "{\"root\":[{\"TableName\":\"JWGL_KSGL_SJKS\",\"PNum\":\"1\",\"PRows\":\"10\",\"CJNM\":\""
						+ list_sjcj.get(position)[0] + "\"}]}";

				String sNum_jstm = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
				String yhnm_jstm = LoginServices.GetNBBM();
				Log.i("ems_JWGL_KSGL_SJKS", info_jstm);
				List<JSONObject> list = new WebServices().getObject(code_jstm,
						info_jstm, sNum_jstm, yhnm_jstm);
				if (list == null) {
					System.out.print("�˿γ�����Ŀ");
				} else {
					for (int i = 0; i < list.size(); i++) {
						JSONObject object = list.get(i);
						try {
							String[] str = new String[] {
									object.getString("NBBM"),
									object.getString("TMNM"),
									object.getString("XSDA"),
									object.getString("TM") };
							list_jstm.add(str);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					count++;
				}

				// ͨ����Ŀ����ȡ��JWGL_KSGL_JSTM�еĽ�����Ŀ�ο���
				list_jstm_ckda.clear();
				for (int i = 0; i < list_jstm.size(); i++) {
					String code_jstm_ckda = "0205";
					String info_jstm_ckda = "{\"root\":[{\"TableName\":\"JWGL_KSGL_JSTM\",\"PNum\":\"1\",\"PRows\":\"10\",\"NBBM\":\""
							+ list_jstm.get(i)[1] + "\"}]}";
					String sNum_jstm_ckda = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
					String yhnm_jstm_ckda = LoginServices.GetNBBM();
					List<JSONObject> list_jstm_ckda_json = new WebServices()
							.getObject(code_jstm_ckda, info_jstm_ckda,
									sNum_jstm_ckda, yhnm_jstm_ckda);
					if (list_jstm_ckda_json != null) {
						for (int j = 0; j < list_jstm_ckda_json.size(); j++) {
							JSONObject object = list_jstm_ckda_json.get(j);
							try {
								String[] str = new String[] {
										object.getString("NBBM"),
										object.getString("DA"),
										object.getString("TM") };
								list_jstm_ckda.add(str);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						count++;
					} else {
						System.out.print("�˿γ�����Ŀ");
					}
				}

				// ͨ���ɼ�����ȡ��JWGL_KSGL_WDTM�е��ʴ���Ŀ����Ŀ�����ѧ����
				list_wdtm.clear();
				String code_wdtm = "0205";
				String info_wdtm = "{\"root\":[{\"TableName\":\"JWGL_KSGL_WDTM\",\"PNum\":\"1\",\"PRows\":\"10\",\"CJNM\":\""
						+ list_sjcj.get(position)[0] + "\",\"TMLX\":\"3\"}]}";
				String sNum_wdtm = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
				String yhnm_wdtm = LoginServices.GetNBBM();
				List<JSONObject> list_wdtm_json = new WebServices().getObject(
						code_wdtm, info_wdtm, sNum_wdtm, yhnm_wdtm);
				if (list_wdtm_json != null) {
					for (int i = 0; i < list_wdtm_json.size(); i++) {
						JSONObject object = list_wdtm_json.get(i);
						try {
							String[] str = new String[] {
									object.getString("NBBM"),
									object.getString("TMNM"),
									object.getString("XSDA"),
									object.getString("TM") };
							list_wdtm.add(str);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					count++;
				} else {
					System.out.println("����Ŀ��");
				}

				// ͨ����Ŀ����ȡ��JWGL_KSGL_JSTM�еĽ�����Ŀ�ο���
				list_wdtm_ckda.clear();
				for (int i = 0; i < list_wdtm.size(); i++) {

					String code_jstm_ckda = "0205";
					String info_jstm_ckda = "{\"root\":[{\"TableName\":\"JWGL_KSGL_WDTM\",\"PNum\":\"1\",\"PRows\":\"10\",\"NBBM\":\""
							+ list_wdtm.get(i)[1] + "\"}]}";
					String sNum_jstm_ckda = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
					String yhnm_jstm_ckda = LoginServices.GetNBBM();
					List<JSONObject> list_wdtm_ckda_json = new WebServices()
							.getObject(code_jstm_ckda, info_jstm_ckda,
									sNum_jstm_ckda, yhnm_jstm_ckda);
					if (list_wdtm_ckda_json != null) {
						for (int j = 0; j < list_wdtm_ckda_json.size(); j++) {
							JSONObject object = list_wdtm_ckda_json.get(j);
							try {
								String[] str = new String[] {
										object.getString("NBBM"),
										object.getString("DA"),
										object.getString("TM") };
								list_wdtm_ckda.add(str);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						count++;
					}else {
						System.out.println("����Ŀ��");
					}
				}
				
					myHandler.sendEmptyMessage(1);
			}
		}.start();

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case R.layout.doc_deal:
			if (resultCode == 20) {
				Toast.makeText(SJPF_ManageActivity.this, "����ɹ�", Toast.LENGTH_SHORT)
						.show();
				// IniDoc();
			} else if (resultCode == 21) {
				Toast.makeText(SJPF_ManageActivity.this, "����ʧ�ܣ����Ժ����Ի���ϵ����Ա��",
						Toast.LENGTH_SHORT).show();

			}

			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
		
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:

				if (list_sjcj.size() > 0) {
					
					layout_spinner.setVisibility(View.VISIBLE);
					
					initXsxx();
					initSpinner();
				} else {

				}
				pd.dismiss();
				break;

			case 1:
				if (count > 0) {
					scrollView.setVisibility(View.VISIBLE);
					layout_deal.setVisibility(View.VISIBLE);
					
					initDaAnActivity();
				}else{
					scrollView.setVisibility(View.GONE);
					layout_deal.setVisibility(View.GONE);
				}
				
				pd.dismiss();
			}
		}
	};

	protected void initDaAnActivity() {
		
		/*�͹���÷�*/
		txtkgtf.setText(list_sjcj.get(position)[5]);
		
		if (list_jstm_ckda.size() > 0) {
			txt_tm1.setText(list_jstm_ckda.get(0)[2]);
			txt_ckda1.setText(list_jstm_ckda.get(0)[1]);
		}
		if (list_jstm.size() > 0) {
			txt_xsda1.setText(list_jstm.get(0)[2]);
		}

		if (list_jstm_ckda.size() > 1) {
			txt_tm2.setText(list_jstm_ckda.get(1)[2]);
			txt_ckda2.setText(list_jstm_ckda.get(1)[1]);
		}
		if (list_jstm.size() > 1) {
			txt_xsda2.setText(list_jstm.get(1)[2]);
		}
		if (list_jstm_ckda.size() > 0) {
			txt_tm3.setText(list_jstm_ckda.get(0)[2]);
			txt_ckda3.setText(list_jstm_ckda.get(0)[1]);
		}
		if (list_jstm.size() > 0) {
			txt_xsda3.setText(list_jstm.get(0)[2]);
		}
	}



	protected void initContent() {
		// TODO Auto-generated method stub
		layout_spinner.setVisibility(View.VISIBLE);
		scrollView.setVisibility(View.VISIBLE);
		layout_deal.setVisibility(View.VISIBLE);
	}

}
