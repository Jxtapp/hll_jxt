package com.hll.left;

import com.hll.jxtapp.R;
import com.hll.util.JxtUtil;
import com.hll.adapter.ImgTxtAdapter;
import com.hll.basic.BaseView;
import com.hll.basic.LeftMoveView;
import com.hll.entity.ImgTxtBean;
import com.hll.entity.UserO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class PersonInfoView extends BaseView {

	private Context context;
	private ListView listview;
	private ImgTxtAdapter adapter;
	private Button leftButton;
	
	public PersonInfoView(Context context){
		this.context = context;
	}
	
	public void init(){
		view = LayoutInflater.from(context).inflate(R.layout.item01, null);
		listview = (ListView)view.findViewById(R.id.item01_listview);
		leftButton = (Button)view.findViewById(R.id.item01_leftButton);
		adapter = new ImgTxtAdapter(context);
		listview.setAdapter(adapter);
		
		leftButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				leftMoveView.showHideLeftMenu();
			}
		});
		
		listview.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!isScroll() || leftMoveView.getNowState() == LeftMoveView.LEFT){
					return true;
				}
				return false;
			}
		});
		UserO userInfo = JxtUtil.getLastUserInfo();
		if(userInfo==null || userInfo.getAccount()==null){
			ImgTxtBean b = new ImgTxtBean();
			b.setResid(R.drawable.ic_launcher);
			b.setText("赶快登陆吧！");
			adapter.addObject(b);
		}else{
			ImgTxtBean bType = new ImgTxtBean();
			bType.setResid(R.drawable.ic_launcher);
			bType.setText("类型："+userInfo.getType());
			adapter.addObject(bType);
			
			ImgTxtBean bNickName = new ImgTxtBean();
			bNickName.setResid(R.drawable.ic_launcher);
			bNickName.setText("昵称："+userInfo.getNickName());
			adapter.addObject(bNickName);
			
			ImgTxtBean bEmail = new ImgTxtBean();
			bEmail.setResid(R.drawable.ic_launcher);
			bEmail.setText("邮箱： "+userInfo.getEmail());
			adapter.addObject(bEmail);
			
			ImgTxtBean bTel = new ImgTxtBean();
			bTel.setResid(R.drawable.ic_launcher);
			bTel.setText("电话： "+userInfo.getTel());
			adapter.addObject(bTel);
			
			ImgTxtBean lastLoadTime = new ImgTxtBean();
			lastLoadTime.setResid(R.drawable.ic_launcher);
			lastLoadTime.setText("最近登陆时间： "+userInfo.getLastLoadTime());
			adapter.addObject(lastLoadTime);
			
			ImgTxtBean lastLoadIp = new ImgTxtBean();
			lastLoadIp.setResid(R.drawable.ic_launcher);
			lastLoadIp.setText("最近登陆Ip地址： "+userInfo.getLastLoadIp());
			adapter.addObject(lastLoadIp);
			
			ImgTxtBean lastLoadPort = new ImgTxtBean();
			lastLoadPort.setResid(R.drawable.ic_launcher);
			lastLoadPort.setText("最近登陆端口号： "+userInfo.getLastLoadPort());
			adapter.addObject(lastLoadPort);
		}
	}
}
