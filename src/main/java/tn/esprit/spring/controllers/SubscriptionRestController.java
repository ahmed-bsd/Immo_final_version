package tn.esprit.spring.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.repository.SubscriptionRepository;
import tn.esprit.spring.service.SubscriptionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "subscription")
@RequestMapping("api/dashboard/subscription")
public class SubscriptionRestController {

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    SubscriptionRepository subscriptionRepository;
///////// ADD SUBSCRIPTION ///////////////////
    @ApiOperation(value = "add one subscription")
    @PostMapping("/add/subscription")
    @ResponseBody
    public Subscription addSubscrition(@RequestBody Subscription sub) {
        Subscription subscription= subscriptionService.addSubscription(sub);
        return subscription;
    }

//////// GET ALL SUBSCRIPTIONS ///////////////////
    @ApiOperation(value = "get all subscriptions list")
    @GetMapping("/retrieve/allSubscriptions")
    @ResponseBody
    public List<Subscription> getSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

//////// DELETE SUBSCRIPTION ///////////////////

    @ApiOperation(value = "delete one subscription")
    @DeleteMapping("/delete/subscription/{id}")
    public void deleteSubscription(@PathVariable("id") Long id) {
         subscriptionService.deletetSubscription(id);
    }
    //////// GET ALL SUBSCRIPTIONS ///////////////////
    @ApiOperation(value = "get all subscriptions list")
    @GetMapping("/retrieve/allSubscriptionsValid")
    @ResponseBody
    public List<Subscription> getSubValid() {
        return subscriptionService.getAllValidSubscriptions();
    }


//////// ASSIGN SUB TO OFFER  ///////////////////
    @ApiOperation(value = "assign subscription to offer")
    @PutMapping("/addSubToOffer/{SubId}/{offerId}")
    public void addSubscriptionToOffer(@PathVariable("SubId") long SubId, @PathVariable("offerId") long offerId) throws Exception {
        subscriptionService.assignSubscriptionToOffer(SubId,offerId);
    }

    @Scheduled(cron = "0 * * ? * *")
    public void archivSub() {
        subscriptionService.archiveSubscription();
    }
    @GetMapping("/stat")
    public String mostPop(@RequestParam("free") String free,@RequestParam("moun") String moun,@RequestParam("pack") String pack ) {
        return subscriptionService.getMostPopSub(free,moun,pack);
    }



}
