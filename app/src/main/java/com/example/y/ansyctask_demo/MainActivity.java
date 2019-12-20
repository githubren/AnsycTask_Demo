package com.example.y.ansyctask_demo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.y.aidl_demo.IPerson;

public class MainActivity extends AppCompatActivity {
    private TextView mTitle;
    private ProgressBar mProgbar;
    private Button mBtn;

    private EditText mEntryContent;
    private Button mQuery;
    private TextView mResult;

    private IPerson mIPerson;
    private PersonConnection conn = new PersonConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAsyncTask task = new MyAsyncTask(mTitle,mProgbar);
                task.execute(1);
            }
        });

        Intent service = new Intent("android.intent.action.AIDLService");
        service.setPackage("com.example.y.aidl_demo");

        bindService(service,conn,BIND_AUTO_CREATE);
        mQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = mEntryContent.getText().toString();
                int number = Integer.parseInt(num);
                try {
                    mResult.setText(mIPerson.queryPerson(number));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mEntryContent.setText("");
            }
        });
    }

    private void initView() {
        mTitle = findViewById(R.id.title);
        mProgbar = findViewById(R.id.prgbar);
        mBtn = findViewById(R.id.start_btn);

        mEntryContent = findViewById(R.id.edit_text);
        mQuery = findViewById(R.id.btn_query);
        mResult = findViewById(R.id.result_tv);
    }

    private final class PersonConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIPerson = IPerson.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIPerson = null;
        }
    }
}
