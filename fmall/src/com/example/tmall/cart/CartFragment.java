package com.example.tmall.cart;

import java.util.List;

import com.example.tmall.Datas;
import com.example.tmall.OrderActivity;
import com.example.tmall.R;
import com.example.tmall.bean.Product;
import com.example.tmall.constant.HttpUrl;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 购物车主界面
 * 
 */
public class CartFragment extends Fragment implements OnClickListener,OnItemClickListener{
	private static final String CART_TAG="CartFragmet";

	private List<Product> cartProducts;
	private CartAdapter mCartAdapter;
	
	private ListView mListView;
	private TextView mEdit;
	private CheckBox mCheckAll;
	private RelativeLayout mBottonLayout;
	private TextView mPriceAll;
	private TextView mSelectNum;
	private TextView mFavorite;
	private TextView mDelete;
	//
	private LinearLayout bottom;
	private FrameLayout nothing;
	
	
	 /** 批量模式下，用来记录当前选中状态 */
	private SparseArray<Boolean> mSelectState = new SparseArray<Boolean>();
	private float  totalPrice=0; // 商品总价
	private ViewHolder holder = null;
	private Boolean isEditModel=false;// 编辑模式
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.cart_fragment, null);
		initView(view);
		loadData();
		return view;
	}

	private void initView(View view) {
		mListView=(ListView) view.findViewById(R.id.cart_listview);
		mListView.setSelector(R.drawable.list_selector);
		mListView.setOnItemClickListener(CartFragment.this);
		
		mBottonLayout = (RelativeLayout) view.findViewById(R.id.bottom_cart_rl);
		mCheckAll = (CheckBox) view.findViewById(R.id.cart_check_box);
		mPriceAll = (TextView) view.findViewById(R.id.tv_cart_total);
		mSelectNum = (TextView) view.findViewById(R.id.tv_cart_select_num);
		mFavorite = (TextView) view.findViewById(R.id.tv_cart_favorite);
		mDelete = (TextView) view.findViewById(R.id.tv_cart_buy_or_del);
		mEdit=(TextView) view.findViewById(R.id.tv_cart_edit);
		
		mEdit.setOnClickListener(this);
		mDelete.setOnClickListener(this);
		mFavorite.setOnClickListener(this);
		mCheckAll.setOnClickListener(this);
		
		//
		bottom=(LinearLayout) view.findViewById(R.id.bottom_cart_bar);
		nothing=(FrameLayout) view.findViewById(R.id.cart_nothing);
		
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateUI();
	}
	private void updateUI(){
		if(Datas.cartProducts.size()==0){
			bottom.setVisibility(View.GONE);
			mEdit.setVisibility(View.GONE);
			nothing.setVisibility(View.VISIBLE);
		}else{  
			bottom.setVisibility(View.VISIBLE);
			mEdit.setVisibility(View.VISIBLE);
			nothing.setVisibility(View.GONE);
		}
	}
	
	private void loadData(){
		cartProducts=Datas.cartProducts;
		mCartAdapter=new CartAdapter();
		mListView.setAdapter(mCartAdapter);
		
	}
	
	
	
	/*点击事件监听*/
	@Override
	public void onClick(View v) {
		
		switch (v.getId())
		{
			case R.id.tv_cart_edit://编辑
				isEditModel = !isEditModel;
				if (isEditModel)
				{
					mEdit.setText("完成");
					mDelete.setText("删除");
					mBottonLayout.setVisibility(View.GONE);
					mFavorite.setVisibility(View.VISIBLE);
					

				} else{
					mEdit.setText("编辑全部");
					mFavorite.setVisibility(View.GONE);
					mBottonLayout.setVisibility(View.VISIBLE);
					mDelete.setText("结算");
				}
				break;
			case R.id.cart_check_box://全选
				if (mCheckAll.isChecked())
				{
					totalPrice = 0;
					if (cartProducts != null)
					{
						mSelectState.clear();
						int size = cartProducts.size();
						if (size == 0)
						{
							return;
						}
						for (int i = 0; i < size; i++)
						{
							int _id =  cartProducts.get(i).getId();
							mSelectState.put(_id, true);
							
							totalPrice += cartProducts.get(i).getProCount() * cartProducts.get(i).getProPrice();
						}
						mCartAdapter.notifyDataSetChanged();
						mPriceAll.setText("￥" + totalPrice + "元");
						mSelectNum.setText("已选" + mSelectState.size() + "件商品");

					}
				} else
				{
					if (mCartAdapter != null)
					{
						totalPrice = 0;
						mSelectState.clear();
						mCartAdapter.notifyDataSetChanged();
						mPriceAll.setText("￥" + 0.00 + "元");
						mSelectNum.setText("已选" + 0 + "件商品");

					}
					
				}
				break;
			case R.id.tv_cart_buy_or_del://结算删除
				if(isEditModel){//删除
					int size=cartProducts.size();
					for(int i=size-1;i>-1;i--)
					{
						int _id=cartProducts.get(i).getId();
						if(mSelectState.get(_id,false)){
							cartProducts.remove(i);
						}
					}
					mCartAdapter.notifyDataSetChanged();
					updateUI();
					
				}else{//结算
					if(mSelectState.size()==0){
						
					}else{
						if(Datas.isLogin){
							int size=cartProducts.size();
							for(int i=size-1;i>-1;i--)
							{
								int _id=cartProducts.get(i).getId();
								if(mSelectState.get(_id,false)){
									Datas.ensureOrderProducts.add(cartProducts.get(i));
								}
							}
							Intent intent=new Intent(getActivity(),OrderActivity.class);
							intent.putExtra(OrderActivity.TOTALPRICE, ""+totalPrice);
							startActivity(intent);
						}else{
							Toast.makeText(getActivity(), "请登录后再进行操作！", Toast.LENGTH_SHORT).show();
						}
							
						
					}

				}
				break;
			case R.id.tv_cart_favorite://收藏
				Toast.makeText(getActivity(), "收藏", Toast.LENGTH_SHORT).show();
				break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Product product = cartProducts.get(position);

		ViewHolder holder = (ViewHolder) view.getTag();
		int _id =  product.getId();

		boolean selected = !mSelectState.get(_id, false);
		holder.chose.toggle();
		if (selected)
		{
			mSelectState.put(_id, true);
			totalPrice += product.getProCount() * product.getProPrice();
			
		}
		else
		{
			mSelectState.delete(_id);
			totalPrice -= product.getProCount() * product.getProPrice();
			
		}
		mSelectNum.setText("已选" + mSelectState.size() + "件商品");
		mPriceAll.setText("￥" + totalPrice + "元");
		if (mSelectState.size() == cartProducts.size())
		{
			mCheckAll.setChecked(true);
		} else
		{
			mCheckAll.setChecked(false);
		}
	}
	
	/*数据适配器*/
	private  class CartAdapter extends BaseAdapter{ 
	      
	    @Override  
	    public int getCount() {  
	       
	        if (cartProducts==null) {  
	            return 0;  
	        }else {  
	            return cartProducts.size();  
	        }  
	    }  
	  
	    @Override  
	    public Object getItem(int position) {  
	      
	        if (cartProducts == null) {   
	            return null;   
	        } else {   
	            return cartProducts.get(position);   
	        }   
	    }  
	  
	    @Override  
	    public long getItemId(int position) {  
	       
	        return position;  
	    }  
	  
	    @Override    
	    public View getView(final int position, View convertView, ViewGroup parent) {  
	        
	           
	        if (convertView == null) {   
	            holder = new ViewHolder();
	            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_cart, null);  
	            holder.image=(ImageView) convertView.findViewById(R.id.pro_image);  
	            holder.chose=(CheckBox) convertView.findViewById(R.id.pro_checkbox);  
	            holder.proName=(TextView) convertView.findViewById(R.id.pro_name);  
	            holder.proPrice=(TextView)convertView.findViewById(R.id.pro_price);  
	            holder.proCount=(TextView) convertView.findViewById(R.id.pro_count);
	            holder.add = (TextView) convertView.findViewById(R.id.tv_add);
	            holder.red = (TextView) convertView.findViewById(R.id.tv_reduce);
	            convertView.setTag(holder);   
	              
	        } else {   
	            holder = (ViewHolder) convertView.getTag();   
	        }   
	          
	        if (cartProducts != null) {   
	            Product product=cartProducts.get(position);  
	            holder.proName.setText(product.getProName().toString());  
	            holder.proPrice.setText("￥"+String.valueOf(product.getProPrice())+"元");  
	            holder.proCount.setText(String.valueOf(product.getProCount()));
	            holder.chose.setChecked(mSelectState.get(product.getId(), false));
	            Picasso.with(getActivity()).load(HttpUrl._HTTP+product.getProImg()).into(holder.image);
	        }
	        /*加*/
	        holder.add.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					
					int _id = (int) cartProducts.get(position).getId();

					boolean selected = mSelectState.get(_id, false);

					cartProducts.get(position).setProCount(cartProducts.get(position).getProCount() + 1);

					notifyDataSetChanged();

					if (selected)
					{
						totalPrice += cartProducts.get(position).getProPrice();
						mPriceAll.setText("￥" + totalPrice + "元");

					}

				}
			});
	        /*减*/
	        holder.red.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{

					if (cartProducts.get(position).getProCount() == 1)
						return;

					int _id = (int) cartProducts.get(position).getId();

					boolean selected = mSelectState.get(_id, false);
					cartProducts.get(position).setProCount(cartProducts.get(position).getProCount() - 1);
					notifyDataSetChanged();

					if (selected)
					{
						totalPrice -= cartProducts.get(position).getProPrice();
						mPriceAll.setText("￥" + totalPrice + "元");

					} 

				}
			});
	        
	        return convertView;   
	    }  
	   
	}
	private class ViewHolder {  
        ImageView image;  
        TextView proName;  
        CheckBox chose;  
        TextView proPrice;  
        TextView proCount;
        TextView add;
		TextView red;
	}

}
