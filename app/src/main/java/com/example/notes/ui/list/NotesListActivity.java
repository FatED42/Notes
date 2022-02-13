package com.example.notes.ui.list;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.ui.details.NoteDetailsFragment;

public class NotesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        getSupportFragmentManager().setFragmentResultListener(NotesListFragment.NOTE_SELECTED, this, (requestKey, result) -> {

            Note note = result.getParcelable(NotesListFragment.SELECTED_NOTE_BUNDLE);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_container, NoteDetailsFragment.newInstance(note))
                    .addToBackStack("")
                    .commit();
        });
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager()
                .popBackStack();
    }
}