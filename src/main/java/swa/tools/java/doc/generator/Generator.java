package swa.tools.java.doc.generator;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swa.tools.Common.ToolsException;
import swa.tools.java.doc.generator.test.CreditBankinfoExample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jinyan on 4/10/18 4:25 PM.
 */
public class Generator {
    private static final Logger logger = LoggerFactory.getLogger(Generator.class);
    private Map<String, String> fileNameMap = Maps.newHashMap();

    public static void main(String[] args) throws Exception {
        Generator generator = new Generator();
        generator.iteratorPath("/home/jinyan/Documents/github/toos/src/main/java/swa/tools");
        List<FieldZ> fieldZS = generator.parseFieldZInfo(CreditBankinfoExample.class);
        System.out.println(fieldZS);
    }

    public void generator(String projectDir, String className, String methodName, String docName, String authorName) {
    }

    public void parseRequestInfo() {

    }

    public InterfaceZ parseInterfaceInfo(String projectDir, String className, String docName, String authorName, String methodName) throws Exception {
        InterfaceZ interfaceZ = new InterfaceZ();

        iteratorPath(projectDir);
        String fileName = fileNameMap.get(className);

        interfaceZ.setAuthor(authorName);
        interfaceZ.setDocName(docName);
        logger.debug("className:{}", className);

        String packageName = "";

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = "";
        String tempJoin = "";
        List<String> lines = Lists.newArrayList();
        while ((line = br.readLine()) != null) {
            if (line.startsWith("package")) {
                packageName = line.split(" ")[1];
                packageName = packageName.substring(0, packageName.length() - 1);
                continue;
            }
            lines.add(line);
        }
        interfaceZ.setClassName(className);
        interfaceZ.setPackageName(packageName);
        interfaceZ.setTime(new Date());
        interfaceZ.setClazz(Class.forName(packageName + "." + className));


        int methodLine = 0;
        int lastMethodLine = 0;

        Method[] methods = interfaceZ.getClazz().getMethods();
        List<MethodZ> methodZs = Lists.newArrayList();


        for (Method method : methods) {
            if (method.getName().equals(methodName) || !Strings.isNullOrEmpty(methodName)) {
                StringBuilder desc = new StringBuilder();

                for (int i = 0; i < lines.size(); i++) {
                    String currentLine = lines.get(i);
                    if (currentLine.contains(";")) {
                        lastMethodLine = methodLine;
                        methodLine = i;
                        if (!currentLine.contains(methodName)) {
                            desc.delete(0, desc.length());
                        }
                    } else {
                        desc.append(currentLine);
                    }
                    if (currentLine.contains(methodName)) {
                        break;
                    }
                }

                MethodZ methodZ = new MethodZ();
                String descS = desc.toString();
                descS = descS.replace("/", "");
                descS = descS.replace("*", "");
                methodZ.setMethodDesc(descS);
                methodZ.setMethod(method);
                methodZ.setMethodName(method.getName());
                methodZ.setRequests(parseClassZInfo(method.getParameterTypes()));
                methodZ.setResponses(parseClassZInfo(new Class[]{method.getReturnType()}));
                methodZs.add(methodZ);
            }
        }
        interfaceZ.setMethodZS(methodZs);
        System.out.println("interface:" + interfaceZ);
        return interfaceZ;
    }

    // 列出根目录下的所有文件
    private void iteratorPath(String dir) {
        File or = new File(dir);
        File[] files = or.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileNameMap.put(file.getName().substring(0, file.getName().length() - 5), file.getAbsoluteFile().toString());
                } else if (file.isDirectory()) {
                    iteratorPath(file.getAbsolutePath());
                }
            }
        }
    }

    //请求或响应
    // TODO: 4/13/18 添加多个参数解析
    private List<ClassZ> parseClassZInfo(Class<?>[] classes) throws Exception {
        List<ClassZ> list = Lists.newArrayList();
        ClassZ result = new ClassZ();
        // TODO: 4/10/18 根据classes 处理类嵌套的情况
        Class cla = classes[0];
        if (cla == null) {
            throw new ToolsException("class load error");
        }
        System.out.println(cla.getPackage());
        String packageName = String.valueOf(cla.getPackage());
        String className = cla.getName();

        if (Strings.isNullOrEmpty(fileNameMap.get(cla.getSimpleName()))) {
            result.setPackageName(packageName);
            result.setFieldList(Lists.newArrayList());
            result.setClazz(cla);
            result.setClassName(cla.getSimpleName());
            result.setClassDesc(cla.getSimpleName());
            return Lists.newArrayList(result);
        }

        BufferedReader br = new BufferedReader(new FileReader(fileNameMap.get(cla.getSimpleName())));
        String line = "";
        String tempJoin = "";
        List<String> lines = Lists.newArrayList();
        while ((line = br.readLine()) != null) {
            if (line.contains("class")) {
                break;
            }
            lines.add(line);
        }
        StringBuilder desc = new StringBuilder();
        for (int i = lines.size() - 1; i >= 0; i--) {
            if (lines.get(i).contains("import")) {
                break;
            }
            desc.append(lines.get(i));
        }
        result.setClassDesc(desc.toString());
        result.setClassName(cla.getName());
        result.setPackageName(packageName);
        result.setClazz(cla);
        result.setFieldList(parseFieldZInfo(cla));

        return Lists.newArrayList(result);
    }

    // TODO: 4/10/18 添加动态加载
//    public static void compiler(String name) throws IOException {
//        String javaPackageName = name.replace(".",File.separator)+".java";
//        String javaAbsolutePath = classPath+javaPackageName;
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//        compiler.run(null,null,null,"-encoding","UTF-8","-classpath",jarAbsolutePath.toString(),javaAbsolutePath);
//    }

    //请求或响应的属性
    private List<FieldZ> parseFieldZInfo(Class clazz) throws Exception {
        List<FieldZ> result = Lists.newArrayList();
        BufferedReader br = new BufferedReader(new FileReader(fileNameMap.get(clazz.getSimpleName())));
        String line = "";

        Boolean isBegin = false;
        List<String> lines = Lists.newArrayList();
        while ((line = br.readLine()) != null) {
            if (isBegin) {
                lines.add(line);
            }
            if (line.contains("class")) {
                isBegin = true;
            }
        }

        Field[] fields = clazz.getDeclaredFields();

        FieldZ fieldZ = new FieldZ();
        String desc = "";
        //status=1代表解析开始
        Integer status = 0;
        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i).trim();
            if (currentLine.startsWith("/**")) {
                status = 1;
                desc = "";
            } else if (currentLine.startsWith("*/")) {
                if (status == 1) {
                    status = 2;
                }
            } else if (currentLine.startsWith("*")) {
                desc += currentLine.substring(1, currentLine.length());
            } else {
                if (currentLine.endsWith(";") && status == 2) {
                    String[] words = currentLine.substring(0, currentLine.length() - 1).split(" ");
                    fieldZ.setFieldName(words[words.length - 1]);
                    fieldZ.setDesc(new String(desc));
                    fieldZ.setType(Converter.replace(words[words.length - 2]));
                    status = 0;
                    result.add(new FieldZ(fieldZ));
                    fieldZ = new FieldZ();
                    desc = "";
                }
            }
        }
        return result;
    }
}
