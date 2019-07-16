package com.the.dietector.dietector;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TRIANAHE on 04/01/2018.
 */

public class Evidences implements Parcelable {
    int id;
    String code;
    String name;
    boolean checked;

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked){
        this.checked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    public Evidences() {
    }

    protected Evidences(Parcel in) {
        this.id = in.readInt();
        this.code = in.readString();
        this.name = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Evidences> CREATOR = new Parcelable.Creator<Evidences>() {
        @Override
        public Evidences createFromParcel(Parcel source) {
            return new Evidences(source);
        }

        @Override
        public Evidences[] newArray(int size) {
            return new Evidences[size];
        }
    };
}
