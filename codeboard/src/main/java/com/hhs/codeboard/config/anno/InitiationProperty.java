package com.hhs.codeboard.config.anno;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@ConstructorBinding
@ConfigurationProperties(prefix = "codeboard.init")
public class InitiationProperty {

    public InitiationProperty(String sequenceTableName) {
        this.sequenceTableName = sequenceTableName;
    }

    private final String sequenceTableName;

    public String getSequenceTableName() {
        return sequenceTableName;
    }
}
