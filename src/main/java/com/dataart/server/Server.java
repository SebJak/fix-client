package com.dataart.server;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import quickfix.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("Server")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Server {
    Connector acceptorConnector;

    public Server(SessionSettings sessionSettings, MessageStoreFactory messageStoreFactory, LogFactory logFactory,
                           MessageFactory messageFactory, Application application) throws ConfigError {
        acceptorConnector = new SocketAcceptor(application, messageStoreFactory, sessionSettings, logFactory, messageFactory);
    }

    @PostConstruct
    public void start() throws ConfigError {
        log.info("Starting Fix Server");
        acceptorConnector.start();
    }

    @PreDestroy
    public void stop() {
        log.info("Stopping Fix Server");
        acceptorConnector.stop();
    }
}
