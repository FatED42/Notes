package com.example.notes.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotesRepoImpl implements NotesRepo {

    private static final NotesRepo INSTANCE = new NotesRepoImpl();

    public static NotesRepo getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();

        notes.add(new Note(UUID.randomUUID().toString(), "Первая заметка", "Текст первой заметки. Не очень длинный, но интересный", "5 февраля 2022"));
        notes.add(new Note(UUID.randomUUID().toString(), "Вторая заметка", "Текст второй заметки. Не очень длинный, но интересный", "4 февраля 2022"));
        notes.add(new Note(UUID.randomUUID().toString(), "Третья заметка", "Текст третьей заметки. Не очень длинный, но интересный", "3 февраля 2022"));
        notes.add(new Note(UUID.randomUUID().toString(), "Четвертая заметка", "Текст четвертой заметки. Не очень длинный, но интересный", "2 февраля 2022"));
        notes.add(new Note(UUID.randomUUID().toString(), "Пятая заметка", "Текст пятой заметки. Не очень длинный, но интересный", "1 февраля 2022"));
        notes.add(new Note(UUID.randomUUID().toString(), "Шестая заметка", "Текст шестой заметки. Не очень длинный, но интересный", "29 января 2022"));
        notes.add(new Note(UUID.randomUUID().toString(), "Седьмая заметка", "Текст седьмой заметки. Не очень длинный, но интересный", "25 января 2022"));

        return notes;
    }
}
