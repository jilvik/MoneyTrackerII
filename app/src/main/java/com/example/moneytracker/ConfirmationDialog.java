package com.example.moneytracker;

        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.DialogFragment;


public class ConfirmationDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Delete")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", (dialog1, which) -> {
                    if (getTargetFragment() != null) {
                        ((BudgetFragment) getTargetFragment()).removeSelectedItems();
                    }
                })
                .setNegativeButton("No", (dialog2, which) -> {
                    dialog2.cancel();
                })
                .create();

        return dialog;
    }
}
