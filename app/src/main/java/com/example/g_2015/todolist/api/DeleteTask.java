package com.example.g_2015.todolist.api;

import com.example.g_2015.todolist.api.todoApi.model.Todo;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by g-2015 on 2015/02/16.
 */
public class DeleteTask extends AsyncTask<Todo, Void, Void> {

    @Override
    protected Void doInBackground(Todo... params) {
        for (Todo todo : params) {
            try {
                ApiClient.getInstance().delete(todo.getId()).execute();
            } catch (IOException e) {
                //TODO
                e.printStackTrace();
            }
        }
        return null;
    }
}
