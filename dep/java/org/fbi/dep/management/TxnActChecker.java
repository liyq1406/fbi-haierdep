package org.fbi.dep.management;

import org.fbi.dep.model.ActList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by lenovo on 2015-04-17.
 */
public abstract class TxnActChecker implements TxnChecker {

    protected String readFile(String fileName) {
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                buffer.append(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return buffer.toString();
    }

    protected List<ActList.Act> getActnoWhitelist(String fileName) {
        String xml = readFile(fileName);
        ActList actnoWhiteList = new ActList();
        actnoWhiteList = actnoWhiteList.toBean(xml);
        return actnoWhiteList.Acts;
    }
}
