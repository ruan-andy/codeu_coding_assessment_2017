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

    //LinkedList for storing values to check if it is more than one
    LinkedList<String> tmpValues = new LinkedList<String>();
    // Stack for operator's/not letters to check for syntax
    Stack<Character> operators = new Stack();

    Stack<String> tmpKeys = new Stack();
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
        if (operators.peek().equals('{')) {
          operators.pop();
          if (tmpValues.size() == 1) {
            myJson.setString(tmpKeys.pop(), tmpValues.get(0));
          }
        } else {
          throw new IOException("Missing { for }");
        }
      } else if(curChar == '\\' ) {
        operators.push(curChar);

      } else if(curChar == '\"' ) {
        if (!operators.peek().equals('\\')) {
          throw new IOException("Missing \\ before \"");
        }
        operators.pop();
        startString = !startString;
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
        if (!startString && !keyValue) {
          tmpValues.add(tmpString);
        }
      } else if(curChar == ':' ) {
        if (!startString) {
          throw new IOException("String did not end");
        }
        tmpKeys.push(tmpString);
        keyValue = false;
      } else if(curChar == ',' ) {
        tmpValues.add(tmpString);
        keyValue = false;
      } else if(Character.isLetter(curChar) || Character.isDigit(curChar) || curChar == ' ') {
        if (operators.peek().equals('\\') && (curChar == 't' || curChar == 'n') ) {
          tmpString += operators.pop();
        }

        tmpString += curChar;
      }
    }
    if (!tmpKeys.empty() || !operators.empty()) {
      throw new IOException("Unleft closing");
    }
    return myJson;
  }
}
