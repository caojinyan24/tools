package swa.tools.java.doc.generator.doclet;

import com.sun.javadoc.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jinyan on 4/11/18 1:44 PM.
 */
public class DocDoclet extends Doclet {
    public static void main(String[] args) {
//        com.sun.tools.javadoc.Main.execute(args);
    }

    //输出public类型的方法
    public static boolean start(RootDoc root) {
        try {
            ClassDoc[] classes = root.classes();
            InterfaceZ interfaceZ = new InterfaceZ();
            for (int i = 0; i < classes.length; i++) {
                ClassDoc clazz = classes[i];

                interfaceZ.setClassName(clazz.name());
                interfaceZ.setPackageName(clazz.containingPackage().name());

                MethodDoc[] methods = clazz.methods();

                List<MethodZ> methodZS = new ArrayList<>();
                for (int j = 0; j < methods.length; j++) {
                    MethodDoc methodDoc = methods[j];
                    methodZS.add(convert(methodDoc));
                }
                interfaceZ.setMethodZS(methodZS);
                System.out.println("--interface:" + interfaceZ);

            }
        } catch (Exception e) {
            System.out.println("***" + e);
        }
        return true;
    }

    private static MethodZ convert(MethodDoc methodDoc) {
        MethodZ methodZ = new MethodZ();
        try {
            methodZ.setMethodName(methodDoc.name());
            methodZ.setMethodDesc(methodDoc.commentText());
            methodZ.setResponses(convertResponse(methodDoc.returnType()));
            methodZ.setRequests(convertRequests(methodDoc.parameters()));
        } catch (Exception e) {
            System.out.println("***" + e);
        }
        return methodZ;
    }

    private static List<ClassZ> convertRequests(Parameter[] parameters) {
        List<ClassZ> result = new ArrayList<>();
        try {
            if (parameters != null) {
                for (Parameter parameter : parameters) {
                    ClassZ classZ = new ClassZ();
                    classZ.setClassDesc(parameter.name());
                    classZ.setPackageName(parameter.typeName());
                    ClassDoc reqClass = parameter.type().asClassDoc();
                    FieldDoc[] fieldDocs = reqClass.fields();
                    List<FieldZ> fieldZS = new ArrayList<>();
                    for (FieldDoc fieldDoc : fieldDocs) {
                        FieldZ fieldZ = new FieldZ();
                        fieldZ.setDesc(fieldDoc.getRawCommentText());
                        fieldZ.setFieldName(fieldDoc.name());
                        fieldZ.setType(fieldDoc.type().typeName());
                        fieldZS.add(fieldZ);
                    }
                    classZ.setFieldList(fieldZS);
                    result.add(classZ);
                }
            }
        } catch (Exception e) {
            System.out.println("***" + e);
        }
        return result;
    }


    private static List<ClassZ> convertResponse(Type response) {
        ClassZ result = new ClassZ();
        try {

            result.setClassName(response.typeName());
            result.setPackageName(response.simpleTypeName());
//            result.setClassDesc(response.asClassDoc().getRawCommentText());
            ClassDoc respClass = response.asClassDoc();
            System.out.println("####################3feild2:" + response.typeName() + "," + response.asClassDoc());

            FieldDoc[] fieldDocs = respClass.fields();
            List<FieldZ> fieldZS = new ArrayList<>();
            for (FieldDoc fieldDoc : fieldDocs) {
                FieldZ fieldZ = new FieldZ();
                fieldZ.setDesc(fieldDoc.getRawCommentText());
                fieldZ.setFieldName(fieldDoc.name());
                fieldZ.setType(fieldDoc.type().typeName());
                fieldZS.add(fieldZ);
            }
            result.setFieldList(fieldZS);
        } catch (Exception e) {
            System.out.println("***" + e);
        }
        List<ClassZ> re = new ArrayList<>();
        re.add(result);
        return re;
    }
}


