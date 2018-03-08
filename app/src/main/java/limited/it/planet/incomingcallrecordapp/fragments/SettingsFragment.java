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
import android.widget.EditText;
import android.widget.TextView;

import limited.it.planet.incomingcallrecordapp.R;
import limited.it.planet.incomingcallrecordapp.constant.Constants;
import limited.it.planet.incomingcallrecordapp.util.SendMobNumberToServer;

import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.getValueFromSharedPreferences;
import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.saveToSharedPreferences;


public class SettingsFragment extends AppFragment {

    static EditText edtHTTPURLCall ,edtHTTPURLSms ;
    TextView txvHelpCall,txvHelpSms ;

    static String sendMobNumberAPI = "";
    static String sendSmsAPI = "";

    public static String httpURLfromEDT = "";
    public static String mobNumber = "";
    static SendMobNumberToServer sendMobNumberToServer;
    boolean chageHTTPByUser;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendMobNumberToServer = new SendMobNumberToServer(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        edtHTTPURLCall = (EditText)rootView.findViewById(R.id.edt_http_url_call);
        edtHTTPURLSms = (EditText)rootView.findViewById(R.id.edt_http_url_sms);
        //edtShowNumber = (EditText)rootView.findViewById(R.id.edt_show_number);
        txvHelpCall = (TextView)rootView.findViewById(R.id.txv_help_call);
        txvHelpSms = (TextView)rootView.findViewById(R.id.txv_help_sms);

        sendMobNumberAPI = Constants.sendMobNumberAPI;
        sendSmsAPI = Constants.sendSMSAPI;
        //saveToSharedPreferences("mob_number_api",sendMobNumberAPI,getActivity());
        txvHelpCall.setText(sendMobNumberAPI);
       // String defaultAPI = getValueFromSharedPreferences("mob_number_api",getActivity());
        edtHTTPURLCall.setText(sendMobNumberAPI );


        edtHTTPURLCall.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(chageHTTPByUser){
                    httpURLfromEDT = edtHTTPURLCall.getText().toString();
                    // httpURLfromEDT = sendMobNumberAPI;
                    saveToSharedPreferences("mob_number_api",httpURLfromEDT,getActivity());
                    //edtShowNumber.setText(mobNumber);

                    sendMobNumberToServer.mobileNumberSendToServer(mobNumber);

                }else {
                    chageHTTPByUser = true;
                    saveToSharedPreferences("mob_number_api",sendMobNumberAPI,getActivity());
                }

            }
        });
        return rootView;
    }



    public static void inComing(String number) {
        mobNumber = number;
        //edtShowNumber.setText(number);
        edtHTTPURLCall.setText(sendMobNumberAPI);
        sendMobNumberToServer.mobileNumberSendToServer(number);
    }


}
