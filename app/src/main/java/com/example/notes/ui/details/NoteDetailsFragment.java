package com.example.notes.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepo;
import com.example.notes.domain.NotesRepoImpl;
import com.example.notes.ui.NavDrawable;

public class NoteDetailsFragment extends Fragment {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String KEY_REQUEST = "NoteDetailsFragment_KEY_REQUEST";
    private EditText noteTitle;
    private EditText noteText;

    private final NotesRepo repo = NotesRepoImpl.getInstance();

    public static NoteDetailsFragment newInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        NoteDetailsFragment fragment = new NoteDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NoteDetailsFragment() {
        super(R.layout.fragment_note_details);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.note_details_toolbar);
        if (requireActivity() instanceof NavDrawable) {
            ((NavDrawable) requireActivity()).setAppBar(toolbar);
        }

        Note note = requireArguments().getParcelable(ARG_NOTE);

        noteTitle = view.findViewById(R.id.note_title_text);
        noteTitle.setText(note.getTitle());
        noteText = view.findViewById(R.id.note_text);
        noteText.setText(note.getText());


        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.save_note) {

                Note updatedNote = repo.update(note.getId(), noteTitle.getText().toString(), noteText.getText().toString());

                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_NOTE, updatedNote);

                getParentFragmentManager()
                        .setFragmentResult(KEY_REQUEST, bundle);

            }
            return false;
        });

    }
}
