package org.fbi.dep.transform;

import org.fbi.dep.model.txn.Toa9901002;

/**
 * 泰安房产资金监管系统，解除监管翻译
 * Created by IntelliJ IDEA.
 * User: hanjianlong
 * Date: 15-6-30
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class Toa9901002Transform extends AbstractToaTransform {

    @Override
    public Toa9901002 transform(String tiaStrDatagram, String txCode) {
        Toa9901002 Toa9901002 = convertStrToBean(tiaStrDatagram);
        return Toa9901002;
    }

    private Toa9901002 convertStrToBean(String strPara) {
        Toa9901002 Toa9901002Para =new Toa9901002();
        /*正确返回：
          01	结果	                4   0000表示成功
          02	预售资金监管平台流水	16
        */
        /*错误返回：
          01    返回结果                4   0000表示成功
          02    错误原因描述	        60
        */
        String[] sourceStrArray = strPara.split("\\|");
        /* 1、如果用“.”作为分隔的话，必须是如下写法：String.split("\\."),这样才能正确的分隔开，不能用String.split(".");
           2、如果用“|”作为分隔的话，必须是如下写法：String.split("\\|"),这样才能正确的分隔开，不能用String.split("|");
             “.”和“|”都是转义字符，必须得加"\\";
           3、如果在一个字符串中有多个分隔符，可以用“|”作为连字符，比如：“a=1 and b =2 or c=3”,把三个都分隔出来，可以用String.split("and|or");
        */
        String strRtnCode=sourceStrArray[0];
        Toa9901002Para.header.RETURN_CODE = strRtnCode;
        if("0000".equals(strRtnCode)) {
            Toa9901002Para.header.REQ_SN=sourceStrArray[1];
        }else{
            Toa9901002Para.header.RETURN_MSG = sourceStrArray[1];
        }
        return Toa9901002Para;
    }
}
