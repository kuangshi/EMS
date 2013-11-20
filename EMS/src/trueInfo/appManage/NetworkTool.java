package trueInfo.appManage;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;
import static trueInfo.appManage.Config.ProductID;

public class NetworkTool {

	/**
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getContent(String NAMESPACE, String METHOD_NAME,
			String URL, String SOAP_ACTION) throws Exception {
		
		String retString = "";

		SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME);
		
		// 设置需调用WebService接口需要传入的两个参数username、password
		soapObject.addProperty("ProductID", ProductID);

		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(soapObject);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		envelope.encodingStyle = SoapEnvelope.ENC;
		@SuppressWarnings("deprecation")
		HttpTransportSE httpTranstation = new HttpTransportSE(URL);
		try {
			httpTranstation.call(SOAP_ACTION, envelope);
			Object result = envelope.getResponse();
			if (envelope.getResponse() != null) {
				Log.i("connectWebService", result.toString());

				retString = String.valueOf(result);

				System.out.println("成功！");
			}

		} catch (Exception e) {
			System.out.println("失败！");
			System.out.println(e);
			e.printStackTrace();
		}
		return retString;
	}
}
