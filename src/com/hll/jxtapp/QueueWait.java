package com.hll.jxtapp;
/*
 * 排队等待页面
 * @author heyi
 * 2016/8/12
 */
import java.util.ArrayList;
import java.util.List;

import com.hll.adapter.QueueGroupListAdapter;
import com.hll.entity.QueueListItemBean;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class QueueWait extends FragmentActivity implements OnClickListener {

	private ImageView returnPre;
	private TextView titleSce;
	private TextView queueWaitMsg;
	private TextView queueWaitUserMsg;
	private TextView chatRoomShow;
	private TextView chatRoomIn;
	private TextView chatRoonSend;
	private String userName;
	private String placeName;
	private int planWalkTime;
	private int onTheCarNum;
	private int youQueueNum;
	private ScrollView secScrollView;
	private QueueGroupListAdapter adapter;
	private List<QueueListItemBean> list;
	private ListView listView;
	private TextView queueGroup;
	private TextView chatRoom;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.queue_wait);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

		titleSce = (TextView) findViewById(R.id.id_sec_title);
		queueWaitMsg = (TextView) findViewById(R.id.id_queue_wait_queue_msg);
		queueWaitUserMsg = (TextView) findViewById(R.id.id_queue_wait_user_msg);
		returnPre = (ImageView) findViewById(R.id.id_return);
		chatRoomShow = (TextView) findViewById(R.id.id_chat_room_show);
		chatRoomIn = (TextView) findViewById(R.id.id_chat_room_in);
		chatRoonSend = (TextView) findViewById(R.id.id_chat_room_send);
		listView = (ListView) findViewById(R.id.id_queue_list_view);

		secScrollView=(ScrollView) findViewById(R.id.id_queue_wait_scrollview);
		queueGroup=(TextView) findViewById(R.id.id_queue_group);
		chatRoom=(TextView) findViewById(R.id.id_chat_room);
	}

	@Override
	protected void onStart() {
		super.onStart();
		initPage();
		initEvent();
		list = new ArrayList<QueueListItemBean>();
		initList();
	}

	private void initList() {

		secScrollView.smoothScrollBy(0, 0);
		initData();
		adapter = new QueueGroupListAdapter(this, list);
		listView.setAdapter(adapter);
		setListViewHeightBasedOnChildren(listView);
	}

	int index = 1;
	private void initData() {
		for (int i = 0; i < 20; i++) {
			if(index<10){
				list.add(new QueueListItemBean(index,".    小李"+index, R.drawable.queue_list_sure,R.drawable.queue_list_sure));
			}
			else list.add(new QueueListItemBean(index,".小李"+index, R.drawable.queue_list_sure,R.drawable.queue_list_sure));
			index++;
		}
	}

	private void initEvent() {

		returnPre.setOnClickListener(this);
		chatRoonSend.setOnClickListener(this);
		queueGroup.setOnClickListener(this);
		chatRoom.setOnClickListener(this);

	}

	//初始化页面
	private void initPage() {
		titleSce.setText("排队等候");
		userName = "李先生（15071285589）";
		placeName = "东湖";
		planWalkTime = 19;
		queueWaitUserMsg.setText("   尊敬的" + userName + ",您选择" + placeName + "训练场，需步行 " + planWalkTime + " 分钟。");
		onTheCarNum = 3;
		youQueueNum = 10;
		queueWaitMsg.setText("目前在车内的是第 " + onTheCarNum + " 位，你排在第" + youQueueNum + " 位");

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

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.id_return:
			finish();
			break;

		case R.id.id_chat_room_send:
			chatRoomShow.setText(chatRoomIn.getText());
			break;
		case R.id.id_queue_group:
			chatRoomShow.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			break;
		case R.id.id_chat_room:
			chatRoomShow.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}
}
