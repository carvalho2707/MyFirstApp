package pt.tiagocarvalho.myfirstapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pt.tiagocarvalho.myfirstapp.fragments.EducationFragment;
import pt.tiagocarvalho.myfirstapp.fragments.OverviewFragment;
import pt.tiagocarvalho.myfirstapp.fragments.ProjectsFragment;
import pt.tiagocarvalho.myfirstapp.model.User;
import pt.tiagocarvalho.myfirstapp.utils.Constants;

public class DetailedActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.vpDetailsView);
        tabLayout = (TabLayout) findViewById(R.id.detailsTab);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Gson gson = new Gson();
        String result = this.getIntent().getExtras().getString(Constants.SELECTED_USER);
        User user = null;
        if (result != null) {
            user = gson.fromJson(result, User.class);
            getSupportActionBar().setTitle(user.getName());
        }

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        DetailedActivity.ViewPagerAdapter adapter = new DetailedActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverviewFragment(), "Overview");
        adapter.addFragment(new ProjectsFragment(), "Projects");
        adapter.addFragment(new EducationFragment(), "Education");
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
