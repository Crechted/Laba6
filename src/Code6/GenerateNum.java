package Code6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * this class generate ID
 */
public class GenerateNum implements Serializable {
    private static final long serialVersionUID = 19L;
    private static ArrayList<Long> listNum = new ArrayList<>();

    public static long getNum(){
        while(true) {
            long num = Math.abs(new Random().nextLong());
            boolean b = true;
            for (long l : listNum) {
                if (num != l)
                    b = true;
                else
                    b = false;
            }
            if(b) {
                listNum.add(num);
                return num;
            }
        }
    }
}
