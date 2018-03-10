package limited.it.planet.incomingcallrecordapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import limited.it.planet.incomingcallrecordapp.R;
import limited.it.planet.incomingcallrecordapp.constant.Constants;
import limited.it.planet.incomingcallrecordapp.util.SendMobNumberToServer;
import limited.it.planet.incomingcallrecordapp.util.SendSMSSendToServer;

import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.getBoleanValueSharedPreferences;
import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.getValueFromSharedPreferences;
import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.saveBoleanValueSharedPreferences;
import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.saveToSharedPreferences;


public class SettingsFragment extends AppFragment {

    static EditText edtHTTPURLCall ,edtHTTPURLSms ,editTextParamMobile,editTextParamSMS;
    CheckBox checkBoxMobile,checkBoxSMS;
    TextView txvHelpCall ;

    static String sendMobNumberAPI = "";
    static String sendSmsAPI = "";

    public static String httpURLNumerfromEDT = "";
    public static String httpURLSMSfromEDT = "";
    public static String paramMobile = "";
    public static String paramSMS = "";

    public static String mobile = "";
    static SendMobNumberToServer sendMobNumberToServer;
    static SendSMSSendToServer sendSMSSendToServer;
   static boolean chageHTTPByUser;
   static boolean chageHTTPSMSByUser;
    static boolean chageMobileByUser;
    static boolean chageSMSByUser;
   static boolean isCheckMobileNumber;
   static boolean isCheckSMS ;


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendMobNumberToServer = new SendMobNumberToServer(getActivity());
        sendSMSSendToServer= new SendSMSSendToServer(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        edtHTTPURLCall = (EditText)rootView.findViewById(R.id.edt_http_url_call);
        edtHTTPURLSms = (EditText)rootView.findViewById(R.id.edt_http_url_sms);
        editTextParamMobile = (EditText)rootView.findViewById(R.id.edt_param_call);
        editTextParamSMS = (EditText)rootView.findViewById(R.id.edt_param_sms);
        checkBoxMobile =(CheckBox)rootView.findViewById(R.id.checkbox_send_incoming_number);
        checkBoxSMS = (CheckBox)rootView.findViewById(R.id.checkbox_send_sms);

        //edtShowNumber = (EditText)rootView.findViewById(R.id.edt_show_number);
        txvHelpCall = (TextView)rootView.findViewById(R.id.txv_help_call);


        sendMobNumberAPI = Constants.baseAPI;
        sendSmsAPI = Constants.baseAPI;
        //saveToSharedPreferences("mob_number_api",sendMobNumberAPI,getActivity());
        txvHelpCall.setText(sendMobNumberAPI );


        editTextParamMobile.setText("mobile");
        editTextParamSMS.setText("sms");


        checkBoxMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxMobile.isChecked()){
                    saveBoleanValueSharedPreferences("is_mobile",true,getActivity());

                }else {
                    saveBoleanValueSharedPreferences("is_mobile",false,getActivity());
                }
            }
        });
        checkBoxSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxSMS.isChecked()){
                    saveBoleanValueSharedPreferences("is_sms",true,getActivity());
                }else {
                    saveBoleanValueSharedPreferences("is_sms",false,getActivity());
                }
            }
        });

        edtHTTPURLCall.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               // if(chageHTTPByUser){
                httpURLNumerfromEDT = edtHTTPURLCall.getText().toString();
                    // httpURLfromEDT = sendMobNumberAPI;

                    saveToSharedPreferences("mob_number_api",httpURLNumerfromEDT,getActivity());
                    //edtShowNumber.setText(mobNumber);

                    //sendMobNumberToServer.mobileNumberSendToServer(mobNumber);

                chageHTTPByUser = true;


            }
        });

        edtHTTPURLSms.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // if(chageHTTPByUser){
                httpURLSMSfromEDT = edtHTTPURLSms.getText().toString();
                // httpURLfromEDT = sendMobNumberAPI;

                saveToSharedPreferences("sms_api",httpURLSMSfromEDT,getActivity());
                //edtShowNumber.setText(mobNumber);

                //sendMobNumberToServer.mobileNumberSendToServer(mobNumber);

                chageHTTPSMSByUser = true;


            }
        });

        editTextParamMobile.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                paramMobile = editTextParamMobile.getText().toString();

                saveToSharedPreferences("mobile_param",paramMobile,getActivity());

                chageMobileByUser = true;


            }
        });
        editTextParamSMS.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                paramSMS = editTextParamSMS.getText().toString();

                saveToSharedPreferences("sms_param",paramSMS,getActivity());

                chageSMSByUser = true;


            }
        });
        return rootView;
    }



    public static void inComingNumber(String number) {
        mobile = number;
        if(number!=null && !number.isEmpty() && sendMobNumberToServer!=null){
            if(isCheckMobileNumber){
                sendMobNumberToServer.mobileNumberSendToServer(number);
            }

          //  sendMobNumberToServer.mobileNumberSendToServer(number);
        }


        if(chageHTTPByUser){
            edtHTTPURLCall.setText(httpURLNumerfromEDT);
        }
    }

    public static void inComingSMS(String sms,String number) {

        if(sms!=null && !number.isEmpty() && sendMobNumberToServer!=null){
            if(isCheckSMS){
                sendSMSSendToServer.smsSendToServer(sms,number);
            }

            //  sendMobNumberToServer.mobileNumberSendToServer(number);
        }


//        if(chageHTTPSMSByUser){
//            edtHTTPURLSms.setText(httpURLSMSfromEDT);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isCheckMobileNumber =getBoleanValueSharedPreferences("is_mobile",getActivity());
         isCheckSMS = getBoleanValueSharedPreferences("is_sms",getActivity());

        if(isCheckMobileNumber){
            checkBoxMobile.setChecked(isCheckMobileNumber);
        }

        if(isCheckSMS){
            checkBoxSMS.setChecked(isCheckSMS);
        }

//        String saveHttpNumberAPI = getValueFromSharedPreferences("mob_number_api",getActivity());
//        if(saveHttpNumberAPI!=null && !saveHttpNumberAPI.isEmpty()){
//            edtHTTPURLCall.setText(saveHttpNumberAPI);
//        }
//
//
//        String saveHttpSmsAPI = getValueFromSharedPreferences("sms_api",getActivity());
//        if(saveHttpSmsAPI!=null && !saveHttpSmsAPI.isEmpty()){
//            edtHTTPURLSms.setText(saveHttpSmsAPI );
//        }


    }

}
