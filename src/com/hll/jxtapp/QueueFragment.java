package com.hll.jxtapp;

import com.hll.adapter.SecPageAdapter;
import com.hll.entity.SecPageItemBean;

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
	private SecPageAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.queue, container, false);
	}

	public void onActivityCreated(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view = inflater.inflate(R.layout.queue, container, false);
		dropDownLocation = (ImageView) view.findViewById(R.id.id_down_queue);
		placeChoice = (ImageView) view.findViewById(R.id.id_place_choice);
		teachType = (ImageView) view.findViewById(R.id.id_teach_type);
		nearPlace = (ImageView) view.findViewById(R.id.id_near_place);
		orderLearn = (LinearLayout) view.findViewById(R.id.id_order_learn);
		queueWait = (LinearLayout) view.findViewById(R.id.id_queue_wait);
		appItroduce = (LinearLayout) view.findViewById(R.id.id_app_indroduce);
		functionInfo = (LinearLayout) view.findViewById(R.id.id_function_info);
		guaranteeService = (LinearLayout) view.findViewById(R.id.id_guarantee_service);
		feedbackPass = (LinearLayout) view.findViewById(R.id.id_feedback_pass);
		aboutUsCall = (LinearLayout) view.findViewById(R.id.id_about_us_call);
		listView = (ListView) view.findViewById(R.id.id_coach_self_choice);
		initEvent();

		// List<SecPageItemBean> dataList = new ArrayList<>();
		adapter = new SecPageAdapter(context);
		for (int i = 0; i < 6; i++) {
			adapter.add(new SecPageItemBean(R.drawable.coach, 3000 + i * 100, "小车", 1 + i, "查看更多" + i));
		}
		listView.setAdapter(adapter);
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

			// Toast.makeText(this, "我的消息", Toast.LENGTH_SHORT).show();
			break;
		case R.id.id_place_choice:

			break;
		case R.id.id_teach_type:

			break;
		case R.id.id_near_place:

			break;
		case R.id.id_order_learn:

			break;
		case R.id.id_queue_wait:

			break;
		case R.id.id_app_indroduce:

			break;
		case R.id.id_function_info:

			break;
		case R.id.id_guarantee_service:

			break;
		case R.id.id_feedback_pass:

			break;
		case R.id.id_about_us_call:

			break;
		}
	}

}
