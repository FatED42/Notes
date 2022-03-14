package com.example.notes.domain;

import java.util.List;

public interface NotesRepo {

    void getNotes(Callback<List<Note>> callback);

    void add(String title, String text, Callback<Note> callback);

    void delete(Note note, Callback<Void> callback);

    void update(String id, String newTitle, String newText, Callback<Note> callback);

}
