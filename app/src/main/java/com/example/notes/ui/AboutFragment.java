package com.example.notes.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;

public class AboutFragment extends Fragment {

    private ImageButton gitHubBtn;
    private ImageButton emailBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gitHubBtn = view.findViewById(R.id.githubBtn);
        emailBtn = view.findViewById(R.id.emailBtn);

        gitHubBtnClicked();
        emailBtnClicked();
    }

    private void emailBtnClicked() {
        emailBtn.setOnClickListener(v -> {
            String[] email = {getString(R.string.dev_email)};
            Uri uri = Uri.parse("mailto:");
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
            startActivity(Intent.createChooser(emailIntent, getString(R.string.send_feedback)));
            Log.i("email", "Finished sending email...");
        });
    }

    private void gitHubBtnClicked() {
        gitHubBtn.setOnClickListener(v -> {
            String url = getString(R.string.dev_github);
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }
}
