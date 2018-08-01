package com.unify.rrls.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.RoleMaster.class.getName(), jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.StrategyMaster.class.getName(), jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.OpportunityMaster.class.getName(), jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.AdditionalFileUpload.class.getName(), jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.FileUpload.class.getName(), jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.FileUploadComments.class.getName(), jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.OpportunityName.class.getName(), jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.StrategyMapping.class.getName(), jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.OpportunityMasterContact.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.OpportunitySummaryData.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.FinancialSummaryData.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.NonFinancialSummaryData.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.OpportunitySector.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.OpportunityAutomation.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.HistoryLogs.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.OpportunityQuestion.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.AnswerComment.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.DeleteNotification.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.ReplyComment.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.CommentOpportunity.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.OpportunityLearning.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.DecimalConfiguration.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.UserDelegationAudit.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.FixedLearning.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.FixedLearningMapping.class.getName(),jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.ExternalResearchAnalyst.class.getName(), jcacheConfiguration);
            cm.createCache(com.unify.rrls.domain.ReviewExternal.class.getName(), jcacheConfiguration);           
            cm.createCache(com.unify.rrls.domain.ExternalRASector.class.getName(), jcacheConfiguration); 
            cm.createCache(com.unify.rrls.domain.ExternalRAContacts.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
