package trueInfo.appManage;

import static trueInfo.appManage.Config.ProductID;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import trueInfo.ems.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class UpdateManager {
	/* ������ */
	private static final int GETVERTION_FINISH = 0;
	/* ������ */
	private static final int DOWNLOAD = 1;
	/* ���ؽ��� */
	private static final int DOWNLOAD_FINISH = 2;
	/* ��ȡ�Է���˵İ汾��������Ϣ */
	private int newVerCode = 0;
	private String newVerName = "";
	private String newDescription = "";

	/* ���ر���·�� */
	private String mSavePath;
	/* ��¼���������� */
	private int progress;
	/* �Ƿ�ȡ������ */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* ���½����� */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GETVERTION_FINISH:

				checkUpdate();
				break;
			// ��������
			case DOWNLOAD:
				// ���ý�����λ��
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// ��װ�ļ�
				installApk();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	/*
	 * ��ʼ������------׼������
	 */
	public void beginCheckUpdate() {
		// �����̻߳�ȡ���°汾��Ϣ
		new getNewVersionInfoThread().start();

	}

	/**
	 * ����������
	 */
	public void checkUpdate() {
		if (isUpdate()) {
			// ��ʾ��ʾ�Ի���
			showNoticeDialog();
		} else {
			Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * �������Ƿ��и��°汾
	 * 
	 * @return
	 */
	private boolean isUpdate() {
		// ��ȡ��ǰ����汾
		int versionCode = getVersionCode(mContext);

		Log.i("xymoa", "xymoa----------------"+String.valueOf(versionCode) );
		
		// �汾�ж�
		if (newVerCode > versionCode) {
			return true;
		}

		return false;
	}

	/**
	 * �ӷ������˻�ȡ���°汾��Ϣ
	 * 
	 */
	private class getNewVersionInfoThread extends Thread {
		@Override
		public void run() {
			String retString = "";

			SoapObject soapObject = new SoapObject(Config.NAMESPACE,
					Config.METHOD_NAME);
			soapObject.addProperty("ProductID", ProductID);
			soapObject.addProperty("SNum", "0394cb6d-2575-4f73-92e9-6d402c39e20c");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(soapObject);
			envelope.bodyOut = soapObject;
			envelope.dotNet = true;
			envelope.encodingStyle = SoapEnvelope.ENC;
			@SuppressWarnings("deprecation")
			HttpTransportSE httpTranstation = new HttpTransportSE(Config.URL);
			try {
				httpTranstation.call(Config.SOAP_ACTION, envelope);
				Object result = envelope.getResponse();
				retString = String.valueOf(result);

				String str = "anyType{}";

				Log.i("moa", "moa1111111:---------------" + retString);

				if (retString != null & !str.equalsIgnoreCase(retString)) {

					newVerCode = Integer.parseInt(retString);
					mHandler.sendEmptyMessage(GETVERTION_FINISH);

				}


			} catch (Exception e) {
				System.out.println("ʧ�ܣ�");
				System.out.println(e);
				e.printStackTrace();
			}

		}
	};

	/**
	 * ��ȡ����汾��
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// ��ȡ����汾�ţ���ӦAndroidManifest.xml��android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					"trueInfo.ems", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * ��ʾ������¶Ի���
	 */
	private void showNoticeDialog() {
		// ����Ի���
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		builder.setMessage(R.string.soft_update_info);
		// ����
		builder.setPositiveButton(R.string.soft_update_updatebtn,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// ��ʾ���ضԻ���
						showDownloadDialog();
					}
				});
		// �Ժ����
		builder.setNegativeButton(R.string.soft_update_later,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		Dialog noticeDialog = builder.create();
		
		noticeDialog.show();
	}

	/**
	 * ��ʾ������ضԻ���
	 */
	private void showDownloadDialog() {
		// ����������ضԻ���
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_updating);
		// �����ضԻ������ӽ�����
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		// ȡ������
		builder.setNegativeButton(R.string.soft_update_cancel,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// ����ȡ��״̬
						cancelUpdate = true;
					}
				});
		mDownloadDialog = builder.create();
		builder.setCancelable(true);
		mDownloadDialog.show();
		// �����ļ�
		downloadApk();
	}

	/**
	 * ����apk�ļ�
	 */
	private void downloadApk() {
		// �������߳��������
		new downloadApkThread().start();
	}

	/**
	 * �����ļ��߳�
	 * 
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {

				// �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// ��ô洢����·��
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";
					URL url = new URL(Config.SOFT_URL);
					// ��������
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// ��ȡ�ļ���С
					int length = conn.getContentLength();
					// ����������
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// �ж��ļ�Ŀ¼�Ƿ����
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, "EMS_1_0_1.APK");
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// ����
					byte buf[] = new byte[1024];
					// д�뵽�ļ���
					do {
						int numread = is.read(buf);
						count += numread;
						// ���������λ��
						progress = (int) (((float) count / length) * 100);
						// ���½���
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// �������
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// д���ļ�
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// ���ȡ����ֹͣ����.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// ȡ�����ضԻ�����ʾ
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * ��װAPK�ļ�
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, "EMS_1_0_1.APK");
		if (!apkfile.exists()) {
			return;
		}
		// ͨ��Intent��װAPK�ļ�
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
