package com.example.notes.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private final String id;
    private final String title;
    private final String text;
    private final String date;

    public Note(String id, String title, String text, String date) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    protected Note(Parcel in) {
        id = in.readString();
        title = in.readString();
        text = in.readString();
        date = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(text);
        parcel.writeString(date);
    }
}
