
import org.apache.commons.lang.StringUtils;

import java.io.*;

/**
 * Created by lenovo on 2014-11-06.
 */
public class FormCodeMsgReader {

    public static void main(String[] args) {
        try {
            String fileName = "D:\\msg.txt";
            FileInputStream fis = new FileInputStream(new File(fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(inputStreamReader);

            File file = new File("SbsFormCode.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            String line = null;
            int i = 0;
            while ((line = br.readLine()) != null && line.contains("=")) {
                bw.newLine();
//                line.
                String[] lineArr = line.split("=");
                String str = null;
                if (lineArr.length == 1 || StringUtils.isEmpty(lineArr[1])) {
                    str = "    " + lineArr[0] + "(\"" + lineArr[0] + "\", \"" + lineArr[0] + "\"),";
                } else {
                    str = "    " + lineArr[0] + "(\"" + lineArr[0] + "\", \"" + lineArr[1] + "\"),";
                }
                bw.write(str);
                System.out.println(str);
                i++;
                lineArr = null;
            }
            bw.flush();
            bw.close();
            br.close();
            System.out.println("Ð´ÈëÐÐÊý:" + i);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
