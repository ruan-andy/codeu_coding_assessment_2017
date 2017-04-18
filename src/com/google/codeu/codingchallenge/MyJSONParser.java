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

import java.io.IOException;
import java.util.*;
final class MyJSONParser implements JSONParser {

  @Override
  public JSON parse(String in) throws IOException {
    //empty constructor call
    MyJSON myJson = new MyJSON();

    //temporary variables for JSON object
    String tmpString = "";
    MyJSON tmpJSON = null;

    //LinkedList for storing values to check if it is more than one
    LinkedList<MyJSON> tmpValues = new LinkedList<MyJSON>();
    // Stack for operator's/not letters to check for syntax
    Stack<Character> operators = new Stack<Character>();

    Stack<String> tmpKeys = new Stack<String>();
    //boolean variables for syntax checking
    boolean isObject = false;
    boolean startString = false;
    boolean keyValue = true;

    // for loop for going through the String input
    for (int i = 0; i < in.length(); i++) {
      // get current character
      char curChar = in.charAt(i);

      // if statements for possible characters
      if (curChar == '{' ) { // Character ({)
        keyValue = true;
        operators.push(curChar);

      } else if(curChar == '}' ) { // Character (})
        //checking if there is a opening bracket to match the closing bracket
        if (!operators.isEmpty() && operators.peek().equals('{')) {
          operators.pop();
          isObject = false;
        } else {
          throw new IOException("Expected { to match }");
        }

        //creating the object with object as value
        if (!tmpValues.isEmpty() && !tmpKeys.isEmpty()) {
          tmpJSON = new MyJSON(tmpKeys.pop(), tmpValues);
          tmpValues.clear();
        }

      } else if(curChar == '\"' ) { // Character: (")
        //Check for Opening '\"' and closing '\"'
        if (!operators.isEmpty()) {
          if (operators.peek().equals('\"')) {
            //pop '\"' if there is a closing '\"'
            operators.pop();
            startString = !startString;
          } else {
            //add '\"' into the stack indicates the start of the String
            operators.push(curChar);
            startString = !startString;
          }
        } else {
          // same as above if the list is initially empty
          if (!startString) {
            operators.push(curChar);
            startString = !startString;
          } else {
            throw new IOException("Missing \"");
          }

        }

        // Creates the MyJSON object with the given keys and the values
        if (!tmpKeys.isEmpty() && !startString && !keyValue && isObject) {
          tmpJSON = new MyJSON(tmpKeys.pop(), tmpString);
          tmpString = "";
        }

      } else if(curChar == ':' ) { // Character (:)

        if (startString) {
          throw new IOException("String did not end");
        }
        // push keys into the key stack
        tmpKeys.push(tmpString);
        tmpString = "";
        keyValue = false;
        isObject = true;

      } else if(curChar == ',' ) { // Character (,)
        // ',' indicates a list of MyJSON objects, put in Linked List
        tmpValues.add(tmpJSON);
        keyValue = true;

      } else if(startString &&
          (Character.isLetter(curChar) || Character.isDigit(curChar)
          || curChar == ' ') ) { //Check for letter, digits, and white space

        //append to the tmpString
        tmpString += curChar;
      }
    }

    //Final check for missing operators matching
    if (!tmpKeys.isEmpty() || !operators.isEmpty()) {
      throw new IOException("UnMatched operators");
    }

    return myJson;
  }
}
