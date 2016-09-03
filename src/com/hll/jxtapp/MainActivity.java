package com.hll.jxtapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.hll.basic.SystemBarTintManager;
import com.hll.common.SocketService;
import com.hll.entity.UserO;
import com.hll.jxtapp.R;
import com.hll.util.JxtUtil;
import com.hll.util.MyApplication;
import com.hll.util.NetworkInfoUtil;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments;

	private LinearLayout mTabRcommend;
	private LinearLayout mTabQueue;
	private LinearLayout mTabSimulate;
	private LinearLayout tabUserInfo;
	private LinearLayout tabStudentInfo;

	private TextView mTvRecommend;
	private TextView mTvQueue;
	private TextView mTvSimulate;
	private TextView mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);;
		setContentView(R.layout.activity_main);

		initView();
		initEvent();

		setSelect(1);
		
		tryLogin();                  //尝试自动登陆
	}
	
	/**
	 * 改变顶部背景
	 * @author heyi
	 */
	@TargetApi(19)   
    private void setTranslucentStatus(boolean on) {  
        Window win = getWindow();  
        WindowManager.LayoutParams winParams = win.getAttributes();  
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;  
        if (on) {  
            winParams.flags |= bits;  
        } else {  
            winParams.flags &= ~bits;  
        }  
        win.setAttributes(winParams);  
    }

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Intent intent = new Intent(this, SocketService.class);
		stopService(intent);
	}
	
	private void initEvent() {
		mTabRcommend.setOnClickListener(this);
		mTabQueue.setOnClickListener(this);
		mTabSimulate.setOnClickListener(this);
		tabUserInfo.setOnClickListener(this);
		tabStudentInfo.setOnClickListener(this);
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		mTabRcommend = (LinearLayout) findViewById(R.id.id_tab_recommend);
		mTabQueue = (LinearLayout) findViewById(R.id.id_tab_queue);
		mTabSimulate = (LinearLayout) findViewById(R.id.id_tab_simulate);
		tabUserInfo = (LinearLayout) findViewById(R.id.id_userInfo);
		tabStudentInfo = (LinearLayout) findViewById(R.id.id_studentInfo);
		

		mTvRecommend = (TextView) findViewById(R.id.id_tv_recommend);
		mTvQueue = (TextView) findViewById(R.id.id_tv_queue);
		mTvSimulate = (TextView) findViewById(R.id.id_tv_simulate);
		mTitle = (TextView) findViewById(R.id.id_title);

		mFragments = new ArrayList<Fragment>();
		Fragment mTab01 = new RecommendFragment();
		Fragment mTab02 = new QueueFragment();
		Fragment mTab03 = new SimulateFragment();
		mFragments.add(mTab01);
		mFragments.add(mTab02);
		mFragments.add(mTab03);

		/**
		 * 改变顶部背景
		 * @author heyi
		 */
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  
            setTranslucentStatus(true);  
            SystemBarTintManager tintManager = new SystemBarTintManager(this);  
            tintManager.setStatusBarTintEnabled(true);  
            tintManager.setStatusBarTintResource(R.drawable.top_blc);//通知栏所需颜色
        } 
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mFragments.get(arg0);
			}
		};
		mViewPager.setAdapter(mAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				int currentItem = mViewPager.getCurrentItem();
				String title;
				resetTextView();
				switch (currentItem) {
				case 0:
					mTvRecommend.setTextColor(Color.parseColor(getString(R.string.color_green)));
					title = mTvRecommend.getText().toString();
					mTitle.setText(title);
					break;
				case 1:
					mTvQueue.setTextColor(Color.parseColor(getString(R.string.color_green)));
					title = mTvQueue.getText().toString();
					mTitle.setText(title);
					break;
				case 2:
					mTvSimulate.setTextColor(Color.parseColor(getString(R.string.color_green)));
					title = mTvSimulate.getText().toString();
					mTitle.setText(title);
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	protected void resetTextView() {
		mTvRecommend.setTextColor(Color.parseColor(getString(R.string.color_black)));
		mTvQueue.setTextColor(Color.parseColor(getString(R.string.color_black)));
		mTvSimulate.setTextColor(Color.parseColor(getString(R.string.color_black)));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_tab_recommend:
			setSelect(0);
			break;
		case R.id.id_tab_queue:
			setSelect(1);
			break;
		case R.id.id_tab_simulate:
			setSelect(2);
			break;
		case R.id.id_userInfo:
			Intent intent1 = new Intent(this, LeftMenuView.class);
			startActivity(intent1);
			break;
		case R.id.id_studentInfo:
			Intent intent2 = new Intent(this, StudentInfoActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}

	private void setSelect(int i) {
		mViewPager.setCurrentItem(i);
	}

	
	/**
	 * 尝试自动登陆
	 */
	private void tryLogin(){
		UserO user = JxtUtil.getLastUserInfo();
		if(user != null){
			final String accountStr = user.getAccount();
			final String passwordStr = user.getPassword();
			if(accountStr==null||passwordStr==null){
				return;
			}
			final String url = NetworkInfoUtil.baseUtl+"/user/login/"+accountStr+"/"+passwordStr+"/email.action";
			new Thread(){
				public void run() {
					Looper.prepare();
					Map<String,String> map = JxtUtil.getMapFromServer(url);
					if(map != null){
						if(map.get("loginType") != null){
							//JxtUtil.toastCenter(MyApplication.getContext(), "您已经登陆了", Toast.LENGTH_SHORT);
							return;
						}
						UserO user = new UserO();
						user.setType(Integer.valueOf(map.get("type")));
						user.setName(map.get("name"));
						user.setNickName(map.get("nickName"));
						user.setAccount(accountStr);
						user.setPassword(passwordStr);
						user.setEmail(map.get("email"));
						user.setTel(map.get("tel"));
						user.setLastLoadTime(map.get("lastLoadTime"));
						user.setLastLoadIp(map.get("lastLoadIp"));
						user.setLastLoadPort(map.get("lastLoadPort"));
						JxtUtil.saveLastUserInfo(user);                                        //保存用户信息
						NetworkInfoUtil.accountId = map.get("account"); 
						Log.e("integer",map.get("sessionKey"));//用户的account
						NetworkInfoUtil.socketId = Integer.valueOf(map.get("sessionKey"));     //保存websocket验证的key值
						
						NetworkInfoUtil.name = map.get("name");                                //保存用户名字
						NetworkInfoUtil.nickName = map.get("nickName");                        //保存用户昵称
					}else{
						JxtUtil.toastCenter(MyApplication.getContext(), "登陆失败", Toast.LENGTH_SHORT);
					}
					Looper.loop();
				};
			}.start();
		}
	}
}
