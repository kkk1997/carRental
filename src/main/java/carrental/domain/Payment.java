package carrental.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    @Column
    private BigDecimal price;
    @Column
    private Title title;
    @Column
    private String narrative;

    public Payment() {
    }

    public Payment(BigDecimal price, Title title, String narrative) {
        this.price = price;
        this.title = title;
        this.narrative = narrative;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;

        Payment payment = (Payment) o;

        if (getPrice() != null ? !getPrice().equals(payment.getPrice()) : payment.getPrice() != null) return false;
        if (getTitle() != payment.getTitle()) return false;
        return getNarrative() != null ? getNarrative().equals(payment.getNarrative()) : payment.getNarrative() == null;
    }

    @Override
    public int hashCode() {
        int result = getPrice() != null ? getPrice().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getNarrative() != null ? getNarrative().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        price = price.setScale(2, RoundingMode.CEILING);
        return "Payment{" +
                "price=" + price +
                ", title=" + title +
                ", narrative='" + narrative + '\'' +
                '}';
    }
}
