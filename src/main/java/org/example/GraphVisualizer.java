import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.view.Viewer;

public class GraphVisualizer {
    public void visualizeGraph(WeightedGraph graph) {
        Graph g = new SingleGraph("Weighted Graph");

        // Add nodes to the graph
        for (WeightedGraph.Vertex vertex : graph.getVertices()) {
            Node node = g.addNode(vertex.getName());
            node.setAttribute("ui.label", vertex.getName());
        }

        // Add edges to the graph
        for (WeightedGraph.Vertex vertex : graph.getVertices()) {
            for (WeightedGraph.Edge edge : vertex.getEdges()) {
                Node sourceNode = g.getNode(edge.getSource().getName());
                Node targetNode = g.getNode(edge.getTarget().getName());

                org.graphstream.graph.Edge graphEdge = g.addEdge(
                        sourceNode.getId() + "_" + targetNode.getId(),
                        sourceNode,
                        targetNode,
                        true
                );

                graphEdge.setAttribute("ui.label", edge.getName());
            }
        }

        // Set up layout and viewer
        Layout layout = new SpringBox(false);
        Viewer viewer = g.display();
        viewer.enableAutoLayout(layout);
    }

    public static void main(String[] args) {
        TripRace tripRace = new TripRace();
        WeightedGraph graph = tripRace.generateGraph();

        GraphVisualizer visualizer = new GraphVisualizer();
        visualizer.visualizeGraph(graph);
    }
}
