package trueInfo.ems;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import trueInfo.services.LoginServices;
import trueInfo.util.PublicClass;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DocDealLXFSActivity extends Activity {
	// private MyDialog dialog;
	private LinearLayout layout;
	String NBBM;
	String CLYJ = "";
	String BMMC = "";
	String XGZD = "";
	String resultStr = "0";


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.doc_deal);
		// dialog=new MyDialog(this);
		
		NBBM = getIntent().getStringExtra("NBBM");
		BMMC = getIntent().getStringExtra("BMMC");
		XGZD = getIntent().getStringExtra("XGZD");
	}

	private void doDispose() {
		// 再次启动新线程获取文档按钮状态
		new Thread() {
			@Override
			public void run() {
				try {

					SoapObject soapObject = new SoapObject(PublicClass.NAMESPACE,
							PublicClass.METHOD_NAME_UPDATE);

					soapObject.addProperty("Code", "0202");
					soapObject.addProperty("Info",
							"{\"root\":[{\"TableName\":\"" + BMMC
									+ "\",\"NBBM\":\"" + NBBM
									+ "\",\""+XGZD+"\":\"" + CLYJ + "\"}]}");
					soapObject.addProperty("SNum",
							"0394cb6d-2575-4f73-92e9-6d402c39e20c");
					soapObject.addProperty("yhnm", LoginServices.GetNBBM());


					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
							SoapEnvelope.VER11);
					envelope.setOutputSoapObject(soapObject);
					envelope.bodyOut = soapObject;
					envelope.dotNet = true;
					envelope.encodingStyle = SoapEnvelope.ENC;

					@SuppressWarnings("deprecation")
					HttpTransportSE httpTranstation = new HttpTransportSE(PublicClass.URL);

					httpTranstation.call(PublicClass.SOAP_ACTION_UPDATE,
							envelope);
					Object result = envelope.getResponse();

					String resStr = String.valueOf(result);

					Log.i("moa", "moa--------处理文件-------" + resStr);

					String str = "anyType{}";

					if (envelope.getResponse() != null
							& !str.equalsIgnoreCase(resStr)) {

						JSONObject jsonresult = new JSONObject(resStr);

						// 获取JSONArray
						JSONArray array = jsonresult.getJSONArray("root");

						if (array.length() > 0) {

							JSONObject obj = array.getJSONObject(0);
							if (obj.has("Result")) {
								resultStr = obj.getString("Result");
							} else {
								resultStr = "0";
							}

						} else {
							resultStr = "0";
						}

						myHandler.sendEmptyMessage(0);
					}

				} catch (Exception e) {
					resultStr = "0";
					myHandler.sendEmptyMessage(0);
					System.out.println("ExamSys---------失败！");
					System.out.println("ExamSys--------------" + e);
					e.printStackTrace();
				}
			}
		}.start();
	}

	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:

				if ("1".equalsIgnoreCase(resultStr)) {
					// TODO Auto-generated method stub
//					Toast.makeText(getApplicationContext(), "处理成功，窗口即将关闭！",
//							Toast.LENGTH_SHORT).show();
					setResult(20);
					finish();
				}
				break;
			case 1:
				break;

			}
			super.handleMessage(msg);
		}
	};

	// 对应“否”
	public void exitbutton1(View v) {
		this.finish();
	}

	// 对应“是”
	public void exitbutton0(View v) {
		// 第一步：准备需要传回服务器的值。
		EditText edtCLYJ = (EditText) findViewById(R.id.txtCLYJ);
		CLYJ = edtCLYJ.getEditableText().toString().trim();
		if("".equalsIgnoreCase(CLYJ))
		{
			Toast.makeText(DocDealLXFSActivity.this, "请填写联系方式！", Toast.LENGTH_SHORT).show();
		}else {
			// 第二步处理文件
			doDispose();
		}

	}

}
