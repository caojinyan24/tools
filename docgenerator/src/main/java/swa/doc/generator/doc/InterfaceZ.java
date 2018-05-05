package swa.doc.generator.doc;

import java.util.Date;
import java.util.List;

/**
 * @author jinyan
 * @date 4/10/18 4:21 PM
 */
public class InterfaceZ {
    private List<MethodZ> methodZS;
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

    public void setDocName(String docName) {
        this.docName = docName;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InterfaceZ{");
        sb.append("methodZS=").append(methodZS);
        sb.append(", className='").append(className).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", docName='").append(docName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
