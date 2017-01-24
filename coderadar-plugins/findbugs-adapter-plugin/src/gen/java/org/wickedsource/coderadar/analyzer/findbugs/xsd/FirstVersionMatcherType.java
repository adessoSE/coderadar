//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.01.24 um 10:39:08 PM CET 
//


package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für FirstVersionMatcherType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="FirstVersionMatcherType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}MatcherType"&gt;
 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="relOp" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FirstVersionMatcherType")
public class FirstVersionMatcherType
    extends MatcherType
{

    @XmlAttribute(name = "value", required = true)
    protected long value;
    @XmlAttribute(name = "relOp", required = true)
    protected String relOp;

    /**
     * Ruft den Wert der value-Eigenschaft ab.
     * 
     */
    public long getValue() {
        return value;
    }

    /**
     * Legt den Wert der value-Eigenschaft fest.
     * 
     */
    public void setValue(long value) {
        this.value = value;
    }

    /**
     * Ruft den Wert der relOp-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelOp() {
        return relOp;
    }

    /**
     * Legt den Wert der relOp-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelOp(String value) {
        this.relOp = value;
    }

}
