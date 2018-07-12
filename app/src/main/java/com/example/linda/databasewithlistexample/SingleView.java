package com.example.linda.databasewithlistexample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SingleView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);

        //TextView txtCode = (TextView) this.findViewById(R.id.txtCode);
        String getCode = "";
        TextView txtCountryName = (TextView) this.findViewById(R.id.txtCountryName);
        Button btnGoToWeb = (Button) this.findViewById(R.id.btnGoToWeb);

        Intent intent =  getIntent();
        getCode = getIntent().getStringExtra("code");
/*        getName = getIntent().getStringExtra("name");
        getUri = getIntent().getStringExtra("uri");

        String uri = getIntent().getStringExtra("uri");
        Uri myUri = Uri.parse(uri);
        startActivity(new Intent(Intent.ACTION_VIEW, myUri));
*/

    }
}
