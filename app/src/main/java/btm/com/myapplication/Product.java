package btm.com.myapplication;

/**
 * Created by Marden on 23/09/2018.
 */

public class Product {

    private Integer id;
    private String name;
    private String desc;
    private String value;

    public Product () {

    }

    public Product (String name, String desc, String value, Integer id) {
        this.name = name;
        this.desc = desc;
        this.value = value;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
