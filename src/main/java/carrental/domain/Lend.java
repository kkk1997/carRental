package carrental.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
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


    public Lend(Car car, User user, LocalDate startDate, LocalDate endDate) {
        this.car = car;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        actual = true;
    }

    public Lend(){

    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getActual() {
        return actual;
    }

    public void setActual(Boolean actual) {
        this.actual = actual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lend lend = (Lend) o;

        if (ID != null ? !ID.equals(lend.ID) : lend.ID != null) return false;
        if (car != null ? !car.equals(lend.car) : lend.car != null) return false;
        if (user != null ? !user.equals(lend.user) : lend.user != null) return false;
        if (startDate != null ? !startDate.equals(lend.startDate) : lend.startDate != null) return false;
        if (endDate != null ? !endDate.equals(lend.endDate) : lend.endDate != null) return false;
        return actual != null ? actual.equals(lend.actual) : lend.actual == null;
    }

    @Override
    public int hashCode() {
        int result = ID != null ? ID.hashCode() : 0;
        result = 31 * result + (car != null ? car.hashCode() : 0);
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
