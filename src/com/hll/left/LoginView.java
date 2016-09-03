package com.hll.left;

import com.hll.jxtapp.R;
import com.hll.adapter.ImgTxtAdapter;
import com.hll.basic.BaseView;
import com.hll.entity.ImgTxtBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class LoginView extends BaseView {

	private Context context;
	private ListView listview;
	private ImgTxtAdapter adapter;
	private Button leftButton;
	
	public LoginView(Context context){
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
			}
		});
		
		this.listview.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return isScroll;
			}
		});
		//显示用户信息，如果没有用户的信息，则显示未登陆
		ImgTxtBean b = new ImgTxtBean();
		b.setResid(R.drawable.ic_launcher);
		b.setText("未登陆");
		adapter.addObject(b);
	}
}
