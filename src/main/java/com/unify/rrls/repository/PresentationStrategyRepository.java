package com.unify.rrls.repository;

import com.unify.rrls.domain.PresentationFileUpload;
import com.unify.rrls.domain.PresentationStrategyMapping;
import com.unify.rrls.domain.StrategyMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PresentationStrategyRepository extends JpaRepository<PresentationStrategyMapping, Long>  {

//    List<PresentationFileUpload> findByPresentationId(PresentationFileUpload presentationFileUpload);

    Page<PresentationStrategyMapping> findByStrategyMaster(StrategyMaster strategyMaster,Pageable pageable);

    @Query(value="select count(*) FROM presentation_strategy_mapping where strategy_id =?1", nativeQuery = true)
    Integer findCountOfPresentation(Long id);

    @Modifying
    @Transactional

    @Query(value="delete FROM presentation_strategy_mapping where presentation_id =?1 and strategy_id =?2", nativeQuery = true)
    Integer deleteByPresentationIdAndStrategyId(Long presentationId,Long strategyId);

}
