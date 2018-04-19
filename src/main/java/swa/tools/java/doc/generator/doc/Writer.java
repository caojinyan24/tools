package swa.tools.java.doc.generator.doc;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swa.tools.java.doc.generator.doclet.InterfaceZ;

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
            configuration.setObjectWrapper(new ObjectWrapper() {
                @Override
                public TemplateModel wrap(Object obj) throws TemplateModelException {
                    return new BeanModel(obj, new BeansWrapper(VERSION_2_3_28));
                }
            });
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
