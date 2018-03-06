package carrental.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Table
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private BigDecimal accountBalance;
    @Column
    private LocalDate driverLicenseExp;
    @Column
    private String accountNumber;
    @Column
    private Role role;


    public User(String name, String address, String email, String password, LocalDate driverLicenseExp,String accountNumber) {
        this(name, address, email, password, driverLicenseExp, accountNumber, Role.CUSTOMER);
    }

    public User(String name, String address, String email, String password, LocalDate driverLicenseExp,String accountNumber, Role role) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
        accountBalance = BigDecimal.ZERO;
        this.driverLicenseExp = driverLicenseExp;
        this.role = role;
        this.accountNumber = accountNumber;
    }

    public User(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDriverLicenseExp() {
        return driverLicenseExp;
    }

    public void setDriverLicenseExp(LocalDate driverLicenseExp) {
        this.driverLicenseExp = driverLicenseExp;
    }

    public void setAddress(String address) { this.address = address; }

    public void setEmail(String email) {  this.email = email;  }

    public void setID(Long ID) {  this.ID = ID;  }

    public void setPassword(String password) { this.password = password;  }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setRole(Role role) {  this.role = role;  }

    public String getName() {
        return name;
    }

    public String getAddress() {  return address;   }

    public String getEmail() {   return email;  }

    public Long getID() { return ID; }

    public String getPassword() {  return password;  }

    public Role getRole() {  return role;  }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public boolean isAdmin() { return Role.ADMIN.equals(role); }

    public void setAdmin(Boolean role) { this.role = role ? Role.ADMIN : Role.CUSTOMER; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user1 = (User) o;

        if (!getName().equals(user1.getName())) return false;
        if (!getAddress().equals(user1.getAddress())) return false;
        if (!getEmail().equals(user1.getEmail())) return false;
        if (getID() != user1.getID()) return false;
        if (!getPassword().equals(user1.getPassword())) return false;
        if (getAccountBalance() != user1.getAccountBalance()) return false;
        return getRole() == user1.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,address,password, email, ID,accountBalance, role,accountNumber);
    }

    @Override
    public String toString() {
        return  name;
    }


}