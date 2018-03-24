package carrental.service.interf;

import java.math.BigDecimal;

public interface IPaymentService {

    boolean charge(String bankCardNumber, BigDecimal amount);
}
