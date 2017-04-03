package com.gonigoni.utill;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

public class LoopService extends Service {

    private AudioReader audioReader = new AudioReader();
    private int sampleRate = 8000;
    private int inputBlockSize = 256;
    private int sampleDecimate = 1;
    private int maxdashBell = 100;

    public LoopService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        doStart();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        doStop();
    }

    public void doStart()
    {
        audioReader.startReader(sampleRate, inputBlockSize * sampleDecimate, new AudioReader.Listener()
        {
            @Override
            public final void onReadComplete(int dB)
            {
                receiveDecibel(dB);
            }

            @Override
            public void onReadError(int error)
            {

            }
        });
    }

    private void receiveDecibel(final int dB)
    {
        if (Math.abs(dB) > maxdashBell) {
            // 문자 발송
            // 주석 처리
            //final Session session = Session.getInstanse();
            //session.getStartPOIItem().getPOIPoint().
            //sendSMS("119", "사용자가 위험해요. 주소 정보입니다." + session.getStartPOIItem().getPOIAddress());
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        final PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "알림 문자 메시지가 전송되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }


            }
        }, new IntentFilter(SENT));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    public void doStop()
    {
        audioReader.stopReader();
    }
}
