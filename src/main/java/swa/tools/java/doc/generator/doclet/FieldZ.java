package swa.tools.java.doc.generator.doclet;

/**
 *
 * @author jinyan
 * @date 4/10/18 4:20 PM
 */
public class FieldZ {
    private String type;
    private String fieldName;
    private String desc;

    public FieldZ() {
    }

    public FieldZ(FieldZ fieldZ) {
        this.type = fieldZ.type;
        this.fieldName = fieldZ.fieldName;
        this.desc = fieldZ.desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FieldZ{");
        sb.append("type='").append(type).append('\'');
        sb.append(", fieldName='").append(fieldName).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
