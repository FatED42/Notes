package com.example.notes.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.domain.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class NotesRVAdapter extends RecyclerView.Adapter<NotesRVAdapter.NoteViewHolder> {

    public OnNoteClicked getOnNoteClicked() {
        return onNoteClicked;
    }

    public void setOnNoteClicked(OnNoteClicked onNoteClicked) {
        this.onNoteClicked = onNoteClicked;
    }

    interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    private List<Note> data = new ArrayList<>();
    private SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

    public void setData(Collection<Note> toSet) {
        data.clear();
        data.addAll(toSet);
    }

    private OnNoteClicked onNoteClicked;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note item = data.get(position);

        holder.noteTitle.setText(item.getTitle());
        holder.noteText.setText(item.getText());
        holder.noteDate.setText(format.format(item.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle;
        TextView noteText;
        TextView noteDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.findViewById(R.id.note_card).setOnClickListener(view -> {
                if (getOnNoteClicked() != null) {
                    int clickedAt = getAdapterPosition();
                    getOnNoteClicked().onNoteClicked(data.get(clickedAt));
                }
            });

            noteTitle = itemView.findViewById(R.id.note_title);
            noteText = itemView.findViewById(R.id.note_text);
            noteDate = itemView.findViewById(R.id.note_date);
        }

    }

}
