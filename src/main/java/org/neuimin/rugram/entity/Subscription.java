package org.neuimin.rugram.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subscriptions")
@SequenceGenerator(name = "userIdSeq", sequenceName = "user_id_seq", allocationSize = 1)
@Data
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdSeq")
    @Column(name = "id", nullable = false, columnDefinition = "serial")
    private Long id;

    @Column(name = "subscriber_id")
    private Long subscriberId;

    @Column(name = "subscription_id")
    private Long subscriptionId;

    public Subscription() {

    }

    public Subscription(Long id, Long subscriberId, Long subscriptionId) {
        this.id = id;
        this.subscriberId = subscriberId;
        this.subscriptionId = subscriptionId;
    }

    public Subscription(Long subscriberId, Long subscriptionId) {
        this.subscriberId = subscriberId;
        this.subscriptionId = subscriptionId;
    }
}
