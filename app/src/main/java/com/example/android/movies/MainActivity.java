package com.example.android.movies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String[] movies = {"JAWS", "Airplane!", "Raiders of the Lost Ark", "Ghostbusters", "Groundhog Day", "Dumb and Dumber"};
    private ListView listView1;

    public static ArrayList<String> Titles;
    public static ArrayList<String> movieIDs;

    private String[] id = {"tt0073195", "tt0080339", "tt0082971", "tt0087332", "tt0107048", "tt0109686"};
    private String ID;
    private String MOVIE;

    private int deleteIndex;

    private Button addbutton;
    private Button addbutton2;
    private Button backbutton;
    private Button backbutton2;

    private TextView title;
    private TextView bigTitle;
    private TextView addtitle;
    private TextView addid;

    private EditText titleEdit;
    private EditText idEdit;

    private Handler handler;
    private AlertDialog DeletionPrompt;
    private SharedPreferences memory;


  //  Shared Preferences p = getPreferences(context.mode_private);

   // public Shared getPreferences()
   //     Int c = p.get
  //  }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memory = getPreferences(Context.MODE_PRIVATE);
        addbutton = findViewById(R.id.addbutton);
        listView1 = findViewById(R.id.listview1);
        Titles = new ArrayList<String>();
        movieIDs = new ArrayList<String>();

        for (int i = 0; i < movies.length; i++) {
            Titles.add(movies[i]);
        }
        for (int j = 0; j < id.length; j++) {
            movieIDs.add(id[j]);
        }


        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, movies);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/" + movieIDs.get(index)));
                        startActivity(intent1);
                    }
                });

        listView1.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int progress, long l) {
                        deleteIndex = progress;
                        DeletionPrompt.show();
                        return true;
                    }
                }
        );

        longPressed();

        for(int i = 6; i < memory.getInt("Additional Movies", 0)+6; i++){
            Titles.add(memory.getString("MOVIE"+i, "error"));
            movieIDs.add(memory.getString("CODE"+i, "error"));
        }
        Map<String, ?> map = memory.getAll();

    }







    public void addMovie (View v){
       Intent intent2 = new Intent(this, Add.class);
       startActivityForResult(intent2, 3);



    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        MOVIE = data.getStringExtra("Movie Title:");
        ID = data.getStringExtra("Movie ID:");
        movieIDs.add(ID);
        Titles.add(MOVIE);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, Titles);
        listView1.setAdapter(adapter);



    }







    public void longPressed(){
        AlertDialog.Builder movieDelete = new AlertDialog.Builder(MainActivity.this);
        movieDelete.setMessage("Confirm to delete?");
        movieDelete.setTitle("Remove");
        movieDelete.setNegativeButton("No Way", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        movieDelete.setPositiveButton("Let's Do It", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Titles.remove(deleteIndex);
                movieIDs.remove(deleteIndex);
                SharedPreferences.Editor edit = memory.edit();
                edit.remove("MOVIE" + deleteIndex);
                edit.remove("CODE" + deleteIndex);
                edit.putInt("Added", memory.getInt("Added Movies", 0));
                edit.apply();
                Map<String, ?> map = memory.getAll();


                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_view, Titles);
                listView1.setAdapter(adapter);



            }

        });

        DeletionPrompt = movieDelete.create();
    }


    @Override
    public void onStop(){
        super.onStop();
        SharedPreferences.Editor edit = memory.edit();
        for(int i = 6; i < Titles.size(); i++){
            edit.putString("Movie:" + i, Titles.get(i));
            edit.putString("Movie ID:" + i, movieIDs.get(i));
            edit.putInt("Additional Movies:", Titles.size()-6);
        }
        edit.apply();
    }
}

