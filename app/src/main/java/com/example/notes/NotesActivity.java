package com.example.notes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.ui.AboutFragment;
import com.example.notes.ui.AuthFragment;
import com.example.notes.ui.list.NotesListFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        if (savedInstanceState == null) {
            openNotes();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.action_list:
                    openNotes();
                    return true;
                case R.id.action_about_dev:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.notes_container, new AboutFragment())
                            .commit();
                    return true;
            }

            return false;
        });

        getSupportFragmentManager()
                .setFragmentResultListener(AuthFragment.KEY_AUTH, this, (requestKey, result) -> openNotes());
    }

    private void openNotes() {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_container, new AuthFragment())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_container, new NotesListFragment())
                    .commit();
        }

    }
}