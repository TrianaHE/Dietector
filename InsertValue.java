package com.the.dietector.dietector;

import java.util.List;

/**
 * Created by TRIANAHE on 06/12/2017.
 */

public class InsertValue<T> {
    int success;
    String message;
    List<T> data;

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<T> getData() {
        return data;
    }
}
