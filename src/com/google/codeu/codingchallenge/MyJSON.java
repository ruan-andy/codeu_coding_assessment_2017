// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.util.Collection;

import java.util.*;

final class MyJSON implements JSON {
  // Hashmap using Linkedlist to hold the JSON with mutiple JSON as value
  private static HashMap<String, JSON> objMap;

  // Hashmap to to hold JSON with String as values
  private static HashMap<String, String>  strMap;

  private String key;
  private String value;
  private LinkedList<MyJSON> object;

  // Empty constructor for setting up the hashmaps
  public MyJSON() {
    objMap = new HashMap<String, JSON>();
    strMap = new HashMap<String, String>();
  }

  // constructor for making string value JSON
  public MyJSON(String key, String value) {
    this.key = key;
    this.value = value;
    this.object = null;
  }

  //constructor for making linkedlist value hashmaps
  public MyJSON(String key, LinkedList<MyJSON> object) {
    this.key = key;
    this.value = null;
    this.object = object;
  }

  @Override
  public JSON getObject(String name) {
    return objMap.get(name);
  }

  @Override
  public JSON setObject(String name, JSON value) {
    objMap.put(name, value);
    return this;
  }

  @Override
  public String getString(String name) {
    return strMap.get(name);
  }

  @Override
  public JSON setString(String name, String value) {
    strMap.put(name, value);
    return this;
  }

  @Override
  public void getObjects(Collection<String> names) {
    Set<String> keys = objMap.keySet();
    for (String i: keys) {
      names.add(i);
    }
  }

  @Override
  public void getStrings(Collection<String> names) {
    Set<String> keys = strMap.keySet();
    for (String i: keys) {
      names.add(i);
    }
  }
}
