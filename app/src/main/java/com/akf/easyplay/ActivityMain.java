package com.akf.easyplay;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {

    private final String MyLog = "MyLog";
    private  static final int MY_PERMISSION_REQUEST = 1;
    ArrayList<String> arrayList;
    ImageButton Next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Next = findViewById(R.id.Next);



        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(MyLog, "Clicked!");
                if(ContextCompat.checkSelfPermission(ActivityMain.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(ActivityMain.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)){
                        ActivityCompat.requestPermissions(ActivityMain.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
                    } else {
                        ActivityCompat.requestPermissions(ActivityMain.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
                    }
                } else {
                    doStuff();
                }
            }
        });
    }



    public void doStuff(){
        getMusic();
        Log.v(MyLog,String.valueOf(arrayList.size()));
        for(int i = 0; i < arrayList.size(); i++){
            Log.v(MyLog, arrayList.get(i));
        }
    }

    public void getMusic(){
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri,null,null,null,null);
        arrayList = new ArrayList<>();

        if(songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songLocation = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentLocation = songCursor.getString(songLocation);
                arrayList.add(currentTitle + "\n" + currentArtist);
            } while (songCursor.moveToNext());
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSION_REQUEST:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(ActivityMain.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"Permission granted!", Toast.LENGTH_SHORT).show();
                        doStuff();
                    }
                } else {
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }

    }


}
