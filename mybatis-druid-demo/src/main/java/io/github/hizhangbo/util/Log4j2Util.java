package io.github.hizhangbo.util;

import com.sun.corba.se.impl.oa.poa.Policies;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.SmtpAppender;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.layout.JsonLayout;

import java.io.IOException;

public class Log4j2Util {

    private static final String LOG_DIR = "logs";
    private static final String SPLIT_SIZE = "10KB";
    private static final String FILE_NUM = "5";
    private static final String FILE_NAME = "console.log";

    private static final String FILE_PATTERN = "/$${date:yyyy-MM-dd}/filename-%d{yyyy-MM-dd}-%i.log";
    private static final String FILE_PATTERN_LAYOUT = "%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n";
    private static final String FILE_CONTENT_FORMAT = "%d{yyyy-MM-dd HH:mm:ss}->%file [%t]:%line->%level->%msg%n";
    private static final String CONSOLE_LINE_FORMAT = "%d{HH:mm:ss:SSS} [%p] [%t] - %l: %m%n";

    private static final ConfigurationBuilder<BuiltConfiguration> configuration
            = ConfigurationBuilderFactory.newConfigurationBuilder();

    private static final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);

    private static void init() {
        addProperty();
        addAppender();
        addRootLogger();
        Configurator.initialize(configuration.build());
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
                .addAttribute("pattern", CONSOLE_LINE_FORMAT));
        //%d [%t] %-5level: %msg%n%throwable

        configuration.add(consoleAppenderBuilder);
    }

    private static void addRollingFileAppender(String name, String fileName, Class<?> clazz) {
        LayoutComponentBuilder rollingFileLayoutBuilder = configuration.newLayout("PatternLayout")
                .addAttribute("pattern", FILE_CONTENT_FORMAT);


        ComponentBuilder<?> policies = configuration.newComponent("Policies")
                .addComponent(configuration.newComponent("SizeBasedTriggeringPolicy")
                        .addAttribute("size", SPLIT_SIZE))
                .addComponent(configuration.newComponent("TimeBasedTriggeringPolicy"));

        AppenderComponentBuilder rollingFileAppenderBuilder = configuration.newAppender(name, RollingFileAppender.PLUGIN_NAME);
        rollingFileAppenderBuilder.addAttribute("fileName", LOG_DIR + fileName);
        rollingFileAppenderBuilder.addAttribute("filePattern", FILE_PATTERN);
        rollingFileAppenderBuilder.add(rollingFileLayoutBuilder);
        rollingFileAppenderBuilder.addComponent(policies);

        configuration.add(rollingFileAppenderBuilder);

        LoggerComponentBuilder loggerComponentBuilder = configuration.newLogger(clazz.getName(), Level.INFO);
        // 按不同logger打印时，additivity设置false，则可以避免rootLogger重复打印
        loggerComponentBuilder.addAttribute("additivity", false);

        AppenderRefComponentBuilder appenderRefComponentBuilder = configuration.newAppenderRef(name);
        loggerComponentBuilder.addComponent(appenderRefComponentBuilder);

        configuration.add(loggerComponentBuilder);
        ctx.updateLoggers();
//        Configurator.initialize(configuration.build());
    }

    private static void addFileAppender() {
        LayoutComponentBuilder fileLayoutBuilder = configuration.newLayout("PatternLayout")
                .addAttribute("pattern", FILE_PATTERN_LAYOUT);

        AppenderComponentBuilder fileAppenderBuilder = configuration.newAppender("log", FileAppender.PLUGIN_NAME);
        fileAppenderBuilder.addAttribute("fileName", LOG_DIR + "/" + FILE_NAME);
        fileAppenderBuilder.addAttribute("append", false);
        fileAppenderBuilder.add(fileLayoutBuilder);
        configuration.add(fileAppenderBuilder);
    }

    //<SMTP name="Mail" subject="****SaaS系统正式版异常信息" to="yong.shi@lengjing.info" from="message@lengjing.info" smtpUsername="message@lengjing.info" smtpPassword="LENG****1234" smtpHost="mail.lengjing.info" smtpDebug="false" smtpPort="25" bufferSize="10">
    //    <PatternLayout pattern="[%-5p]:%d{YYYY-MM-dd HH:mm:ss} [%t] %c{1}:%L - %msg%n" />
    //        </SMTP>
    private static void addSMTPAppender() {
//        SmtpAppender
        AppenderComponentBuilder smtpAppenderBuilder = configuration.newAppender("smtp", "SMTP");
        smtpAppenderBuilder.addAttribute("", "");
    }

    private static void addRootLogger() {
        RootLoggerComponentBuilder rootLogger = configuration.newRootLogger(Level.INFO);
        rootLogger.add(configuration.newAppenderRef("stdout"));
        rootLogger.add(configuration.newAppenderRef("log"));
        configuration.add(rootLogger);
    }

    private static void addAppender() {
        addConsoleAppender();
        addFileAppender();
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
        addRollingFileAppender("rollingFile", "/rolling.log", Log4j2Util.class);

        Logger logger = LogManager.getLogger(Log4j2Util.class);
        logger.debug("This is a Debug Message!");
        logger.info("This is an Info Message!");
        logger.warn("This is a WARN Message!");
        logger.error("Error Occured", new RuntimeException("test log4j2"));

//        Logger rollingFileLogger = LogManager.getLogger("rollingFile");
//        rollingFileLogger.info("rolling......");


    }


//    private static void addLogger() {
//        LoggerComponentBuilder springLogger = configuration.newLogger("org.springframework", Level.INFO);
//        springLogger.add(configuration.newAppenderRef("log"));
//        springLogger.add(configuration.newAppenderRef("stdout"));
//
//        springLogger.addAttribute("additivity", false);
//
//
//        LoggerComponentBuilder mybatisLogger = configuration.newLogger("org.mybatis", Level.INFO);
//        mybatisLogger.add(configuration.newAppenderRef("log"));
//        mybatisLogger.add(configuration.newAppenderRef("stdout"));
//
//        mybatisLogger.addAttribute("additivity", false);
//
//        configuration.add(springLogger);
//        configuration.add(mybatisLogger);
//    }
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
