package com.jumei.test.util;

public enum KS3Method {

    upload(0), delete(1), update(2), move(3), copyforce(4), copy(5), moveforce(6);

    private int type;

    private KS3Method(int type) {
        this.type = type;
    }

    public int getValue() {
        return type;
    }
}
