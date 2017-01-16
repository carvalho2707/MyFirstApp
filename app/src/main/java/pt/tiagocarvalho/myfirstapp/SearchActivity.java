package pt.tiagocarvalho.myfirstapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import pt.tiagocarvalho.myfirstapp.db.DBHelper;
import pt.tiagocarvalho.myfirstapp.fragments.AddUserDialog;
import pt.tiagocarvalho.myfirstapp.model.Recurso;
import pt.tiagocarvalho.myfirstapp.utils.Constants;
import pt.tiagocarvalho.myfirstapp.views.TagEditText;

public class SearchActivity extends AppCompatActivity implements AddUserDialog.AddUserDialogListener {
    private RangeSeekBar<Integer> rangeSeekBar;
    private Button btnSearch;
    private EditText etPersonName;
    private TagEditText etTecnologias;
    private Spinner sState;
    private DBHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_search);

        myDb = new DBHelper(this);

        rangeSeekBar = (RangeSeekBar<Integer>) findViewById(R.id.rsbAge);
        rangeSeekBar.setSelectedMinValue(0);
        rangeSeekBar.setSelectedMaxValue(100);

        etPersonName = (EditText) findViewById(R.id.etPersonName);
        etTecnologias = (TagEditText) findViewById(R.id.etTecnologias);
        sState = (Spinner) findViewById(R.id.spinnerState);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                intent.putExtra(Constants.PERSON_NAME, etPersonName.getText().toString());
                intent.putExtra(Constants.PERSON_STATE, sState.getSelectedItem().toString());
                intent.putExtra(Constants.PERSON_AGE_MIN, rangeSeekBar.getSelectedMinValue());
                intent.putExtra(Constants.PERSON_AGE_MAX, rangeSeekBar.getSelectedMaxValue());
                intent.putExtra(Constants.PERSON_TECH, etTecnologias.getText().toString());
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new AddUserDialog();
                newFragment.show(getSupportFragmentManager(), "missiles");
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name, String email, String age, String image) {
        // User touched the dialog's positive button
        Recurso recurso = new Recurso(name, email, Integer.valueOf(age), image);
        myDb.addRecurso(recurso);
    }

}
