package com.example.tmall.classify;

import java.util.List;

import com.example.tmall.R;
import com.example.tmall.bean.Product;
import com.example.tmall.constant.HttpUrl;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>{

	private List<Product> productsList;
	private Context mContext;
	public ProductsAdapter(List<Product> productsList, Context mContext) {
		super();
		this.productsList = productsList;
		this.mContext = mContext;
	}

	@Override
	public int getItemCount() {

		return productsList==null?0:productsList.size();
	}

	@Override
	public void onBindViewHolder(ProductViewHolder viewHolder, int position) {
		Product product=productsList.get(position);
		Picasso.with(mContext).load(HttpUrl._HTTP+product.getProImg()).into(viewHolder.imageView);
		viewHolder.textView.setText(product.getProName());
		
	}

	@Override
	public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(itemView);
	}
	
	public class ProductViewHolder extends RecyclerView.ViewHolder{
		public ImageView imageView;
		public TextView textView;
		public ProductViewHolder(View itemView) {
			super(itemView);
			imageView=(ImageView) itemView.findViewById(R.id.product_item_img);
			textView=(TextView) itemView.findViewById(R.id.product_item_title);
		}
		
	}
	
}
