package pt.tiagocarvalho.myfirstapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;

import pt.tiagocarvalho.myfirstapp.utils.Constants;
import pt.tiagocarvalho.myfirstapp.model.User;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ...
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Gson gson = new Gson();
        String result = this.getIntent().getExtras().getString(Constants.SELECTED_USER);
        User user = null;
        if (result != null) {
            user = gson.fromJson(result, User.class);
            getSupportActionBar().setTitle(user.getName());
        }

    }
}
