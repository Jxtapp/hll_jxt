package com.hll.adapter;

/**
 * @author heyi
 * 2016/6/1
 */
import java.util.List;

import com.hll.entity.SecPageItemBean;
import com.hll.jxtapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SecPageAdapter extends BaseAdapter {

	private Context context;
	private List<SecPageItemBean> list;
	private LayoutInflater inflater;
	
	
	public SecPageAdapter(Context context,List<SecPageItemBean> listBean) {
		this.context=context;
		list = listBean;
		inflater = LayoutInflater.from(context);
	}


	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public SecPageItemBean getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void add(SecPageItemBean secPageItemBean) {
		list.add(secPageItemBean);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		ViewHolder holder = null;
      if (convertView == null) {
          holder = new ViewHolder();
          convertView = inflater.inflate(R.layout.item_coach_self_choice, null);
          holder.coachSelfImg = (ImageView) convertView.findViewById(R.id.id_img_coach_self);
          holder.coachPrice = (TextView) convertView.findViewById(R.id.id_tv_coach_price);
          holder.teachType = (TextView) convertView.findViewById(R.id.id_tv_coach_teach_type);
          holder.orderTimes = (TextView) convertView.findViewById(R.id.id_tv_coach_order_times);
          holder.moreInfo = (TextView) convertView.findViewById(R.id.id_coach_more_info);
          convertView.setTag(holder);
      } else {
          holder = (ViewHolder) convertView.getTag();
      }
      SecPageItemBean bean =list.get(position);
      holder.coachSelfImg.setImageResource(bean.coachSelfImg);
      holder.coachPrice.setText("预约价格：       "+String.valueOf(bean.coachPrice)); 
      holder.teachType.setText("学车类型：        "+bean.teachType);
      holder.orderTimes.setText("预约次数：            "+String.valueOf(bean.orderTimes));
      holder.moreInfo.setText(bean.moreInfo);
      return convertView;
	}
	 class ViewHolder{
		 public ImageView coachSelfImg;
		 public TextView  coachPrice;
		 public TextView  teachType;
		 public TextView  orderTimes;
		 public TextView  moreInfo;
	    }

}
