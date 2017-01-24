//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.01.24 um 10:39:08 PM CET 
//


package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für designationType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="designationType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&gt;
 *     &lt;enumeration value="UNCLASSIFIED"/&gt;
 *     &lt;enumeration value="BAD_ANALYSIS"/&gt;
 *     &lt;enumeration value="NOT_A_BUG"/&gt;
 *     &lt;enumeration value="MOSTLY_HARMLESS"/&gt;
 *     &lt;enumeration value="SHOULD_FIX"/&gt;
 *     &lt;enumeration value="MUST_FIX"/&gt;
 *     &lt;enumeration value="I_WILL_FIX"/&gt;
 *     &lt;enumeration value="OBSOLETE_CODE"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "designationType")
@XmlEnum
public enum DesignationType {

    UNCLASSIFIED,
    BAD_ANALYSIS,
    NOT_A_BUG,
    MOSTLY_HARMLESS,
    SHOULD_FIX,
    MUST_FIX,
    I_WILL_FIX,
    OBSOLETE_CODE;

    public String value() {
        return name();
    }

    public static DesignationType fromValue(String v) {
        return valueOf(v);
    }

}
