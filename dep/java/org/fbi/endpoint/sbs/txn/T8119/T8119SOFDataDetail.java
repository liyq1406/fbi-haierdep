package org.fbi.endpoint.sbs.txn.T8119;

import org.fbi.endpoint.sbs.core.SOFDataDetail;

/**
 01	SOF-TRANSMIT-DATA
 15	SOF-HEADER-G
 20	SOF-FMH		X(3)	空格
 20	SOF-BRANCH-ID	机构号	X(10)	从请求报文取出	010
 20	SOF-TERM-ID		X(4)	从请求报文取出
 20	SOF-OUT-DEVICE		X(1)	固定值
 20	SOF-FORM-ID
 25	SOF-FORM-ID-DEV	设备号	X(1)
 25	SOF-FORM-ID-SYS	系统号	X(2)
 25	SOF-FORM-ID-CODE	FORM号	X(4)		T***－成功
 20	SOF-STATUS	保留字段	X(2)
 15	SOF-DATA-G
 20	SOF-DATA-LEN	包长度	X(2)	失败时为0,成功时为报文体的实际长度
 15	TXXX-CURCNT	本包内记录数	X(6)	满6位前补0
 	以下为重复输出数据（重复n次，n=本包内记录数）

 20	T108-ACTNUM	帐号	X(22)	从请求报文取出
 20	T108-ACTNAM	帐户名	X(72)	左对齐，右补空格
 20	T108-BOKBAL	帐户余额	X(17)	右对齐，左补空格,如果余额为负数，则数字左侧有一个负号	+9(13).99
 20	T108-AVABAL	有效余额	X(17)	右对齐，左补空格	+9(13).99
 20	T108-FRZSTS	冻结状态	X(1)		"0-没有记录
 1-全部不准存
 2-全部不准取
 3-全部不准动
 4-部分不准存
 R-有记录"
 20	T108-ACTSTS	帐户状态	X(1)		空格-正常,I-失效
 20	T108-RECSTS	记录状态	X(1)		空格-正常,I-失效

 */
public class T8119SOFDataDetail extends SOFDataDetail {

    private String ACTNUM;
    private String ACTNAM;
    private String BOKBAL;
    private String AVABAL;
    private String FRZSTS;
    private String ACTSTS;
    private String RECSTS;

    {
        offset = 0;
        fieldTypes = new int[]{1, 1, 1, 1, 1, 1, 1};
        fieldLengths = new int[]{22, 72, 17, 17, 1, 1, 1};
    }

    public String getACTNAM() {
        return ACTNAM;
    }

    public void setACTNAM(String ACTNAM) {
        this.ACTNAM = ACTNAM;
    }

    public String getACTNUM() {
        return ACTNUM;
    }

    public void setACTNUM(String ACTNUM) {
        this.ACTNUM = ACTNUM;
    }

    public String getACTSTS() {
        return ACTSTS;
    }

    public void setACTSTS(String ACTSTS) {
        this.ACTSTS = ACTSTS;
    }

    public String getAVABAL() {
        return AVABAL;
    }

    public void setAVABAL(String AVABAL) {
        this.AVABAL = AVABAL;
    }

    public String getBOKBAL() {
        return BOKBAL;
    }

    public void setBOKBAL(String BOKBAL) {
        this.BOKBAL = BOKBAL;
    }

    public String getFRZSTS() {
        return FRZSTS;
    }

    public void setFRZSTS(String FRZSTS) {
        this.FRZSTS = FRZSTS;
    }

    public String getRECSTS() {
        return RECSTS;
    }

    public void setRECSTS(String RECSTS) {
        this.RECSTS = RECSTS;
    }
}
