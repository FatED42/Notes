package com.example.notes.ui.list;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepoImpl;
import com.example.notes.ui.details.NoteDetailsActivity;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String NOTE_SELECTED = "NOTE_SELECTED";
    public static final String SELECTED_NOTE_BUNDLE = "SELECTED_NOTE_BUNDLE";

    private LinearLayout container;

    private NotesListPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesListPresenter(this, NotesRepoImpl.getInstance());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        container = view.findViewById(R.id.container);

        presenter.requestNotes();
    }

    @Override
    public void showNotes(List<Note> notes) {
        for (Note note : notes) {
            View itemView = getLayoutInflater().inflate(R.layout.item_note, container, false);

            itemView.setOnClickListener(view -> {

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(SELECTED_NOTE_BUNDLE, note);

                    getParentFragmentManager()
                            .setFragmentResult(NOTE_SELECTED, bundle);
                } else {
                    NoteDetailsActivity.show(requireContext(), note);
                }
            });

            TextView noteTitle = itemView.findViewById(R.id.note_title);
            noteTitle.setText(note.getTitle());

            TextView noteText = itemView.findViewById(R.id.note_text);
            noteText.setText(note.getText());

            TextView noteDate = itemView.findViewById(R.id.note_date);
            noteDate.setText(note.getDate());

            container.addView(itemView);
        }
    }
}
