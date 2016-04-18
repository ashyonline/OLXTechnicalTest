package codingbad.com.olxtechnicaltest.database;

import java.util.ArrayList;
import java.util.List;

import codingbad.com.olxtechnicaltest.model.Category;

/**
 * Created by Ayelen Chavez on 4/18/16.
 * Hardcoded images to avoid waisting my quota of 100 custom search per day
 */
public class DummyData {

    private static final String pic1
            = "http://files.vividscreen.info/soft/4fa7e852d1bdf100978ec5ff4dc66ab3/Clever-Kitten-1920x1200.jpg";

    private static final String pic2
            = "http://www.petmd.com/sites/default/cache/imagecache/node-gallery-display/shutterstock_19820554-slide1.jpg";

    private static final String pic3
            = "http://www.hdwallpapers.in/thumbs/cute_baby_boy_green_eyes-t1.jpg";

    private static final String pic4
            = "http://i.dailymail.co.uk/i/pix/2014/09/18/1411051378250_wps_4_A_baby_seal_cautiously_di.jpg";

    private static final String pic5
            = "http://static.tumblr.com/5d57d5e2c4815897359c56bd4546b985/n9jaxcm/G17mrconq/tumblr_static_pigletcute.jpg";

    private static final String pic6 = "http://www.rabbit.org/adoption/bunny.jpg";

    private static final List<Category> categoriesDistribution = new ArrayList<>();

    public static String getUrlFor(String categoryName) {
        if (categoryName.equals("KITTEN")) {
            return pic1;
        }

        if (categoryName.equals("PUPPY")) {
            return pic2;
        }

        if (categoryName.equals("BABY")) {
            return pic3;
        }

        if (categoryName.equals("SEAL")) {
            return pic4;
        }

        if (categoryName.equals("PIG")) {
            return pic5;
        }

        if (categoryName.equals("BUNNY")) {
            return pic6;
        }

        return pic1;
    }

    // ignoring category name for now and retrieving what I have, 5 times (to have 30 items)
    public static List<String> getUrlsFor(String categoryName) {
        List<String> dummyRes = new ArrayList<>();
        dummyRes.add(pic1);
        dummyRes.add(pic2);
        dummyRes.add(pic3);
        dummyRes.add(pic4);
        dummyRes.add(pic5);
        dummyRes.add(pic6);

        dummyRes.add(pic1);
        dummyRes.add(pic2);
        dummyRes.add(pic3);
        dummyRes.add(pic4);
        dummyRes.add(pic5);
        dummyRes.add(pic6);

        dummyRes.add(pic1);
        dummyRes.add(pic2);
        dummyRes.add(pic3);
        dummyRes.add(pic4);
        dummyRes.add(pic5);
        dummyRes.add(pic6);

        dummyRes.add(pic1);
        dummyRes.add(pic2);
        dummyRes.add(pic3);
        dummyRes.add(pic4);
        dummyRes.add(pic5);
        dummyRes.add(pic6);

        dummyRes.add(pic1);
        dummyRes.add(pic2);
        dummyRes.add(pic3);
        dummyRes.add(pic4);
        dummyRes.add(pic5);
        dummyRes.add(pic6);

        return dummyRes;
    }

    public List<Category> getData() {
        return categoriesDistribution;
    }
}
