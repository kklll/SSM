package com.validator;

import com.controller.TestForSale;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TransactionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        System.out.println(TestForSale.class.equals(clazz));
        return TestForSale.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TestForSale transaction = (TestForSale) target;
        double dis = transaction.getAmount() - (transaction.getPrice() * transaction.getQuantity());
        if (Math.abs(dis) > 0.01)
            errors.rejectValue("amount", null, "交易金额不匹配");
    }
}
