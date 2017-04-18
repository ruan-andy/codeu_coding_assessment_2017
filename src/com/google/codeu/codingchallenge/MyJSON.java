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

  // MyJSON variables
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
    setString(key, value);
  }

  //constructor for making linkedlist value hashmaps
  public MyJSON(String key, LinkedList<MyJSON> object) {
    this.key = key;
    this.value = null;
    this.object = object;
    setObject(key, new MyJSON(object) );
  }

  //constructor for turning list MYJSON to MYJSON objects
  public MyJSON(LinkedList<MyJSON> object) {
    this.object = object;
  }

  @Override
  public JSON getObject(String name) {
    // get from the hashmap
    return objMap.get(name);
  }

  @Override
  public JSON setObject(String name, JSON value) {
    // set from hashmaps and if it is not there, it will create new
    objMap.put(name, value);
    return this;
  }

  @Override
  public String getString(String name) {
    // get from strings only hashmaps
    return strMap.get(name);
  }

  @Override
  public JSON setString(String name, String value) {
    // set from strings hash map, if key not available, create new
    strMap.put(name, value);
    return this;
  }

  @Override
  public void getObjects(Collection<String> names) {
    // get all the keys from the object hash map
    Set<String> keys = objMap.keySet();
    // add them to the collection of names
    for (String i: keys) {
      names.add(i);
    }
  }

  @Override
  public void getStrings(Collection<String> names) {
    // get all the keys from the string hashmap
    Set<String> keys = strMap.keySet();
    //add the keys to the names collection
    for (String i: keys) {
      names.add(i);
    }
  }
}
