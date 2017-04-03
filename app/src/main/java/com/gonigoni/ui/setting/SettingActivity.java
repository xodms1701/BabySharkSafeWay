package com.gonigoni.ui.setting;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gonigoni.babysharksafeway.R;
import com.gonigoni.utill.LoopService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.text_dB)
    TextView text_dB;
    @BindView(R.id.checkBox)
    RadioButton checkBox;
    @BindView(R.id.radioButton)
    RadioButton radioButton;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);


        text_dB.setText(String.valueOf(140) + "dB");

        if (!isServiceRunningCheck()) {
            radioButton.setChecked(true);
        } else {
            checkBox.setChecked(true);
        }

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startService(new Intent(this, LoopService.class));
            }
        });



        radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //stopService(new Intent("com.gonigoni.utill.LoopService"));
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text_dB.setText(Integer.toString(progress + 60) + "dB");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }


    public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.gonigoni.utill.LoopService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}
