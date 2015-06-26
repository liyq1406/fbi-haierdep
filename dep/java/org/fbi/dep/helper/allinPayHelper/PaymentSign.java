package org.fbi.dep.helper.allinPayHelper;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * Created by Lichao.W At 2015/6/24 21:52
 * wanglichao@163.com
 */
public class PaymentSign {

    public static void initProvider()
    {
        //if(initProv) return ;
        BouncyCastleProvider p=new BouncyCastleProvider();
        Security.addProvider(p);
    }
}
