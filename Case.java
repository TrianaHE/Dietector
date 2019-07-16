package com.the.dietector.dietector;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TRIANAHE on 05/01/2018.
 */

public class Case implements Parcelable {
    int id_case;
    int id_problem;
    int id_evidence;

    public int getId_case() {
        return id_case;
    }
    public int getId_problem() {
        return id_problem;
    }

    public int getId_evidence() {
        return id_evidence;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_case);
        dest.writeInt(this.id_problem);
        dest.writeInt(this.id_evidence);
    }

    public Case() {
    }

    protected Case(Parcel in) {
        this.id_case = in.readInt();
        this.id_problem = in.readInt();
        this.id_evidence = in.readInt();
    }

    public static final Parcelable.Creator<Case> CREATOR = new Parcelable.Creator<Case>() {
        @Override
        public Case createFromParcel(Parcel source) {
            return new Case(source);
        }

        @Override
        public Case[] newArray(int size) {
            return new Case[size];
        }
    };
}
