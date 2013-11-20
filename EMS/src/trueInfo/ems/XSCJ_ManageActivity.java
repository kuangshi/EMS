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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class XSCJ_ManageActivity extends Activity {
	
	public static String TAG = "XSCJ";
	private String xjbh = "";
	ProgressDialog pd;
	MyAdapter myAdapter;
	ListView _list;
	
	List<String[]> list = new ArrayList<String[]>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.xscj_manage_activity);
		initActivity();
	}

	private void initActivity() {
		// TODO Auto-generated method stub
		myAdapter = new MyAdapter();
		_list = (ListView)this.findViewById(R.id._list);
		_list.setAdapter(myAdapter);
	}

	public void btn_click(View v) {
		switch(v.getId()) {
		case R.id.reback_btn:
			finish();
			break;
		case R.id.btn_ok:
			EditText txt_zjhm = (EditText)findViewById(R.id.txtId);
			xjbh = txt_zjhm.getText().toString();
			if(xjbh.length() == 0) {
				Toast.makeText(XSCJ_ManageActivity.this, "请输入学籍编号！", Toast.LENGTH_SHORT).show();
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
				//由学籍内码（编号）取得考试成绩表中的课程内码和考试成绩
				try {
					String code = "0205";
					String info = "{\"root\":[{\"TableName\":\"JWGL_XSGL_KSCJ\",\"PNum\":\"1\",\"PRows\":\"100\",\"XJNM\":\""
							+ xjbh + "\"}]}";
					Log.i(TAG, "------------------" + info);
					String sNum = "0394cb6d-2575-4f73-92e9-6d402c39e20c";
					String yhnm = LoginServices.GetNBBM();
					List<JSONObject> list_ = new WebServices().getObject(code,
							info, sNum, yhnm);
					for (int i = 0; i < list_.size(); i++) {
						JSONObject object = list_.get(i);
						String[] str = new String[] {
								object.getString("NBBM").toString(),
								object.getString("KCNM").toString(),
								object.getString("KSCJ").toString(),
								object.getString("KCMC").toString(),
								object.getString("KCBH").toString()};
						list.add(str);
					}

					myHander.sendEmptyMessage(1);
				} catch (Exception e) {
					myHander.sendEmptyMessage(99);
					System.out.print(e);
					e.getStackTrace();
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
				if(list.size()>0) {
					System.out.println("成绩下载成功");
					myAdapter.refresh();
				}else{
					Toast.makeText(XSCJ_ManageActivity.this, "无数据！", Toast.LENGTH_SHORT).show();
				}
				break;
			case 2:
				pd.dismiss();
				break;
			case 99:
				pd.dismiss();
				Toast.makeText(XSCJ_ManageActivity.this, "下载失败！", Toast.LENGTH_SHORT).show();
				default:
					break;
			}
		}
	};
	
	
	class MyAdapter extends BaseAdapter {

		public MyAdapter() {
			super();
			// TODO Auto-generated constructor stub
		}

		public void refresh() {
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position - 1);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.list_item_2line, null);

			}
			if (list.size() > position) {
				ImageView ivImage = (ImageView) convertView
						.findViewById(R.id.bulletImg);
				ivImage.setImageResource(R.drawable.file_icon_green2);

				// 标题

				TextView tvContent = (TextView) convertView
						.findViewById(R.id.newscontent);

				tvContent.setText(list.get(position)[3]);

				// 名称
				TextView tvHJMC = (TextView) convertView
						.findViewById(R.id.HJMC);

				String HJMC = list.get(position)[4];
				tvHJMC.setText("[" + HJMC + "]");

				// 时间
				TextView tvDJSJ = (TextView) convertView
						.findViewById(R.id.DJSJ);
				tvDJSJ.setText("成绩 ："+list.get(position)[2]);
				
				// tvDJSJ.setText(DJSJ.substring(0, DJSJ.indexOf(" ")));
			} else {
				Toast.makeText(XSCJ_ManageActivity.this, "操作过于频繁！",
						Toast.LENGTH_SHORT).show();
			}
			return convertView;
		}

	}

}
