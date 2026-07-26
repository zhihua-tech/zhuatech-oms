/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.oms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "oms_user")
public class UserAccount extends BaseEntity {
    public enum Role { ADMIN, MANAGER, SALES, PURCHASING, FINANCE, WAREHOUSE, VIEWER }

    @Column(nullable = false, unique = true, length = 32)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 50)
    private String fullName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;
    @Column(length = 50)
    private String department;
    @Column(nullable = false)
    private boolean enabled = true;

    protected UserAccount() {}

    public UserAccount(String username, String password, String fullName, Role role, String department) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.department = department;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public Role getRole() { return role; }
    public String getDepartment() { return department; }
    public boolean isEnabled() { return enabled; }
}
