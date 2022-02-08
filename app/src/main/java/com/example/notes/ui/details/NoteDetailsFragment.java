package com.example.notes.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.ui.list.NotesListFragment;

public class NoteDetailsFragment extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";

    public static NoteDetailsFragment newInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);

        NoteDetailsFragment fragment = new NoteDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView noteTitle;
    private TextView noteText;

    public NoteDetailsFragment() {
        super(R.layout.fragment_note_details);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteTitle = view.findViewById(R.id.note_title);
        noteText = view.findViewById(R.id.note_text);

        getParentFragmentManager()
                .setFragmentResultListener(NotesListFragment.NOTE_SELECTED, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(NotesListFragment.SELECTED_NOTE_BUNDLE);

                        updateNote(note);
                    }
                });

        Bundle arguments = getArguments();

        if (arguments != null && arguments.containsKey(ARG_NOTE)) {
            Note note = arguments.getParcelable(ARG_NOTE);
            updateNote(note);
        }

    }

    private void updateNote(Note note) {
        noteTitle.setText(note.getTitle());
        noteText.setText(note.getText());
    }
}
