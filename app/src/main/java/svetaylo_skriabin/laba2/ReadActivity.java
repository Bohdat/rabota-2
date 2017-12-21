package svetaylo_skriabin.laba2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ReadActivity extends AppCompatActivity {

    DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDB = new DataBaseHelper(this);
        String id = getIntent().getExtras().getString("ID");
        Cursor cursor = myDB.getRowByID(id);

        String text =
                "ID: " + cursor.getString(0) + "\n" +
                "Name: " + cursor.getString(1) + "\n" +
                        "Surname: " + cursor.getString(2) + "\n" +
                        "Phone Number: " + cursor.getString(3);

        TextView txtResult = findViewById(R.id.readTextView);
        txtResult.setText(text);
        cursor.close();
    }
}
