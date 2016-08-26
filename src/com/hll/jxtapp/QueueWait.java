package com.hll.jxtapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hll.adapter.ItemOfChatContentAdapter;
import com.hll.adapter.QueueGroupListAdapter;
import com.hll.common.SocketDadaBaseHelper;
import com.hll.common.SocketService;
import com.hll.common.SocketService.SocketSendBinder;
import com.hll.entity.QueueListItemBean;
import com.hll.entity.Item;
import com.hll.entity.ItemOfChatContentBean;
import com.hll.entity.MessageChat;
import com.hll.entity.OrderLeanO;
import com.hll.entity.Queue;
import com.hll.entity.QueueListItemBean;
import com.hll.entity.SchoolPlaceO;
import com.hll.entity.SocketChatO;
import com.hll.entity.SocketMsg;
import com.hll.entity.UserO;
import com.hll.util.JxtUtil;
import com.hll.util.MyApplication;
import com.hll.util.NetworkInfoUtil;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/*
 * 排队等待页面
 * @author heyi
 * 2016/8/12
 */
public class QueueWait extends FragmentActivity implements OnClickListener {

	private ImageView returnPre;
	private TextView titleSce;
	private TextView queueWaitMsg;
	private TextView queueWaitUserMsg;
	private ListView chatRoomShowListView;
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
	private QueueGroupListAdapter queueStateAdapter;                                     //排队状态listView 适配器
	private List<QueueListItemBean> queueStateList = new ArrayList<QueueListItemBean>(); //排队状态数据list
	private ListView queueStateListView;                                                 //排队状态listView              
	private TextView queueGroup;
	private TextView chatRoom;
	private ItemOfChatContentAdapter chatAdapter;
	private List<SocketChatO> chatList = new ArrayList<>();                              //聊天框中的内容 list
	private TextView queueBtn;                                                           //加入排队
	private TextView queueNoBtn;                                                         //取消排队
	private  LinearLayout msgSendIn;
	private Spinner driverPlaceSpinner;
	private ArrayAdapter<Item> driverPlaces;
	private List<Item> driverPlacesList = new ArrayList<>();
	private SocketBroadcastReceiver socketBroadcastReceivr;                             //socket广播接收器  
	private SocketSendBinder socketSendBinder;                                          //绑定 socket service
	private List<Queue> queueInfo;                                                      //排队的数据     
	private LocalBroadcastManager localBroadcastManager;
	private SQLiteDatabase SocketDatabase;
	private int firstChatNum=0;
	private boolean hasHistoryChat = true;
	
	private OrderLeanO userQueueInfo;         //用户的排队信息
	private Context context;
	private Gson gson;
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
		chatRoomShowListView = (ListView) findViewById(R.id.id_chat_room_show);
		chatRoomIn = (TextView) findViewById(R.id.id_chat_room_in);
		chatRoonSend = (TextView) findViewById(R.id.id_chat_room_send);
		queueStateListView = (ListView) findViewById(R.id.id_queue_list_view);

		secScrollView=(ScrollView) findViewById(R.id.id_queue_wait_scrollview);
		queueGroup=(TextView) findViewById(R.id.id_queue_group);
		chatRoom=(TextView) findViewById(R.id.id_chat_room);
		View footer=getLayoutInflater().inflate(R.layout.queue_or_no, null);

		queueStateListView.addFooterView(footer);
		queueBtn=(TextView) findViewById(R.id.id_queue_btn);
		queueNoBtn=(TextView) findViewById(R.id.id_queue_no_btn);
		msgSendIn=(LinearLayout) findViewById(R.id.id_msg_in_and_send);
		driverPlaceSpinner=(Spinner) findViewById(R.id.id_driver_place_queue_spinner);
		context = this;
		SQLiteOpenHelper socketDbHelper = new SocketDadaBaseHelper(context, "Socket.db", null, 3);
		SocketDatabase = socketDbHelper.getWritableDatabase();
	}

	@Override
	protected void onStart() {
		super.onStart();
		SocketService.tranType = true;                       //改socket信息 通知为 广播
		getPrepareData();                                    //用户排队数据初始化，查询是否登陆，是否报名了驾校，可选场地...
		showUserInfoByPlace();                               //显示用户基本信息，姓名、电话
		initEvent();                                         //按钮初始化事件
		initList();
		initPage();
		boolean bl = prepare();
		if(bl==false){
			return;
		}
		registSocketReceiver();                              //注册广播接收器   
		startSocketService();                                //启动socket服务
		bindSocketService();                                 //绑定 socket 服务
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SocketService.tranType = false;                       //改socket信息 广播为通知
		unRegistSocketReceiver();                             //解除websocket广播接收器
		unBindSocketService();                                //解除绑定 websocketService
	}
	/**
	 * 用户排队数据初始化，查询是否登陆，是否报名了驾校，可选场地... liaoyun 2016-8-14
	 * @return
	 */
	private void getPrepareData() {
		Thread thread1 = new Thread(){
			@Override
			public void run() {
				String url = NetworkInfoUtil.baseUtl + "/queue/getOrderLeanInfo.action";
				OrderLeanO myOrder = JxtUtil.getObjectFromServer(OrderLeanO.class, url);
				userQueueInfo = myOrder;
			}
		};
		thread1.start();
		try {
			thread1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 预约前的初始化工作，判断用户是否登陆，是否报名了驾校
	 * liaoyun 2016-8-12
	 */
	private boolean prepare() {
		if(userQueueInfo==null){
			JxtUtil.toastCenter(this, "没有连上网络，网络质量差 ",Toast.LENGTH_LONG);
			return false;
		}else if(userQueueInfo.getLoginState() == 0){                                             //没有登陆服务器
			JxtUtil.toastCenter(this, "您还没有登陆 ",Toast.LENGTH_LONG);
			return false;
		}else if(userQueueInfo.getSchoolPlace()==null || userQueueInfo.getSchoolPlace().size()<1){//没有报名驾校
			JxtUtil.toastCenter(this, "您还没有在驾校报名 ",Toast.LENGTH_LONG);
			return false;
		}
		return true;
	}
	
	private void initList() {
		secScrollView.smoothScrollBy(0, 0);
		queueStateAdapter = new QueueGroupListAdapter(this, queueStateList);
		queueStateListView.setAdapter(queueStateAdapter);
		setListViewHeightBasedOnChildren(queueStateListView);
		chatAdapter=new ItemOfChatContentAdapter(this, chatList);
		chatRoomShowListView.setAdapter(chatAdapter);
		chatRoomShowListView.setOnScrollListener(new ChatOnScrollListener());   //向下滑动加载历史数据
	}

	//初始化页面
	private void initPage() {
		
		driverPlacesList.add(new Item("-1", "请选择场地"));
		List<SchoolPlaceO> list = userQueueInfo.getSchoolPlace();
		for (SchoolPlaceO vo : list) {
			driverPlacesList.add(new Item(""+vo.getId(), vo.getPlaceName()));
		}
		driverPlaces=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, driverPlacesList);
		driverPlaces.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		driverPlaceSpinner.setAdapter(driverPlaces);
		driverPlaceSpinner.setOnItemSelectedListener(new SpinnerOnItemClickListener());   //下拉框绑定监听器
	}

	private void initEvent() {
		returnPre.setOnClickListener(this);     //返回按钮点击事件
		chatRoonSend.setOnClickListener(this);  //
		queueGroup.setOnClickListener(this);    //
		chatRoom.setOnClickListener(this);      //
		msgSendIn.setOnClickListener(this);
	}

	//初始化页面
	private void showUserInfoByPlace() {
		titleSce.setText("排队等候");
		UserO userInfo = JxtUtil.getLastUserInfo();
		userName =  userInfo.getNickName();
		if(userInfo.getTel() != null && !userInfo.getTel().trim().equals("")){
			userName = userName + "  (" + userInfo.getTel() + ")";
		}
		placeName = "东湖";
		planWalkTime = 19;
		queueWaitUserMsg.setText("   尊敬的" + userName + ",您选择" + placeName + "训练场，需步行 " + planWalkTime + " 分钟。");
		onTheCarNum = 3;
		youQueueNum = 10;
		queueWaitMsg.setText("目前在车内的是第 " + onTheCarNum + " 位，你排在第" + youQueueNum + " 位");
		queueBtn.setText("确定排队");
		queueNoBtn.setText("取消排队");
		queueBtn.setOnClickListener(this);
		queueNoBtn.setOnClickListener(this);
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
			finish();                                          //退出页面
			break;
		case R.id.id_chat_room_send:
			sendChatMessage();                                 //群发消息
			String msg=chatRoomIn.getText().toString();
			//new SendMessageThread(msg).start();
			chatRoomIn.setText("");
			break;
		case R.id.id_queue_group:
			chatRoomShowListView.setVisibility(View.GONE);
			queueStateListView.setVisibility(View.VISIBLE);
			msgSendIn.setVisibility(View.GONE);
			queueWaitMsg.setVisibility(View.VISIBLE);
			queueWaitUserMsg.setVisibility(View.VISIBLE);
			//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			break;
		case R.id.id_chat_room:                                //进入聊天页面
			chatRoomShowListView.setVisibility(View.VISIBLE);
			queueStateListView.setVisibility(View.GONE);
			msgSendIn.setVisibility(View.VISIBLE);
			queueWaitMsg.setVisibility(View.GONE);
			queueWaitUserMsg.setVisibility(View.GONE);
			break;
		case R.id.id_queue_btn:                                //加入排队
			insertIntoTeam();
			break;
		case R.id.id_queue_no_btn:                             //取消排队
			giveUpTeam();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 群发消息
	 */
	private void sendChatMessage() {
		List<Queue> list = queueInfo;
		if(list == null || list.size()==0){
			JxtUtil.toastCenter(context, "没有人排队了", Toast.LENGTH_LONG);
			return;
		}
		String message = (String) chatRoomIn.getText().toString();
		if(message == null || message.trim().equals("")){
			return;
		}
		List<String> users = new ArrayList<>();
		for (Queue q : list) {
			users.add(q.getUserAccount().trim());
		}
		SocketMsg socketMsg = JxtUtil.createSocketMsg(SocketMsg.SCENE_CHAT, SocketMsg.TYPE_TRANSMIT, users, message);
		socketSendBinder.sendMessage(socketMsg);
	}

	/* * 开启一个线程，用来发送消息到后台数据库中
	 * @author heyi
	 * 2016/8/17
	 */
	private class SendMessageThread extends Thread{
		String msg;
		public SendMessageThread(String msg) {
			this.msg=msg;
		}
		Item item = (Item) driverPlaceSpinner.getSelectedItem();
		MessageChat messageChat;
		UserO userInfo = JxtUtil.getLastUserInfo();
		@Override
		public void run() {
			super.run();
			String placeName =item.getDesc();
			String schoolAccount = userQueueInfo.getSchoolPlace().get(0).getSchoolAccount();
			String userAccount=userInfo.getAccount();
			messageChat.setSchollAccount(schoolAccount);
			messageChat.setPlaceName(placeName);
			messageChat.setUserAccount(userAccount);
			messageChat.setMsg(msg);
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			messageChat.setSendTime(time);
			String msgJson=gson.toJson(messageChat);
			HttpURLConnection conn=JxtUtil.postHttpConn(NetworkInfoUtil.baseUtl+"/messageChat/addMessage.action", msgJson);
			//接收数据
			try {
				InputStream is=conn.getInputStream();
				String str=JxtUtil.streamToJsonString(is);
				SocketChatO chatContent=gson.fromJson(str, new TypeToken<ItemOfChatContentBean>(){}.getType());
				//添加最新消息到聊天框
				chatList.add(chatContent);
				//更新ui聊天框
				new ChatMsgHandler().sendEmptyMessage(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 更新了chatlist，发给handler去更新ui
	 * @author heyi
	 * 2016/8/17
	 */
	private class ChatMsgHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			chatAdapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * 取消排队 liaoyun 2016-8-15
	 */
	private void giveUpTeam() {
		Item item = (Item) driverPlaceSpinner.getSelectedItem();
		String code = item.getCode();
		final String url = NetworkInfoUtil.baseUtl + "/queue/giveUpMyQueue/" + code + ".action";
		final QueueStateHandler handler = new QueueStateHandler();
		new Thread(){
			public void run() {
				Looper.prepare();
				List<Queue> list = JxtUtil.getListObjectFromServer(Queue.class, url);
				Message msg = new Message();
				msg.obj = list;
				handler.sendMessage(msg);
				Looper.loop();
			};
		}.start();
	}

	/**
	 * 加入排队 liaoyun 2016-8-15
	 */
	private void insertIntoTeam() {
		Item item = (Item) driverPlaceSpinner.getSelectedItem();
		String code = item.getCode();
		final String url = NetworkInfoUtil.baseUtl + "/queue/insertQueue/" + code + ".action";
		final QueueStateHandler handler = new QueueStateHandler();
		new Thread(){
			public void run() {
				Looper.prepare();
				List<Queue> list = JxtUtil.getListObjectFromServer(Queue.class, url);
				Message msg = new Message();
				msg.obj = list;
				handler.sendMessage(msg);
				Looper.loop();
			};
		}.start();
	}

	/**
	 * 场地下拉框 监听
	 * @author LiaoYun 2016-8-23
	 */
	private class SpinnerOnItemClickListener implements OnItemSelectedListener{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String code = getPlaceSpinnerCode();
			if(!code.equals("-1")){
				getQueueStateByPlaceId(code);                               //按场地id加载排队信息
				List<SocketChatO> list = setOpenPageChat(SocketDatabase);   //刚 进入页面时，加载的聊天信息，取最近的10条
				chatList.addAll(list);
				chatAdapter.notifyDataSetChanged();                         //通知chatList显示更新
			}
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	/**
	 * 获取 当前 场地 spinner中的场地 code
	 * @return
	 */
	private String getPlaceSpinnerCode() {
		Item item = (Item) driverPlaceSpinner.getSelectedItem();
		String code = item.getCode();
		return code;
	}
	/**
	 * 按场地id加载排队信息 liaoyun 2016-8-15
	 * @param code
	 */
	public void getQueueStateByPlaceId(final String code) {
		final QueueStateHandler handler = new QueueStateHandler();
		new Thread(){
			public void run() {
				Looper.prepare();
				String url = NetworkInfoUtil.baseUtl + "/queue/findLastQueueState/" + code + ".action";
				List<Queue> list = JxtUtil.getListObjectFromServer(Queue.class, url);
				Message msg = new Message();       //显示当前的排队信息
				msg.obj = list;
				handler.sendMessage(msg);
				Looper.loop();
			};
		}.start();
	}

	@SuppressLint("HandlerLeak")
	private class QueueStateHandler extends Handler{
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			List<Queue> list = (List<Queue>) msg.obj;
			queueInfo = list;
			showQueueState(list);
		}
	}
	
	/**
	 * 在页面上展示排队信息 liaoyun 2016-8-15
	 * @param list
	 */
	protected void showQueueState(List<Queue> list) {
		queueStateList.clear();
		if(list == null || list.size()==0){
			JxtUtil.toastCenter(context, "现在还没有人排队", Toast.LENGTH_SHORT);
			queueStateAdapter.notifyDataSetChanged();
			return;
		}
		for (Queue vo : list) {
			queueStateList.add(new QueueListItemBean(vo.getMySet(),vo.getUserNickName(), R.drawable.queue_list_sure,R.drawable.queue_list_sure));
			queueStateAdapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * 注册广播接收器
	 */
	@SuppressWarnings("static-access")
	private void registSocketReceiver(){
		Log.e("socket","regist Socket broadcast Receiver ");
		localBroadcastManager = localBroadcastManager.getInstance(this);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.hll.socketBroadcast.SOCKET_BROADCAST");
		socketBroadcastReceivr = new SocketBroadcastReceiver();
		localBroadcastManager.registerReceiver(socketBroadcastReceivr, intentFilter);
		//registerReceiver(socketBroadcastReceivr, intentFilter);
	}
	
	/**
	 * 销毁广播接收器
	 */
	private void unRegistSocketReceiver(){
		try{
			localBroadcastManager.unregisterReceiver(socketBroadcastReceivr);
		}catch(Exception e){
			e.printStackTrace();
		}
		//unregisterReceiver(socketBroadcastReceivr);
	}
	
	/**
	 * 接收 websocket 推送的   广播接收器
	 * @author liaoyun 2016-8-20
	 */
	private class SocketBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context con, Intent intent) {
			//JxtUtil.toastCenter(context, "收到信息", Toast.LENGTH_LONG);
			//收到消息后的处理，两种情况，           1：聊天信息;        2:排队变化信息
			SocketMsg smg = (SocketMsg) intent.getSerializableExtra("message");
			if(smg.getScene()==SocketMsg.SCENE_CHAT){         //如果是聊天信息
				//1、数据存入数据库
				String placeId = getPlaceSpinnerCode();
				SocketChatO socketChat = new SocketChatO(placeId,smg.getAccount(), smg.getName(), smg.getNickName(), smg.getMessage(), smg.getTime());
				long newId = insertChat(SocketDatabase, socketChat);
				//2、数据加入到聊天显示框尾部
				socketChat.setId(newId);
				chatList.add(socketChat);
				chatAdapter.notifyDataSetChanged();
				//显示 到最下面一行
				chatRoomShowListView.setSelection(chatList.size()-1);
				//清空输入框
				chatRoomIn.setText("");
			}else if(smg.getScene()==SocketMsg.SCENE_QUEUE){  //如果是排队变化信息
				//更新队列信息
				JxtUtil.toastCenter(context, smg.getMessage(), Toast.LENGTH_LONG);
			}else if(smg.getScene()==SocketMsg.SCENE_LOGIN){  //websocekt 登陆成功
				JxtUtil.toastCenter(context, smg.getMessage(), Toast.LENGTH_LONG);
			}
		}
	}
	/**
	 * 向 表 chat_t 插入聊天信息,并返回 插入 的 id
	 * @param db
	 * @param socketChat
	 */
	private long insertChat(SQLiteDatabase db,SocketChatO socketChat){
		String code = getPlaceSpinnerCode();
		ContentValues values = new ContentValues();
		values.put("placeid", code);
		values.put("account", socketChat.getAccount());
		values.put("name", socketChat.getName());
		values.put("nickName", socketChat.getNickName());
		values.put("content", socketChat.getContent());
		values.put("sendTime", socketChat.getSendTime());
		db.insert("chat_t", null, values);
		//查询插入的当前记录的  id
		long id = 0;
		String sql = "select max(id) as id from chat_t where placeid = ?";
		Cursor result = db.rawQuery(sql, new String[]{code});
		if(result.moveToFirst()){
			id = result.getLong(result.getColumnIndex("id"));
		}
		return id;
	}
	
	/**
	 * 从数据库 chat_t 中取出数据，查询历史记录。 LiaoYun 2016-8-23
	 * @param db
	 * @param start 开始位置（用 id指定即可）
	 * @param num   条数
	 * @return
	 */
	private List<SocketChatO> getChat(SQLiteDatabase db,long start,int num){
		String sql="select t.* from (select id, account, name, nickName, content, sendTime from chat_t "
				+ "where placeid = ? and id < ? order by id desc limit ? offset 0) t order by t.id";
		String code = getPlaceSpinnerCode();
		Cursor result = db.rawQuery(sql, new String[]{code,""+start, ""+num});
		List<SocketChatO> list = new ArrayList<SocketChatO>();
		if(result.moveToFirst()){
			do{
				long id = result.getInt(result.getColumnIndex("id"));
				String account = result.getString(result.getColumnIndex("account"));
				String name = result.getString(result.getColumnIndex("name"));
				String nickName = result.getString(result.getColumnIndex("nickName"));
				String content = result.getString(result.getColumnIndex("content"));
				String sendTime = result.getString(result.getColumnIndex("sendTime"));
				SocketChatO vo = new SocketChatO(id, code, account, name, nickName, content, sendTime);
				list.add(vo);
			}while(result.moveToNext());
		}
		return list;
	}
	
	/**
	 * 获取 刚 进入页面时，加载的聊天信息，取最近的10条, LiaoYun 2016-8-23
	 * @param db
	 * @return
	 */
	private List<SocketChatO> setOpenPageChat(SQLiteDatabase db){
		String sql = "select t.* from (select id, account, name, nickName, content, sendTime from chat_t"
				+ " where placeid = ? order by id desc limit 10 offset 0) t order by t.id";
		String code = getPlaceSpinnerCode();
		Cursor result = db.rawQuery(sql,new String[]{code});
		List<SocketChatO> list = new ArrayList<SocketChatO>();
		if(result.moveToFirst()){
			do{
				long id = result.getInt(result.getColumnIndex("id"));
				String account = result.getString(result.getColumnIndex("account"));
				String name = result.getString(result.getColumnIndex("name"));
				String nickName = result.getString(result.getColumnIndex("nickName"));
				String content = result.getString(result.getColumnIndex("content"));
				String sendTime = result.getString(result.getColumnIndex("sendTime"));
				SocketChatO vo = new SocketChatO(id, code, account, name, nickName, content, sendTime);
				list.add(vo);
			}while(result.moveToNext());
		}
		return list;
	}
	
	/**
	 * chat list 滑动监听，，，当滑动到最上面时，下拉加载历史数据
	 * @author LiaoYun 2016-8-23
	 */
	private class ChatOnScrollListener implements OnScrollListener{

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			firstChatNum = firstVisibleItem;
		}

		@Override
		public void onScrollStateChanged(AbsListView arg0, int scrollState) {
			//当第一条显示为第0条数据 ，且滑动为静止，且有历史数据时，加载历时数据
			if(firstChatNum==0 && scrollState==OnScrollListener.SCROLL_STATE_IDLE && hasHistoryChat){
				long start = chatList.get(0).getId();
				List<SocketChatO> list = getChat(SocketDatabase, start, 10);
				if(list==null || list.size()==0){
					hasHistoryChat = false;           //没有历史数据了
					JxtUtil.toastCenter(context, "没有数据了", Toast.LENGTH_LONG);
				}else{
					chatList.addAll(0, list);
					chatAdapter.notifyDataSetChanged();
				}
			}
		}
		
	}
	/**
	 * 如果SocketService还没有启动，则启动SocketService
	 */
	private void startSocketService(){
		boolean isRuning = SocketService.IS_STARTED;
		if(!isRuning){
			Log.e("socket","服务 还没有启动。。。。。。。。。。。。。。。。。。。。");
			Intent intent = new Intent(MyApplication.getContext(), SocketService.class);
			startService(intent);
		}
	}
	/**
	 * 绑定 socket 服务
	 */
	private void bindSocketService(){
		Intent bindIntend = new Intent(context, SocketService.class);
		bindService(bindIntend, connection, BIND_AUTO_CREATE);
	}
	
	/**
	 * 取消 绑定 socket 服务
	 */
	private void unBindSocketService(){
		unbindService(connection);
	}
	
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder iBinder) {
			socketSendBinder = (SocketSendBinder) iBinder;
		}
	};
	
}
