package com.cmcc.arp.annotation;


import org.springframework.context.annotation.Import;

import com.cmcc.arp.configuration.AntiReplayConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AntiReplayConfiguration.class)
public @interface EnableAntiReplay {

}
