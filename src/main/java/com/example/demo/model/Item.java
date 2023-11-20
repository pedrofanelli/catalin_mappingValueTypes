package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.EventType;

import com.example.demo.converter.MonetaryAmountConverter;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Item {

	/* 
    The <code>Item</code> entity defaults to field access, the <code>@Id</code> is on a field.
	 */
	 @Id
	 @GeneratedValue(generator = "ID_GENERATOR")
	 private Long id;
	
	 /*
	    The <code>@Access(AccessType.PROPERTY)</code> setting on the <code>name</code> field switches this
	    particular property to runtime access through getter/setter methods by the JPA provider.
	 */
	 @Access(AccessType.PROPERTY)
	 @Column(name = "ITEM_NAME") // Mappings are still expected here!
	 private String name;
	 
	 @NotNull
	    @Convert(converter = MonetaryAmountConverter.class)
	    @Column(name = "PRICE", length = 63)
	    private MonetaryAmount buyNowPrice;
	
	 @OneToMany(mappedBy = "item",
	         cascade = CascadeType.PERSIST,
	         orphanRemoval = true) // Includes CascadeType.REMOVE
	 private Set<Bid> bids = new HashSet<>();
	
	 @NotNull
	 @Basic(fetch = FetchType.LAZY) // Defaults to EAGER
	 private String description;
	
	 @NotNull
	 @Enumerated(EnumType.STRING) // Defaults to ORDINAL
	 private AuctionType auctionType = AuctionType.HIGHEST_BID;
	
	 @Formula(
	         "CONCAT(SUBSTR(DESCRIPTION, 1, 12), '...')"
	 )
	 private String shortDescription;
	
	 @Formula(
	         "(SELECT AVG(B.AMOUNT) FROM BID B WHERE B.ITEM_ID = ID)"
	 )
	 private BigDecimal averageBidAmount;
	
	 @Column(name = "IMPERIALWEIGHT")
	 @ColumnTransformer(
	         read = "IMPERIALWEIGHT / 2.20462",
	         write = "? * 2.20462"
	 )
	 private double metricWeight;
	
	 @CreationTimestamp
	 private LocalDate createdOn;
	
	 @UpdateTimestamp
	 private LocalDateTime lastModified;
	
	 @Column(insertable = false)
	 @ColumnDefault("1.00")
	 @Generated(
	         //org.hibernate.annotations.GenerationTime.INSERT   // deprecated
			 event = EventType.INSERT
	 )
	 private BigDecimal initialPrice;
	
	 /* 
	     Hibernate will call <code>getName()</code> and <code>setName()</code> when loading and storing items.
	 */
	 public String getName() {
	     return name;
	 }
	
	 public void setName(String name) {
	     this.name =
	             !name.startsWith("AUCTION: ") ? "AUCTION: " + name : name;
	 }
	
	 public MonetaryAmount getBuyNowPrice() {
         return buyNowPrice;
     }

     public void setBuyNowPrice(MonetaryAmount buyNowPrice) {
         this.buyNowPrice = buyNowPrice;
     }
	 
	 public Set<Bid> getBids() {
	     return Collections.unmodifiableSet(bids);
	 }
	
	 public String getDescription() {
	     return description;
	 }
	
	 public void setDescription(String description) {
	     this.description = description;
	 }
	
	 public AuctionType getAuctionType() {
	     return auctionType;
	 }
	
	 public void setAuctionType(AuctionType auctionType) {
	     this.auctionType = auctionType;
	 }
	
	 public String getShortDescription() {
	     return shortDescription;
	 }
	
	 public BigDecimal getAverageBidAmount() {
	     return averageBidAmount;
	 }
	
	 public double getMetricWeight() {
	     return metricWeight;
	 }
	
	 public void setMetricWeight(double metricWeight) {
	     this.metricWeight = metricWeight;
	 }
	
	 public LocalDate getCreatedOn() {
	     return createdOn;
	 }
	
	 public LocalDateTime getLastModified() {
	     return lastModified;
	 }
	
	 public BigDecimal getInitialPrice() {
	     return initialPrice;
	 }
}
