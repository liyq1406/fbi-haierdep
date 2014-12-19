package org.fbi.dep.txn;

/**
 * 交易处理
 */
public interface TxnProcessor {
    String process(String userid, String msgData);
}
