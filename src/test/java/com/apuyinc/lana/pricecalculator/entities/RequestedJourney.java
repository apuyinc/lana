package com.apuyinc.lana.pricecalculator.entities;

public class RequestedJourney {

    private String JourneyTitle;
    private String JourneyPrice;

    public RequestedJourney(String journeyTitle, String journeyPrice) {
        JourneyTitle = journeyTitle;
        JourneyPrice = journeyPrice;
    }

    public String getJourneyTitle() {
        return JourneyTitle;
    }

    public String getJourneyPrice() {
        return JourneyPrice;
    }
}
