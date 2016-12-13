package com.bar.android.my_proj;

/**
 * Created by kavin on 12/8/2016.
 */
public class SongTracker {

    private long SongId;
    private String SongName;
    private String SongLoc;
    private String UserGroup;
    private int Count;
    private int Duration;
    private String Artist;
    private int Year;

    public int getYear() {
        return this.Year;
    }
    public void setYear(int Year) {
        this.Year = Year;
    }

    public String getArtist() {
        return this.Artist;
    }
    public void setArtist(String Artist) {
        this.Artist = Artist;
    }

    public int getDuration() {
        return this.Duration;
    }
    public void setDuration(int Duration) {
        this.Duration = Duration;
    }

    public String getUserGroup() {
        return this.UserGroup;
    }
    public void setUserGroup(String UserGroup) {
        this.UserGroup = UserGroup;
    }

    public int getCount() {
        return this.Count;
    }
    public void setCount(int Count) {
        this.Count = Count;
    }

    public String getSongLoc() {
        return this.SongLoc;
    }
    public void setSongLoc(String SongLoc) {
        this.SongLoc = SongLoc;
    }
    public String getSongName() {
        return this.SongName;
    }
    public void setSongName(String SongName) {
        this.SongName = SongName;
    }

    public long getSongId() {
        return this.SongId;
    }
    public void setSongId(long SongId) {
        this.SongId = SongId;
    }
}