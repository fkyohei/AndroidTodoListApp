/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-01-14 17:53:03 UTC)
 * on 2015-02-16 at 09:08:44 UTC 
 * Modify at your own risk.
 */

package com.example.g_2015.todolist.api.todoApi.model;

/**
 * Model definition for Todo.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the todoApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Todo extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean checked;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long created;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String text;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long updated;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getChecked() {
    return checked;
  }

  /**
   * @param checked checked or {@code null} for none
   */
  public Todo setChecked(java.lang.Boolean checked) {
    this.checked = checked;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getCreated() {
    return created;
  }

  /**
   * @param created created or {@code null} for none
   */
  public Todo setCreated(java.lang.Long created) {
    this.created = created;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Todo setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getText() {
    return text;
  }

  /**
   * @param text text or {@code null} for none
   */
  public Todo setText(java.lang.String text) {
    this.text = text;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getUpdated() {
    return updated;
  }

  /**
   * @param updated updated or {@code null} for none
   */
  public Todo setUpdated(java.lang.Long updated) {
    this.updated = updated;
    return this;
  }

  @Override
  public Todo set(String fieldName, Object value) {
    return (Todo) super.set(fieldName, value);
  }

  @Override
  public Todo clone() {
    return (Todo) super.clone();
  }

}
