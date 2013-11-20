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
	/** Handler What加载数据完毕 **/
	private static final int WHAT_DID_LOAD_DATA = 0;
	/** Handler What更新数据完毕 **/
	private static final int WHAT_DID_REFRESH = 1;
	/** Handler What更多数据完毕 **/
	private static final int WHAT_DID_MORE = 2;
	private static final int COUNTLIST = 3;
	private static final int TOASTSHOW = 4;
	// LISTVIEW设置
	private static final int PAGESIZE = 9; // 每次取几条记录
	private int pageIndex = 1; // 用于保存当前是第几页,1代表第一页
	private String ischild = null;
	private int countall = 0;
	int pageCount = 1; // 总页数
	private ListView mListView;
	private TextView Textviewzdy;
	private Button login_reback_btn;
	MyAdapter mAdapter;
	CharSequence str;

	private PullDownView mPullDownView;

	// private List<String> mStrings = new ArrayList<String>();

	/**
	 * 自定义Adapter
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
		 * 获取更多（分页获取）
		 */
		public void more() {
			// step 1: 当前页++

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
			newscontent2.setText("来自："+diaryList1.get(position)[3]);
			newscontent3.setText("时间："+diaryList1.get(position)[4].substring(0, diaryList1.get(position)[4].indexOf(" ")));
			return convertView;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_list_activity);
		Intent intent = getIntent();
		mAdapter = new MyAdapter(this);
		
		//设置标题栏的名称
		Textviewzdy = (TextView) findViewById(R.id.Textviewzdy);
		str = getString(R.string.title_activity_notice);
		Textviewzdy.setText(str);
		/*
		login_reback_btn = (Button) findViewById(R.id.login_reback_btn);
		
		if (ischild != null)// 此变量不为空说明此不是父目录
		{
			login_reback_btn.setVisibility(View.VISIBLE);
		} else {
			login_reback_btn.setVisibility(View.GONE);
		}
		*/
		
		/*
		 * 1.使用PullDownView 2.设置OnPullDownListener 3.从mPullDownView里面获取ListView
		 */
		mPullDownView = (PullDownView) findViewById(R.id.pull_down_view);
		mPullDownView.setOnPullDownListener(this);
		
		mListView = mPullDownView.getListView();

		mListView.setOnItemClickListener(this);

		mListView.setAdapter(mAdapter);

		// 设置可以自动获取更多 滑到最后一个自动获取 改成false将禁用自动获取更多
		mPullDownView.enableAutoFetchMore(true, 1);
		// 隐藏 并禁用尾部
		mPullDownView.setHideFooter();
		// 显示并启用自动获取更多
		mPullDownView.setShowFooter();
		// 隐藏并且禁用头部刷新
		mPullDownView.setHideHeader();
		// 显示并且可以使用头部刷新
		mPullDownView.setShowHeader();

		getDiaryList_count();

	}

	public void btn_back(View v) { // 标题栏 返回按钮
		NoticeListActivity.this.finish();
	}

	/** 刷新事件接口 这里要注意的是获取更多完 要关闭 刷新的进度条RefreshComplete() **/
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

				/** 关闭 刷新完毕 ***/
				mPullDownView.RefreshComplete();// 这个事线程安全的 可看源代码

				mUIHandler.sendEmptyMessage(WHAT_DID_REFRESH);

			}
		}).start();

	}

	/** 刷新事件接口 这里要注意的是获取更多完 要关闭 更多的进度条 notifyDidMore() **/
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
				// 告诉它获取更多完毕 这个事线程安全的 可看源代码

				if (pageIndex > pageCount)// 说明是最后一页
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
				// 诉它数据加载完毕;
				break;
			}
			case WHAT_DID_REFRESH: {

				mAdapter.notifyDataSetChanged();
				// 告诉它更新完毕
				break;
			}

			case WHAT_DID_MORE: {

				if (msg.obj != null) {

					getDiaryList();
				}

				break;

			}
			case COUNTLIST: {
				// 加载数据 本类使用
				getDiaryList();
				break;

			}
			case TOASTSHOW: {
				Toast.makeText(NoticeListActivity.this, "已经是最后一条记录了！",
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

	// 方法：获取信息列表
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
						// 获取JSONArray
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
					System.out.println("失败！");
					System.out.println(e);
					e.printStackTrace();

				}
			}
		}.start();

	}

	// 方法：获取信息列表
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

					// 新增修改
					String retStr = String.valueOf(result);
					if (retStr != null && !"anyType{}".equalsIgnoreCase(retStr)) {
						JSONObject jsonresult = new JSONObject(retStr);

						// 获取JSONArray
						JSONArray array = jsonresult.getJSONArray("root");
						if (array.length() > 0) {
							JSONObject obj = array.getJSONObject(0);
							if (obj.has("RCOUNT")) {
								countall = Integer.parseInt(obj.getString("RCOUNT")); // 总条数
								Log.i("EMS","----countall----"+countall+"-------");
								mUIHandler.sendEmptyMessage(COUNTLIST);
							}
						}
					}
				} catch (Exception e) {
					System.out.println("失败！");
					System.out.println(e);
					e.printStackTrace();

				}
			}
		}.start();

	}

}
