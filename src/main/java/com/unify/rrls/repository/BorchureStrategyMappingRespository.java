package com.unify.rrls.repository;

import com.unify.rrls.domain.BrochureFileUpload;
import com.unify.rrls.domain.BrochureStrategyMapping;
import com.unify.rrls.domain.PresentationStrategyMapping;
import com.unify.rrls.domain.StrategyMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BorchureStrategyMappingRespository extends JpaRepository<BrochureStrategyMapping, Long> {

    Page<BrochureStrategyMapping> findByStrategyMaster(StrategyMaster strategyMaster, Pageable pageable);

    @Query(value="select count(*) FROM brochure_strategy_mapping where strategy_id =?1", nativeQuery = true)
    Integer findCountOfBrochure(Long id);


    @Modifying
    @Transactional
    @Query(value="delete FROM brochure_strategy_mapping where brochure_id =?1 and strategy_id =?2", nativeQuery = true)
    Integer deleteByBrochureIdAndStrategyId(Long brochureId,Long strategyId);

}
