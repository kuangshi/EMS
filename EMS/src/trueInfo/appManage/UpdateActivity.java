package trueInfo.appManage;

import java.io.File;
import java.io.FileOutputStream;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import dalvik.system.VMRuntime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import static trueInfo.appManage.Config.ProductID;

public class UpdateActivity extends Activity {
	private static final String TAG = "Update";
	public ProgressDialog pBar;
	private Handler handler = new Handler();

	private int newVerCode = 0;
	private String newVerName = "";
	
	private final static float TARGET_HEAP_UTILIZATION = 0.75f; 
	private final static long NewSize =24*1024*1024;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);

		
		VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION);
		VMRuntime.getRuntime().setMinimumHeapSize(NewSize); 
		
		if (getServerVerCode()) {
			doNewVersionUpdate();
		} else {
			notNewVersionShow();
		}

	}

	private boolean getServerVerCode() {
		try {
			String verjson = NetworkTool.getContent(Config.NAMESPACE,
					Config.METHOD_NAME, Config.URL, Config.SOAP_ACTION);
			Log.d("verjson", verjson);
			try {
				if(verjson!="")
				{
					newVerName=verjson;
					return true;
				}else
				{
					return true;
				}
				
//				JSONObject result = new JSONObject(verjson);// 转换为JSONObject
//				int num = result.length();
//				Log.d("result", String.valueOf(num));
//				JSONArray array = result.getJSONArray("UserPosition");// 获取JSONArray
//				// JSONArray array = new JSONArray(verjson);
//				Log.d("array", array.toString());
//				if (array.length() > 0) {
//					JSONObject obj = array.getJSONObject(0);
//					Log.d("obj", obj.getString("Coordinate"));
//					Log.d("obj", obj.getString("Id"));
//					try {
//						newVerCode = Integer.parseInt(obj.getString("Id"));
//
//						newVerName = obj.getString("Coordinate");
//						Log.d("JSONObject", obj.getString("Coordinate"));
//						int vercode = Config.getVerCode(this);
//						
//						if (newVerCode > vercode) {
//							return true;
//						} else {
//							return false;
//						}
//
//					} catch (Exception e) {
//						newVerCode = -1;
//						newVerName = "";
//						return false;
//					}
//				}
			} catch (Exception e) {
				Log.e("JSONArray", e.getMessage());
				return false;
			}

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return false;
		}
		
	}

	private void notNewVersionShow() {
		int verCode = Config.getVerCode(this);
		String verName = Config.getVerName(this);
		StringBuffer sb = new StringBuffer();
		sb.append("版本名称:");
		sb.append(verName);
		sb.append("版本号:");
		sb.append(verCode);
		sb.append(",\n无更新!");
		Dialog dialog = new AlertDialog.Builder(UpdateActivity.this).setTitle(
				"提示").setMessage(sb.toString())// 
				.setPositiveButton("确定",// 
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}

						}).create();
		dialog.show();
	}

	private void doNewVersionUpdate() {
		int verCode = Config.getVerCode(this);
		String verName = Config.getVerName(this);
		StringBuffer sb = new StringBuffer();
		sb.append("版本名称:");
		sb.append(verName);
		sb.append("版本号:");
		sb.append(verCode);
		sb.append(",新版本名称");
		sb.append(newVerName);
		sb.append("新版本号:");
		sb.append(newVerCode);
		sb.append(", 有更新");
		Dialog dialog = new AlertDialog.Builder(UpdateActivity.this).setTitle(
				"是否更新").setMessage(sb.toString())

		.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				pBar = new ProgressDialog(UpdateActivity.this);
				pBar.setTitle("下载中");
				pBar.setMessage("程序下载中，请稍候..");
				pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				downFile("");
			}

		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {

				finish();
			}
		}).create();
		dialog.show();
	}

	void downFile(final String url) {
		pBar.show();
		new Thread() {
			@Override
			public void run() {

				SoapObject soapObject = new SoapObject(Config.NAMESPACE,
						Config.METHOD_NAME_GetAPK);
				soapObject.addProperty("ProductID", ProductID);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.setOutputSoapObject(soapObject);
				envelope.bodyOut = soapObject;
				envelope.dotNet = true;
				envelope.encodingStyle = SoapEnvelope.ENC;
				@SuppressWarnings("deprecation")
				HttpTransportSE httpTranstation = new HttpTransportSE(
						Config.URL);
				try {
					
					httpTranstation.call(Config.SOAP_ACTION_GetAPK, envelope);
					
					Object result = envelope.getResponse();
					
					if (envelope.getResponse() != null) {
					
						//Log.i("connectWebService", result.toString());

						String retString = String.valueOf(result);
						byte[] m_out_bytes = Base64.decode(retString);
						// 下载到本地
						File saveFile = new File(Environment
								.getExternalStorageDirectory(),
								Config.UPDATE_SAVENAME);

						FileOutputStream FOS = new FileOutputStream(saveFile);
						// 创建写入文件内存流，通过此流向目标写文件
						FOS.write(m_out_bytes, 0, m_out_bytes.length);
						System.out.println("成功！");
						FOS.flush();
						if (FOS != null) {
							FOS.close();
						}
						down();
					}

				} catch (Exception e) {
					System.out.println("失败！");
					System.out.println(e);
					e.printStackTrace();
				}
			}

		}.start();

	}

	void down() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				pBar.cancel();
				update();
			}
		});

	}

	void update() {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), Config.UPDATE_SAVENAME)),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

}