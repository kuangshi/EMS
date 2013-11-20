package trueInfo.ems;

import static trueInfo.util.PublicClass.NAMESPACE;
import static trueInfo.util.PublicClass.URL;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import trueInfo.services.LoginServices;
import trueInfo.util.MD5;
import trueInfo.util.PublicClass;
import trueInfo.util.SharedPrefsUtil;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePWDActivity extends Activity {

EditText	login_user_edit;
EditText	login_passwd_edit;
EditText	login_passwd_new;
Button login_login_btn;
Button login_login_btnsms;
EditText login_smsinfo;
String password;
String passwordNew;
String passwordNew1;
String uno;
String pwd;
String randomNum;
String OPID;
TextView title_name;
ImageView imgRefresh;
ImageView imgBack;
ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		Intent intent = getIntent();
		uno=SharedPrefsUtil.getValue(this, "CommunityID", "");
		OPID = SharedPrefsUtil.getValue(this, "OPID", "");
		title_name=(TextView)findViewById(R.id.dispTitle);
		title_name.setText("修改密码");
		imgRefresh=(ImageView)findViewById(R.id.imgRefresh);
		imgRefresh.setVisibility(View.GONE);
		imgBack=(ImageView)findViewById(R.id.imgBack);
		imgBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		login_user_edit=(EditText)findViewById(R.id.login_user_edit);
		login_passwd_edit=(EditText)findViewById(R.id.login_passwd_edit);
		login_passwd_new=(EditText)findViewById(R.id.login_passwd_new);
		login_login_btn=(Button)findViewById(R.id.login_login_btn);
		
		login_login_btn.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				password = login_user_edit.getEditableText().toString().trim();
				passwordNew = login_passwd_edit.getEditableText().toString().trim();
				passwordNew1 = login_passwd_new.getEditableText().toString().trim();
				if(password.equals(""))
				{
					Toast.makeText(ChangePWDActivity.this, "原密码不能为空",
							Toast.LENGTH_SHORT).show();// 输出提示消息	
				}
				if(passwordNew.equals(passwordNew1))
				{
					// 启动进度条
					pd = ProgressDialog.show(ChangePWDActivity.this, null,
							"数据加载中", true, true);
					getDiaryList_changepwd();
				}else
				{
					Toast.makeText(ChangePWDActivity.this, "新密码不一致，请重新填写",
							Toast.LENGTH_SHORT).show();// 输出提示消息
				}
			}
		});
		
		
		
	}
	public void btn_back(View v) {     //标题栏 返回按钮
		
		finish();
      } 
	
	 Handler myHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 0:
				
					pd.dismiss();
					Toast.makeText(ChangePWDActivity.this, "恭喜，密码修改成功！",
							Toast.LENGTH_SHORT).show();// 输出提示消息
					Intent intent=new Intent(ChangePWDActivity.this,LoginActivity.class);
					startActivity(intent);
					finish();
					break;
				case 1:
					
					pd.dismiss();
					Toast.makeText(ChangePWDActivity.this, "失败，密码修改失败！",
							Toast.LENGTH_SHORT).show();// 输出提示消息
					break;
				
				}
				super.handleMessage(msg);
			}
				
				};
	
	
	/**
	 * 获取详情信息
	 */
	private void getDiaryList_changepwd() {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				try {
					 
					SoapObject soapObject = new SoapObject(NAMESPACE,
							PublicClass.METHOD_NAME_UPDATE);

					soapObject.addProperty("Code", "0001");
					soapObject.addProperty("Info", "{\"root\":[{\"oldpwd\":\"" + MD5.md5(password)
							+ "\",\"newpwd\":\"" + MD5.md5(passwordNew1)
							+ "\",\"id\":\"" + OPID
							+ "\"}]}");
					soapObject.addProperty("SNum",
							"0394cb6d-2575-4f73-92e9-6d402c39e20c");
					soapObject.addProperty("yhnm",uno);

					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
							SoapEnvelope.VER11);
					envelope.setOutputSoapObject(soapObject);
					envelope.bodyOut = soapObject;
					envelope.dotNet = true;
					envelope.encodingStyle = SoapEnvelope.ENC;

					@SuppressWarnings("deprecation")
					HttpTransportSE httpTranstation = new HttpTransportSE(URL);

					httpTranstation.call(PublicClass.SOAP_ACTION_UPDATE, envelope);
					Object result = envelope.getResponse();

					String retStr = String.valueOf(result);

					String str = "anyType{}";

					Log.i("moa", "moa:---------------" + retStr);
					
					if(retStr.equals("true"))
					{
					    myHandler.sendEmptyMessage(0);

					}else
					{
						myHandler.sendEmptyMessage(1);
					}

					
				

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	

	

}
