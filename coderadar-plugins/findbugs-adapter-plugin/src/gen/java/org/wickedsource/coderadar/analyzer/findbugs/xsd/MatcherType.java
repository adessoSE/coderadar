//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.01.24 um 10:39:08 PM CET 
//


package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MatcherType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MatcherType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MatcherType")
@XmlSeeAlso({
    BugMatcherType.class,
    ClassMatcherType.class,
    FirstVersionMatcherType.class,
    LastVersionMatcherType.class,
    DesignationMatcherType.class,
    BugCodeMatcherType.class,
    LocalMatcherType.class,
    BugPatternMatcherType.class,
    PriorityMatcherType.class,
    RankMatcherType.class,
    PackageMatcherType.class,
    MethodMatcherType.class,
    FieldMatcherType.class,
    OrMatcherType.class,
    AndMatcherType.class,
    MatchMatcherType.class,
    NotMatcherType.class
})
public abstract class MatcherType {


}
