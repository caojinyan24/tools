package swa.tools.java.doc.generator.doc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jinyan
 * @date 4/13/18 4:07 PM
 */
public class GeneratorUtil {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorUtil.class);
    private static List<String> FILES = new CopyOnWriteArrayList<>();

    /**
     * 文档生成，可通过两种方式选择类的加载方式
     *
     * @param args ‘’
     * @throws Exception ‘’
     */
    public static void main(String[] args) throws Exception {
        //待生成的类
        String className = "IScanningPersonalReportService";
        //待生成的类的方法,为null时生成接口类的所有方法
        String methodName = "query";
        //文档作者
        String author = "jinyan.cao";
        //文档名称
        String docName = "DocName";
        //生成文档选用的模板名称
        String templateName = "javaDocTemplate.ftl";
        String dir = new File("").getAbsolutePath() + "/src/main/java/swa/tools/java/doc/generator/doc/test/";
        InterfaceZ interfaceZ = parseFromLocal(dir, className, methodName, docName, author);
        new Writer(templateName).write(interfaceZ);
    }

    /**
     * 通过把要处理的类文件拷贝至test目录下来加载类
     *
     * @param dir        要加载的文件所在的目录
     * @param className  要生成文档的类名
     * @param methodName 接口名
     * @param docName    生成文档的名称
     * @param author     生成文档的作者
     * @return 最终解析完成的接口信息
     * @throws Exception ‘’
     */
    private static InterfaceZ parseFromLocal(String dir, String className, String methodName, String docName, String author) throws Exception {
        return new Parser(dir, className, methodName, docName, author).parseInterfaceInfo();
    }


}


