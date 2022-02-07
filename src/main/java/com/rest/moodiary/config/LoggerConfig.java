package com.rest.moodiary.config;

import com.rest.moodiary.logger.LogTrace;
import com.rest.moodiary.logger.LogTraceAspect;
import com.rest.moodiary.logger.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace){
        return new LogTraceAspect(logTrace);
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
