package com.example.pixelwallpaper;

public class WallpaperModel {
    private int id;
    private  String orginalurl,mediumurl;
    public WallpaperModel() {
    }

    public WallpaperModel(int id, String orginalurl, String mediumurl) {
        this.id = id;
        this.orginalurl = orginalurl;
        this.mediumurl = mediumurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrginalurl() {
        return orginalurl;
    }

    public void setOrginalurl(String orginalurl) {
        this.orginalurl = orginalurl;
    }

    public String getMediumurl() {
        return mediumurl;
    }

    public void setMediumurl(String mediumurl) {
        this.mediumurl = mediumurl;
    }
}
