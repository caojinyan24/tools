package swa.tools.java.doc.generator.test;

import java.io.Serializable;
import java.util.List;

/**
 * 华道查询不良信息的结果参数
 * Created by jinyan on 4/9/18 2:31 PM.
 */
public class IllegalInfoResponseDto implements Serializable {

    private static final long serialVersionUID = 776156567975259255L;
    /**
     * 查询结果(1:查询人员有违法记录；2：查询人员无违法记录；-1：异常状态)
     */
    private String result;
    /**
     * 结果描述
     */
    private String resultDesc;
    /**
     * 犯罪类型描述（A：在逃;B:前科；C:吸毒；D：涉毒）
     */
    private String msg;
    /**
     * 被查人员的案发时间（只有前科有案发时间，格式为[4,10)）
     */
    private List<String> caseTime;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getCaseTime() {
        return caseTime;
    }

    public void setCaseTime(List<String> caseTime) {
        this.caseTime = caseTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IllegalInfoResponseDto{");
        sb.append("result='").append(result).append('\'');
        sb.append(", resultDesc='").append(resultDesc).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", caseTime=").append(caseTime);
        sb.append('}');
        return sb.toString();
    }
}
