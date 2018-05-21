package com.unify.rrls.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "decimal_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DecimalConfiguration implements Serializable {
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
	@JoinColumn(name = "user_id")
    private User user;
    
    @Column(name="decimal_value")
    private Integer decimalValue;
    
    @Column(name="rupees_iden")
    private String rupee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getDecimalValue() {
		return decimalValue;
	}

	public void setDecimalValue(Integer decimalValue) {
		this.decimalValue = decimalValue;
	}
	public String getRupee() {
		return rupee;
	}
	public void setRupee(String rupee) {
		this.rupee = rupee;
	}
	   @Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			DecimalConfiguration decimalConfiguration = (DecimalConfiguration) o;
			if (decimalConfiguration.getId() == null || getId() == null) {
				return false;
			}
			return Objects.equals(getId(), decimalConfiguration.getId());
		}

    

}
