package carrental.service.impl;

import carrental.domain.Lend;
import carrental.domain.Payment;
import carrental.domain.User;
import carrental.service.interf.ILendService;
import com.vaadin.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;


public class DummyUserServiceControl {
    private ILendService lendService;

    @Autowired
    protected MessageSource messageSource;

    protected  void setLendService(ILendService lendService){
        this.lendService = lendService;
    }

    protected void editUserControl(User updateUser) {
        if(updateUser == null || !isValidUser(updateUser)){
            throw new IllegalArgumentException("Nem adta meg jól az adatokat!");
        }
    }

    protected void controlPasswordControl(String account, String password) {
        if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
            throw new IllegalArgumentException("Nem adta meg jól a felhasználónevet vagy a jelszót");
        }
    }

    protected void addToAccountControl(User user, BigDecimal price) {
        if(!isValidUser(user)){
            throw new IllegalArgumentException("Nem adta meg jól a felhasználót vagy az árat!");
        }
    }

    protected void payControl(Lend lend) {
        if(!isValidUser(lend.getUser())){
            throw new IllegalArgumentException("Nem adta meg jól a nevet!");
        }
        if (lend.getUser().getAccountBalance().compareTo(lendService.rentalPrice(lend).subtract(new BigDecimal(lend.getPayments().stream()
                .mapToDouble(payment -> (payment.getPrice() == null ? new Double(0) : payment.getPrice().doubleValue())).sum()))) < 0) {
            throw new IllegalArgumentException("Nincs elegendő pénz!");
        }
    }

    protected void checkNameControl(String name) {
        if(StringUtils.isEmpty(name)){
            throw new IllegalArgumentException("Nem adta meg jól az emailt!");
        }
    }

    protected void isAdminControl(User user, String s) {
        if (user == null) {
            throw new IllegalArgumentException(s);
        }
    }

    protected void createUserControl(String name, String address, String account, String pw, String accountNumber, LocalDate licenseExpdate) {
        isValidUserArgument(name,address,account,pw,accountNumber,licenseExpdate);
    }

    private boolean isValidUserArgument(String name, String address, String account, String pw,String accountNumber,LocalDate licenseExpdate) {
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(address) || StringUtils.isEmpty(pw) || accountNumber.isEmpty() || licenseExpdate == null){
            throw new IllegalArgumentException("Nem lehet üres mező!");
        }else if(!name.matches("^[\\p{L} .'-]+$")){
            throw new IllegalArgumentException("Nem adta meg jól a nevet!");
        }else if(!account.matches("^(.+)@(.+)$")){
            throw new IllegalArgumentException("Nem adta meg jól az e-mailt!");
        }else if(pw.length() < 4){
            throw new IllegalArgumentException("Legalább 4 karakter hosszú kell legyen a jelszó!");
        }else if(accountNumber.length() != 12){
            throw new IllegalArgumentException("12 karakter hosszú a bankszámlaszám!");
        }else if(!accountNumber.matches("[0-9]+")){
            throw new IllegalArgumentException("Nem adta meg jól a bankszámlaszámot!");
        }else if(licenseExpdate.compareTo(LocalDate.now()) < 0){
            throw new IllegalArgumentException("Lejárt a jogsítvány!");
        }
        return true;
    }

    private boolean isValidUser(User user){
        return user != null && isValidUserArgument(user.getName(),user.getAddress(),user.getEmail()
                ,user.getPassword(),user.getAccountNumber(),user.getDriverLicenseExp());
    }

    protected void  checkPayDetailControl(BigDecimal price, Lend lend){
        if(price.compareTo(BigDecimal.ZERO) < 1){
            throw new IllegalArgumentException("Rossz összeg!");
        }
        else if((paymentSum(lend).add(price)).compareTo(lendService.rentalPrice(lend)) > 0){
            throw new IllegalArgumentException("Több, mint a kölcsönzés összege!");
        }
    }

    private BigDecimal paymentSum(Lend lend){
        BigDecimal sum = BigDecimal.ZERO;
        for(Payment payment : lend.getPayments()){
            sum = sum.add(payment.getPrice());
        }
        return sum;
    }
}
