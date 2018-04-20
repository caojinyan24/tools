package swa.tools.java.doc.generator.doc;

import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import static freemarker.template.Configuration.VERSION_2_3_28;

/**
 * @author jinyan
 * @date 4/13/18 10:34 AM
 */
public class Writer {
    private static final Logger logger = LoggerFactory.getLogger(Writer.class);
    private String templateName;

    public Writer() {
    }

    public Writer(String templateName) {
        this.templateName = templateName;
    }

    public void write(InterfaceZ interfaceZ) throws Exception {
        OutputStreamWriter w = null;
        try {
            Configuration configuration = new Configuration(VERSION_2_3_28);
            configuration.setDirectoryForTemplateLoading(new File(new File("").getAbsolutePath() + "/src/main/resources/java/doc/generator"));
            configuration.setDateTimeFormat("yyyy-MM-dd");
            w = new OutputStreamWriter(new FileOutputStream("docs/" + interfaceZ.getClassName() + "Doc.md"));
            configuration.getTemplate(templateName).process(interfaceZ, w);
            w.flush();
        } finally {
            if (w != null) {
                w.close();
            }
        }

    }


}
