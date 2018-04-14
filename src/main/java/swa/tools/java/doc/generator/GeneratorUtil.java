package swa.tools.java.doc.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jinyan
 * @date 4/13/18 4:07 PM
 */
public class GeneratorUtil {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorUtil.class);
    // TODO: 4/10/18 添加动态加载


    public Integer compiler(String name) {
        OutputStream outputStream = null;
        try {
            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            outputStream = new FileOutputStream(new File(""));
            return javaCompiler.run(null, outputStream, null, name);
        } catch (Throwable e) {
            logger.error("parseError:{}", e);
            return -1;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
            }
        }
    }


    public static void main(String[] args) throws Exception {
////        String filePath = "/home/jinyan/Documents/github/toos/src/main/java/swa/tools";
        String className = "IBadInfoQueryService";
        String methodName = "queryIllegalInfo";
        String author = "jinyan.cao";
        String docName = "donjjio*********";
        String dir = new File("").getAbsolutePath() + "/src/main/java/swa/tools/java/doc/generator/test/";
        CopyOnWriteArrayList<File> fileList = new CopyOnWriteArrayList<>();
        new GeneratorUtil().iteratorFile(dir, fileList);
        InterfaceZ interfaceZ = new Parser(dir, className, methodName, docName, author).parseInterfaceInfo();
        System.out.println("interface:" + interfaceZ);
        new Writer("yooliCreditPlatformTemplate.ftl").write(interfaceZ);
//
    }

    private void iteratorFile(String filePath, CopyOnWriteArrayList<File> fileList) {
        File or = new File(filePath);
        fileList.addAll(CollectionUtils.arrayToList(or.listFiles()));

        while (true) {
            if (fileList.isEmpty()) {
                break;
            }
            for (File file : fileList) {
                if (file.isFile()) {
                    String fileName = file.getAbsolutePath();
                    try {
                        if (fileName.endsWith(".class")) {
                            fileList.remove(file);
                            continue;
                        }
                        Integer status = new GeneratorUtil().compiler(fileName);
                        if (status == 0) {
                            fileList.remove(file);
                            logger.info("left:{},fileName:{}", fileList.size(), fileName);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (file.isDirectory()) {
                    fileList.remove(file);
                    iteratorFile(file.getAbsolutePath(), fileList);

                }
            }
        }
    }
}


