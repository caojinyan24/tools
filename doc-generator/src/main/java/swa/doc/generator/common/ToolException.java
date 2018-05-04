package swa.doc.generator.common;


public class ToolException extends RuntimeException {
    private String msg;

    public ToolException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
