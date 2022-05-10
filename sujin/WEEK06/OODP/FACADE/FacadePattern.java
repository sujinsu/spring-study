package oodp.FACADE;

import java.util.Map;

public class FacadePattern {

    public static void main(String[] args) {
        double[] myGeoLoc = new GeoLocation().getGeoLoc();

        InternetConnection conn = new InternetConnection();
        conn.connect();
        String data = conn.getData("https://주소_APi_URL", myGeoLoc);
        conn.disconnect();

        Map<String, Object> address = new Json().parse(data);

        System.out.println(address.get("address"));

        new MyLocFacade().printMyAddress();
    }
}
