package edu.hanu.mycart.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.mycart.models.Product;
import edu.hanu.mycart.models.ProductInCart;

public class ProductCursorWrapper extends CursorWrapper {
    private Cursor cursor;

    public ProductCursorWrapper(Cursor cursor) {
        super(cursor);
        this.cursor = cursor;
    }

    public ProductInCart getProductInCart() {
        int idIndex = cursor.getColumnIndex(DBHelper.PRODUCT_ID);
        int thumbnailIndex = cursor.getColumnIndex(DBHelper.PRODUCT_THUMBNAIL);
        int nameIndex = cursor.getColumnIndex(DBHelper.PRODUCT_NAME);
        int priceIndex = cursor.getColumnIndex(DBHelper.PRODUCT_PRICE);
        int quantityIndex = cursor.getColumnIndex(DBHelper.PRODUCT_QUANTITY);

        int id = cursor.getInt(idIndex);
        String thumbnail = cursor.getString(thumbnailIndex);
        String name = cursor.getString(nameIndex);
        int price = cursor.getInt(priceIndex);
        int quantity = cursor.getInt(quantityIndex);

        Product product = new Product(id, thumbnail, name, price);
        ProductInCart productInCart = new ProductInCart(product, quantity);

        return productInCart;
    }

    public List<ProductInCart> getProductsInCart() {
        List<ProductInCart> productsInCart = new ArrayList<>();

        // for each row in cursor
        while (!cursor.isLast()) {
            cursor.moveToNext();

            ProductInCart productInCart = this.getProductInCart();
            productsInCart.add(productInCart);
        }
        return productsInCart;
    }
}
