package com.example.y.ansyctask_demo;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * AsyncTask三个参数：
 * 第一个是执行耗时操作时需要从外部传入的参数
 * 第二个是更新Progressbar时传递的参数
 * 第三个是doInBackground()方法执行完后返回的参数
 */

public class MyAsyncTask extends AsyncTask<Integer,Integer,String> {
    private TextView mTextView;
    private ProgressBar mPrgbar;

    public MyAsyncTask(TextView mTextView, ProgressBar mPrgbar) {
        this.mTextView = mTextView;
        this.mPrgbar = mPrgbar;
    }

    /**
     * onPreExecute()方法调用之后调用这个方法
     * 运行在子线程中 进行一些耗时操作
     * 调用方法publishProgress()更新Progressbar
     * @param integers
     * @return
     */
    @Override
    protected String doInBackground(Integer... integers) {
        for (int i = 10; i <= 100; i+=10) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        return "完成";
    }

    /**
     * 在执行耗时操作之前调用
     * 此方法在ui线程中执行 可以在此进行一些ui上的操作
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mTextView.setText("异步线程执行中...");
    }

    /**
     * doInBackgroud()方法执行完后调用此方法
     * 在ui线程中执行
     * @param s——接收doInBackgroud()方法返回的值
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        mTextView.setText(s);
    }

    /**
     * 根据doInBackgroud()方法中调用publishProgress()传进来的参数更新progressbar
     * @param values 接收publishProgress()传过来的参数
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        mPrgbar.setProgress(values[0]);
    }
}
