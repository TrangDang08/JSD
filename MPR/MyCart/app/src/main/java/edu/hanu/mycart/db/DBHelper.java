package edu.hanu.mycart.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "productDb";
    private static final int DB_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    static final String TABLE_NAME = "cart";
    static final String PRODUCT_ID = "id";
    static final String PRODUCT_THUMBNAIL = "thumbnail";
    static final String PRODUCT_NAME = "name";
    static final String PRODUCT_PRICE = "unit_price";
    static final String PRODUCT_QUANTITY = "quantity";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE cart (" +
                PRODUCT_ID + " INTERGER PRIMARY KEY," +
                PRODUCT_THUMBNAIL + " TEXT," +
                PRODUCT_NAME + " TEXT NOT NULL," +
                PRODUCT_PRICE + " BIGINT NOT NULL," +
                PRODUCT_QUANTITY + " INT" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        sqLiteDatabase.execSQL("DROP TABLE cart");
        onCreate(sqLiteDatabase);
    }
}
