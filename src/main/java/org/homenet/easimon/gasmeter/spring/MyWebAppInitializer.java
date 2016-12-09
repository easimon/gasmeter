package org.homenet.easimon.gasmeter.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.homenet.easimon.gasmeter.spring.configuration.DispatcherConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
//      // Create the 'root' Spring application context
//      AnnotationConfigWebApplicationContext rootContext =
//        new AnnotationConfigWebApplicationContext();
//      rootContext.register(ApplicationConfiguration.class);
//
//      // Manage the lifecycle of the root application context
//      container.addListener(new ContextLoaderListener(rootContext));

      // Create the dispatcher servlet's Spring application context
      AnnotationConfigWebApplicationContext dispatcherContext =
        new AnnotationConfigWebApplicationContext();
      dispatcherContext.register(DispatcherConfiguration.class);

      // Register and map the dispatcher servlet
      ServletRegistration.Dynamic dispatcher =
        container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
      dispatcher.setLoadOnStartup(1);
      dispatcher.addMapping("/");
    }

 }