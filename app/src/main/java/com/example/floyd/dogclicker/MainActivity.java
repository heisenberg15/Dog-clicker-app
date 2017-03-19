package com.example.floyd.dogclicker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.floyd.dogclicker.dialog.HelpDialog;

public class MainActivity extends AppCompatActivity
{
    Toolbar mainActivityToolbar;
    SeekBar volumeSeekBar = null;
    ImageView clickView;
    MediaPlayer mp;
    SettingsActivity settingsActivity;
    private AudioManager audioManager = null;
    int action;
    int keyCode;
    TextView minutesView, secondsView;
    ImageView increaseMinutes, decreaseMinutes, increaseSeconds, decreaseSeconds;
    int minute, second;
    FloatingActionButton fab;
    private Handler handler;
    boolean timerIsOn = false;
    CountDownTimer countDownTimer;
    int timeLeft;
    private Vibrator vibrator;
    ConstraintLayout mainLayout;
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_main);
        retrySavedSettings();

        settingsActivity = new SettingsActivity();
        mp = MediaPlayer.create(MainActivity.this, R.raw.clicker_mono7);
        clickView = (ImageView) findViewById(R.id.click_image_id);
        volumeSeekBar = (SeekBar) findViewById(R.id.seekBar_id);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        minutesView = (TextView) findViewById(R.id.timer_minute_id);
        secondsView = (TextView) findViewById(R.id.timer_seconds_id);
        increaseMinutes = (ImageView) findViewById(R.id.arrow_up_minutes_id);
        decreaseMinutes = (ImageView) findViewById(R.id.arrow_down_minutes_id);
        increaseSeconds = (ImageView) findViewById(R.id.arrow_up_seconds_id);
        decreaseSeconds = (ImageView) findViewById(R.id.arrow_down_seconds_id);
        minute = Integer.parseInt(minutesView.getText().toString());
        second = Integer.parseInt(secondsView.getText().toString());
        fab = (FloatingActionButton) findViewById(R.id.fab_id);
        mainLayout = (ConstraintLayout) findViewById(R.id.activity_main);
        handler = new Handler();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_id);

        initToolbar();
        initControls();
        onButtonClick();
        initTimerControls();
    }


    private void initToolbar()
    {
        mainActivityToolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(mainActivityToolbar);
        getSupportActionBar().setTitle(R.string.dog_clicker);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_settings_id:
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.menu_settings_help_id:
                HelpDialog helpDialog = new HelpDialog();
                helpDialog.show(getFragmentManager(), "helpDialog");
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void initControls()
    {
        try
        {
            volumeSeekBar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekBar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        action = event.getAction();
        keyCode = event.getKeyCode();

//        System.out.println(SettingsActivity.switchIsOn);
        if (SettingsActivity.switchIsOn)
        {
            int action = event.getAction();
            int keyCode = event.getKeyCode();

            switch (keyCode)
            {
                case KeyEvent.KEYCODE_VOLUME_UP:
                    if (action == KeyEvent.ACTION_DOWN)
                    {
                        makeSound();
                    }
                    return true;
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    if (action == KeyEvent.ACTION_DOWN)
                    {
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
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
//        System.out.println(SettingsActivity.switchIsOn);
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP))
        {
            if (!SettingsActivity.switchIsOn)
            {
                volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            if (!SettingsActivity.switchIsOn)
            {
                volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN))
        {
            if (!SettingsActivity.switchIsOn)
            {
                volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
        {
            if (!SettingsActivity.switchIsOn)
            {
                volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    void onButtonClick()
    {
        clickView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                makeSound();
                return false;
            }
        });
    }


    void makeSound()
    {
        try
        {
            mp.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    void initTimerControls()
    {
        increaseMinutes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (timerIsOn)
                {
                    return;
                }

                if (minute != 40)
                {
                    minute++;
                    minutesView.setText(String.valueOf(minute));
                } else if (minute == 40)
                {
                    minute = 0;
                    minutesView.setText(String.valueOf(minute));
                }
            }
        });

        decreaseMinutes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (timerIsOn)
                {
                    return;
                }

                if (minute != 0)
                {
                    minute--;
                    minutesView.setText(String.valueOf(minute));
                } else if (minute == 0)
                {
                    minute = 40;
                    minutesView.setText(String.valueOf(minute));
                }
            }
        });

        increaseSeconds.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (timerIsOn)
                {
                    return;
                }

                if (second != 59)
                {
                    second++;
                    secondsView.setText(String.valueOf(second));
                } else if (second == 59)
                {
                    second = 0;
                    secondsView.setText(String.valueOf(second));
                }
            }
        });

        decreaseSeconds.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (timerIsOn)
                {
                    return;
                }

                if (second != 0)
                {
                    second--;
                    secondsView.setText(String.valueOf(second));
                } else if (second == 0)
                {
                    second = 59;
                    secondsView.setText(String.valueOf(second));
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                timerIsOn = !timerIsOn;
                timeLeft = (minute * 60) + second;


                if (timerIsOn)
                {
                    fab.setImageResource(R.drawable.ic_pause_white_24px);
                    countDownTimer = new CountDownTimer(timeLeft * 1000, 1000)
                    {
                        @Override
                        public void onTick(long millisUntilFinished)
                        {
                            secondsView.setText(String.valueOf(second));

                            if (second == 0 && minute != 0)
                            {
                                minute--;
                                minutesView.setText(String.valueOf(minute));
                                second = 60;
                            }

                            second--;
                        }

                        @Override
                        public void onFinish()
                        {
                            secondsView.setText(String.valueOf(0));
                            fab.setImageResource(R.drawable.ic_play_arrow_white_24px);

                            long[] pattern = {0, 400, 100, 400};

                            if (second != 0)
                            {
                                vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(pattern, -1);
                                Snackbar snackBar = Snackbar.make(coordinatorLayout, "Time's up", 7000);
//                                View view = snackBar.getView();
//                                TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
//                                textView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
                                snackBar.show();
                            }

                            second = 0;
                            minute = 0;
                            timerIsOn = false;
//                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                            if (notification != null)
//                            {
//                                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                                r.play();
//                            }\

                        }
                    }.start();

                } else
                {
                    pauseTimer();
                }

//                if (second == 1)
//                {
//                    second = 59;
//                }
//
//                if (second == 0 && minute != 0)
//                {
//                    minute--;
//                    minutesView.setText(minute);
//                }

//                timerIsOn = !timerIsOn;
//
//                if (!timerIsOn) {
//                    startTime = SystemClock.elapsedRealtime();
//                    System.out.println("Start Time: " + startTime);
//                }
//
//                if (timerIsOn) {
//                    difference = (SystemClock.elapsedRealtime() - startTime) / 1000 % 60;
//                    System.out.println("Difference: " + difference);
//                }
//
//                // Setting fab image
//                if (timerIsOn) {
//
//                    if (minute == 0 && second == 0) {
//                        return;
//                    }
//
//                    fab.setImageResource(R.drawable.ic_pause_white_24px);
//                } else {
//                    fab.setImageResource(R.drawable.ic_play_arrow_white_24px);
//                }
//
//                if (difference == 0) {
//                    isFastClick = true;
//                    System.out.println(isFastClick);
//                } else {
//                    isFastClick = false;
//                    System.out.println(isFastClick);
//                }
//
//                if (!isFastClick) {
//
//                    final Runnable runnable = new Runnable() {
//                        @Override
//                        public void run() {
//
//
//                            if (!timerIsOn) {
//                                minutesView.setText(String.valueOf(minute));
//                                secondsView.setText(String.valueOf(second));
//                                return;
//                            }
//
//                            if (minute == 0 && second == 0) {
//                                timerIsOn = false;
//                                return;
//                            }
//
//                            if (minute != 0 && second == 0) {
//                                minuteIsZero = false;
//
//                                if (!minuteIsZero) {
//                                    second = 60;
//                                    minute--;
//                                    secondsView.setText(String.valueOf(second));
//                                    minutesView.setText(String.valueOf(minute));
//                                }
//
//                            }
//
//                            second--;
//
//                            if (second == 0) {
//                                secondsView.setText(String.valueOf(second));
//                                if (minute == 0) {
//                                    timerIsOn = false;
//                                    fab.setImageResource(R.drawable.ic_play_arrow_white_24px);
//                                    return;
//                                }
//
//                                if (minuteIsZero) {
//                                    minute--;
//                                }
//
//                                minuteIsZero = true;
//
//                                if (minute == 0) {
//                                    second = 60;
//                                }
//                                minutesView.setText(String.valueOf(minute));
//                            } else {
//                                secondsView.setText(String.valueOf(second));
//                            }
//
//
//                            handler.postDelayed(this, 1000);
//
//                        }
//                    };
//
//
//                    handler.postDelayed(runnable, 1000);
//
//                }
            }
        });

    }


    private void pauseTimer()
    {
        countDownTimer.cancel();
        fab.setImageResource(R.drawable.ic_play_arrow_white_24px);
    }


    private void retrySavedSettings()
    {
        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
        SettingsActivity.switchIsOn = settings.getBoolean("switchIsOn", false);
    }

}
