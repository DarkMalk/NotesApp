package utils;

import android.app.AlertDialog;
import android.content.Context;

import com.example.notesapp.R;

public class Dialog {
    public interface DialogCallback {
        void onResult(boolean isConfirm);
    }

    public static void showDialog(Context context, String title, String message, DialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder
                .setTitle(title)
                .setMessage(message);

        builder.setPositiveButton(
                context.getString(R.string.dialog_btn_accept),
                (dialog, which) -> { callback.onResult(true); }
        );

        builder.setNegativeButton(
                context.getString(R.string.dialog_btn_cancel),
                (dialog, which) -> { callback.onResult(false); }
        );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
