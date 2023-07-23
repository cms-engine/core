package com.ecommerce.engine.config;

import org.springframework.stereotype.Component;

@Component
public class ServiceListener {

    /*@Override
    public void serviceInit(ServiceInitEvent event) {

        event.getSource().addSessionInitListener(
                initEvent -> {
                    LoggerFactory.getLogger(getClass())
                            .info("A new Session has been initialized!");

                    initEvent.getSession().setErrorHandler(new VaadinErrorHandler());
                });

        //VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());

        event.getSource().addUIInitListener(
                initEvent -> LoggerFactory.getLogger(getClass())
                        .info("A new UI has been initialized!"));
    }*/
}
