package com.example.g_2015.todolist.api;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;


@Entity
@Cache
/**
 * Created by g-2015 on 2015/02/13.
 */
public class Todo {

    @Id
    Long id;

    private String text;

    private long created;

    @Index
    private long updated;

    @Index
    private boolean isChecked;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public static Todo createTodo() {
        long date = new Date().getTime();
        Todo todo = new Todo();
        todo.setCreated(date);
        todo.setUpdated(date);
        todo.setChecked(false);

        return todo;
    }
}
