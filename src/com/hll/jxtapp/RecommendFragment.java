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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
	private int lastItem;
	private LoadDataHandler loadDataHandler;
	private LinearLayout recomdendAd;   //特别推荐栏
	private LoadAdHandler loadAdHandler = new LoadAdHandler(); //加载特别推荐的图片
	private ImageView recommendAdImg;//特别推荐栏图片
	private TextView recommendAdPrice;//特别推荐栏价格
	private SchoolSelectBy schoolSelect = new SchoolSelectBy();
	private Gson gson = new Gson();
	
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
		if(loadDataHandler==null){
			loadDataHandler = new LoadDataHandler();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		//加载 特别推荐栏的数据
		recomdendAdLoadData(recomdendAd);
		if(driverSchoolInfoList==null){
			driverSchoolInfoList = new ArrayList<RecommendSchoolInfoO>();
		}
		for(int i=0; i<5; i++){
			driverSchoolInfoList.add(new RecommendSchoolInfoO(null,1000,"address"+i,"count"+i));
		}
		
		lview = (ListView) mainActivity.findViewById(R.id.driverschoollist);
		//绑定数据源 和 listView
		if(recommondSchoolListAdapter==null){
			recommondSchoolListAdapter = new RecommondSchoolListAdapter(mainActivity, driverSchoolInfoList);
		}
		lview.setAdapter(recommondSchoolListAdapter);
		//如果滑动到最下方，加载更多数据 监听
		lview.setOnScrollListener(new driverScollScrollListener());
		//listVidw 元素 的 单击事件监听器
		lview.setOnItemClickListener(new driverListClickListener());
	}

	private class driverScollScrollListener implements OnScrollListener{
		@Override
		public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			lastItem = firstVisibleItem + visibleItemCount;
		}

		@Override
		public void onScrollStateChanged(AbsListView listView, int scrollState) {
			//如果滑动到最下方，加载更多数据
			Log.i("key",lastItem+"   "+recommondSchoolListAdapter.getCount());
			if(lastItem==recommondSchoolListAdapter.getCount() && scrollState==OnScrollListener.SCROLL_STATE_IDLE){
				driverSchoolInfoList = recommondSchoolListAdapter.getDriverSchoolInfoList();
				new loadDataThread().start();
			}
			
		}
	}
	private class driverListClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Log.i("on item click",arg2+"  "+arg3);
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
			//String sss = JxtUtil.doPost(NetworkInfoUtil.baseUtl+"/recommond/getSchoolList.action", selectJson);
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
}
