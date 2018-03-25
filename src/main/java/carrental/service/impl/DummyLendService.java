package carrental.service.impl;

import carrental.dao.interf.CarRepository;
import carrental.dao.interf.LendRepository;
import carrental.dao.interf.PaymentRepository;
import carrental.dao.interf.UserRepository;
import carrental.domain.*;
import carrental.service.LendVariablesNotValidException;
import carrental.service.interf.ILendService;
import carrental.service.interf.IPaymentService;
import carrental.service.interf.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class DummyLendService extends DummyLendServiceControl implements ILendService {

    private static final Logger LOG = LoggerFactory.getLogger(DummyLendService.class);
    private LendRepository lendRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IPaymentService paymentService;
    @Autowired
    private IUserService userService;
    @Autowired
    private PaymentRepository paymentRepository;

    public DummyLendService(LendRepository lendRepository){
        this.lendRepository = lendRepository;
        setLendDAO(lendRepository);
    }

    @Override
    public Status updateLend(User user) {
        try {
            Lend lend = getActualLendOfUserName(user.getName());
            int days = daysCalc(LocalDate.now(), lend.getEndDate());
            if (lend.getActual() && days < 1) {
                //    sendEmail(lend.getUser().getEmail(), MessageSourc.getMessage("user.lend.expiredLend"), MessageSourc.getMessage("user.lend.expiredLend") + "\n" + lend);
                LOG.info("Lejárt a kölcsönzés! Adatai: {}", lend);
                return Status.EXPIRED;
            } else if (lend.getActual() && days < 3) {
                //  sendEmail(lend.getUser().getEmail(), MessageSourc.getMessage("user.lend.soonExpireLend"), MessageSourc.getMessage("user.lend.soonExpLend") + lend + MessageSourc.getMessage("user.lend.soonLend") + days + MessageSourc.getMessage("user.lend.day"));
                LOG.info("Hamarosan lejár a {} kölcsönzés, még {} nap", lend, days);
                return Status.SOON;
            }
            LOG.info("Minden rendben");
            return Status.OK;
        } catch (NoSuchElementException e) {
            LOG.info("Nincs aktuális kölcsönzése");
        }
        return Status.WRONG;
    }

    @Override
    public List<Lend> listExpiredLend(){
        return lendRepository.findAll()
                              .stream()
                              .filter(l -> !l.getActual())
                              .collect(Collectors.toList());
    }

    @Override
    public List<Lend> listActualLend(){
        return lendRepository.findAll()
                .stream()
                .filter(l -> l.getActual())
                .collect(Collectors.toList());
    }

    @Override
    public Lend expireLend(Lend lend)throws LendVariablesNotValidException {
        Payment payment;
        if(lend.getActual() && lend.getEndDate().compareTo(LocalDate.now()) < 0){
            payment = new Payment(rentalPrice(lend), Title.PENALTY,"Késett " + daysCalc(lend.getEndDate(),LocalDate.now())+ " napot");
        }else{
            payment = new Payment(rentalPrice(lend), Title.PAY,"");
        }
        return getLend(lend, payment);
    }

    private Lend getLend(Lend lend, Payment payment) {
        lend.setActual(false);
        lend.getCar().setLend(null);
        lend.addPayment(payment);
        carRepository.saveAndFlush(lend.getCar());
        paymentRepository.save(payment);
        lend.setEndDate(LocalDate.now());
        return lendRepository.saveAndFlush(lend);
    }


    @Override
    public Lend expireLendByUser(User user) throws RuntimeException{
        Lend lend = lendRepository.findLendOfUser(user);
        return expireLend(lend);
    }

    @Override
    public boolean isUserLend(String user) throws LendVariablesNotValidException{
        Lend lend = isUserLendControl(user);
        return lend.getActual();
    }

    @Override
    public BigDecimal rentalPrice(Lend lend) throws RuntimeException{
        rentalPriceControl(lend);
        BigDecimal price = BigDecimal.ZERO;
        price = getPrice(lend, price);
        return price;
    }

    private BigDecimal getPrice(Lend lend, BigDecimal price) {
        if(lend.getActual() && lend.getEndDate().compareTo(LocalDate.now()) < 0){
            price = expiredLendPriceCalc(lend);
        }else{
            price = price.add(new BigDecimal(daysCalc(lend.getStartDate(),lend.getEndDate())));
            price = price.multiply(lend.getCar().getPrice());
        }
        return price;
    }

    @Override
    public Lend createLend(User user, Car car, LocalDate startDate, LocalDate endDate)throws LendVariablesNotValidException {
        checkLend(user, car, startDate, endDate);
        Lend lend = new Lend(car, user, startDate, endDate) ;
        BigDecimal price = rentalPrice(lend).multiply(new BigDecimal(0.6));

        if(price.compareTo(user.getAccountBalance()) > 0){
            throw new LendVariablesNotValidException("Nincs elegendő pénz");
        }else if(user.getDriverLicenseExp().compareTo(endDate) < 0){
            throw new LendVariablesNotValidException("Lejár a jogosítvány");
        }
        else if(paymentService.charge(user.getAccountNumber(),price)){
            return lendAddPaymend(user, lend, price);
        }else{
            throw new LendVariablesNotValidException("Nem megfelelő!");
        }
    }

    private Lend lendAddPaymend(User user, Lend lend, BigDecimal price) {
        lend.getCar().setLend(lend);
        Payment payment = new Payment(price, Title.DEPOSIT, "");
        payment.setPrice(price);
        lend.addPayment(payment);
        lendRepository.save(lend);
        userService.addToAccount(user, price.negate());
        userRepository.saveAndFlush(user);
        lendRepository.saveAndFlush(lend);
        carRepository.saveAndFlush(lend.getCar());
        return lend;
    }

    private void checkLend(User user, Car car, LocalDate startDate, LocalDate endDate) {
        lendControl(user, car, startDate, endDate);
        boolean isLend = listLend().stream()
                .anyMatch(lend -> lend.getActual() && lend.getUser().equals(user));
        if(isLend){
            throw new LendVariablesNotValidException("Már van aktuális kölcsönzése!");
        }
        boolean isAllocated = listLend().stream()
                .anyMatch(lend -> lend.getCar().getLicensePlateNumber().equals(car.getLicensePlateNumber()) && lend.getEndDate().compareTo(startDate) > 0);
        if(isAllocated){
            throw new LendVariablesNotValidException("Erre az időszakra le van foglalva az autó!");
        }
    }

    @Override
    public Lend editLend(Lend updateLend) throws RuntimeException{
        editLendControl(updateLend);
        return lendRepository.saveAndFlush(updateLend);
    }

    @Override
    public Lend deleteLend(String user,LocalDate startDate) throws LendVariablesNotValidException{
        Lend deleteLend = deleteLendControl(user, startDate);
        lendRepository.delete(deleteLend);
        return deleteLend;
    }

    @Override
    public Lend deleteLend(User user) throws LendVariablesNotValidException{
        Lend deleteLend = deleteLendControl(user);
        if(deleteLend != null){
            lendRepository.delete(deleteLend);
        }
        return deleteLend;
    }

    @Override
    public int daysCalc(LocalDate startDate, LocalDate endDate) throws RuntimeException{
        daysCalcControl(startDate, endDate);
        int startDateDays = startDate.getYear() * 365 + startDate.getMonthValue() * 31 + startDate.getDayOfMonth();
        int endDateDays = endDate.getYear() * 365 + endDate.getMonthValue() * 31 + endDate.getDayOfMonth();
        return endDateDays - startDateDays;
    }

    @Override
    public List<Lend> listLend(){
        return lendRepository.findAll();
    }

    @Override
    public BigDecimal expiredLendPriceCalc(Lend lend){
        BigDecimal days = new BigDecimal(daysCalc(lend.getStartDate(),lend.getEndDate()));
        BigDecimal expiredDays = new BigDecimal(daysCalc(lend.getEndDate(),LocalDate.now()));
        BigDecimal price = BigDecimal.ZERO;
        price = price.add(days.multiply(lend.getCar().getPrice()));
        price = price.add(expiredDays.multiply(lend.getCar().getPrice().multiply((new BigDecimal(1.5)))));
        return price;
    }

    @Override
    public Lend getLendOfUserName(String name){
        return lendRepository.findAll().stream()
                                .filter(lend -> lend.getUser().getName().equals(name))
                                .findFirst()
                                .get();
    }

    @Override
    public Lend getActualLendOfUserName(String name){
        return lendRepository.findAll().stream()
                .filter(lend -> lend.getUser().getName().equals(name) && lend.getActual())
                .findFirst()
                .get();
    }

    private void sendEmail(String email,String subject, String text){
        final String username = "car0rental0email@gmail.com";
        final String password = "carrental";

        Properties props = getEmailProperties();

        Session session = getSession(username, password, props);

        try {
            getEmailMessage(email, subject, text, session);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void getEmailMessage(String email, String subject, String text, Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("car0rental0email@gmail.com"));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email));
        message.setSubject(subject);
        message.setText(text);

        Transport.send(message);

        LOG.info("Sikeres email");
    }

    private Session getSession(String username, String password, Properties props) {
        return Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    private Properties getEmailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    @Override
    public String getUserData(User user) {
        Lend lend = lendRepository.findAll()
                .stream()
                .filter(l -> l.getUser().getID().equals(user.getID()))
                .findFirst().get();
        return "Rendszám " + lend.getCar().getLicensePlateNumber()
                + " Kölcsönzés kezdete " + lend.getStartDate()
                + " Kölcsönzés lejárta " + lend.getEndDate()
                + " Kölcsönzés összege: " + rentalPrice(lend)
                + " Ügyfél egyenlege: "  + lend.getUser().getAccountBalance() + " Ft";
    }
}
