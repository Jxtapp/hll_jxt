package com.hll.adapter;

import java.util.List;

import com.hll.entity.RecommendSchoolInfoO;
import com.hll.basic.ImageCallBack;
import com.hll.basic.NetworkDownImage;
import com.hll.jxtapp.R;
import com.hll.util.NetworkInfoUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
	private int lastItem;
	
	
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
			//viewHolder.num = (TextView) converView.findViewById(R.id.item_num);
			converView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) converView.getTag();
		}
		RecommendSchoolInfoO vo=driverSchoolInfoList.get(position);
		viewHolder.price.setText(String.valueOf(vo.getItemPrice()));
		viewHolder.address.setText(vo.getItemAddress());
		//从网上下载图片
		if(vo.getItemImg()!=null){
			NetworkDownImage downImage = new NetworkDownImage(NetworkInfoUtil.picUtl+"/"+vo.getItemImg());
			//接口回调，加载图片
			downImage.loadImage(new ImageCallBack(){
				@Override
				public void getDrawable(Drawable drawable) {
					viewHolder.imageView.setImageDrawable(drawable);
				}
			});
		}
		return converView;
	}
	
	private class ViewHolder{
		public ImageView imageView;
		public TextView price;
		public TextView address;
		public TextView num;
	}
	
}
