package com.example.notes.domain;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreNotesRepo implements NotesRepo {

    private static final String NOTES = "notes";
    private static final String TITLE = "title";
    private static final String TEXT = "text";
    private static final String CREATED_AT = "createdAt";

    public static final NotesRepo INSTANCE = new FirestoreNotesRepo();

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(Callback<List<Note>> callback) {

        db.collection(NOTES)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                    ArrayList<Note> result = new ArrayList<>();

                    for (DocumentSnapshot snapshot : documents) {
                        String id = snapshot.getId();
                        String title = snapshot.getString(TITLE);
                        String text = snapshot.getString(TEXT);
                        Date createdAt = snapshot.getDate(CREATED_AT);

                        result.add(new Note(id, title, text, createdAt));
                    }

                    callback.onSuccess(result);

                });
    }

    @Override
    public void add(String title, String text, Callback<Note> callback) {

        Map<String, Object> data = new HashMap<>();
        Date createdAt = new Date();

        data.put(TITLE, title);
        data.put(TEXT, text);
        data.put(CREATED_AT, createdAt);

        db.collection(NOTES)
                .add(data)
                .addOnSuccessListener(documentReference -> {

                    String id = documentReference.getId();

                    callback.onSuccess(new Note(id, title, text, createdAt));

                });
    }

    @Override
    public void delete(Note note, Callback<Void> callback) {

        db.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(unused -> callback.onSuccess(unused));

    }

    @Override
    public void update(String id, String newTitle, String newText, Callback<Note> callback) {

        Map<String, Object> data = new HashMap<>();

        data.put(TITLE, newTitle);
        data.put(TEXT, newText);

        db.collection(NOTES)
                .document(id)
                .update(data)
                .addOnSuccessListener(unused ->
                        callback.onSuccess(new Note(id, newTitle, newText, new Date()))
                );

    }
}
