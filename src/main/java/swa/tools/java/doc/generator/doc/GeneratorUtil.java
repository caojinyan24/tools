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
        String className = "TestInterface";
        String methodName = "query";
        String author = "jinyan.cao";
        String docName = "donjjio*********";
        String templateName = "yooliCreditPlatformTemplate.ftl";
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
//
//    /**
//     * 通过动态编辑加载类
//     * 这种方式不需要把要处理的类拷贝到test目录下，但是需要保证文件中的引用都能被引进来，否则将无法动态编译
//     *
//     * @param dir        要加载的文件所在的目录
//     * @param className  要生成文档的类名
//     * @param methodName 接口名
//     * @param docName    生成文档的名称
//     * @param author     生成文档的作者
//     * @return 最终解析完成的接口信息
//     * @throws Exception ‘’
//     */
//    private static InterfaceZ parseFromDynamicLoad(String dir, String className, String methodName, String docName, String author) throws Exception {
//        loadClasses(dir);
//        return parseFromLocal(dir, className, methodName, docName, author);
//    }
//
//
//    // 加载根目录下的所有文件
//    private static void loadClasses(String dir) {
//        File or = new File(dir);
//        File[] files = or.listFiles();
//        if (files != null) {
//            for (File file : files) {
//                if (file.isFile() && file.getName().endsWith(".java")) {
//                    FILES.add(file.getPath());
//                    logger.info("files:{}", FILES);
//                } else if (file.isDirectory()) {
//                    loadClasses(file.getAbsolutePath());
//                }
//            }
//        }
//        while (true) {
//            for (String f : FILES) {
//                if (compiler(f) == 0 || !FILES.contains(f)) {
//                    FILES.remove(f);
//                }
//            }
//            if (FILES.isEmpty()) {
//                break;
//            }
//        }
//    }
//
//    /**
//     * 动态编译，生成.class文件
//     *
//     * @param name 类名
//     * @return 0代表成功
//     */
//    private static Integer compiler(String name) {
//        OutputStream outputStream = null;
//        try {
//            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
//            return javaCompiler.run(null, null, null, name);
//        } catch (Throwable e) {
//            logger.error("parseError:{}", e);
//            return -1;
//        } finally {
//            try {
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            } catch (IOException e) {
//            }
//        }
//    }


}


