package io.github.hizhangbo.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.layout.JsonLayout;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.IOException;

public class Log4j2Util {

    private static final String LOG_DIR = "logs";
    private static final String SPLIT_SIZE = "10KB";
    private static final String FILE_NUM = "5";
    private static final String FILE_NAME = "app.log";

    private static final String FILE_PATTERN = "/$${date:yyyy-MM-dd}/filename-%d{yyyy-MM-dd}-%i.log";
    private static final String FILE_PATTERN_LAYOUT = "%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n";

    private static final ConfigurationBuilder<BuiltConfiguration> configuration
            = ConfigurationBuilderFactory.newConfigurationBuilder();

    private static void init() {
        addProperty();
        addAppender();
        addLogger();
        addRootLogger();
    }

    public static final void getLogger() {
        LoggerContext loggerContext = Configurator.initialize(configuration.build());
        Logger stdoutLogger = loggerContext.getLogger("stdout");
        stdoutLogger.info("test ......");


        Logger fileLog = loggerContext.getLogger("log1");
        fileLog.info("test file log ......");
    }

    private static void addProperty() {
        configuration.setStatusLevel(Level.WARN);
        configuration.setMonitorInterval("30");

        configuration.addProperty("logDir", LOG_DIR);
        configuration.addProperty("splitSize", SPLIT_SIZE);
        configuration.addProperty("fileNum", FILE_NUM);
        configuration.addProperty("defaultName", FILE_NAME);
    }

    private static void addConsoleAppender() {
        AppenderComponentBuilder consoleAppenderBuilder =
                configuration.newAppender("stdout", ConsoleAppender.PLUGIN_NAME)
                        .addAttribute("target",
                                ConsoleAppender.Target.SYSTEM_OUT);
        consoleAppenderBuilder.add(configuration.newLayout("PatternLayout")
                .addAttribute("pattern", "%d{HH:mm:ss:SSS} [%p] [%t] - %l: %m%n"));
        //%d [%t] %-5level: %msg%n%throwable

        configuration.add(consoleAppenderBuilder);
    }

    private static void addFileAppender(String fileName) {
        LayoutComponentBuilder fileLayoutBuilder = configuration.newLayout("PatternLayout")
                .addAttribute("pattern", FILE_PATTERN_LAYOUT);

        AppenderComponentBuilder fileAppenderBuilder = configuration.newAppender("log", FileAppender.PLUGIN_NAME);
        fileAppenderBuilder.addAttribute("fileName", LOG_DIR + "/" + fileName);
        fileAppenderBuilder.addAttribute("append", true);
        fileAppenderBuilder.add(fileLayoutBuilder);
        configuration.add(fileAppenderBuilder);

    }

    private static void addRootLogger() {
        RootLoggerComponentBuilder rootLogger = configuration.newRootLogger(Level.INFO);
        rootLogger.add(configuration.newAppenderRef("stdout"));
        rootLogger.add(configuration.newAppenderRef("log"));
        configuration.add(rootLogger);
    }

    private static void addLogger() {
        LoggerComponentBuilder springLogger = configuration.newLogger("org.springframework", Level.INFO);
        springLogger.add(configuration.newAppenderRef("log"));
        springLogger.add(configuration.newAppenderRef("stdout"));

        springLogger.addAttribute("additivity", false);


        LoggerComponentBuilder mybatisLogger = configuration.newLogger("org.mybatis", Level.INFO);
        mybatisLogger.add(configuration.newAppenderRef("log"));
        mybatisLogger.add(configuration.newAppenderRef("stdout"));

        mybatisLogger.addAttribute("additivity", false);

        configuration.add(springLogger);
        configuration.add(mybatisLogger);

    }

    private static void addAppender() {
        addConsoleAppender();
        addFileAppender(FILE_NAME);
    }

    public static void printConfiguration() {
        try {
            configuration.writeXmlConfiguration(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        init();
        printConfiguration();
        getLogger();
    }


//    private static Layout jsonLayout() {
//        return JsonLayout.createDefaultLayout();
//    }
//
//    private static Layout patternLayout() {
//        return PatternLayout.newBuilder().withPattern("[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n").build();
//    }

//    /**
//     * RollingFileAppender.PLUGIN_NAME
//     * 日志个数 5
//     * 日志大小 1KB
//     * 按日期 rollover
//     *
//     * @param name     插件名
//     * @param fileName 日志名
//     * @return RollingFileAppender
//     */
//    private static Appender rollingFileAppender(String name, String fileName) {
//        DefaultRolloverStrategy rolloverStrategy = DefaultRolloverStrategy.newBuilder().withMax(FILE_NUM).build();
//        SizeBasedTriggeringPolicy sizeBasedTriggeringPolicy = SizeBasedTriggeringPolicy.createPolicy(SPLIT_SIZE);
//        TimeBasedTriggeringPolicy timeBasedTriggeringPolicy = TimeBasedTriggeringPolicy.newBuilder().build();
//
//        ThresholdFilter filter = ThresholdFilter.createFilter(Level.INFO, Filter.Result.ACCEPT, Filter.Result.DENY);
//
//        RollingFileAppender rollingFileAppender = RollingFileAppender.newBuilder()
//                .withFileName(fileName)
//                .withFilePattern(LOG_DIR + FILE_PATTERN.replace("filename", fileName))
//                .withPolicy(sizeBasedTriggeringPolicy)
//                .withPolicy(timeBasedTriggeringPolicy)
//                .withStrategy(rolloverStrategy)
//                .setLayout(jsonLayout())
//                .setConfiguration(configuration())
//                .setFilter(filter)
//                .setName(name)
//                .build();
//
//        return rollingFileAppender;
//    }

//    private static Appender consoleAppender() {
//        ConsoleAppender consoleAppender = ConsoleAppender.newBuilder()
//                .setName("stdout")
//                .setLayout(jsonLayout())
//                .build();
//
//        return consoleAppender;
//    }
//
//    private static Appender fileAppender(String fileName) {
//        PatternLayout patternLayout = PatternLayout.newBuilder()
//                .withPattern(FILE_PATTERN_LAYOUT)
//                .build();
//
//        Appender fileAppender = FileAppender.newBuilder()
//                .withFileName(LOG_DIR + "/" + fileName)
//                .withAppend(true)
//                .setName("log")
//                .setLayout(patternLayout)
//                .build();
//        return fileAppender;
//    }
}
