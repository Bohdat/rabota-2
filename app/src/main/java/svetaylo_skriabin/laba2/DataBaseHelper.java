package svetaylo_skriabin.laba2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Employee.db";
    public static final String TABLE_NAME = "Employee_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "PHONE";

    public DataBaseHelper (Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SURNAME TEXT, PHONE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(String name, String surname, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, phone);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean updateData(String id, String name, String surname, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, phone);
        int result = db.update(TABLE_NAME, contentValues, "ID =?", new String[]{id});
        if(result > 0){
            return true;
        }else {
            return false;
        }
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME, "ID=?", new String[]{id});
        return i;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME, null);
        return res;
    }

    public Cursor getRowByID(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(
                "Employee_table",
                null,
                "ID = ?",
                new String[] {id},
                null,
                null,
                null);
        res.moveToFirst();
        return res;
    }
}