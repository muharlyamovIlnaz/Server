package com.ilnaz.server.config;

import com.ilnaz.server.auth.filter.JwtAuthFilterTeacher;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        migrateDatabase();
        authConfig(sce);
    }

    private void migrateDatabase(){
        FlywayConfiguration.migrateDatabase();
    }

    private void authConfig(ServletContextEvent sce){
        sce.getServletContext()
                .addFilter("JwtAuthFilter", new JwtAuthFilterTeacher())
                .addMappingForUrlPatterns(null, false, "/student/*");
    }
}
