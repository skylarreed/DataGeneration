package com.sr.datagen.utilities;

import com.sr.datagen.models.Merchant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

@Slf4j(topic = "MerchantGeneratorTests")
public class MerchantGenTests {

    @Test
    public void testMerchantGeneratorForGenuineMerchant() {
        Merchant merchant = new Merchant();
        merchant.setMerchantName(MerchantNameGenerator.getMerchantName(4050));
        Assertions.assertEquals("Transportation Services", MerchantNameGenerator.getMerchantCategoryCode(4050));
        Assertions.assertNotNull(merchant.getMerchantName());
        log.info("Merchant generated successfully.");
        merchant.setMerchantName(MerchantNameGenerator.getMerchantName(0));
        Assertions.assertEquals("Unknown", MerchantNameGenerator.getMerchantCategoryCode(0));
        Assertions.assertNotNull(merchant.getMerchantName());
        log.info("Merchant generated successfully.");
    }
}
