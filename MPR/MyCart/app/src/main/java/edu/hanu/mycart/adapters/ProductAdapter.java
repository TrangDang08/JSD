package edu.hanu.mycart.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.mycart.R;
import edu.hanu.mycart.db.ProductManager;
import edu.hanu.mycart.models.Product;
import edu.hanu.mycart.models.ProductInCart;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private List<Product> products = new ArrayList<>();

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
        }
        ProductManager productManager = ProductManager.getInstance(itemView.getContext());

        public void bind(Product product, int position) {
            String imageUrl = product.getThumbnail();
            ImageView productImage = itemView.findViewById(R.id.productImage);
            Picasso.get().load(imageUrl).into(productImage);

            String name = product.getName();
            TextView productName = itemView.findViewById(R.id.productName);
            productName.setText(name);

            int unitPrice = product.getUnitPrice();
            TextView productPrice = itemView.findViewById(R.id.productPrice);
            productPrice.setText(String.valueOf(unitPrice));

            Button btnAdd = itemView.findViewById(R.id.btnAddToCart);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<ProductInCart> productInCartList = productManager.all();
                    Boolean checkInCart = false;
                    int quantity = 1;

                    for (int i = 0; i < productInCartList.size(); i++) {
                        ProductInCart productSingle = productInCartList.get(i);
                        if (productSingle.getProduct().getId() == product.getId()) {
                            checkInCart = true;
                            quantity = productSingle.getQuantity();
                            int quantityInc = quantity + 1;
                            productManager.update(productSingle.getProduct().getId(), quantityInc);
                        }
                    }

                    if (checkInCart == false) {
                        productManager.insert(product.getId(), imageUrl, name, unitPrice, quantity);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (products != null) {
            return products.size();
        }
        return 0;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.product, parent, false);
        ProductHolder productHolder = new ProductHolder(itemView);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product, position);
    }

    public void filterProduct(ArrayList<Product> filteredProduct) {
        products = filteredProduct;
        notifyDataSetChanged();
    }
}
