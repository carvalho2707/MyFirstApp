package pt.tiagocarvalho.myfirstapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;

import pt.tiagocarvalho.myfirstapp.adapter.DataAdapter;
import pt.tiagocarvalho.myfirstapp.db.DBHelper;
import pt.tiagocarvalho.myfirstapp.model.Recurso;
import pt.tiagocarvalho.myfirstapp.utils.Constants;

public class ResultsActivity extends AppCompatActivity {
    private String personName;
    private String personState;
    private int minAge;
    private int maxAge;
    private String techList;
    private DBHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDb = new DBHelper(this);

        ArrayList<Recurso> recursoList = null;
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            personName = extras.getString(Constants.PERSON_NAME);
            personState = extras.getString(Constants.PERSON_STATE);
            minAge = extras.getInt(Constants.PERSON_AGE_MIN);
            maxAge = extras.getInt(Constants.PERSON_AGE_MAX);
            techList = extras.getString(Constants.PERSON_TECH);
            recursoList = myDb.queryRecursos(personName, minAge, maxAge);
        }

        setContentView(R.layout.activity_results);


        //create the view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        DataAdapter adapter = new DataAdapter(recursoList, new DataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recurso item) {
                Intent intent = new Intent(getApplicationContext(), DetailedActivity.class);
                Gson gson = new Gson();
                intent.putExtra(Constants.SELECTED_RECURSO, gson.toJson(item, Recurso.class));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(ResultsActivity.this).toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(Constants.PERSON_NAME, personName);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
