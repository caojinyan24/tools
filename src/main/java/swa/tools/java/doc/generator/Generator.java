package swa.tools.java.doc.generator;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swa.tools.Common.ToolsException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by jinyan on 4/10/18 4:25 PM.
 */
public class Generator {
    private static final Logger logger = LoggerFactory.getLogger(Generator.class);
    private Map<String, String> fileNameMap = Maps.newHashMap();

    InterfaceZ interfaceZ = new InterfaceZ();

    public void generator(String projectDir, String className, String methodName, String docName, String authorName) {
    }

    public void parseRequestInfo() {

    }

    public void parseInterfaceInfo(String projectDir, String className, String docName, String authorName, String methodName) throws Exception {

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
                methodZ.setRequest(parseClassZInfo(method.getParameterTypes()));
                methodZ.setResponse(parseClassZInfo(new Class[]{method.getReturnType()}));
                methodZs.add(methodZ);
            }
        }
        interfaceZ.setMethodZS(methodZs);
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
    private ClassZ parseClassZInfo(Class<?>[] classes) throws Exception {
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
            return result;
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

        return result;
    }

    //请求或响应的属性
    private List<FieldZ> parseFieldZInfo(Class clazz) throws Exception {
        List<FieldZ> result = Lists.newArrayList();
        Map<String, Field> fieldMap = Maps.newHashMap();
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
        List<String> find = Lists.newArrayList();
        for (Field field : fields) {
            fieldMap.put(field.getType().getSimpleName() + " " + field.getName(), field);
            find.add(field.getType().getName() + " " + field.getName());
        }
        for (int i = 0; i < lines.size(); i++) {
            for (String s : find) {
                if (lines.get(i).contains(s)) {
                    // TODO: 4/10/18 解析
                    String desc = "";
                    for (int j = i - 1; j >= 0; j--) {
                        if (lines.get(j).contains(";")) {
                            break;
                        }
                        desc = lines.get(j) + desc;
                    }
                    desc = desc.replace("/", "");
                    desc = desc.replace("*", "");
                    FieldZ fieldZ = new FieldZ();
                    fieldZ.setDesc(desc);
                    Field field = fieldMap.get(s);
                    fieldZ.setFieldName(field.getName());
                    fieldZ.setType(field.getType().getName());
                    result.add(fieldZ);
                }
            }
        }
        return result;
    }

    // TODO: 4/10/18 添加动态加载
//    public static void compiler(String name) throws IOException {
//        String javaPackageName = name.replace(".",File.separator)+".java";
//        String javaAbsolutePath = classPath+javaPackageName;
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//        compiler.run(null,null,null,"-encoding","UTF-8","-classpath",jarAbsolutePath.toString(),javaAbsolutePath);
//    }

    public static void main(String[] args) throws Exception {
        Generator generator = new Generator();
        generator.parseInterfaceInfo("/home/jinyan/Documents/github/toos/src/main/java/swa/tools", "CreditBankinfoMapper", "", "", "countByExample");
        System.out.println(generator.fileNameMap);
        System.out.println(generator.interfaceZ);
    }
}
