package org.latheild.common.spring.config;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationConstant {
    @Value("${spring.application.name}")
    public String applicationName;

    @Value("${spring.application.index}")
    public int applicationIndex;
}
