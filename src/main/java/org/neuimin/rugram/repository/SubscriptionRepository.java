package org.neuimin.rugram.repository;

import jakarta.transaction.Transactional;
import org.neuimin.rugram.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


    @Transactional
    Subscription findBySubscriberIdAndSubscriptionId(Long subscriberId, Long subscriptionId);

    @Transactional
    void deleteBySubscriberIdAndSubscriptionId(Long subscriberId, Long subscriptionId);

}
