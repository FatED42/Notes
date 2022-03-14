package com.example.notes.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepo;
import com.example.notes.domain.NotesRepoImpl;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NoteEditBottomSheetFragment extends BottomSheetDialogFragment {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String KEY_REQUEST = "NoteDetailsFragment_KEY_REQUEST";

    private final NotesRepo repo = NotesRepoImpl.getInstance();

    public static NoteEditBottomSheetFragment newInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        NoteEditBottomSheetFragment fragment = new NoteEditBottomSheetFragment();
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

        Note note = requireArguments().getParcelable(ARG_NOTE);

        EditText noteTitle = view.findViewById(R.id.edit_note_title);
        noteTitle.setText(note.getTitle());
        EditText noteText = view.findViewById(R.id.edit_note_text);
        noteText.setText(note.getText());

        view.findViewById(R.id.btn_save).setOnClickListener(view1 -> {
            Note updatedNote = repo.update(note.getId(), noteTitle.getText().toString(), noteText.getText().toString());

            Bundle bundle = new Bundle();
            bundle.putParcelable(ARG_NOTE, updatedNote);

            getParentFragmentManager().setFragmentResult(KEY_REQUEST, bundle);

            dismiss();
        });
    }
}
