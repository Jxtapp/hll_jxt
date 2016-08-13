package com.hll.adapter;
/**
 * 聊天框中，每个item的内容adapter
 * @author heyi
 */
import java.util.ArrayList;
import java.util.List;

import com.hll.entity.ItemOfChatContentBean;
import com.hll.jxtapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemOfChatContentAdapter extends BaseAdapter{

	private Context context;
	private List<ItemOfChatContentBean> list;
	private LayoutInflater   inflater;
	
	
	public ItemOfChatContentAdapter(Context context, List<ItemOfChatContentBean> list) {
		super();
		this.context = context;
		this.list = list;
		inflater=LayoutInflater.from(context);
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
		
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_chat_content,null);
			viewHolder.nameOfPerson=(TextView) convertView.findViewById(R.id.id_name_of_person_in_chatroom);
			viewHolder.contentOfItem=(TextView) convertView.findViewById(R.id.id_content_of_item_in_chatroom);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		ItemOfChatContentBean itemOfChatConten=list.get(position);
		viewHolder.nameOfPerson.setText(itemOfChatConten.nameOfPersonInChatroom);
		viewHolder.contentOfItem.setText(itemOfChatConten.contentOfItemInChatroom);
		 return convertView;
	}

	
	class ViewHolder{
		public TextView nameOfPerson;
		public TextView contentOfItem;
	}
	
}
