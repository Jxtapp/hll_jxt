package com.hll.jxtapp;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.hll.entity.SocketMsg;
import com.hll.util.JxtUtil;
import com.hll.util.NetworkInfoUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
  
/**
 * webSocket 测试 
 * @author liaoyun 2016-8-17
 */
public class ChatTestActivity extends Activity implements OnClickListener{
	private String serverSocketAddress = "ws://192.168.191.1:8080/hll/websocket/ly.action";   //服务器socket地址
	private WebSocketClient client;                                 //连接客户端
	private Draft_17 draft;                                         //连接协议
	private EditText editText;
	private Button btn;
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_test_activity);
		editText = (EditText) findViewById(R.id.edit);
		btn = (Button) findViewById(R.id.btn);
		textView = (TextView) findViewById(R.id.show);
		draft = new Draft_17();
		Log.e("socket "," 连接地址为 "+serverSocketAddress);
		try {
			if(client==null){
				client = new SocketClient(new URI(serverSocketAddress), draft);  //实例化client
				client.connect();                                                //调用connect 进行连接
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String loginMsg = JxtUtil.createSocketMsg(SocketMsg.SCENE_LOGIN, 0, null, null); 
				client.send(loginMsg);
			}
		} catch (URISyntaxException e) {
			Log.e("socket ",e.getMessage());
		}
		btn.setOnClickListener(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
	}
	/**
	 * socket 客户端
	 * @author liaoyun 2016-8-17
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
		public void onMessage(String msg) {                              //获的 服务器 通知
			Log.e("socket","sever say: "+msg);
			textView.setText("sever say: "+msg);
		}

		@Override
		public void onOpen(ServerHandshake handShake) {                  //连接到服务器
			Log.e("socket","连接到服务器 " + serverSocketAddress);
		}
	}
	@Override
	public void onClick(View view) {
		Log.e("socket","click....");
		switch (view.getId()) {
			case R.id.btn:
				sendMsgToServer();
				break;
			default:
				Log.e("socket","click  not....");
				break;
		}
	}

	private void sendMsgToServer() {
		try {
			if(client != null){
				List<String> users = new ArrayList<>();
				users.add(NetworkInfoUtil.accountId);
				String msg = JxtUtil.createSocketMsg(SocketMsg.SCENE_CHAT, SocketMsg.TYPE_BROADCAST, users, "hellow");
				if(msg != null){
					client.send(msg);
				}
			}
		} catch (Exception e) {
			Log.e("socket",e.getMessage());
			Log.e("socket","..........................");
		}
	}
}
