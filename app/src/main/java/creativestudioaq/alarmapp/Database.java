package creativestudioaq.alarmapp;

/**
 * Created by HosungKim on 2016-02-13.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;


public class Database extends SQLiteOpenHelper {
    static Database instance = null;
    static SQLiteDatabase database = null;

    static final String DATABASE_NAME = "DB";
    static final int DATABASE_VERSION = 1;

    public static final String ALARM_TABLE = "alarm";
    public static final String COLUMN_ALARM_ID = "_id";
    public static final String COLUMN_ALARM_ACTIVE = "alarm_active";
    public static final String COLUMN_ALARM_TIME = "alarm_time";
    public static final String COLUMN_ALARM_DAYS = "alarm_days";
    public static final String COLUMN_ALARM_TONE = "alarm_tone";
    public static final String COLUMN_ALARM_VOLUME = "alarm_volume";
    public static final String COLUMN_ALARM_VIBRATE = "alarm_vibrate";
    public static final String COLUMN_ALARM_SIMPLE = "alarm_simple";
    public static final String COLUMN_ALARM_FEELING_OK = "alarm_feeling_ok";
    public static final String COLUMN_ALARM_RABBIT_FEELING = "alarm_rabbit_feeling";
    public static final String COLUMN_ALARM_MY_FEELING = "alarm_feeling";
    public static final String COLUMN_ALARM_REPEAT_USE = "alarm_repeat_use";
    public static final String COLUMN_ALARM_REPEAT_MINUTE = "alarm_repeat_minute";
    public static final String COLUMN_ALARM_REPEAT_NUM = "alarm_repeat_num";


    public static void init(Context context) {
        if (null == instance) {
            instance = new Database(context);
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
        cv.put(COLUMN_ALARM_ACTIVE, alarm.getAlarmActive());
        cv.put(COLUMN_ALARM_TIME, alarm.getAlarmTimeString());

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(bos);
            oos.writeObject(alarm.getDays());
            byte[] buff = bos.toByteArray();

            cv.put(COLUMN_ALARM_DAYS, buff);

        } catch (Exception e) {
        }

        //  cv.put(COLUMN_ALARM_DIFFICULTY, alarm.getDifficulty().ordinal()); 
        cv.put(COLUMN_ALARM_TONE, alarm.getAlarmTonePath());
        cv.put(COLUMN_ALARM_VOLUME, alarm.getVolume());
        cv.put(COLUMN_ALARM_VIBRATE, alarm.getVibrate());
        //   cv.put(COLUMN_ALARM_NAME, alarm.getAlarmName()); 
        cv.put(COLUMN_ALARM_SIMPLE, alarm.getSimple());
        cv.put(COLUMN_ALARM_FEELING_OK, alarm.getFeelingOk());
        cv.put(COLUMN_ALARM_RABBIT_FEELING, alarm.getRabbitFeeling());
        cv.put(COLUMN_ALARM_MY_FEELING, alarm.getMyFeeling());
        cv.put(COLUMN_ALARM_REPEAT_USE, alarm.getRepeatUse());
        cv.put(COLUMN_ALARM_REPEAT_MINUTE, alarm.getRepeatMinute());
        cv.put(COLUMN_ALARM_REPEAT_NUM, alarm.getRepeatNum());

        return getDatabase().insert(ALARM_TABLE, null, cv);
    }

    public static int update(Alarm alarm) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ALARM_ACTIVE, alarm.getAlarmActive());
        cv.put(COLUMN_ALARM_TIME, alarm.getAlarmTimeString());

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(bos);
            oos.writeObject(alarm.getDays());
            byte[] buff = bos.toByteArray();

            cv.put(COLUMN_ALARM_DAYS, buff);

        } catch (Exception e) {
        }

        //  cv.put(COLUMN_ALARM_DIFFICULTY, alarm.getDifficulty().ordinal()); 
        cv.put(COLUMN_ALARM_TONE, alarm.getAlarmTonePath());
        cv.put(COLUMN_ALARM_VOLUME, alarm.getVolume());
        cv.put(COLUMN_ALARM_VIBRATE, alarm.getVibrate());
        //   cv.put(COLUMN_ALARM_NAME, alarm.getAlarmName()); 
        cv.put(COLUMN_ALARM_SIMPLE, alarm.getSimple());
        cv.put(COLUMN_ALARM_FEELING_OK, alarm.getFeelingOk());
        cv.put(COLUMN_ALARM_RABBIT_FEELING, alarm.getRabbitFeeling());
        cv.put(COLUMN_ALARM_MY_FEELING, alarm.getMyFeeling());
        cv.put(COLUMN_ALARM_REPEAT_USE, alarm.getRepeatUse());
        cv.put(COLUMN_ALARM_REPEAT_MINUTE, alarm.getRepeatMinute());
        cv.put(COLUMN_ALARM_REPEAT_NUM, alarm.getRepeatNum());


        return getDatabase().update(ALARM_TABLE, cv, "_id=" + alarm.getId(), null);
    }

    public static int deleteEntry(Alarm alarm) {
        return deleteEntry(alarm.getId());
    }

    public static int deleteEntry(int id) {
        return getDatabase().delete(ALARM_TABLE, COLUMN_ALARM_ID + "=" + id, null);
    }

    public static int deleteAll() {
        return getDatabase().delete(ALARM_TABLE, "1", null);
    }

    public static Alarm getAlarm(int id) {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_ALARM_ID,
                COLUMN_ALARM_ACTIVE,
                COLUMN_ALARM_TIME,
                COLUMN_ALARM_DAYS,
                COLUMN_ALARM_TONE,
                COLUMN_ALARM_VOLUME,
                COLUMN_ALARM_VIBRATE,
                COLUMN_ALARM_SIMPLE,
                COLUMN_ALARM_FEELING_OK,
                COLUMN_ALARM_RABBIT_FEELING,
                COLUMN_ALARM_MY_FEELING,
                COLUMN_ALARM_REPEAT_USE,
                COLUMN_ALARM_REPEAT_MINUTE,
                COLUMN_ALARM_REPEAT_NUM
        };
        Cursor c = getDatabase().query(ALARM_TABLE, columns, COLUMN_ALARM_ID + "=" + id, null, null, null,
                null);
        Alarm alarm = null;

        if (c.moveToFirst()) {

            alarm = new Alarm();
            alarm.setId(c.getInt(1));
            alarm.setAlarmActive(c.getInt(2) == 1);
            alarm.setAlarmTime(c.getString(3));
            byte[] repeatDaysBytes = c.getBlob(4);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(repeatDaysBytes);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Alarm.Day[] repeatDays;
                Object object = objectInputStream.readObject();
                if (object instanceof Alarm.Day[]) {
                    repeatDays = (Alarm.Day[]) object;
                    alarm.setDays(repeatDays);
                }
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // alarm.setDifficulty(Difficulty.values()[cursor.getInt(4)]);
            alarm.setAlarmTonePath(c.getString(4));
            alarm.setVolume(c.getFloat(5));
            alarm.setVibrate(c.getInt(6) == 1);
            //alarm.setAlarmName(cursor.getString(8));


            alarm.setSimple(c.getInt(7) == 1);
            alarm.setFeelingOk(c.getInt(8) == 1);
            alarm.setRabbitFeeling(c.getString(9));
            alarm.setMyFeeling(c.getString(10));
            alarm.setRepeatUse(c.getInt(11) == 1);
            alarm.setRepeatMinute(c.getInt(12));
            alarm.setRepeatNum(c.getInt(13));

        }
        c.close();
        return alarm;
    }

    public static Cursor getCursor() {

        String[] columns = new String[]{
                COLUMN_ALARM_ID,
                COLUMN_ALARM_ACTIVE,
                COLUMN_ALARM_TIME,
                COLUMN_ALARM_DAYS,
                COLUMN_ALARM_TONE,
                COLUMN_ALARM_VOLUME,
                COLUMN_ALARM_VIBRATE,
                COLUMN_ALARM_SIMPLE,
                COLUMN_ALARM_FEELING_OK,
                COLUMN_ALARM_RABBIT_FEELING,
                COLUMN_ALARM_MY_FEELING,
                COLUMN_ALARM_REPEAT_USE,
                COLUMN_ALARM_REPEAT_MINUTE,
                COLUMN_ALARM_REPEAT_NUM
        };
        return getDatabase().query(ALARM_TABLE, columns, null, null, null, null,
                null);
    }

    Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE IF NOT EXISTS " + ALARM_TABLE + " ( "
                + COLUMN_ALARM_ID + " INTEGER primary key autoincrement, "
                + COLUMN_ALARM_ACTIVE + " INTEGER NOT NULL, "
                + COLUMN_ALARM_TIME + " TEXT NOT NULL, "
                + COLUMN_ALARM_DAYS + " BLOB NOT NULL, "
                + COLUMN_ALARM_TONE + " TEXT NOT NULL, "
                + COLUMN_ALARM_VOLUME + " REAL NOT NULL, "
                + COLUMN_ALARM_VIBRATE + " INTEGER NOT NULL, "
                + COLUMN_ALARM_SIMPLE + " INTEGER NOT NULL, "
                + COLUMN_ALARM_FEELING_OK + " INTEGER NOT NULL, "
                + COLUMN_ALARM_RABBIT_FEELING + " TEXT NOT NULL, "
                + COLUMN_ALARM_MY_FEELING + " TEXT NOT NULL, "
                + COLUMN_ALARM_REPEAT_USE + " INTEGER NOT NULL, "
                + COLUMN_ALARM_REPEAT_MINUTE + " INTEGER NOT NULL,"
                + COLUMN_ALARM_REPEAT_NUM + " INTEGER NOT NULL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ALARM_TABLE);
        onCreate(db);
    }

    public static List<Alarm> getAll() {
        List<Alarm> alarms = new ArrayList<Alarm>();
        Cursor cursor = Database.getCursor();
        if (cursor.moveToFirst()) {

            do {

                Alarm alarm = new Alarm();
                alarm.setId(cursor.getInt(0));
                alarm.setAlarmActive(cursor.getInt(1) == 1);
                alarm.setAlarmTime(cursor.getString(2));
                byte[] repeatDaysBytes = cursor.getBlob(3);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                        repeatDaysBytes);
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(
                            byteArrayInputStream);
                    Alarm.Day[] repeatDays;
                    Object object = objectInputStream.readObject();
                    if (object instanceof Alarm.Day[]) {
                        repeatDays = (Alarm.Day[]) object;
                        alarm.setDays(repeatDays);
                    }
                } catch (StreamCorruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

               // alarm.setDifficulty(Difficulty.values()[cursor.getInt(4)]);
                alarm.setAlarmTonePath(cursor.getString(4));
                alarm.setVolume(cursor.getFloat(5));
                alarm.setVibrate(cursor.getInt(6) == 1);
                //alarm.setAlarmName(cursor.getString(8));


                alarm.setSimple(cursor.getInt(7) == 1);
                alarm.setFeelingOk(cursor.getInt(8) == 1);

                alarm.setRabbitFeeling(cursor.getString(9));
                alarm.setMyFeeling(cursor.getString(10));
                alarm.setRepeatUse(cursor.getInt(11) == 1);
                alarm.setRepeatMinute(cursor.getInt(12));
                alarm.setRepeatNum(cursor.getInt(13));



                alarms.add(alarm);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return alarms;
    }
}