package com.example.floyd.dogclicker.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
    View dividerBottom;
    View dividertTop;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.help_dialog_layout, container, false);
        cancelDialog = (TextView) rootView.findViewById(R.id.cancel_dialog_id);
        scrollView = (ScrollView) rootView.findViewById(R.id.dialog_scroller_id);
        dividerBottom = rootView.findViewById(R.id.divider_bottom_id);
        dividertTop = rootView.findViewById(R.id.divider_top_id);


        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener()
        {
            @Override
            public void onScrollChanged()
            {
                if (scrollView != null)
                {
//                    Log.i("zaza","scroll:"+scrollView.getScrollY());
                    Log.i("zaza","scroll Height:"+scrollView.getHeight());
                    Log.i("zaza","scroll child bottom : "+ scrollView.getChildAt(0).getBottom());
//                    int a = scrollView.getHeight() + scrollView.getScrollY();
//                    Log.i("zaza","scroll height + Y "+ a);
//
//                    System.out.println(String.valueOf(scrollView.getScrollY()));
//


                    int diff = (scrollView.getChildAt(0).getHeight() + scrollView.getPaddingBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

                    // if diff is zero, then the bottom has been reached
                    Log.i("zaza", "diff : " + diff);
                    Log.i("zaza", "padding bottom : " + scrollView.getPaddingBottom());

                    if (diff == 0)
                    {
                        dividerBottom.setVisibility(View.INVISIBLE);
                    } else
                    {
                        dividerBottom.setVisibility(View.VISIBLE);
                    }

                    Log.i("zaza", "Y : " + scrollView.getScrollY());
                    if (scrollView.getScrollY() == 0)
                    {
                        dividertTop.setVisibility(View.INVISIBLE);
                    } else
                    {
                        dividertTop.setVisibility(View.VISIBLE);
                    }

                }
            }
        });

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
