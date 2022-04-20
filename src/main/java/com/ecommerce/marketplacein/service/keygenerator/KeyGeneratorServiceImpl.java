package com.ecommerce.marketplacein.service.keygenerator;

import com.ecommerce.marketplacein.repository.KeyGeneratorRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyGeneratorServiceImpl implements KeyGeneratorService {

    @Autowired
    private KeyGeneratorRepository repository;

    @Override
    public synchronized String getNextKeySequence(String code, Integer codeSize) {
        Long nextSequence = repository.getCurrentSequence(code) + 1;
        return StringUtils.leftPad(nextSequence.toString(), codeSize, "0");
    }
}
