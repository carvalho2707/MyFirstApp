package pt.tiagocarvalho.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import pt.tiagocarvalho.myfirstapp.utils.Constants;
import pt.tiagocarvalho.myfirstapp.views.TagEditText;

public class SearchActivity extends AppCompatActivity {
    private RangeSeekBar<Integer> rangeSeekBar;
    private Button btnSearch;
    private EditText etPersonName;
    private TagEditText etTecnologias;
    private Spinner sState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_search);

        rangeSeekBar = (RangeSeekBar<Integer>) findViewById(R.id.rsbAge);
        rangeSeekBar.setSelectedMinValue(20);
        rangeSeekBar.setSelectedMaxValue(60);

        etPersonName = (EditText) findViewById(R.id.etPersonName);
        etTecnologias = (TagEditText) findViewById(R.id.etTecnologias);
        sState = (Spinner) findViewById(R.id.spinnerState);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(Constants.PERSON_NAME, etPersonName.getText().toString());
                intent.putExtra(Constants.PERSON_STATE, sState.getSelectedItem().toString());
                intent.putExtra(Constants.PERSON_AGE_MIN, rangeSeekBar.getSelectedMinValue());
                intent.putExtra(Constants.PERSON_AGE_MAX, rangeSeekBar.getSelectedMaxValue());
                intent.putExtra(Constants.PERSON_TECH, etTecnologias.getText().toString());
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


    }
}
