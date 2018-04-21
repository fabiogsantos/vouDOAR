package br.com.tcc.tecdam.voudoar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.tcc.tecdam.voudoar.domain.Campanha;

/**
 * Created by fabio.goncalves on 12/04/2018.
 */

public class VouDoarDAO extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "VouDoar";

    public VouDoarDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Campanha.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insereCampanha(Campanha campanha) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues dados = campanha.GetContentValues();
        long id = writableDatabase.insert(Campanha.TABLE_NAME, null, dados );
        return id;
    }

    public Campanha buscaCampanha(long idCampanha) {
        String sql = "SELECT * FROM "+Campanha.TABLE_NAME+" WHERE id = ?";
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) {
            return null;
        }
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idCampanha)});
        if (cursor == null) {
            return null;
        }
        cursor.moveToFirst();
        Campanha campanha = new Campanha(cursor);
        return campanha;
    }
}
