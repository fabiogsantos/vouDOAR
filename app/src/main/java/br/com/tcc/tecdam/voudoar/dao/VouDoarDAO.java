package br.com.tcc.tecdam.voudoar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.tcc.tecdam.voudoar.domain.Campanha;

/**
 * Created by fabio.goncalves on 12/04/2018.
 */

public class VouDoarDAO extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "VouDoar";

    private Context context;

    public VouDoarDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Campanha.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(Campanha.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(Campanha.SQL_CREATE_TABLE);
    }

    public void gravaCampanha(Campanha campanha) {
        if (existeCampanha(campanha)) {
            atualizaCampanha(campanha);
        } else {
            insereCampanha(campanha);
        }
    }

    private void insereCampanha(Campanha campanha) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues dados = campanha.GetContentValues();
        writableDatabase.insert(Campanha.TABLE_NAME, null, dados );
    }

    private void atualizaCampanha(Campanha campanha) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues dados = campanha.GetContentValues();
        writableDatabase.update(Campanha.TABLE_NAME, dados, Campanha.COLUMN_ID+"=?",new String[]{campanha.getId()});
    }

    private boolean existeCampanha(Campanha campanha) {
        if (campanha.getId().isEmpty()) return false;
        Cursor cursor = getCursorCampanha(campanha.getId());
        if (cursor == null) return false;
        return (cursor.getCount() > 0);
    }

    @Nullable
    private Cursor getCursorCampanha(String idCampanha) {
        String sql = "SELECT * FROM "+ Campanha.TABLE_NAME+" WHERE id = ? LIMIT 1";
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) return null;
        Cursor cursor = db.rawQuery(sql, new String[]{idCampanha});
        if (cursor == null) return null;
        return cursor;
    }

    public Campanha buscaCampanha(String idCampanha) {
        if (idCampanha.isEmpty()) return null;
        Cursor cursor = getCursorCampanha(idCampanha);
        if (cursor == null) return null;
        cursor.moveToFirst();
        Campanha campanha = new Campanha(cursor);
        return campanha;
    }

    public List<Campanha> buscaCampanhas() {
        List<Campanha> campanhas = new ArrayList<Campanha>();

        String sql = "SELECT * FROM "+Campanha.TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) return null;

        Cursor cursor = db.rawQuery(sql, new String[]{});
        if (cursor == null) return null;

        while (cursor.moveToNext()) {
            campanhas.add(new Campanha(cursor));
        }

        return campanhas;
    }
}
