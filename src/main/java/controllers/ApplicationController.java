/**
 * Copyright (C) 2013 the original author or authors.
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

package controllers;

import java.util.List;
import java.util.Map;

import models.Article;
import models.Picture;
import models.Project;
import ninja.Result;
import ninja.Results;
import ninja.utils.NinjaProperties;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import dao.ArticleDao;
import dao.PictureDao;
import dao.ProjectDao;
import dao.SetupDao;

public class ApplicationController {

    @Inject
    ArticleDao articleDao;
    @Inject
    ProjectDao projectDao;
    @Inject
    PictureDao pictureDao;

    @Inject
    SetupDao setupDao;

    public ApplicationController() {

    }

    /**
     * Method to put initial data in the db...
     * 
     * @return
     */
    public Result setup() {

        setupDao.setup();

        return Results.ok();

    }

    @Inject
    NinjaProperties np;
    
    public Result index() {
    	Project project = projectDao.getLatestProjectFrontPage();
    	Picture picture = pictureDao.getLatestProjectPictureFrontPage(project);
    	Project newsEvent=projectDao.getLatestNewsEventFrontPage();
    	System.out.println("np id "+newsEvent.getId());
    	Picture newsPicture = pictureDao.getLatestProjectPictureFrontPage(newsEvent);
    	Article frontPost = articleDao.getFirstArticleForFrontPage();
        String desc=project.getDescription();

        if(desc.length()>500){
        	desc=desc.substring(0, 500);
        	project.setDescription(desc);
        }
        
        List<Article> olderPosts = articleDao.getOlderArticlesForFrontPage();
        
/*        Map<String, Object> map = Maps.newHashMap();
        map.put("frontArticle", frontPost);
        map.put("olderArticles", olderPosts);
        map.put("frontProject", project);
        map.put("picture", picture);
        map.put("newsEvent",newsEvent);
*/
        return Results.html().render("frontArticle", frontPost).render("olderArticles", olderPosts).render("frontProject", project)
        		.render("picture", picture).render("newsEvent",newsEvent).render("newsPicture", newsPicture);

    }
}
