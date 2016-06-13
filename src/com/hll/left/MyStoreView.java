package com.hll.left;

import com.hll.jxtapp.R;
import com.hll.adapter.ImgTxtAdapter;
import com.hll.basic.BaseView;
import com.hll.basic.LeftMoveView;
import com.hll.entity.ImgTxtBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MyStoreView extends BaseView {

	private Context context;
	private ListView listview;
	private ImgTxtAdapter adapter;
	private Button leftButton;
	
	public MyStoreView(Context context){
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
		
		this.listview.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!isScroll() || leftMoveView.getNowState() == LeftMoveView.LEFT){
					return true;
				}
				return false;
			}
		});
		
		for(int i=0;i<20;i++){
			ImgTxtBean b = new ImgTxtBean();
			b.setResid(R.drawable.ic_launcher);
			b.setText("item01 - "+(i+1));
			adapter.addObject(b);
		}
		
		
	}
}
