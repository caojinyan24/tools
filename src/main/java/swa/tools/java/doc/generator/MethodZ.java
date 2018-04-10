package swa.tools.java.doc.generator;

import java.lang.reflect.Method;

/**
 * Created by jinyan on 4/10/18 5:18 PM.
 */
public class MethodZ {
    private ClassZ request;
    private ClassZ response;
    private String methodName;
    private Method method;
    private String methodDesc;

    public ClassZ getRequest() {
        return request;
    }

    public void setRequest(ClassZ request) {
        this.request = request;
    }

    public ClassZ getResponse() {
        return response;
    }

    public void setResponse(ClassZ response) {
        this.response = response;
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
        sb.append("request=").append(request);
        sb.append(", response=").append(response);
        sb.append(", methodName='").append(methodName).append('\'');
        sb.append(", methodDesc='").append(methodDesc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
