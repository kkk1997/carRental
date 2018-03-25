package carrental.service.impl;

import carrental.dao.interf.PaymentRepository;
import carrental.service.interf.IPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DummyPaymentService  implements IPaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    private static final Logger LOG = LoggerFactory.getLogger(DummyPaymentService.class);
    @Override
    public boolean charge(String bankCardNumber, BigDecimal amount) {
        amount = amount.setScale(2, RoundingMode.CEILING);
        LOG.info("Bankkártyaszám: {} összeg: {}",bankCardNumber,amount);
        return true;
    }
}
