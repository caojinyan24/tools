package swa.tools.java.doc.generator.test;


/**
 * 华道查询不良信息的请求参数
 * Created by jinyan on 4/9/18 2:30 PM.
 */
public class IllegalInfoRequestDto {
    private static final long serialVersionUID = 1191665250644688385L;

    private String name;

    private String idCard;

    public static void check(IllegalInfoRequestDto requestDto) throws Exception {
        if (requestDto == null) {
            throw new Exception("请求参数不能为空");
        }
        if (requestDto.getName() == null || "".equals(requestDto.getName())) {
            throw new Exception("姓名不能为空");
        }
        if (requestDto.getIdCard() == null || "".equals(requestDto.getIdCard())) {
            throw new Exception("身份证号码不能为空");
        }

    }

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
