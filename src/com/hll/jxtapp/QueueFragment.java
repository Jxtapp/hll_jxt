package com.hll.jxtapp;

/**
 * 驾校通预约排队页面activity
 * @author heyi
 * 2016/6/1
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * app第二个页面，主要实现排队的功能
 * @author  heyi
 * 2016/5/27
 */
import com.hll.adapter.SecPageAdapter;
import com.hll.entity.SecPageItemBean;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class QueueFragment extends Fragment implements OnClickListener,OnItemClickListener,OnScrollListener{
	private ImageView dropDownLocation;
	private LinearLayout orderLearn;
	private LinearLayout queueWait;
	private LinearLayout appItroduce;
	private LinearLayout functionInfo;
	private LinearLayout guaranteeService;
	private LinearLayout feedbackPass;
	private LinearLayout aboutUsCall;
	private ListView listView;
	private Vector<SecPageItemBean> list;
	private SecPageAdapter adapter;
	private Activity mainActivity;
	private Spinner spinnerPlaceChoice;
	private Spinner spinnerTeachType;
	private Spinner spinnerNearPlace;
	private List<String> listPlaceChoice;
	private List<String> listTeachType;
	private List<String> listNearPlace;
	private ArrayAdapter<String> adapterPlaceChoice;
	private ArrayAdapter<String> adapterTeachType;
	private ArrayAdapter<String> adapterNearPlace;
	private ScrollView secScrollView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.queue, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mainActivity = getActivity();
		dropDownLocation = (ImageView) mainActivity.findViewById(R.id.id_down_queue);
		orderLearn = (LinearLayout) mainActivity.findViewById(R.id.id_order_learn);
		queueWait = (LinearLayout) mainActivity.findViewById(R.id.id_queue_wait);
		appItroduce = (LinearLayout) mainActivity.findViewById(R.id.id_app_indroduce);
		functionInfo = (LinearLayout) mainActivity.findViewById(R.id.id_function_info);
		guaranteeService = (LinearLayout) mainActivity.findViewById(R.id.id_guarantee_service);
		feedbackPass = (LinearLayout) mainActivity.findViewById(R.id.id_feedback_pass);
		aboutUsCall = (LinearLayout) mainActivity.findViewById(R.id.id_about_us_call);
		listView = (ListView) mainActivity.findViewById(R.id.id_coach_self_choice);
		spinnerPlaceChoice = (Spinner) mainActivity.findViewById(R.id.id_sp_place_choice);
		spinnerTeachType = (Spinner) mainActivity.findViewById(R.id.id_sp_teach_type);
		spinnerNearPlace = (Spinner) mainActivity.findViewById(R.id.id_sp_near_place);
		secScrollView = (ScrollView) mainActivity.findViewById(R.id.id_queue_scrollview);
		//添加底部视图
		View footer =getLayoutInflater(savedInstanceState).inflate(R.layout.load_more,null);
		listView.addFooterView(footer);
		
	}



	@Override
	public void onStart() {
		super.onStart();
		// 事件初始化
		initEvent();
	
		//vector是线程安全的
		list = new Vector<SecPageItemBean>();
		// 教练列表
		coachList();
		
		// 自选教练
		choiceCochSelf();
	}

	// 初始化监听事件
		private void initEvent() {

			dropDownLocation.setOnClickListener(this);
			orderLearn.setOnClickListener(this);
			queueWait.setOnClickListener(this);
			appItroduce.setOnClickListener(this);
			functionInfo.setOnClickListener(this);
			guaranteeService.setOnClickListener(this);
			feedbackPass.setOnClickListener(this);
			aboutUsCall.setOnClickListener(this);
			listView.setOnScrollListener(this);
		}

	// 教练列表
	private void coachList() {

		secScrollView.smoothScrollBy(0, 0);
		initData();
		//new LoadDataThread().start();//加载数据的工作线程
		adapter=new SecPageAdapter(mainActivity, list);
		listView.setAdapter(adapter);
		// return view;
		setListViewHeightBasedOnChildren(listView);
	}

	/**
	 * 为listView添加数据
	 */
	int index=1; //数据的计数器(索引)
	//初始化数据
	 void initData() {
		 
		for (int i = 0; i < 10; i++) {
//			SecPageItemBean secItemBean = new SecPageItemBean();
//			secItemBean.coachPrice=3000 + (index) * 10;
//			secItemBean.coachSelfImg=R.drawable.coach;
//			secItemBean.teachType="小车";
//			secItemBean.orderTimes=index;
//			secItemBean.moreInfo="查看更多";
			list.add(new SecPageItemBean(R.drawable.coach, 3000 + (index) * 10, "小车", index, "查看更多"));
			//list.add(secItemBean);
			index++;
		}

	}

	// 获取并设置ListView高度的方法
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		listView.setLayoutParams(params);
	}

	// 根据条件自选教练选项
	private void choiceCochSelf() {

		listPlaceChoice = new ArrayList<>();
		listTeachType = new ArrayList<>();
		listNearPlace = new ArrayList<>();

		listPlaceChoice.add("武汉光谷广场");
		listPlaceChoice.add("洪山森林公园");
		listPlaceChoice.add("汉口楚河汉街");
		listPlaceChoice.add("武昌街道口");
		listPlaceChoice.add("武汉黄鹤楼");

		listTeachType.add("小车C1");
		listTeachType.add("小车C2");
		listTeachType.add("中车B1");
		listTeachType.add("中车B2");
		listTeachType.add("大车A1");
		listTeachType.add("大车A2");

		listNearPlace.add("1-2km");
		listNearPlace.add("2-4km");
		listNearPlace.add("4-6km");
		listNearPlace.add("6-8km");
		listNearPlace.add("8-10km");

		adapterPlaceChoice = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item,
				listPlaceChoice);
		adapterTeachType = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, listTeachType);
		adapterNearPlace = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, listNearPlace);

		adapterPlaceChoice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterTeachType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterNearPlace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerPlaceChoice.setAdapter(adapterPlaceChoice);
		spinnerTeachType.setAdapter(adapterTeachType);
		spinnerNearPlace.setAdapter(adapterNearPlace);

	}

	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.id_down_queue:

			Toast.makeText(mainActivity, "排队", Toast.LENGTH_SHORT).show();
			break;
		
		case R.id.id_order_learn:

			Intent intent1 = new Intent(mainActivity, OrderLearn.class);
			startActivity(intent1);
			// Toast.makeText(mainActivity, "学习次数", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_queue_wait:

			Intent intent2 = new Intent(mainActivity, QueueWait.class);
			startActivity(intent2);
			//Toast.makeText(mainActivity, "排队等待", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_app_indroduce:

			Toast.makeText(mainActivity, "软件介绍", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_function_info:

			Toast.makeText(mainActivity, "功能介绍", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_guarantee_service:

			Toast.makeText(mainActivity, "保障服务", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_feedback_pass:

			Toast.makeText(mainActivity, "反馈通道", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_about_us_call:

			Toast.makeText(mainActivity, "联系我们", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	/**
	 * listView内容的点击事件
	 * 
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * listView里的滚动方法
	 * 
	 */
	int visibleLastIndex=0;
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		
		//System.out.println("totalItemCount:"+totalItemCount);
		visibleLastIndex=firstVisibleItem+visibleItemCount-1;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//adapter.getCount();
		if(visibleLastIndex==adapter.getCount()&&scrollState==OnScrollListener.SCROLL_STATE_IDLE){
			new LoadDataThread().start();
		}
	}
	
	//线程之间通信的桥梁handler
	private Handler handler =new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		};
	};
	

	//模拟加载数据
	class LoadDataThread extends Thread{
		@Override
		public void run() {
			initData();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//通过handler处理器去通知主线程，说数据已加载完毕
			handler.sendEmptyMessage(1);
		}
	}

}
