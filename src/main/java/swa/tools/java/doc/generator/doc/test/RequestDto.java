package swa.tools.java.doc.generator.doc.test;


import java.io.Serializable;

/**
 * 华道查询不良信息的请求参数
 * Created by jinyan on 4/9/18 2:30 PM.
 */
public class RequestDto implements Serializable {
    private static final long serialVersionUID = 1191665250644688385L;
    /**
     * aa
     */
    private String name;
    /**
     * aa
     */
    private String idCard;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IllegalInfoRequestDto{");
        sb.append(super.toString());
        sb.append("name='").append(name).append('\'');
        sb.append(", idCard='").append(idCard).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
