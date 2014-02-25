package com.lichsword.filescan.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mStateTextView;

    private final String FORMAT_FOUND_DIR = "%d：已扫描目录（个）\n";
    private final String FORMAT_FOUND_FILE = "%d：已扫描文件（个）\n";
    private final String FORMAT_FOUND_APK = "%d：已扫描APK（个）\n";
    private final String FORMAT_TIME = "%dmin:%ds：扫描时间（分：秒）\n";
    private final String FORMAT_AVERAGE_SPEED = "%d：平均扫描速度（个/秒）\n";
    private final String FORMAT_MAX_SPEED = "%d：最高扫描速度（个/秒）\n";

    private int time;// second
    private int dir;
    private int file;
    private int apk;
    private int maxSpeed;
    private int avgSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStateTextView = (TextView) findViewById(R.id.tv_state);
        onDisplay();
    }

    private void onDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(FORMAT_FOUND_DIR, dir));
        sb.append(String.format(FORMAT_FOUND_FILE, file));
        sb.append(String.format(FORMAT_FOUND_APK, apk));
        sb.append(String.format(FORMAT_TIME, time/60, time%60));
        sb.append(String.format(FORMAT_AVERAGE_SPEED, avgSpeed));
        sb.append(String.format(FORMAT_MAX_SPEED, maxSpeed));
        mStateTextView.setText(sb.toString());
    }

}
