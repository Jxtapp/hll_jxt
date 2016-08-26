package com.hll.jxtapp;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.hll.entity.RecommendSchoolInfoO;
import com.hll.entity.SchoolSelectBy;
import com.hll.adapter.RecommondSchoolListAdapter;
import com.hll.basic.ImageCallBack;
import com.hll.basic.NetworkDownImage;
import com.hll.common.MyLocationInfo;
import com.hll.util.JxtUtil;
import com.hll.util.MyApplication;
import com.hll.util.NetworkInfoUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RecommendFragment extends Fragment implements OnClickListener{
	
	private Activity mainActivity;
	private List<RecommendSchoolInfoO> driverSchoolInfoList = new ArrayList<RecommendSchoolInfoO>();
	private View footer;                                                 //listView 上拉加载条
	private ListView lview;                                              //驾校详细信息栏
	private TextView myaddress;
	private RecommondSchoolListAdapter recommondSchoolListAdapter;
	private LoadDataHandler loadDataHandler = new LoadDataHandler();
	private LinearLayout recomdendAd;                                   //特别推荐栏
	private LoadAdHandler loadAdHandler = new LoadAdHandler();          //加载特别推荐的图片
	private ImageView recommendAdImg;                                   //特别推荐栏图片
	private TextView recommendAdPrice;                                  //特别推荐栏价格
	private Gson gson = new Gson();
	private int firstVP = 0;                                            //list view first visible postion
	private int start_index = 0,end_index = 0,totalCount = 0;
	private ScrollView scrollView;
	private ImageView nextLeft,nextRight;
	private int currentAd = 0;                                         //current advertisement nunber
	private ImageView baiduMap;                                        //点击进入百度地图
	private float ontouchY1 = 0,ontouchY2 = 0;
	private int[] touchPosition = new int[2];  
	private Spinner areaSpinner,kindSpinner;                           //条件选择框
	MyAddressHandler myAddressHandler = new MyAddressHandler();
	private SchoolSelectBy schoolSelect = new SchoolSelectBy();        //当前查询条件
	private int isSpinnerClicked = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.recommend, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mainActivity = getActivity();
		nextLeft = (ImageView) mainActivity.findViewById(R.id.next_left);
		nextRight = (ImageView) mainActivity.findViewById(R.id.next_right);
		myaddress = (TextView) mainActivity.findViewById(R.id.my_address);
		baiduMap = (ImageView) mainActivity.findViewById(R.id.to_map);
		scrollView = (ScrollView) mainActivity.findViewById(R.id.id_first_page_scrollview);
		lview = (ListView) mainActivity.findViewById(R.id.driverschoollist);       
		recomdendAd = (LinearLayout) mainActivity.findViewById(R.id.recommend_ad); 
		areaSpinner = (Spinner) mainActivity.findViewById(R.id.area_spinner);
		kindSpinner = (Spinner) mainActivity.findViewById(R.id.kind_spinner);
		
		lview.setOnTouchListener(new ListViewOntouchListener());
		nextLeft.setOnClickListener(this);
		nextRight.setOnClickListener(this);
		baiduMap.setOnClickListener(this);                            //添加 监听器，进入地图
		lview.setOnScrollListener(new driverScollScrollListener());   //如果滑动到最下方，加载更多数据 监听
		lview.setOnItemClickListener(new driverListClickListener());  //listVidw 元素 的 单击事件监听器
		areaSpinner.setOnItemSelectedListener(new SpinnerChangedListener());
		kindSpinner.setOnItemSelectedListener(new SpinnerChangedListener());
		
		myaddress();                                                   //定位
		setListViewHeight();                                           //设置 listView 的高度
		LayoutInflater inflater = LayoutInflater.from(mainActivity);   //add footer
		footer = inflater.inflate(R.layout.load_more, null);
		lview.addFooterView(footer);
		footer.setVisibility(View.GONE);
		recomdendAdLoadData(recomdendAd);                             //加载 特别推荐栏的数据
		new LoadDataThread().start();                                 //加载初始数据
		//绑定数据源 和 listView
		recommondSchoolListAdapter = new RecommondSchoolListAdapter(mainActivity, driverSchoolInfoList);
		lview.setAdapter(recommondSchoolListAdapter);
	}
	
	//设置 listView 的高度
	private void setListViewHeight(){
		ViewGroup.LayoutParams params = lview.getLayoutParams();
		int[] wdSize = JxtUtil.getScreenSize();
		params.height = wdSize[0];
		lview.setLayoutParams(params);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if(firstVP != 0){
			lview.setSelection(firstVP);
			firstVP = 0;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	

	private void loadListImg(int start_index, int end_index) throws Exception{
		for(; start_index < end_index; start_index++){
			Log.i("indexdd",start_index+"  "+end_index+"  "+driverSchoolInfoList.size());
			final String imgName = driverSchoolInfoList.get(start_index).getItemImg();
			final ImageView imgView = (ImageView) lview.findViewWithTag(imgName);
			//从网上下载图片
			if(imgName!=null){
				NetworkDownImage downImage = new NetworkDownImage(NetworkInfoUtil.picUtl+"/"+imgName);
				//接口回调，加载图片
				downImage.loadImage(new ImageCallBack(){
					@Override
					public void getDrawable(Drawable drawable) {
						String imgTag = (String) imgView.getTag();
						if(imgTag != null && imgTag.equals(imgName)){
							imgView.setImageDrawable(drawable);
						}
					}
				});
			}
		}
	}
	
	//从服务器加载数据
	private class LoadDataThread extends Thread{
		public LoadDataThread(){
			setSelection(schoolSelect);
		}
		@Override
		public void run() {
			Looper.prepare();
			//查询对象序列化
			String selectJson = gson.toJson(schoolSelect);
			String url = NetworkInfoUtil.baseUtl+"/recommond/getSchoolList.action";
			List<RecommendSchoolInfoO> list =  JxtUtil.postListObjectFromServer(RecommendSchoolInfoO.class,url, selectJson);
			if(list!=null && list.size()>0){
				driverSchoolInfoList.addAll(list);
				loadDataHandler.sendEmptyMessage(0);
			}else{
				JxtUtil.toastCenter(MyApplication.getContext(), "没有更多数据了", Toast.LENGTH_SHORT);
				loadDataHandler.sendEmptyMessage(0);
			}
			Looper.loop();
		}
	}
	
	//加载 特别推荐栏的数据
	private void recomdendAdLoadData(LinearLayout linearLayout) {
		if(recommendAdImg==null){
			recommendAdImg = (ImageView) linearLayout.findViewById(R.id.recommend_ad_img);
		}
		if(recommendAdPrice==null){
			recommendAdPrice = (TextView) linearLayout.findViewById(R.id.recommend_ad_price);
		}
		new LoadAdThead().start();
	}
	
	private class LoadAdThead extends Thread{
		@Override
		public void run() {
			super.run();
			String url = NetworkInfoUtil.baseUtl+"/recommond/getRecommondAdInfo/"+ currentAd +".action";
			List<RecommendSchoolInfoO> list = JxtUtil.getListObjectFromServer(RecommendSchoolInfoO.class,url);
			if(list!=null && list.size()>0){
				Message msg = new Message();
				msg.obj=list.get(0);
				loadAdHandler.sendMessage(msg);
			}
		}
	}

	/**
	 * 设置选择条件
	 * liaoyun 2016-8-8
	 * @param ssb
	 */
	private void setSelection(SchoolSelectBy ssb){
		String areaSelect = areaSpinner.getSelectedItem().toString();
		String orderTypeSelect = kindSpinner.getSelectedItem().toString();
		ssb.setTranAreaSp(areaSelect);
		ssb.setOrderType(orderTypeSelect);
		ssb.setStartIndex(driverSchoolInfoList.size());
	}
	
	//----------------get address --------------------------
	private void myaddress(){
		final MyLocationInfo myLocationInfo = new MyLocationInfo();
		new Thread(){
			@SuppressWarnings("static-access")
			public void run() {
				Looper.prepare();
				myLocationInfo.getMyaddress(mainActivity);
				if(MyLocationInfo.nowLat != 0.0 && MyLocationInfo.nowLng != 0.0){
					myLocationInfo.reverseAddressResolution(MyLocationInfo.nowLat, MyLocationInfo.nowLng);
				}
				if(MyLocationInfo.MY_ADDRESS != null){
					Message msg = new Message();
					msg.obj = myLocationInfo.MY_ADDRESS;
					myAddressHandler.sendMessage(msg);
				}
				Looper.loop();
			};
		}.start();
	}
	
/*****************更新ui的 handler********************************************/
	@SuppressLint("HandlerLeak")
	private class MyAddressHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			myaddress.setText(msg.obj.toString());
		}
	}
	@SuppressLint("HandlerLeak")
	private class LoadDataHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			recommondSchoolListAdapter.notifyDataSetChanged();
			showPic();
			footer.setVisibility(View.GONE);
		}
	}
	/**
	 * 显示 listView 中的图片
	 */
	private void showPic(){
		try {//加载图片
			if(end_index==totalCount){
				loadListImg(start_index,end_index-1);
			}else{
				loadListImg(start_index,end_index);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressLint("HandlerLeak")
	private class LoadAdHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			RecommendSchoolInfoO vo = (RecommendSchoolInfoO) msg.obj;
			recommendAdPrice.setText(String.valueOf(vo.getItemPrice()));
			String pic = vo.getItemImg();
			if(pic!=null){
				String url=NetworkInfoUtil.picUtl+pic;
				NetworkDownImage downImage = new NetworkDownImage(url);
				downImage.loadImage(new ImageCallBack() {
					@Override
					public void getDrawable(Drawable drawable) {
						recommendAdImg.setImageDrawable(drawable);
					}
				});
			}
		}
	}
	
/********* listener *************************************************************/
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.next_right:                 //下一个广告
				currentAd++;
				new LoadAdThead().start();
				break;
			case R.id.next_left:                 //上一个广告
				currentAd--;
				new LoadAdThead().start();
				break;
			case R.id.to_map:                    //进入百度地图
				Intent intent = new Intent(mainActivity,RecommendMapActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}
	//------ driver list item on click event, show the detail information of the driver school------
	private class driverListClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			firstVP=lview.getFirstVisiblePosition();
			RecommendSchoolInfoO info = driverSchoolInfoList.get(arg2);
			Intent intent = new Intent(mainActivity,RecommondSchoolDtailActivity.class);
			Bundle bd = new Bundle();
			bd.putSerializable("info", info);
			intent.putExtras(bd);
			startActivity(intent);
		}
	}
	
	private class driverScollScrollListener implements OnScrollListener{
		@Override
		public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			end_index = firstVisibleItem + visibleItemCount;
			start_index = firstVisibleItem;
			totalCount = totalItemCount;
		}

		@Override
		public void onScrollStateChanged(AbsListView listView, int scrollState) {
			//如果滑动到最下方，加载更多数据
			if(end_index==totalCount && scrollState==OnScrollListener.SCROLL_STATE_IDLE){
				driverSchoolInfoList = recommondSchoolListAdapter.getDriverSchoolInfoList();
				footer.setVisibility(View.VISIBLE);
				new LoadDataThread().start();
			}
			// when scroll event stoped,start to onload bitmap resource
			if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
				//reminded: at the bottom of the listView,the last item is footer 
				showPic();
			}
		}
	}

	private class ListViewOntouchListener implements OnTouchListener{
		@Override
		public boolean onTouch(View arg0, MotionEvent me) {
			scrollView.requestDisallowInterceptTouchEvent(true);
			lview.getLocationInWindow(touchPosition);
			switch (me.getAction()) {
			case MotionEvent.ACTION_DOWN:
				me.getX();
				ontouchY1 = me.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				me.getX();
				ontouchY2 = me.getY();
				if(ontouchY2 - ontouchY1 < 0){                                //scroll up
					if(touchPosition[0] > 100){
						scrollView.requestDisallowInterceptTouchEvent(false);
					}else{
						scrollView.requestDisallowInterceptTouchEvent(true);
					}
				}else{                                                         //scroll down
					if(start_index==0){
						scrollView.requestDisallowInterceptTouchEvent(false);
					}else{
						scrollView.requestDisallowInterceptTouchEvent(true);
					}
				}
				break;
			default:
				break;
			}
			return false;
		}
	} 
	
	private class SpinnerChangedListener implements OnItemSelectedListener{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View view, int arg2,
				long arg3) {
			isSpinnerClicked++;
			if(isSpinnerClicked < 3){
				return;
			}
			//清空 listView,重新按条件加载数据
			driverSchoolInfoList.clear();
			setSelection(schoolSelect);
			recommondSchoolListAdapter.notifyDataSetChanged();
			new LoadDataThread().start();
		}
		@Override
		public void onNothingSelected(AdapterView<?> view) {
		}
	}
}
