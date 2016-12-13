package com.bar.android.my_proj;

/**
 * Created by kavin on 12/8/2016.
 */
public class UserGrpTracker {

    private String UserGroup;
    private long UserGrpID;

    public long getUserGrpID() {
        return this.UserGrpID;
    }
    public void setUserGrpID(long UserGrpID) {
        this.UserGrpID = UserGrpID;
    }

    public String getUserGroup() {
        return UserGroup;
    }
    public void setUserGroup(String UserGroup) {
        this.UserGroup = UserGroup;
    }
}
