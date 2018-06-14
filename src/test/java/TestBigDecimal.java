import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TestBigDecimal {

    @Test
    public void testDividBigDecimalZero() {
//        BigDecimal a = new BigDecimal(12);
//        BigDecimal b = new BigDecimal(1.00);
//        BigDecimal c = a.divide(b, 4, BigDecimal.ROUND_HALF_UP);

        BigDecimal decimal = new BigDecimal(0);
        System.out.println(String.valueOf(decimal.setScale(0, BigDecimal.ROUND_HALF_UP)));
    }

    @Test
    public void testNullPointException() {
        Map<String, Object> map = new HashMap<>();
        map.put("aaa", "0");
        if (StringUtils.isBlank((String) map.get("aaa"))) {
            System.out.println("can pass");
        }
        BigDecimal bigDecimal = new BigDecimal((String) map.get("aaa"));
        System.out.println("bigdecimal:" + bigDecimal);
    }
}
