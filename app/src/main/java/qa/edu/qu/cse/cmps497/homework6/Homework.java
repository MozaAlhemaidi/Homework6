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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public boolean getIs_screenshot() {
        return is_screenshot;
    }

    public void setIs_screenshot(boolean is_screenshot) {
        this.is_screenshot = is_screenshot;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }




}
