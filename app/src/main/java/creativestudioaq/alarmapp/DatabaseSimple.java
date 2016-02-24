package creativestudioaq.alarmapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MIN on 2016. 2. 24..
 */
public class DatabaseSimple extends SQLiteOpenHelper {
    static DatabaseSimple instance = null;
    static SQLiteDatabase database = null;

    static final String DATABASE_NAME = "SIMPLE_DB";
    static final int DATABASE_VERSION = 1;

    public static final String ALARM_TABLE = "simpleAlarm";
    public static final String COLUMN_SIMPLE_ALARM_ID = "_id";
    public static final String COLUMN_SIMPLE_ALARM_ACTIVE = "alarm_active";
    public static final String COLUMN_SIMPLE_ALARM_TIME = "alarm_time";


    DatabaseSimple(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void init(Context context) {
        if (null == instance) {
            instance = new DatabaseSimple(context);
        }
    }

    public static SQLiteDatabase getDatabase() {
        if (null == database) {
            database = instance.getWritableDatabase();
        }
        return database;
    }

    public static void deactivate() {
        if (null != database && database.isOpen()) {
            database.close();
        }
        database = null;
        instance = null;
    }

    public static long create(Alarm alarm) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SIMPLE_ALARM_ACTIVE, alarm.getAlarmActive());
        cv.put(COLUMN_SIMPLE_ALARM_TIME, alarm.getAlarmTimeString());

        return getDatabase().insert(ALARM_TABLE, null, cv);
    }

    public static int update(Alarm alarm) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SIMPLE_ALARM_ACTIVE, alarm.getAlarmActive());
        cv.put(COLUMN_SIMPLE_ALARM_TIME, alarm.getAlarmTimeString());

        return getDatabase().update(ALARM_TABLE, cv, "_id=" + alarm.getId(), null);
    }

    public static int deleteEntry(Alarm alarm) {
        return deleteEntry(alarm.getId());
    }

    public static int deleteEntry(int id) {
        return getDatabase().delete(ALARM_TABLE, COLUMN_SIMPLE_ALARM_ID + "=" + id, null);
    }

    public static int deleteAll() {
        return getDatabase().delete(ALARM_TABLE, "1", null);
    }

    public static Alarm getAlarm(int id) {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_SIMPLE_ALARM_ID,
                COLUMN_SIMPLE_ALARM_ACTIVE,
                COLUMN_SIMPLE_ALARM_TIME
        };
        Cursor c = getDatabase().query(ALARM_TABLE, columns, COLUMN_SIMPLE_ALARM_ID + "=" + id, null, null, null,
                null);
        Alarm alarm = null;

        if (c.moveToFirst()) {

            alarm = new Alarm();
            alarm.setId(c.getInt(1));
            alarm.setAlarmActive(c.getInt(2) == 1);
            alarm.setAlarmTime(c.getString(3));
            alarm.setFeelingOk(false);
            alarm.setSimple(true);
        }
        c.close();
        return alarm;
    }


    public static Cursor getCursor() {

        String[] columns = new String[]{
                COLUMN_SIMPLE_ALARM_ID,
                COLUMN_SIMPLE_ALARM_ACTIVE,
                COLUMN_SIMPLE_ALARM_TIME
        };
        return getDatabase().query(ALARM_TABLE, columns, null, null, null, null,
                null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + ALARM_TABLE + " ( "
                + COLUMN_SIMPLE_ALARM_ID + " INTEGER primary key autoincrement, "
                + COLUMN_SIMPLE_ALARM_ACTIVE + " INTEGER NOT NULL, "
                + COLUMN_SIMPLE_ALARM_TIME + " TEXT NOT NULL )");

        Log.i("database", "simpleDatabase Create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ALARM_TABLE);
        onCreate(db);
    }

    public static List<Alarm> getAll() {
        List<Alarm> alarms = new ArrayList<Alarm>();
        Cursor cursor = DatabaseSimple.getCursor();
        if (cursor.moveToFirst()) {
            do {
                Alarm alarm = new Alarm();
                alarm.setId(cursor.getInt(0));
                alarm.setAlarmActive(cursor.getInt(1) == 1);
                alarm.setAlarmTime(cursor.getString(2));
                alarm.setFeelingOk(false);
                alarm.setSimple(true);

                alarms.add(alarm);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return alarms;
    }
}
