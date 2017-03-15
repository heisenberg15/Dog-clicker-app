package com.example.floyd.dogclicker.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.floyd.dogclicker.R;

 /**
 * Created by floyd on 3/13/2017.
 */

public class HelpDialog extends DialogFragment
{

    View rootView;
    TextView cancelDialog;
    ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.help_dialog_layout, container, false);
        cancelDialog = (TextView) rootView.findViewById(R.id.cancel_dialog_id);
        scrollView = (ScrollView) rootView.findViewById(R.id.dialog_scroller_id);


        dismissDialog();

        return rootView;
    }


    private void dismissDialog()
    {
        cancelDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
    }

}
