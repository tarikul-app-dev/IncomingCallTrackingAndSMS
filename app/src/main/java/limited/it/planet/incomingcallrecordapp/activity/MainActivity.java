package limited.it.planet.incomingcallrecordapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import limited.it.planet.incomingcallrecordapp.R;
import limited.it.planet.incomingcallrecordapp.fragments.AppFragment;
import limited.it.planet.incomingcallrecordapp.fragments.DashboardFragment;
import limited.it.planet.incomingcallrecordapp.fragments.SettingsFragment;
import limited.it.planet.incomingcallrecordapp.util.ToolbarUI;

public class MainActivity extends AppCompatActivity implements ToolbarUI{
    BottomNavigationView bottomNavigationView;
    int currentItemIndex;
    Toolbar mToolbar;
    ActionBar actionBar;
    public static TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_main);
        headerText = (TextView)findViewById(R.id.header_text);

        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        setMainFragment(new DashboardFragment(), "dashboard", "dashboard");


        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigation);
        addBottomNavigationItems();
       // String manufacturer = android.os.Build.MANUFACTURER;

//        try {
//            Intent intent = new Intent();
//            String manufacturer = android.os.Build.MANUFACTURER;
//            if ("xiaomi".equalsIgnoreCase(manufacturer)) {
//                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
//            } else if ("oppo".equalsIgnoreCase(manufacturer)) {
//                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
//            } else if ("vivo".equalsIgnoreCase(manufacturer)) {
//                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
//            }else if ("huawei".equalsIgnoreCase(manufacturer)) {
//                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
//            }
//
//            List<ResolveInfo> list = this.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//            if  (list.size() > 0) {
//                this.startActivity(intent);
//            }
//        } catch (Exception e) {
//            e.getStackTrace();
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
            }

        }
    }


    public void addBottomNavigationItems(){

        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                ("Dashboard", ContextCompat.getColor(this, R.color.colorAccent), R.drawable.ic_dashboard);
        final BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                ("Settings", ContextCompat.getColor(this, R.color.colorActive), R.drawable.ic_settings);
//        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
//                ("Outgoing", ContextCompat.getColor(this, R.color.colorPrimary), R.drawable.ic_call_outgoing);
//        BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem
//                ("Favorite", ContextCompat.getColor(this, R.color.colorPrimaryDark), R.drawable.ic_interest);
        bottomNavigationView.addTab(bottomNavigationItem);
        bottomNavigationView.addTab(bottomNavigationItem1);
//        bottomNavigationView.addTab(bottomNavigationItem2);
//        bottomNavigationView.addTab(bottomNavigationItem3);
        bottomNavigationView.isWithText(true);
        bottomNavigationView.isColoredBackground(false);
        bottomNavigationView.clearAnimation();

        currentItemIndex = bottomNavigationView.getCurrentItem();

//        bottomNavigationView.setItemActiveColorWithoutColoredBackground(R.color.colorActive);
//        bottomNavigationItem.setColor(R.color.colorActive);
        bottomNavigationView.setTextActiveSize(30);
        bottomNavigationView.setTextInactiveSize(30);
        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {

                switch (index) {
                    case 0: {
                        currentItemIndex = bottomNavigationView.getCurrentItem();
                        setMainFragment(new DashboardFragment(), "dashboard", "dashboard", currentItemIndex, index);
                    }
                    break;
                    case 1: {
                        currentItemIndex = bottomNavigationView.getCurrentItem();
                        setMainFragment(new SettingsFragment(), "settings", "settings", currentItemIndex, index);
                    }
                    break;
//                    case 2: {
//                        currentItemIndex = bottomNavigationView.getCurrentItem();
//                        setMainFragment(new OutgoingFragment(), "outgoing", "outgoing", currentItemIndex, index);
//                    }
//                    break;
//                    case 3: {
//                        currentItemIndex = bottomNavigationView.getCurrentItem();
//                        setMainFragment(new FavoriteFragment(), "favorite", "favorite", currentItemIndex, index);
//                    }
//                    break;
                    default: {
                        setMainFragment(new DashboardFragment(), "dashboard", "dashboard");
                    }
                    break;
                }

            }
        });
    }



    public void setMainFragment(AppFragment fragmentMain, String type, String tag){
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.trans_right_in, R.anim.trans_right_out)
                .replace(R.id.fragment_content, fragmentMain, tag)
                .commit();
        fragmentMain.setToolbarUI((ToolbarUI) (MainActivity.this), type);
    }
    public void setMainFragment(AppFragment fragmentMain, String type, String tag, int currentItemIndex, int switchIndex){
        if(currentItemIndex>switchIndex){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.trans_right_in, R.anim.trans_right_out)
                    .replace(R.id.fragment_content, fragmentMain, tag)
                    .commit();
            fragmentMain.setToolbarUI((ToolbarUI) (MainActivity.this), type);
        } else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.trans_left_in, R.anim.trans_left_out)
                    .replace(R.id.fragment_content, fragmentMain, tag)
                    .commit();
            fragmentMain.setToolbarUI((ToolbarUI) (MainActivity.this), type);
        }

    }

    public void setToolbarType(String type) {
        if (type == "dashboard") {

            headerText.setText("Dashboard");
        } else if (type == "settings") {

            headerText.setText("Settings");
            //  headerText.setVisibility(View.VISIBLE);

        }
//        else if (type == "outgoing") {
//
//            headerText.setText("OutGoing");
//            //headerText.setVisibility(View.VISIBLE);
//
//        } else if (type == "favorite") {
//
//            headerText.setText("Favorite");
//          //  headerText.setVisibility(View.VISIBLE);
//
//        }
    }
}
