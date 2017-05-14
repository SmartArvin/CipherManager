package com.miyang.ciphermanager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class MyDialog extends Dialog{
	 Context context;
	    public MyDialog(Context context) {
	        super(context);
	        this.context = context;
	    }
	    public MyDialog(Context context, int theme){
	        super(context, theme);
	        this.context = context;
	    }
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        this.setContentView(R.layout.dialog_classify);
	    }

	}