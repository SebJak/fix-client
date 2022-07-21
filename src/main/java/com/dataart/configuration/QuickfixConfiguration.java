package com.dataart.configuration;

import com.dataart.server.MiddlewareApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.*;

import javax.sql.DataSource;


@Configuration
@Slf4j
public class QuickfixConfiguration {

    @Bean
    public SessionSettings create(@Value("${quickfix.session.settings.path}") String sessionSettingsPath) throws ConfigError {
        log.info("Session file {}", sessionSettingsPath);
        return new SessionSettings(getClass().getClassLoader().getResource(sessionSettingsPath).getFile());
    }

    @Bean
    public MessageStoreFactory messageStoreFactory(SessionSettings sessionSettings) {
        return new MemoryStoreFactory();
    }

    @Bean
    public LogFactory logFactory(SessionSettings sessionSettings) {
        return new ScreenLogFactory();
    }

    @Bean
    public MessageFactory messageFactory(SessionSettings sessionSettings) {
        return new DefaultMessageFactory();
    }

    @Bean
    public Application application() {
        return new MiddlewareApp();
    }

    @Bean("jdbcLogFactory")
    public LogFactory jdbcLogFactory(SessionSettings sessionSettings, DataSource dataSource) {
        sessionSettings.setBool(JdbcSetting.SETTING_JDBC_LOG_HEARTBEATS, false);
        JdbcLogFactory factory = new JdbcLogFactory(sessionSettings);
        factory.setDataSource(dataSource);
        return factory;
    }

}
