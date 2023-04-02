package edu.hanu.mycart.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.mycart.R;
import edu.hanu.mycart.db.ProductManager;
import edu.hanu.mycart.models.ProductInCart;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private List<ProductInCart> productInCarts = new ArrayList<>();
    private TextView totalPrice;

    public CartAdapter(List<ProductInCart> productInCarts) {
        this.productInCarts = productInCarts;
    }

    public TextView getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(TextView totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String calculate(List<ProductInCart> productInCarts) {
        long totalPriceNum = 0;
        if (productInCarts.size() > 0) {
            for (int i = 0; i < productInCarts.size(); i++) {
                int unitPrice = productInCarts.get(i).getProduct().getUnitPrice();
                int quantity = productInCarts.get(i).getQuantity();

                totalPriceNum += unitPrice * quantity;
            }
        }
        return String.valueOf(totalPriceNum);
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        public CartHolder(@NonNull View itemView) {
            super(itemView);
        }
        ProductManager productManager = ProductManager.getInstance(itemView.getContext());

        public void bind(ProductInCart productInCart, int position) {
            ImageView productImageCart = itemView.findViewById(R.id.productImageCart);
            Picasso.get().load(productInCart.getProduct().getThumbnail()).into(productImageCart);

            TextView productNameCart = itemView.findViewById(R.id.productNameCart);
            productNameCart.setText(productInCart.getProduct().getName());

            TextView productPriceCart = itemView.findViewById(R.id.productPriceCart);
            int unitPrice = productInCart.getProduct().getUnitPrice();
            productPriceCart.setText(String.valueOf(unitPrice));

            TextView quantity = itemView.findViewById(R.id.quantity);
            int quantityNum = productInCart.getQuantity();
            quantity.setText(String.valueOf(quantityNum));

            TextView productTotalPrice = itemView.findViewById(R.id.productTotalPrice);
            long productTotalPriceNum = unitPrice * quantityNum;
            productTotalPrice.setText(String.valueOf(productTotalPriceNum));

            // TODO: xử lý realtime
            ImageButton btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quan = productInCart.getQuantity();
                    int quantityInc = quan + 1;
                    productInCart.setQuantity(quantityInc);
                    Log.i("Increase ", productInCart.getProduct().getId() + " " + productInCart.getProduct().getName() + " quantity: " + quantityInc);
                    productManager.update(productInCart.getProduct().getId(), quantityInc);
                    notifyItemChanged(getAdapterPosition());
                    quantity.setText(String.valueOf(quantityInc));
                    List<ProductInCart> productInCartList = productManager.all();
                    totalPrice.setText(calculate(productInCartList));
                }
            });

            ImageButton btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quan = productInCart.getQuantity();
                    if (quan > 1) {
                        int quantityDec = quan - 1;
                        productInCart.setQuantity(quantityDec);
                        Log.i("Decrease ", productInCart.getProduct().getId() + " " + productInCart.getProduct().getName() + " quantity: " + quantityDec);
                        productManager.update(productInCart.getProduct().getId(), quantityDec);
                        notifyItemChanged(getAdapterPosition());
                        quantity.setText(String.valueOf(quantityDec));
                        List<ProductInCart> productInCartList = productManager.all();
                        totalPrice.setText(calculate(productInCartList));
                    } else if (quan == 1) {
                        productManager.delete(productInCart.getProduct().getId());
                        notifyItemRemoved(getAdapterPosition());
                        List<ProductInCart> productInCartList = productManager.all();
                        totalPrice.setText(calculate(productInCartList));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productInCarts.size();
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.product_in_cart, parent, false);
        CartHolder cartHolder = new CartHolder(itemView);
        return cartHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        ProductInCart productInCart = productInCarts.get(position);
        holder.bind(productInCart, position);
    }
}
