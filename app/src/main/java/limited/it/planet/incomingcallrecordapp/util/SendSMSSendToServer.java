package limited.it.planet.incomingcallrecordapp.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import limited.it.planet.incomingcallrecordapp.constant.Constants;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.getValueFromSharedPreferences;

/**
 * Created by Tarikul on 3/10/2018.
 */

public class SendSMSSendToServer {
    public static final String RESPONSE_LOG = Constants.LOG_TAG_RESPONSE;
    String sendSMSAPI = "";

    Context mContext;
    public  SendSMSSendToServer(Context context){
        this.mContext = context;
        sendSMSAPI = getValueFromSharedPreferences("sms_api",mContext);
    }

    public void smsSendToServer(String sms,String number){

       // if(sendSMSAPI!=null && !sendSMSAPI.isEmpty()){
            SendSMSTask sendSMSTask = new SendSMSTask(sms,number);
            sendSMSTask.execute();
       // }


    }

    public class SendSMSTask extends AsyncTask<String, Integer, String> {
        String mSMSNumber;
        String mSms;

        public SendSMSTask (String sms,String smsNumber){
            mSms = sms;
            mSMSNumber = smsNumber;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();

            try {
                RequestBody requestBody = new FormBody.Builder()
                        .add("mob",mSMSNumber)
                        .add("text",mSms)
                        .build();


                Request request = new Request.Builder()
                        .url(Constants.baseAPI)
                        .post(requestBody)
                        .build();


                Response response = null;
                //client.setRetryOnConnectionFailure(true);
                response = client.newCall(request).execute();
                if (response.isSuccessful()){
                    final String result = response.body().string();
                    Log.d(RESPONSE_LOG,result);

//                    ((Activity)mContext).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }


    }

}
