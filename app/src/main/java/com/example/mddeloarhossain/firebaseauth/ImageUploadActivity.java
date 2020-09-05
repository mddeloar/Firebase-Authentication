package com.example.mddeloarhossain.firebaseauth;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ImageUploadActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private Button chooseButton, saveButton, skipButton;
    EditText sliderImageName, sliderDescription, sliderDescriptionNo;
    private ImageView imageView;
    private Uri imageUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;
    ProgressDialog progressDialog;
    int i=0;
    private static final int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        databaseReference = FirebaseDatabase.getInstance().getReference("sliderimages");
        storageReference = FirebaseStorage.getInstance().getReference("sliderimages");

        chooseButton = findViewById(R.id.chooseImageButton);
        saveButton = findViewById(R.id.saveImageButton);
        imageView = findViewById(R.id.imageViewId);
        sliderImageName = findViewById(R.id.slider_image_name);
        sliderDescription = findViewById(R.id.slider_image_desc);
        sliderDescriptionNo = findViewById(R.id.slider_image_desc_no);

        chooseButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.chooseImageButton:
                openFileChooser();

                break;

            case R.id.saveImageButton:
                progressDialog = new ProgressDialog(ImageUploadActivity.this);
                progressDialog.setTitle("Saving information!");
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                if(uploadTask !=null && uploadTask.isInProgress()){
                    Toast.makeText(getApplicationContext(),"Uploading is in progress",Toast.LENGTH_SHORT).show();

                }else{
                    saveData();

                }
                break;

        }

    }

    void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(imageView);
        }
    }

    //getting the extesion of the image
    public String getFileExtension(Uri imageUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));

    }

    public void saveData() {

        try {
            StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Get a URL to the uploaded content

                            try{
                            //i=i+1;
                                //String imageName = "Hello"+i;
                                String imageName = sliderImageName.getText().toString();
                                String description = sliderDescription.getText().toString();
                                String descriptionNo = sliderDescriptionNo.getText().toString();
                                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urlTask.isSuccessful()) ;
                                Uri downloadUri = urlTask.getResult();

                                AdapterImageUpload upload = new AdapterImageUpload(description, downloadUri.toString());
                                //databaseReference.child(uploadId).setValue(upload);
                                //databaseReference.child(imageName).setValue(upload.getImageName());
                                databaseReference.child(imageName).setValue(upload.getImageUrl());
                                databaseReference.child(descriptionNo).setValue(upload.getImageName());
                                Toast.makeText(getApplicationContext(), "Successfully image stored.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent intent1 = (new Intent(getApplicationContext(), ImageUploadActivity.class));
                                startActivity(intent1);
                            }catch(Exception e){
                                Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();


                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Toast.makeText(getApplicationContext(), " Image not stored.", Toast.LENGTH_SHORT).show();
                        }
                    });


        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();


        }
    }
}
