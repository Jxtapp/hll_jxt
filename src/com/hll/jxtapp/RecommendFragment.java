package com.hll.jxtapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hll.entity.RecommendSchoolInfoO;
import com.hll.entity.SchoolSelectBy;
import com.hll.adapter.RecommondSchoolListAdapter;
import com.hll.basic.ImageCallBack;
import com.hll.basic.NetworkDownImage;
import com.hll.util.JxtUtil;
import com.hll.util.NetworkInfoUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RecommendFragment extends Fragment {
	
	private Activity mainActivity;
	private List<RecommendSchoolInfoO> driverSchoolInfoList;
	private View footer;   //listView 上拉加载条
	private ListView lview;
	private RecommondSchoolListAdapter recommondSchoolListAdapter;
	private LoadDataHandler loadDataHandler;
	private LinearLayout recomdendAd;   //特别推荐栏
	private LoadAdHandler loadAdHandler = new LoadAdHandler(); //加载特别推荐的图片
	private ImageView recommendAdImg;//特别推荐栏图片
	private TextView recommendAdPrice;//特别推荐栏价格
	private SchoolSelectBy schoolSelect = new SchoolSelectBy();
	private Gson gson = new Gson();
	private int firstVP = 0;  //list view first visible postion
	private int start_index = 0;
	private int end_index = 0;
	private int totalCount = 0;
	//点击进入百度地图
	private ImageView baiduMap;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.recommend, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mainActivity = getActivity();
		//特别推荐栏
		if(recomdendAd == null){
			recomdendAd = (LinearLayout) mainActivity.findViewById(R.id.recommend_ad);
		}
		//驾校详细信息栏
		lview = (ListView) mainActivity.findViewById(R.id.driverschoollist);
		//add footer
		LayoutInflater inflater = LayoutInflater.from(mainActivity);
		footer = inflater.inflate(R.layout.load_more, null);
		//footer.setVisibility(View.GONE);
		lview.addFooterView(footer);
		if(loadDataHandler==null){
			loadDataHandler = new LoadDataHandler();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if(baiduMap == null){
			baiduMap = (ImageView) mainActivity.findViewById(R.id.to_map);
		}
		//添加 监听器，进入地图
		baiduMap.setOnClickListener(new BaiduMapClickListener());
		//加载 特别推荐栏的数据
		recomdendAdLoadData(recomdendAd);
		if(driverSchoolInfoList==null){
			driverSchoolInfoList = new ArrayList<RecommendSchoolInfoO>();
		}
		for(int i=0; i<5; i++){
			driverSchoolInfoList.add(new RecommendSchoolInfoO(null,1000,"address"+i,"count"+i,"天天驾校"+i));
		}
		
		//lview = (ListView) mainActivity.findViewById(R.id.driverschoollist);
		//绑定数据源 和 listView
		if(recommondSchoolListAdapter==null){
			recommondSchoolListAdapter = new RecommondSchoolListAdapter(mainActivity, driverSchoolInfoList);
		}
		lview.setAdapter(recommondSchoolListAdapter);
		//如果滑动到最下方，加载更多数据 监听
		lview.setOnScrollListener(new driverScollScrollListener());
		//listVidw 元素 的 单击事件监听器
		//lview.setOnItemClickListener(new driverListClickListener());
	}

	@Override
	public void onResume() {
		super.onResume();
		if(firstVP != 0){
			lview.setSelection(firstVP);
			firstVP = 0;
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
			Log.i("key",end_index+"   "+recommondSchoolListAdapter.getCount());
			if(end_index==totalCount && scrollState==OnScrollListener.SCROLL_STATE_IDLE){
				driverSchoolInfoList = recommondSchoolListAdapter.getDriverSchoolInfoList();
				//footer.setVisibility(View.VISIBLE);
				new loadDataThread().start();
			}
			// when scroll event stoped,start to onload bitmap resource
			if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
				//reminded: at the bottom of the listView,the last item is footer 
				try {
					if(end_index==totalCount){
						loadListImg(start_index,end_index-1);
					}else{
						loadListImg(start_index,end_index);
					}
				} catch (Exception e) {
					Log.i("indexdd",e.getMessage());
				}
			}
		}
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
	// driver list item on click event, show the detail information of the driver school
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
	//从服务器加载数据
	private class loadDataThread extends Thread{
		public loadDataThread(){
			//改变查询的页数
		}
		@Override
		public void run() {
			//查询对象序列化
			String selectJson = gson.toJson(schoolSelect);
			HttpURLConnection conn = JxtUtil.postHttpConn(NetworkInfoUtil.baseUtl+"/recommond/getSchoolList.action",selectJson);
			try {
				InputStream is = conn.getInputStream();
				if(is!=null){
					String str = JxtUtil.streamToJsonString(is);
					List<RecommendSchoolInfoO> list = gson.fromJson(str, new TypeToken<List<RecommendSchoolInfoO>>(){}.getType());
					driverSchoolInfoList.addAll(list);
					loadDataHandler.sendEmptyMessage(0);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressLint("HandlerLeak")
	private class LoadDataHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			recommondSchoolListAdapter.notifyDataSetChanged();
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
			try {
				HttpURLConnection con = JxtUtil.getHttpConn(NetworkInfoUtil.baseUtl+"/recommond/getRecommondAdInfo/0.action");
				InputStream ris = con.getInputStream();
				String jsonStr = JxtUtil.streamToJsonString(ris);
				Gson gson = new Gson();
				List<RecommendSchoolInfoO> list = gson.fromJson(jsonStr, new TypeToken<List<RecommendSchoolInfoO>>(){}.getType());
				if(list!=null && list.get(0)!=null){
					Message msg = new Message();
					msg.obj=list.get(0);
					loadAdHandler.sendMessage(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	
	//百度地图按钮监听器
	private class BaiduMapClickListener implements OnClickListener{
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(mainActivity,RecommendMapActivity.class);
			startActivity(intent);
		}
	}
}
