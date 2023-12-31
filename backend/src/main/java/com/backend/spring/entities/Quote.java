package com.backend.spring.entities;

import com.backend.spring.dto.ShopInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.NONE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quotes")
public class Quote {
    @Id
    @SequenceGenerator(name = "quote_sequence", sequenceName = "quote_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "quote_sequence")
    @Column(name = "quote_id")
    private Long id;

    @ToString.Exclude
    @JsonProperty(access = WRITE_ONLY)
    @ManyToOne(optional = false)
    @JoinColumn(name = "shop_id", referencedColumnName = "shop_id")
    private Shop shop;

    @Transient
    @JsonProperty(access = READ_ONLY)
    @Getter(NONE)
    private ShopInfo shopInfo;

    @Transient
    @JsonProperty(access = WRITE_ONLY)
    @Getter(NONE)
    private Long shopId = -1L;

    @ManyToOne(optional = false, cascade = MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private VehicleOwner vehicleOwner;
    @ToString.Exclude
    @JsonProperty(access = WRITE_ONLY)
    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "service_id", referencedColumnName = "service_id")
    private Service service;
    @Transient
    @Getter(value = NONE)
    private String serviceName;
    @Column(name = "expiry_time", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime expiryDate = LocalDateTime.now().plusMonths(6);
    @Getter(value = NONE)
    private QuoteStatus status = QuoteStatus.PENDING_REVIEW;
    private Double price = null;
    private String description = "";

    public Quote(Shop shop, VehicleOwner vehicleOwner, Service service, Double price, String description) {
        this.shop = shop;
        this.vehicleOwner = vehicleOwner;
        this.service = service;
        this.price = price;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quote quote)) return false;
        return Objects.equals(getShopInfo(), quote.getShopInfo()) &&
                Objects.equals(vehicleOwner, quote.vehicleOwner) &&
                Objects.equals(getServiceName(), quote.getServiceName()) &&
                Objects.equals(expiryDate, quote.expiryDate) &&
                status == quote.status &&
                Objects.equals(price, quote.price) &&
                Objects.equals(description, quote.description);
    }

    public QuoteStatus getStatus() {
        if (expiryDate.isBefore(LocalDateTime.now())) {
            this.status = QuoteStatus.EXPIRED;
        }
        return status;
    }

    public String getServiceName() {
        if (service != null)
            return service.getName();
        return serviceName;
    }

    public ShopInfo getShopInfo() {
        if (shop != null && shopInfo == null)
            this.shopInfo = new ShopInfo(shop);
        return shopInfo;
    }

    public Long getShopId() {
        if (shop != null)
            return shop.getId();
        return shopId;
    }
}
