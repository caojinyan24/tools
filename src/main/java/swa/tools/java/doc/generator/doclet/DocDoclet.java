package swa.tools.java.doc.generator.doclet;

import com.sun.javadoc.*;

import java.text.MessageFormat;


/**
 * Created by jinyan on 4/11/18 1:44 PM.
 */
public class DocDoclet extends Doclet {

    public static boolean start(RootDoc root) {
        ClassDoc[] classes = root.classes();
        for (int i = 0; i < classes.length; i++) {
            System.out.println("class:" + classes[i].name());
            //iterate over all methods and print their names.
            MethodDoc[] methods = classes[i].methods();
            System.out.println("Methods");
            System.out.println("-------");
            for (int j = 0; j < methods.length; j++) {
                System.out.println("Method: name = " + methods[j].name());
            }
            System.out.println("Fields");
            System.out.println("------");
            //iterate over all fields, printing name, comment text, and type.
            FieldDoc[] fields = classes[i].fields();
            for (int j = 0; j < fields.length; j++) {
                Object[] field_info = {fields[j].name(), fields[j].commentText(),
                        fields[j].type()};
                System.out.println(FIELDINFO.format(field_info));
//                iterate over all field tags and print their values.
                Tag[] tags = fields[j].tags();
                for (int k = 0; k < tags.length; k++) {
                    System.out.println("\tField Tag Name= " + tags[k].name());
                    System.out.println("\tField Tag Value = " + tags[k].text());
                }
            }
        }
        //No error processing done, simply return true.
        return true;
    }

    private static MessageFormat FIELDINFO =
            new MessageFormat("Field: name = {0}, comment = {1}, type = {2};");
}


