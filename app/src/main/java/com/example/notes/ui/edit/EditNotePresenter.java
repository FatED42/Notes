package com.example.notes.ui.edit;

import android.os.Bundle;

import com.example.notes.R;
import com.example.notes.domain.Callback;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepo;

public class EditNotePresenter extends AbstractNotePresenter {

    public static final String KEY_UPDATE = "KEY_UPDATE";

    private final Note toEdit;

    public EditNotePresenter(EditNoteView view, NotesRepo repo, Note note) {
        super(view, repo);
        toEdit = note;

        title = toEdit.getTitle();
        text = toEdit.getText();
    }

    @Override
    void refresh() {
        editNoteView.setButtonTitle(R.string.btn_update);
        editNoteView.setNoteTitle(toEdit.getTitle());
        editNoteView.setNoteText(toEdit.getText());
    }

    @Override
    void onActionButtonClicked() {
        editNoteView.showProgress();

        repo.update(toEdit.getId(), title, text, new Callback<Note>() {
            @Override
            public void onSuccess(Note data) {
                editNoteView.hideProgress();

                Bundle bundle = new Bundle();
                bundle.putParcelable(EditNoteBottomSheetFragment.ARG_NOTE, data);

                editNoteView.publishResult(KEY_UPDATE, bundle);
            }
        });
    }

}
