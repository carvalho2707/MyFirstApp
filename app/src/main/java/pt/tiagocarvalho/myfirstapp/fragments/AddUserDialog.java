package pt.tiagocarvalho.myfirstapp.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import pt.tiagocarvalho.myfirstapp.R;

public class AddUserDialog extends DialogFragment {
    private AddUserDialogListener mListener;
    private EditText etName;
    private EditText etMail;
    private EditText etAge;
    private ImageButton ibPicture;
    private Bitmap selectedPhoto;
    private String photoName;
    private static final int PICK_IMAGE_REQUEST = 1;

    public interface AddUserDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String name, String email, String age, String image);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_user_dialog, null);

        etName = (EditText) view.findViewById(R.id.etNameAdd);
        etMail = (EditText) view.findViewById(R.id.etEmailAdd);
        etAge = (EditText) view.findViewById(R.id.etAgeAdd);
        ibPicture = (ImageButton) view.findViewById(R.id.ibPictureAdd);

        ibPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.button_create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String path = saveToInternalStorage(selectedPhoto, photoName);
                        mListener.onDialogPositiveClick(AddUserDialog.this, etName.getText().toString(), etMail.getText().toString(), etAge.getText().toString(), path);
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddUserDialog.this.getDialog().cancel();
                    }
                });
        builder.setTitle(R.string.add_resource);
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddUserDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == -1 && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Cursor cursor = null;
            try {
                cursor = getActivity().getContentResolver().query(uri, new String[]{
                        MediaStore.Images.ImageColumns.DISPLAY_NAME
                }, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    photoName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
                }
            } finally {

                if (cursor != null) {
                    cursor.close();
                }
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                selectedPhoto = bitmap;
                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                ibPicture.setBackground(ob);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String name) {
        bitmapImage = Bitmap.createScaledBitmap(bitmapImage, 64, 64, false);
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String fullpath = directory.getPath() + File.separator + name;
        File mypath = new File(fullpath);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fullpath;
    }

}
