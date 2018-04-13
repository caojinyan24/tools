package swa.tools.java.doc.generator;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import static freemarker.template.Configuration.VERSION_2_3_28;

/**
 * Created by jinyan on 4/13/18 10:34 AM.
 */
public class Writer {
    private static final Logger logger = LoggerFactory.getLogger(Writer.class);

    public static void main(String[] args) throws Exception {
        new Writer().write();
    }

    public void write() throws Exception {
        InterfaceZ in = new InterfaceZ();
        in = new Generator().parseInterfaceInfo("/home/jinyan/Documents/github/toos/src/main/java/swa/tools", "CreditBankinfoMapper", "", "", "countByExample");
        Configuration configuration = new Configuration(VERSION_2_3_28);
        configuration.setDirectoryForTemplateLoading(new File("/home/jinyan/Documents/github/toos/src/main/resources/java/doc/generator"));
        configuration.setDateTimeFormat("yyyy-MM-dd");
        InterfaceZ finalIn = in;
        configuration.setObjectWrapper(new ObjectWrapper() {
            @Override
            public TemplateModel wrap(Object obj) throws TemplateModelException {
                BeanModel result = new BeanModel(obj, new BeansWrapper(VERSION_2_3_28));
                return result;
            }
        });
        OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream("aa.md"));
        configuration.getTemplate("javaDocTemplate.ftl").process(in, w);
        w.flush();
        w.close();
    }
}
