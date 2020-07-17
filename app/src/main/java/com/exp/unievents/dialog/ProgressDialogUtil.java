package com.exp.unievents.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatTextView;

import com.exp.unievents.R;

public class ProgressDialogUtil {

  public Dialog createDialog(Context context, String message) {
    final AppCompatDialog progressDialog = new AppCompatDialog(context);
    progressDialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    progressDialog.setContentView(R.layout.custom_progress_dialog);
    if (progressDialog.getWindow()!= null)
      progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    if (message != null)
      ((AppCompatTextView) progressDialog.findViewById(R.id.tv_progress_text)).setText(message);
    else
      progressDialog.findViewById(R.id.tv_progress_text).setVisibility(View.GONE);
    return progressDialog;
  }

}
