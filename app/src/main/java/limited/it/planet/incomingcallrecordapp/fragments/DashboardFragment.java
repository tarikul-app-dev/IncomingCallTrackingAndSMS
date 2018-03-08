package limited.it.planet.incomingcallrecordapp.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import limited.it.planet.incomingcallrecordapp.R;
import limited.it.planet.incomingcallrecordapp.constant.Constants;
import limited.it.planet.incomingcallrecordapp.util.MyPhoneReceiver;
import limited.it.planet.incomingcallrecordapp.util.SendMobNumberToServer;

import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.getBoleanValueSharedPreferences;
import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.getValueFromSharedPreferences;
import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.saveBoleanValueSharedPreferences;
import static limited.it.planet.incomingcallrecordapp.util.SharedPreferenceSaveAndGet.saveToSharedPreferences;

public class DashboardFragment extends AppFragment {

    Switch swAutoCallEnd,swAppONOff;

  //  boolean chageHTTPByUser;


    public DashboardFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        swAutoCallEnd = (Switch)rootView.findViewById(R.id.switch_automatic_call_record);
        swAppONOff = (Switch)rootView.findViewById(R.id.switch_app_on_off) ;

       // swAutoCallEnd.setChecked(false);

        swAutoCallEnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    swAutoCallEnd.setChecked(true);
                    saveBoleanValueSharedPreferences("auto_call_end_yes",true,getActivity());
                    saveBoleanValueSharedPreferences("auto_call_end_no",false,getActivity());
                } else {
                    // The toggle is disabled
                    saveBoleanValueSharedPreferences("auto_call_end_no",true,getActivity());
                    saveBoleanValueSharedPreferences("auto_call_end_yes",false,getActivity());
                    swAutoCallEnd.setChecked(false);

                }
            }
        });
        swAppONOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    saveBoleanValueSharedPreferences("app_on_yes",true,getActivity());
                    saveBoleanValueSharedPreferences("app_on_no",false,getActivity());
                    ComponentName component = new ComponentName(getActivity(), MyPhoneReceiver.class);
                    PackageManager pm = getActivity().getPackageManager();
                    pm.setComponentEnabledSetting(
                            component,
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);
                } else {
                    saveBoleanValueSharedPreferences("app_on_yes",false,getActivity());
                    saveBoleanValueSharedPreferences("app_on_no",true,getActivity());
                    ComponentName component = new ComponentName(getActivity(), MyPhoneReceiver.class);
                    PackageManager pm = getActivity().getPackageManager();
                    pm.setComponentEnabledSetting(
                            component,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
                }

            }
        });



        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();

        boolean checkYes =getBoleanValueSharedPreferences("auto_call_end_yes",getActivity());
       // boolean checkNo = getBoleanValueSharedPreferences("auto_call_end_no",getActivity());

            if(checkYes){
                swAutoCallEnd.setChecked(checkYes);
            }else {
                swAutoCallEnd.setChecked(false);
            }

    boolean checkAppOn = getBoleanValueSharedPreferences("app_on_yes",getActivity());
        if(checkAppOn){
            swAppONOff.setChecked(checkAppOn);
        }else {
            swAppONOff.setChecked(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        saveBoleanValueSharedPreferences("auto_call_end_yes",false,getActivity());
//        saveBoleanValueSharedPreferences("auto_call_end_no",true,getActivity());
//        Toast.makeText(getActivity(), "Call onStop", Toast.LENGTH_SHORT).show();

        Toast.makeText(getActivity(), "Call onDestroy", Toast.LENGTH_SHORT).show();
    }


}
