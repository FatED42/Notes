package com.example.notes.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NotesRepoImpl implements NotesRepo {

    private static final NotesRepo INSTANCE = new NotesRepoImpl();

    public static NotesRepo getInstance() {
        return INSTANCE;
    }

    private Executor executor = Executors.newSingleThreadExecutor();

    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    private final List<Note> notes = new ArrayList<>();

    public NotesRepoImpl() {
        notes.add(new Note(UUID.randomUUID().toString(), "Первая заметка", "Текст первой заметки. Не очень длинный, но интересный", new Date()));
        notes.add(new Note(UUID.randomUUID().toString(), "Вторая заметка", "Текст второй заметки. Не очень длинный, но интересный", new Date()));
        notes.add(new Note(UUID.randomUUID().toString(), "Третья заметка", "Текст третьей заметки. Не очень длинный, но интересный", new Date()));
        notes.add(new Note(UUID.randomUUID().toString(), "Четвертая заметка", "Текст четвертой заметки. Не очень длинный, но интересный", new Date()));
        notes.add(new Note(UUID.randomUUID().toString(), "Пятая заметка", "Текст пятой заметки. Не очень длинный, но интересный", new Date()));
        notes.add(new Note(UUID.randomUUID().toString(), "Шестая заметка", "Текст шестой заметки. Не очень длинный, но интересный", new Date()));
        notes.add(new Note(UUID.randomUUID().toString(), "Седьмая заметка", "Текст седьмой заметки. Не очень длинный, но интересный", new Date()));
    }


    @Override
    public void getNotes(Callback<List<Note>> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(1500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainThreadHandler.post(() -> callback.onSuccess(notes));
        });
    }

    @Override
    public void add(String title, String text, Callback<Note> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(1500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Note note = new Note(UUID.randomUUID().toString(), title, text, new Date());
            notes.add(note);
            mainThreadHandler.post(() -> callback.onSuccess(note));
        });
    }

    @Override
    public void delete(Note note, Callback<Void> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(1500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notes.remove(note);
            mainThreadHandler.post(() -> callback.onSuccess(null));
        });
    }

    @Override
    public void update(String id, String newTitle, String newText, Callback<Note> callback) {
        Note toChangeNote = null;
        int indexToChangeNote = -1;

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId().equals(id)) {
                toChangeNote = notes.get(i);
                indexToChangeNote = i;
                break;
            }
        }

        Note newNote = new Note(toChangeNote.getId(), newTitle, newText, toChangeNote.getCreatedAt());
        notes.set(indexToChangeNote, newNote);
        callback.onSuccess(newNote);
    }
}
