package com.example.g_2015.todolist.api;

import com.example.g_2015.todolist.api.todoApi.model.Todo;

import android.os.AsyncTask;

import java.io.IOException;


/**
 * Created by g-2015 on 2015/02/16.
 */
public class AddTask extends AsyncTask<Todo, Void, Todo> {

    @Override
    protected Todo doInBackground(Todo... params) {
        Todo todo = params[0];
        try {
            return ApiClient.getInstance().add(todo).execute();
        } catch (IOException e) {
            return null;
        }
    }
}
