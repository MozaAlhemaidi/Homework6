package qa.edu.qu.cse.cmps497.homework6;

/**
 * Created by MozaAl-hemaidi on 12/5/15.
 */

public class Homework {
    String title;
    String summary;
    String purpose;
    String due;
    boolean is_screenshot;
    String screenshot;
    int points;
    public Homework(String t, String s,String p,String d,boolean is,String sc,int po ){
        title=t;
        summary=s;
        purpose=p;
        due=d;
        is_screenshot=is;
        screenshot=sc;
        points=po;

    }
    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getDue() {
        return due;
    }


    public String getScreenshot() {
        return screenshot;
    }

    public int getPoints() {
        return points;
    }

    public boolean getIs_screenshot() {
        return is_screenshot;
    }
}
