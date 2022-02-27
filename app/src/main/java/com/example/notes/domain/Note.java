package com.example.notes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

public class Note implements Parcelable {

    private final String id;
    private final String title;
    private final String text;
    private final Date createdAt;

    public Note(String id, String title, String text, Date createdAt) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
    }

    protected Note(Parcel in) {
        id = in.readString();
        title = in.readString();
        text = in.readString();
        createdAt = (Date) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(text);
        parcel.writeSerializable(createdAt);
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

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(title, note.title) && Objects.equals(text, note.text) && Objects.equals(createdAt, note.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, createdAt);
    }
}
