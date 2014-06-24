package org.fbi.endpoint.unionpay.core;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-8-27
 * Time: 13:24:32
 * To change this template use File | Settings | File Templates.
 */

/*
    INFO	TRX_CODE	交易代码	C(1, 20)	100004	否
	VERSION	版本	C(2)	04	否
	DATA_TYPE	数据格式	N(1)	2：xml格式	否
	LEVEL	处理级别	N(1)	0实时处理
            默认0	否
	USER_NAME	用户名	C(1,20)		否
	USER_PASS	用户密码			否
	REQ_SN	交易流水号	C(30)		否	不重复流水(对代收付系统需要唯一)
	SIGNED_MSG	签名信息	C		否	对本报文的所有信息进行签名（除本域外——也不含<SIGNED_MSG> </SIGNED_MSG>）
 */
public class BaseTiaHeader {
    public String TRX_CODE = "";
    public String VERSION = "04";
    public String DATA_TYPE = "2";
    public String LEVEL = "0";
    //测试
    //public String USER_NAME = "YSCS002";
    //public String USER_PASS = "111111";

    //生产
    //住房按揭
    //用户号   	用户名	用户密码	证书密码	商户编号	          权限	商户名称
    //DSF01954	HAIER3	123456	123456	000191400100880	管理员	海尔集团财务有限责任公司个人按揭（代收）
    //DSF01955	HAIER4	123456	123456	000191400100880	浏览者	海尔集团财务有限责任公司个人按揭（代收）
    //public String USER_NAME = "DSF01955";

    //消费信贷
    //DSF01956	HAIER5	123456	123456	000191400100881	管理员	海尔集团财务有限责任公司贷款（代收）
    //DSF01957	HAIER6	123456	123456	000191400100881	浏览者	海尔集团财务有限责任公司贷款（代收）
    //public String USER_NAME = "DSF01957";

    public String USER_NAME = "";
    public String USER_PASS = "";


    public String REQ_SN = "";
    public String SIGNED_MSG = "";

}