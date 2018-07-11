package com.example.linda.databasewithlistexample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class DeleteCountry extends Activity {
    private CountriesDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    private EditText countryCode;
    String strCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE) ;
        setContentView(R.layout.activity_delete_country);

        dbHelper = new CountriesDbAdapter(this);
        dbHelper.open();

        Button delButton = (Button) findViewById(R.id.btnDel);

        final EditText countryCode = (EditText) findViewById(R.id.edtName);

        //countryCode.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

/*        InputFilter[] editFilters = countryCode.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = 3;
        countryCode.setFilters(newFilters);
*/

        // Apply the filters to control the input (3 letters and capital)
        ArrayList<InputFilter> curInputFilters = new ArrayList<InputFilter>(Arrays.asList(countryCode.getFilters()));
        curInputFilters.add(0, new InputFilter.LengthFilter(3));
        curInputFilters.add(1, new InputFilter.AllCaps());
        InputFilter[] newInputFilters = curInputFilters.toArray(new InputFilter[curInputFilters.size()]);
        countryCode.setFilters(newInputFilters);

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
                        Toast.makeText(getBaseContext(), "Country code "+ strCode + " does not exist.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getBaseContext(), "Record Deleted", Toast.LENGTH_LONG).show();

                    }
                }
                startActivity(new Intent(DeleteCountry.this, MainActivity.class));

            }
        });
    }}

