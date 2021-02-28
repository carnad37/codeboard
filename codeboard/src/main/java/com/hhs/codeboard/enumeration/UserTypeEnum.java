package com.hhs.codeboard.enumeration;

public enum UserTypeEnum {
    ADMIN("A", "관리자"), NORMAL("N", "일반회원"), WAIT("W", "승인대기");
    
    private String typeName;
    private String typeCode;

    UserTypeEnum(String typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean matchTypeCode(String userType) {
        return this.typeCode.equals(typeCode);
    }

}
