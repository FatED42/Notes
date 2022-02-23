package com.example.notes.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.ui.details.NoteDetailsFragment;
import com.example.notes.ui.list.NotesListFragment;
import com.google.android.material.navigation.NavigationView;

public class NotesActivity extends AppCompatActivity implements NavDrawable {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        drawerLayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_settings:
                    Toast.makeText(NotesActivity.this, "Settings", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.nav_about:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.notes_container, new AboutFragment())
                            .commit();
                    return true;
            }
            return false;
        });

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
    public void setAppBar(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}