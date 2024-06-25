package com.unify.rrls.repository;

import com.unify.rrls.domain.HyfBondData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface HyfBondRepository extends JpaRepository<HyfBondData,Long> {
    HyfBondData findById(Long id);
    @Transactional
    void deleteAll();
    @Query(value="select * from hyf_term_sheet where file_name = :fileName",nativeQuery = true)
    HyfBondData findByFileNames(@Param("fileName") String fileName);

    HyfBondData findByIsin(String isin);

    Optional<HyfBondData> findByCompanyNameAndTermSheetFileName(String companyName, String termSheetFileName);

//    HyfBondData findByFileName(String filename);
}
