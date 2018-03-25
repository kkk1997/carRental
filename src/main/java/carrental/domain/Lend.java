package carrental.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LEND")
public class Lend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    @OneToOne
    private Car car;
    @OneToOne
    private User user;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column
    private Boolean actual;

    @Column
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Payment> payments;


    public Lend(Car car, User user, LocalDate startDate, LocalDate endDate) {
        this.car = car;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        actual = true;
        payments = new ArrayList<>();
    }

    public Lend(){

    }

    public String getActualString() {
        return actual? "igen" : "nem";
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payment) {
        this.payments = payment;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {   return endDate;  }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getActual() {
        return actual;
    }

    public void setActual(Boolean actual) {
        this.actual = actual;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Lend lend = (Lend)o;

        if (car != null ? !car.equals(lend.car) : lend.car != null)
            return false;
        if (user != null ? !user.equals(lend.user) : lend.user != null)
            return false;
        if (startDate != null ? !startDate.equals(lend.startDate) : lend.startDate != null)
            return false;
        if (endDate != null ? !endDate.equals(lend.endDate) : lend.endDate != null)
            return false;
        return actual != null ? actual.equals(lend.actual) : lend.actual == null;
    }

    @Override
    public int hashCode() {
        int result = car != null ? car.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (actual != null ? actual.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String actualString = actual ? "igen" : "nem";
        return  " autó:'" + car + '\'' +
                ", ügyfél:'" + user + '\'' +
                ", kölcsönzés kezdete: " + startDate +
                ", kölcsönzés vége:" + endDate +
                ", aktuális-e: " + actualString;
    }
}
