package com.example.linda.databasewithlistexample;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AddNewCountry extends Activity {
    private CountriesDbAdapter dbHelper;
    //private SimpleCursorAdapter dataAdapter;

    String strName = "";
    String strCode = "";
    String strUri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setContentView(R.layout.activity_add_new_country);
        dbHelper = new CountriesDbAdapter(this);
        dbHelper.open();

        Button saveButton = (Button) findViewById(R.id.btnAdd);
        final EditText name = (EditText) findViewById(R.id.edtName);
        final EditText uri = (EditText) findViewById(R.id.edtUri);
        final EditText countryCode = (EditText) findViewById(R.id.edtCode);

        // Apply the filters to control the input (3 letters and capital)
        ArrayList<InputFilter> curInputFilters = new ArrayList<InputFilter>(Arrays.asList(countryCode.getFilters()));
        curInputFilters.add(0, new InputFilter.LengthFilter(3));
        curInputFilters.add(1, new InputFilter.AllCaps());
        InputFilter[] newInputFilters = curInputFilters.toArray(new InputFilter[curInputFilters.size()]);
        countryCode.setFilters(newInputFilters);

        countryCode.requestFocus();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strName = name.getText().toString();
                strCode = countryCode.getText().toString();
                strUri = uri.getText().toString();
                boolean created = true;

                if (strName.equals("")) {
                    Toast.makeText(getBaseContext(), "Country name cannot be blank.", Toast.LENGTH_LONG).show();
                } else if (strName.equals("delete")) {
                    dbHelper.deleteAllCountries();
                    Toast.makeText(getBaseContext(), "Database has been deleted.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddNewCountry.this, MainActivity.class));
                }else if (strName.equals("add")) {
                    dbHelper.insertSomeCountries();
                    Toast.makeText(getBaseContext(), "Database has been populated.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddNewCountry.this, MainActivity.class));
                } else if (strUri.equals("")) {
                    Toast.makeText(getBaseContext(), "URL cannot be blank.", Toast.LENGTH_LONG).show();
                    countryCode.requestFocus();
                }else if (strCode.equals("") || (strCode.length() != 3)) {
                    Toast.makeText(getBaseContext(), "Country code cannot be blank.", Toast.LENGTH_LONG).show();
                } else if (!(Patterns.WEB_URL.matcher(strUri).matches())) {
                 //   boolean isURL = Patterns.WEB_URL.matcher(strUri).matches();
                    Toast.makeText(getBaseContext(), "URL Is Not Valid", Toast.LENGTH_LONG).show();
                } else {
                    strCode = strCode.toUpperCase();
                    created = dbHelper.createCountry(strCode, strName, strUri);
                    Log.d("CountryDB","After createCountry");
                    if (created) {
                        Toast.makeText(getBaseContext(), "New record created.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddNewCountry.this, MainActivity.class));
                        Log.d("CountryDB","Record was created.");
                    } else {
                        Toast.makeText(getBaseContext(), "Country code " + strCode + " already exists. Cannot create record.", Toast.LENGTH_LONG).show();
                        Log.d("CountryDB","Record was not created.");
                    }

                }
                // }
            }
        });
    }
}

