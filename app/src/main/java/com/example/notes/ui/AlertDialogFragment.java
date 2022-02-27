package com.example.notes.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.notes.R;

public class AlertDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.dialog_delete_title)
                .setIcon(R.drawable.app_logo)
                .setPositiveButton(R.string.button_yes, (dialogInterface, i) ->
                        Toast.makeText(requireContext(), R.string.note_deleted, Toast.LENGTH_SHORT).show())
                .setNegativeButton(R.string.button_no, (dialogInterface, i) ->
                        Toast.makeText(requireContext(), R.string.note_not_deleted, Toast.LENGTH_SHORT).show())
                .create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }
}
