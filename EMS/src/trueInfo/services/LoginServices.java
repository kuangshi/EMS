package trueInfo.services;

import static trueInfo.util.PublicClass.METHOD_NAME;
import static trueInfo.util.PublicClass.NAMESPACE;
import static trueInfo.util.PublicClass.SOAP_ACTION;
import static trueInfo.util.PublicClass.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

import trueInfo.util.MD5;

public class LoginServices {

	/**
	 * ����webservice��֤�û���������
	 */

	private static String NBBM = ""; // ���
	private static String CommunityID="";
	private static String UNO="";

	public static String GetCommunityID()
	{
		return CommunityID;
	}
	
	public static void SetCommunityID(String _communityId)
	{
		CommunityID=_communityId;
	}
	
	public static String GetNBBM() {

		return NBBM;
	}

	public static void SetNBBM(String _nbbm) {
		NBBM = _nbbm;
	}

	public static boolean LoginCheck(String username, String password) {

		
		
		//		return true;
		// ָ��WebService�������ռ�͵��õķ�����
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			
		request.addProperty("Code", "0205");
		request.addProperty("Info", "{\"root\":[{\"TableName\":\"XTWH_QXWH_YHGL\"," +
				"\"PNum\":\"1\",\"PRows\":\"10\",\"DLZH\":\""+username+"\",\"DLMM\":\""+MD5.md5(password)+"\"}]}");
		request.addProperty("SNum", "0394cb6d-2575-4f73-92e9-6d402c39e20c");
		request.addProperty("yhnm", "ED5B2CD1643A40CFB05AE2FB1F1CEC9D");

		// ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request); // �ȼ���envelope.bodyOut = request;

		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		
		
		try {
			// ����WebService
			androidHttpTransport.call(SOAP_ACTION, envelope);

			Object retObj = envelope.getResponse();

			// �����޸�

			String retStr = String.valueOf(retObj);
			Log.i("moa", "---------------"+retStr);
			
			if (retStr != null && retStr != "anyType{}") {
				JSONObject jsonresult = new JSONObject(retStr);

				// ��ȡJSONArray
				JSONArray array = jsonresult.getJSONArray("root");

				if (array.length() > 0) {

					JSONObject obj = array.getJSONObject(0);
						SetNBBM(obj.getString("NBBM"));
						//SetCommunityID(obj.getString(""));
						return true;

				} else {
					return false;
				}

			} else {

				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
			return false;
		}

	}
}
