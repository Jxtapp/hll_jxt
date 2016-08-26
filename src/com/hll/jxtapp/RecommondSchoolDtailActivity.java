package com.hll.jxtapp;

import com.hll.basic.ImageCallBack;
import com.hll.basic.NetworkDownImage;
import com.hll.entity.RecommendSchoolInfoO;
import com.hll.entity.SchoolDetailInfo;
import com.hll.util.JxtUtil;
import com.hll.util.NetworkInfoUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * the detail information of the driver school
 * @author liaoyun
 * 2016-7-8
 */
public class RecommondSchoolDtailActivity extends Activity implements OnClickListener{
	private TextView tel, qq, wechat, price, address, bugget, scInfo, title;
	private ImageView topPic, back;
	private RecommendSchoolInfoO fromItent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.recommond_driverschool_detail);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		
		Intent intent = getIntent();
		fromItent = (RecommendSchoolInfoO) intent.getSerializableExtra("info");     //获取 intent 传过来的信息
		
		title  = (TextView) findViewById(R.id.id_sec_title);                                 
		tel    = (TextView) findViewById(R.id.tel_num);
		qq     = (TextView) findViewById(R.id.qq_num);
		wechat = (TextView) findViewById(R.id.wechat_num);                         //微信
		price  = (TextView) findViewById(R.id.price_num);                          //价格
		address= (TextView) findViewById(R.id.address_detail);
		bugget = (TextView) findViewById(R.id.bugget);                             //优惠活动信息
		scInfo = (TextView) findViewById(R.id.school_detail);                      //驾校详情
		topPic = (ImageView) findViewById(R.id.top_pic);                           //顶部图片
		back   = (ImageView) findViewById(R.id.id_return);                         //返回按钮
		
		setTitle("");
		back.setOnClickListener(this);                                             //返回监听
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		showShoolDetail();
		super.onResume();
	}
	
	private void setTitle(String str){
		title.setText(str);
	}
	
	/**
	 * 展示驾校详情
	 */
	private void showShoolDetail(){
		final ShowDetailHandler handler = new ShowDetailHandler();
		new Thread(){
			public void run() {
				Looper.prepare();
				String url = NetworkInfoUtil.baseUtl + "/recommond/getSchoolInfo/" + fromItent.getPlaceId().trim() + ".action";
				SchoolDetailInfo sdi = JxtUtil.getObjectFromServer(SchoolDetailInfo.class, url);     //从服务器获取信息
				if(sdi != null){
					Message msg = new Message();
					msg.obj = sdi;
					handler.sendMessage(msg);
				}
				Looper.loop();
			};
		}.start();
		
	}
	
	/**
	 * 展示页面数据的 handler
	 */
	@SuppressLint("HandlerLeak")
	private class ShowDetailHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			SchoolDetailInfo sdi = (SchoolDetailInfo) msg.obj;
			sdi.nullToSpace();
			title.setText(sdi.getPlaceName());
			tel.setText(sdi.getTel());
			qq.setText(sdi.getQq());
			wechat.setText(sdi.getWechat());
			price.setText(sdi.getPrice() + " 元");
			address.setText(sdi.getPlaceAddress());
			bugget.setText("	" + sdi.getBugget());
			scInfo.setText("	" + sdi.getSchoolInfo());
			//加载图片
			if(sdi.getTopPIc() != null && !sdi.getTopPIc().equals("")){
				NetworkDownImage downImage = new NetworkDownImage(NetworkInfoUtil.picUtl+"/"+sdi.getTopPIc().trim());
				downImage.loadImage(new ImageCallBack() {
					@Override
					public void getDrawable(Drawable drawable) {
						if(drawable != null){
							topPic.setImageDrawable(drawable);
						}
					}
				});
			}
		}
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.id_return:                   //退出页面
			finish();
			break;

		default:
			break;
		}
	}
}
