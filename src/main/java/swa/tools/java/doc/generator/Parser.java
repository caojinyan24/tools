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
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jinyan
 * @date 4/10/18 4:25 PM
 */
public class Parser {
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);
    private Map<String, String> fileNameMap = Maps.newHashMap();
    private Map<String, Class> classMap = Maps.newHashMap();
    private String projectDir;
    private String className;
    private String methodName;
    private String docName;
    private String author;


    public Parser() {
    }

    public Parser(String projectDir, String className, String methodName, String docName, String author) {
        this.projectDir = projectDir;
        this.className = className;
        this.methodName = methodName;
        this.docName = docName;
        this.author = author;
    }


    InterfaceZ parseInterfaceInfo() throws Exception {
        InterfaceZ interfaceZ = new InterfaceZ();


        iteratorForInfo(projectDir);
        String fileName = fileNameMap.get(className);
        String packageName = "";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
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
        interfaceZ.setDocName(docName);
        interfaceZ.setAuthor(author);

        Method[] methods = interfaceZ.getClazz().getMethods();
        List<MethodZ> methodZs = Lists.newArrayList();


        for (Method method : methods) {
            if (method.getName().equals(methodName) || !Strings.isNullOrEmpty(methodName)) {
                StringBuilder desc = new StringBuilder();
                for (String currentLine : lines) {
                    if (currentLine.contains(";")) {
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
        logger.info("interface:{}", interfaceZ);
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

    private void iteratorForInfo(String dir) throws Exception {
        iteratorPath(dir);
        Set<Map.Entry<String, String>> entrySet = fileNameMap.entrySet();
        for (Map.Entry entry : entrySet) {
            BufferedReader br = new BufferedReader(new FileReader((String) entry.getValue()));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("package")) {
                    line = line.replace("package", "").replace(";", "").trim();
                    classMap.put((String) entry.getKey(), Class.forName(line + "." + entry.getKey()));
                    break;
                }
            }
        }
    }

    //请求或响应
    private List<ClassZ> parseClassZInfo(Class<?>[] classes) throws Exception {
        List<ClassZ> result = new CopyOnWriteArrayList<>();
        for (Class c : classes) {
            result.addAll(parseClassZInfo(c));
        }
        //标示下次插入的位置
        for (ClassZ z : result) {
            for (FieldZ fieldZ : z.getFieldList()) {
                for (String key : fileNameMap.keySet()) {
                    if (fieldZ.getType().contains(key)) {
                        result.addAll(parseClassZInfo(classMap.get(key)));
                    }
                }
            }
        }

        return result;
    }

    private List<ClassZ> parseClassZInfo(Class cla) throws Exception {
        ClassZ result = new ClassZ();
        if (cla == null) {
            throw new ToolsException("class load error");
        }
        System.out.println(cla.getPackage());
        String packageName = String.valueOf(cla.getPackage());

        if (Strings.isNullOrEmpty(fileNameMap.get(cla.getSimpleName()))) {
            result.setPackageName(packageName);
            result.setFieldList(Lists.newArrayList());
            result.setClazz(cla);
            result.setClassName(cla.getSimpleName());
            result.setClassDesc(cla.getSimpleName());
            return Lists.newArrayList(result);
        }

        BufferedReader br = new BufferedReader(new FileReader(fileNameMap.get(cla.getSimpleName())));
        String line;
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


    //请求或响应的属性
    private List<FieldZ> parseFieldZInfo(Class clazz) throws Exception {
        List<FieldZ> result = Lists.newArrayList();
        BufferedReader br = new BufferedReader(new FileReader(fileNameMap.get(clazz.getSimpleName())));
        String line;

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


        FieldZ fieldZ = new FieldZ();
        StringBuilder desc = new StringBuilder();
        //status=1代表解析开始
        Integer status = 0;
        for (String line1 : lines) {
            String currentLine = line1.trim();
            if (currentLine.startsWith("/**")) {
                status = 1;
                desc = new StringBuilder();
            } else if (currentLine.startsWith("*/")) {
                if (status == 1) {
                    status = 2;
                }
            } else if (currentLine.startsWith("*")) {
                desc.append(currentLine.substring(1, currentLine.length()));
            } else {
                if (currentLine.endsWith(";") && status == 2) {
                    String[] words = currentLine.substring(0, currentLine.length() - 1).split(" ");
                    fieldZ.setFieldName(words[words.length - 1]);
                    fieldZ.setDesc(desc.toString());
                    fieldZ.setType(Converter.replace(words[words.length - 2]));
                    status = 0;
                    result.add(new FieldZ(fieldZ));
                    fieldZ = new FieldZ();
                    desc = new StringBuilder();
                }
            }
        }
        return result;
    }
}
