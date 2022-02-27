package com.example.notes.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepo;
import com.example.notes.domain.NotesRepoImpl;
import com.example.notes.ui.NavDrawable;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String NOTE_SELECTED = "NOTE_SELECTED";
    public static final String SELECTED_NOTE_BUNDLE = "SELECTED_NOTE_BUNDLE";

    private NotesRepo repo = new NotesRepoImpl();
    private NotesListPresenter presenter;
    private RecyclerView list;

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

        list = view.findViewById(R.id.list);

        Toolbar toolbar = view.findViewById(R.id.notes_list_toolbar);
        if (requireActivity() instanceof NavDrawable) {
            ((NavDrawable) requireActivity()).setAppBar(toolbar);
        }

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.add_note) {
                Toast.makeText(requireContext(), "Note added", Toast.LENGTH_LONG).show();
                return true;
            }
            return false;
        });

        presenter.requestNotes();
    }

    @Override
    public void showNotes(List<Note> notes) {

        list.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        NotesRVAdapter adapter = new NotesRVAdapter();
        adapter.setOnNoteClicked(note -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(SELECTED_NOTE_BUNDLE, note);

            getParentFragmentManager()
                    .setFragmentResult(NOTE_SELECTED, bundle);
        });

        list.setAdapter(adapter);
        adapter.setData(repo.getNotes());
        adapter.notifyDataSetChanged();
    }
}

