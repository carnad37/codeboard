package com.hhs.codeboard.config.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LoggerController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
