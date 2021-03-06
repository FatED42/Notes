package com.example.notes.ui.list;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.FirestoreNotesRepo;
import com.example.notes.domain.Note;
import com.example.notes.ui.edit.AddNotePresenter;
import com.example.notes.ui.edit.EditNoteBottomSheetFragment;
import com.example.notes.ui.edit.EditNotePresenter;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

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

        presenter = new NotesListPresenter(this, FirestoreNotesRepo.INSTANCE);
        adapter = new NotesRVAdapter(this);

        list.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);

        Toolbar toolbar = view.findViewById(R.id.notes_list_toolbar);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.add_note) {
                EditNoteBottomSheetFragment.newAddInstance()
                        .show(getParentFragmentManager(), "EditNoteBottomSheetFragment");
                return true;
            }
            return false;
        });

        adapter.setOnNoteClicked(new NotesRVAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoteLongClicked(Note note, int position) {
                presenter.setSelectedNote(note);
                presenter.setSelectedNoteIndex(position);
            }
        });

        getParentFragmentManager().setFragmentResultListener(EditNotePresenter.KEY_UPDATE, getViewLifecycleOwner(), (requestKey, result) -> {
            Note note = result.getParcelable(EditNoteBottomSheetFragment.ARG_NOTE);
            adapter.updateNote(note, presenter.getSelectedNoteIndex());
            adapter.notifyItemChanged(presenter.getSelectedNoteIndex());
        });

        getParentFragmentManager().setFragmentResultListener(AddNotePresenter.KEY_ADD, getViewLifecycleOwner(), (requestKey, result) -> {
            Note note = result.getParcelable(EditNoteBottomSheetFragment.ARG_NOTE);
            int index = adapter.addNote(note);
            adapter.notifyItemInserted(index);
            list.smoothScrollToPosition(index);
        });

        presenter.requestNotes();
    }

    @Override
    public void showNotes(List<Note> notes) {
        adapter.setData(notes);
        adapter.notifyDataSetChanged();
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
        switch (item.getItemId()) {
            case R.id.action_delete:
                presenter.deleteNote();
                return true;

            case R.id.action_edit:
                EditNoteBottomSheetFragment.newUpdateInstance(presenter.getSelectedNote())
                        .show(getParentFragmentManager(), "NoteEditBottomSheetFragment");
                return true;
        }
        return super.onContextItemSelected(item);
    }
}

