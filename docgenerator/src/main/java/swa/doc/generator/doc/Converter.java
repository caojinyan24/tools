package swa.doc.generator.doc;

/**
 * @author jinyan
 * @date 4/13/18 2:24 PM
 */
class Converter {
    static String replace(String origin) {
        origin = origin.replace("<", "&lt;");
        origin = origin.replace(">", "&gt;");
        return origin;
    }
}
