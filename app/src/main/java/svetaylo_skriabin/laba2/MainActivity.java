package svetaylo_skriabin.laba2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDB;
    ArrayList<String> list, idList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDB = new DataBaseHelper(this);
        list = new ArrayList<>();
        idList = new ArrayList<>();

        Cursor res = myDB.getAllData();
        if (res != null && res.getCount()>0){
            while (res.moveToNext()){
                list.add(res.getString(1) + " " + res.getString(2) + "\n" + res.getString(3));
                idList.add(res.getString(0));
            }

            ListView listView = findViewById(R.id.listView);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
            registerForContextMenu(listView);
            res.close();
            Toast.makeText(this, "Data retrieved", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "No data to retrieve", Toast.LENGTH_SHORT).show();
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        menu.add("call");
        menu.add("show");
        menu.add("modify");
        menu.add("delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ListView.AdapterContextMenuInfo info = (ListView.AdapterContextMenuInfo) item.getMenuInfo();
        String selectedId = idList.get(info.position);
        Intent intent;

        switch (item.getTitle().toString()){
            case "call":
                Cursor cursor = myDB.getRowByID(selectedId);
                callPhone(cursor.getString(cursor.getColumnIndex("PHONE")));
                cursor.close();
                return true;

            case "show":
                intent = new Intent(this,ReadActivity.class);
                intent.putExtra("ID", selectedId);
                startActivity(intent);
                return true;

            case "modify":
                intent = new Intent(this,ModifyActivity.class);
                intent.putExtra("ID", selectedId);
                startActivity(intent);
                return true;

            case "delete":
                myDB.deleteData(selectedId);
                adapter.notifyDataSetInvalidated();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    public void callPhone(String number)
    {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission to call is needed", Toast.LENGTH_SHORT).show();
        }else {
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}