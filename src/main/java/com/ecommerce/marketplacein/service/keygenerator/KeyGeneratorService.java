package com.ecommerce.marketplacein.service.keygenerator;

public interface KeyGeneratorService {

    String getNextKeySequence(String code, Integer codeSize);

}
