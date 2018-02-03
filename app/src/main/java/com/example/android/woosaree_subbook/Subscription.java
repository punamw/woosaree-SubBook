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

    //Subscription fields
    public Subscription(String vName, String vDate, String vCharge, String vComment) {
        mSubName = vName;
        mSubDate = vDate;
        mSubCharge = vCharge;
        mSubComment = vComment;

    }

    /**
     * Get subscription name
     * @return mSubName
     */
    public String getSubName() {
        return this.mSubName;
    }


    /**
     * Get subscription date
     * @return mSubDate
     */
    public String getSubDate() {
        return this.mSubDate;
    }

    /**
     * Get subscription charge
     * @return mSubCharge
     */
    public String getSubCharge() {
        return this.mSubCharge;
    }

    /**
     * Get subscription comment
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
     * Set the date of the subscription
     * @param date
     */
    public void setSubDate(String date) {

        this.mSubDate = date;
    }

    /**
     * Set the charge of the subscription
     * @param charge
     */
    public void setSubCharge(String charge) {
        this.mSubCharge = charge;
    }

    /**
     * Set the comment of the subscription
     * @param comment
     */
    public void setSubComment(String comment) {
        this.mSubComment = comment;
    }


}
