package com.hll.adapter;

import java.util.List;

import com.hll.entity.RecommendSchoolInfoO;
import com.hll.jxtapp.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;

/**
 * recommend driver school list adapter
 * @author liaoyun
 * 2016-6-1
 */

public class RecommondSchoolListAdapter extends BaseAdapter{
	//要显示的数据
	private List<RecommendSchoolInfoO> driverSchoolInfoList;
	//可以将文件转化为 view
	private LayoutInflater inflater;
	private ViewHolder viewHolder;
	public List<RecommendSchoolInfoO> getDriverSchoolInfoList() {
		return driverSchoolInfoList;
	}

	public void setDriverSchoolInfoList(
			List<RecommendSchoolInfoO> driverSchoolInfoList) {
		this.driverSchoolInfoList = driverSchoolInfoList;
	}

	public RecommondSchoolListAdapter(Context context,List<RecommendSchoolInfoO> driverSchoolInfoList){
		this.driverSchoolInfoList = driverSchoolInfoList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return driverSchoolInfoList.size();
	}

	@Override
	public Object getItem(int index) {
		return driverSchoolInfoList.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}
	
	@Override
	public View getView(int position, View converView, ViewGroup parent) {
		if(converView==null){//view 没有被实例化过,利用 converView 的缓存作用
			viewHolder = new ViewHolder();
			converView = inflater.inflate(R.layout.driver_school_list, null);
			viewHolder.imageView = (ImageView) converView.findViewById(R.id.item_img);
			viewHolder.price = (TextView) converView.findViewById(R.id.item_price);
			viewHolder.address = (TextView) converView.findViewById(R.id.item_address);
			viewHolder.schoolName = (TextView) converView.findViewById(R.id.item_name);
			converView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) converView.getTag();
		}
		RecommendSchoolInfoO vo=driverSchoolInfoList.get(position);
		viewHolder.price.setText("价格： "+String.valueOf(vo.getItemPrice()));
		viewHolder.address.setText("地点： "+vo.getItemAddress());
		viewHolder.schoolName.setText(vo.getSchoolname());
		viewHolder.imageView.setImageResource(R.drawable.on_loading1);
		//添加标记，防止图片错位
		viewHolder.imageView.setTag(vo.getItemImg());
		return converView;
	}
	
	private class ViewHolder{
		public TextView schoolName;
		public ImageView imageView;
		public TextView price;
		public TextView address;
	}
}
