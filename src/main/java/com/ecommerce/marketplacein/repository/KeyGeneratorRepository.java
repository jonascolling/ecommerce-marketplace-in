package com.ecommerce.marketplacein.repository;


import com.ecommerce.marketplacein.model.KeyGenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KeyGeneratorRepository extends JpaRepository<KeyGenerator, Long> {

    @Query("select idKeyGenerator from KeyGenerator where code = :code")
    Long getCurrentSequence(@Param("code") String code);

}
