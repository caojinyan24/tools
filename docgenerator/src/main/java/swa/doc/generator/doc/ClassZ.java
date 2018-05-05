package swa.doc.generator.doc;

import java.util.List;

/**
 *
 * @author jinyan
 * @date 4/10/18 5:11 PM
 */
public class ClassZ {
    private List<FieldZ> fieldList;
    private String className;
    private String packageName;
    private String classDesc;
    private Class clazz;

    public List<FieldZ> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<FieldZ> fieldList) {
        this.fieldList = fieldList;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getClassDesc() {
        return classDesc;
    }

    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClassZ{");
        sb.append("fieldList=").append(fieldList);
        sb.append(", className='").append(className).append('\'');
        sb.append(", packageName='").append(packageName).append('\'');
        sb.append(", classDesc='").append(classDesc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
