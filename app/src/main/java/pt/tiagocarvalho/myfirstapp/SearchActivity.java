package pt.tiagocarvalho.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.florescu.android.rangeseekbar.RangeSeekBar;

public class SearchActivity extends AppCompatActivity {
    private RangeSeekBar<Integer> rangeSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_search_activity);

        rangeSeekBar = (RangeSeekBar<Integer>)findViewById(R.id.rsbAge);
        rangeSeekBar.setSelectedMinValue(20);
        rangeSeekBar.setSelectedMaxValue(40);
    }
}
