package com.example.floyd.dogclicker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    Toolbar mainActivityToolbar;
    SeekBar volumeSeekBar = null;
    ImageView clickView;
    MediaPlayer mp;
    SettingsActivity settingsActivity;
    private AudioManager audioManager = null;
    int action;
    int keyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_main);
        retrySavedSettings();

        settingsActivity = new SettingsActivity();
        mp = MediaPlayer.create(MainActivity.this, R.raw.louder10);
        clickView = (ImageView) findViewById(R.id.click_image_id);
        volumeSeekBar = (SeekBar) findViewById(R.id.seekBar_id);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        initToolbar();
        initControls();
        onButtonClick();

    }


    private void initToolbar() {
        mainActivityToolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(mainActivityToolbar);
        getSupportActionBar().setTitle(R.string.dog_clicker);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings_id:
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void initControls() {
        try {
            volumeSeekBar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekBar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        action = event.getAction();
        keyCode = event.getKeyCode();

//        System.out.println(SettingsActivity.switchIsOn);
        if (SettingsActivity.switchIsOn) {
            int action = event.getAction();
            int keyCode = event.getKeyCode();

            switch (keyCode) {
                case KeyEvent.KEYCODE_VOLUME_UP:
                    if (action == KeyEvent.ACTION_DOWN) {
                        makeSound();
                    }
                    return true;
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    if (action == KeyEvent.ACTION_DOWN) {
                        makeSound();
                    }
                    return true;
                default:
                    return super.dispatchKeyEvent(event);
            }
        }

        return super.dispatchKeyEvent(event);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        System.out.println(SettingsActivity.switchIsOn);
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            if (!SettingsActivity.switchIsOn) {
                volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (!SettingsActivity.switchIsOn) {
                volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            if (!SettingsActivity.switchIsOn) {
                volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            if (!SettingsActivity.switchIsOn) {
                volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    void onButtonClick() {
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSound();
            }
        });
    }


    void makeSound() {
        try {
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void retrySavedSettings() {
        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
        SettingsActivity.switchIsOn = settings.getBoolean("switchIsOn", false);
    }

}
