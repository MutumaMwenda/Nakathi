package com.example.nakathisacco.UtilitiesPackage;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.nakathisacco.R;


public class AppUtilits {

    private static ProgressDialog pDialog;




    public static void displayMessage(Context mContext, String message){

      MessageDialog messageDialog = null;
      if (messageDialog == null)
         messageDialog = new MessageDialog(mContext, message);
      messageDialog.displayMessageShow();

    }

    public static void showDialog(Activity activity) {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }
    public static void showDialog(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    public static void dismissDialog() {
        if (pDialog != null && pDialog.isShowing()) {


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    pDialog.dismiss();
                }
            }, 4000); // 3000 milliseconds delay
        }

    }

    public static void successDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.success_dialog, null, false);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Nakathi SACCO");
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();

        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }





}
