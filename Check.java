package com.the.dietector.dietector;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TRIANAHE on 13/12/2017.
 */

public class Check implements Parcelable {
    int id;
    String nama;
    int berat;
    int tinggi;
    String status;
    float berat_ideal;
    String create_at;
    String update_at;

    public int getId() {
        return id;

    }
    public String getNama() {
        return nama;

    }

    public int getBerat() {
        return berat;
    }

    public int getTinggi() {
        return tinggi;
    }

    public String getStatus() {
        return status;

    }

    public float getBeratIdeal() {
        return berat_ideal;
    }

    public String getCreateAt() {
        return create_at;
    }

    public String getUpdateAt() {
        return update_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nama);
        dest.writeInt(this.berat);
        dest.writeInt(this.tinggi);
        dest.writeString(this.status);
        dest.writeFloat(this.berat_ideal);
        dest.writeString(this.create_at);
        dest.writeString(this.update_at);
    }

    public Check() {
    }

    protected Check(Parcel in) {
        this.id = in.readInt();
        this.nama = in.readString();
        this.berat = in.readInt();
        this.tinggi = in.readInt();
        this.status = in.readString();
        this.berat_ideal = in.readFloat();
        this.create_at = in.readString();
        this.update_at = in.readString();
    }

    public static final Parcelable.Creator<Check> CREATOR = new Parcelable.Creator<Check>() {
        @Override
        public Check createFromParcel(Parcel source) {
            return new Check(source);
        }

        @Override
        public Check[] newArray(int size) {
            return new Check[size];
        }
    };
}
