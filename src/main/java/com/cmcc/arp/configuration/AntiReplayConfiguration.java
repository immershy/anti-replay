package com.cmcc.arp.configuration;

import com.cmcc.arp.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmcc.arp.RequestPlayer;
import com.cmcc.arp.filter.AntiReplayFilter;

/**
 * Created by zmcc on 17/4/1.
 */
@Configuration
public class AntiReplayConfiguration {

    @Autowired
    private ErrorHandler errorHandler;

    @Autowired
    private RequestStore requestStore;

    @Bean
    @ConditionalOnMissingBean
    public AntiReplayFilter antiReplayFilter() {
        return new AntiReplayFilter();
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ServletErrorHandler();
    }

//    @Bean
//    @ConditionalOnMissingBean
//    public RequestStore requestStore() {
//        return new InMemoryRequestStore();
//    }

    @Bean
    @ConditionalOnMissingBean
    public RequestStore requestStore() {
        return new InMemoryWindowRequestStore();
    }

    @Bean
    public RequestPlayer requestPlayer() {
        return new RequestPlayer();
    }

}
