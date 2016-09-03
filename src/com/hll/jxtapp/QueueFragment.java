package com.hll.jxtapp;
/**
 * 驾校通预约排队页面activity
 * @author heyi
 * 2016/6/1
 */

/**
 * app第二个页面，主要实现排队的功能
 * @author  heyi
 * 2016/5/27
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hll.adapter.SecPageAdapter;
import com.hll.basic.ImageCallBack;
import com.hll.basic.NetworkDownImage;
import com.hll.entity.CouchSelectBy;
import com.hll.entity.SecPageItemBean;
import com.hll.util.JxtUtil;
import com.hll.util.NetworkInfoUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

public class QueueFragment extends Fragment implements OnClickListener,OnItemClickListener,OnScrollListener{
	private LinearLayout orderLearn;
	private LinearLayout queueWait;
	private ListView listView;
	private List<SecPageItemBean> list;
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

	private int startIndex = 0,endIndex = 0,totalCount = 0;
	private double ontouchY1;
	private double ontouchY2;
	private int[] touchPosition = new int[2];
	
	private CouchSelectBy couchSelectBy=new CouchSelectBy();
	private List<SecPageItemBean>  secPageItemBeanList=new ArrayList<>();
    private Gson gson=new Gson();
    
    private LoadDataHandler loadDataHandler=new LoadDataHandler();
    
    private int isSpinnerClicked=0;
    private int firstVP=0;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.queue, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mainActivity = getActivity();
		orderLearn = (LinearLayout) mainActivity.findViewById(R.id.id_order_learn);
		queueWait = (LinearLayout) mainActivity.findViewById(R.id.id_queue_wait);
		listView = (ListView) mainActivity.findViewById(R.id.id_coach_self_choice);
		spinnerPlaceChoice = (Spinner) mainActivity.findViewById(R.id.id_sp_place_choice);
		spinnerTeachType = (Spinner) mainActivity.findViewById(R.id.id_sp_teach_type);
		spinnerNearPlace = (Spinner) mainActivity.findViewById(R.id.id_sp_near_place);
		secScrollView = (ScrollView) mainActivity.findViewById(R.id.id_queue_scrollview);
		//设置listView 的高度，（当 scrollList 与 listView 混用时，高度只会显示 一行）
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height=900;
		listView.setLayoutParams(params);
		//添加底部视图
		View footer =getLayoutInflater(savedInstanceState).inflate(R.layout.load_more,null);
		listView.addFooterView(footer);
		new QueueLoadDataThread().start();
		spinnerPlaceChoice.setOnItemSelectedListener(new SpinnerChangedListener());
		spinnerTeachType.setOnItemSelectedListener(new SpinnerChangedListener());
		spinnerNearPlace.setOnItemSelectedListener(new SpinnerChangedListener());
	}



	@Override
	public void onStart() {
		super.onStart();
		// 事件初始化
		initEvent();
		adapter=new SecPageAdapter(mainActivity, secPageItemBeanList);
		listView.setAdapter(adapter);
		if(firstVP != 0){
			listView.setSelection(firstVP);
			firstVP = 0;
		}
		
		// 自选教练
		choiceCochSelf();
	}

	// 初始化监听事件
		private void initEvent() {

			orderLearn.setOnClickListener(this);
			queueWait.setOnClickListener(this);
			listView.setOnScrollListener(this);
			listView.setOnTouchListener(new listViewOntouchListener());
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
		case R.id.id_order_learn:

			Intent intent1 = new Intent(mainActivity, OrderLearn.class);
			startActivity(intent1);
			break;
		case R.id.id_queue_wait:

			Intent intent2 = new Intent(mainActivity, QueueWait.class);
			startActivity(intent2);
			break;
		}
	}

	/**
	 * listView里的滚动方法
	 * 
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		startIndex=firstVisibleItem;
		//System.out.println("totalItemCount:"+totalItemCount);
		endIndex=firstVisibleItem+visibleItemCount-1;
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
	
	//listView和ScrollView之间的冲突问题
		private class listViewOntouchListener implements OnTouchListener{
			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				secScrollView.requestDisallowInterceptTouchEvent(true);
				listView.getLocationInWindow(touchPosition);
				switch (me.getAction()) {
				case MotionEvent.ACTION_DOWN:
					me.getX();
					ontouchY1 = me.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					me.getX();
					ontouchY2 = me.getY();
					if(ontouchY2 - ontouchY1 < 0){//scroll up
						Log.i("222", "up "+touchPosition[1]);
						//425dip
						if(touchPosition[1] > 600){
							secScrollView.requestDisallowInterceptTouchEvent(false);
						}else{
							secScrollView.requestDisallowInterceptTouchEvent(true);
						}
					}else{//scroll down
						if(startIndex==0){
							secScrollView.requestDisallowInterceptTouchEvent(false);
						}else{
							secScrollView.requestDisallowInterceptTouchEvent(true);
						}
					}
					break;
				default:
					break;
				}
				return false;
			}
		} 
	
		
	//从服务器加载数据
			private class QueueLoadDataThread extends Thread{
				public QueueLoadDataThread(){
					//改变查询的页数
				}
				@Override
				public void run() {
					//查询对象序列化
					String selectJson = gson.toJson(couchSelectBy);
					HttpURLConnection conn = JxtUtil.postHttpConn(NetworkInfoUtil.baseUtl+"/couchList/getCouchList.action",selectJson);
					try {
						InputStream is = conn.getInputStream();
						if(is!=null){
							String str = JxtUtil.streamToJsonString(is);
							List<SecPageItemBean> list = gson.fromJson(str, new TypeToken<List<SecPageItemBean>>(){}.getType());
							secPageItemBeanList.addAll(list);
							loadDataHandler.sendEmptyMessage(0);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			
			private class LoadDataHandler extends Handler{
				@Override
				public void handleMessage(Message msg) {
					adapter.notifyDataSetChanged();
					//加载图片
					try {
						if(endIndex==totalCount){
							loadListImg(startIndex,endIndex-1);
						}else{
							loadListImg(startIndex,endIndex);
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}	
			//加載圖片
			private void loadListImg(int start_index, int end_index) throws Exception{
				for(; start_index < end_index; start_index++){
					Log.i("indexdd",start_index+"  "+end_index+"  "+secPageItemBeanList.size());
					final String imgName = secPageItemBeanList.get(start_index).getCoachSelfImg();
					final ImageView imgView = (ImageView) listView.findViewWithTag(imgName);
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
			/**
			 * spinner监听，实现联级查询
			 */
			
			private class SpinnerChangedListener implements OnItemSelectedListener{
				@Override
				public void onItemSelected(AdapterView<?> arg0, View view, int arg2,
						long arg3) {
					isSpinnerClicked++;
					if(isSpinnerClicked < 4){
						return;
					}
					//setSelection(schoolSelect);
					//清空 listView,重新按条件加载数据
					secPageItemBeanList.clear();
					adapter.notifyDataSetChanged();
					setSelection(couchSelectBy);
					new QueueLoadDataThread().start();
				}
				@Override
				public void onNothingSelected(AdapterView<?> view) {
				}
			}
			/*
			 * 
			 * 获取spinner改变查询条件
			 */
			public void setSelection(CouchSelectBy csb) {
				String placeChoice = spinnerPlaceChoice.getSelectedItem().toString();
				String teachType = spinnerTeachType.getSelectedItem().toString();
				String nearPlace = spinnerNearPlace.getSelectedItem().toString();
				csb.setTranAreaSp(placeChoice);
				csb.setTranDistance(nearPlace);
				csb.setCarType(teachType);
				
			}
			/**
			 * 加载更多listview数据
			 */
@Override
public void onScrollStateChanged(AbsListView view, int scrollState) {
	
	//adapter.getCount();
	if(endIndex==adapter.getCount()&&scrollState==OnScrollListener.SCROLL_STATE_IDLE){
		secPageItemBeanList=adapter.getList();
		new QueueLoadDataThread().start();
	}
	if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
		try {
			if(endIndex==totalCount){
				loadListImg(startIndex,endIndex-1);
			}else{
				loadListImg(startIndex,endIndex);
			}
		} catch (Exception e) {
			Log.i("indexdd",e.getMessage());
		}
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

}


