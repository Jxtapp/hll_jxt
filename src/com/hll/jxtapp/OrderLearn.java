package com.hll.jxtapp;

/**
 * 预约学车控制Activity
 * @author heyi
 * 2016/6/21
 */
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
		//titleTv.setText("预约学车");
		userName="李先生(15071285589)";
		schoolName="交安驾校";
		orderLearnMsgTv.setText("     尊敬的"+userName+",下面给您带来"+schoolName+"未来三天预约驾校车详情。");
		//选择训练条件和内容
		orderItemChoice();
		initEvent();
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
		
		orderPlaceList.add("天马湖");
		orderPlaceList.add("东九楼");
		orderPlaceList.add("赛马场");
		orderPlaceList.add("杨家湾");
		
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
