package com.ecommerce.engine.config;

import com.vaadin.flow.server.*;
import org.slf4j.LoggerFactory;

//@Component
public class ServiceListener implements VaadinServiceInitListener, SessionInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {

        event.getSource().addSessionInitListener(
                initEvent -> LoggerFactory.getLogger(getClass())
                        .info("A new Session has been initialized!"));

        //VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());

        event.getSource().addUIInitListener(
                initEvent -> LoggerFactory.getLogger(getClass())
                        .info("A new UI has been initialized!"));
    }

    @Override
    public void sessionInit(SessionInitEvent event) throws ServiceException {
        event.getSession().setErrorHandler(new CustomErrorHandler());
    }
}
