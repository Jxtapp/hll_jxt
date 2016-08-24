package com.hll.common;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.hll.entity.SocketMsg;
import com.hll.util.JxtUtil;
import com.hll.util.MyApplication;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

/**
 * websocket 连接 service ,用于时时接收推送的数据
 * @author liaoyun 2016-8-20
 */
public class SocketService extends Service{
	public static boolean tranType = false;                         //通知还是广播;当为false 时，通知；当为true时，广播
	private String serverSocketAddress = "ws://192.168.1.3:8080"
			+ "/hll/websocket/ly.action";                           //服务器socket地址
	private WebSocketClient client;                                 //连接客户端
	private Draft_17 draft;                                         //连接协议
	private NotificationManager nfManager;
	private Notification notification;
	private LocalBroadcastManager localBroadcastManager;     
	private SocketSendBinder socketSendBinder = new SocketSendBinder();
	public static boolean IS_STARTED = false;
	
	@Override
	public void onCreate() {
		Log.e("socket ","socket service onCreate ......");
		super.onCreate();
		IS_STARTED = true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("socket ","connecting ...onStartCommand...");
		draft = new Draft_17(); 
		socketConnect();                                                  //连接socket服务
		localBroadcastManager = LocalBroadcastManager.getInstance(this);
		nfManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification(R.drawable.alert_dark_frame, "有新信息了", System.currentTimeMillis());
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return socketSendBinder;
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		return true;                              //返回 true ，使其可以被重复绑定
	}
	/**
	 * 连接 websocket服务 
	 */
	private boolean socketConnect(){
		try {
			if(client==null){
				client = new SocketClient(new URI(serverSocketAddress), draft);  //实例化client
				client.connect();                                                //调用connect 进行连接
			}
			return true;
		} catch (Exception e) {
			Log.e("socket ",e.getMessage());
			return false;
		}
	}
	
	/**
	 * socket 客户端
	 * @author liaoyun
	 */
	private class SocketClient extends WebSocketClient{

		public SocketClient(URI serverUri, Draft draft) {
			super(serverUri, draft);
			Log.e("socket","socket instance... ");
		}

		@Override
		public void onClose(int arg0, String arg1, boolean arg2) {
			Log.e("socket","socket close ... ");
		}

		@Override
		public void onError(Exception exc) {
			Log.e("socket","socket 出错了   " + exc.getMessage());
		}

		@Override
		public void onMessage(String msg) {                              //获的 服务器 发的信息
			Log.e("socket","sever say: "+msg);
			if(tranType){
				sendBroadcast(msg);                                      //广播
			}else{
				showNotification(msg);                                   //通知 
			}
		}

		@Override
		public void onOpen(ServerHandshake handShake) {                  //连接到服务器
			Log.e("socket","连接到服务器 " + serverSocketAddress);
			String loginMsg = JxtUtil.createSocketMsgString(SocketMsg.SCENE_LOGIN, 0, null, "登陆成功了"); 
			client.send(loginMsg);
		}
	}
	
	/**
	 * 向服务端发送信息
	 */
	public void sendMsgToServer(SocketMsg socketMsg) {
		if(client != null){
			final String msg = JxtUtil.objectToJson(socketMsg);
			if(msg != null){
				new Thread(){
					public void run() {
						try{
							client.send(msg);
						}catch(Exception e){
							try{
								Log.e("socket","socket 连接失败，正在建立新的连接 1");
								Thread.sleep(1500);
								client.send(msg);
							}catch(Exception e1){
								try{
									Log.e("socket","socket 连接失败，正在建立新的连接 2");
									client.connect();
									Thread.sleep(1500);
									client.send(msg);
								}catch(Exception e2){
									try{
										Log.e("socket","socket 连接失败，正在建立新的连接 3");
										client.connect();
										Thread.sleep(1500);
										client.send(msg);
									}catch(Exception e3){
										JxtUtil.toastCenter(MyApplication.getContext(), "网络异常，请稍后再试", Toast.LENGTH_LONG);
									}
								}
							}
						}
					}
				}.start();
			}
		}
	}
	
	/**
	 * 发送通知  liaoyun 2016-8-20
	 * @param socketMsg
	 */
	private void showNotification(String strFromServer){
		final SocketMsg socketMsg = JxtUtil.jsonStrToObject(strFromServer, SocketMsg.class);
		if(socketMsg == null){
			return;
		}
		new Thread(){
			@SuppressWarnings("deprecation")
			public void run() {
				String info = socketMsg.getMessage();
				if(info != null && !info.equals("")){
					notification.setLatestEventInfo(MyApplication.getContext(), "from jxt", info, null);
					nfManager.notify(1,notification);
				}
			};
		}.start();
	}
	/**
	 * 发送广播 liaoyun 2016-8-20
	 * @param strFromServer
	 */
	private void sendBroadcast(String strFromServer){
		final SocketMsg smg = JxtUtil.jsonStrToObject(strFromServer, SocketMsg.class);
		if(smg == null){
			return;
		}
		new Thread(){
			public void run() {
				Log.e("socket","sendBroadcast  com.hll.socketBroadcast.SOCKET_BROADCAST");
				Bundle bundle = new Bundle();
				bundle.putSerializable("message", smg);
				Intent intent = new Intent("com.hll.socketBroadcast.SOCKET_BROADCAST");
				intent.putExtras(bundle);
				localBroadcastManager.sendBroadcast(intent);
			};
		}.start();
	}
	
	/**
	 * 与 activity 通信
	 * @author liaoyun 2016-8-20
	 */
	public class SocketSendBinder extends Binder{
		
		public void sendMessage(SocketMsg socketMsg){
			sendMsgToServer(socketMsg);
		}
	}
}
