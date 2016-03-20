package com.dimon.mobilesafe;

/**
 * Created by Dimon on 2016/2/25.
 */
public class Update {
    private String versionName;
    private int versionCode;
    private String description;
    private String downloadUrl;

    public Update(String versionName, int versionCode, String description, String downloadUrl) {
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.description = description;
        this.downloadUrl = downloadUrl;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "Update{" +
                "versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", description='" + description + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }

}
