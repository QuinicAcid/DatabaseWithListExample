package com.example.linda.databasewithlistexample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AddNewCountry extends Activity {
    private CountriesDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

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

        countryCode.requestFocus();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strName = name.getText().toString();
                strCode = countryCode.getText().toString();
                strUri = uri.getText().toString();

                if (strName.equals("") || strUri.equals("")) {
                    Toast.makeText(getBaseContext(), "Invalid Data", Toast.LENGTH_LONG).show();
                    countryCode.requestFocus();
                }else if (strCode.equals("") || (strCode.length() != 3)) {
                    Toast.makeText(getBaseContext(), "Invalid Country Code", Toast.LENGTH_LONG).show();

                } else if (!(Patterns.WEB_URL.matcher(strUri).matches())) {
                 //   boolean isURL = Patterns.WEB_URL.matcher(strUri).matches();
                    Toast.makeText(getBaseContext(), "URL Is Not Valid", Toast.LENGTH_LONG).show();

                } else {

                    strCode = strCode.toUpperCase();
                    dbHelper.createCountry(strCode, strName, strUri);
                    Log.d("CountryDB","After createCountry");
                    Toast.makeText(getBaseContext(), "Record Added", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddNewCountry.this, MainActivity.class));
                }
                // }
            }
        });
    }
}

