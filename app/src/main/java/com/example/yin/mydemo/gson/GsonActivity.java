package com.example.yin.mydemo.gson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yin.mydemo.http.OkhttpHelp;
import com.example.yin.mydemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GsonActivity extends AppCompatActivity {
    private static final String TAG = "GsonActivity";

    private String URL1="https://test.kaisuobao.net/api/api.login.index.php?";
    private String URL2="https://test.kaisuobao.net/api/api.lock.index.php?";

    @BindView(R.id.jsonobject_btn)
    Button jsonobjectBtn;
    @BindView(R.id.jsonobject_tv)
    TextView jsonobjectTv;
    @BindView(R.id.jsonarray_btn)
    Button jsonarrayBtn;
    @BindView(R.id.jsonarray_tv)
    TextView jsonarrayTv;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x001:
                    JsonObjectBean job= (JsonObjectBean) msg.obj;
                    jsonobjectTv.setText("r:/n"+job.getR()+"msg：/n"+job.getMsg());
                    break;
                case 0x002:
                    List<JsonArrayBean> list= (List<JsonArrayBean>) msg.obj;
                    jsonarrayTv.setText(list.get(0).getName());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.jsonobject_btn, R.id.jsonarray_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jsonobject_btn:
                getJsonObject();
                break;
            case R.id.jsonarray_btn:
                getJsonArray();
                break;
        }
    }
    private void getJsonObject(){
        RequestBody requestBody=new FormBody.Builder()
                .add("username","15158193846")
                .add("password","123456")
                .build();
        OkhttpHelp.sendOkhttpPost(URL1,requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonstr=response.body().string();
                Log.i(TAG, "jsonobject: "+jsonstr);
                Gson gson=new Gson();
                JsonObjectBean job=gson.fromJson(jsonstr,JsonObjectBean.class);
                Message message=new Message();
                message.obj=job;
                message.what=0x001;
                handler.sendMessage(message);
            }
        });
    }

    private void getJsonArray(){
        RequestBody requestbody=new FormBody.Builder()
                .add("cat_name","门锁")
                .add("city_name","杭州")
                .build();
        OkhttpHelp.sendOkhttpPost(URL2, requestbody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonstr=response.body().string();
                Log.i(TAG, "jsonarray: "+jsonstr);
                try {
                    JSONObject obj=new JSONObject(jsonstr);
                    JSONArray array=new JSONArray(obj.optString("data"));
                    Log.i(TAG, "data: "+array.toString());

                    Gson gson=new Gson();
                    List<JsonArrayBean> list=gson.fromJson(array.toString(),new TypeToken<List<JsonArrayBean>>(){}.getType());
                    Message msg=new Message();
                    msg.what=0x002;
                    msg.obj=list;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
