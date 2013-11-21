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

public class Func_ZhglActivity2 extends Activity {

	private static final int TAKE_PHOTO_ID = Menu.FIRST;
	private static final int UPLOAD_PHOTO_ID = Menu.FIRST + 1;
	private static final int EXIT_ID = Menu.FIRST + 3;
	protected static final int GUIUPDATEIDENTIFIER = 0x101;
	protected static final int GUIUPDATEIDENTIFIER1 = 0x102;

	String strLog, strjw, uno;
	ProgressDialog pd = null; // ProgressDialog对象引用

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Intent intent = getIntent(); // 获得启动该Activity的Intent
		uno=SharedPrefsUtil.getValue(this, "CommunityID", "");

		setContentView(R.layout.func_selector);

		// 初始化功能菜单
		initFuncGrids();
	}

	private void initFuncGrids() {

		GridView funcSeleView = (GridView) findViewById(R.id.func_selector);
		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.fawen);
		map.put("ItemText", "学校管理");
		lstImageItem.add(map);

		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.qings00);
		map.put("ItemText", "专业管理");
		lstImageItem.add(map);


		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.b11);
		map.put("ItemText", "课程管理");
		lstImageItem.add(map);
		
		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.b10);
		map.put("ItemText", "报考管理");
		lstImageItem.add(map);

		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.grwj);
		map.put("ItemText", "财务管理");
		lstImageItem.add(map);
		

		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems = new SimpleAdapter(this, // 没什么解释
				lstImageItem, // 数据来源
				R.layout.night_item, // night_item的XML实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemText" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// null);
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
				i.setClass(Func_ZhglActivity2.this,
						TodoList_ManageActivity.class);
				i.putExtra("uno", uno);
				i.putExtra("dbfl", "06");
				startActivity(i);
				break;
			case 1:
				i = new Intent();
				i.setClass(Func_ZhglActivity2.this,
						TodoList_ManageActivity.class);
				i.putExtra("uno", uno);
				i.putExtra("dbfl", "07");
				startActivity(i);
				break;
			case 2:
				i = new Intent();
				i.setClass(Func_ZhglActivity2.this,
						TodoList_ManageActivity.class);
				i.putExtra("uno", uno);
				i.putExtra("dbfl", "08");
				startActivity(i);
				break;
			case 3:
				i = new Intent();
				i.setClass(Func_ZhglActivity2.this,
						TodoList_ManageActivity.class);
				i.putExtra("uno", uno); // 设置Intent的Extra字段
				i.putExtra("dbfl", "09");
				//i.putExtra("TableName", "ZHBG_QSBG_JBXX");
				startActivity(i);

				break;
			case 4:
				i = new Intent();
				i.setClass(Func_ZhglActivity2.this,
						TodoList_ManageActivity.class);
				i.putExtra("uno", uno); // 设置Intent的Extra字段
				i.putExtra("dbfl", "10");
				startActivity(i);
				break;
//			case 5:
//
//				i = new Intent();
//				i.setClass(Func_GrsyActivity.this,
//						TodoList_ManageActivity.class);
//				i.putExtra("uno", uno); // 设置Intent的Extra字段
//				i.putExtra("dbfl", "02");
//				i.putExtra("TableName", "ZHBG_HYJY_JBXX");
//				startActivity(i);
//
//				break;
//			case 6:
//
//				i = new Intent();
//				i.setClass(Func_GrsyActivity.this,
//						TodoList_ManageActivity.class);
//				i.putExtra("uno", uno); // 设置Intent的Extra字段
//				i.putExtra("dbfl", "02");
//				i.putExtra("TableName", "ZHBG_DQGL_JBXX");
//				startActivity(i);
//
//				break;
//			case 7:
//
//				i = new Intent();
//				i.setClass(Func_GrsyActivity.this,
//						TodoList_ManageActivity.class);
//				i.putExtra("uno", uno); // 设置Intent的Extra字段
//				i.putExtra("dbfl", "02");
//				i.putExtra("TableName", "ZHBG_XZSW_JBXX");
//				startActivity(i);
//
//				break;
//			case 8:
//
//				i = new Intent();
//				i.setClass(Func_GrsyActivity.this,
//						TodoList_ManageActivity.class);
//				i.putExtra("uno", uno); // 设置Intent的Extra字段
//				i.putExtra("dbfl", "02");
//				i.putExtra("TableName", "ZHBG_BMSW_JBXX");
//				startActivity(i);
//
//				break;
		
			default:
				break;
			}
		}
	}
}
