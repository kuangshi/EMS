package trueInfo.appManage;

import trueInfo.ems.R;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class Config {
	private static final String TAG = "Trueinfo.Config";

	public static final String ProductID="ZNDX-MOA-001";
	
	public static final String UPDATE_SAVENAME = "updateapksamples.apk";
	
	
	// ............................
	public static final String NAMESPACE = "http://tempuri.org/";
//	public static final String URL = "http://www.zb139.com/WebServer/CMservice/CMWebService.asmx";
	public static final String URL = "http://122.207.97.113/webservice/DBHelperOra.asmx";

	public static final String METHOD_NAME = "GetVersion";
	public static final String SOAP_ACTION = "http://tempuri.org/GetVersion";
	
	// ............................GetApk............................实际未使用
	public static final String METHOD_NAME_GetAPK = "GetAPK";
	public static final String SOAP_ACTION_GetAPK = "http://tempuri.org/GetAPK";

	
	public static final String SOFT_URL = "http://122.207.97.113/ems.apk";
	
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					"trueInfo.ems", 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verCode;
	}

	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					"trueInfo.ems", 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verName;

	}

	public static String getAppName(Context context) {
		String verName = context.getResources().getText(R.string.app_name)
				.toString();
		return verName;
	}
}
