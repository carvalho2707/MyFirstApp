package pt.tiagocarvalho.myfirstapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pt.tiagocarvalho.myfirstapp.model.Recurso;

/**
 * Created by tiago.carvalho on 01/11/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ResourceFinder.db";
    private static final int DATABASE_VERSION = 1;

    public static class RecursoEntry implements BaseColumns {
        public static final String TABLE_NAME = "recurso";
        private static final String COLUMN_NAME_NAME = "name";
        private static final String COLUMN_NAME_EMAIL = "email";
        private static final String COLUMN_NAME_AGE = "age";
        private static final String COLUMN_NAME_IMAGE = "image";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + RecursoEntry.TABLE_NAME + " (" +
                    RecursoEntry._ID + " INTEGER PRIMARY KEY," +
                    RecursoEntry.COLUMN_NAME_NAME + " TEXT," +
                    RecursoEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    RecursoEntry.COLUMN_NAME_AGE + " INTEGER," +
                    RecursoEntry.COLUMN_NAME_IMAGE + " TEXT )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RecursoEntry.TABLE_NAME;

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
        contentValues.put(RecursoEntry.COLUMN_NAME_NAME, recurso.getName());
        contentValues.put(RecursoEntry.COLUMN_NAME_EMAIL, recurso.getEmail());
        contentValues.put(RecursoEntry.COLUMN_NAME_AGE, recurso.getAge());
        contentValues.put(RecursoEntry.COLUMN_NAME_IMAGE, recurso.getImage());
        long result = db.insert(RecursoEntry.TABLE_NAME, null, contentValues);
        Log.d("TIAGO", "" + result);
        return result;
    }

    public Recurso getRecursoById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from recurso where id=" + id + "", null);
        if (cursor != null)
            cursor.moveToFirst();

        Recurso recurso = new Recurso(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4));
        return recurso;
    }

    public ArrayList<Recurso> queryRecursos(String name, int minAge, int maxAge) {
        ArrayList<Recurso> recursoList = new ArrayList<Recurso>();
        // Select All Query
        String[] projection = {
                RecursoEntry._ID,
                RecursoEntry.COLUMN_NAME_NAME,
                RecursoEntry.COLUMN_NAME_EMAIL,
                RecursoEntry.COLUMN_NAME_AGE,
                RecursoEntry.COLUMN_NAME_IMAGE
        };

        List<String> args = new ArrayList<>();
        args.add(String.valueOf(minAge));
        args.add(String.valueOf(maxAge));

        String selection = RecursoEntry.COLUMN_NAME_AGE + " > ? AND " + RecursoEntry.COLUMN_NAME_AGE + " < ? ";

        if (!TextUtils.isEmpty(name)) {
            selection += " and name = ?";
            args.add(name);
        }

        String[] selectionArgs = args.toArray(new String[args.size()]);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                RecursoEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                Recurso recurso = new Recurso();
                recurso.setId(cursor.getLong(cursor.getColumnIndexOrThrow(RecursoEntry._ID)));
                recurso.setName(cursor.getString(cursor.getColumnIndexOrThrow(RecursoEntry.COLUMN_NAME_NAME)));
                recurso.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(RecursoEntry.COLUMN_NAME_EMAIL)));
                recurso.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(RecursoEntry.COLUMN_NAME_AGE)));
                recurso.setImage(cursor.getString(cursor.getColumnIndexOrThrow(RecursoEntry.COLUMN_NAME_IMAGE)));
                recursoList.add(recurso);
            } while (cursor.moveToNext());
        }

        return recursoList;
    }
}
