//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.01.24 um 09:29:42 AM CET 
//


package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für NotMatcherType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="NotMatcherType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}MatcherType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{}Matcher"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotMatcherType", propOrder = {
    "matcher"
})
public class NotMatcherType
    extends MatcherType
{

    @XmlElementRef(name = "Matcher", type = JAXBElement.class)
    protected JAXBElement<? extends MatcherType> matcher;

    /**
     * Ruft den Wert der matcher-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PackageMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link OrMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link DesignationMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link LocalMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link LastVersionMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link BugPatternMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link ClassMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MethodMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link BugCodeMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link FieldMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link RankMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link FirstVersionMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link NotMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link BugMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link AndMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link PriorityMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MatchMatcherType }{@code >}
     *     
     */
    public JAXBElement<? extends MatcherType> getMatcher() {
        return matcher;
    }

    /**
     * Legt den Wert der matcher-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PackageMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link OrMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link DesignationMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link LocalMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link LastVersionMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link BugPatternMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link ClassMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MethodMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link BugCodeMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link FieldMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link RankMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link FirstVersionMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link NotMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link BugMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link AndMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link PriorityMatcherType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MatchMatcherType }{@code >}
     *     
     */
    public void setMatcher(JAXBElement<? extends MatcherType> value) {
        this.matcher = value;
    }

}
