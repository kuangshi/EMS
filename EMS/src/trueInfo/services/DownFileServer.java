package trueInfo.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import trueInfo.util.PublicClass;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class DownFileServer {

	String wjbt="", zwnm,BMMC;
	File file;

	// String BMMC;
	// 下载附件。。。。
	public String downFile(Context context, String BMMC, String nbbm) {
		this.BMMC = BMMC;

		SoapObject soapObject = new SoapObject(PublicClass.NAMESPACE,
				PublicClass.METHOD_NAME);

		soapObject.addProperty("Code", "0202");
		soapObject.addProperty("Info", "{\"root\":[{\"TableName\":\"" + BMMC
				+ "\",\"jlnm\":\"" + nbbm + "\"}]}");
		soapObject.addProperty("SNum", "0394cb6d-2575-4f73-92e9-6d402c39e20c");
		soapObject.addProperty("yhnm", "ED5B2CD1643A40CFB05AE2FB1F1CEC9D");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(soapObject);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		envelope.encodingStyle = SoapEnvelope.ENC;

		@SuppressWarnings("deprecation")
		HttpTransportSE httpTranstation = new HttpTransportSE(PublicClass.URL);
		try {

			httpTranstation.call(PublicClass.SOAP_ACTION, envelope);
			Object result = envelope.getResponse();

			String resStr = String.valueOf(result);

			String str = "anyType{}";

			if (envelope.getResponse() != null & !str.equalsIgnoreCase(resStr)) {
				JSONObject jsonresult = new JSONObject(resStr);

				// 获取JSONArray
				JSONArray array = jsonresult.getJSONArray("root");

				if (array.length() > 0) {

					JSONObject obj = array.getJSONObject(0);

					if (obj.has("NBBM")) {
						zwnm = obj.getString("NBBM");
						wjbt = obj.getString("WJBT");
						//wjbt="2.xls";
						
					} else {
						zwnm = "";
					}
					Log.i("zwnm_wjbt",zwnm+"-----正文内码+文件标题----"+wjbt);

				} else {
					zwnm = "";
				}

				// myHandler.sendEmptyMessage(1);
			}
		} catch (Exception e) {
			System.out.println("文件下载失败！");
			System.out.println(e);
			e.printStackTrace();
		} finally {
			// myHandler.sendEmptyMessage(1);
		}
		
		/*
		 * 如果文件存在： 下载。。。
		 */
		if (!(wjbt.equals(""))) {
			downFile();
		}

		return wjbt;

	}

	private void downFile() {
		// TODO Auto-generated method stub
		String extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();
		File folder = new File(extStorageDirectory, "ExamSys");
		folder.mkdir();
		// file = new File(folder, "Read.pdf");
		//测试
		//wjbt = "1.txt";
		Log.i("file-wjbt", folder + "--------folder-wjbt--------" + wjbt);
		file = new File(folder, wjbt);
		Log.i("file-wjbt", "--------file---------" + file.toString());
		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		SoapObject soapObject1 = new SoapObject(PublicClass.NAMESPACE,
				PublicClass.METHOD_NAME_BYTE);

		soapObject1.addProperty("Code", "0101");
		soapObject1.addProperty("Info", "{\"root\":[{\"TableName\":\"" + BMMC
				+ "\",\"wjfl\":\"附件\",\"nbbm\":\"" + zwnm + "\"}]}");
		soapObject1.addProperty("SNum", "0394cb6d-2575-4f73-92e9-6d402c39e20c");
		soapObject1.addProperty("yhnm", "ED5B2CD1643A40CFB05AE2FB1F1CEC9D");

		SoapSerializationEnvelope envelope1 = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope1.setOutputSoapObject(soapObject1);
		envelope1.bodyOut = soapObject1;
		envelope1.dotNet = true;
		envelope1.encodingStyle = SoapEnvelope.ENC;

		@SuppressWarnings("deprecation")
		HttpTransportSE httpTranstation1 = new HttpTransportSE(PublicClass.URL);
		try {

			httpTranstation1.call(PublicClass.SOAP_ACTION_BYTE, envelope1);
			Object result = envelope1.getResponse();

			String resStr = String.valueOf(result);

			String str = "anyType{}";
			if (envelope1.getResponse() != null & !str.equalsIgnoreCase(resStr)) {

				String retString = String.valueOf(result);
				byte[] m_out_bytes = Base64.decode(retString);

				FileOutputStream FOS = new FileOutputStream(file);
				// 创建写入文件内存流，通过此流向目标写文件
				FOS.write(m_out_bytes, 0, m_out_bytes.length);
				System.out.println("文件下载成功！");
				FOS.flush();
				if (FOS != null) {
					FOS.close();
				}

				// myHandler.sendEmptyMessage(0);

			}
		} catch (Exception e) {
			System.out.println("文件下载失败！");
			System.out.println(e);
			e.printStackTrace();

		} finally {
			// myHandler.sendEmptyMessage(1);
		}
	}

	// Handler myHandler = new Handler() {
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case 0:
	// break;
	// case 1:
	// break;
	// case 2:
	// break;
	// }
	// }
	// };
}
