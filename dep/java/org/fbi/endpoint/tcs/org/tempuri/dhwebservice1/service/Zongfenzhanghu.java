
package org.fbi.endpoint.tcs.org.tempuri.dhwebservice1.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BANK_SERIAL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TRADE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PAY_ACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PAY_ACCOUNT_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AP_AMOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TO_COMPANY_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="REC_COMPANY_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="REC_ACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VOUCHER_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PURPOSE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DC_FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bankserial",
    "tradedate",
    "payaccount",
    "payaccountname",
    "apamount",
    "tocompanycode",
    "reccompanyname",
    "recaccount",
    "voucherflag",
    "purpose",
    "dcflag"
})
@XmlRootElement(name = "zongfenzhanghu")
public class Zongfenzhanghu {

    @XmlElement(name = "BANK_SERIAL")
    protected String bankserial;
    @XmlElement(name = "TRADE_DATE")
    protected String tradedate;
    @XmlElement(name = "PAY_ACCOUNT")
    protected String payaccount;
    @XmlElement(name = "PAY_ACCOUNT_NAME")
    protected String payaccountname;
    @XmlElement(name = "AP_AMOUNT")
    protected String apamount;
    @XmlElement(name = "TO_COMPANY_CODE")
    protected String tocompanycode;
    @XmlElement(name = "REC_COMPANY_NAME")
    protected String reccompanyname;
    @XmlElement(name = "REC_ACCOUNT")
    protected String recaccount;
    @XmlElement(name = "VOUCHER_FLAG")
    protected String voucherflag;
    @XmlElement(name = "PURPOSE")
    protected String purpose;
    @XmlElement(name = "DC_FLAG")
    protected String dcflag;

    /**
     * Gets the value of the bankserial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBANKSERIAL() {
        return bankserial;
    }

    /**
     * Sets the value of the bankserial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBANKSERIAL(String value) {
        this.bankserial = value;
    }

    /**
     * Gets the value of the tradedate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTRADEDATE() {
        return tradedate;
    }

    /**
     * Sets the value of the tradedate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTRADEDATE(String value) {
        this.tradedate = value;
    }

    /**
     * Gets the value of the payaccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAYACCOUNT() {
        return payaccount;
    }

    /**
     * Sets the value of the payaccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAYACCOUNT(String value) {
        this.payaccount = value;
    }

    /**
     * Gets the value of the payaccountname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAYACCOUNTNAME() {
        return payaccountname;
    }

    /**
     * Sets the value of the payaccountname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAYACCOUNTNAME(String value) {
        this.payaccountname = value;
    }

    /**
     * Gets the value of the apamount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPAMOUNT() {
        return apamount;
    }

    /**
     * Sets the value of the apamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPAMOUNT(String value) {
        this.apamount = value;
    }

    /**
     * Gets the value of the tocompanycode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOCOMPANYCODE() {
        return tocompanycode;
    }

    /**
     * Sets the value of the tocompanycode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOCOMPANYCODE(String value) {
        this.tocompanycode = value;
    }

    /**
     * Gets the value of the reccompanyname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRECCOMPANYNAME() {
        return reccompanyname;
    }

    /**
     * Sets the value of the reccompanyname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRECCOMPANYNAME(String value) {
        this.reccompanyname = value;
    }

    /**
     * Gets the value of the recaccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRECACCOUNT() {
        return recaccount;
    }

    /**
     * Sets the value of the recaccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRECACCOUNT(String value) {
        this.recaccount = value;
    }

    /**
     * Gets the value of the voucherflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVOUCHERFLAG() {
        return voucherflag;
    }

    /**
     * Sets the value of the voucherflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVOUCHERFLAG(String value) {
        this.voucherflag = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPURPOSE() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPURPOSE(String value) {
        this.purpose = value;
    }

    /**
     * Gets the value of the dcflag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDCFLAG() {
        return dcflag;
    }

    /**
     * Sets the value of the dcflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDCFLAG(String value) {
        this.dcflag = value;
    }

}
