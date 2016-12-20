package pt.tiagocarvalho.myfirstapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;

import pt.tiagocarvalho.myfirstapp.utils.Constants;
import pt.tiagocarvalho.myfirstapp.adapter.DataAdapter;
import pt.tiagocarvalho.myfirstapp.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<User> userArrayList = generateData();
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            String personName = extras.getString(Constants.PERSON_NAME);
            String personState = extras.getString(Constants.PERSON_STATE);
            int minAge = extras.getInt(Constants.PERSON_AGE_MIN);
            int maxAge = extras.getInt(Constants.PERSON_AGE_MAX);
            String techList = extras.getString(Constants.PERSON_TECH);
            userArrayList = filterData(userArrayList, personName, minAge, maxAge, techList, personState);
        }

        if (userArrayList.size() == 0) {
            setContentView(R.layout.activity_main_no_results);
        } else {
            setContentView(R.layout.activity_main);
        }

        //create the view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DataAdapter adapter = new DataAdapter(userArrayList, new DataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User item) {
                Intent intent = new Intent(getApplicationContext(), DetailedActivity.class);
                Gson gson = new Gson();
                intent.putExtra(Constants.SELECTED_USER, gson.toJson(item, User.class));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);
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

    private ArrayList<User> generateData() {
        ArrayList<User> userArrayList = new ArrayList<>();

        ArrayList<String> list = new ArrayList<String>() {{
            add("Java");
            add("SQL");
            add("Android");
        }};
        User user = new User();
        user.setName("Tiago Carvalho");
        user.setAge(23);
        user.setTechList(list);
        user.setImageId(1);
        userArrayList.add(user);

        list = new ArrayList<String>() {{
            add("Java");
        }};
        user = new User();
        user.setName("João Carvalho");
        user.setAge(20);
        user.setTechList(list);
        user.setImageId(2);
        userArrayList.add(user);

        list = new ArrayList<String>() {{
            add("SQL");
            add("DataMining");
            add("JS");
        }};
        user = new User();
        user.setName("Filipe Carvalho");
        user.setAge(25);
        user.setImageId(3);
        user.setTechList(list);
        userArrayList.add(user);

        list = new ArrayList<String>() {{
            add("Management");
        }};
        user = new User();
        user.setName("Sebastião Carvalho");
        user.setAge(50);
        user.setTechList(list);
        user.setImageId(1);
        userArrayList.add(user);

        list = new ArrayList<String>() {{
            add("Java");
            add("SQL");
        }};
        user = new User();
        user.setName("Andre Carvalho");
        user.setAge(30);
        user.setTechList(list);
        user.setImageId(2);
        userArrayList.add(user);

        list = new ArrayList<String>() {{
            add("Java");
            add("SQL");
        }};
        user = new User();
        user.setName("Mario Carvalho");
        user.setAge(26);
        user.setTechList(list);
        user.setImageId(3);
        userArrayList.add(user);

        list = new ArrayList<String>() {{
            add("Java");
            add("SQL");
        }};
        user = new User();
        user.setName("Abilio Carvalho");
        user.setAge(37);
        user.setTechList(list);
        user.setImageId(1);
        userArrayList.add(user);

        list = new ArrayList<String>() {{
            add("Java");
            add("SQL");
        }};
        user = new User();
        user.setName("Gertudes Carvalho");
        user.setAge(27);
        user.setTechList(list);
        user.setImageId(2);
        userArrayList.add(user);

        list = new ArrayList<String>() {{
            add("Java");
            add("SQL");
        }};
        user = new User();
        user.setName("Sofia Carvalho");
        user.setAge(22);
        user.setTechList(list);
        user.setImageId(3);
        userArrayList.add(user);

        list = new ArrayList<String>() {{
            add("Java");
            add("SQL");
        }};
        user = new User();
        user.setName("Ricardo Carvalho");
        user.setAge(29);
        user.setTechList(list);
        user.setImageId(1);
        userArrayList.add(user);

        return userArrayList;
    }

    public ArrayList<User> filterData(ArrayList<User> userList, String personName, int minAge, int maxAge, String techList, String state) {
        ArrayList<User> userFiltered = new ArrayList<>();
        for (User user : userList) {
            boolean nameOk = true;
            if (!TextUtils.isEmpty(personName)) {
                if (!personName.equals(user.getName())) {
                    nameOk = false;
                }
            }
            boolean techOk = true;
            if (!TextUtils.isEmpty(techList)) {
                String[] techs = techList.split(",");
                for (String tech : techs) {
                    if (!user.getTechList().contains(tech)) {
                        techOk = false;
                    }
                }
            }

            boolean ageOk = true;
            if (user.getAge() < minAge || user.getAge() > maxAge) {
                ageOk = false;
            }
            if (nameOk && techOk && ageOk) {
                userFiltered.add(user);
            }
        }
        return userFiltered;
    }

}
