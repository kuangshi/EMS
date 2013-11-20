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
	ProgressDialog pd = null; // ProgressDialog��������

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.func_selector);

		// ��ʼ�����ܲ˵�
		initFuncGrids();
	}

	private void initFuncGrids() {

		GridView funcSeleView = (GridView) findViewById(R.id.func_selector);
		// ���ɶ�̬���飬����ת������
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.bg_xsgl01); // ���ͼ����Դ��ID
		map.put("ItemText", "ѧ����Ϣ");
		lstImageItem.add(map);

		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.bg_xsgl02);
		map.put("ItemText", "ѧ���ɼ�");
		lstImageItem.add(map);

		map = new HashMap<String, Object>();
		map.put("ItemImage", R.drawable.bg_xsgl03);
		map.put("ItemText", "������Ϣ");
		lstImageItem.add(map);
		

		// �ظ�����

		// ������������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ
		SimpleAdapter saImageItems = new SimpleAdapter(this, // ûʲô����
				lstImageItem, // ������Դ
				R.layout.night_item, // night_item��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				new String[] { "ItemImage", "ItemText" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// null);
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
