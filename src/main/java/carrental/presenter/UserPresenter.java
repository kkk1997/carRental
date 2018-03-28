package carrental.presenter;

import carrental.domain.Lend;
import carrental.domain.Role;
import carrental.domain.User;
import carrental.service.interf.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserPresenter {
    @Autowired
    private IUserService userService;

    private Role role;

    private User actualUser;

    public User login(String uname, String pass) throws IllegalArgumentException {
        return userService.login(uname, pass);
    }

    public User signup(String name, String address, String account, String pw, LocalDate driverLicExp, String accountNumber) throws IllegalArgumentException {
        return userService.createUser(name,address,account,pw,driverLicExp,accountNumber);
    }

    public User editUser(User updateUser){
        return userService.editUser(updateUser);
    }

    public BigDecimal addToAccount(User user, BigDecimal price){
        return userService.addToAccount(user,price);
    }

    public void pay(Lend lend){
        userService.pay(lend);
    }

    public User deleteUser(User user){
        return userService.deleteUser(user);
    }

    public List<User> listUser(){
        return userService.listUser();
    }

    public User getUserOfEmail(String email){
        return userService.getUserOfEmail(email);
    }

    public void setUserRole(User user) { role = user.getRole(); }

    public Role getUserRole() { return role; }

    public User createUser(User user){
        return userService.createUser(user);
    }

    public User getActualUser() {
        return actualUser;
    }

    public void setActualUser(User actualUser) {
        this.actualUser = actualUser;
    }

    public BigDecimal payDetail(BigDecimal price, Lend lend){
        return userService.payDetail(price,lend);
    }
}
