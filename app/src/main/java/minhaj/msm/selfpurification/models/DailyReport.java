package minhaj.msm.selfpurification.models;

public class DailyReport {

    private int id;
    private DateModel date;

    public DailyReport(boolean q01, boolean q02, boolean q03, boolean q04, boolean q05, boolean q06, boolean q07, boolean q08, boolean q09, boolean q10) {
        this.q01 = q01;
        this.q02 = q02;
        this.q03 = q03;
        this.q04 = q04;
        this.q05 = q05;
        this.q06 = q06;
        this.q07 = q07;
        this.q08 = q08;
        this.q09 = q09;
        this.q10 = q10;
    }

    private boolean q01;
    private boolean q02;
    private boolean q03;
    private boolean q04;
    private boolean q05;
    private boolean q06;
    private boolean q07;
    private boolean q08;
    private boolean q09;
    private boolean q10;

    public boolean isQ10() {
        return q10;
    }

    public void setQ10(boolean q10) {
        this.q10 = q10;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date.getDate();
    }

    public void setDate(DateModel date) {
        this.date = date;
        this.id = date.getId();
    }

    public boolean isQ01() {
        return q01;
    }

    public void setQ01(boolean q01) {
        this.q01 = q01;
    }

    public boolean isQ02() {
        return q02;
    }

    public void setQ02(boolean q02) {
        this.q02 = q02;
    }

    public boolean isQ03() {
        return q03;
    }

    public void setQ03(boolean q03) {
        this.q03 = q03;
    }

    public boolean isQ04() {
        return q04;
    }

    public void setQ04(boolean q04) {
        this.q04 = q04;
    }

    public boolean isQ05() {
        return q05;
    }

    public void setQ05(boolean q05) {
        this.q05 = q05;
    }

    public boolean isQ06() {
        return q06;
    }

    public void setQ06(boolean q06) {
        this.q06 = q06;
    }

    public boolean isQ07() {
        return q07;
    }

    public void setQ07(boolean q07) {
        this.q07 = q07;
    }

    public boolean isQ08() {
        return q08;
    }

    public void setQ08(boolean q08) {
        this.q08 = q08;
    }

    public boolean isQ09() {
        return q09;
    }

    public void setQ09(boolean q09) {
        this.q09 = q09;
    }

    public int getScore() {
        int score = 0;
        score += q01 ? 1 : 0;
        score += q02 ? 1 : 0;
        score += q03 ? 1 : 0;
        score += q04 ? 1 : 0;
        score += q05 ? 1 : 0;
        score += q06 ? 1 : 0;
        score += q07 ? 1 : 0;
        score += q08 ? 1 : 0;
        score += q09 ? 1 : 0;
        score += q10 ? 1 : 0;
        return score;
    }

}
