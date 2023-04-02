package edu.hanu.mycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.mycart.adapters.CartAdapter;
import edu.hanu.mycart.db.ProductManager;
import edu.hanu.mycart.models.Product;
import edu.hanu.mycart.models.ProductInCart;

public class CartActivity extends AppCompatActivity {

    public List<ProductInCart> cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));

        TextView totalPrice = findViewById(R.id.totalPrice);

//        List<Product> products = new ArrayList<>();
//        products.add(new Product(1, "https://m.media-amazon.com/images/I/617jZZZhr3S._SL1500_.jpg", "Den de ban", 10000));
//        products.add(new Product(2, "https://m.media-amazon.com/images/I/617jZZZhr3S._SL1500_.jpg", "Lamp", 15000));
//        for (int i = 0; i < products.size(); i++) {
//            Product product = products.get(i);
//            cart.add(new ProductInCart(product, 2));
//        }

        ProductManager productManager = ProductManager.getInstance(CartActivity.this);
        cart = productManager.all();

        CartAdapter cartAdapter = new CartAdapter(cart);
        cartAdapter.setTotalPrice(totalPrice);
        rvCart.setAdapter(cartAdapter);

        long totalPriceNum = 0;
        if (cart.size() > 0) {
            for (int i = 0; i < cart.size(); i++) {
                int unitPrice = cart.get(i).getProduct().getUnitPrice();
                int quantity = cart.get(i).getQuantity();

                totalPriceNum += unitPrice * quantity;
            }
        }
        totalPrice.setText(String.valueOf(totalPriceNum) + " VND");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cartMenu:
                // back to product list
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}