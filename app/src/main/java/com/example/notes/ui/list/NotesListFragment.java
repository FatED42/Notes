package com.example.notes.ui.list;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepoImpl;
import com.example.notes.ui.NavDrawable;
import com.example.notes.ui.details.NoteDetailsFragment;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String NOTE_SELECTED = "NOTE_SELECTED";
    public static final String SELECTED_NOTE_BUNDLE = "SELECTED_NOTE_BUNDLE";

    private NotesListPresenter presenter;
    private RecyclerView list;
    private NotesRVAdapter adapter;
    private ProgressBar progressBar;

    public NotesListFragment() {
        super(R.layout.fragment_notes_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = view.findViewById(R.id.list);
        progressBar = view.findViewById(R.id.progress_bar);

        presenter = new NotesListPresenter(this, NotesRepoImpl.getInstance());
        adapter = new NotesRVAdapter(this);

        list.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);

        Toolbar toolbar = view.findViewById(R.id.notes_list_toolbar);
        if (requireActivity() instanceof NavDrawable) {
            ((NavDrawable) requireActivity()).setAppBar(toolbar);
        }

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.add_note) {
                presenter.addNote();
                return true;
            }
            return false;
        });

        adapter.setOnNoteClicked(new NotesRVAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(SELECTED_NOTE_BUNDLE, note);
                getParentFragmentManager()
                        .setFragmentResult(NOTE_SELECTED, bundle);
            }

            @Override
            public void onNoteLongClicked(Note note, int position) {
                presenter.setSelectedNote(note);
                presenter.setSelectedNoteIndex(position);
            }
        });

        getParentFragmentManager().setFragmentResultListener(NoteDetailsFragment.KEY_REQUEST, getViewLifecycleOwner(), (requestKey, result) -> {
            Note note = result.getParcelable(NoteDetailsFragment.ARG_NOTE);
            adapter.updateNote(note, presenter.getSelectedNoteIndex());
            adapter.notifyItemChanged(presenter.getSelectedNoteIndex());
        });

        presenter.requestNotes();
    }

    @Override
    public void showNotes(List<Note> notes) {
        adapter.setData(notes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addNote(Note note) {
        int index = adapter.addNote(note);
        adapter.notifyItemInserted(index);
        list.smoothScrollToPosition(index);
    }

    @Override
    public void removeNote(Note note, int index) {
        adapter.removeNote(index);
        adapter.notifyItemRemoved(index);
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
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.menu_notes_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            presenter.deleteNote();
        }
        return super.onContextItemSelected(item);
    }
}

