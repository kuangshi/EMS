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

	ProgressDialog pd = null; // ProgressDialog对象引用
	int countall = 0;
	BadgeView badge1 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Intent intent = getIntent(); // 获得启动该Activity的Intent
		uno = SharedPrefsUtil.getValue(this, "CommunityID", "");
		OPID = SharedPrefsUtil.getValue(this, "OPID", "");
		setContentView(R.layout.func_selector);
		// 初始化功能菜单
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
						// 初始化浮标信息
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
					Log.i("moa", "moa-------------很奇怪怎么没变啊");
					if (badge1 != null) {
						badge1.hide();
						// badge1.toggle();
					}
				}
				break;
			case 98:
				// 出现了网络异常
				// Toast.makeText(getBaseContext(), "没有数据！", Toast.LENGTH_SHORT)
				// .show();
				break;
			case 99:
				// 出现了未捕获的异常
				// Toast.makeText(getBaseContext(), "出现了异常,请稍候再试或联系管理员！",
				// Toast.LENGTH_SHORT).show();
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void initFuncGrids() {

		funcSeleView = (GridView) findViewById(R.id.func_selector);
		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.huiy00);
		map.put("ItemText", "公告通知");
		lstImageItem.add(map);

//		map = new HashMap<String, Object>();
//		map.put("ItemImage", R.drawable.b10);
//		map.put("ItemText", "个人信息");
//		lstImageItem.add(map);

		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.qings00);
		map.put("ItemText", "用户管理");
		lstImageItem.add(map);
//
//		map = new HashMap<String, Object>();
//		map.put("ItemImage", R.drawable.fawen);
//		map.put("ItemText", "实践评分");
//		lstImageItem.add(map);

//		map = new HashMap<String, Object>();
//		map.put("ItemImage", R.drawable.b11);
//		map.put("ItemText", "邮件通知");
//		lstImageItem.add(map);

		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems = new SimpleAdapter(this, // 没什么解释
				lstImageItem, // 数据来源
				R.layout.night_item, // night_item的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemText" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// 添加并且显示
		funcSeleView.setAdapter(saImageItems);
		// saImageItems.notifyDataSetChanged();

		// 添加消息处理
		funcSeleView.setOnItemClickListener(new ItemClickListener());

	}

	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, // The AdapterView where
				// the click happened
				View arg1, // The view within the AdapterView that was clicked
				int arg2, // The position of the view in the adapter
				long arg3 // The row id of the item that was clicked
		) {
			// 在本例中arg2=arg3
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);

			((SimpleAdapter) arg0.getAdapter()).notifyDataSetChanged();

			Intent i;
			switch (arg2) {
			case 0:
				i = new Intent();
				i.setClass(Func_Selector.this, TodoList_ManageActivity.class);
			//	i.putExtra("uno", uno); // 设置Intent的Extra字段
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
//				i.putExtra("uno", uno); // 设置Intent的Extra字段
//				i.putExtra("dbfl", "02");
//				i.putExtra("TableName", "ZHBG_SBZL_JBXX");
//				startActivity(i);
//				break;
//			case 3:
//				i = new Intent();
//				i.setClass(Func_Selector.this,TodoList_ManageActivity.class);
//				i.putExtra("uno", uno); // 设置Intent的Extra字段
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