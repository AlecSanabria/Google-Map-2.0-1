package UnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import OSMGraph.AbstractEdge;
import OSMGraph.Graph;
import OSMGraph.OSMGraphConstructor;
import OSMGraph.RoadEdge;
import OSMUtil.OSMAbstractDataModel;
import OSMUtil.OSMMathUtil;
import OSMUtil.OSMNode;
import OSMUtil.OSMXMLInterpreter;
import OSMUtil.Unit;

/**
 * author: Lining Pan
 */
public class OSMTesting {
	private static long id = 1;
	private static final double DELTA = 1e-2;

	@Test
	public void testDistanceCalculation() {
		OSMNode n1 = new OSMNode(id++, null, 32.9697, -96.80322);
		OSMNode n2 = new OSMNode(id++, null, 29.46786, -98.53506);
		assertEquals(OSMMathUtil.distance(n1, n2), 262.68, DELTA);
		assertEquals(262.68, OSMMathUtil.distance(n1, n2, Unit.MILE), DELTA);
		assertEquals(422.74, OSMMathUtil.distance(n1, n2, Unit.KILO), DELTA);
		assertEquals(228.11, OSMMathUtil.distance(n1, n2, Unit.NM), DELTA);
	}
	
//	@Test
//	public void testXMLIterpreterIndiana() {
//		runTestWithFileAndName("OSMResource/indiana-latest.osm", "Indiana");
//	}
	
	@Test
	public void testXMLIterpreter() {
		runTestWithFileAndName("OSMResource/test.osm", "small test");
	}
	
//	@Test
//	public void testXMLIterpreterTerreHaute() {
//		runTestWithFileAndName("OSMResource/Terre Haute.osm", "Terre Haute");
//	}
	
//	@Test
//	public void testEqualAndHashCode() {
//		OSMAbstractDataModel dm = OSMXMLInterpreter.loadFromFile("OSMResource/Terre Haute.osm");
//		assert dm != null;
//		Set<Long> id = dm.getAllNodeId();
//		for(Long i : id) {
//			OSMNode tmp = new OSMNode(i,null,0,0);
//			assertNotSame(dm.getNodeById(i), tmp);
//			//System.out.println(tmp.hashCode());
//			assertEquals(tmp, dm.getNodeById(i));
//		}
//		
//	}
	
	private static void runTestWithFileAndName(String file, String name) {
		System.out.println(String.format("Start running test %s", name));
		long startTime = System.currentTimeMillis();
		
		OSMAbstractDataModel dm = OSMXMLInterpreter.loadFromFile(file);

		long endTime = System.currentTimeMillis();

        assert dm != null;
//        System.out.println(dm.getAllNodeId().size());
//		System.out.println(dm.getAllWayId().size());
		long count = 0;//Node included in more than one way;
		long empty_count = 0;
		for(Long n: dm.getAllNodeId()) {
			Set<Long> s = dm.getAllWayContainsNode(n);
			if(s == null)
				empty_count ++;
			else if(s.size() > 1) {
				count++;
			}
		}
//		System.out.println(String.format("%d node included by more than one way.",count));
//		System.out.println(String.format("%d node not included by any way.",empty_count));
		System.out.println(String.format("XML: %s: %d milliseconds", name, endTime - startTime));
		System.out.println();
	}

	@Test
    public void testGraphConstructSmall(){
        OSMAbstractDataModel dm = OSMXMLInterpreter.loadFromFile("OSMResource/test.osm");
        assert dm != null;
        OSMGraphConstructor gc = new OSMGraphConstructor(dm);
        Graph g = gc.getGraph();
        long vids[] = {153357350, 153421657, 153497538, 153532013};
        long notV[] = {153380829, 153516590};
        for(long i : vids){
            assertTrue(g.isVertex(i));
        }
        for(long i : notV){
            assertFalse(g.isVertex(i));
        }
        for(AbstractEdge e: g.getEdgesContainedByWay(17571013)){
            assertEquals(50.0,((RoadEdge) e).getSpeed(),DELTA);
        }
        for(AbstractEdge e: g.getEdgesContainedByWay(17570942)){
            assertEquals(60.0,((RoadEdge) e).getSpeed(),DELTA);
        }
    }

//    @Test
//    public void testSearchOnSmall(){
//        OSMAbstractDataModel dm = OSMXMLInterpreter.loadFromFile("OSMResource/test.osm");
//        assert dm != null;
//        OSMGraphConstructor gc = new OSMGraphConstructor(dm);
//        Graph g = gc.getGraph();
//        long vids[] = {153357350, 153421657, 153497538, 153532013};
//        long notV[] = {153380829, 153516590};
//        AbstractVertex f = new RoadVertex(dm.getNodeById(Long.parseLong("153357350")));
//        AbstractVertex t = new RoadVertex(dm.getNodeById(Long.parseLong("153532013")));
//        AbstractSearchEngine engine = new DijkstraSearchEngine(g);
//        engine.getVertexRoute(f,t);
//    }
//    @Test
//    public void testSearchOnTerreHaute(){
//        OSMAbstractDataModel dm = OSMXMLInterpreter.loadFromFile("OSMResource/Terre Haute.osm");
//        assert dm != null;
//        OSMGraphConstructor gc = new OSMGraphConstructor(dm);
//        Graph g = gc.getGraph();
//        AbstractVertex f = new RoadVertex(dm.getNodeById(Long.parseLong("181903355")));
//        AbstractVertex t = new RoadVertex(dm.getNodeById(Long.parseLong("4638405162")));
//        AbstractSearchEngine engine = new DijkstraSearchEngine(g);
//        List<RoadEdge> route = engine.getVertexRoute(f,t);
//        System.out.println(route.get(0).getFromNode().getLocString());
//        for(RoadEdge e: route){
//            System.out.println(e.getToNode().getLocString());
//        }
//    }

//    @Test
//    public void testGraphConstructTH1(){
//        OSMAbstractDataModel dm = OSMXMLInterpreter.loadFromFile("OSMResource/Terre Haute.osm");
//        assert dm != null;
//        OSMGraphConstructor gc = new OSMGraphConstructor(dm);
//        Graph g = gc.getGraph();
//        AbstractVertex vt = g.getVertexById(181908029);
//        assert vt != null;
//        Iterator<AbstractEdge> it = g.getEdgeIterator(vt);
//        while(it.hasNext()){
//            System.out.println(it.next());
//        }
//        System.out.println();
//    }

//    @Test
//    public void testGraphConstructTH2(){
//        long startTime = System.currentTimeMillis();
//        OSMAbstractDataModel dm = OSMXMLInterpreter.loadFromFile("OSMResource/Terre Haute.osm");
//        assert dm != null;
//        long endTime = System.currentTimeMillis();
//        System.out.println(String.format("Load: %s: %d milliseconds", "Terre Haute", endTime - startTime));
//
//        startTime = System.currentTimeMillis();
//        OSMGraphConstructor gc = new OSMGraphConstructor(dm);
//        Graph g = gc.getGraph();
//        endTime = System.currentTimeMillis();
//        System.out.println(String.format("To Graph: %s: %d milliseconds", "Terre Haute", endTime - startTime));
//        System.out.println();
//    }

//    @Test
//    public void testGraphConstructIN(){
//        long startTime = System.currentTimeMillis();
//        OSMAbstractDataModel dm = OSMXMLInterpreter.loadFromFile("OSMResource/indiana-latest.osm");
//        assert dm != null;
//        long endTime = System.currentTimeMillis();
//        System.out.println(String.format("Load: %s: %d milliseconds", "indiana", endTime - startTime));
//
//        startTime = System.currentTimeMillis();
//        OSMGraphConstructor gc = new OSMGraphConstructor(dm);
//        Graph g = gc.getGraph();
//        endTime = System.currentTimeMillis();
//        System.out.println(String.format("To Graph: %s: %d milliseconds", "indiana", endTime - startTime));
//        System.out.println();
//    }

//	@Test
//	public void testXMLSeqIterpreter() {
//		long startTime = System.currentTimeMillis();
//		
//		OSMXMLInterpreter.loadFromFileSequential("OSMResource/test.osm");
//		
//		long endTime = System.currentTimeMillis();
//		System.out.println("XML: Seq: small:       " + (endTime - startTime) + " milliseconds");
//	}
//	
//	@Test
//	public void testXMLSeqIterpreterTerreHaute() {
//		long startTime = System.currentTimeMillis();
//		
//		OSMXMLInterpreter.loadFromFileSequential("OSMResource/Terre Haute.osm");
//		
//		long endTime = System.currentTimeMillis();
//		System.out.println("XML: Seq: Terre Haute: " + (endTime - startTime) + " milliseconds");
//	}
// It is not possible to load the map of Indiana using the XML Interpreter
// must use database mongoDB is probably a good choice
}