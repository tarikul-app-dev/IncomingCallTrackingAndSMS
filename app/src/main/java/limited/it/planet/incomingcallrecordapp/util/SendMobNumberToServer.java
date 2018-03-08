package limited.it.planet.incomingcallrecordapp.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import limited.it.planet.incomingcallrecordapp.constant.Constants;
import limited.it.planet.incomingcallrecordapp.database.DataHelper;
import limited.it.planet.incomingcallrecordapp.fragments.DashboardFragment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.getValueFromSharedPreferences;

/**
 * Created by Tarikul on 3/1/2018.
 */

public class SendMobNumberToServer {
    String sendMobNumberAPI = "";
    String retrieveAPIFromMemory = "";
    Context mContext;
      public  SendMobNumberToServer(Context context){
            this.mContext = context;
          retrieveAPIFromMemory = getValueFromSharedPreferences("mob_number_api",mContext);
        }

        public void mobileNumberSendToServer(String number){
            //get Number
            sendMobNumberAPI = retrieveAPIFromMemory + number;

            if(sendMobNumberAPI!=null && !sendMobNumberAPI.isEmpty()){
                GetSearchDataTask getSearchDataTask = new GetSearchDataTask();
                getSearchDataTask.execute();
            }


        }

    public class GetSearchDataTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // loadingDialog = ProgressDialog.show(ctx, "Please wait", "Loading...");
        }


        @Override
        protected String doInBackground(String... params) {
            String responseBodyText = null;

            OkHttpClient client = new OkHttpClient();

            try {

                Request request = new Request.Builder()
                        .url(sendMobNumberAPI)

                        .build();


                Response response = null;

                response = client.newCall(request).execute();
                if (!response.isSuccessful()) {

                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext,"Error",Toast.LENGTH_SHORT).show();

                        }
                    });
                    throw new IOException("Okhttp Error: " + response);

                } else {
                    responseBodyText = response.body().string();

                   // JSONObject jobject = new JSONObject(responseBodyText);

                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext,"Send To Server Successfully",Toast.LENGTH_SHORT).show();

                        }
                    });

                }



            } catch (Exception e) {
                e.printStackTrace();
            }


            return responseBodyText;


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //loadingDialog.dismiss();


            // Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();

        }
    }
}
