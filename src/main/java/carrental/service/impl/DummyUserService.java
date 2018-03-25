package carrental.service.impl;

import carrental.dao.interf.LendRepository;
import carrental.dao.interf.UserRepository;
import carrental.domain.*;
import carrental.service.interf.ILendService;
import carrental.service.interf.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DummyUserService extends DummyUserServiceControl implements IUserService {

    @Autowired
    private UserRepository userDAO;
    @Autowired
    private LendRepository lendRepository;
    private ILendService lendService;

    public DummyUserService(UserRepository userDAO,ILendService lendService){
        this.userDAO = userDAO;
        this.lendService = lendService;
        setLendService(lendService);
    }

    @Override
    public User editUser(User updateUser)throws RuntimeException{
        editUserControl(updateUser);
        return userDAO.saveAndFlush(updateUser);
    }

    @Override
    public boolean controlPassword(String account, String password)throws RuntimeException{
        controlPasswordControl(account, password);
        return userDAO.findAll()
                      .stream()
                      .anyMatch(u -> u.getEmail().equals(account) && u.getPassword().equals(password));
    }

    @Override
    public BigDecimal addToAccount(User user, BigDecimal price)throws RuntimeException{
        addToAccountControl(user, price);
        user.setAccountBalance(user.getAccountBalance().add(price));
        userDAO.save(user);
        return user.getAccountBalance();
    }

    @Override
    public void pay(Lend lend)throws RuntimeException{
        payControl(lend);
        lend.getUser().setAccountBalance(lend.getUser().getAccountBalance().subtract(lendService.rentalPrice(lend).subtract(new BigDecimal(
                lend.getPayments().stream().mapToDouble(payment -> (payment.getPrice() == null ? new Double(0) : payment.getPrice().doubleValue())).sum()))));
        lendService.expireLend(lend);
        userDAO.save(lend.getUser());
    }

    private boolean checkName(String email)throws RuntimeException{
        checkNameControl(email);
        return userDAO.findAll()
                      .stream()
                      .anyMatch(u -> u.getEmail().equals(email));
    }

    @Override
    public BigDecimal payDetail(BigDecimal price, Lend lend) throws RuntimeException{
        checkPayDetailControl(price,lend);
        Payment payment = new Payment(price, Title.PAY, "");
        payment.setPrice(price);
        lend.addPayment(payment);
        User user = lend.getUser();
        addToAccount(user, price.negate());
        user.setAccountBalance(user.getAccountBalance());
        userDAO.saveAndFlush(user);
        lendRepository.save(lend);
        return user.getAccountBalance();
    }



    @Override
    public User createUser(String name, String address, String account, String pw, LocalDate driverLicenseExp,String accountNumber) throws RuntimeException{
        return createUser(name, address, account, pw, driverLicenseExp, accountNumber, Role.CUSTOMER);
    }

    @Override
    public User createUser(String name, String address, String account, String pw, LocalDate driverLicenseExp,String accountNumber, Role role) throws RuntimeException{
        createUserControl(name, address, account, pw,accountNumber,driverLicenseExp);
        if(!checkName(account)){
            User newUser = new User(name,address,account,pw,driverLicenseExp,accountNumber,role);
            userDAO.save(newUser);
            return newUser;
        }
        throw new IllegalArgumentException("Már regisztráltak ezzel az e-mail címmel!");
    }

    @Override
    public User createUser(User user){
        createUserControl(user.getName(),user.getAddress(),user.getEmail(),user.getPassword(),user.getAccountNumber(),user.getDriverLicenseExp());
        if(!checkName(user.getEmail())){
            User newUser = new User(user.getName(),user.getAddress(),user.getEmail(),user.getPassword(),user.getDriverLicenseExp(),user.getAccountNumber());
            newUser.setRole(user.isAdmin() ? Role.ADMIN : Role.CUSTOMER);
            userDAO.save(newUser);
            return newUser;
        }
        throw new IllegalArgumentException("Már regisztráltak ezzel az e-mail címmel!");
    }


    @Override
    public List<User> listUser(){
        return userDAO.findAll();
    }


    @Override
    public User deleteUser(String name)throws RuntimeException{
        checkNameControl(name);
        User user = userDAO.findByName(name);
        isAdminControl(user, "Nincs ilyen felhasználó!");
        Lend lend = getLend(user);
        lendService.deleteLend(name,lend.getStartDate());
        userDAO.delete(user);
        return user;
    }

    @Override
    public User deleteUser(User user)throws RuntimeException{
        isAdminControl(user, "Nincs ilyen felhasználó!");
        lendService.deleteLend(user);
        userDAO.delete(user);
        return user;
    }

    private Lend getLend(User user) {
        return lendService.listActualLend().stream()
                                                 .filter(l -> l.getUser().getID().equals(user.getID()))
                                                 .findAny()
                                                 .get();
    }

    @Override
    public User login(String username, String password) throws RuntimeException{
        if(!controlPassword(username,password)){
            throw new IllegalArgumentException("Nem adta meg jól a felhasználónevet vagy a jelszót");
        }else{
            return getUserOfEmail(username);
        }
    }

    @Override
    public User getUserOfName(String name){
        return userDAO.findByName(name);
    }

    @Override
    public User getUserOfEmail(String email){
        return userDAO.findByEmail(email);
    }
}
