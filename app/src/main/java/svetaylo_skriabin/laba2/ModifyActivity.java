package svetaylo_skriabin.laba2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyActivity extends AppCompatActivity {

    DataBaseHelper myDB;
    EditText txtId, txtName, txtSurname, txtPhone;
    Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDB = new DataBaseHelper(this);
        txtId = findViewById(R.id.modifyTextId);
        txtName = findViewById(R.id.modifyTextName);
        txtSurname = findViewById(R.id.modifyTextSurname);
        txtPhone = findViewById(R.id.modifyTextPhone);
        btnClick = findViewById(R.id.modifyButton);

        String id = getIntent().getExtras().getString("ID");
        Cursor cursor = myDB.getRowByID(id);

        txtId.setHint("ID: " + cursor.getString(0));
        txtName.setHint("Name: " + cursor.getString(1));
        txtSurname.setHint("Surname: " + cursor.getString(2));
        txtPhone.setHint("PhoneNum: " + cursor.getString(3));

        cursor.close();

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtId.getText().toString();
                String name = txtName.getText().toString();
                String surname = txtSurname.getText().toString();
                String phone = txtPhone.getText().toString();
                Boolean result = myDB.updateData(id, name, surname, phone);
                if (result){
                    Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Data update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
