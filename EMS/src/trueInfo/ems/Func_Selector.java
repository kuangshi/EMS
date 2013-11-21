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

public class Func_Selector extends Activity {
	private static final int TAKE_PHOTO_ID = Menu.FIRST;
	private static final int UPLOAD_PHOTO_ID = Menu.FIRST + 1;
	private static final int EXIT_ID = Menu.FIRST + 3;
	protected static final int GUIUPDATEIDENTIFIER = 0x101;
	protected static final int GUIUPDATEIDENTIFIER1 = 0x102;

	GridView funcSeleView;
	String strLog, strjw, uno;
	String OPID = null;

	ProgressDialog pd = null; // ProgressDialog��������
	int countall = 0;
	BadgeView badge1 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Intent intent = getIntent(); // ���������Activity��Intent
		uno = SharedPrefsUtil.getValue(this, "CommunityID", "");
		OPID = SharedPrefsUtil.getValue(this, "OPID", "");
		setContentView(R.layout.func_selector);
		// ��ʼ�����ܲ˵�
		initFuncGrids();
	}

	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				break;
			case 1:
				if (countall > 0) {
					if (badge1 != null && badge1.isShown()) {
						badge1.setText(String.valueOf(countall));

					} else {
						// ��ʼ��������Ϣ
						LinearLayout rlayout = (LinearLayout) (funcSeleView
								.getChildAt(4)); //
						ImageView imgView2 = (ImageView) rlayout
								.findViewById(R.id.ItemImage);
						badge1 = new BadgeView(getBaseContext(), imgView2);
						badge1.setText(String.valueOf(countall));
						badge1.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
						badge1.show();
					}

				} else {
					Log.i("moa", "moa-------------�������ôû�䰡");
					if (badge1 != null) {
						badge1.hide();
						// badge1.toggle();
					}
				}
				break;
			case 98:
				// �����������쳣
				// Toast.makeText(getBaseContext(), "û�����ݣ�", Toast.LENGTH_SHORT)
				// .show();
				break;
			case 99:
				// ������δ������쳣
				// Toast.makeText(getBaseContext(), "�������쳣,���Ժ����Ի���ϵ����Ա��",
				// Toast.LENGTH_SHORT).show();
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void initFuncGrids() {

		funcSeleView = (GridView) findViewById(R.id.func_selector);
		// ���ɶ�̬���飬����ת������
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.huiy00);
		map.put("ItemText", "����֪ͨ");
		lstImageItem.add(map);

//		map = new HashMap<String, Object>();
//		map.put("ItemImage", R.drawable.b10);
//		map.put("ItemText", "������Ϣ");
//		lstImageItem.add(map);

		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.qings00);
		map.put("ItemText", "�û�����");
		lstImageItem.add(map);
//
//		map = new HashMap<String, Object>();
//		map.put("ItemImage", R.drawable.fawen);
//		map.put("ItemText", "ʵ������");
//		lstImageItem.add(map);

//		map = new HashMap<String, Object>();
//		map.put("ItemImage", R.drawable.b11);
//		map.put("ItemText", "�ʼ�֪ͨ");
//		lstImageItem.add(map);

		// ������������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ
		SimpleAdapter saImageItems = new SimpleAdapter(this, // ûʲô����
				lstImageItem, // ������Դ
				R.layout.night_item, // night_item��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				new String[] { "ItemImage", "ItemText" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// ��Ӳ�����ʾ
		funcSeleView.setAdapter(saImageItems);
		// saImageItems.notifyDataSetChanged();

		// �����Ϣ����
		funcSeleView.setOnItemClickListener(new ItemClickListener());

	}

	// ��AdapterView������(���������߼���)���򷵻ص�Item�����¼�
	class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, // The AdapterView where
				// the click happened
				View arg1, // The view within the AdapterView that was clicked
				int arg2, // The position of the view in the adapter
				long arg3 // The row id of the item that was clicked
		) {
			// �ڱ�����arg2=arg3
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);

			((SimpleAdapter) arg0.getAdapter()).notifyDataSetChanged();

			Intent i;
			switch (arg2) {
			case 0:
				i = new Intent();
				i.setClass(Func_Selector.this, TodoList_ManageActivity.class);
			//	i.putExtra("uno", uno); // ����Intent��Extra�ֶ�
				i.putExtra("dbfl", "01");
				startActivity(i);
				break;

			case 1:
				i = new Intent();
				i.setClass(Func_Selector.this, TodoList_ManageActivity.class);
				i.putExtra("uno", uno);
				i.putExtra("dbfl", "02");
				//i.putExtra("TableName", "ZHBG_SBZL_JBXX");
				startActivity(i);
				break;
//			case 2:
//				i = new Intent();
//				i.setClass(Func_Selector.this, TodoList_ManageActivity.class);
//				i.putExtra("uno", uno); // ����Intent��Extra�ֶ�
//				i.putExtra("dbfl", "02");
//				i.putExtra("TableName", "ZHBG_SBZL_JBXX");
//				startActivity(i);
//				break;
//			case 3:
//				i = new Intent();
//				i.setClass(Func_Selector.this,TodoList_ManageActivity.class);
//				i.putExtra("uno", uno); // ����Intent��Extra�ֶ�
//				i.putExtra("dbfl", "02");
//				i.putExtra("TableName", "ZHBG_SBZL_JBXX");
//				startActivity(i);
//				break;
			default:
				break;
			}
		}

	}

	

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.i("moa", "moa------------onRestart----------------");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		Log.i("moa", "moa------------onResume----------------");
		super.onResume();
	}

}