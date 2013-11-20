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

public class Func_XsglActivity extends Activity {

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

		setContentView(R.layout.func_selector);

		// 初始化功能菜单
		initFuncGrids();
	}

	private void initFuncGrids() {

		GridView funcSeleView = (GridView) findViewById(R.id.func_selector);
		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.bg_xsgl01); // 添加图像资源的ID
		map.put("ItemText", "学生信息");
		lstImageItem.add(map);

		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.bg_xsgl02);
		map.put("ItemText", "学生成绩");
		lstImageItem.add(map);

		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.bg_xsgl03);
		map.put("ItemText", "报考信息");
		lstImageItem.add(map);
		

		// 重复内容

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
				i.setClass(Func_XsglActivity.this, XSXX_ManageActivity.class);
				startActivity(i);
				break;

			case 1:
				i = new Intent();
				i.setClass(Func_XsglActivity.this, XSCJ_ManageActivity.class);
				startActivity(i);
				break;
			case 2:
				i = new Intent();
				i.setClass(Func_XsglActivity.this, BaoKao_ManageActivity.class);
				startActivity(i);
				break;
			default:
				break;
			}
		}
	}
}
