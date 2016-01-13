package com.example.broadcastreceiver;


import com.example.myapplication.myApplication;
import com.example.myinterface.duanxinglistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMS_receiver extends BroadcastReceiver {

	public duanxinglistener listener;
	public String yanzhengma;
	@Override
	public void onReceive(Context context, Intent intent) {
		String action=intent.getAction();
		SmsMessage[] messages = null;
		if(action.equals("android.provider.Telephony.SMS_RECEIVED"))
		{
			Bundle bundle=intent.getExtras();
			if(bundle!=null)
			{
				Object[] object=(Object[])bundle.get("pdus");
				messages=new SmsMessage[object.length];
				for(int i=0;i<object.length;i++)
				{
					messages[i]=SmsMessage.createFromPdu((byte[])object[i]);
				}
				SmsMessage message=messages[0];
				Log.i("message",message.getDisplayMessageBody());
				Toast.makeText(myApplication.GetContext(), message.getDisplayMessageBody(), Toast.LENGTH_SHORT).show();
				String a[]=message.getDisplayMessageBody().split("£º");
				yanzhengma=a[1];
				Toast.makeText(myApplication.GetContext(), yanzhengma, Toast.LENGTH_SHORT).show();
				listener.get_haoma(yanzhengma);
			}
		}

	}
	
	public void setlistener(duanxinglistener listener)
	{
		this.listener=listener;
	}
}
