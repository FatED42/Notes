package com.example.notes.ui.edit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.R;
import com.example.notes.domain.FirestoreNotesRepo;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepo;
import com.example.notes.domain.NotesRepoImpl;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EditNoteBottomSheetFragment extends BottomSheetDialogFragment implements EditNoteView {

    public static final String ARG_NOTE = "ARG_NOTE";

    private Button actionButton;
    private EditText noteTitle;
    private EditText noteText;
    private ProgressBar progressBar;
    private AbstractNotePresenter presenter;

    public static EditNoteBottomSheetFragment newAddInstance() {
        return new EditNoteBottomSheetFragment();
    }

    public static EditNoteBottomSheetFragment newUpdateInstance(Note note) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        EditNoteBottomSheetFragment fragment = new EditNoteBottomSheetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        actionButton = view.findViewById(R.id.action_button);
        noteTitle = view.findViewById(R.id.edit_note_title);
        noteText = view.findViewById(R.id.edit_note_text);
        progressBar = view.findViewById(R.id.progress_bar);

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.onTitleChanged(editable.toString());
            }
        });

        noteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.onTextChanged(editable.toString());
            }
        });

        actionButton.setOnClickListener(view1 -> presenter.onActionButtonClicked());

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            Note note = requireArguments().getParcelable(ARG_NOTE);
            presenter = new EditNotePresenter(this, FirestoreNotesRepo.INSTANCE, note);
        } else {
            presenter = new AddNotePresenter(this, FirestoreNotesRepo.INSTANCE);
        }

        presenter.refresh();
    }

    @Override
    public void setButtonTitle(int title) {
        actionButton.setText(title);
    }

    @Override
    public void setNoteTitle(String title) {
        noteTitle.setText(title);
    }

    @Override
    public void setNoteText(String text) {
        noteText.setText(text);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setActionButtonEnabled(boolean isEnabled) {
        actionButton.setEnabled(isEnabled);
    }

    @Override
    public void publishResult(String key, Bundle bundle) {
        getParentFragmentManager().setFragmentResult(key, bundle);
        dismiss();
    }
}
