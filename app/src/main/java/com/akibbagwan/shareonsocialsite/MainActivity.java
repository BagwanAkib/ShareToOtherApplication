package com.akibbagwan.shareonsocialsite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import in.akib.content_core.Share;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int FILE_SELECT_CODE = 1111;
    private static final int REQUEST_CODE = 2222;
    private static final String SELECTED_FILE = "File selected";
    private static final String TAG = "MainActivity";
    private ImageView imageView;
    private EditText editText;
    private Uri selectedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editText);
        findViewById(R.id.btn_image_select).setOnClickListener(this);
        findViewById(R.id.imgBtnWhats_app).setOnClickListener(this);
        findViewById(R.id.imgBtnFacebook_app).setOnClickListener(this);
        findViewById(R.id.imgBtnShare_app).setOnClickListener(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        if (savedInstanceState != null && savedInstanceState.getParcelable(SELECTED_FILE) != null) {
            selectedUri = savedInstanceState.getParcelable(SELECTED_FILE);
            imageView.setImageURI(selectedUri);
            ((TextView) findViewById(R.id.txt_path)).setText(Share.getFileNameFromURI(selectedUri, this));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SELECTED_FILE, selectedUri);
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_SELECT_CODE) {
            if (resultCode == RESULT_OK && data.getData() != null) {
                Uri uri = data.getData();
                selectedUri = uri;
                imageView.setImageURI(uri);
                ((TextView) findViewById(R.id.txt_path)).setText(Share.getFileNameFromURI(uri, this));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_image_select:
                if (isStoragePermissionGranted()) {
                    showFileChooser();
                }
                break;
            case R.id.imgBtnWhats_app:
                if (checkURI())
                    Share.shareOnWhatsApp(this, editText.getText().toString(), selectedUri, null);
                break;
            case R.id.imgBtnFacebook_app:
                if (checkURI())
                    Share.shareOnFacebookApp(this, editText.getText().toString(), selectedUri, null);
                break;
            case R.id.imgBtnShare_app:
                if (checkURI())
                    Share.shareOnOtherApp(this, editText.getText().toString(), selectedUri, null);
                break;
        }
    }

    private boolean checkURI() {
        if (selectedUri == null) {
            Toast.makeText(this, "please select Image", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }
}
