package creativestudioaq.alarmapp;

/**
 * Created by honggyu on 2016-02-05.
 */
public class Repeatlist {

    private String _time;
    private Boolean _check;


    public String gettime() {
        return _time;
    }


    public Boolean getcheck() {
        return _check;
    }


    public Repeatlist(String time, Boolean check) {
        _time = time;
        _check = check;
    }
}
