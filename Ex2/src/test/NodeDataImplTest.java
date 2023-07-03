package test;

import com.api.NodeData;
import com.impl.GeoLocationImpl;
import com.impl.NodeDataImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class NodeDataImplTest {

    @Test
    public void getKey() throws FileNotFoundException {
        GeoLocationImpl location = new GeoLocationImpl("3,2,1");
        NodeDataImpl node = new NodeDataImpl(2,"3,2,1");
        assertEquals(node.getKey(),2);

    }

    @Test
    public void getLocation() throws FileNotFoundException {
        GeoLocationImpl location = new GeoLocationImpl("3,2,1");
        NodeDataImpl node = new NodeDataImpl(2,"3,2,1");

      Assertions.assertEquals(node.getLocation().x(),3);
        Assertions.assertEquals(node.getLocation().y(),2);
        Assertions.assertEquals(node.getLocation().z(),1);



    }

    @Test
    public void setLocation() throws FileNotFoundException {
        NodeDataImpl node = new NodeDataImpl(2,"4,5,1");

        GeoLocationImpl location = new GeoLocationImpl("3,2,1");


         node.setLocation(location);


    }

    @Test
    public void getWeight() throws FileNotFoundException {
        GeoLocationImpl location = new GeoLocationImpl("3,2,1");


        NodeDataImpl node = new NodeDataImpl(1,1,"1,1,1",3,location);
        Assertions.assertEquals(3,node.getWeight());

    }

    @Test
    public void setWeight() throws FileNotFoundException {
        GeoLocationImpl location = new GeoLocationImpl("3,2,1");

        NodeDataImpl node = new NodeDataImpl(1,1,"1,1,1",3,location);
        node.setWeight(22);
        Assertions.assertEquals(node.getWeight(),22);



    }

    @Test
    public void getInfo() throws FileNotFoundException {
        GeoLocationImpl location = new GeoLocationImpl("3,2,1");
        NodeDataImpl node = new NodeDataImpl(1,1,"1,1,1",3,location);
        Assertions.assertEquals("1,1,1",node.getInfo());


    }

    @Test
    public void setInfo() throws FileNotFoundException {
        GeoLocationImpl location = new GeoLocationImpl("3,2,1");
        NodeDataImpl node = new NodeDataImpl(1,1,"1,1,1",3,location);
        node.setInfo("7,7,7");
        Assertions.assertEquals("7,7,7",node.getInfo());



    }



    @Test
    public void getTag() throws FileNotFoundException {
        GeoLocationImpl location = new GeoLocationImpl("3,2,1");
        NodeDataImpl node = new NodeDataImpl(1,255,"1,1,1",3,location);
        Assertions.assertEquals(255,node.getTag());
    }

    @Test
    public void setTag() throws FileNotFoundException {
        GeoLocationImpl location = new GeoLocationImpl("3,2,1");
        NodeDataImpl node = new NodeDataImpl(1,255,"1,1,1",3,location);
        node.setTag(0);
        Assertions.assertEquals(0,node.getTag());

    }
}