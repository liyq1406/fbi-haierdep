package org.fbi.dep.txn;

import org.fbi.dep.model.base.ToaXml;
import org.fbi.endpoint.sbs.domain.SOFFormBody;

import java.lang.reflect.Field;

/**
 * 交易处理
 */
public abstract class AbstractTxnProcessor implements TxnProcessor {
    public abstract String process(String userid, String msgData);

    protected void copyFormBodyToToa(SOFFormBody formBody, ToaXml toa) {
        try {
            Field[] fields = formBody.getClass().getFields();
            Class toaCLass = toa.getClass();
            Object obj = null;
            for (Field field : fields) {
                obj = field.get(formBody);
                if (obj != null) {
                    Field toaField = toaCLass.getField(field.getName());
                    if (toaField != null) {
                        toaField.set(toa, obj);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("TxnProcessor copyFormBodyToToa 解析异常");
        }
    }
}
