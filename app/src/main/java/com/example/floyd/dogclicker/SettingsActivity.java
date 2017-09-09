package com.example.floyd.dogclicker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity
{
    Toolbar toolbar;
    Switch aSwitch;
    View toggleSwitchView;
    static boolean switchIsOn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        aSwitch = (Switch) findViewById(R.id.switch_id);
        toggleSwitchView = findViewById(R.id.toggle_switch_view_id);

        toggleSwitchView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                aSwitch.toggle();
            }
        });

        setSwitchUi();
        initToolbar();
        checkSwitch();
    }


    void initToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.settings_toolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.settings);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void checkSwitch()
    {
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                switchIsOn = isChecked;
            }
        });
    }


    @Override
    protected void onStop()
    {
        if (switchIsOn)
        {
            SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("switchIsOn", true);
            editor.commit();
        } else
        {
            SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("switchIsOn", false);
            editor.commit();
        }

        super.onStop();
    }

    private void setSwitchUi()
    {
        if (switchIsOn)
        {
            aSwitch.setChecked(true);
        } else
        {
            aSwitch.setChecked(false);
        }
    }
}
