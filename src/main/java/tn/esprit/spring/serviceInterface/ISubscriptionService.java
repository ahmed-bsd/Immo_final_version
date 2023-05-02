package tn.esprit.spring.serviceInterface;

import tn.esprit.spring.entities.Subscription;

import java.util.List;

public interface ISubscriptionService {

    public Subscription addSubscription(Subscription etd);
    public List<Subscription> getAllSubscriptions();

    public void deletetSubscription(Long id);
    public void archiveSubscription();
    public List<Subscription> getAllValidSubscriptions();


}
