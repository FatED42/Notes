package com.example.notes.ui.edit;

import android.text.TextUtils;

import com.example.notes.domain.NotesRepo;

public abstract class AbstractNotePresenter {

    protected EditNoteView editNoteView;
    protected NotesRepo repo;
    protected String title;
    protected String text;

    public AbstractNotePresenter(EditNoteView view, NotesRepo repo) {
        this.editNoteView = view;
        this.repo = repo;
    }

    public void onTitleChanged(String title) {
        this.title = title;
        editNoteView.setActionButtonEnabled(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(text));
    }

    public void onTextChanged(String text) {
        this.text = text;
        editNoteView.setActionButtonEnabled(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(text));
    }

    abstract void refresh();

    abstract void onActionButtonClicked();

}
