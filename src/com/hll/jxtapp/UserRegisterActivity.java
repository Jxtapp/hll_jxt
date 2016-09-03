package com.hll.jxtapp;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * 客户端注册事件
 * @author 何毅
 * 2016/8/27
 */
import com.hll.jxtapp.R.id;
import com.hll.util.JxtUtil;
import com.hll.util.NetworkInfoUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegisterActivity extends Activity{

	private String account;
	private String pswFir;
	private String pseSec;
	private EditText accountReg;
	private EditText pswFirReg;
	private EditText pswSecReg;
	private Button sureReg;
	Boolean isTheSameAccount=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register);
		accountReg=(EditText) findViewById(R.id.id_reg_account);
		pswFirReg=(EditText) findViewById(R.id.id_reg_psw_first);
		pswSecReg=(EditText) findViewById(R.id.id_reg_psw_second);
		sureReg=(Button) findViewById(id.id_reg_sure_btn);
		sureReg.setOnClickListener(registerAccount);
		account=accountReg.getText().toString();
		pswFir=pswFirReg.getText().toString();
		pseSec=pswSecReg.getText().toString();
		
	}
	private OnClickListener registerAccount=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(account==null || account.equals("")){
				Toast toast=Toast.makeText(UserRegisterActivity.this, "账号不能为空", Toast.LENGTH_LONG);
				toast.show();
			}
			else if(account!=null&&isTheSameAccount()){
				Toast toast =Toast.makeText(UserRegisterActivity.this, "账号已经被注册", Toast.LENGTH_LONG);
				toast.show();
			}
			else if(pswFir==null|| account.equals("")){
				Toast toast =Toast.makeText(UserRegisterActivity.this, "密码不能为空", Toast.LENGTH_LONG);
				toast.show();
			}
			else if(!pswSame()){
				Toast toast =Toast.makeText(UserRegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_LONG);
				toast.show();
			}
			else if(!isTheSameAccount()&&pswSame()) {
				
				Toast toast =Toast.makeText(UserRegisterActivity.this, "注册成功，马上登录吧", Toast.LENGTH_LONG);
				toast.show();
			}
		}

		
		
		private boolean isTheSameAccount() {
			new Thread(){
				public void run() {
					String url = NetworkInfoUtil.baseUtl+"/user/register/"+account+"/isTheSameAccount.action";
					
					Map<String,String> map = JxtUtil.getMapFromServer(url);
					
					HttpURLConnection conn = JxtUtil.postHttpConn(url, "");
					try {
						InputStream is =  conn.getInputStream();
						if(is != null){                                                         //登陆成功
							String s = JxtUtil.streamToJsonString(is);
							if(s=="true") isTheSameAccount= true;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				};
			}.start();
			return isTheSameAccount;
		}

	};
	private boolean pswSame() {
		if(pseSec==null|| account.equals(""))return false;
	
		else if(pseSec.length()!=pswFir.length()) return false;
		else{
			for(int i=0;i<pswFir.length();i++){
				if(pswFir.charAt(i)!=pseSec.charAt(i)) return false;
				else continue;
			}
		}
		return true;
	}
	
}
