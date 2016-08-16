package com.hll.jxtapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import com.hll.entity.Item;
import com.hll.entity.OrderLeanO;
import com.hll.entity.ScheduleO;
import com.hll.entity.SchoolPlaceO;
import com.hll.entity.UserO;
import com.hll.util.JxtUtil;
import com.hll.util.NetworkInfoUtil;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.Builder;
public class OrderLearn extends FragmentActivity implements OnClickListener{
	private TextView orderLearnMsgTv;
	private Spinner exerciceItemChoiceSp, exerciceContentChoiceSp, exercicePlaceChoiceSp;
	private ImageButton todayMorningIB,tomorrowMorningIB, theDayAfterTomorrowMorningIB, todayAfternoonIB, tomorrowAfternoonIB, theDayAfterTomorrowAfternoonIB,todayEveningIB, tomorrowEveningIB, theDayAfterTomorrowEveningIB, orderSureButton;
	private List<Item> orderItemList = new ArrayList<>();
	private List<String> orderContentList = new ArrayList<>();
	private List<Item> orderPlaceList = new ArrayList<>();
	private ArrayAdapter<Item> orderItemAdapter;
	private ArrayAdapter<String> orderContentAdapter;
	private ArrayAdapter<Item> orderPlaceAdapter;
	private String userName, schoolName;
	private ImageView returnPre;
	private TextView  titleSce, menuSce;
	private OrderLeanO orderLean;                               //保存用户的预约信息
	private UserO userInfo;                                     //用户最近一次的登陆信息
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.order_learn);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		orderLearnMsgTv=(TextView) findViewById(R.id.id_order_learn_msg);
		exerciceItemChoiceSp=(Spinner) findViewById(R.id.id_exercice_item_choice);
		exerciceContentChoiceSp=(Spinner) findViewById(R.id.id_exercice_content_choice);
		exercicePlaceChoiceSp=(Spinner) findViewById(R.id.id_exercice_place_choice);
		todayMorningIB=(ImageButton) findViewById(R.id.id_today_morning);
		tomorrowMorningIB=(ImageButton) findViewById(R.id.id_tomorrow_morning);
		theDayAfterTomorrowMorningIB=(ImageButton) findViewById(R.id.id_theDayAfterTomorrow_morning);
		todayAfternoonIB=(ImageButton) findViewById(R.id.id_today_afternoon);
		tomorrowAfternoonIB=(ImageButton) findViewById(R.id.id_tomorrow_afternoon);
		theDayAfterTomorrowAfternoonIB=(ImageButton) findViewById(R.id.id_theDayAfterTomorrow_afternoon);
		todayEveningIB=(ImageButton) findViewById(R.id.id_today_evening);
		tomorrowEveningIB=(ImageButton) findViewById(R.id.id_tomorrow_evening);
		theDayAfterTomorrowEveningIB=(ImageButton) findViewById(R.id.id_theDayAfterTomorrow_evening);
		orderSureButton=(ImageButton) findViewById(R.id.id_order_sure_but);
		returnPre=(ImageView) findViewById(R.id.id_return);
		titleSce=(TextView) findViewById(R.id.id_sec_title);
		menuSce=(TextView) findViewById(R.id.id_sec_menu);
		context = this;
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		userInfo = JxtUtil.getLastUserInfo();
		findMyOrderInfo();
		boolean bl = orderPrepare();
		if(bl==true){
			showMyOrder();
		}
		exerciceItemChoiceSp.setOnItemSelectedListener(new SpinnerChangeListener());
		exercicePlaceChoiceSp.setOnItemSelectedListener(new SpinnerChangeListener());
		initEvent();
	}
	
	/**
	 * 显示我的预约信息
	 * liaoyun 2016-8-12
	 */
	private void showMyOrder() {
		userName = userInfo.getNickName();
		if(userInfo.getTel() != null && !userInfo.getTel().trim().equals("")){
			userName = userName + "  (" + userInfo.getTel() + ")";
		}
		schoolName = orderLean.getSchoolPlace().get(0).getSchoolName();
		orderLearnMsgTv.setText("     尊敬的"+userName+",下面给您带来"+schoolName+"未来三天预约驾校车详情。");
		//选择训练条件和内容
		orderItemChoice();
		
		String subj = orderLean.getSchedule().get(0).getSubj();       //上次保存的训练项目编号
		String placeId = orderLean.getSchedule().get(0).getPlaceId(); //上次保存的训练场地编号
		setSpinner(subj,exerciceItemChoiceSp);
		setSpinner(placeId,exercicePlaceChoiceSp);
		
		//显示三天预约的具体信息
		showThreeDaysOrder(orderLean.getSchedule());
	}

	/**
	 * 显示三天预约的具体信息 liaoyun 2016-8-12
	 */
	private void showThreeDaysOrder(List<ScheduleO>  list) {
		Date today = JxtUtil.getServerTime(orderLean.getServerTime());//服务器时间,即今天
		Date tomrrow = getDateAfter(today);                           //明天
		Date daftert = getDateAfter(tomrrow);                         //后天
		if(list == null || list.size()<1){
			fillSchedule(todayMorningIB,0);
			fillSchedule(todayAfternoonIB,0);
			fillSchedule(todayEveningIB,0);
			fillSchedule(tomorrowMorningIB,0);
			fillSchedule(tomorrowAfternoonIB,0);
			fillSchedule(tomorrowEveningIB,0);
			fillSchedule(theDayAfterTomorrowMorningIB,0);
			fillSchedule(theDayAfterTomorrowAfternoonIB,0);
			fillSchedule(theDayAfterTomorrowEveningIB,0);
			return;
		}
		ScheduleO todaySchedule = new ScheduleO();                    //今天的计划
		ScheduleO tomrrowSchedule = new ScheduleO();                  //明天的计划
		ScheduleO daftertSchedule = new ScheduleO();                  //后天的计划
		for (ScheduleO li : list) {
			if(isEqualDate(today, li.getOrderDate())){
				todaySchedule = li;
			}else if(isEqualDate(tomrrow, li.getOrderDate())){
				tomrrowSchedule = li;
			}else if(isEqualDate(daftert, li.getOrderDate())){
				daftertSchedule = li;
			}
		}
		fillSchedule(todayMorningIB,todaySchedule.getAm());
		fillSchedule(todayAfternoonIB,todaySchedule.getPm());
		fillSchedule(todayEveningIB,todaySchedule.getEv());
		fillSchedule(tomorrowMorningIB,tomrrowSchedule.getAm());
		fillSchedule(tomorrowAfternoonIB,tomrrowSchedule.getPm());
		fillSchedule(tomorrowEveningIB,tomrrowSchedule.getEv());
		fillSchedule(theDayAfterTomorrowMorningIB,daftertSchedule.getAm());
		fillSchedule(theDayAfterTomorrowAfternoonIB,daftertSchedule.getPm());
		fillSchedule(theDayAfterTomorrowEveningIB,daftertSchedule.getEv());
	}

	private void setSpinner(String code, Spinner spinner) {
		int count = spinner.getCount();
		for (int i=0; i<count; i++) {
			Item item = (Item) spinner.getItemAtPosition(i);
			String itemCode = item.getCode();
			if(code.trim().equals(itemCode.trim())){
				spinner.setSelection(i);
				break;
			}
		}
	}

	private void fillSchedule(ImageButton button, int isScheduled) {
		if(isScheduled == 0){
			button.setImageResource(R.drawable.choice_false);
			button.setTag(R.drawable.choice_false);
		}else if(isScheduled == 1){
			button.setImageResource(R.drawable.choice_true);
			button.setTag(R.drawable.choice_true);
		}
	}
	
	private int getOrderState(ImageButton button){
		Integer db = (Integer) button.getTag();
		db = db == null ? 0 : db;
		if(db == R.drawable.choice_true){
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 * 是否是同一天 liaoyun 2016-8-12
	 * @param date1
	 * @param date2
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean isEqualDate(Date date1,String date2){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String s1 = df.format(date1);
		if(s1.trim().equals(date2.trim())){
			return true;
		}
		return false;
	}
	/**
	 * 日期向后推一天
	 * @return
	 */
	private Date getDateAfter(Date date){
		Calendar ca = new GregorianCalendar();
		ca.setTime(date);
		ca.add(Calendar.DATE, 1);
		return ca.getTime();
		
	}
	/**
	 * 预约前的初始化工作，判断用户是否登陆，是否报名了驾校
	 * liaoyun 2016-8-12
	 */
	private boolean orderPrepare() {
		if(orderLean==null){
			JxtUtil.toastCenter(this, "没有连上网络，网络质量差 ",Toast.LENGTH_LONG);
			return false;
		}else if(orderLean.getLoginState() == 0){                                        //没有登陆服务器
			JxtUtil.toastCenter(this, "您还没有登陆 ",Toast.LENGTH_LONG);
			return false;
		}else if(orderLean.getSchoolPlace()==null || orderLean.getSchoolPlace().size()<1){//没有报名驾校
			JxtUtil.toastCenter(this, "您还没有在驾校报名 ",Toast.LENGTH_LONG);
			return false;
		}
		return true;
	}

	/**
	 * 用户的预约信息
	 * liaoyun 2016-8-11
	 */
	private void findMyOrderInfo() {
		Thread thread1 = new Thread(){
			public void run() {
				String url = NetworkInfoUtil.baseUtl + "/queue/getOrderLeanInfo.action";
				OrderLeanO myOrder = JxtUtil.getObjectFromServer(OrderLeanO.class, url);
				orderLean = myOrder;
			};
		};
		thread1.start();
		try {
			thread1.join();                  //阻塞主线程，使子线程与主线程同步
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 选择训练条件和内容
	 */
	private void orderItemChoice() {

		orderItemList=new ArrayList<>();
		orderContentList=new ArrayList<>();
		orderPlaceList=new ArrayList<>();
		
		orderItemList.add(new Item("2", "科目二"));
		orderItemList.add(new Item("3", "科目三"));
		
		orderContentList.add("直行");
		orderContentList.add("打方向");
		orderContentList.add("直角转弯");
		orderContentList.add("S曲线");
		orderContentList.add("侧方停车");
		orderContentList.add("上坡起步");
		orderContentList.add("倒车入库");
		
		List<SchoolPlaceO> pList = orderLean.getSchoolPlace();
		for (SchoolPlaceO sp : pList) {
			orderPlaceList.add(new Item(""+sp.getId(), sp.getPlaceName()));
		}
		
		orderItemAdapter=new ArrayAdapter<Item>(this,android.R.layout.simple_spinner_item,orderItemList);
		orderContentAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,orderContentList);
		orderPlaceAdapter=new ArrayAdapter<Item>(this,android.R.layout.simple_spinner_item,orderPlaceList);
		
		orderItemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		orderContentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		orderPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		exerciceItemChoiceSp.setAdapter(orderItemAdapter);
		exerciceContentChoiceSp.setAdapter(orderContentAdapter);
		exercicePlaceChoiceSp.setAdapter(orderPlaceAdapter);
	}

	private void initEvent() {
		todayMorningIB.setOnClickListener(this);
		tomorrowMorningIB.setOnClickListener(this);
		theDayAfterTomorrowMorningIB.setOnClickListener(this);
		todayAfternoonIB.setOnClickListener(this);
		tomorrowAfternoonIB.setOnClickListener(this);
		theDayAfterTomorrowAfternoonIB.setOnClickListener(this);
		todayEveningIB.setOnClickListener(this);
		tomorrowEveningIB.setOnClickListener(this);
		theDayAfterTomorrowEveningIB.setOnClickListener(this);
		orderSureButton.setOnClickListener(this);
		returnPre.setOnClickListener(this);
		titleSce.setOnClickListener(this);
		menuSce.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.id_return:
			finish();
			break;
		case R.id.id_today_morning:
			changeState(todayMorningIB);
			break;
		case R.id.id_today_afternoon:
			changeState(todayAfternoonIB);
			break;
		case R.id.id_today_evening:
			changeState(todayEveningIB);
			break;
		case R.id.id_tomorrow_morning:
			changeState(tomorrowMorningIB);
			break;
		case R.id.id_tomorrow_afternoon:
			changeState(tomorrowAfternoonIB);
			break;
		case R.id.id_tomorrow_evening:
			changeState(tomorrowEveningIB);
			break;
		case R.id.id_theDayAfterTomorrow_morning:
			changeState(theDayAfterTomorrowMorningIB);
			break;
		case R.id.id_theDayAfterTomorrow_afternoon:
			changeState(theDayAfterTomorrowAfternoonIB);
			break;
		case R.id.id_theDayAfterTomorrow_evening:
			changeState(theDayAfterTomorrowEveningIB);
			break;
		case R.id.id_order_sure_but:                       //保存数据到服务器
			saveOrNot();                                 
			break;
		default:
			break;
		}
	}
	
	private void changeState(ImageButton button){
		Integer state = (Integer) button.getTag();
		state = state == null ? 0 : state;
		switch(state) {
		    case R.drawable.choice_true:
		    	button.setImageResource(R.drawable.choice_false);
		    	button.setTag(R.drawable.choice_false);
		    break;
		    case R.drawable.choice_false:
		    default:
		    	button.setImageResource(R.drawable.choice_true);
		    	button.setTag(R.drawable.choice_true);
		    break;
		}
	}
	/**
	 * 是否保存预约计划 liaoyun 2016-8-13
	 */
	private void saveOrNot(){
		Builder dialog = new AlertDialog.Builder(OrderLearn.this);  
		dialog.setTitle("温馨提示!您好:");  
		dialog.setMessage("请认真检查已经选择的时间，方便自己的同时，也方便别人！没问题就点确定吧！");  
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener(){  
            public void onClick(DialogInterface dialog, int which) {  
            	//orderSureButton.setImageDrawable(getResources().getDrawable(R.drawable.order_sure_but));  
            	saveThreeDaysSchedule();
            }  
        });
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		});
		dialog.create().show();
	}
	
	/**
	 * 保存三天的预约计划 liaoyun 201608013
	 */
	@SuppressLint("SimpleDateFormat")
	private void saveThreeDaysSchedule(){
		boolean bl = orderPrepare();
		if(!bl){
			return;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		OrderLeanO myOrder = new OrderLeanO();
		
		ScheduleO todayOrder = new ScheduleO();                                     //今天的计划
		ScheduleO tomorOrder = new ScheduleO();                                     //明天的计划
		ScheduleO dafteOrder = new ScheduleO();                                     //后天的计划
		
		Date today = JxtUtil.getServerTime(orderLean.getServerTime());              //设置计划的日期
		Date tomor = getDateAfter(today);
		Date dafte = getDateAfter(tomor);
		todayOrder.setOrderDate(df.format(today));
		tomorOrder.setOrderDate(df.format(tomor));
		dafteOrder.setOrderDate(df.format(dafte));
		
		todayOrder.setAm(getOrderState(todayMorningIB));                             //今天上午
		todayOrder.setPm(getOrderState(todayAfternoonIB));                           //今天下午        
		todayOrder.setEv(getOrderState(todayEveningIB));                             //今天晚上
		tomorOrder.setAm(getOrderState(tomorrowMorningIB));                          //明天上午
		tomorOrder.setPm(getOrderState(tomorrowAfternoonIB));                        //明天下午
		tomorOrder.setEv(getOrderState(tomorrowEveningIB));                          //明天晚上
		dafteOrder.setAm(getOrderState(theDayAfterTomorrowMorningIB));               //后天上午
		dafteOrder.setPm(getOrderState(theDayAfterTomorrowAfternoonIB));             //后天下午
		dafteOrder.setEv(getOrderState(theDayAfterTomorrowEveningIB));               //后天晚上
		
		Item subject = (Item) exerciceItemChoiceSp.getSelectedItem();                //设置训练项目
		todayOrder.setSubj(subject.getCode());
		tomorOrder.setSubj(subject.getCode());
		dafteOrder.setSubj(subject.getCode());
		
		String schoolAccount = orderLean.getSchoolPlace().get(0).getSchoolAccount(); //驾校编号
		todayOrder.setSchoolAccount(schoolAccount);
		tomorOrder.setSchoolAccount(schoolAccount);
		dafteOrder.setSchoolAccount(schoolAccount);
		
		Item placeId = (Item) exercicePlaceChoiceSp.getSelectedItem();               //场地id
		todayOrder.setPlaceId(placeId.getCode());
		tomorOrder.setPlaceId(placeId.getCode());
		dafteOrder.setPlaceId(placeId.getCode());
		
		myOrder.getSchedule().add(todayOrder);
		myOrder.getSchedule().add(tomorOrder);
		myOrder.getSchedule().add(dafteOrder);
		
		new saveThreeDaysScheduleThread(myOrder).start();                            //将数据保存到服务器
	}
	
	/**
	 * 保存预约信息到服务器
	 * @author liaoyun 2016-8-13
	 */
	private class saveThreeDaysScheduleThread extends Thread{
		private OrderLeanO order;
		public saveThreeDaysScheduleThread(OrderLeanO order){
			super();
			this.order = order;
		}
		@Override
		public void run() {
			Looper.prepare();
			String jsonStr = JxtUtil.objectToJson(order);
			String url = NetworkInfoUtil.baseUtl + "/queue/saveOrderLeanInfo.action";
			OrderLeanO result = JxtUtil.postObjectFromServer(OrderLeanO.class, url, jsonStr);
			if(result == null){//保存失败
				JxtUtil.toastCenter(context, "保存失败!", Toast.LENGTH_LONG);
			}else{             //保存成功
				JxtUtil.toastCenter(context, "保存成功!", Toast.LENGTH_LONG);
				orderLean = result;
			}
			Looper.loop();
		}
	}
	
	private class SpinnerChangeListener implements OnItemSelectedListener{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Item sub = (Item) exerciceItemChoiceSp.getSelectedItem();
			Item place = (Item) exercicePlaceChoiceSp.getSelectedItem();
			String subjCode = sub.getCode();
			String placeCode = place.getCode();
			String subj = orderLean.getSchedule().get(0).getSubj();       //上次保存的训练项目编号
			String placeId = orderLean.getSchedule().get(0).getPlaceId(); //上次保存的训练场地编号
			if(subjCode.trim().equals(subj.trim()) && placeCode.trim().equals(placeId.trim())){
				showThreeDaysOrder(orderLean.getSchedule());
			}else{
				showThreeDaysOrder(null);
			}
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	} 
}
