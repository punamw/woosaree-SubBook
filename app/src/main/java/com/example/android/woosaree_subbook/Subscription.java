package com.example.android.woosaree_subbook;

/**
 * Created by PunamWoosaree on 2018-01-21.
 */

public class Subscription {

    // Name of com.example.android.woosaree_subbook.Subscription
    private String mSubName;

    // Description of Date
    private String mSubDate;

    // Description of Charge
    private String mSubCharge;

    // Description of Comment
    private String mSubComment;


    public Subscription(String vName, String vDate, String vCharge, String vComment) {
        mSubName = vName;
        mSubDate = vDate;
        mSubCharge = vCharge;
        mSubComment = vComment;

    }

    /*
     * Get com.example.android.woosaree_subbook.Subscription Name
     * @return mSubName
     */
    public String getSubName() {
        return this.mSubName;
    }


    /**
     * Get counter date
     * @return mSubDate
     */
    public String getSubDate() {
        return this.mSubDate;
    }

    /**
     * Get counter charge
     * @return mSubCharge
     */
    public String getSubCharge() {
        return this.mSubCharge;
    }

    /**
     * Get counter comment
     * @return mSubComment
     */
    public String getSubComment() {
        return this.mSubComment;
    }


    /**
     * Set the name of the subscription
     * @param name
     */
    public void setSubName(String name) {
        this.mSubName = name;
    }

    /**
     * Set the date of the counter
     * @param date
     */
    public void setSubDate(String date) {

        this.mSubDate = date;
    }

    /**
     * Set the charge of the counter
     * @param charge
     */
    public void setSubCharge(String charge) {
        this.mSubCharge = charge;
    }

    /**
     * Set the comment of the counter
     * @param comment
     */
    public void setSubComment(String comment) {
        this.mSubComment = comment;
    }


}
