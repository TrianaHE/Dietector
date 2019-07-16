package com.the.dietector.dietector;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TRIANAHE on 05/01/2018.
 */

public class Problem implements Parcelable {
    int id;
    String code;
    String name;
    String notes;

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
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
        dest.writeString(this.notes);
    }

    public Problem() {
    }

    protected Problem(Parcel in) {
        this.id = in.readInt();
        this.code = in.readString();
        this.name = in.readString();
        this.notes = in.readString();
    }

    public static final Parcelable.Creator<Problem> CREATOR = new Parcelable.Creator<Problem>() {
        @Override
        public Problem createFromParcel(Parcel source) {
            return new Problem(source);
        }

        @Override
        public Problem[] newArray(int size) {
            return new Problem[size];
        }
    };
}
