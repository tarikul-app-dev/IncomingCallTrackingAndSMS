package limited.it.planet.incomingcallrecordapp.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import limited.it.planet.incomingcallrecordapp.fragments.SettingsFragment;

import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.getBoleanValueSharedPreferences;
import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.saveToSharedPreferences;

/**
 * Created by Tarikul on 3/6/2018.
 */

public class SMSReceiver extends BroadcastReceiver {

    SendSMSSendToServer sendSMSSendToServer;
    @Override
    public void onReceive(Context context, Intent intent) {
        sendSMSSendToServer = new SendSMSSendToServer(context);
        boolean isSMSOn = getBoleanValueSharedPreferences("is_sms",context);

        String getSMSNumber = "";
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String sms = "";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];


            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                sms += " :";
                sms += msgs[i].getMessageBody().toString();
                getSMSNumber = msgs[i].getOriginatingAddress();
               // str += "n";
            }
            //---display the new SMS message---
            Toast.makeText(context, getSMSNumber, Toast.LENGTH_SHORT).show();
            if(isSMSOn){
                sendSMSSendToServer.smsSendToServer(sms,getSMSNumber);
            }


           // SettingsFragment.inComingSMS(sms,getSMSNumber);
        }
        //Toast.makeText(context, "Working app while app close", Toast.LENGTH_SHORT).show();
    }
}
