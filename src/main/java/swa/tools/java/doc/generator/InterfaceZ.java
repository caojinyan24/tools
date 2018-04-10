package swa.tools.java.doc.generator;

import java.util.Date;
import java.util.List;

/**
 * Created by jinyan on 4/10/18 4:21 PM.
 */
public class InterfaceZ {
    private List<MethodZ> methodZS;
    private String packageName;
    private String className;
    private String author;
    private String docName;
    private Date time;
    private Class clazz;

    public List<MethodZ> getMethodZS() {
        return methodZS;
    }

    public void setMethodZS(List<MethodZ> methodZS) {
        this.methodZS = methodZS;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDocName() {
        return docName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InterfaceZ{");
        sb.append("methodZS=").append(methodZS);
        sb.append(", packageName='").append(packageName).append('\'');
        sb.append(", className='").append(className).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", docName='").append(docName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
