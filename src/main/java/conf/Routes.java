/**
 * Copyright (C) 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;

import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;

import controllers.ApplicationController;
import controllers.ContactUsController;
import controllers.DonationController;
import controllers.LoginLogoutController;
import controllers.ProjectController;

public class Routes implements ApplicationRoutes {
    
    @Inject
    NinjaProperties ninjaProperties;

    /**
     * Using a (almost) nice DSL we can configure the router.
     * 
     * The second argument NinjaModuleDemoRouter contains all routes of a
     * submodule. By simply injecting it we activate the routes.
     * 
     * @param router
     *            The default router of this application
     */
    @Override
    public void init(Router router) {  

    	
        // puts test data into db:
        if (!ninjaProperties.isProd()) {
        	router.GET().route("/app/dashboard").with(ApplicationController.class, "dashboard");
            router.GET().route("/settings").with(ApplicationController.class, "settings");
            router.GET().route("/settings/user/new").with(ApplicationController.class, "newUser");
            router.POST().route("/settings/user/new").with(ApplicationController.class, "create");
            router.POST().route("/settings/user/new/upload").with(ApplicationController.class, "upload");
            router.GET().route("/members").with(ApplicationController.class, "memberList");
            router.POST().route("/settings/user/update").with(ApplicationController.class,"update");
            router.GET().route("/settings/user/loginCredentials").with(ApplicationController.class, "newLoginCredentials");
            router.POST().route("/settings/user/loginCredentials").with(ApplicationController.class, "createLoginCredentials");
            router.GET().route("/settings/user/{id}").with(ApplicationController.class, "editUser");
            
//            router.GET().route("/settings/user/changepassword").with(ApplicationController.class, "settings");
            
        }
        
        ///////////////////////////////////////////////////////////////////////
        // Login / Logout
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/login").with(LoginLogoutController.class, "login");
        router.POST().route("/login").with(LoginLogoutController.class, "loginPost");
        router.GET().route("/logout").with(LoginLogoutController.class, "logout");
        
        ////// Contact Us routing
        router.GET().route("/contact/new").with(ContactUsController.class, "newContactUs");
        router.POST().route("/contact").with(ContactUsController.class, "create");
        router.GET().route("/contact").with(ContactUsController.class, "index");

        ////// Project routing
        router.GET().route("/project").with(ProjectController.class, "index");
        router.POST().route("/project").with(ProjectController.class, "create");
        router.GET().route("/project/new").with(ProjectController.class, "newProject");
        router.POST().route("/project/upload").with(ProjectController.class, "upload");
        router.GET().route("/project/events").with(ProjectController.class, "showNewsEvent");
        router.GET().route("/project/{id}").with(ProjectController.class, "showProject");
        router.GET().route("/project/edit/{id}").with(ProjectController.class, "editProject");
        router.POST().route("/project/update").with(ProjectController.class, "update");
        
        ////// Donate Routing
        router.GET().route("/donation").with(DonationController.class, "index");
        router.GET().route("/donation/new").with(DonationController.class, "newDonation");
        router.POST().route("/donation/new").with(DonationController.class, "create");
        router.GET().route("/donation/edit/{id}").with(DonationController.class, "editDonation");
        router.POST().route("/donation/update").with(DonationController.class, "update");
        
        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
        
		///////////////////////////////////////////////////////////////////////
		// Index / Catchall shows index page, leave this at the end load this after all resources are loaded
		///////////////////////////////////////////////////////////////////////
		router.GET().route("/.*").with(ApplicationController.class, "index");
    }

}
