package com.jimmy.colockservice;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	Boolean flag=true;
	Notification notification;
	Intent notificationIntent;
	PendingIntent pendingIntent;
	DateFormat df;
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("MyService", "onBind executed");
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("MyService", "onStartCommand executed");
		notification= new Notification(R.drawable.clock,
					"开启通知栏时钟模式", System.currentTimeMillis());
		notificationIntent= new Intent(this, MainActivity.class);
		pendingIntent= PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		df = new SimpleDateFormat("HH:mm:ss");
		new Thread(new Runnable() {  
	        @Override  
	        public void run() {  
	    		
	    		while(flag){
	    		String time =df.format(new Date());
	    		notification.setLatestEventInfo(MyService.this, "", time,
	    				pendingIntent);
	    		startForeground(1, notification);
	    		try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    		}
	        }  
	    }).start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		flag=false;
		Log.d("MyService", "onDestroy executed");
	}

}
