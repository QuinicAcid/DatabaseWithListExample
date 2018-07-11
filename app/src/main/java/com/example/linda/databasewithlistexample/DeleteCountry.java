package com.example.linda.databasewithlistexample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DeleteCountry extends Activity {
    private CountriesDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    private EditText countryCode;
    String strCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setContentView(R.layout.activity_delete_country);

        dbHelper = new CountriesDbAdapter(this);
        dbHelper.open();

        Button delButton = (Button) findViewById(R.id.btnDel);

        final EditText countryCode = (EditText) findViewById(R.id.edtName);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Before delete", Toast.LENGTH_LONG).show();
                boolean deleted = true;
                String strCode = countryCode.getText().toString();
                if (strCode.equals(""))
                {
                    Toast.makeText(getBaseContext(), "All fields must be entered- reenter", Toast.LENGTH_LONG).show();
                    countryCode.setFocusable(true);
                }
                else {
                    deleted =  dbHelper.deleteOneCountry(strCode);
                    if (deleted ==  false) {
                        Toast.makeText(getBaseContext(), "Record does not exist .", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getBaseContext(), "Record Deleted", Toast.LENGTH_LONG).show();

                    }
                }
                startActivity(new Intent(DeleteCountry.this, MainActivity.class));

            }
        });
    }}

