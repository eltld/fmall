package com.example.tmall.user;

import com.example.tmall.Datas;
import com.example.tmall.R;
import com.readystatesoftware.viewbadger.BadgeView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 个人中心界面
 */
public class UserFragment extends Fragment implements OnClickListener{
	

    private LinearLayout settings,order,prize;
    private RelativeLayout rl_user;
    private TextView tv_name;
    private TextView fukuan,fahuo,shouhuo,pingjia,tuikuan;
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	
    	View view=LayoutInflater.from(getActivity()).inflate(R.layout.user_fragment, null);
    	initView(view);
		return view;
	}

    @Override
    public void onResume() {
    	
    	super.onResume();
		tv_name.setText(Datas.currentUser.getUserName());
    }
	private void initView(View view){
		tv_name=(TextView) view.findViewById(R.id.textView_name);
    	settings=(LinearLayout) view.findViewById(R.id.ll_settings);
    	settings.setOnClickListener(this);
    	order=(LinearLayout) view.findViewById(R.id.ll_order);
    	order.setOnClickListener(this);
    	prize=(LinearLayout) view.findViewById(R.id.ll_prize);
    	prize.setOnClickListener(this);
    	rl_user=(RelativeLayout) view.findViewById(R.id.rl_user);
    	rl_user.setOnClickListener(this);
    	
    	fukuan=(TextView) view.findViewById(R.id.tv_daifukuan);
    	fukuan.setOnClickListener(this);
    	
    	fahuo=(TextView) view.findViewById(R.id.tv_daifahuo);
    	fahuo.setOnClickListener(this);
    
    	
    	shouhuo=(TextView) view.findViewById(R.id.tv_daishouhuo);
    	shouhuo.setOnClickListener(this);
    	BadgeView badgeView3=new BadgeView(getContext(), shouhuo);
    	badgeView3.setText("3");
    	badgeView3.show();
    	
    	pingjia=(TextView) view.findViewById(R.id.tv_daipingjia);
    	pingjia.setOnClickListener(this);
    	
    	tuikuan=(TextView) view.findViewById(R.id.tv_tuikuan);
    	tuikuan.setOnClickListener(this);
    
    }




	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
			case R.id.ll_settings:
				Intent intent1=new Intent(getActivity(),SettingsActivity.class);
				startActivity(intent1);
				break;
				
			case R.id.ll_prize:
				Intent intent2=new Intent(getActivity(),PrizeActivity.class);
				startActivity(intent2);
				break;
				
			case R.id.ll_order:
				Intent intent3=new Intent(getActivity(),OrderManagementActivity.class);
				startActivity(intent3);
				break;
				
			case R.id.rl_user:
				if(Datas.isLogin){
					Intent intent5=new Intent(getActivity(),UserInformationActivity.class);
					startActivity(intent5);
				}else{
					Intent intent4=new Intent(getActivity(),LoginActivity.class);
					startActivity(intent4);
				}
				break;
				
			case R.id.tv_daifukuan:
				Toast.makeText(getActivity(), "待付款", Toast.LENGTH_SHORT).show();
				break;
			case R.id.tv_daifahuo:
				Toast.makeText(getActivity(), "待发货", Toast.LENGTH_SHORT).show();
				break;
			case R.id.tv_daishouhuo:
				Toast.makeText(getActivity(), "待收货", Toast.LENGTH_SHORT).show();
				break;
			case R.id.tv_daipingjia:
				Toast.makeText(getActivity(), "待评价", Toast.LENGTH_SHORT).show();
				break;
			case R.id.tv_tuikuan:
				Toast.makeText(getActivity(), "退款", Toast.LENGTH_SHORT).show();
				break;
		}
	}

}
