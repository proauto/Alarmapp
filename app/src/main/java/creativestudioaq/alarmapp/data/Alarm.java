package creativestudioaq.alarmapp.data;

/**
 * Created by HosungKim on 2016-02-13.
 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import creativestudioaq.alarmapp.tool.AlarmAlertBroadcastReciever;

public class Alarm implements Serializable {

    //난이도

    public enum Difficulty{
        EASY,
        MEDIUM,
        HARD;

        @Override
        public String toString() {
            switch(this.ordinal()){
                case 0:
                    return "Easy";
                case 1:
                    return "Medium";
                case 2:
                    return "Hard";
            }
            return super.toString();
        }
    }

    //요일일

    public enum Day{
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY;

        @Override
        public String toString() {
            switch(this.ordinal()){
                case 0:
                    return "일";
                case 1:
                    return "월";
                case 2:
                    return "화";
                case 3:
                    return "수";
                case 4:
                    return "목";
                case 5:
                    return "금";
                case 6:
                    return "토";
            }
            return super.toString();
        }

    }
    private static final long serialVersionUID = 8699489847426803789L;
    private int id;
    private Boolean alarmActive = true;
    private Calendar alarmTime = Calendar.getInstance();
    private Day[] days = { Day.SUNDAY, Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY};
    private String alarmTonePath = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
    private Boolean vibrate = false;
    private String alarmName = "AliceAlarm";
    private Difficulty difficulty = Difficulty.EASY;
    private Boolean simple = false;
    private Boolean feelingOk = true;
    private String rabbitFeeling = "";
    private String myFeeling = "";
    private float volume = 50f;
    private Boolean repeatUse = false;
    private int repeatMinute = 3;
    private int repeatNum = 1;

    public Alarm() {

    }

    public Boolean getAlarmActive() {
        return alarmActive;
    }


    public void setAlarmActive(Boolean alarmActive) {
        this.alarmActive = alarmActive;
    }


    public Calendar getAlarmTime() {
        if (alarmTime.before(Calendar.getInstance()))
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);
        while(!Arrays.asList(getDays()).contains(Day.values()[alarmTime.get(Calendar.DAY_OF_WEEK)-1])){
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);
        }
        return alarmTime;
    }

    public String getAlarmTimeString() {

        String time = "";
        if (alarmTime.get(Calendar.HOUR_OF_DAY) <= 9)
            time += "0";
        time += String.valueOf(alarmTime.get(Calendar.HOUR_OF_DAY));
        time += ":";

        if (alarmTime.get(Calendar.MINUTE) <= 9)
            time += "0";
        time += String.valueOf(alarmTime.get(Calendar.MINUTE));

        return time;
    }

    /**
     * @param alarmTime
     *            the alarmTime to set
     */
    public void setAlarmTime(Calendar alarmTime) {
        this.alarmTime = alarmTime;
    }

    /**
     * @param alarmTime
     *            the alarmTime to set
     */
    public void setAlarmTime(String alarmTime) {

        String[] timePieces = alarmTime.split(":");

        Calendar newAlarmTime = Calendar.getInstance();
        newAlarmTime.set(Calendar.HOUR_OF_DAY,
                Integer.parseInt(timePieces[0]));
        newAlarmTime.set(Calendar.MINUTE, Integer.parseInt(timePieces[1]));
        newAlarmTime.set(Calendar.SECOND, 0);
        setAlarmTime(newAlarmTime);
    }

    /**
     * @return the repeatDays
     */
    public Day[] getDays() {
        return days;
    }


    public void setDays(Day[] days) {
        this.days = days;
    }

    public void addDay(Day day){
        boolean contains = false;
        for(Day d : getDays())
            if(d.equals(day))
                contains = true;
        if(!contains){
            List<Day> result = new LinkedList<Day>();
            for(Day d : getDays())
                result.add(d);
            result.add(day);
            setDays(result.toArray(new Day[result.size()]));
        }
    }

    public void removeDay(Day day) {

        List<Day> result = new LinkedList<Day>();
        for(Day d : getDays())
            if(!d.equals(day))
                result.add(d);
        setDays(result.toArray(new Day[result.size()]));
    }

    /**
     * @return the alarmTonePath
     */
    public String getAlarmTonePath() {
        return alarmTonePath;
    }

    /**
     * @param alarmTonePath the alarmTonePath to set
     */
    public void setAlarmTonePath(String alarmTonePath) {
        this.alarmTonePath = alarmTonePath;
    }

    /**
     * @return the vibrate
     */
    public Boolean getVibrate() {
        return vibrate;
    }

    /**
     * @param vibrate
     *            the vibrate to set
     */
    public void setVibrate(Boolean vibrate) {
        this.vibrate = vibrate;
    }

    /**
     * @return the alarmName
     */
    public String getAlarmName() {
        return alarmName;
    }

    /**
     * @param alarmName
     *            the alarmName to set
     */
    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public Boolean getSimple() {
        return simple;
    }

    public void setSimple(Boolean simple) {
        this.simple = simple;
    }

    public Boolean getFeelingOk() {
        return feelingOk;
    }

    public String getRabbitFeeling() {
        return rabbitFeeling;
    }

    public void setRabbitFeeling(String rabbitFeeling) {
        this.rabbitFeeling = rabbitFeeling;
    }

    public void setFeelingOk(Boolean feelingOk) {
        this.feelingOk = feelingOk;
    }

    public String getMyFeeling() {
        return myFeeling;
    }

    public void setMyFeeling(String myFeeling) {
        this.myFeeling = myFeeling;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getRepeatUse() {
        return repeatUse;
    }

    public void setRepeatUse(Boolean repeatUse) {
        this.repeatUse = repeatUse;
    }

    public int getRepeatMinute() {
        return repeatMinute;
    }

    public void setRepeatMinute(int repeatMinute) {
        this.repeatMinute = repeatMinute;
    }

    public int getRepeatNum() {
        return repeatNum;
    }

    public void setRepeatNum(int repeatNum) {
        this.repeatNum = repeatNum;
    }

    public String getRepeatDaysString() {


        StringBuilder daysStringBuilder = new StringBuilder();


        if(getDays().length == Day.values().length){
            daysStringBuilder.append("매일");
        }else{
            Arrays.sort(getDays(), new Comparator<Day>() {
                @Override
                public int compare(Day lhs, Day rhs) {
                    return lhs.ordinal() - rhs.ordinal();
                }
            });
            for(Day d : getDays()){
                        daysStringBuilder.append(d.toString());
                        daysStringBuilder.append(' ');
                }
            }

            daysStringBuilder.setLength(daysStringBuilder.length());

        return daysStringBuilder.toString();
    }

    public void schedule(Context context) {
        setAlarmActive(true);

        Intent myIntent = new Intent(context, AlarmAlertBroadcastReciever.class);
        myIntent.putExtra("alarm", this);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, getAlarmTime().getTimeInMillis(), pendingIntent);
    }

    public String getTimeUntilNextAlarmMessage(){
        long timeDifference = getAlarmTime().getTimeInMillis() - System.currentTimeMillis();
        long days = timeDifference / (1000 * 60 * 60 * 24);
        long hours = timeDifference / (1000 * 60 * 60) - (days * 24);
        long minutes = timeDifference / (1000 * 60) - (days * 24 * 60) - (hours * 60);
        long seconds = timeDifference / (1000) - (days * 24 * 60 * 60) - (hours * 60 * 60) - (minutes * 60);
        String alert = "알람이 ";
        if (days > 0) {
            alert += String.format(
                    "%d 일 %d 시간 %d 분 ", days,
                    hours, minutes);
        } else {
            if (hours > 0) {
                alert += String.format("%d 시간 %d 분 ",
                        hours, minutes);
            } else {
                if (minutes > 0) {
                    alert += String.format("%d 분 ", minutes);
                } else {
                    alert += String.format("잠시 ");
                }

            }
        }

        alert += "후에 울립니다.";

        return alert;
    }
}

