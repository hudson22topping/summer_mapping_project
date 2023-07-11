package main.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteRequestTest {

    @Test
    void setPriority() {
        RouteRequest testRR = new RouteRequest();
        testRR.setPriority("CHEAP");
        assertTrue(testRR.priority == "CHEAP");
        testRR.setPriority("WILD PARTY");
        assertTrue(testRR.priority == " ");
    }

    @Test
    void generateTransitRequestTest1() {
    }

    @Test
    void generateTransitRequestTest2() {
    }

    @Test
    void userSetup() {
        UserAccount testUser = new UserAccount("SallyFields");
        testUser.initializeTransitUser();  // Transit Mode and CHEAP priority

        // Get settings from user account
        RouteRequest testRR = new RouteRequest();
        testRR.setModePrefFromAccount(testUser);
        testRR.setPriority(testUser.getPriority());
        testRR.setOrigin("Dunwoody Marta Station");
        testRR.setDestination("Piedmont Atlanta Hospital");
        GeoModel testModel = new GeoModel();
        testModel.createGeoModel(testRR.getOrigin(), testRR.getDestination(), testRR.getModePrefAsList());
    }
}