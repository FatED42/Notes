package com.example.notes.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.ui.AlertDialogFragment;
import com.example.notes.ui.NavDrawable;

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
    private EditText noteText;

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

        noteTitle = view.findViewById(R.id.note_title);
        noteText = view.findViewById(R.id.note_text);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.save_note:
                    Toast.makeText(requireContext(), "Note saved", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.delete_note:
                    new AlertDialogFragment().show(getParentFragmentManager(), "AlertDialogFragment");
                    return true;
                case R.id.share_note:
                    Toast.makeText(requireContext(), "Note shared", Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
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
