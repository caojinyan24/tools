package swa.tools.java.doc.generator.doc;

/**
 * @author jinyan
 * @date 4/13/18 2:24 PM
 */
public class Converter {
    static String replace(String origin) {
        origin = origin.replace("<", "&lt;");
        origin = origin.replace(">", "&gt;");
        return origin;
    }
}
