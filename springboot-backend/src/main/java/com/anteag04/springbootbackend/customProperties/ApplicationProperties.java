package com.anteag04.springbootbackend.customProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "my")
public class ApplicationProperties {

    private int levelUpTime;

    public int getLevelUpTime() {
        return levelUpTime;
    }

    public void setLevelUpTime(int levelUpTime) {
        this.levelUpTime = levelUpTime;
    }
}
