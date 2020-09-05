package com.example.mddeloarhossain.firebaseauth;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewFlipper viewFlipper;

    private Uri imageUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;
    ProgressDialog progressDialog;
    int i=0;
    private String ImageURL1,ImageURL2,ImageURL3, ImageURL4, Desc1, Desc2 ,Desc3, Desc4;
    private ImageView img1, img2, img3, img4;
    private TextView txt1, txt2, txt3, txt4;
    private static final int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseReference = FirebaseDatabase.getInstance().getReference("sliderimages");
        storageReference = FirebaseStorage.getInstance().getReference("sliderimages");



        viewFlipper = findViewById(R.id.view_flipper_id);
        //int images[]= {R.drawable.jonakipic1, R.drawable.jonakipic2, R.drawable.jonakipic3, R.drawable.jonakipic4};
        //Toast.makeText(this,"activity_auto_image_slider_by_view_flipper",Toast.LENGTH_SHORT).show();

        /*for (int image: images) {
            //Toast.makeText(this,"for loop",Toast.LENGTH_SHORT).show();
            flipperImages(image);

        }*/

        img1 = findViewById(R.id.slider_image_view1);
        img2 = findViewById(R.id.slider_image_view2);
        img3 = findViewById(R.id.slider_image_view3);
        img4 = findViewById(R.id.slider_image_view4);

        txt1 = findViewById(R.id.slider_text_view1);
        txt2 = findViewById(R.id.slider_text_view2);
        txt3 = findViewById(R.id.slider_text_view3);
        txt4 = findViewById(R.id.slider_text_view4);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                try {

                    ImageURL1 = dataSnapshot.child("SliderNo1").getValue().toString();
                    Desc1 = dataSnapshot.child("DescNo1").getValue().toString();
                    txt1.setText(Desc1);

                    Picasso.with(getApplicationContext())
                            .load(ImageURL1)
                            .placeholder(R.drawable.jonakipic1)
                            .fit()
                            .centerCrop()
                            .into(img1);

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();

                    //Toast.makeText(this, "Your profile image is not set", Toast.LENGTH_SHORT).show();
                }
                try {

                    ImageURL2 = dataSnapshot.child("SliderNo2").getValue().toString();
                    Desc2 = dataSnapshot.child("DescNo2").getValue().toString();
                    txt2.setText(Desc2);

                    Picasso.with(getApplicationContext())
                            .load(ImageURL2)
                            .placeholder(R.drawable.jonakipic2)
                            .fit()
                            .centerCrop()
                            .into(img2);

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();

                    //Toast.makeText(this, "Your profile image is not set", Toast.LENGTH_SHORT).show();
                }
                try {

                    ImageURL3 = dataSnapshot.child("SliderNo3").getValue().toString();
                    Desc3 = dataSnapshot.child("DescNo3").getValue().toString();
                    txt3.setText(Desc3);

                    Picasso.with(getApplicationContext())
                            .load(ImageURL3)
                            .placeholder(R.drawable.jonakipic3)
                            .fit()
                            .centerCrop()
                            .into(img3);

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();

                    //Toast.makeText(this, "Your profile image is not set", Toast.LENGTH_SHORT).show();
                }
                try {

                    ImageURL4 = dataSnapshot.child("SliderNo4").getValue().toString();
                    Desc4 = dataSnapshot.child("DescNo4").getValue().toString();
                    txt4.setText(Desc4);

                    Picasso.with(getApplicationContext())
                            .load(ImageURL4)
                            .placeholder(R.drawable.jonakipic4)
                            .fit()
                            .centerCrop()
                            .into(img4);

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();

                    //Toast.makeText(this, "Your profile image is not set", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });



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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
            Intent intent = new Intent(this,ImageUploadActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void flipperImages(int image){
        try {
            //Toast.makeText(this,"flipperImages",Toast.LENGTH_SHORT).show();
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(image);

            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.setAutoStart(true);

            //Animation
            viewFlipper.setInAnimation(this, R.anim.slide_in_right);
            viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
        }catch (Exception e){
            Toast.makeText(this,"Error: "+e,Toast.LENGTH_SHORT).show();

        }
    }
}
