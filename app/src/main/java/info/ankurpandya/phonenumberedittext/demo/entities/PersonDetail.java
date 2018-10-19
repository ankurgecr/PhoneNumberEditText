package info.ankurpandya.phonenumberedittext.demo.entities;

import java.io.Serializable;

public class PersonDetail implements Serializable {
    private String id;
    private String name;
    private String phoneNumber;

    public PersonDetail(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return this.id.equals(((PersonDetail) obj).getId());
        } catch (Exception e) {
            return super.equals(obj);
        }
    }
}
