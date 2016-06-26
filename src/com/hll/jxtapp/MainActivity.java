package com.hll.jxtapp;

import java.util.ArrayList;
import java.util.List;

import com.hll.basic.SlideMenu;
import com.hll.basic.LeftMoveView;
import com.hll.basic.RightMoveView;
import com.hll.jxtapp.R;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
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
	private LinearLayout mTabFriend;
	private LinearLayout mTabMoreInfo;
	private LinearLayout tabUserInfo;
	private LinearLayout tabStudentInfo;

	// private ImageButton mImgRcommend;
	// private ImageButton mImgQueue;
	// private ImageButton mImgSimulate;
	// private ImageButton mImgFriend;
	// private ImageButton mImgMoreInfo;

	//private SlideMenu slideMenu;
	
	private TextView mTvRecommend;
	private TextView mTvQueue;
	private TextView mTvSimulate;
	private TextView mTvFriend;
	private TextView mTvMoreInfo;
	private TextView mTitle;
	private LeftMoveView leftMoveView;
	private RightMoveView rightMoveView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initView();
		initEvent();

		setSelect(1);
	}

	private void initEvent() {
		mTabRcommend.setOnClickListener(this);
		mTabQueue.setOnClickListener(this);
		mTabSimulate.setOnClickListener(this);
		mTabFriend.setOnClickListener(this);
		mTabMoreInfo.setOnClickListener(this);
		tabUserInfo.setOnClickListener(this);
		tabStudentInfo.setOnClickListener(this);
		//slideMenu.setOnClickListener(this);
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		mTabRcommend = (LinearLayout) findViewById(R.id.id_tab_recommend);
		mTabQueue = (LinearLayout) findViewById(R.id.id_tab_queue);
		mTabSimulate = (LinearLayout) findViewById(R.id.id_tab_simulate);
		mTabFriend = (LinearLayout) findViewById(R.id.id_tab_friend);
		mTabMoreInfo = (LinearLayout) findViewById(R.id.id_tab_moreinfo);
		tabUserInfo = (LinearLayout) findViewById(R.id.id_userInfo);
		tabStudentInfo = (LinearLayout) findViewById(R.id.id_studentInfo);
		
		//slideMenu = (SlideMenu) findViewById(R.id.slide_menu);

		// mImgRcommend = (ImageButton) findViewById(R.id.id_tab_recommend_img);
		// mImgQueue = (ImageButton) findViewById(R.id.id_tab_queue_img);
		// mImgSimulate = (ImageButton) findViewById(R.id.id_tab_simulate_img);
		// mImgFriend = (ImageButton) findViewById(R.id.id_tab_friend_img);
		// mImgMoreInfo = (ImageButton) findViewById(R.id.id_tab_moreinfo_img);

		mTvRecommend = (TextView) findViewById(R.id.id_tv_recommend);
		mTvQueue = (TextView) findViewById(R.id.id_tv_queue);
		mTvSimulate = (TextView) findViewById(R.id.id_tv_simulate);
		mTvFriend = (TextView) findViewById(R.id.id_tv_friend);
		mTvMoreInfo = (TextView) findViewById(R.id.id_tv_moreinfo);
		mTitle = (TextView) findViewById(R.id.id_title);

		mFragments = new ArrayList<Fragment>();
		Fragment mTab01 = new RecommendFragment();
		Fragment mTab02 = new QueueFragment();
		Fragment mTab03 = new SimulateFragment();
		Fragment mTab04 = new FriendFragment();
		Fragment mTab05 = new MoreInfoFragment();
		mFragments.add(mTab01);
		mFragments.add(mTab02);
		mFragments.add(mTab03);
		mFragments.add(mTab04);
		mFragments.add(mTab05);

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
				case 3:
					mTvFriend.setTextColor(Color.parseColor(getString(R.string.color_green)));
					title = mTvFriend.getText().toString();
					mTitle.setText(title);
					break;
				case 4:
					mTvMoreInfo.setTextColor(Color.parseColor(getString(R.string.color_green)));
					title = mTvMoreInfo.getText().toString();
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
		mTvFriend.setTextColor(Color.parseColor(getString(R.string.color_black)));
		mTvMoreInfo.setTextColor(Color.parseColor(getString(R.string.color_black)));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_tab_recommend:
//			Intent intent = new Intent(this, RecommendFragment.class);
//			startActivity(intent);
			setSelect(0);
			break;
		case R.id.id_tab_queue:
			setSelect(1);
			break;
		case R.id.id_tab_simulate:
			setSelect(2);
			break;
		case R.id.id_tab_friend:
			setSelect(3);
			break;
		case R.id.id_tab_moreinfo:
			setSelect(4);
			break;
		case R.id.id_userInfo:
			Intent intent1 = new Intent(this, UserInfoActivity.class);
			startActivity(intent1);
			break;
		case R.id.id_studentInfo:
			Intent intent2 = new Intent(this, StudentInfoActivity.class);
			startActivity(intent2);
//		case R.id.id_userInfo:
//			if (slideMenu.isMainScreenShowing()) {
//				slideMenu.openMenu();
//			} else {
//				slideMenu.closeMenu();
//			}
//			break;
		default:
			break;
		}
	}

	private void setSelect(int i) {
		mViewPager.setCurrentItem(i);
	}

	/**
	 * 返回
	 */
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			if (leftMoveView.getNowState() == LeftMoveView.MAIN) {
//				leftMoveView.moveToLeft(true);
//				return true;
//			} else {
//				finish();
//			}
//		}
//		return false;
//		// return super.onKeyDown(keyCode, event);
//	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.add(1, 100, 1, "菜单");
		//item.setTitle("�˵�");
		item.setIcon(R.drawable.more_info_bar);// api>=11 ����ʾͼ�� ��
		menu.add(1, 101, 1, "我的消息");
		menu.add(1, 102, 1, "我的收藏");
		menu.add(1, 103, 1, "评价");
		menu.add(1, 104, 1, "推送消息");
		menu.add(1, 105, 1, "关于软件");
		menu.add(1, 106, 1, "更多关于我们");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//case 100:
//			Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//			item.setIntent(intent);
		//	break;

		case 101:
			Toast.makeText(MainActivity.this, "我的消息", Toast.LENGTH_SHORT)
					.show();
			break;
		case 102:
			Toast.makeText(MainActivity.this, "我的收藏", Toast.LENGTH_SHORT)
					.show();
			break;
		case 103:
			Toast.makeText(MainActivity.this, "评价", Toast.LENGTH_SHORT)
					.show();
			break;
		case 104:
			Toast.makeText(MainActivity.this, "推送消息", Toast.LENGTH_SHORT)
					.show();
			break;
		case 105:
			Toast.makeText(MainActivity.this, "关于软件", Toast.LENGTH_SHORT)
					.show();
			break;
		case 106:
			Toast.makeText(MainActivity.this, "更多关于我们", Toast.LENGTH_SHORT)
					.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
