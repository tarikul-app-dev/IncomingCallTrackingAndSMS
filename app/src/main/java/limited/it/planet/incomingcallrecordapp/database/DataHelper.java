package limited.it.planet.incomingcallrecordapp.database;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import limited.it.planet.incomingcallrecordapp.constant.Constants;
import limited.it.planet.incomingcallrecordapp.util.MyPhoneReceiver;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tarikul on 2/26/2018.
 */

public class DataHelper {
    // db version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "incoming_call_record";
    private static final String DATABASE_TABLE_CALL_RECORD = "table_call_record";
    private DataHelper.DBHelper dbhelper;
    private final Context context;
    private SQLiteDatabase database;

    // insert row
    public static final String KEY_ROWID = "id";
    public static final String KEY_USER_NUMBER = "user_number";
    public static final String KEY_DATE = "date";
    public static final String KEY_VOICE_FILE_PATH = "voice_file_path";



    private static class DBHelper extends SQLiteOpenHelper {

        @SuppressLint("NewApi")
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // create table to store msgs
            db.execSQL(" CREATE TABLE " + DATABASE_TABLE_CALL_RECORD + " ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_USER_NUMBER + " TEXT, "
                    + KEY_DATE + " TEXT, "
                    + KEY_VOICE_FILE_PATH + " TEXT );");


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_CALL_RECORD);


            onCreate(db);
        }

    }
    // constructor
    public DataHelper(Context c) {
        context = c;
    }

    // open db
    public DataHelper open() {
        dbhelper = new  DBHelper(context);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    // close db
    public void close() {
        dbhelper.close();
    }


    public long insertUserProfile(String userNumber,String date,String voiceFilePath){
        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_NUMBER, userNumber);
        cv.put(KEY_DATE, date);
        cv.put(KEY_VOICE_FILE_PATH, voiceFilePath);

        long dbInsert = database.insert(DATABASE_TABLE_CALL_RECORD, null, cv);

        if(dbInsert != -1) {

            Toast.makeText(context, "New row added in Basic Information, row id: " + dbInsert, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        }



        return dbInsert;
    }


}
