package trueInfo.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import trueInfo.util.PublicClass;
import android.util.Log;

public class WebServices {

	List<JSONObject> list_jsonObject = new ArrayList<JSONObject>();

	public List getObject(String code, String info, String sNum, String yhnm) {

		// ָ��WebService�������ռ�͵��õķ�����
		SoapObject request = new SoapObject(PublicClass.NAMESPACE,
				PublicClass.METHOD_NAME);

		request.addProperty("Code", code);
		request.addProperty("Info", info);
		request.addProperty("SNum", sNum);
		request.addProperty("yhnm", yhnm);

		// ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request); // �ȼ���envelope.bodyOut = request;

		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				PublicClass.URL);

		try {
			// ����WebService
			androidHttpTransport.call(PublicClass.SOAP_ACTION, envelope);

			Object retObj = envelope.getResponse();

			// �����޸�
			String retStr = String.valueOf(retObj);

			Log.i("WebService.TAG", "===========" + retStr);

			if (retStr != null && !(retStr.equals("anyType{}"))) {
				JSONObject jsonresult = new JSONObject(retStr);
				JSONObject obj;
				// ��ȡJSONArray
				JSONArray array = jsonresult.getJSONArray("root");

				for (int i = 0; i < array.length(); i++) {
					obj = array.getJSONObject(i);
					list_jsonObject.add(obj);
				}

				System.out.println("web�����سɹ���");
				return list_jsonObject;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("web������ʧ�ܣ�");
			System.out.println(e);
			e.printStackTrace();
			return null;
		}
	}

	public void mend_data(String code, String info, String sNum, String yhnm) {
		try {
			String resultStr = "";
			SoapObject soapObject = new SoapObject(PublicClass.NAMESPACE,
					PublicClass.METHOD_NAME_UPDATE);
			soapObject.addProperty("Code", code);
			soapObject.addProperty("Info", info);
			soapObject.addProperty("SNum", sNum);
			soapObject.addProperty("yhnm", yhnm);
			
			Log.i("examSys",code+"----"+info+"----"+sNum+"----"+yhnm);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(soapObject);
			envelope.bodyOut = soapObject;
			envelope.dotNet = true;
			envelope.encodingStyle = SoapEnvelope.ENC;

			@SuppressWarnings("deprecation")
			HttpTransportSE httpTranstation = new HttpTransportSE(
					PublicClass.URL);

			httpTranstation.call(PublicClass.SOAP_ACTION_UPDATE, envelope);
			Object result = envelope.getResponse();

			String resStr = String.valueOf(result);

			Log.i("examSys", "--------�����ļ�-------" + resStr);

			String str = "anyType{}";

			if (envelope.getResponse() != null & !str.equalsIgnoreCase(resStr)) {

				JSONObject jsonresult = new JSONObject(resStr);

				// ��ȡJSONArray
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
				Log.i("examSys","----------------update--"+resultStr);
			}

		} catch (Exception e) {
			System.out.println("ExamSys---------ʧ�ܣ�");
			System.out.println("ExamSys--------------" + e);
			e.printStackTrace();
		}
	}

}
