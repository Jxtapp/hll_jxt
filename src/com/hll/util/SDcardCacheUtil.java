package com.hll.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * 本地图片缓存，将网络图片保存在内存卡上，以节约资源。当要获取图片时，就从sdcard上的目录中去找，如果找到的话，使用该图片 并更新最后使用时间。如果
 * 找不到，则通过url去服务器下载，并缓存图片。
 * @author liaoyun
 * 2016-7-10
 */
public class SDcardCacheUtil {
	
	private static final String TAG                  = "Bmp Cache";
	private static final String BMP_DIR              = Environment.getExternalStorageDirectory()+"/hllCache";
	private static final int    MB                   = 1024 * 1024;
	private static final int    FREE_SD_SPACE_NEEDED = 10 * MB;     // 10MB
	private static final int    CACHE_SIZE           = 10 * MB;     // 10MB
	private static long         DIR_SIZE             = 0;
	//private static final String SUBFFIX              = "jpg";

	static{
		File dir = new File(BMP_DIR);
		if(dir.exists()){
			File[] files = dir.listFiles();
			if(files != null){
				for (int i = 0; i < files.length; i++) {
					DIR_SIZE += files[i].length();
				}
			}
		}
	}
	/**
	 * get cache bitmap from sd card
	 * liaoyun 2016-7-10
	 * @param bmpName   like 1123.jpg
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static BitmapDrawable getBmpFromSD(String bmpName){
//		if(bmpName.contains(File.separator)){//if the bmpName is a url
//			int index = bmpName.lastIndexOf(File.separator)+1;
//			bmpName = bmpName.substring(index,bmpName.length());
//		}
		Log.i(TAG,BMP_DIR+"/"+bmpName);
		String name = bmpName;//.substring(0,bmpName.indexOf(".")) + SUBFFIX;
		File file = new File(BMP_DIR,name);
		BitmapDrawable bm = null;
		try {
			InputStream is = new FileInputStream(file);
			bm = new BitmapDrawable(is);
			if(file.exists() && bm != null){
				Log.i(TAG,BMP_DIR+"/"+bmpName+"   finded");
				updateFileTime(file);
			}
		} catch (FileNotFoundException e) {
			Log.w(TAG, e);
		}
		return bm;
	}
	/**
	 * is there a sd card in phone
	 * liaoyun 2016-7-10
	 * @return
	 */
	public static boolean isSDExist(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * save pic to sd card 
	 * @param bm
	 * @param url  //the url of the pic 
	 */
	public static void saveBmpToSD(Bitmap bm, String bmpName){
		Log.i(TAG,"===============start to save bmp=====================");
		if(bm==null){
			Log.w(TAG,"tring to save null bitmap");
			return;
		}
		//判断 sd card 上的 空间
		if(FREE_SD_SPACE_NEEDED > freeSpaceOnsd()){
			removeChace();
			Log.w(TAG,"low free space on sd card,do not cache");
			return;
		}
		File fileDir = new File(BMP_DIR);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		String name = bmpName;//.substring(0,bmpName.indexOf(".")) + SUBFFIX;
		File file = new File(BMP_DIR,name);
		
		try {
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();
			DIR_SIZE += file.length();
			removeChace();
		} catch (IOException e) {
			Log.w(TAG,e);
		}
	}

	/**
	 * get the size of free space on the sd card 
	 * liaoyun 2016-7-10
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int freeSpaceOnsd() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFree = (stat.getAvailableBlocks() * stat.getBlockSize());
		return (int) sdFree;
	}
	
	/**
	 * update the time last modified;
	 * liaoyun 2016-7-10
	 * @param file
	 */
	private static void updateFileTime(File file){
		long newModifiedTime = System.currentTimeMillis();
		file.setLastModified(newModifiedTime);
	}

	/**
	 * 计算存储目录下的文件大小，当文件总大小大于规定的CACH_SIZE 或者 sdcard 剩余空间小于 FREE_SD_SPACE_NEEDED 的规定，那么
	 * 删除 40% 最近没有被使用的文件
	 * liaoyun 2016-7-10 
	 * @param dir
	 */
	public static void removeChace(){
		if(DIR_SIZE > CACHE_SIZE || FREE_SD_SPACE_NEEDED > freeSpaceOnsd()){
			Log.i(TAG,"DIR_SIZE="+DIR_SIZE+"   "+"CACHE_SIZE="+CACHE_SIZE+"  "+"FREE_SD_SPACE_NEEDED="+FREE_SD_SPACE_NEEDED);
			File dir = new File(BMP_DIR);
			File[] files = dir.listFiles();
			int removeFactor = (int) (0.4 * files.length +1);
			Arrays.sort(files, new FileLastModifSort());
			Log.i(TAG,"clear shome expired cache files");
			for (int i = 0; i < removeFactor; i++) {
				files[i].delete();
			}
		}
	}
	
	/**
	 * file order by last modified time
	 * @author liaoyun
	 * 2016-7-10
	 */
	private static class FileLastModifSort implements Comparator<File>{
		@Override
		public int compare(File arg0, File arg1) {
			if(arg0.lastModified() > arg1.lastModified()){
				return 1;
			}else if(arg0.lastModified() == arg1.lastModified()){
				return 0;
			}else{
				return -1;
			}
		}
	}
}
