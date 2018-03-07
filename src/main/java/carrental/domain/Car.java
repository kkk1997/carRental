package carrental.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long ID;
    @Column
    private String licensePlateNumber;
    @Column
    private String type;
    @Column
    private BigDecimal price;
    @Column
    private Integer age;
    @OneToOne
    private Lend lend;

    public Car(String licensePlateNumber, String type, BigDecimal price, int age) {
        this.licensePlateNumber = licensePlateNumber;
        this.type = type;
        this.price = price;
        this.age = age;
    }

    public Car() {
        age = 0;
        price = BigDecimal.ZERO;
    }


    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Lend getLend() {
        return lend;
    }

    public void setLend(Lend lend) {
        this.lend = lend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (ID != null ? !ID.equals(car.ID) : car.ID != null) return false;
        if (licensePlateNumber != null ? !licensePlateNumber.equals(car.licensePlateNumber) : car.licensePlateNumber != null)
            return false;
        if (type != null ? !type.equals(car.type) : car.type != null) return false;
        if (price != null ? !price.equals(car.price) : car.price != null) return false;
        if (age != null ? !age.equals(car.age) : car.age != null) return false;
        return lend != null ? lend.equals(car.lend) : car.lend == null;
    }

    @Override
    public int hashCode() {
        int result = ID != null ? ID.hashCode() : 0;
        result = 31 * result + (licensePlateNumber != null ? licensePlateNumber.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (lend != null ? lend.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return  licensePlateNumber;
    }

}
