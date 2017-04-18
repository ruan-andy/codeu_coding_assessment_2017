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
    String tmpKey = "";
    String tmpValue = "";
    String tmpString = "";
    JSON tmpJSON = null;

    //LinkedList for storing values to check if it is more than one
    LinkedList<JSON> tmpValues = new LinkedList<JSON>();
    // Stack for operator's/not letters to check for syntax
    Stack<Character> operators = new Stack<Character>();

    Stack<String> tmpKeys = new Stack<String>();
    //boolean variables for syntax checking
    boolean objectStart = true;
    boolean isObject = false;
    boolean startString = false;
    boolean keyValue = true;

    for (int i = 0; i < in.length(); i++) {
      char curChar = in.charAt(i);
      if (curChar == '{' ) {
        objectStart = true;
        operators.push(curChar);

      } else if(curChar == '}' ) {
        if (!operators.isEmpty() && operators.peek().equals('{')) {
          operators.pop();
          isObject = false;
        } else {
          throw new IOException("Missing { for }");
        }
      } else if(curChar == '\"' ) {
        if (!operators.isEmpty()) {
          if (operators.peek().equals('\"')) {
            operators.pop();
            startString = !startString;
          } else {
            operators.push(curChar);
            startString = !startString;
          }
        } else {
          if (!startString) {
            operators.push(curChar);
            startString = !startString;
          } else {
            throw new IOException("Missing \"");
          }

        }
        /*
        if (operators.isEmpty() ||  !operators.peek().equals('\\') ) {
          if(!operators.isEmpty()) {
            String hi = operators.pop() + tmpString;
            throw new IOException(hi);
          }
          throw new IOException("Missing \\ before \"");
        }
        //&& !operators.peek().equals('\\')
        operators.pop();
        startString = !startString;
        */

        /*
        if (objectStart) {
          tmpKeys.push(tmpString);
        } else {
          myJson.setString((String) tmpKeys.pop(), tmpString);
        }
        if (!startString) {
          tmpString = "";
          startString = !startString;
        }*/

        /*
        if (!startString && !keyValue) {
          tmpValues.add(tmpString);
          tmpString = "";
        }*/

        if (!tmpKeys.isEmpty() && !startString && !keyValue && isObject) {
          System.out.println(!startString);
          System.out.println(!tmpKeys.isEmpty());
          System.out.println(tmpString);
          tmpJSON = new MyJSON(tmpKeys.pop(), tmpString);
          System.out.println(tmpString);
          //myJson.setString(tmpKeys.pop(), tmpString);
          tmpString = "";
        }

      } else if(curChar == ':' ) {
        if (startString) {
          throw new IOException("String did not end");
        }
        tmpKeys.push(tmpString);
        tmpString = "";
        keyValue = false;
        isObject = true;
      } else if(curChar == ',' ) {
        tmpValues.add(tmpJSON);
        keyValue = true;

      } else if(startString && (Character.isLetter(curChar) || Character.isDigit(curChar) || curChar == ' ') ) {
        /*
        if (!operators.isEmpty() && operators.peek().equals('\\') && (curChar == 't' || curChar == 'n') ) {
          tmpString += operators.pop();
        }*/

        tmpString += curChar;
      }

      //System.out.println(tmpString);
      //System.out.println(curChar);
      //System.out.println(startString);
    }

    System.out.println(!startString);
    System.out.println(!tmpKeys.isEmpty());
    System.out.println(keyValue);
    System.out.println(isObject);
    System.out.println(tmpString);

    //System.out.println(myJson.getString("name"));
    if (!tmpKeys.isEmpty() || !operators.isEmpty()) {
      //throw new IOException("Unfinished");
    }
    return myJson;
  }
}
