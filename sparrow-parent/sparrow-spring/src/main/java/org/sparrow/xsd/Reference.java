package org.sparrow.xsd;

/**
 * @ClassName Reference
 * @Author Reference
 * @Description //TODO
 * @Date: 2018/12/20 11:05
 **/
public class Reference {
    private String id;
    private String name;
    private Integer age;

    private String interfaces;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String interfaces) {
        this.interfaces = interfaces;
    }
}
