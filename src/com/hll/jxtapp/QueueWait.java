package com.hll.jxtapp;
/**
 * 排队等待实现类
 * @author heyi
 * 2016/6/25
 */
import java.util.Vector;
import com.hll.adapter.QueueGroupListAdapter;
import com.hll.entity.QueueListItemBean;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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
	private Vector<QueueListItemBean> list;
	private ListView listView;

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
	}

	@Override
	protected void onStart() {
		super.onStart();
		initPage();
		initEvent();
		list = new Vector<QueueListItemBean>();
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
		for (int i = 0; i < 10; i++) {
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

	}

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
			Intent intent1 = new Intent(this, MainActivity.class);
			startActivity(intent1);
			break;

		case R.id.id_chat_room_send:
			chatRoomShow.setText(chatRoomIn.getText());
			break;
		default:
			break;
		}
	}
}
