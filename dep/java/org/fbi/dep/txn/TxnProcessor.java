package org.fbi.dep.txn;

/**
 * ���״���
 */
public interface TxnProcessor {
    String process(String userid, String msgData);
}
