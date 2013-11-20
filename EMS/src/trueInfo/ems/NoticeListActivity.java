package trueInfo.ems;

import static trueInfo.util.PublicClass.NAMESPACE;
import static trueInfo.util.PublicClass.URL;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import trueInfo.services.LoginServices;
import trueInfo.util.PublicClass;
import trueInfo.util.PullDownView;
import trueInfo.util.PullDownView.OnPullDownListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NoticeListActivity extends Activity implements OnPullDownListener,
		OnItemClickListener {
	ArrayList<String[]> diaryList1 = new ArrayList<String[]>();
	/** Handler What����������� **/
	private static final int WHAT_DID_LOAD_DATA = 0;
	/** Handler What����������� **/
	private static final int WHAT_DID_REFRESH = 1;
	/** Handler What����������� **/
	private static final int WHAT_DID_MORE = 2;
	private static final int COUNTLIST = 3;
	private static final int TOASTSHOW = 4;
	// LISTVIEW����
	private static final int PAGESIZE = 9; // ÿ��ȡ������¼
	private int pageIndex = 1; // ���ڱ��浱ǰ�ǵڼ�ҳ,1�����һҳ
	private String ischild = null;
	private int countall = 0;
	int pageCount = 1; // ��ҳ��
	private ListView mListView;
	private TextView Textviewzdy;
	private Button login_reback_btn;
	MyAdapter mAdapter;
	CharSequence str;

	private PullDownView mPullDownView;

	// private List<String> mStrings = new ArrayList<String>();

	/**
	 * �Զ���Adapter
	 * 
	 * @author jhao
	 * @version 1.0
	 */
	class MyAdapter extends BaseAdapter {
		private Context context;

		public MyAdapter(Context context) {
			this.context = context;
		}

		public MyAdapter(Context context, int res) {
			this.context = context;
		}

		public void refresh() {
			notifyDataSetChanged();
		}

		public int getCount() {
			return diaryList1.size();
		}

		public Object getItem(int position) {
			return diaryList1.get(position - 1);
		}

		public long getItemId(int position) {

			return 0;
		}

		/**
		 * ��ȡ���ࣨ��ҳ��ȡ��
		 */
		public void more() {
			// step 1: ��ǰҳ++

		}

		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.list_item_notice_2line, null);
			}

			

			TextView newscontent1 = (TextView) convertView
					.findViewById(R.id.title);
			TextView newscontent2 = (TextView) convertView
					.findViewById(R.id.sb);
			TextView newscontent3 = (TextView) convertView
					.findViewById(R.id.time);

			newscontent1.setText(diaryList1.get(position)[1]);
			newscontent2.setText("���ԣ�"+diaryList1.get(position)[3]);
			newscontent3.setText("ʱ�䣺"+diaryList1.get(position)[4].substring(0, diaryList1.get(position)[4].indexOf(" ")));
			return convertView;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_list_activity);
		Intent intent = getIntent();
		mAdapter = new MyAdapter(this);
		
		//���ñ�����������
		Textviewzdy = (TextView) findViewById(R.id.Textviewzdy);
		str = getString(R.string.title_activity_notice);
		Textviewzdy.setText(str);
		/*
		login_reback_btn = (Button) findViewById(R.id.login_reback_btn);
		
		if (ischild != null)// �˱�����Ϊ��˵���˲��Ǹ�Ŀ¼
		{
			login_reback_btn.setVisibility(View.VISIBLE);
		} else {
			login_reback_btn.setVisibility(View.GONE);
		}
		*/
		
		/*
		 * 1.ʹ��PullDownView 2.����OnPullDownListener 3.��mPullDownView�����ȡListView
		 */
		mPullDownView = (PullDownView) findViewById(R.id.pull_down_view);
		mPullDownView.setOnPullDownListener(this);
		
		mListView = mPullDownView.getListView();

		mListView.setOnItemClickListener(this);

		mListView.setAdapter(mAdapter);

		// ���ÿ����Զ���ȡ���� �������һ���Զ���ȡ �ĳ�false�������Զ���ȡ����
		mPullDownView.enableAutoFetchMore(true, 1);
		// ���� ������β��
		mPullDownView.setHideFooter();
		// ��ʾ�������Զ���ȡ����
		mPullDownView.setShowFooter();
		// ���ز��ҽ���ͷ��ˢ��
		mPullDownView.setHideHeader();
		// ��ʾ���ҿ���ʹ��ͷ��ˢ��
		mPullDownView.setShowHeader();

		getDiaryList_count();

	}

	public void btn_back(View v) { // ������ ���ذ�ť
		NoticeListActivity.this.finish();
	}

	/** ˢ���¼��ӿ� ����Ҫע����ǻ�ȡ������ Ҫ�ر� ˢ�µĽ�����RefreshComplete() **/
	@Override
	public void onRefresh() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				/** �ر� ˢ����� ***/
				mPullDownView.RefreshComplete();// ������̰߳�ȫ�� �ɿ�Դ����

				mUIHandler.sendEmptyMessage(WHAT_DID_REFRESH);

			}
		}).start();

	}

	/** ˢ���¼��ӿ� ����Ҫע����ǻ�ȡ������ Ҫ�ر� ����Ľ����� notifyDidMore() **/
	@Override
	public void onMore() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (countall % PAGESIZE == 0) {
					pageCount = countall / PAGESIZE;
				} else {
					pageCount = (countall / PAGESIZE) + 1;
				}
				pageIndex++;
				// ��������ȡ������� ������̰߳�ȫ�� �ɿ�Դ����

				if (pageIndex > pageCount)// ˵�������һҳ
				{
					pageIndex = pageCount;
					mPullDownView.notifyDidMore();
					mUIHandler.sendEmptyMessage(TOASTSHOW);

				} else {

					Message msg = mUIHandler.obtainMessage(WHAT_DID_MORE);
					msg.obj = pageIndex;
					msg.sendToTarget();

				}
			}
		}).start();
	}

	private Handler mUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_DID_LOAD_DATA: {
				if (msg.obj != null) {

					mPullDownView.notifyDidMore();
					List<String[]> strings = (List<String[]>) msg.obj;
					if (!strings.isEmpty()) {
						mAdapter.notifyDataSetChanged();
					}
				}
				// Toast.makeText(OrderManageActivity.this,
				// ""+mListView.getAdapter().getCount(),
				// Toast.LENGTH_LONG).show();
				// �������ݼ������;
				break;
			}
			case WHAT_DID_REFRESH: {

				mAdapter.notifyDataSetChanged();
				// �������������
				break;
			}

			case WHAT_DID_MORE: {

				if (msg.obj != null) {

					getDiaryList();
				}

				break;

			}
			case COUNTLIST: {
				// �������� ����ʹ��
				getDiaryList();
				break;

			}
			case TOASTSHOW: {
				Toast.makeText(NoticeListActivity.this, "�Ѿ������һ����¼�ˣ�",
						Toast.LENGTH_SHORT).show();

				break;
			}
			}

		}

	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int positionReal = position - 1;
		Intent intent = new Intent(NoticeListActivity.this,
				NoticeInfoActivity.class);
			intent.putExtra("NBBM",diaryList1.get(positionReal)[0]);
			intent.putExtra("BT",diaryList1.get(positionReal)[1]);
			intent.putExtra("NR",diaryList1.get(positionReal)[2]);
			intent.putExtra("XM",diaryList1.get(positionReal)[3]);
			intent.putExtra("SJ",diaryList1.get(positionReal)[4].substring(0, diaryList1.get(positionReal)[4].indexOf(" ")));
		startActivity(intent);
	}

	// ��������ȡ��Ϣ�б�
	public void getDiaryList() {
		new Thread() {
			public void run() {
				try {
					SoapObject soapObject = new SoapObject(NAMESPACE,
							PublicClass.METHOD_NAME);

					soapObject.addProperty("Code", "0205");
					soapObject
							.addProperty(
									"Info",
									"{\"root\":[{\"TableName\":\"JWGL_NBGL_GGTZ\",\"PNum\":\"1\",\"PRows\":\"10\",\"ZDNM\":\""+ LoginServices.GetNBBM()+"\"}]}");
					soapObject.addProperty("SNum",
							"0394cb6d-2575-4f73-92e9-6d402c39e20c");
					soapObject.addProperty("yhnm",
							"ED5B2CD1643A40CFB05AE2FB1F1CEC9D");

					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
							SoapEnvelope.VER11);
					envelope.setOutputSoapObject(soapObject);
					envelope.bodyOut = soapObject;
					envelope.dotNet = true;
					envelope.encodingStyle = SoapEnvelope.ENC;

					@SuppressWarnings("deprecation")
					HttpTransportSE httpTranstation = new HttpTransportSE(URL);

					httpTranstation.call(PublicClass.SOAP_ACTION, envelope);
					Object result = envelope.getResponse();

					String retStr = String.valueOf(result);

					String str = "anyType{}";

					Log.i("moa", "moa:---------------" + retStr);

					if (retStr != null & !str.equalsIgnoreCase(retStr)) {

						JSONObject jsonresult = new JSONObject(retStr);
						// ��ȡJSONArray
						JSONArray array = jsonresult.getJSONArray("root");

						if (array.length() > 0) {
							for (int i = 0; i < array.length(); i++) {

								JSONObject obj = array.getJSONObject(i);
								String[] sa = new String[] {
										obj.getString("NBBM").toString(),
										obj.getString("BT").toString(),
										obj.getString("NR").toString(),
										obj.getString("YHMC").toString(),
										obj.getString("DJSJ").toString()};
								diaryList1.add(sa);
							}

						}
						// Log.i("moa", diaryList1.toString());
						Message msg = mUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
						msg.obj = diaryList1;
						msg.sendToTarget();
					}
					// Log.d("diaryList", String.valueOf(diaryList_txl.size()));

				} catch (Exception e) {
					System.out.println("ʧ�ܣ�");
					System.out.println(e);
					e.printStackTrace();

				}
			}
		}.start();

	}

	// ��������ȡ��Ϣ�б�
	public void getDiaryList_count() {
		new Thread() {
			public void run() {
				try {
					SoapObject soapObject = new SoapObject(
							PublicClass.NAMESPACE, PublicClass.METHOD_NAME);

					// soapObject.addProperty("DLZH", uno);
					// soapObject.addProperty("YWBM", "0301");
					// soapObject.addProperty("Flag", "123");

					soapObject.addProperty("Code", "0204");

					soapObject.addProperty(
									"Info",	"{\"root\":[{\"TableName\":\"JWGL_NBGL_GGTZ\",\"PNum\":\"1\",\"PRows\":\"10\",\"NBBM\":\"6bc79dad-4bc4-400e-8ff3-e4cd17558f55\"}]}");

					soapObject.addProperty("SNum",
							"0394cb6d-2575-4f73-92e9-6d402c39e20c");
					soapObject.addProperty("yhnm", LoginServices.GetNBBM());

					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
							SoapEnvelope.VER11);
					envelope.setOutputSoapObject(soapObject);
					envelope.bodyOut = soapObject;
					envelope.dotNet = true;
					envelope.encodingStyle = SoapSerializationEnvelope.ENC;

					@SuppressWarnings("deprecation")
					HttpTransportSE httpTranstation = new HttpTransportSE(
							PublicClass.URL);

					httpTranstation.call(PublicClass.SOAP_ACTION, envelope);

					Object result = (Object) envelope.getResponse();

					// �����޸�
					String retStr = String.valueOf(result);
					if (retStr != null && !"anyType{}".equalsIgnoreCase(retStr)) {
						JSONObject jsonresult = new JSONObject(retStr);

						// ��ȡJSONArray
						JSONArray array = jsonresult.getJSONArray("root");
						if (array.length() > 0) {
							JSONObject obj = array.getJSONObject(0);
							if (obj.has("RCOUNT")) {
								countall = Integer.parseInt(obj.getString("RCOUNT")); // ������
								Log.i("EMS","----countall----"+countall+"-------");
								mUIHandler.sendEmptyMessage(COUNTLIST);
							}
						}
					}
				} catch (Exception e) {
					System.out.println("ʧ�ܣ�");
					System.out.println(e);
					e.printStackTrace();

				}
			}
		}.start();

	}

}
