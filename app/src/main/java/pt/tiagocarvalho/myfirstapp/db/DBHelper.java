package pt.tiagocarvalho.myfirstapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import pt.tiagocarvalho.myfirstapp.model.Recurso;

/**
 * Created by tiago.carvalho on 01/11/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ResourceFinder.db";
    private static final int DATABASE_VERSION = 1;
    private static final String RECURSO_TABLE_NAME = "recurso";
    private static final String RECURSO_COLUMN_ID = "id";
    private static final String RECURSO_COLUMN_NAME = "name";
    private static final String RECURSO_COLUMN_EMAIL = "email";
    private static final String RECURSO_COLUMN_AGE = "age";
    private static final String RECURSO_COLUMN_IMAGE_ID = "image_id";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RECURSO_TABLE_NAME + " (" +
                    RECURSO_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    RECURSO_COLUMN_NAME + " TEXT," +
                    RECURSO_COLUMN_EMAIL + " TEXT," +
                    RECURSO_COLUMN_AGE + " INTEGER," +
                    RECURSO_COLUMN_IMAGE_ID + " INTEGER )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RECURSO_TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long addRecurso(Recurso recurso) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECURSO_COLUMN_NAME, recurso.getName());
        contentValues.put(RECURSO_COLUMN_EMAIL, recurso.getEmail());
        contentValues.put(RECURSO_COLUMN_AGE, recurso.getAge());
        contentValues.put(RECURSO_COLUMN_IMAGE_ID, recurso.getImageId());
        long result = db.insert(RECURSO_TABLE_NAME, null, contentValues);
        return result;
    }

    public Recurso getRecursoById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from recurso where id=" + id + "", null);
        if (cursor != null)
            cursor.moveToFirst();

        Recurso recurso = new Recurso(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
        return recurso;
    }

    public List<Recurso> getAllRecursos() {
        List<Recurso> recursoList = new ArrayList<Recurso>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + RECURSO_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Recurso recurso = new Recurso();
                recurso.setId(cursor.getInt(0));
                recurso.setName(cursor.getString(1));
                recurso.setEmail(cursor.getString(2));
                recurso.setAge(cursor.getInt(3));
                recurso.setImageId(cursor.getInt(4));

                recursoList.add(recurso);
            } while (cursor.moveToNext());
        }

        return recursoList;
    }

    /*public int getContactsCount() {
    }*/

   /* public int updateContact(Contact contact) {
    }*/

   /* public void deleteContact(Contact contact) {
    }*/
}
