package com.example.floyd.dogclicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    Toolbar toolbar;
    Switch aSwitch;

    static int switchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        aSwitch = (Switch) findViewById(R.id.switch_id);

        switchState = 1;
//        setSwitchState(1);
        initToolbar();
//        checkSwitch();
    }

    void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.settings_toolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.settings);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void checkSwitch() {
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setSwitchState(1);
                } else {
                    setSwitchState(0);
                }
            }
        });
    }

    public int getSwitchState() {
        return switchState;
    }

    public void setSwitchState(int switchState) {
        this.switchState = switchState;
    }

}
