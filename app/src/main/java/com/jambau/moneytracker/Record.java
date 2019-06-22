package com.jambau.moneytracker;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Getter;


@Getter
class Record implements Parcelable {

    public static final String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_INCOME = "incomes";
    public static final String TYPE_EXPENSE = "expenses";
    public static final String TYPE_BUDGET = "budget";

    private int id;
    private String name;
    private String price;
    private String type;

    protected Record(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readString();
        type = in.readString();
    }

    public Record(String name, String price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(type);
    }
}
