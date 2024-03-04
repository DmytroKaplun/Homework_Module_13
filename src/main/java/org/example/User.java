package org.example;

import com.google.gson.Gson;
import lombok.Getter;

public class User {
    @Getter
    private Integer id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    private User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.username = builder.username;
        this.email = builder.email;
        this.address = builder.address;
        this.phone = builder.phone;
        this.website = builder.website;
        this.company = builder.company;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", company=" + company +
                '}';
    }

    public static class Builder {
        private Integer id;
        private String name;
        public String username;
        private String email;
        private Address address;
        private String phone;
        private String website;
        private Company company;
        public Builder(String name, String username) {
            this.name = name;
            this.username = username;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }
        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }
        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }
        public Builder setWebsite(String website) {
            this.website = website;
            return this;
        }
        public Builder setCompany(Company company) {
            this.company = company;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }
}
