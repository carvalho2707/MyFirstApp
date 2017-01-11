package pt.tiagocarvalho.myfirstapp;

import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import pt.tiagocarvalho.myfirstapp.utils.Constants;
import pt.tiagocarvalho.myfirstapp.adapter.DataAdapter;
import pt.tiagocarvalho.myfirstapp.model.User;
import pt.tiagocarvalho.myfirstapp.utils.Utils;

public class ResultsActivity extends AppCompatActivity {
    private String personName;
    private String personState;
    private int minAge;
    private int maxAge;
    private String techList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<User> userArrayList = generateData();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            personName = extras.getString(Constants.PERSON_NAME);
            personState = extras.getString(Constants.PERSON_STATE);
            minAge = extras.getInt(Constants.PERSON_AGE_MIN);
            maxAge = extras.getInt(Constants.PERSON_AGE_MAX);
            techList = extras.getString(Constants.PERSON_TECH);
            userArrayList = Utils.filterData(userArrayList, personName, minAge, maxAge, techList, personState);
        }


        setContentView(R.layout.activity_main);


        //create the view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        DataAdapter adapter = new DataAdapter(userArrayList, new DataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User item) {
                Intent intent = new Intent(getApplicationContext(), DetailedActivity.class);
                Gson gson = new Gson();
                intent.putExtra(Constants.SELECTED_USER, gson.toJson(item, User.class));
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

    private ArrayList<User> generateData() {
        ArrayList<User> userArrayList = new ArrayList<>();
        Random rand = new Random();
        ArrayList<String> list1 = new ArrayList<String>() {{
            add("Java");
            add("SQL");
            add("Android");
        }};
        ArrayList<String> list2 = new ArrayList<String>() {{
            add("Android");
        }};

        ContentResolver cr = ResultsActivity.this.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {

                User user = new User();
                String id = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                user.setName(name);
                user.setAge(20 + rand.nextInt(50));
                int idPic = rand.nextInt(3);
                if (idPic == 0) {
                    user.setImageId(1);
                } else if (idPic == 1) {
                    user.setImageId(2);
                } else {
                    user.setImageId(3);
                }
                if (!TextUtils.isEmpty(name)) {
                    userArrayList.add(user);
                }
                idPic = rand.nextInt(2);
                if (idPic == 0) {
                    user.setTechList(list1);
                } else if (idPic == 1) {
                    user.setTechList(list2);
                }
            }
        }
        Collections.sort(userArrayList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return userArrayList;
    }

}
