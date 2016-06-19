package com.hll.jxtapp;
/**
 * 驾校通预约排队页面activity
 * @author heyi
 * 2016/6/1
 */
import java.util.ArrayList;
import java.util.List;

/**
 * app第二个页面，主要实现排队的功能
 * @author  heyi
 * 2016/5/27
 */
import com.hll.adapter.SecPageAdapter;
import com.hll.entity.SecPageItemBean;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class QueueFragment extends Fragment implements OnClickListener {
	private ImageView dropDownLocation;
	private ImageView placeChoice;
	private ImageView teachType;
	private ImageView nearPlace;
	private LinearLayout orderLearn;
	private LinearLayout queueWait;
	private LinearLayout appItroduce;
	private LinearLayout functionInfo;
	private LinearLayout guaranteeService;
	private LinearLayout feedbackPass;
	private LinearLayout aboutUsCall;
	private ListView listView;
	private Context context;
	private List<SecPageItemBean> list;
	private SecPageAdapter adapter;
	private Activity mainActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.queue, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mainActivity=getActivity();
		//View view = inflater.inflate(R.layout.queue, container, false);
		dropDownLocation = (ImageView) mainActivity.findViewById(R.id.id_down_queue);
		placeChoice = (ImageView) mainActivity.findViewById(R.id.id_place_choice);
		teachType = (ImageView) mainActivity.findViewById(R.id.id_teach_type);
		nearPlace = (ImageView) mainActivity.findViewById(R.id.id_near_place);
		orderLearn = (LinearLayout) mainActivity.findViewById(R.id.id_order_learn);
		queueWait = (LinearLayout) mainActivity.findViewById(R.id.id_queue_wait);
		appItroduce = (LinearLayout) mainActivity.findViewById(R.id.id_app_indroduce);
		functionInfo = (LinearLayout) mainActivity.findViewById(R.id.id_function_info);
		guaranteeService = (LinearLayout) mainActivity.findViewById(R.id.id_guarantee_service);
		feedbackPass = (LinearLayout) mainActivity.findViewById(R.id.id_feedback_pass);
		aboutUsCall = (LinearLayout) mainActivity.findViewById(R.id.id_about_us_call);
		listView = (ListView) mainActivity.findViewById(R.id.id_coach_self_choice);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		initEvent();

		list=new ArrayList<SecPageItemBean>();
		
		// List<SecPageItemBean> dataList = new ArrayList<>();
		
		for (int i = 0; i < 20; i++) {
			list.add(new SecPageItemBean(R.drawable.coach, 3000+(i+1)*10, "小车",i+1, "查看更多" ));
		}
//		if(adapter==null){
//			adapter = new SecPageAdapter(mainActivity,list);
//		}
		
		listView.setAdapter(new SecPageAdapter(mainActivity,list));
		// return view;
	}

	private void initEvent() {

		dropDownLocation.setOnClickListener(this);
		placeChoice.setOnClickListener(this);
		teachType.setOnClickListener(this);
		nearPlace.setOnClickListener(this);
		orderLearn.setOnClickListener(this);
		queueWait.setOnClickListener(this);
		appItroduce.setOnClickListener(this);
		functionInfo.setOnClickListener(this);
		guaranteeService.setOnClickListener(this);
		feedbackPass.setOnClickListener(this);
		aboutUsCall.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.id_down_queue:

			Toast.makeText(mainActivity, "排队", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_place_choice:

			Toast.makeText(mainActivity, "选择场地", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_teach_type:

			Toast.makeText(mainActivity, "教学类型", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_near_place:

			Toast.makeText(mainActivity, "附近场地", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_order_learn:

			Toast.makeText(mainActivity, "学习次数", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_queue_wait:

			Toast.makeText(mainActivity, "排队等待", Toast.LENGTH_SHORT).show();
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

}
