package creativestudioaq.alarmapp;

/**
 * Created by honggyu on 2016-01-31.
 */
public class Alarmlist {
    private String _time;
    private String _day;
    private Boolean _check;


    public String gettime() {
        return _time;
    }

    public String getday(){
        return _day;
    }

    public Boolean getcheck() {
        return _check;
    }



    public Alarmlist(String time,String day, Boolean check) {
        _time = time;
        _day = day;
        _check = check;
    }
}