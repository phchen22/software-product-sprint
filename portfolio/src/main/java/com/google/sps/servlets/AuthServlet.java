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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns login status of the user*/
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    boolean loginstatus = false;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        UserService userService = UserServiceFactory.getUserService();

        //If the user is logged in
        if (userService.isUserLoggedIn()) {
            String userEmail = userService.getCurrentUser().getEmail();
            String urlToRedirectToAfterUserLogsOut = "/auth";
            String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
            loginstatus = true;

            //Show login status message
            response.getWriter().println("<p>Hello " + userEmail + "!</p>");
            response.getWriter().println("<p>Login status: </p>" + loginstatus);
            response.getWriter().println("<p>Logout <a href=\"" + logoutUrl + "\">here</a>.</p>");
        } else {
            String urlToRedirectToAfterUserLogsIn = "/auth";
            String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
            loginstatus = false;

            //Show not-logged in status message
            response.getWriter().println("<p>Hello stranger.</p>");
            response.getWriter().println("<p>Login status: </p>" + loginstatus);
            response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
    }
  }
    
}