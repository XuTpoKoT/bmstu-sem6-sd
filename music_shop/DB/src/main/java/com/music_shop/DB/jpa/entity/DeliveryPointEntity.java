package com.music_shop.DB.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Entity
@Table(name="deliverypoint", schema="public")
public class DeliveryPointEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String address;

    public DeliveryPointEntity() {

    }
}
