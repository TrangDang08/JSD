package edu.hanu.mycart.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import edu.hanu.mycart.models.Product;
import edu.hanu.mycart.models.ProductInCart;

public class ProductManager {

    private static ProductManager instance;

    public static ProductManager getInstance(Context context) {
        if (instance == null) {
            instance = new ProductManager(context);
        }
        return instance;
    }

    private SQLiteDatabase db;

    private ProductManager(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<ProductInCart> all() {
        Cursor cursor = db.rawQuery("SELECT * FROM cart", null);
        ProductCursorWrapper wrapper = new ProductCursorWrapper(cursor);
        List<ProductInCart> productsInCart = wrapper.getProductsInCart();
        return productsInCart;
    }

    public Boolean insert(int id, String thumbnail, String name, int price, int quantity) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.PRODUCT_ID, id);
        contentValues.put(DBHelper.PRODUCT_THUMBNAIL, thumbnail);
        contentValues.put(DBHelper.PRODUCT_NAME, name);
        contentValues.put(DBHelper.PRODUCT_PRICE, price);
        contentValues.put(DBHelper.PRODUCT_QUANTITY, quantity);

        long result = db.insert(DBHelper.TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean update(int id, int quantity) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.PRODUCT_QUANTITY, quantity);

        long result = db.update(DBHelper.TABLE_NAME, contentValues, DBHelper.PRODUCT_ID + "=" + id, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void delete(int id) {
        db.delete(DBHelper.TABLE_NAME, DBHelper.PRODUCT_ID + "=" + id, null);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        db.close();
    }
}
