package limited.it.planet.incomingcallrecordapp.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

//import com.vit.db.DataHelper;
//
//import android.content.BroadcastReceiver;
//
//import android.content.Context;
//
//import android.content.Intent;
//
//import android.graphics.Path;

import android.media.MediaRecorder;

import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Environment;

import android.telephony.PhoneStateListener;

import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

import android.text.format.Time;

import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import org.json.JSONException;
import org.json.JSONObject;

import limited.it.planet.incomingcallrecordapp.constant.Constants;
import limited.it.planet.incomingcallrecordapp.database.DataHelper;
import limited.it.planet.incomingcallrecordapp.fragments.DashboardFragment;
import limited.it.planet.incomingcallrecordapp.fragments.SettingsFragment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.getBoleanValueSharedPreferences;

/**
 * Created by Tarikul on 2/25/2018.
 */

public class MyPhoneReceiver extends BroadcastReceiver {
    MediaRecorder recorder;

    TelephonyManager telManager;

    boolean recordStarted;

    private Context ctx;

    static boolean status = false;
    Time today;
    String phoneNumber;

    String selected_song_name;
    String voiceFilePath = "";
    String searchByMobNumber = "";

    private Dialog loadingDialog;
    private ITelephony telephonyService;
    // Get the object of SmsManager
   // final SmsManager sms = SmsManager.getDefault();
    public static final String SMS_BUNDLE = "pdus";
    SendMobNumberToServer sendMobNumberToServer;


    @Override
    public void onReceive(Context context, Intent intent) {
        ctx = context;

        sendMobNumberToServer = new SendMobNumberToServer(ctx);
        boolean checkAutoCallEndYes = getBoleanValueSharedPreferences("auto_call_end_yes",ctx);
        boolean isAppOn = getBoleanValueSharedPreferences("app_on_yes",ctx);
        Toast.makeText(ctx, String.valueOf(isAppOn), Toast.LENGTH_SHORT).show();


        if(checkAutoCallEndYes && isAppOn){
            TelephonyManager telephony = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Class c = Class.forName(telephony.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                telephonyService = (ITelephony) m.invoke(telephony);
                //telephonyService.silenceRinger();

                telephonyService.endCall();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        String action = intent.getAction();
//
//        Log.i("action>>>>>>>>>>>>>>>>>", "" + action);
//
//        today = new Time(Time.getCurrentTimezone());
//
//        today.setToNow();

//        if (status == false) {
//
//            try {
//
//                recorder = new MediaRecorder();
//
//                recorder.reset();
//
//                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//
//                recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
//
//                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
//
//
//                String date = today.monthDay + "_" + (today.month + 1) + "_"
//
//                        + today.year;
//
//
//                String time = today.format("%k_%M_%S");
//
//
//                File file = createDirIfNotExists(date + "_" + time);
//
//                voiceFilePath = file.getAbsolutePath();
//
//                recorder.setOutputFile(voiceFilePath);
//
//                recorder.prepare();
//
//                recorder.start();
//
//                recordStarted = true;
//
//                status = true;
//
//
//            } catch (Exception ex) {
//
//
//                ex.printStackTrace();
//
//
//            }


            Bundle extras = intent.getExtras();


            if (extras != null) {

                // OFFHOOK

                String state = extras.getString(TelephonyManager.EXTRA_STATE);

                Log.w("DEBUG", "aa" + state);

                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

                    phoneNumber = extras

                            .getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

                   // incoming(phoneNumber);
                    sendMobNumberToServer.mobileNumberSendToServer(phoneNumber);
                    SettingsFragment.inComing(phoneNumber);

                    Toast.makeText(ctx, phoneNumber, Toast.LENGTH_SHORT).show();

                    //incomingcallrecord(action, context);

                } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {

                    phoneNumber = intent

                            .getStringExtra(Intent.EXTRA_PHONE_NUMBER);

                    Log.i("number >>>>>>>>>>>>>>", "" + this.getResultData());


                  //  incomingcallrecord(action, context);


                }else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                    Toast.makeText(ctx, "Called When App Close", Toast.LENGTH_SHORT).show();
                    phoneNumber = intent

                            .getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    sendMobNumberToServer.mobileNumberSendToServer(phoneNumber);
                    Toast.makeText(ctx, phoneNumber, Toast.LENGTH_SHORT).show();

                }


            }


//        } else {
//
//
//            status = false;
//
//        }


    }



    private void incoming(String number) {



        DataHelper db1 = new DataHelper(ctx);

        db1.open();



        String date = today.monthDay + "_" + (today.month + 1) + "_"

                + today.year;

        String time = today.format("%k_%M_%S");

       // db1.insertUserProfile(number, date + "_" + time,getVoiceFilePath());



        db1.close();





    }



    private void incomingcallrecord(String action, Context context) {

        // TODO Auto-generated method stub

        if (action.equals("android.intent.action.PHONE_STATE")) {

            telManager = (TelephonyManager) context

                    .getSystemService(Context.TELEPHONY_SERVICE);

            telManager.listen(phoneListener,

                    PhoneStateListener.LISTEN_CALL_STATE);

        }



    }



    private final PhoneStateListener phoneListener = new PhoneStateListener() {



        @Override

        public void onCallStateChanged(int state, String incomingNumber) {

            Log.d("calling number", "calling number" + incomingNumber);

            try {

                switch (state) {



                    case TelephonyManager.CALL_STATE_RINGING: {



                        Log.e("CALL_STATE_RINGING", "CALL_STATE_RINGING");



                        break;

                    }

                    case TelephonyManager.CALL_STATE_OFFHOOK: {



                        Log.e("CALL_STATE_OFFHOOK", "CALL_STATE_OFFHOOK");


                        break;

                    }

                    case TelephonyManager.CALL_STATE_IDLE: {



                        Log.e("CALL_STATE_IDLE", "CALL_STATE_IDLE");



                        if (recordStarted) {



                            recorder.stop();

                            recorder.reset();

                            recorder.release();

                            recorder = null;

                            recordStarted = false;

                        }



                        break;

                    }

                    default: {

                    }

                }

            } catch (Exception ex) {

            }



        }



//        private void encriptCurrentRecordedFile() {
//
//
//
//            SimpleCrypto simpleCrypto = new SimpleCrypto();
//
//
//
//            try {
//
//
//
//                incrept = simpleCrypto.encrypt("abc", getAudioFileFromSdcard());
//
//
//
//                FileOutputStream fos = new FileOutputStream(new File(
//
//                        "/sdcard/PhoneCallRecording/" + selected_song_name
//
//                                + ".3GPP"));
//
//                fos.write(incrept);
//
//                fos.close();
//
//
//
//            } catch (Exception e) {
//
//
//
//                e.printStackTrace();
//
//
//
//            }
//
//
//
//        }



//        private byte[] getAudioFileFromSdcard() throws FileNotFoundException {
//
//
//
//            byte[] inarry = null;
//
//
//
//            try {
//
//
//
//                File sdcard = new File(
//
//                        Environment.getExternalStorageDirectory()+ "/PhoneCallRecording");
//
//
//
//                File file = new File(sdcard, selected_song_name + ".3GPP");
//
//
//
//                FileInputStream fileInputStream = null;
//
//
//
//                byte[] bFile = new byte[(int) file.length()];
//
//
//
//                // convert file into array of bytes
//
//                fileInputStream = new FileInputStream(file);
//
//                fileInputStream.read(bFile);
//
//                fileInputStream.close();
//
//                inarry = bFile;
//
//
//
//            } catch (IOException e) {
//
//                // TODO Auto-generated catch block
//
//                e.printStackTrace();
//
//            }
//
//
//
//            return inarry;
//
//        }

    };



    public File createDirIfNotExists(String path) {



        selected_song_name = path;



        File folder = new File(Environment.getExternalStorageDirectory()

                + "/PhoneCallRecording");

        if (!folder.exists()) {



            if (!folder.mkdirs()) {



                Log.e("TravellerLog :: ", "folder is created");



            }

        }



        File file = new File(folder, path + ".3GPP");

        try {

            if (!file.exists()) {



                if (file.createNewFile()) {



                    Log.e("TravellerLog :: ", "file is created");

                }

            }



        } catch (IOException e) {



            // TODO Auto-generated catch block

            e.printStackTrace();



        }

        return file;



    }

    public String getVoiceFilePath(){
        return  voiceFilePath;
    }

}
