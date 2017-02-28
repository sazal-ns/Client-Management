/*
 * Copyright (c) 2017. By RTSoftBD.
 * Author: Noor Nabiul Alam Siddiqui
 */

package com.rtsoftbd.siddiqui.clientmanagement.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by RTsoftBD_Siddiqui on 2017-02-06.
 */

public class ShowDialog extends AppCompatActivity{
    Context context;
    String  content,title;
    Drawable icon;
    Boolean is;

    public ShowDialog(Context context, String title, String content,Boolean is, Drawable icon) {
        this.title = title;
        this.context = context;
        this.content = content;
        this.icon = icon;
        this.is = is;

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .icon(icon)
                .cancelable(is);
        MaterialDialog dialog = builder.build();
        dialog.show();
    }
}
