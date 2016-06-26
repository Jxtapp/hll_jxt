package com.hll.adapter;

import java.util.List;

import com.hll.entity.QueueListItemBean;
import com.hll.jxtapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QueueGroupListAdapter extends BaseAdapter{

	private Context context;
	private List<QueueListItemBean> list;
	private LayoutInflater inflater;
	
	
	
	public QueueGroupListAdapter(Context context, List<QueueListItemBean> list) {
		super();
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
	      if (convertView == null) {
	          holder = new ViewHolder();
	          convertView = inflater.inflate(R.layout.queue_group_list_item, null);
	          holder.queueListSureOne = (ImageView) convertView.findViewById(R.id.id_queue_list_sure_one);
	          holder.queueListSureTow = (ImageView) convertView.findViewById(R.id.id_queue_list_sure_tow);
	          holder.queueListNum = (TextView) convertView.findViewById(R.id.id_queue_list_number);
	          holder.queueListName = (TextView) convertView.findViewById(R.id.id_queue_list_name);
	          convertView.setTag(holder);
	      } else {
	          holder = (ViewHolder) convertView.getTag();
	      }
	      QueueListItemBean bean =list.get(position);
	      holder.queueListSureOne.setImageResource(bean.queueListSureOne);
	      holder.queueListSureTow.setImageResource(bean.queueListSureTow);
	      holder.queueListNum.setText(String.valueOf(bean.queueListNum)); 
	      holder.queueListName.setText(bean.queueListName);
	      return convertView;
		}
		 class ViewHolder{
			 public ImageView queueListSureOne;
			 public ImageView queueListSureTow;
			 public TextView  queueListNum;
			 public TextView  queueListName;
		    }
	
}
