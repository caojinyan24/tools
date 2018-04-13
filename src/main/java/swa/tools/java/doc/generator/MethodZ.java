package swa.tools.java.doc.generator;

import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author jinyan
 * @date 4/10/18 5:18 PM
 */
public class MethodZ {
    private List<ClassZ> requests;
    private List<ClassZ> responses;
    private String methodName;
    private Method method;
    private String methodDesc;

    public List<ClassZ> getRequests() {
        return requests;
    }

    public void setRequests(List<ClassZ> requests) {
        this.requests = requests;
    }

    public List<ClassZ> getResponses() {
        return responses;
    }

    public void setResponses(List<ClassZ> responses) {
        this.responses = responses;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MethodZ{");
        sb.append("requests=").append(requests);
        sb.append(", responses=").append(responses);
        sb.append(", methodName='").append(methodName).append('\'');
        sb.append(", method=").append(method);
        sb.append(", methodDesc='").append(methodDesc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
