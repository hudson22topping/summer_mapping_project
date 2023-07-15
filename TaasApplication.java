package main.java;

import java.util.LinkedList;

public class TaasApplication {
    static GeoModel instanceGM;
    static RouteAnalyzer instanceRA;
    static RouteRequest instanceRR;
    static LinkedList<WeightedGraph> instanceRouteOffering;
    static WeightedGraph chosenRoute;
    static UserInterface instanceUI;

    // Todo - interface with UI
//    UserInterface instanceUI = new UserInterface();


    public static void main(String[] args) {
        TaasApplication userInstance = new TaasApplication();
        manageSession(userInstance);
    }
/*    private static RouteRequest userSetup() {  // Todo - remove any usages, then delete
        UserAccount testUser = new UserAccount("SallyFields");
        testUser.initializeTransitUser();  // Transit Mode and CHEAP priority

        // Get settings from user account
        RouteRequest testRR = new RouteRequest();
        testRR.setModePrefFromAccount(testUser);
        testRR.setPriority(testUser.getPriority());
        testRR.setOrigin("Dunwoody Marta Station");
        testRR.setDestination("Piedmont Atlanta Hospital");
        return testRR;
    }  */
    private static RouteRequest getUserRequest() {
        UserInterface instanceUI = new UserInterface();
        instanceRR = instanceUI.getRequest();
        return instanceRR;
    }
    private static void manageSession(TaasApplication session){
        session.instanceUI = new UserInterface();
        session.instanceRR = session.instanceUI.getRequest();
        session.instanceGM = new GeoModel(session.instanceRR);
        session.instanceRA = new RouteAnalyzer(session.instanceGM.overallGraph, session.instanceRR); // comment
        session.instanceGM.overallGraph.printGraph();
        session.instanceRouteOffering = session.instanceRA.findRoutes(); // comment
        System.out.println("Done");
//          chosenRoute = instanceUI.getUsersChoiceOfRoute(session.instanceRouteOffering);
//          instanceUI.displayRoute(chosenRoute);
    }

}
