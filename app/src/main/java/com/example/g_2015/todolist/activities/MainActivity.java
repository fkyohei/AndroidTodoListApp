package com.example.g_2015.todolist.activities;

import com.example.g_2015.todolist.R;
import com.example.g_2015.todolist.api.AddTask;
import com.example.g_2015.todolist.api.DeleteTask;
import com.example.g_2015.todolist.api.ListTask;
import com.example.g_2015.todolist.api.todoApi.model.Todo;
import com.example.g_2015.todolist.api.todoApi.model.TodoCollection;
import com.example.g_2015.todolist.views.TodoListAdapter;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;
import android.content.SharedPreferences;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.List;




public class MainActivity extends ActionBarActivity {

    public static final int EDIT_REQUEST_CODE = 0x111;

    EditText todoAddEdit;

    Button todoAddButton;

    ListView todoListView;

    TodoListAdapter todoListAdapter;

    private final String PROJECT_ID = "440728337053";
    AsyncTask<Void, Void, String> registtask = null;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public GoogleCloudMessaging gcm;
    public String regid = "";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoAddEdit = (EditText) findViewById(R.id.todo_add_edit);
        todoAddButton = (Button) findViewById(R.id.todo_add_button);
        todoListView = (ListView) findViewById(R.id.list_view);

        loadTodoList();
    }

    private void loadTodoList() {
        new ListTask() {
            @Override
            protected void onPostExecute(TodoCollection todoCollection) {
                super.onPostExecute(todoCollection);
                setupUi(todoCollection);
            }
        }.execute();
    }

    private void reloadTodoList() {
        if (todoListAdapter != null) {
            todoListAdapter.clear();
            loadTodoList();
        }
    }

    private void setupUi(TodoCollection todoCollection) {

        todoListAdapter = new TodoListAdapter(this);
        if (todoCollection != null) {
            List<Todo> todoList = todoCollection.getItems();
            if (todoList != null) {
                for (Todo todo : todoList) {
                    todoListAdapter.add(todo);
                }
            }
        }
        todoListView.setAdapter(todoListAdapter);
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = todoListAdapter.getItem(position);
                openTodoEdit(todo);
            }
        });
        todoAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = todoAddEdit.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    todoAddEdit.setText("");
                    addTodo(text);
                }
            }
        });
    }

    private void openTodoEdit(Todo todo) {
        try {
            startActivityForResult(TodoEditActivity.createIntent(this, todo), EDIT_REQUEST_CODE);
        } catch (IOException e) {
            Toast.makeText(this, "error occurred : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                reloadTodoList();
            }
        }
    }

    private void addAdapter(Todo todo) {
        if (todo != null) {
            todoListAdapter.add(todo);
            todoListAdapter.notifyDataSetChanged();
        }
    }

    private void addTodo(String todoText) {
        Todo todo = new Todo();
        todo.setText(todoText);
        new AddTask() {
            @Override
            protected void onPostExecute(Todo todo) {
                addAdapter(todo);
            }
        }.execute(todo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteCheckedTodo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteCheckedTodo() {
        final List<Todo> checkedTodoList = todoListAdapter.getCheckedTodoList();
        new DeleteTask() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                for (Todo todo : checkedTodoList) {
                    todoListAdapter.remove(todo);
                }
            }
        }.execute(checkedTodoList.toArray(new Todo[0]));
    }

    @Override
    public void onStart() {
        super.onStart();

        context = getApplicationContext();

        // Play serviceが有効かチェック
        if (checkPlayServices()) {

            gcm = GoogleCloudMessaging.getInstance(context);
            regid = getRegistrationId(context);

            if(regid.equals("")){

                regist_id();
            }

        } else {
            Log.i("", "Google Play Services は無効");
        }
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("", "Play Service not support");
            }
            return false;
        }
        return true;
    }


    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (regid.equals("")) {
            return "";
        }
        // アプリケーションがバージョンアップされていた場合、レジストレーションIDをクリア
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);

        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            return "";
        }

        return registrationId;
    }


    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }


    private int getAppVersion(Context context) {

        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("package not found : " + e);
        }


    }


    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private void regist_id(){
        if (regid.equals("")) {
            registtask = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    try {
                        //GCMサーバーへ登録
                        regid = gcm.register(PROJECT_ID);
Log.i("", regid);
                        //取得したレジストレーションIDを自分のサーバーへ送信して記録しておく
                        //サーバーサイドでは、この レジストレーションIDを使ってGCMに通知を要求します
                    registeid2YouServer(regid);

                        // レジストレーションIDを端末に保存
                    storeRegistrationId(context, regid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(String result) {
                    registtask = null;


                }
            };
            registtask.execute(null, null, null);
        }

    }


    public boolean registeid2YouServer(String regId) {


        String serverUrl = "http://localhost:8080/pushtest.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("my_id", regId);
        try {
            post(serverUrl, params);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void post(String endpoint, Map<String, String> params)
        throws IOException {
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
