package swa.tools.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jinyan on 4/10/18 4:48 PM.
 */
public class ToolsException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(ToolsException.class);

    public ToolsException(String message) {
        super(message);
    }
}
