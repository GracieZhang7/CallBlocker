package com.example.administrator.icall;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

public class InterceptService extends Service
{
    public static final int ALART_MANAGER_REQUEST_CODE = 301;
    public static final String EXTRA_CLICKED_NOTIFY = "click notification";
    public static final int NOTIFICATION_REQUEST_CODE = 300;
    public static final String UNKNOW_NUMBER = "Unknow Number";
    private ITelephony iTelephony;
    private AudioManager mAudioManager;
    private IncomingCallReceiver mReceiver;
    private String numberName = null;
    private TelephonyManager localTelephonyManager;


    public IBinder onBind(Intent paramIntent)
    {
        return null;
    }

    public void onCreate() {
        Log.i("xxxxxx", "start services" );
        super.onCreate();
        this.mReceiver = new IncomingCallReceiver();
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.intent.action.PHONE_STATE");
        //localIntentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");

        registerReceiver(this.mReceiver, localIntentFilter);
    }
    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        localTelephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        try
        {
            Method localMethod = TelephonyManager.class.getDeclaredMethod("getITelephony", (Class[])null);
            localMethod.setAccessible(true);
            this.iTelephony = ((ITelephony)localMethod.invoke(localTelephonyManager, (Object[])null));
        }
        catch (Exception localException1)
        {
            localException1.printStackTrace();
        }
        return START_STICKY;
    }
    public void onDestroy()
    {
        unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }
    private void endCall() {
        try {
            if (iTelephony != null){
                iTelephony.endCall();
                Log.i("xxxxxx","block" );
            }
            return;
        }
        catch (RemoteException localRemoteException) {
            localRemoteException.printStackTrace();
        }
    }
    private class IncomingCallReceiver extends BroadcastReceiver
    {
        private IncomingCallReceiver()
        {
        }

        public void onReceive(Context paramContext, Intent paramIntent)
        {
            String str1 = paramIntent.getStringExtra("state");
            String str2 = paramIntent.getStringExtra("incoming_number");
            Log.i("xxxxxx","state:"+str1+",no:"+ str2 );

            switch(localTelephonyManager.getCallState()){
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.i("xxxxxx","ring" );
                    if(Bean.isBlock(InterceptService.this,str2))
                        endCall();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }


        }


    }


}