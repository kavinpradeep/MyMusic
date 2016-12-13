package com.bar.android.my_proj;

/**
 * Created by kavin on 12/8/2016.
 */
public class NextSongTracker {

    private String SongId;
    private String UserGrp;
    private int Id;

    public int getId() {
        return this.Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }

    public String getSongId() {
        return this.SongId;
    }
    public void setSongId(String SongId) {
        this.SongId = SongId;
    }

    public String getUserGrp() {
        return this.UserGrp;
    }
    public void setUserGrp(String UserGrp) {
        this.UserGrp = UserGrp;
    }

}
