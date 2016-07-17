package com.hll.basic;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.hll.util.SDcardCacheUtil;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
/**
 * download pic from the server
 * @author liaoyun
 * 2016-6-3
 */
public class NetworkDownImage {
	// url of the pic
	private String imagePath;
	
	public NetworkDownImage(String path){
		this.imagePath=path;
	}
	
	@SuppressLint("HandlerLeak")
	public void loadImage(final ImageCallBack callBack){
		
		int index = imagePath.lastIndexOf(File.separator)+1;
		final String bmpName = imagePath.substring(index,imagePath.length());
		
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Drawable drawable = (Drawable) msg.obj;
				callBack.getDrawable(drawable);
			}
		};
		
		//get pic from the sd card cache
		Drawable drawable = SDcardCacheUtil.getBmpFromSD(bmpName);
		if(drawable != null){
			Message msg = Message.obtain();
			msg.obj = drawable;
			handler.sendMessage(msg);
			return;
		}
		
		// if there is no cache in the sd card ,download pic from the server
		new Thread(){
			public void run() {
				try {
					URL url = new URL(imagePath);
					InputStream is = url.openStream();
					Drawable drawable = Drawable.createFromStream(is,"");
					Message message = Message.obtain();
					message.obj = drawable;
					handler.sendMessage(message);
					//save pic to sdcard
					BitmapDrawable bd = (BitmapDrawable) drawable;
					SDcardCacheUtil.saveBmpToSD(bd.getBitmap(), bmpName);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
}
