package com.lab.epam.crm.gym.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import lombok.experimental.FieldDefaults;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import static lombok.AccessLevel.PRIVATE;

@Configuration
@FieldDefaults(level = PRIVATE, makeFinal = true)
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource(value = "classpath:application-${spring.profiles.active}.properties",
                ignoreResourceNotFound = true)})
public class LogbackConfig {
    String logFileDir;
    String logLevel;

    public LogbackConfig(@Value("${logging.file.dir}") String logFileDir,
                         @Value("${logging.level}")String logLevel) {
        this.logFileDir = logFileDir;
        this.logLevel = logLevel;
    }

    @Bean
    public LoggerContext loggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public PatternLayoutEncoder encoder(LoggerContext ctx) {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(ctx);
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} %level [%thread] %logger{36} %m%n");
        return encoder;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public ConsoleAppender<ILoggingEvent> consoleAppender(LoggerContext ctx,
                                                          PatternLayoutEncoder encoder) {
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setName("consoleAppender");
        consoleAppender.setContext(ctx);
        consoleAppender.setEncoder(encoder);
        return consoleAppender;
    }

    @Bean
    public TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy(LoggerContext ctx) {
        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setContext(ctx);
        rollingPolicy.setFileNamePattern(logFileDir + "/javaApp.%d{yyyy-MM-dd}.log");
        rollingPolicy.setMaxHistory(60);
        return rollingPolicy;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public RollingFileAppender<ILoggingEvent> fileAppender(LoggerContext ctx,
                                                           PatternLayoutEncoder encoder,
                                                           TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy) {
        RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
        fileAppender.setName("fileAppender");
        fileAppender.setContext(ctx);
        fileAppender.setEncoder(encoder);
        fileAppender.setFile(logFileDir + "/javaApp.log");

        rollingPolicy.setParent(fileAppender);
        fileAppender.setRollingPolicy(rollingPolicy);
        rollingPolicy.start();
        return fileAppender;
    }

    @Bean
    public Logger rootLogger(LoggerContext ctx, ConsoleAppender<ILoggingEvent> consoleAppender, RollingFileAppender<ILoggingEvent> fileAppender) {
        Logger rootLogger = ctx.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.ERROR);
        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(fileAppender);
        return rootLogger;
    }

    @Bean
    public Logger storageLogger(LoggerContext ctx, ConsoleAppender<ILoggingEvent> consoleAppender, RollingFileAppender<ILoggingEvent> fileAppender) {
        Logger storageLogger = ctx.getLogger("com.lab.epam.crm.gym.dao.storage");
        storageLogger.setLevel(Level.toLevel(logLevel, Level.DEBUG));
        storageLogger.setAdditive(false);
        storageLogger.addAppender(consoleAppender);
        storageLogger.addAppender(fileAppender);
        return storageLogger;
    }

    @Bean
    public Logger daoLogger(LoggerContext ctx, ConsoleAppender<ILoggingEvent> consoleAppender, RollingFileAppender<ILoggingEvent> fileAppender) {
        Logger daoLogger = ctx.getLogger("com.lab.epam.crm.gym.dao.impl");
        daoLogger.setLevel(Level.toLevel(logLevel, Level.DEBUG));
        daoLogger.setAdditive(false);
        daoLogger.addAppender(consoleAppender);
        daoLogger.addAppender(fileAppender);
        return daoLogger;
    }
}
