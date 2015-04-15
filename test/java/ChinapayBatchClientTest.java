import org.fbi.endpoint.chinapaysh.ChinapayShTxnHandler;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by lenovo on 2014-06-03.
 */
public class ChinapayBatchClientTest {

    static final String BATCH_MER_ID = ChinapayShTxnHandler.BATCH_MER_ID;
    static final String BATCH_FILE_PATH = ChinapayShTxnHandler.BATCH_FILE_PATH;

    public static void main(String[] args) {

//        qry("808080211388500_20140702_151155_Q.txt");
      /*  try {
            uploadBatchFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        downloadNoticeFile();
    }

    // 批量文件
    public static void uploadBatchFile() throws IOException {

        Date dt = new Date();
        String MerSeqId = new SimpleDateFormat("HHmmss").format(dt); // 批次号
        String TransDate = new SimpleDateFormat("yyyyMMdd").format(dt); // 交易日期

        int n = 3; // 批量交易数量
        // 文件的第一行，包含：商户号，批次号，总笔数，总金额，各项用“|”分割
        String fileContent = BATCH_MER_ID + "|" + MerSeqId + "|" + n + "|300";
        System.out.println("文件头：" + fileContent);

		/*
         * 一行代表一笔交易记录。在每一行交易记录中，交易所需的各项都用“|”隔开
		 * 商户日期，流水号[订单号]，开户行代号，卡折标志，卡号/折号，持卡人姓名，证件类型，证件号码，金额，用途，私有域
		 */
        for (int i = 1; i <= n; i++) {
            String OrdId = TransDate + MerSeqId + "0" + i;  // 订单号
            fileContent += "\r\n"
                    + TransDate
                    + "|"
                    + OrdId
                    + "|0103|0|6228401111111111111|张三|01|310104200005053209|100|扣款|二次扣款测试";

        }
        System.out.println("文件内容：");
        System.out.println(fileContent);
        // 文件命名规范：MERID_YYYYMMDD_XXXXXX_Q.TXT
        String fileName = BATCH_MER_ID + "_" + TransDate + "_" + MerSeqId + "_Q.txt";
        System.out.println("filePathName=[" + BATCH_FILE_PATH + fileName + "]");
        // 写入文件
        File file = new File(BATCH_FILE_PATH + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(fileContent.getBytes("UTF-8"));
        fos.flush();
        fos.close();
        try {
            Map<String, String> response = new ChinapayShTxnHandler().uploadBatchFile(fileName, fileContent);
            readMap(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 代扣文件下载
    public static void downloadNoticeFile() {
        // 原始文件名
        String orFileName = "808080211388500_20140702_151155_Q.txt";
        // 回盘文件名
        String fileName = "808080211388500_20140702_151155_P.txt";

        try {
            Map<String, String> response = new ChinapayShTxnHandler().downloadNoticeFile(orFileName, fileName);
            readMap(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 代扣文件查询接口
    public static void qry(String fileName) {

        try {
            Map<String, String> response = new ChinapayShTxnHandler().qryBatchFileStatus(fileName);
            readMap(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readMap(Map<String, String> response) {
        for(Map.Entry<String, String> entry : response.entrySet()) {
            System.out.println(entry.getKey() + "  "  + entry.getValue());
        }
     }
}
