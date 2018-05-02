package com.unify.rrls.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.unify.rrls.domain.DecimalConfiguration;
import com.unify.rrls.domain.OpportunityLearning;
import com.unify.rrls.domain.OpportunitySummaryData;
import com.unify.rrls.domain.StrategyMaster;
import com.unify.rrls.domain.User;
import com.unify.rrls.repository.DecimalConfigurationRepository;
import com.unify.rrls.repository.UserRepository;
import com.unify.rrls.security.SecurityUtils;
import com.unify.rrls.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class DecimalConfigurationResource {
	
	private final DecimalConfigurationRepository decimalConfigurationRepository;
	private final UserRepository userRepository;
	
	private static final String ENTITY_NAME = "decimalConfiguration";
	
	private final Logger log = LoggerFactory.getLogger(DecimalConfigurationResource.class);
	
	public DecimalConfigurationResource(DecimalConfigurationRepository decimalConfigurationRepository,
			UserRepository userRepository) {
		this.decimalConfigurationRepository = decimalConfigurationRepository;
		this.userRepository=userRepository;
	}
	
	  @PostMapping("/decimal-config")
	    @Timed
	    public ResponseEntity<DecimalConfiguration> createDecimalConfiguration(@RequestBody DecimalConfiguration decimalConfiguration)
	        throws URISyntaxException, IOException, MissingServletRequestParameterException {
	        log.debug("REST request to save DecimalConfiguration : {}", decimalConfiguration);


	        if (decimalConfiguration.getId() != null) {
	            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
	                "A new OpportunityLearning cannot already have an ID")).body(null);
	        }
	        DecimalConfiguration result = decimalConfigurationRepository.save(decimalConfiguration);
	     
	        return ResponseEntity.created(new URI("/api/decimal-config/" + result.getId()))
	            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	    }
	  
	  @GetMapping("/decimal-config/{name}")
	    @Timed
	    public ResponseEntity<DecimalConfiguration> DecimalConfiguration(@PathVariable String name) {
	        log.debug("REST request to get StrategyMaster : {}", name);
	      
	        User user=userRepository.findByLogin(name);
	        DecimalConfiguration result = decimalConfigurationRepository.findByUser(user);
	    
	        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
	    }


}
