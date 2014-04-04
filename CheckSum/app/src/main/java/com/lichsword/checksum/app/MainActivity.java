package com.lichsword.checksum.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.zip.Adler32;
import java.util.zip.CRC32;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText repeatCountEditText;
    private TextView stateTextView;
    private Button crc32Button;
    private Button adlerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repeatCountEditText = (EditText) findViewById(R.id.RepeatCount);
        stateTextView = (TextView) findViewById(R.id.tv_state);
        crc32Button = (Button) findViewById(R.id.CRC32Button);
        adlerButton = (Button) findViewById(R.id.AdlerButton);
        crc32Button.setOnClickListener(this);
        adlerButton.setOnClickListener(this);
        initData();
    }

    private byte[] data;

    private void initData() {
//        data = new byte[127];
//        for (byte i = 0; i < data.length; i++) {
//            data[i] = i;
//        }
//        String string = "asdhjasjdashdasjkdhjkashdjkasjkhdhkasdhasdhksakhdaskhdkhasdhkaskhdaskhdas" +
//                "dasdaksjdhkashdksakhjdhjksahkjdsakhdkhjsadkhjasd" +
//                "sadksahdksadkjasjdhjsadjksakdkjsadhksadkasdqwfgewhjgfuegfkefhehjffqfkjqwkfhkqwf" +
//                "dqwfgewhjgfuegfkefhehjffqfkjqwkfhkqwfdqwfgewhjgfuegfkefhehjffqfkjqwkfhkqwf" +
//                "fhqjwhfkhqjwfwqjdhqwfhjqkwjfhkjqwfhjkqwhjkfhqjwkfkhqwfhkqwufiquwfhqwf" +
//                "fhqjwhfkhqjwfwqjdhqwfhjqkwjfhkjqwfhjkqwhjkfhqjwkfkhqwfhkqwufiquwfhqwf" +
//                "fhqjwhfkhqjwfwqjdhqwfhjqkwjfhkjqwfhjkqwhjkfhqjwkfkhqwfhkqwufiquwfhqwf" +
//                "fhqjwhfkhqjwfwqjdhqwfhjqkwjfhkjqwfhjkqwhjkfhqjwkfkhqwfhkqwufiquwfhqwf" +
//                "fhqjwhfkhqjwfwqjdhqwfhjqkwjfhkjqwfhjkqwhjkfhqjwkfkhqwfhkqwufiquwfhqwf" +
//                "fhqjwhfkhqjwfwqjdhqwfhjqkwjfhkjqwfhjkqwhjkfhqjwkfkhqwfhkqwufiquwfhqwf";
        String string = "helloworldshowmethemoneygoodbye";

        Log.d(TAG, "string=" + string);
        data = string.getBytes();

    }

    private long start;
    private long end;
    private long value;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CRC32Button:
                start = System.currentTimeMillis();
                int count = getRepeatCount();
                for (int i = 0; i < count; i++) {
                    CRC32 crc32 = new CRC32();
                    crc32.update(data);
                    value = crc32.getValue();
                }
                end = System.currentTimeMillis();
                stateTextView.setText(dump());
                break;
            case R.id.AdlerButton:
                start = System.currentTimeMillis();
                count = getRepeatCount();
                for (int i = 0; i < count; i++) {
                    Adler32 adler32 = new Adler32();
                    adler32.update(data);
                    value = adler32.getValue();
                }
                end = System.currentTimeMillis();
                stateTextView.setText(dump());
                break;
            default:
                break;
        }
    }

    private int getRepeatCount() {
        String value = repeatCountEditText.getText().toString();
        return Integer.valueOf(value);
    }

    private String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append("循环次数：" + getRepeatCount());
        sb.append('\n');
        sb.append("费时(ms)：" + (end - start));
        sb.append('\n');
        sb.append("值：" + value);
        return sb.toString();
    }
}
