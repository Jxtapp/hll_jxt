package com.hll.jxtapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.hll.entity.OrderLeanO;
import com.hll.entity.ScheduleO;
import com.hll.entity.SchoolPlaceO;
import com.hll.entity.UserO;
import com.hll.util.JxtUtil;
import com.hll.util.NetworkInfoUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.Builder;
public class OrderLearn extends FragmentActivity implements OnClickListener{

	private TextView orderLearnMsgTv;
	private Spinner exerciceItemChoiceSp;
	private Spinner exerciceContentChoiceSp;
	private Spinner exercicePlaceChoiceSp;
	private ImageButton todayMorningIB;
	private ImageButton tomorrowMorningIB;
	private ImageButton theDayAfterTomorrowMorningIB;
	private ImageButton todayAfternoonIB;
	private ImageButton tomorrowAfternoonIB;
	private ImageButton theDayAfterTomorrowAfternoonIB;
	private ImageButton todayEveningIB;
	private ImageButton tomorrowEveningIB;
	private ImageButton theDayAfterTomorrowEveningIB;
	private ImageButton orderSureButton;
	private List<String> orderItemList;
	private List<String> orderContentList;
	private List<String> orderPlaceList;
	private ArrayAdapter<String> orderItemAdapter;
	private ArrayAdapter<String> orderContentAdapter;
	private ArrayAdapter<String> orderPlaceAdapter;
	private String userName;
	private String schoolName;
	private ImageView returnPre;
	private TextView  titleSce;
	private TextView  menuSce;
	private OrderLeanO orderLean;      //保存用户的预约信息
	private UserO userInfo;            //用户最近一次的登陆信息
	
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
		ScheduleO todaySchedule = null;                               //今天的计划
		ScheduleO tomrrowSchedule = null;                             //明天的计划
		ScheduleO daftertSchedule = null;                             //后天的计划
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

	private void fillSchedule(ImageButton button, int isScheduled) {
		if(isScheduled == 0){
			button.setImageResource(R.drawable.choice_false);
			button.setTag(R.drawable.choice_false);
		}else if(isScheduled == 1){
			button.setImageResource(R.drawable.choice_true);
			button.setTag(R.drawable.choice_true);
		}
	}

	/**
	 * 是否是同一天 liaoyun 2016-8-12
	 * @param date1
	 * @param date2
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean isEqualDate(Date date1,Date date2){
		int year1 = date1.getYear();
		int mont1 = date1.getMonth();
		int day1  = date1.getDate();
		int year2 = date2.getYear();
		int mont2 = date2.getMonth();
		int day2  = date2.getDate();
		if(year1 == year2 && mont1==mont2 && day1==day2){
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
		}else if(orderLean.getLoginState() == 0){//没有登陆服务器
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
		
		orderItemList.add("科目二");
		orderItemList.add("科目三");
		
		orderContentList.add("直行");
		orderContentList.add("打方向");
		orderContentList.add("直角转弯");
		orderContentList.add("S曲线");
		orderContentList.add("侧方停车");
		orderContentList.add("上坡起步");
		orderContentList.add("倒车入库");
		
		List<SchoolPlaceO> pList = orderLean.getSchoolPlace();
		for (SchoolPlaceO sp : pList) {
			orderPlaceList.add(sp.getPlaceName());
		}
		
		orderItemAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,orderItemList);
		orderContentAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,orderContentList);
		orderPlaceAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,orderPlaceList);
		
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
			ImageButton ib0=(ImageButton)todayMorningIB;
			assert(R.id.id_today_morning==todayMorningIB.getId());
			Integer integer0 = (Integer) todayMorningIB.getTag();
			   integer0 = integer0 == null ? 0 : integer0;
			   switch(integer0) {
			    case R.drawable.choice_true:
			    	todayMorningIB.setImageResource(R.drawable.choice_false);
			    	todayMorningIB.setTag(R.drawable.choice_false);
			     break;
			    case R.drawable.choice_false:
			    default:
			    	todayMorningIB.setImageResource(R.drawable.choice_true);
			    	todayMorningIB.setTag(R.drawable.choice_true);
			     break;
			}
			break;
		case R.id.id_today_afternoon:
			ImageButton ib1=(ImageButton)todayAfternoonIB;
			assert(R.id.id_today_morning==todayAfternoonIB.getId());
			Integer integer1 = (Integer) todayAfternoonIB.getTag();
			   integer1 = integer1 == null ? 0 : integer1;
			   switch(integer1) {
			    case R.drawable.choice_true:
			    	todayAfternoonIB.setImageResource(R.drawable.choice_false);
			    	todayAfternoonIB.setTag(R.drawable.choice_false);
			     break;
			    case R.drawable.choice_false:
			    default:
			    	todayAfternoonIB.setImageResource(R.drawable.choice_true);
			    	todayAfternoonIB.setTag(R.drawable.choice_true);
			     break;
			}
			break;
		case R.id.id_today_evening:
			ImageButton ib2=(ImageButton)todayEveningIB;
			assert(R.id.id_today_morning==todayEveningIB.getId());
			Integer integer2 = (Integer) todayEveningIB.getTag();
			   integer2 = integer2 == null ? 0 : integer2;
			   switch(integer2) {
			    case R.drawable.choice_true:
			    	todayEveningIB.setImageResource(R.drawable.choice_false);
			    	todayEveningIB.setTag(R.drawable.choice_false);
			     break;
			    case R.drawable.choice_false:
			    default:
			    	todayEveningIB.setImageResource(R.drawable.choice_true);
			    	todayEveningIB.setTag(R.drawable.choice_true);
			     break;
			}
			break;
		case R.id.id_tomorrow_morning:
			ImageButton ib3=(ImageButton)tomorrowMorningIB;
			assert(R.id.id_today_morning==tomorrowMorningIB.getId());
			Integer integer3 = (Integer) tomorrowMorningIB.getTag();
			   integer3 = integer3 == null ? 0 : integer3;
			   switch(integer3) {
			    case R.drawable.choice_true:
			    	tomorrowMorningIB.setImageResource(R.drawable.choice_false);
			    	tomorrowMorningIB.setTag(R.drawable.choice_false);
			     break;
			    case R.drawable.choice_false:
			    default:
			    	tomorrowMorningIB.setImageResource(R.drawable.choice_true);
			    	tomorrowMorningIB.setTag(R.drawable.choice_true);
			     break;
			}
			break;
		case R.id.id_tomorrow_afternoon:
			ImageButton ib4=(ImageButton)tomorrowAfternoonIB;
			assert(R.id.id_today_morning==tomorrowAfternoonIB.getId());
			Integer integer4 = (Integer) tomorrowAfternoonIB.getTag();
			   integer4 = integer4 == null ? 0 : integer4;
			   switch(integer4) {
			    case R.drawable.choice_true:
			    	tomorrowAfternoonIB.setImageResource(R.drawable.choice_false);
			    	tomorrowAfternoonIB.setTag(R.drawable.choice_false);
			     break;
			    case R.drawable.choice_false:
			    default:
			    	tomorrowAfternoonIB.setImageResource(R.drawable.choice_true);
			    	tomorrowAfternoonIB.setTag(R.drawable.choice_true);
			     break;
			}
			break;
		case R.id.id_tomorrow_evening:
			ImageButton ib5=(ImageButton)tomorrowEveningIB;
			assert(R.id.id_today_morning==tomorrowEveningIB.getId());
			Integer integer5 = (Integer) tomorrowEveningIB.getTag();
			   integer5 = integer5 == null ? 0 : integer5;
			   switch(integer5) {
			    case R.drawable.choice_true:
			    	tomorrowEveningIB.setImageResource(R.drawable.choice_false);
			    	tomorrowEveningIB.setTag(R.drawable.choice_false);
			     break;
			    case R.drawable.choice_false:
			    default:
			    	tomorrowEveningIB.setImageResource(R.drawable.choice_true);
			    	tomorrowEveningIB.setTag(R.drawable.choice_true);
			     break;
			}
			break;
		case R.id.id_theDayAfterTomorrow_morning:
			ImageButton ib6=(ImageButton)theDayAfterTomorrowMorningIB;
			assert(R.id.id_today_morning==theDayAfterTomorrowMorningIB.getId());
			Integer integer6 = (Integer) theDayAfterTomorrowMorningIB.getTag();
			   integer6 = integer6 == null ? 0 : integer6;
			   switch(integer6) {
			    case R.drawable.choice_true:
			    	theDayAfterTomorrowMorningIB.setImageResource(R.drawable.choice_false);
			    	theDayAfterTomorrowMorningIB.setTag(R.drawable.choice_false);
			     break;
			    case R.drawable.choice_false:
			    default:
			    	theDayAfterTomorrowMorningIB.setImageResource(R.drawable.choice_true);
			    	theDayAfterTomorrowMorningIB.setTag(R.drawable.choice_true);
			     break;
			}
			break;
		case R.id.id_theDayAfterTomorrow_afternoon:
			ImageButton ib7=(ImageButton)theDayAfterTomorrowAfternoonIB;
			assert(R.id.id_today_morning==theDayAfterTomorrowAfternoonIB.getId());
			Integer integer7 = (Integer) theDayAfterTomorrowAfternoonIB.getTag();
			   integer7 = integer7 == null ? 0 : integer7;
			   switch(integer7) {
			    case R.drawable.choice_true:
			    	theDayAfterTomorrowAfternoonIB.setImageResource(R.drawable.choice_false);
			    	theDayAfterTomorrowAfternoonIB.setTag(R.drawable.choice_false);
			     break;
			    case R.drawable.choice_false:
			    default:
			    	theDayAfterTomorrowAfternoonIB.setImageResource(R.drawable.choice_true);
			    	theDayAfterTomorrowAfternoonIB.setTag(R.drawable.choice_true);
			     break;
			}
			break;
		case R.id.id_theDayAfterTomorrow_evening:
			ImageButton ib8=(ImageButton)theDayAfterTomorrowEveningIB;
			assert(R.id.id_today_morning==theDayAfterTomorrowEveningIB.getId());
			Integer integer8 = (Integer) theDayAfterTomorrowEveningIB.getTag();
			   integer8 = integer8 == null ? 0 : integer8;
			   switch(integer8) {
			    case R.drawable.choice_true:
			    	theDayAfterTomorrowEveningIB.setImageResource(R.drawable.choice_false);
			    	theDayAfterTomorrowEveningIB.setTag(R.drawable.choice_false);
			     break;
			    case R.drawable.choice_false:
			    default:
			    	theDayAfterTomorrowEveningIB.setImageResource(R.drawable.choice_true);
			    	theDayAfterTomorrowEveningIB.setTag(R.drawable.choice_true);
			     break;
			}
			break;
		case R.id.id_order_sure_but:
			Builder dialog = new AlertDialog.Builder(OrderLearn.this);  
            dialog.setTitle("温馨提示!您好:");  
            dialog.setMessage("请认真检查已经选择的时间，方便自己的同时，也方便别人！没问题就点确定吧！");  
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener(){  
                public void onClick(DialogInterface dialog, int which) {  
                    //好了我成功把Button3的图标掠夺过来  
                	orderSureButton.setImageDrawable(getResources().getDrawable(R.drawable.order_sure_but));  
                }  
            }).create();//创建按钮  
            dialog.show();
			break;
		default:
			break;
		}
	}

}
