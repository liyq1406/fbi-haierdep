package org.fbi.dep.helper.allinPayHelper.security.pki;

import java.math.BigInteger;

/**
 * Created by Lichao.W At 2015/6/24 22:37
 * wanglichao@163.com
 */
public class RSAPublicKeyStructure implements KeyStructure {
    private BigInteger modulus;
    private BigInteger pubexp;

    public RSAPublicKeyStructure(BigInteger _modulus, BigInteger _pubexp)
    {
        this.modulus = _modulus;
        this.pubexp = _pubexp;
    }

    public BigInteger getModulus()
    {
        return this.modulus;
    }

    public BigInteger getPubExp()
    {
        return this.pubexp;
    }
}
