package com.example.notes.ui.list;

import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepo;

import java.util.List;

public class NotesListPresenter {

    private final NotesListView view;
    private final NotesRepo repo;

    public NotesListPresenter(NotesListView view, NotesRepo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void requestNotes() {
        List<Note> notes = repo.getNotes();
        view.showNotes(notes);
    }
}
