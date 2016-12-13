package com.bar.android.my_proj;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {
    private SongTrackerDataSource songds;
    private NextSongDataSource nexts;
    JSONArray resultSet;
    NextSongTracker nextPlay;
    String nextsong = null;
    String usergrp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usergrp = extras.getString("usergrp");
        }

        songds = new SongTrackerDataSource(this);
        nexts = new NextSongDataSource(this);
        nexts.open();
        songds.open();
    }

    public void onClick(View view) {
        //@SuppressWarnings("unchecked")
        //ArrayAdapter<UserGrpTracker> adapter = (ArrayAdapter<UserGrpTracker>) getListAdapter();
        //UserGrpTracker usergrp = null;
        /*switch (view.getId()) {
            case R.id.add:

                usergrp = usrgrpdatasource.createUserGrp("Kavin");
                adapter.add(usergrp);

                JsonHelper j = new JsonHelper();
                j.execute("Hey there");

                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    comment = (Comment) getListAdapter().getItem(0);
                    datasource.deleteComment(comment);
                    adapter.remove(comment);
                }

                PostHelper p = new PostHelper();
                p.execute("Hey there");

                break;
        }
        adapter.notifyDataSetChanged();*/
    }

    @Override
    protected void onResume() {
        songds.open();
        nexts.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        songds.close();
        nexts.close();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private class JsonHelper extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String urlString = "http://192.168.0.13:1926/testjson";
            String jsonObject = null;

            try {

                jsonObject = getJSONObjectFromURL(urlString);
                // Parse your json here

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(String result) {
            //do stuff
            //how to return a value to the calling method?
            Toast.makeText(Main2Activity.this, "Done"+result, Toast.LENGTH_SHORT).show();
        }
    }

    public void getMediaList(View view)
    {

        Toast.makeText(Main2Activity.this, "Uploading Data", Toast.LENGTH_SHORT).show();

            String DB_NAME = "music13.db";
            String myTable = "SongTrack12";


            String DB_PATH = getApplicationContext().getDatabasePath(DB_NAME).toString();
            String myPath = DB_PATH;

            Toast.makeText(Main2Activity.this, myPath, Toast.LENGTH_SHORT).show();
            SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

            String searchQuery = "SELECT  * FROM " + myTable;// + " WHERE UserGroup = " + usergrp;
            Cursor cursor = myDataBase.rawQuery(searchQuery, null );
            resultSet     = new JSONArray();
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {

                int totalColumn = cursor.getColumnCount();
                JSONObject rowObject = new JSONObject();

                for( int i=0 ;  i< totalColumn ; i++ )
                {
                    if( cursor.getColumnName(i) != null )
                    {
                        try
                        {
                            if( cursor.getString(i) != null )
                            {
                                Log.d("TAG_NAME", cursor.getString(i) );
                                rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                            }
                            else
                            {
                                rowObject.put( cursor.getColumnName(i) ,  "" );
                            }
                        }
                        catch( Exception e )
                        {
                            Log.d("TAG_NAME", e.getMessage()  );
                        }
                    }
                }
                resultSet.put(rowObject);
                cursor.moveToNext();
            }
            cursor.close();
            Log.d("TAG_NAME", resultSet.toString());

            PostHelper p = new PostHelper();
            p.execute("Hey there");
            //return resultSet;
    }



    public void MusicOpen(View view)
    {
        Bundle sendBundle = new Bundle();
        sendBundle.putString("usergrp", usergrp);
        sendBundle.putString("nextsong",nextsong);
        Intent i = new Intent(Main2Activity.this, AndroidMediaPlayer.class);
        i.putExtras(sendBundle);
        startActivity(i);
        Toast.makeText(Main2Activity.this, "Opening Player", Toast.LENGTH_SHORT).show();
    }

    public void UpdateDuration(View view)
    {
        int duration = 11;
        int Count = 0;
        if(duration > 10)
            Count = 1;
        songds.updateDuration(usergrp,Count,duration,nextPlay.getSongId());
        songds.getAllSongs();

    }

    public void playNext(View view)
    {
        nextPlay = nexts.getNextSong(usergrp);
        nextsong=nextPlay.getUserGrp();
        if(nextPlay == null)
            Toast.makeText(Main2Activity.this, "Opening Player Failed. Fill Recommendation", Toast.LENGTH_SHORT).show();
        else{
            nexts.deleteNextSong(nextPlay);
        }
    }

    public void NextSongUpt(View view)
    {
        try {
            String TAG = "Audio";
            Cursor cur = getContentResolver().query(
                    MediaStore.Audio.Media.INTERNAL_CONTENT_URI , null, null, null,
                    null);
            if (cur == null) {
                Log.e(TAG, "Failed to retrieve music: cursor is null :-(");
            }
            else if (!cur.moveToFirst()) {
                Log.e(TAG, "Failed to move cursor to first row (no query results).");
            }else {
                Log.i(TAG, "Listing...");
                MediaMetadataRetriever md = new MediaMetadataRetriever();
                do {
                    int filePathIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    Boolean value=false;
                    //if(cur.getString(filePathIndex).toLowerCase().contains("mp3".toLowerCase()))
                        value=songds.CheckSong(cur.getString(filePathIndex),usergrp);
                    //else
                    //    value=true;
                    md.setDataSource(cur.getString(filePathIndex));
                    String songyear = md.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);
                    if ( songyear == null )
                        songyear = "0000";
                    if(value==false) {
                        int artistColumn = cur.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                        int titleColumn = cur.getColumnIndex(MediaStore.Audio.Media.TITLE);
                        songds.createSongEntry(0, usergrp, cur.getString(filePathIndex), String.valueOf(titleColumn), 0, cur.getString(artistColumn), Integer.parseInt(songyear));
                        nexts.createSongEntry(cur.getString(filePathIndex), usergrp);
                        Log.v("Check Status : "+value,"Inserted");
                    }
                    else
                        Log.v("Check Status : "+ value,"Not Inserted");

                } while (cur.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //songds.getAllSongs();
    }


    public static String getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);
        urlConnection.setDoOutput(true);
        urlConnection.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        char[] buffer = new char[1024];
        String jsonString = new String();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);
        return jsonString;
    }


    private class PostHelper extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            Boolean jsonObject=null;
            String urlString = "http://192.168.43.252:1926/testpost";

            //JSONObject cred = new JSONObject();
            JSONArray cred = resultSet;

              /*  try {
                    cred.put("id", "Kavin");
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                jsonObject = excutePost(urlString, cred);
            return jsonObject.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            //do stuff
            //how to return a value to the calling method?
            Toast.makeText(Main2Activity.this, "Done"+result, Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean excutePost(String targetURL, JSONArray jsonParam)
    {
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.connect();

            byte[] outputBytes = jsonParam.toString().getBytes();
            OutputStream os = connection.getOutputStream();
            os.write(outputBytes);
            os.flush();
            os.close();
            int response = connection.getResponseCode();
            if (response >= 200 && response <=399){
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (connection.getInputStream())));
                String output;
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }


}