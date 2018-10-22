package OSMUtil;

import java.util.List;
import java.util.Map;

/**
 * author: Lining Pan
 */
public class OSMWay extends OSMAbstractType {

    private List<Long> nodeIdList;

    OSMWay(long _id, Map<String, String> map, List<Long> nList) {
        super(_id, map);
        nodeIdList = nList;
    }

    public final List<Long> getNodeIdList() {
        return nodeIdList;
    }

    public boolean isRoad() {
        return this.hasTag("highway");
    }

    public double getSpeedLimit() {
        int speed = 30;
        if (this.hasTag("maxspeed")) {
            String str = this.getTagValue("maxspeed");
            String[] sl = str.split("\\s+");
            if(sl.length <= 1){
                try{
                    speed = Integer.parseInt(sl[0]);
                } catch (NumberFormatException e){
                    System.out.println("Not a number");
                    System.out.println(str);
                }
            } else if(sl[1].equals("mph")){
                try{
                    speed = Integer.parseInt(sl[0]);
                } catch (NumberFormatException e){
                    System.out.println(str);
                }
            } else {
                System.out.println("Not mph");
                System.out.println(str);
            }
        }
        return (double) speed;
    }

    public boolean isOneway() {
        if (this.hasTag("oneway")) {
            return this.getTagValue("oneway").equals("yes");
        }
        return false;
    }

    public String getName(){
        if(this.hasTag("name")){
            return this.getTagValue("name");
        }
        return "Unknown";
    }
    @Override
    public String toString() {
        return super.toStringHelper(String.format("node list: %s", this.nodeIdList.toString()));
    }
}
