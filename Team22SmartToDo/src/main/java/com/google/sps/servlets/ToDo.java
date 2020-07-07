// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.KeyFactory;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.andrewrs.sps.data.ListRecord;
import com.andrewrs.sps.utils.StringUtil;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  private DatastoreService datastore;
  private Gson gson;
  
  public void init()
  {
    datastore = DatastoreServiceFactory.getDatastoreService();
    gson = new Gson();
  }
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
  {
      
    response.getWriter().println(html);
  }
    @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
  {
    String user_id = getParameter(request, "user_id", "");
      Key<Entity> key = KeyFactory.stringToKey(user_id);
      // TODO query db
      Entity entity = new Entity("message_log");
      entity.setProperty("status", status);
      datastore.put(entity);
    
    response.sendRedirect("/todo.html");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) 
  {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
  private static String html = 
	"<html>\n" + 
	"  <head>\n" + 
	"    <meta charset=\"UTF-8\">\n" + 
	"    <title>Andrew Rubinstein</title>\n" + 
	"    <link rel=\"stylesheet\" href=\"style.css\">\n" + 
	"    <script src=\"script.js\"></script>\n" + 
	"  </head>\n" + 
	"  <body id=\"body\" onload=\"includeDynamicHTML('image_handler')\">\n" + 
	"    <div id=\"content\" class=\"skyline-backdrop\">\n" + 
	"    <br><br>\n" + 
	"    \n" + 
	"    <div class=\"text-container\">\n" + 
	"      <div class=\"center\">\n" + 
	"        <div class=\"text\">\n" + 
	"            <div class=\"list_container\">\n" + 
	"                <div id=\"include-data\"></div>\n" + 
	"            </div>\n" + 
	"        <h1 style=\"font-size:100%;\"> CSV Input </h1>\n" + 
	"\n" + 
	"    <div class=\"text-container\">\n" + 
	"        <form action=\"data\" method=\"POST\">\n" + 
	"            <div class=\"text\">\n" + 
	"                Your Submission: <input type=\"text\" name=\"message\" maxlength=\"250\" size=\"52\" required=true><br>\n" + 
	"                Estimated Time To Complete: <input type=\"text\" name=\"est_time\" maxlength=\"80\" required=true>\n" + 
	"                Due Date: <input type=\"date\" id=\"due_date\" name=\"due_date\"  required=true>\n" + 
	"                <input type=\"submit\">\n" + 
	"            </div>\n" + 
	"        </form>\n" + 
	"    </div>\n" + 
	"</div>\n" + 
	"        \n" + 
	"    </div>\n" + 
	"    </div>\n" + 
	"    </div>\n" + 
	"    </body>\n" + 
	"    <script>updateDataInclude();</script>\n" + 
	"    </html>\n";
}
