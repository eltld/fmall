package com.example.tmall.view;

import com.example.tmall.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
* city_choose_dialog
*
*/
public class CityDialog implements OnClickListener{
	private OnCityChooseListener mOnCityChooseListener;
	private Dialog dialog;
	private Button sure,cancel;
	private TextView tv_title;
	private CityPicker cityPicker;
	/**
	* init the dialog
	* @return
	*/
	public CityDialog(Context context) {
		dialog = new Dialog(context, R.style.city_dialog);
		dialog.setContentView(R.layout.city_dialog);
		cityPicker=(CityPicker) dialog.findViewById(R.id.citypicker);
		tv_title = (TextView) dialog.findViewById(R.id.city_tv_title);
		tv_title.setText("Ñ¡Ôñ³ÇÊÐ");
		sure = (Button) dialog.findViewById(R.id.city_btn_sure);
		cancel = (Button) dialog.findViewById(R.id.city_btn_cancel);
		sure.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	public interface OnCityChooseListener {
		public void onChoose(String city);
	}
	public void setOnCityChooseListener(OnCityChooseListener onCityChooseListener) {
		this.mOnCityChooseListener = onCityChooseListener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.city_btn_cancel:
			
			dismiss();
			break;
		case R.id.city_btn_sure:
			mOnCityChooseListener.onChoose(cityPicker.getCity_string());
			dismiss();
			break;
		default:
			break;
		}
		
	}
	
	public void show() {
		dialog.show();
	}
	public void hide() {
		dialog.hide();
	}
	public void dismiss() {
		dialog.dismiss();
	}
} 