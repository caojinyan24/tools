package swa.tools.java.doc.generator.doclet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jinyan
 * @date 4/16/18 8:36 PM
 */
public class GeneratorUtil {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorUtil.class);

    public static void main(String[] args) {
        com.sun.tools.javadoc.Main.execute(args);
    }

}
