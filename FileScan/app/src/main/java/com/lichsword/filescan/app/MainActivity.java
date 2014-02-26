package com.lichsword.filescan.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private TextView mStateTextView;

    private final String FORMAT_FOUND_DIR = "%d：已扫描目录（个）\n";
    private final String FORMAT_FOUND_FILE = "%d：已扫描文件（个）\n";
    private final String FORMAT_FOUND_APK = "%d：已扫描APK（个）\n";
    private final String FORMAT_TIME = "%02dmin:%02ds：扫描时间（分：秒）\n";
    private final String FORMAT_AVERAGE_SPEED = "%d：平均扫描速度（个/秒）\n";
    private final String FORMAT_MAX_SPEED = "%d：最高扫描速度（个/秒）\n";
    private final String FORMAT_TIME_IO = "%09d：IO时间（毫秒）\n";
    private final String FORMAT_TIME_MEM = "%d：内存时间（毫秒）\n";

    private Timer mTimer;
    private int time;// second
    private int allFileNum;
    private int dirNum;
    private int fileNum;
    private int apkNum;
    private int maxSpeed;
    private int avgSpeed;
    private long timeOnIO;
    private long timeOnMem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStateTextView = (TextView) findViewById(R.id.tv_state);
        onDisplay();
        if (null == mTask || !mTask.isRunning()) {
            mTask = new MyAsyncTask();
            mTask.execute();
        } else {
            Log.d("", "task is running");
        }
    }

    private MyAsyncTask mTask;

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        boolean running = false;

        public boolean isRunning() {
            return running;
        }

        @Override
        protected void onPreExecute() {
            running = true;
            super.onPreExecute();
        }

        long start, end;

        @Override
        protected Void doInBackground(Void... params) {
            // TODO
            String sdcardState = Environment.getExternalStorageState();
            boolean sdCardExist = sdcardState.equals(android.os.Environment.MEDIA_MOUNTED);
            if (sdCardExist) {
                // TODO
                File rootDir = Environment.getExternalStorageDirectory();//获取跟目录
                // ------------- start process
                // -------- prepare
                mTimer = new Timer(true);
                mTimer.schedule(task, 1000, 1000); //延时1000ms后执行，1000ms执行一次
                // --------
                mDirStack.push(rootDir);

                while (!mDirStack.isEmpty()) {
                    File dir = mDirStack.pop();
                    //
                    start = System.currentTimeMillis();

                    boolean isDir = dir.isDirectory();

                    end = System.currentTimeMillis();
                    timeOnIO += (end - start);
                    //
                    if (!isDir) {
                        throw new IllegalArgumentException("not dir");
                    } else {
                        end = System.currentTimeMillis();
                        timeOnIO += (end - start);
                        //
                        start = System.currentTimeMillis();

                        File[] childs = null;
                        if(null==dir || null==dir.listFiles()){
                            continue;
                        }else{
                            childs = dir.listFiles().clone();
                        }

                        if (null== childs || 0==childs.length){
                            continue;
                        }// end if

                        end = System.currentTimeMillis();
                        timeOnIO += (end - start);
                        //
                        for (File file : childs) {
                            allFileNum++;
                            //
                            start = System.currentTimeMillis();
                            isDir = file.isDirectory();
                            end = System.currentTimeMillis();
                            timeOnIO += (end - start);
                            //
                            if (isDir) {
                                mDirStack.push(file);
                                dirNum++;
                            } else {
                                fileNum++;
                                // ------ process file ------
                                String fileName = file.getName();
                                String lowerName = fileName.toLowerCase();
                                if (lowerName.endsWith(APK)) {
                                    apkNum++;
                                } else {
                                    // do nothing
                                }
                                // ------ process file ------
                            }
                            publishProgress();// refresh state
                        }
                        // finish scan all child fileNum
                    }
                }// end while
                // ------------- end process
                Log.i("", "sdcard exist");
            } else {
                Log.e("", "sdcard not exist");
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            onDisplay();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            running = false;
        }
    }

    private LinkedList<File> mDirStack = new LinkedList<File>();

    private static final String APK = ".apk";

    TimerTask task = new TimerTask() {
        public void run() {
            time++;
            avgSpeed = allFileNum / time;
            if (maxSpeed < avgSpeed) {
                maxSpeed = avgSpeed;
            }//end if
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mTimer) {
            mTimer.cancel();//退出计时器
        }// end if
    }

    private long lastDisplayTime = 0;

    private void onDisplay() {
        long now = System.currentTimeMillis();
        if (now - lastDisplayTime > 333) {
            lastDisplayTime = now;

            StringBuilder sb = new StringBuilder();
            sb.append(String.format(FORMAT_FOUND_DIR, dirNum));
            sb.append(String.format(FORMAT_FOUND_FILE, fileNum));
            sb.append(String.format(FORMAT_FOUND_APK, apkNum));
            sb.append(String.format(FORMAT_TIME, time / 60, time % 60));
            sb.append(String.format(FORMAT_AVERAGE_SPEED, avgSpeed));
            sb.append(String.format(FORMAT_MAX_SPEED, maxSpeed));
            sb.append(String.format(FORMAT_TIME_IO, timeOnIO));
            mStateTextView.setText(sb.toString());
        } else {
            // wait
        }

    }

}
