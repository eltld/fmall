package com.example.tmall;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class PayPopupWindow implements OnClickListener,OnDismissListener{
	
	private Context mContext;
	private OnPayWindowClickListener mListener;
	private PopupWindow popupWindow; 
	private TextView tv_pay_ok,tv_price;
	private ImageView iv_close;
	
	public PayPopupWindow(Context mContext,String totalPrice) {
		this.mContext = mContext;
		View view=LayoutInflater.from(mContext).inflate(R.layout.pay_popupwindow, null);
		tv_pay_ok=(TextView) view.findViewById(R.id.pay_ok);
		tv_price=(TextView) view.findViewById(R.id.pay_tv_price);
		tv_price.setText(totalPrice+"元");
		tv_pay_ok.setOnClickListener(this);
		iv_close=(ImageView) view.findViewById(R.id.pay_iv_close);
		iv_close.setOnClickListener(this);
		popupWindow=new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.setOnDismissListener(this);
	}

	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_ok:
			//ProgressDialog dialog = ProgressDialog.show(mContext, "提示", "正在处理");
			mListener.onClick();
			dismiss();
			break;
			
		case R.id.pay_iv_close:
			dismiss();
			break;

		default:
			break;
		}
		
	}

	/**弹窗显示的位置*/  
	public void showAsDropDown(View parent){
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}
	
	/**消除弹窗*/
	public void dismiss(){
		popupWindow.dismiss();
	}
	@Override
	public void onDismiss() {
		// TODO Auto-generated method stub
		
	}
	
	public void setOnClickListener(OnPayWindowClickListener mListener){
		this.mListener=mListener;
	}
	
	public interface OnPayWindowClickListener{
		public void onClick();
	}
}
