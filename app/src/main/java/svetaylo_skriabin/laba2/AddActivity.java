package svetaylo_skriabin.laba2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    DataBaseHelper myDB;
    EditText txtName, txtSurname, txtPhone;
    Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDB = new DataBaseHelper(this);
        txtName = findViewById(R.id.addTextName);
        txtSurname = findViewById(R.id.addTextSurname);
        txtPhone = findViewById(R.id.addTextPhone);
        btnClick = findViewById(R.id.addButton);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                String surname = txtSurname.getText().toString();
                String phone = txtPhone.getText().toString();
                Boolean result = myDB.insertData(name, surname, phone);
                if (result){
                    Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Data insertion failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
