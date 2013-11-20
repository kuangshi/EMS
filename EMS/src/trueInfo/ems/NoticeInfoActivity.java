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
			// {��׺����MIME����}
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
		pd = ProgressDialog.show(NoticeInfoActivity.this, "���Ժ�", "���ڼ���...",true,true);
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
		txtInfo.setText("���ԣ�"+xm+"    ʱ�䣺"+sj);
		content = (TextView)findViewById(R.id.content);
		content.setText(nr);
		//fj
		fujian = (TextView)findViewById(R.id.txtFJ);
			fujian.setText("����鿴����");
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
		// ����intent��Action����
		intent.setAction(Intent.ACTION_VIEW);
		// ��ȡ�ļ�file��MIME����
		String type = getMIMEType(file);
		// ����intent��data��Type���ԡ�
		intent.setDataAndType(/* uri */Uri.fromFile(file), type);
		// ��ת
		startActivity(intent); // �������tryһ�£��п��ܻᱨ��
		// //����˵���MIME�����Ǵ����䣬�������ֻ�����ûװ����ͻ��ˣ��ͻᱨ��
		}catch(Exception e) {
			System.out.println("�򿪴���");
			System.out.print(e);
			e.printStackTrace();
		}
	}

	/**
	 * �����ļ���׺����ö�Ӧ��MIME���͡�
	 * 
	 * @param file
	 */
	private String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// ��ȡ��׺��ǰ�ķָ���"."��fName�е�λ�á�
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* ��ȡ�ļ��ĺ�׺�� */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// ��MIME���ļ����͵�ƥ������ҵ���Ӧ��MIME���͡�
		for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??��������һ�������ʣ����MIME_MapTable��ʲô��
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
					Toast.makeText(NoticeInfoActivity.this, "�޸���", Toast.LENGTH_SHORT).show();
				}else{
					showData(wjbt);
				}
				break;
			case 99:
				pd.dismiss();
				System.out.println("֪ͨ��������ʧ��");
				break;
				default:
					break;
			}
		}

	};
}

