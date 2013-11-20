package trueInfo.ems;

import java.io.File;

import trueInfo.services.DownFileServer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NoticeInfoActivity extends Activity {
	ProgressDialog pd;
	ImageView imgView;
	TextView title,content ,fujian,txtInfo;
	private String bt,xm,sj,nr,nbbm;
	private String wjbt;
	private final String[][] MIME_MapTable = {
			// {后缀名，MIME类型}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" },
			{ ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
			{ ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/x-zip-compressed" }, { "", "*/*" } };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.notice_infoactivity);
		
		getInfo();
		initActivity();
	}

	private void getDataFromWeb() {
		// TODO Auto-generated method stub
		pd = ProgressDialog.show(NoticeInfoActivity.this, "请稍候", "正在加载...",true,true);
		new Thread() {
			public void run() {
				try{
					wjbt = new DownFileServer().downFile(NoticeInfoActivity.this,"JWGL_NBGL_GGTZ",nbbm);
					myHander.sendEmptyMessage(0);
					
				}catch(Exception e) {
					System.out.println(e);
					e.printStackTrace();
					myHander.sendEmptyMessage(99);
				}
								
			}
		}.start();
	}

	private void getInfo() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();

		nbbm = intent.getStringExtra("NBBM");
		bt = intent.getStringExtra("BT");
		nr = intent.getStringExtra("NR");
		xm = intent.getStringExtra("XM");
		sj = intent.getStringExtra("SJ");
		
	}
	
	public void btn_back(View v) {
		this.finish();
	}

	private void initActivity() {
		// TODO Auto-generated method stub
		
		title = (TextView)findViewById(R.id.title);
		title.setText(bt);
		txtInfo = (TextView)findViewById(R.id.txtInfo);
		txtInfo.setText("来自："+xm+"    时间："+sj);
		content = (TextView)findViewById(R.id.content);
		content.setText(nr);
		//fj
		fujian = (TextView)findViewById(R.id.txtFJ);
			fujian.setText("点击查看附件");
			fujian.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 getDataFromWeb();
				}
				
			});

		
	}
	protected void showData(String name) {
		// TODO Auto-generated method stub
		try{
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/ExamSys/" +name);
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		// 获取文件file的MIME类型
		String type = getMIMEType(file);
		// 设置intent的data和Type属性。
		intent.setDataAndType(/* uri */Uri.fromFile(file), type);
		// 跳转
		startActivity(intent); // 这里最好try一下，有可能会报错。
		// //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
		}catch(Exception e) {
			System.out.println("打开错误");
			System.out.print(e);
			e.printStackTrace();
		}
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	private String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}
	
	Handler myHander = new Handler() {
		public void handleMessage(Message msg){
			switch(msg.what) {
			case 0:
				pd.dismiss();
				if(wjbt.equals("")) {
					Toast.makeText(NoticeInfoActivity.this, "无附件", Toast.LENGTH_SHORT).show();
				}else{
					showData(wjbt);
				}
				break;
			case 99:
				pd.dismiss();
				System.out.println("通知附件下载失败");
				break;
				default:
					break;
			}
		}

	};
}

