package com.dataart.client;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import quickfix.*;
import quickfix.field.Password;
import quickfix.field.Username;
import quickfix.fix44.Logon;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("Client")
@DependsOn("Server")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Client {
    Connector initiator;

    public Client(SessionSettings sessionSettings, MessageStoreFactory messageStoreFactory, @Qualifier("jdbcLogFactory") LogFactory logFactory,
                  MessageFactory messageFactory, Application application) throws ConfigError {
        initiator = new SocketInitiator(application, messageStoreFactory, sessionSettings, logFactory, messageFactory);
    }

    @PostConstruct
    public void start() throws ConfigError {
        log.info("Client starting connection");
        initiator.start();
        SessionID sessionId = initiator.getSessions().get(0);
//        Session.lookupSession(sessionId).logon();

//        Logon logon = new Logon();
//
//        logon.set(new quickfix.field.HeartBtInt(60));
//        logon.set(new quickfix.field.ResetSeqNumFlag(true));
//        logon.set(new quickfix.field.EncryptMethod(0));
//        logon.set(new Username("user"));
//        logon.set(new Password("passwords"));

//        try {
//            Session.sendToTarget(logon, sessionId);
//        } catch (SessionNotFound sessionNotFound) {
//            sessionNotFound.printStackTrace();
//        }

    }

    @PreDestroy
    public void stop() {
        log.info("Client closing connection");
        initiator.stop();
    }

    public void sendMessage(Message message) {
        log.debug("#### Message Sent: {}", message);
        SessionID sessionId = initiator.getSessions().get(0);
        try {
            Session.sendToTarget(message, sessionId);
        } catch (SessionNotFound e) {
            log.error("No session found", e);
            throw new RuntimeException(e);
        }
    }
}
