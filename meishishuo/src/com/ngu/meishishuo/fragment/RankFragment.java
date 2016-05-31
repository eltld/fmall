package com.ngu.meishishuo.fragment;

import com.ngu.meishishuo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @author zhoufeng06@qq.com
 * @time 2016年5月30日-上午10:19:25
 * 排行
 */
public class RankFragment extends Fragment{
	private RadioGroup rg_top;//顶部标签
	private RadioButton rb_week,rb_month,rb_all;//周，月，总排行
	private MeiShiFragment weekfragment,monthfragment,allfragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//
		View v=inflater.inflate(R.layout.fragment_rank, container,false);
		initView(v);
		initEvent();
		return v;
	}
	private void initView(View v){
		rg_top=(RadioGroup) v.findViewById(R.id.radiogroup_top);
		rb_week=(RadioButton) v.findViewById(R.id.rb_week);
		rb_month=(RadioButton) v.findViewById(R.id.rb_month);
		rb_all=(RadioButton) v.findViewById(R.id.rb_all);
		FragmentManager fragmentmanager=getFragmentManager();
		FragmentTransaction beginTransaction = fragmentmanager.beginTransaction();
		weekfragment=new MeiShiFragment("64","首页");
		rb_week.setTextColor(getActivity().getResources().getColor(R.color.white));
		beginTransaction.add(R.id.rank_container, weekfragment,"week");
		beginTransaction.commit();
	}
	private void initEvent(){
		rg_top.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 
				FragmentManager fragmentmanager=getFragmentManager();
				FragmentTransaction beginTransaction = fragmentmanager.beginTransaction();
				
				switch (checkedId) {
				case R.id.rb_week://周排行
					//设置文字颜色
					rb_week.setTextColor(getResources().getColor(R.color.white));
					rb_month.setTextColor(getResources().getColor(R.color.black));
					rb_all.setTextColor(getResources().getColor(R.color.black));
					//
					weekfragment=(MeiShiFragment) fragmentmanager.findFragmentByTag("week");	
					beginTransaction.show(weekfragment);
					if(allfragment!=null){
						beginTransaction.hide(allfragment);
					}
					if(monthfragment!=null){
						beginTransaction.hide(monthfragment);
					}
					beginTransaction.commit();
					break;

				case R.id.rb_month://月排行
					//设置文字颜色
					rb_week.setTextColor(getResources().getColor(R.color.black));
					rb_month.setTextColor(getResources().getColor(R.color.white));
					rb_all.setTextColor(getResources().getColor(R.color.black));
					monthfragment=(MeiShiFragment) fragmentmanager.findFragmentByTag("month");
					if(monthfragment==null){
						monthfragment=new MeiShiFragment("65","首页");
						beginTransaction.add(R.id.rank_container, monthfragment,"month");
						
					}
					//
					beginTransaction.show(monthfragment);
					beginTransaction.hide(weekfragment);
					if(allfragment!=null){
						beginTransaction.hide(allfragment);
					}
					beginTransaction.commit();
					break;
				case R.id.rb_all://总排行
					//设置文字颜色
					rb_week.setTextColor(getResources().getColor(R.color.black));
					rb_month.setTextColor(getResources().getColor(R.color.black));
					rb_all.setTextColor(getResources().getColor(R.color.white));
					//
					allfragment=(MeiShiFragment) fragmentmanager.findFragmentByTag("all");
					if(allfragment==null){
						allfragment=new MeiShiFragment("66","首页");
						beginTransaction.add(R.id.rank_container, allfragment,"all");
						
					}
					//显示allfragment
					beginTransaction.show(allfragment);
					beginTransaction.hide(weekfragment);
					if(monthfragment!=null){
						beginTransaction.hide(monthfragment);
					}
					beginTransaction.commit();
					break;

				default:
					break;
				}
			}
		});
	}
}
