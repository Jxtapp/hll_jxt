package com.hll.basic;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
/**
 * 浠庣綉缁滃姞杞藉浘鐗�
 * @author liaoyun
 * 2016-6-3
 */
public class NetworkDownImage {
	//鍥剧墖璺緞
	private String imagePath;
	
	public NetworkDownImage(String path){
		this.imagePath=path;
	}
	
	@SuppressLint("HandlerLeak")
	public void loadImage(final ImageCallBack callBack){
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Drawable drawable = (Drawable) msg.obj;
				callBack.getDrawable(drawable);
			}
		};
		new Thread(){
			public void run() {
				try {
					URL url = new URL(imagePath);
					InputStream is = url.openStream();
					Drawable drawable = Drawable.createFromStream(is,"");
					//????
					Message message = Message.obtain();
					message.obj = drawable;
					handler.sendMessage(message);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
}
