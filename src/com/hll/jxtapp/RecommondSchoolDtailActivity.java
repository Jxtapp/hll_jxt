package com.hll.jxtapp;

import com.hll.entity.RecommendSchoolInfoO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
/**
 * the detail information of the driver school
 * @author liaoyun
 * 2016-7-8
 */
public class RecommondSchoolDtailActivity extends Activity{
	private TextView account;  //school account
	private TextView name;     //school name
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommond_driverschool_detail);
		account = (TextView) findViewById(R.id.count);
		name = (TextView) findViewById(R.id.name);
		Intent it = getIntent();
		Bundle bd = it.getExtras();
		Message msa = new Message();
		msa.obj = bd.get("info");
		MyHandle ha = new MyHandle();
		ha.handleMessage(msa);
	}
	
	private class MyHandle extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			RecommendSchoolInfoO info = (RecommendSchoolInfoO) msg.obj;
			account.setText(info.getSchoolAccount());
			name.setText(info.getSchoolname());
		}
	}
}
