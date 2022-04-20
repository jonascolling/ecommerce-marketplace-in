package com.ecommerce.marketplacein.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class KeyGenerator {

    @Id
    private Long idKeyGenerator;

    public Long getIdKeyGenerator() {
        return idKeyGenerator;
    }

    public void setIdKeyGenerator(Long idKeyGenerator) {
        this.idKeyGenerator = idKeyGenerator;
    }

}
