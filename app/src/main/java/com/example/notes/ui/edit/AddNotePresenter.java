package com.example.notes.ui.edit;

import android.os.Bundle;

import com.example.notes.R;
import com.example.notes.domain.NotesRepo;

public class AddNotePresenter extends AbstractNotePresenter {

    public static final String KEY_ADD = "KEY_ADD";

    public AddNotePresenter(EditNoteView view, NotesRepo repo) {
        super(view, repo);
    }

    @Override
    void refresh() {
        editNoteView.setButtonTitle(R.string.btn_save);
        editNoteView.setActionButtonEnabled(false);
    }

    @Override
    void onActionButtonClicked() {
        editNoteView.showProgress();
        repo.add(title, text, data -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(EditNoteBottomSheetFragment.ARG_NOTE, data);

            editNoteView.publishResult(KEY_ADD, bundle);
            editNoteView.hideProgress();
        });
    }
}
