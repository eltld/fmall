package com.example.tmall.view;

import com.example.tmall.R;
import com.example.tmall.bean.Product;

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
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

/**
 * ������浯��
 */
public class DetailPopupWindow implements OnDismissListener, OnClickListener {
	private PopupWindow popupWindow;
	private Product product;
	
	private TextView pop_choice_black,pop_choice_white,pop_add,pop_reduce,pop_num,pop_ok,tv_price_stock;
	private ImageView pop_del;
	
	private OnWindowClickListener listener;
	private Context context;
	/**����ѡ�����ɫ������*/
	private String str_color="";
	/*��������*/
	private int num=1;
	
	public DetailPopupWindow(Context context,Product product) {
		this.product=product;
		this.context=context;
		View view=LayoutInflater.from(context).inflate(R.layout.detail_popupwindow, null);
		tv_price_stock=(TextView) view.findViewById(R.id.popup_tv_price_stock);
		tv_price_stock.setText("��"+product.getProPrice()+"Ԫ      ���"+product.getProStock()+"��");
		pop_choice_black=(TextView) view.findViewById(R.id.pop_choice_black);
		pop_choice_white=(TextView) view.findViewById(R.id.pop_choice_white);
		pop_add=(TextView) view.findViewById(R.id.pop_add);
		pop_reduce=(TextView) view.findViewById(R.id.pop_reduce);
		pop_num=(TextView) view.findViewById(R.id.pop_num);
		pop_ok=(TextView) view.findViewById(R.id.pop_ok);
		pop_del=(ImageView) view.findViewById(R.id.pop_del);
		
		pop_choice_black.setOnClickListener(this);
		pop_choice_white.setOnClickListener(this);
		pop_add.setOnClickListener(this);
		pop_reduce.setOnClickListener(this);
		pop_ok.setOnClickListener(this);
		pop_del.setOnClickListener(this);
		
		
		
		popupWindow=new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		//����popwindow�Ķ���Ч��
		popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.setOnDismissListener(this);// ��popWindow��ʧʱ�ļ���
	}
	
	
	public interface OnWindowClickListener{
		/** ���õ��ȷ�ϰ�ťʱ�����ӿ� */
		public void onClickOK();
	}

	/**���ü���*/
	public void setOnWindowClickListener(OnWindowClickListener listener){
		this.listener=listener;
	}
	
	
	// ��popWindow��ʧʱ��Ӧ
	@Override
	public void onDismiss() {
		
	}
	
	/**������ʾ��λ��*/  
	public void showAsDropDown(View parent){
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}
	
	/**��������*/
	public void dissmiss(){
		popupWindow.dismiss();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.pop_choice_black:
			
			pop_choice_black.setBackgroundResource(R.drawable.yuanjiao_choice);
			pop_choice_white.setBackgroundResource(R.drawable.yuanjiao);
			str_color=pop_choice_black.getText().toString();
			break;
		case R.id.pop_choice_white:
			
			pop_choice_black.setBackgroundResource(R.drawable.yuanjiao);
			pop_choice_white.setBackgroundResource(R.drawable.yuanjiao_choice);
			str_color=pop_choice_white.getText().toString();
			break;
		case R.id.pop_add:
			if (!pop_num.getText().toString().equals("400")) {
				
				num=Integer.valueOf(pop_num.getText().toString())+1;
				pop_num.setText(""+num);
				product.setProCount(num);
			}else {
				Toast.makeText(context, "���ܳ�������Ʒ����", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.pop_reduce:
			if (!pop_num.getText().toString().equals("1")) {
				num=Integer.valueOf(pop_num.getText().toString())-1;
				pop_num.setText(""+num);
				product.setProCount(num);
			}else {
				Toast.makeText(context, "�����������ܵ���1��", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.pop_del:
			dissmiss();
			
			break;
		case R.id.pop_ok:
			listener.onClickOK();
			dissmiss();
				
			break;

		default:
			break;
		}
	}

}
