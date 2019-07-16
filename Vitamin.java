package com.the.dietector.dietector;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TRIANAHE on 13/12/2017.
 */

public class Vitamin implements Parcelable {
    int id;
    String namavit;
    String deskripsivit;
    String manfaatvit;
    String contohvit;
    String create_at;
    String update_at;

    public int getId() {
        return id;
    }

    public String getNamavit() {
        return namavit;
    }

    public String getDeskripsivit() {
        return deskripsivit;
    }

    public String getManfaatvit() {
        return manfaatvit;
    }

    public String getContohvit() {
        return contohvit;
    }

    public String getCreate_at() {
        return create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.namavit);
        dest.writeString(this.deskripsivit);
        dest.writeString(this.manfaatvit);
        dest.writeString(this.contohvit);
        dest.writeString(this.create_at);
        dest.writeString(this.update_at);
    }

    public Vitamin() {
    }

    protected Vitamin(Parcel in) {
        this.id = in.readInt();
        this.namavit = in.readString();
        this.deskripsivit = in.readString();
        this.manfaatvit = in.readString();
        this.contohvit = in.readString();
        this.create_at = in.readString();
        this.update_at = in.readString();
    }

    public static final Parcelable.Creator<Vitamin> CREATOR = new Parcelable.Creator<Vitamin>() {
        @Override
        public Vitamin createFromParcel(Parcel source) {
            return new Vitamin(source);
        }

        @Override
        public Vitamin[] newArray(int size) {
            return new Vitamin[size];
        }
    };
}
