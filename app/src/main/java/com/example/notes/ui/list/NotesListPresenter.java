package com.example.notes.ui.list;

import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepo;

public class NotesListPresenter {

    private final NotesListView view;
    private final NotesRepo repo;

    private Note selectedNote;

    private int selectedNoteIndex;

    public NotesListPresenter(NotesListView view, NotesRepo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void requestNotes() {
        view.showProgress();

        repo.getNotes(data -> {
            view.showNotes(data);
            view.hideProgress();
        });
    }

    public void addNote() {
        view.showProgress();
        repo.add("My new note here", "Text about my new note is very important", data -> {
            view.hideProgress();
            view.addNote(data);
        });
    }

    public void deleteNote() {
        view.showProgress();
        repo.delete(selectedNote, data -> {
            view.hideProgress();
            view.removeNote(selectedNote, selectedNoteIndex);
        });
    }

    public Note getSelectedNote() {
        return selectedNote;
    }

    public void setSelectedNote(Note selectedNote) {
        this.selectedNote = selectedNote;
    }

    public int getSelectedNoteIndex() {
        return selectedNoteIndex;
    }

    public void setSelectedNoteIndex(int selectedNoteIndex) {
        this.selectedNoteIndex = selectedNoteIndex;
    }
}
