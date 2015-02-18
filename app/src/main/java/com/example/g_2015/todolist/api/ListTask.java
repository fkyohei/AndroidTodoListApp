package com.example.g_2015.todolist.api;

import com.example.g_2015.todolist.api.todoApi.model.TodoCollection;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by g-2015 on 2015/02/16.
 */
public class ListTask extends AsyncTask<Void, Void, TodoCollection> {

   @Override
   protected TodoCollection doInBackground(Void... params) {
        try {
            return ApiClient.getInstance().list().execute();
        } catch (IOException e) {
            return null;
        }
    }
}
