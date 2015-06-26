package org.fbi.dep.helper.allinPayHelper.security.pki;

import java.io.Serializable;

/**
 * Created by Lichao.W At 2015/6/24 22:34
 * wanglichao@163.com
 */
public class ByteArray implements Serializable {
    private byte[] bytes = null;

    ByteArray(byte[] bytes) {
        this.bytes = bytes;
    }

    byte[] getBytes() {
        return this.bytes;
    }
}
