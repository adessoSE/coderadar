//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.01.24 um 10:39:08 PM CET 
//


package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Project"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Jar" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="AuxClasspathEntry" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="SrcDir" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="WrkDir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="Plugin" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="SuppressionFilter" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element ref="{}Matcher" maxOccurs="unbounded"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="Cloud" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Property" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;simpleContent&gt;
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                                     &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                   &lt;/extension&gt;
 *                                 &lt;/simpleContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="online" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                           &lt;attribute name="synced" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                           &lt;attribute name="detailsUrl" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="filepath" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="projectName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="BugInstance" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ShortMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="LongMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;choice maxOccurs="unbounded"&gt;
 *                     &lt;element name="Class"&gt;
 *                       &lt;complexType&gt;
 *                         &lt;complexContent&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                             &lt;sequence&gt;
 *                               &lt;element ref="{}SourceLine"/&gt;
 *                               &lt;element ref="{}Message" minOccurs="0"/&gt;
 *                             &lt;/sequence&gt;
 *                             &lt;attribute name="classname" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="primary" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/complexContent&gt;
 *                       &lt;/complexType&gt;
 *                     &lt;/element&gt;
 *                     &lt;element name="Type"&gt;
 *                       &lt;complexType&gt;
 *                         &lt;complexContent&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                             &lt;sequence&gt;
 *                               &lt;element ref="{}SourceLine" minOccurs="0"/&gt;
 *                               &lt;element ref="{}Message" minOccurs="0"/&gt;
 *                             &lt;/sequence&gt;
 *                             &lt;attribute name="descriptor" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="typeParameters" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/complexContent&gt;
 *                       &lt;/complexType&gt;
 *                     &lt;/element&gt;
 *                     &lt;element name="Method"&gt;
 *                       &lt;complexType&gt;
 *                         &lt;complexContent&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                             &lt;sequence minOccurs="0"&gt;
 *                               &lt;element ref="{}SourceLine"/&gt;
 *                               &lt;element ref="{}Message" minOccurs="0"/&gt;
 *                             &lt;/sequence&gt;
 *                             &lt;attribute name="classname" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="signature" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="isStatic" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                             &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="primary" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/complexContent&gt;
 *                       &lt;/complexType&gt;
 *                     &lt;/element&gt;
 *                     &lt;element ref="{}SourceLine"/&gt;
 *                     &lt;element name="LocalVariable"&gt;
 *                       &lt;complexType&gt;
 *                         &lt;complexContent&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                             &lt;sequence&gt;
 *                               &lt;element ref="{}Message" minOccurs="0"/&gt;
 *                             &lt;/sequence&gt;
 *                             &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="register" use="required" type="{http://www.w3.org/2001/XMLSchema}short" /&gt;
 *                             &lt;attribute name="pc" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *                             &lt;attribute name="role" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/complexContent&gt;
 *                       &lt;/complexType&gt;
 *                     &lt;/element&gt;
 *                     &lt;element name="Field"&gt;
 *                       &lt;complexType&gt;
 *                         &lt;complexContent&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                             &lt;sequence&gt;
 *                               &lt;element ref="{}SourceLine"/&gt;
 *                               &lt;element ref="{}Message" minOccurs="0"/&gt;
 *                             &lt;/sequence&gt;
 *                             &lt;attribute name="classname" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="signature" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="sourceSignature" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="isStatic" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                             &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="primary" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/complexContent&gt;
 *                       &lt;/complexType&gt;
 *                     &lt;/element&gt;
 *                     &lt;element name="Int"&gt;
 *                       &lt;complexType&gt;
 *                         &lt;complexContent&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                             &lt;sequence&gt;
 *                               &lt;element ref="{}Message" minOccurs="0"/&gt;
 *                             &lt;/sequence&gt;
 *                             &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                             &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/complexContent&gt;
 *                       &lt;/complexType&gt;
 *                     &lt;/element&gt;
 *                     &lt;element name="String"&gt;
 *                       &lt;complexType&gt;
 *                         &lt;complexContent&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                             &lt;sequence&gt;
 *                               &lt;element ref="{}Message" minOccurs="0"/&gt;
 *                             &lt;/sequence&gt;
 *                             &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/complexContent&gt;
 *                       &lt;/complexType&gt;
 *                     &lt;/element&gt;
 *                     &lt;element name="Property"&gt;
 *                       &lt;complexType&gt;
 *                         &lt;complexContent&gt;
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                             &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;/restriction&gt;
 *                         &lt;/complexContent&gt;
 *                       &lt;/complexType&gt;
 *                     &lt;/element&gt;
 *                     &lt;element name="UserAnnotation" minOccurs="0"&gt;
 *                       &lt;complexType&gt;
 *                         &lt;simpleContent&gt;
 *                           &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                             &lt;attribute name="designation" type="{}designationType" /&gt;
 *                             &lt;attribute name="user" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                             &lt;attribute name="needsSync" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                             &lt;attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
 *                           &lt;/extension&gt;
 *                         &lt;/simpleContent&gt;
 *                       &lt;/complexType&gt;
 *                     &lt;/element&gt;
 *                   &lt;/choice&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="priority" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
 *                 &lt;attribute name="abbrev" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="category" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="uid" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
 *                 &lt;attribute name="reviews" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="firstSeen" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="consensus" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="isInCloud" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                 &lt;attribute name="last" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="removedByChange" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                 &lt;attribute name="first" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="introducedByChange" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                 &lt;attribute name="shouldFix" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                 &lt;attribute name="ageInDays" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="notAProblem" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                 &lt;attribute name="instanceHash" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="instanceOccurrenceNum" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="instanceOccurrenceMax" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="rank" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="cweid" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="BugCategory" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Abbreviation" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" minOccurs="0"/&gt;
 *                   &lt;element name="Details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="category" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="BugPattern" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ShortDescription" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Details" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="abbrev" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="category" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="cweid" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="BugCode" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="abbrev" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="cweid" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Errors"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="MissingClass" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="errors" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="missingClasses" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="FindBugsSummary"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="FileStats" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="bugCount" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                           &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                           &lt;attribute name="bugHash" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="PackageStats" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="ClassStats" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                     &lt;attribute name="sourceFile" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                     &lt;attribute name="interface" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                                     &lt;attribute name="size" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
 *                                     &lt;attribute name="bugs" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                                     &lt;attribute name="priority_1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                                     &lt;attribute name="priority_2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                                     &lt;attribute name="priority_3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="package" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="total_bugs" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                           &lt;attribute name="total_types" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                           &lt;attribute name="total_size" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
 *                           &lt;attribute name="priority_1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                           &lt;attribute name="priority_2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                           &lt;attribute name="priority_3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="FindBugsProfile" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="ClassProfile" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                     &lt;attribute name="totalMilliseconds" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                                     &lt;attribute name="invocations" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                                     &lt;attribute name="avgMicrosecondsPerInvocation" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                                     &lt;attribute name="maxMicrosecondsPerInvocation" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                                     &lt;attribute name="maxContext" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                     &lt;attribute name="standardDeviationMircosecondsPerInvocation" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="timestamp" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="total_classes" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="referenced_classes" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="total_bugs" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="total_size" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="num_packages" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="vm_version" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="cpu_seconds" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *                 &lt;attribute name="clock_seconds" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *                 &lt;attribute name="peak_mbytes" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *                 &lt;attribute name="alloc_mbytes" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *                 &lt;attribute name="gc_seconds" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *                 &lt;attribute name="priority_1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="priority_2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="priority_3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="SummaryHTML" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ClassFeatures"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ClassFeatureSet" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="Feature" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="History"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="AppVersion" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="sequence" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                           &lt;attribute name="timestamp" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
 *                           &lt;attribute name="release" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="codeSize" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                           &lt;attribute name="numClasses" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="sequence" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="timestamp" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
 *       &lt;attribute name="analysisTimestamp" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
 *       &lt;attribute name="release" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "project",
    "bugInstance",
    "bugCategory",
    "bugPattern",
    "bugCode",
    "errors",
    "findBugsSummary",
    "summaryHTML",
    "classFeatures",
    "history"
})
@XmlRootElement(name = "BugCollection")
public class BugCollection {

    @XmlElement(name = "Project", required = true)
    protected BugCollection.Project project;
    @XmlElement(name = "BugInstance")
    protected List<BugCollection.BugInstance> bugInstance;
    @XmlElement(name = "BugCategory")
    protected List<BugCollection.BugCategory> bugCategory;
    @XmlElement(name = "BugPattern")
    protected List<BugCollection.BugPattern> bugPattern;
    @XmlElement(name = "BugCode")
    protected List<BugCollection.BugCode> bugCode;
    @XmlElement(name = "Errors", required = true)
    protected BugCollection.Errors errors;
    @XmlElement(name = "FindBugsSummary", required = true)
    protected BugCollection.FindBugsSummary findBugsSummary;
    @XmlElement(name = "SummaryHTML")
    protected java.lang.String summaryHTML;
    @XmlElement(name = "ClassFeatures", required = true)
    protected BugCollection.ClassFeatures classFeatures;
    @XmlElement(name = "History", required = true)
    protected BugCollection.History history;
    @XmlAttribute(name = "version", required = true)
    protected java.lang.String version;
    @XmlAttribute(name = "sequence", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long sequence;
    @XmlAttribute(name = "timestamp", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger timestamp;
    @XmlAttribute(name = "analysisTimestamp", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger analysisTimestamp;
    @XmlAttribute(name = "release", required = true)
    protected java.lang.String release;

    /**
     * Ruft den Wert der project-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BugCollection.Project }
     *     
     */
    public BugCollection.Project getProject() {
        return project;
    }

    /**
     * Legt den Wert der project-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BugCollection.Project }
     *     
     */
    public void setProject(BugCollection.Project value) {
        this.project = value;
    }

    /**
     * Gets the value of the bugInstance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bugInstance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBugInstance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BugCollection.BugInstance }
     * 
     * 
     */
    public List<BugCollection.BugInstance> getBugInstance() {
        if (bugInstance == null) {
            bugInstance = new ArrayList<BugCollection.BugInstance>();
        }
        return this.bugInstance;
    }

    /**
     * Gets the value of the bugCategory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bugCategory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBugCategory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BugCollection.BugCategory }
     * 
     * 
     */
    public List<BugCollection.BugCategory> getBugCategory() {
        if (bugCategory == null) {
            bugCategory = new ArrayList<BugCollection.BugCategory>();
        }
        return this.bugCategory;
    }

    /**
     * Gets the value of the bugPattern property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bugPattern property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBugPattern().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BugCollection.BugPattern }
     * 
     * 
     */
    public List<BugCollection.BugPattern> getBugPattern() {
        if (bugPattern == null) {
            bugPattern = new ArrayList<BugCollection.BugPattern>();
        }
        return this.bugPattern;
    }

    /**
     * Gets the value of the bugCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bugCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBugCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BugCollection.BugCode }
     * 
     * 
     */
    public List<BugCollection.BugCode> getBugCode() {
        if (bugCode == null) {
            bugCode = new ArrayList<BugCollection.BugCode>();
        }
        return this.bugCode;
    }

    /**
     * Ruft den Wert der errors-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BugCollection.Errors }
     *     
     */
    public BugCollection.Errors getErrors() {
        return errors;
    }

    /**
     * Legt den Wert der errors-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BugCollection.Errors }
     *     
     */
    public void setErrors(BugCollection.Errors value) {
        this.errors = value;
    }

    /**
     * Ruft den Wert der findBugsSummary-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BugCollection.FindBugsSummary }
     *     
     */
    public BugCollection.FindBugsSummary getFindBugsSummary() {
        return findBugsSummary;
    }

    /**
     * Legt den Wert der findBugsSummary-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BugCollection.FindBugsSummary }
     *     
     */
    public void setFindBugsSummary(BugCollection.FindBugsSummary value) {
        this.findBugsSummary = value;
    }

    /**
     * Ruft den Wert der summaryHTML-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getSummaryHTML() {
        return summaryHTML;
    }

    /**
     * Legt den Wert der summaryHTML-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setSummaryHTML(java.lang.String value) {
        this.summaryHTML = value;
    }

    /**
     * Ruft den Wert der classFeatures-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BugCollection.ClassFeatures }
     *     
     */
    public BugCollection.ClassFeatures getClassFeatures() {
        return classFeatures;
    }

    /**
     * Legt den Wert der classFeatures-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BugCollection.ClassFeatures }
     *     
     */
    public void setClassFeatures(BugCollection.ClassFeatures value) {
        this.classFeatures = value;
    }

    /**
     * Ruft den Wert der history-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BugCollection.History }
     *     
     */
    public BugCollection.History getHistory() {
        return history;
    }

    /**
     * Legt den Wert der history-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BugCollection.History }
     *     
     */
    public void setHistory(BugCollection.History value) {
        this.history = value;
    }

    /**
     * Ruft den Wert der version-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getVersion() {
        return version;
    }

    /**
     * Legt den Wert der version-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setVersion(java.lang.String value) {
        this.version = value;
    }

    /**
     * Ruft den Wert der sequence-Eigenschaft ab.
     * 
     */
    public long getSequence() {
        return sequence;
    }

    /**
     * Legt den Wert der sequence-Eigenschaft fest.
     * 
     */
    public void setSequence(long value) {
        this.sequence = value;
    }

    /**
     * Ruft den Wert der timestamp-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTimestamp() {
        return timestamp;
    }

    /**
     * Legt den Wert der timestamp-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTimestamp(BigInteger value) {
        this.timestamp = value;
    }

    /**
     * Ruft den Wert der analysisTimestamp-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAnalysisTimestamp() {
        return analysisTimestamp;
    }

    /**
     * Legt den Wert der analysisTimestamp-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAnalysisTimestamp(BigInteger value) {
        this.analysisTimestamp = value;
    }

    /**
     * Ruft den Wert der release-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getRelease() {
        return release;
    }

    /**
     * Legt den Wert der release-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setRelease(java.lang.String value) {
        this.release = value;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Abbreviation" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" minOccurs="0"/&gt;
     *         &lt;element name="Details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="category" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "description",
        "abbreviation",
        "details"
    })
    public static class BugCategory {

        @XmlElement(name = "Description", required = true)
        protected java.lang.String description;
        @XmlElement(name = "Abbreviation")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "NMTOKEN")
        protected java.lang.String abbreviation;
        @XmlElement(name = "Details")
        protected java.lang.String details;
        @XmlAttribute(name = "category", required = true)
        protected java.lang.String category;

        /**
         * Ruft den Wert der description-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getDescription() {
            return description;
        }

        /**
         * Legt den Wert der description-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setDescription(java.lang.String value) {
            this.description = value;
        }

        /**
         * Ruft den Wert der abbreviation-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getAbbreviation() {
            return abbreviation;
        }

        /**
         * Legt den Wert der abbreviation-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setAbbreviation(java.lang.String value) {
            this.abbreviation = value;
        }

        /**
         * Ruft den Wert der details-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getDetails() {
            return details;
        }

        /**
         * Legt den Wert der details-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setDetails(java.lang.String value) {
            this.details = value;
        }

        /**
         * Ruft den Wert der category-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getCategory() {
            return category;
        }

        /**
         * Legt den Wert der category-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setCategory(java.lang.String value) {
            this.category = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="abbrev" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="cweid" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "description"
    })
    public static class BugCode {

        @XmlElement(name = "Description", required = true)
        protected java.lang.String description;
        @XmlAttribute(name = "abbrev", required = true)
        protected java.lang.String abbrev;
        @XmlAttribute(name = "cweid")
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger cweid;

        /**
         * Ruft den Wert der description-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getDescription() {
            return description;
        }

        /**
         * Legt den Wert der description-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setDescription(java.lang.String value) {
            this.description = value;
        }

        /**
         * Ruft den Wert der abbrev-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getAbbrev() {
            return abbrev;
        }

        /**
         * Legt den Wert der abbrev-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setAbbrev(java.lang.String value) {
            this.abbrev = value;
        }

        /**
         * Ruft den Wert der cweid-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getCweid() {
            return cweid;
        }

        /**
         * Legt den Wert der cweid-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setCweid(BigInteger value) {
            this.cweid = value;
        }

    }


    /**
     * Each BugInstance can have a sequence of
     *                                 annotations
     * 
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="ShortMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="LongMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;choice maxOccurs="unbounded"&gt;
     *           &lt;element name="Class"&gt;
     *             &lt;complexType&gt;
     *               &lt;complexContent&gt;
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                   &lt;sequence&gt;
     *                     &lt;element ref="{}SourceLine"/&gt;
     *                     &lt;element ref="{}Message" minOccurs="0"/&gt;
     *                   &lt;/sequence&gt;
     *                   &lt;attribute name="classname" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="primary" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *                 &lt;/restriction&gt;
     *               &lt;/complexContent&gt;
     *             &lt;/complexType&gt;
     *           &lt;/element&gt;
     *           &lt;element name="Type"&gt;
     *             &lt;complexType&gt;
     *               &lt;complexContent&gt;
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                   &lt;sequence&gt;
     *                     &lt;element ref="{}SourceLine" minOccurs="0"/&gt;
     *                     &lt;element ref="{}Message" minOccurs="0"/&gt;
     *                   &lt;/sequence&gt;
     *                   &lt;attribute name="descriptor" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="typeParameters" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;/restriction&gt;
     *               &lt;/complexContent&gt;
     *             &lt;/complexType&gt;
     *           &lt;/element&gt;
     *           &lt;element name="Method"&gt;
     *             &lt;complexType&gt;
     *               &lt;complexContent&gt;
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                   &lt;sequence minOccurs="0"&gt;
     *                     &lt;element ref="{}SourceLine"/&gt;
     *                     &lt;element ref="{}Message" minOccurs="0"/&gt;
     *                   &lt;/sequence&gt;
     *                   &lt;attribute name="classname" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="signature" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="isStatic" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *                   &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="primary" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *                 &lt;/restriction&gt;
     *               &lt;/complexContent&gt;
     *             &lt;/complexType&gt;
     *           &lt;/element&gt;
     *           &lt;element ref="{}SourceLine"/&gt;
     *           &lt;element name="LocalVariable"&gt;
     *             &lt;complexType&gt;
     *               &lt;complexContent&gt;
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                   &lt;sequence&gt;
     *                     &lt;element ref="{}Message" minOccurs="0"/&gt;
     *                   &lt;/sequence&gt;
     *                   &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="register" use="required" type="{http://www.w3.org/2001/XMLSchema}short" /&gt;
     *                   &lt;attribute name="pc" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
     *                   &lt;attribute name="role" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;/restriction&gt;
     *               &lt;/complexContent&gt;
     *             &lt;/complexType&gt;
     *           &lt;/element&gt;
     *           &lt;element name="Field"&gt;
     *             &lt;complexType&gt;
     *               &lt;complexContent&gt;
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                   &lt;sequence&gt;
     *                     &lt;element ref="{}SourceLine"/&gt;
     *                     &lt;element ref="{}Message" minOccurs="0"/&gt;
     *                   &lt;/sequence&gt;
     *                   &lt;attribute name="classname" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="signature" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="sourceSignature" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="isStatic" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *                   &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="primary" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *                 &lt;/restriction&gt;
     *               &lt;/complexContent&gt;
     *             &lt;/complexType&gt;
     *           &lt;/element&gt;
     *           &lt;element name="Int"&gt;
     *             &lt;complexType&gt;
     *               &lt;complexContent&gt;
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                   &lt;sequence&gt;
     *                     &lt;element ref="{}Message" minOccurs="0"/&gt;
     *                   &lt;/sequence&gt;
     *                   &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *                   &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;/restriction&gt;
     *               &lt;/complexContent&gt;
     *             &lt;/complexType&gt;
     *           &lt;/element&gt;
     *           &lt;element name="String"&gt;
     *             &lt;complexType&gt;
     *               &lt;complexContent&gt;
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                   &lt;sequence&gt;
     *                     &lt;element ref="{}Message" minOccurs="0"/&gt;
     *                   &lt;/sequence&gt;
     *                   &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;/restriction&gt;
     *               &lt;/complexContent&gt;
     *             &lt;/complexType&gt;
     *           &lt;/element&gt;
     *           &lt;element name="Property"&gt;
     *             &lt;complexType&gt;
     *               &lt;complexContent&gt;
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                   &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;/restriction&gt;
     *               &lt;/complexContent&gt;
     *             &lt;/complexType&gt;
     *           &lt;/element&gt;
     *           &lt;element name="UserAnnotation" minOccurs="0"&gt;
     *             &lt;complexType&gt;
     *               &lt;simpleContent&gt;
     *                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *                   &lt;attribute name="designation" type="{}designationType" /&gt;
     *                   &lt;attribute name="user" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                   &lt;attribute name="needsSync" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *                   &lt;attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
     *                 &lt;/extension&gt;
     *               &lt;/simpleContent&gt;
     *             &lt;/complexType&gt;
     *           &lt;/element&gt;
     *         &lt;/choice&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="priority" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
     *       &lt;attribute name="abbrev" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="category" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="uid" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
     *       &lt;attribute name="reviews" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="firstSeen" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="consensus" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="isInCloud" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *       &lt;attribute name="last" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="removedByChange" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *       &lt;attribute name="first" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="introducedByChange" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *       &lt;attribute name="shouldFix" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *       &lt;attribute name="ageInDays" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="notAProblem" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *       &lt;attribute name="instanceHash" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="instanceOccurrenceNum" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="instanceOccurrenceMax" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="rank" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="cweid" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "shortMessage",
        "longMessage",
        "clazzOrTypeOrMethod"
    })
    public static class BugInstance {

        @XmlElement(name = "ShortMessage")
        protected java.lang.String shortMessage;
        @XmlElement(name = "LongMessage")
        protected java.lang.String longMessage;
        @XmlElements({
            @XmlElement(name = "Class", type = BugCollection.BugInstance.Class.class),
            @XmlElement(name = "Type", type = BugCollection.BugInstance.Type.class),
            @XmlElement(name = "Method", type = BugCollection.BugInstance.Method.class),
            @XmlElement(name = "SourceLine", type = SourceLine.class),
            @XmlElement(name = "LocalVariable", type = BugCollection.BugInstance.LocalVariable.class),
            @XmlElement(name = "Field", type = BugCollection.BugInstance.Field.class),
            @XmlElement(name = "Int", type = BugCollection.BugInstance.Int.class),
            @XmlElement(name = "String", type = BugCollection.BugInstance.String.class),
            @XmlElement(name = "Property", type = BugCollection.BugInstance.Property.class),
            @XmlElement(name = "UserAnnotation", type = BugCollection.BugInstance.UserAnnotation.class)
        })
        protected List<Object> clazzOrTypeOrMethod;
        @XmlAttribute(name = "type", required = true)
        protected java.lang.String type;
        @XmlAttribute(name = "priority", required = true)
        @XmlSchemaType(name = "unsignedByte")
        protected short priority;
        @XmlAttribute(name = "abbrev", required = true)
        protected java.lang.String abbrev;
        @XmlAttribute(name = "category", required = true)
        protected java.lang.String category;
        @XmlAttribute(name = "uid")
        @XmlSchemaType(name = "unsignedLong")
        protected BigInteger uid;
        @XmlAttribute(name = "reviews")
        @XmlSchemaType(name = "unsignedInt")
        protected Long reviews;
        @XmlAttribute(name = "firstSeen")
        protected java.lang.String firstSeen;
        @XmlAttribute(name = "consensus")
        protected java.lang.String consensus;
        @XmlAttribute(name = "isInCloud")
        protected Boolean isInCloud;
        @XmlAttribute(name = "last")
        @XmlSchemaType(name = "unsignedInt")
        protected Long last;
        @XmlAttribute(name = "removedByChange")
        protected Boolean removedByChange;
        @XmlAttribute(name = "first")
        @XmlSchemaType(name = "unsignedInt")
        protected Long first;
        @XmlAttribute(name = "introducedByChange")
        protected Boolean introducedByChange;
        @XmlAttribute(name = "shouldFix")
        protected Boolean shouldFix;
        @XmlAttribute(name = "ageInDays")
        @XmlSchemaType(name = "unsignedInt")
        protected Long ageInDays;
        @XmlAttribute(name = "notAProblem")
        protected Boolean notAProblem;
        @XmlAttribute(name = "instanceHash")
        protected java.lang.String instanceHash;
        @XmlAttribute(name = "instanceOccurrenceNum")
        @XmlSchemaType(name = "unsignedInt")
        protected Long instanceOccurrenceNum;
        @XmlAttribute(name = "instanceOccurrenceMax")
        @XmlSchemaType(name = "unsignedInt")
        protected Long instanceOccurrenceMax;
        @XmlAttribute(name = "rank")
        @XmlSchemaType(name = "unsignedInt")
        protected Long rank;
        @XmlAttribute(name = "cweid")
        @XmlSchemaType(name = "unsignedInt")
        protected Long cweid;

        /**
         * Ruft den Wert der shortMessage-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getShortMessage() {
            return shortMessage;
        }

        /**
         * Legt den Wert der shortMessage-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setShortMessage(java.lang.String value) {
            this.shortMessage = value;
        }

        /**
         * Ruft den Wert der longMessage-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getLongMessage() {
            return longMessage;
        }

        /**
         * Legt den Wert der longMessage-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setLongMessage(java.lang.String value) {
            this.longMessage = value;
        }

        /**
         * Gets the value of the clazzOrTypeOrMethod property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the clazzOrTypeOrMethod property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getClazzOrTypeOrMethod().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BugCollection.BugInstance.Class }
         * {@link BugCollection.BugInstance.Type }
         * {@link BugCollection.BugInstance.Method }
         * {@link SourceLine }
         * {@link BugCollection.BugInstance.LocalVariable }
         * {@link BugCollection.BugInstance.Field }
         * {@link BugCollection.BugInstance.Int }
         * {@link BugCollection.BugInstance.String }
         * {@link BugCollection.BugInstance.Property }
         * {@link BugCollection.BugInstance.UserAnnotation }
         * 
         * 
         */
        public List<Object> getClazzOrTypeOrMethod() {
            if (clazzOrTypeOrMethod == null) {
                clazzOrTypeOrMethod = new ArrayList<Object>();
            }
            return this.clazzOrTypeOrMethod;
        }

        /**
         * Ruft den Wert der type-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getType() {
            return type;
        }

        /**
         * Legt den Wert der type-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setType(java.lang.String value) {
            this.type = value;
        }

        /**
         * Ruft den Wert der priority-Eigenschaft ab.
         * 
         */
        public short getPriority() {
            return priority;
        }

        /**
         * Legt den Wert der priority-Eigenschaft fest.
         * 
         */
        public void setPriority(short value) {
            this.priority = value;
        }

        /**
         * Ruft den Wert der abbrev-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getAbbrev() {
            return abbrev;
        }

        /**
         * Legt den Wert der abbrev-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setAbbrev(java.lang.String value) {
            this.abbrev = value;
        }

        /**
         * Ruft den Wert der category-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getCategory() {
            return category;
        }

        /**
         * Legt den Wert der category-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setCategory(java.lang.String value) {
            this.category = value;
        }

        /**
         * Ruft den Wert der uid-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getUid() {
            return uid;
        }

        /**
         * Legt den Wert der uid-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setUid(BigInteger value) {
            this.uid = value;
        }

        /**
         * Ruft den Wert der reviews-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getReviews() {
            return reviews;
        }

        /**
         * Legt den Wert der reviews-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setReviews(Long value) {
            this.reviews = value;
        }

        /**
         * Ruft den Wert der firstSeen-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getFirstSeen() {
            return firstSeen;
        }

        /**
         * Legt den Wert der firstSeen-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setFirstSeen(java.lang.String value) {
            this.firstSeen = value;
        }

        /**
         * Ruft den Wert der consensus-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getConsensus() {
            return consensus;
        }

        /**
         * Legt den Wert der consensus-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setConsensus(java.lang.String value) {
            this.consensus = value;
        }

        /**
         * Ruft den Wert der isInCloud-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isIsInCloud() {
            return isInCloud;
        }

        /**
         * Legt den Wert der isInCloud-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setIsInCloud(Boolean value) {
            this.isInCloud = value;
        }

        /**
         * Ruft den Wert der last-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getLast() {
            return last;
        }

        /**
         * Legt den Wert der last-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setLast(Long value) {
            this.last = value;
        }

        /**
         * Ruft den Wert der removedByChange-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isRemovedByChange() {
            return removedByChange;
        }

        /**
         * Legt den Wert der removedByChange-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setRemovedByChange(Boolean value) {
            this.removedByChange = value;
        }

        /**
         * Ruft den Wert der first-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getFirst() {
            return first;
        }

        /**
         * Legt den Wert der first-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setFirst(Long value) {
            this.first = value;
        }

        /**
         * Ruft den Wert der introducedByChange-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isIntroducedByChange() {
            return introducedByChange;
        }

        /**
         * Legt den Wert der introducedByChange-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setIntroducedByChange(Boolean value) {
            this.introducedByChange = value;
        }

        /**
         * Ruft den Wert der shouldFix-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isShouldFix() {
            return shouldFix;
        }

        /**
         * Legt den Wert der shouldFix-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setShouldFix(Boolean value) {
            this.shouldFix = value;
        }

        /**
         * Ruft den Wert der ageInDays-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getAgeInDays() {
            return ageInDays;
        }

        /**
         * Legt den Wert der ageInDays-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setAgeInDays(Long value) {
            this.ageInDays = value;
        }

        /**
         * Ruft den Wert der notAProblem-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isNotAProblem() {
            return notAProblem;
        }

        /**
         * Legt den Wert der notAProblem-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setNotAProblem(Boolean value) {
            this.notAProblem = value;
        }

        /**
         * Ruft den Wert der instanceHash-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getInstanceHash() {
            return instanceHash;
        }

        /**
         * Legt den Wert der instanceHash-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setInstanceHash(java.lang.String value) {
            this.instanceHash = value;
        }

        /**
         * Ruft den Wert der instanceOccurrenceNum-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getInstanceOccurrenceNum() {
            return instanceOccurrenceNum;
        }

        /**
         * Legt den Wert der instanceOccurrenceNum-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setInstanceOccurrenceNum(Long value) {
            this.instanceOccurrenceNum = value;
        }

        /**
         * Ruft den Wert der instanceOccurrenceMax-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getInstanceOccurrenceMax() {
            return instanceOccurrenceMax;
        }

        /**
         * Legt den Wert der instanceOccurrenceMax-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setInstanceOccurrenceMax(Long value) {
            this.instanceOccurrenceMax = value;
        }

        /**
         * Ruft den Wert der rank-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getRank() {
            return rank;
        }

        /**
         * Legt den Wert der rank-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setRank(Long value) {
            this.rank = value;
        }

        /**
         * Ruft den Wert der cweid-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getCweid() {
            return cweid;
        }

        /**
         * Legt den Wert der cweid-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setCweid(Long value) {
            this.cweid = value;
        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element ref="{}SourceLine"/&gt;
         *         &lt;element ref="{}Message" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="classname" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="primary" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "sourceLine",
            "message"
        })
        public static class Class {

            @XmlElement(name = "SourceLine", required = true)
            protected SourceLine sourceLine;
            @XmlElement(name = "Message")
            protected java.lang.String message;
            @XmlAttribute(name = "classname", required = true)
            protected java.lang.String classname;
            @XmlAttribute(name = "role")
            protected java.lang.String role;
            @XmlAttribute(name = "primary")
            protected Boolean primary;

            /**
             * Ruft den Wert der sourceLine-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link SourceLine }
             *     
             */
            public SourceLine getSourceLine() {
                return sourceLine;
            }

            /**
             * Legt den Wert der sourceLine-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link SourceLine }
             *     
             */
            public void setSourceLine(SourceLine value) {
                this.sourceLine = value;
            }

            /**
             * Ruft den Wert der message-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getMessage() {
                return message;
            }

            /**
             * Legt den Wert der message-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setMessage(java.lang.String value) {
                this.message = value;
            }

            /**
             * Ruft den Wert der classname-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getClassname() {
                return classname;
            }

            /**
             * Legt den Wert der classname-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setClassname(java.lang.String value) {
                this.classname = value;
            }

            /**
             * Ruft den Wert der role-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getRole() {
                return role;
            }

            /**
             * Legt den Wert der role-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setRole(java.lang.String value) {
                this.role = value;
            }

            /**
             * Ruft den Wert der primary-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isPrimary() {
                return primary;
            }

            /**
             * Legt den Wert der primary-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setPrimary(Boolean value) {
                this.primary = value;
            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element ref="{}SourceLine"/&gt;
         *         &lt;element ref="{}Message" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="classname" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="signature" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="sourceSignature" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="isStatic" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
         *       &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="primary" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "sourceLine",
            "message"
        })
        public static class Field {

            @XmlElement(name = "SourceLine", required = true)
            protected SourceLine sourceLine;
            @XmlElement(name = "Message")
            protected java.lang.String message;
            @XmlAttribute(name = "classname", required = true)
            protected java.lang.String classname;
            @XmlAttribute(name = "name", required = true)
            protected java.lang.String name;
            @XmlAttribute(name = "signature", required = true)
            protected java.lang.String signature;
            @XmlAttribute(name = "sourceSignature")
            protected java.lang.String sourceSignature;
            @XmlAttribute(name = "isStatic", required = true)
            protected boolean isStatic;
            @XmlAttribute(name = "role")
            protected java.lang.String role;
            @XmlAttribute(name = "primary")
            protected Boolean primary;

            /**
             * Ruft den Wert der sourceLine-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link SourceLine }
             *     
             */
            public SourceLine getSourceLine() {
                return sourceLine;
            }

            /**
             * Legt den Wert der sourceLine-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link SourceLine }
             *     
             */
            public void setSourceLine(SourceLine value) {
                this.sourceLine = value;
            }

            /**
             * Ruft den Wert der message-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getMessage() {
                return message;
            }

            /**
             * Legt den Wert der message-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setMessage(java.lang.String value) {
                this.message = value;
            }

            /**
             * Ruft den Wert der classname-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getClassname() {
                return classname;
            }

            /**
             * Legt den Wert der classname-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setClassname(java.lang.String value) {
                this.classname = value;
            }

            /**
             * Ruft den Wert der name-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getName() {
                return name;
            }

            /**
             * Legt den Wert der name-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setName(java.lang.String value) {
                this.name = value;
            }

            /**
             * Ruft den Wert der signature-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getSignature() {
                return signature;
            }

            /**
             * Legt den Wert der signature-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setSignature(java.lang.String value) {
                this.signature = value;
            }

            /**
             * Ruft den Wert der sourceSignature-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getSourceSignature() {
                return sourceSignature;
            }

            /**
             * Legt den Wert der sourceSignature-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setSourceSignature(java.lang.String value) {
                this.sourceSignature = value;
            }

            /**
             * Ruft den Wert der isStatic-Eigenschaft ab.
             * 
             */
            public boolean isIsStatic() {
                return isStatic;
            }

            /**
             * Legt den Wert der isStatic-Eigenschaft fest.
             * 
             */
            public void setIsStatic(boolean value) {
                this.isStatic = value;
            }

            /**
             * Ruft den Wert der role-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getRole() {
                return role;
            }

            /**
             * Legt den Wert der role-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setRole(java.lang.String value) {
                this.role = value;
            }

            /**
             * Ruft den Wert der primary-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isPrimary() {
                return primary;
            }

            /**
             * Legt den Wert der primary-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setPrimary(Boolean value) {
                this.primary = value;
            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element ref="{}Message" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
         *       &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "message"
        })
        public static class Int {

            @XmlElement(name = "Message")
            protected java.lang.String message;
            @XmlAttribute(name = "value", required = true)
            protected long value;
            @XmlAttribute(name = "role")
            protected java.lang.String role;

            /**
             * Ruft den Wert der message-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getMessage() {
                return message;
            }

            /**
             * Legt den Wert der message-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setMessage(java.lang.String value) {
                this.message = value;
            }

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
             * Ruft den Wert der role-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getRole() {
                return role;
            }

            /**
             * Legt den Wert der role-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setRole(java.lang.String value) {
                this.role = value;
            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element ref="{}Message" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="register" use="required" type="{http://www.w3.org/2001/XMLSchema}short" /&gt;
         *       &lt;attribute name="pc" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
         *       &lt;attribute name="role" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "message"
        })
        public static class LocalVariable {

            @XmlElement(name = "Message")
            protected java.lang.String message;
            @XmlAttribute(name = "name", required = true)
            protected java.lang.String name;
            @XmlAttribute(name = "register", required = true)
            protected short register;
            @XmlAttribute(name = "pc", required = true)
            protected int pc;
            @XmlAttribute(name = "role", required = true)
            protected java.lang.String role;

            /**
             * Ruft den Wert der message-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getMessage() {
                return message;
            }

            /**
             * Legt den Wert der message-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setMessage(java.lang.String value) {
                this.message = value;
            }

            /**
             * Ruft den Wert der name-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getName() {
                return name;
            }

            /**
             * Legt den Wert der name-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setName(java.lang.String value) {
                this.name = value;
            }

            /**
             * Ruft den Wert der register-Eigenschaft ab.
             * 
             */
            public short getRegister() {
                return register;
            }

            /**
             * Legt den Wert der register-Eigenschaft fest.
             * 
             */
            public void setRegister(short value) {
                this.register = value;
            }

            /**
             * Ruft den Wert der pc-Eigenschaft ab.
             * 
             */
            public int getPc() {
                return pc;
            }

            /**
             * Legt den Wert der pc-Eigenschaft fest.
             * 
             */
            public void setPc(int value) {
                this.pc = value;
            }

            /**
             * Ruft den Wert der role-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getRole() {
                return role;
            }

            /**
             * Legt den Wert der role-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setRole(java.lang.String value) {
                this.role = value;
            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence minOccurs="0"&gt;
         *         &lt;element ref="{}SourceLine"/&gt;
         *         &lt;element ref="{}Message" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="classname" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="signature" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="isStatic" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
         *       &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="primary" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "sourceLine",
            "message"
        })
        public static class Method {

            @XmlElement(name = "SourceLine")
            protected SourceLine sourceLine;
            @XmlElement(name = "Message")
            protected java.lang.String message;
            @XmlAttribute(name = "classname", required = true)
            protected java.lang.String classname;
            @XmlAttribute(name = "name", required = true)
            protected java.lang.String name;
            @XmlAttribute(name = "signature", required = true)
            protected java.lang.String signature;
            @XmlAttribute(name = "isStatic", required = true)
            protected boolean isStatic;
            @XmlAttribute(name = "role")
            protected java.lang.String role;
            @XmlAttribute(name = "primary")
            protected Boolean primary;

            /**
             * Ruft den Wert der sourceLine-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link SourceLine }
             *     
             */
            public SourceLine getSourceLine() {
                return sourceLine;
            }

            /**
             * Legt den Wert der sourceLine-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link SourceLine }
             *     
             */
            public void setSourceLine(SourceLine value) {
                this.sourceLine = value;
            }

            /**
             * Ruft den Wert der message-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getMessage() {
                return message;
            }

            /**
             * Legt den Wert der message-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setMessage(java.lang.String value) {
                this.message = value;
            }

            /**
             * Ruft den Wert der classname-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getClassname() {
                return classname;
            }

            /**
             * Legt den Wert der classname-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setClassname(java.lang.String value) {
                this.classname = value;
            }

            /**
             * Ruft den Wert der name-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getName() {
                return name;
            }

            /**
             * Legt den Wert der name-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setName(java.lang.String value) {
                this.name = value;
            }

            /**
             * Ruft den Wert der signature-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getSignature() {
                return signature;
            }

            /**
             * Legt den Wert der signature-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setSignature(java.lang.String value) {
                this.signature = value;
            }

            /**
             * Ruft den Wert der isStatic-Eigenschaft ab.
             * 
             */
            public boolean isIsStatic() {
                return isStatic;
            }

            /**
             * Legt den Wert der isStatic-Eigenschaft fest.
             * 
             */
            public void setIsStatic(boolean value) {
                this.isStatic = value;
            }

            /**
             * Ruft den Wert der role-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getRole() {
                return role;
            }

            /**
             * Legt den Wert der role-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setRole(java.lang.String value) {
                this.role = value;
            }

            /**
             * Ruft den Wert der primary-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isPrimary() {
                return primary;
            }

            /**
             * Legt den Wert der primary-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setPrimary(Boolean value) {
                this.primary = value;
            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Property {

            @XmlAttribute(name = "name", required = true)
            protected java.lang.String name;
            @XmlAttribute(name = "value", required = true)
            protected java.lang.String value;

            /**
             * Ruft den Wert der name-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getName() {
                return name;
            }

            /**
             * Legt den Wert der name-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setName(java.lang.String value) {
                this.name = value;
            }

            /**
             * Ruft den Wert der value-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getValue() {
                return value;
            }

            /**
             * Legt den Wert der value-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setValue(java.lang.String value) {
                this.value = value;
            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element ref="{}Message" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "message"
        })
        public static class String {

            @XmlElement(name = "Message")
            protected java.lang.String message;
            @XmlAttribute(name = "value", required = true)
            protected java.lang.String value;
            @XmlAttribute(name = "role")
            protected java.lang.String role;

            /**
             * Ruft den Wert der message-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getMessage() {
                return message;
            }

            /**
             * Legt den Wert der message-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setMessage(java.lang.String value) {
                this.message = value;
            }

            /**
             * Ruft den Wert der value-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getValue() {
                return value;
            }

            /**
             * Legt den Wert der value-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setValue(java.lang.String value) {
                this.value = value;
            }

            /**
             * Ruft den Wert der role-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getRole() {
                return role;
            }

            /**
             * Legt den Wert der role-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setRole(java.lang.String value) {
                this.role = value;
            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element ref="{}SourceLine" minOccurs="0"/&gt;
         *         &lt;element ref="{}Message" minOccurs="0"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="descriptor" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="typeParameters" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "sourceLine",
            "message"
        })
        public static class Type {

            @XmlElement(name = "SourceLine")
            protected SourceLine sourceLine;
            @XmlElement(name = "Message")
            protected java.lang.String message;
            @XmlAttribute(name = "descriptor", required = true)
            protected java.lang.String descriptor;
            @XmlAttribute(name = "role")
            protected java.lang.String role;
            @XmlAttribute(name = "typeParameters")
            protected java.lang.String typeParameters;

            /**
             * Ruft den Wert der sourceLine-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link SourceLine }
             *     
             */
            public SourceLine getSourceLine() {
                return sourceLine;
            }

            /**
             * Legt den Wert der sourceLine-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link SourceLine }
             *     
             */
            public void setSourceLine(SourceLine value) {
                this.sourceLine = value;
            }

            /**
             * Ruft den Wert der message-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getMessage() {
                return message;
            }

            /**
             * Legt den Wert der message-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setMessage(java.lang.String value) {
                this.message = value;
            }

            /**
             * Ruft den Wert der descriptor-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getDescriptor() {
                return descriptor;
            }

            /**
             * Legt den Wert der descriptor-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setDescriptor(java.lang.String value) {
                this.descriptor = value;
            }

            /**
             * Ruft den Wert der role-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getRole() {
                return role;
            }

            /**
             * Legt den Wert der role-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setRole(java.lang.String value) {
                this.role = value;
            }

            /**
             * Ruft den Wert der typeParameters-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getTypeParameters() {
                return typeParameters;
            }

            /**
             * Legt den Wert der typeParameters-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setTypeParameters(java.lang.String value) {
                this.typeParameters = value;
            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;simpleContent&gt;
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
         *       &lt;attribute name="designation" type="{}designationType" /&gt;
         *       &lt;attribute name="user" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="needsSync" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
         *       &lt;attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
         *     &lt;/extension&gt;
         *   &lt;/simpleContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class UserAnnotation {

            @XmlValue
            protected java.lang.String value;
            @XmlAttribute(name = "designation")
            protected DesignationType designation;
            @XmlAttribute(name = "user")
            protected java.lang.String user;
            @XmlAttribute(name = "needsSync")
            protected Boolean needsSync;
            @XmlAttribute(name = "timestamp")
            @XmlSchemaType(name = "unsignedLong")
            protected BigInteger timestamp;

            /**
             * Ruft den Wert der value-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getValue() {
                return value;
            }

            /**
             * Legt den Wert der value-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setValue(java.lang.String value) {
                this.value = value;
            }

            /**
             * Ruft den Wert der designation-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link DesignationType }
             *     
             */
            public DesignationType getDesignation() {
                return designation;
            }

            /**
             * Legt den Wert der designation-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link DesignationType }
             *     
             */
            public void setDesignation(DesignationType value) {
                this.designation = value;
            }

            /**
             * Ruft den Wert der user-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getUser() {
                return user;
            }

            /**
             * Legt den Wert der user-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setUser(java.lang.String value) {
                this.user = value;
            }

            /**
             * Ruft den Wert der needsSync-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isNeedsSync() {
                return needsSync;
            }

            /**
             * Legt den Wert der needsSync-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setNeedsSync(Boolean value) {
                this.needsSync = value;
            }

            /**
             * Ruft den Wert der timestamp-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getTimestamp() {
                return timestamp;
            }

            /**
             * Legt den Wert der timestamp-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setTimestamp(BigInteger value) {
                this.timestamp = value;
            }

        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="ShortDescription" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Details" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="abbrev" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="category" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="cweid" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "shortDescription",
        "details"
    })
    public static class BugPattern {

        @XmlElement(name = "ShortDescription", required = true)
        protected java.lang.String shortDescription;
        @XmlElement(name = "Details", required = true)
        protected java.lang.String details;
        @XmlAttribute(name = "type", required = true)
        protected java.lang.String type;
        @XmlAttribute(name = "abbrev", required = true)
        protected java.lang.String abbrev;
        @XmlAttribute(name = "category", required = true)
        protected java.lang.String category;
        @XmlAttribute(name = "cweid")
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger cweid;

        /**
         * Ruft den Wert der shortDescription-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getShortDescription() {
            return shortDescription;
        }

        /**
         * Legt den Wert der shortDescription-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setShortDescription(java.lang.String value) {
            this.shortDescription = value;
        }

        /**
         * Ruft den Wert der details-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getDetails() {
            return details;
        }

        /**
         * Legt den Wert der details-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setDetails(java.lang.String value) {
            this.details = value;
        }

        /**
         * Ruft den Wert der type-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getType() {
            return type;
        }

        /**
         * Legt den Wert der type-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setType(java.lang.String value) {
            this.type = value;
        }

        /**
         * Ruft den Wert der abbrev-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getAbbrev() {
            return abbrev;
        }

        /**
         * Legt den Wert der abbrev-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setAbbrev(java.lang.String value) {
            this.abbrev = value;
        }

        /**
         * Ruft den Wert der category-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getCategory() {
            return category;
        }

        /**
         * Legt den Wert der category-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setCategory(java.lang.String value) {
            this.category = value;
        }

        /**
         * Ruft den Wert der cweid-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getCweid() {
            return cweid;
        }

        /**
         * Legt den Wert der cweid-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setCweid(BigInteger value) {
            this.cweid = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="ClassFeatureSet" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Feature" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "classFeatureSet"
    })
    public static class ClassFeatures {

        @XmlElement(name = "ClassFeatureSet")
        protected List<BugCollection.ClassFeatures.ClassFeatureSet> classFeatureSet;

        /**
         * Gets the value of the classFeatureSet property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the classFeatureSet property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getClassFeatureSet().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BugCollection.ClassFeatures.ClassFeatureSet }
         * 
         * 
         */
        public List<BugCollection.ClassFeatures.ClassFeatureSet> getClassFeatureSet() {
            if (classFeatureSet == null) {
                classFeatureSet = new ArrayList<BugCollection.ClassFeatures.ClassFeatureSet>();
            }
            return this.classFeatureSet;
        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="Feature" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "feature"
        })
        public static class ClassFeatureSet {

            @XmlElement(name = "Feature")
            protected List<BugCollection.ClassFeatures.ClassFeatureSet.Feature> feature;
            @XmlAttribute(name = "class", required = true)
            protected java.lang.String clazz;

            /**
             * Gets the value of the feature property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the feature property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getFeature().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link BugCollection.ClassFeatures.ClassFeatureSet.Feature }
             * 
             * 
             */
            public List<BugCollection.ClassFeatures.ClassFeatureSet.Feature> getFeature() {
                if (feature == null) {
                    feature = new ArrayList<BugCollection.ClassFeatures.ClassFeatureSet.Feature>();
                }
                return this.feature;
            }

            /**
             * Ruft den Wert der clazz-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getClazz() {
                return clazz;
            }

            /**
             * Legt den Wert der clazz-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setClazz(java.lang.String value) {
                this.clazz = value;
            }


            /**
             * <p>Java-Klasse für anonymous complex type.
             * 
             * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Feature {

                @XmlAttribute(name = "value", required = true)
                protected java.lang.String value;

                /**
                 * Ruft den Wert der value-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link java.lang.String }
                 *     
                 */
                public java.lang.String getValue() {
                    return value;
                }

                /**
                 * Legt den Wert der value-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link java.lang.String }
                 *     
                 */
                public void setValue(java.lang.String value) {
                    this.value = value;
                }

            }

        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="MissingClass" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="errors" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="missingClasses" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "missingClass"
    })
    public static class Errors {

        @XmlElement(name = "MissingClass")
        protected List<java.lang.String> missingClass;
        @XmlAttribute(name = "errors")
        @XmlSchemaType(name = "unsignedInt")
        protected Long errors;
        @XmlAttribute(name = "missingClasses")
        @XmlSchemaType(name = "unsignedInt")
        protected Long missingClasses;

        /**
         * Gets the value of the missingClass property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the missingClass property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMissingClass().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link java.lang.String }
         * 
         * 
         */
        public List<java.lang.String> getMissingClass() {
            if (missingClass == null) {
                missingClass = new ArrayList<java.lang.String>();
            }
            return this.missingClass;
        }

        /**
         * Ruft den Wert der errors-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getErrors() {
            return errors;
        }

        /**
         * Legt den Wert der errors-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setErrors(Long value) {
            this.errors = value;
        }

        /**
         * Ruft den Wert der missingClasses-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getMissingClasses() {
            return missingClasses;
        }

        /**
         * Legt den Wert der missingClasses-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setMissingClasses(Long value) {
            this.missingClasses = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="FileStats" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="bugCount" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                 &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                 &lt;attribute name="bugHash" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="PackageStats" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="ClassStats" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                           &lt;attribute name="sourceFile" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                           &lt;attribute name="interface" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *                           &lt;attribute name="size" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
     *                           &lt;attribute name="bugs" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                           &lt;attribute name="priority_1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                           &lt;attribute name="priority_2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                           &lt;attribute name="priority_3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="package" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="total_bugs" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                 &lt;attribute name="total_types" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                 &lt;attribute name="total_size" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
     *                 &lt;attribute name="priority_1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                 &lt;attribute name="priority_2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                 &lt;attribute name="priority_3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="FindBugsProfile" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="ClassProfile" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                           &lt;attribute name="totalMilliseconds" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                           &lt;attribute name="invocations" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                           &lt;attribute name="avgMicrosecondsPerInvocation" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                           &lt;attribute name="maxMicrosecondsPerInvocation" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                           &lt;attribute name="maxContext" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                           &lt;attribute name="standardDeviationMircosecondsPerInvocation" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="timestamp" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="total_classes" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="referenced_classes" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="total_bugs" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="total_size" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="num_packages" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="vm_version" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="cpu_seconds" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
     *       &lt;attribute name="clock_seconds" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
     *       &lt;attribute name="peak_mbytes" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
     *       &lt;attribute name="alloc_mbytes" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
     *       &lt;attribute name="gc_seconds" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
     *       &lt;attribute name="priority_1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="priority_2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="priority_3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "fileStats",
        "packageStats",
        "findBugsProfile"
    })
    public static class FindBugsSummary {

        @XmlElement(name = "FileStats")
        protected List<BugCollection.FindBugsSummary.FileStats> fileStats;
        @XmlElement(name = "PackageStats")
        protected List<BugCollection.FindBugsSummary.PackageStats> packageStats;
        @XmlElement(name = "FindBugsProfile")
        protected BugCollection.FindBugsSummary.FindBugsProfile findBugsProfile;
        @XmlAttribute(name = "timestamp", required = true)
        protected java.lang.String timestamp;
        @XmlAttribute(name = "total_classes", required = true)
        @XmlSchemaType(name = "unsignedInt")
        protected long totalClasses;
        @XmlAttribute(name = "referenced_classes")
        @XmlSchemaType(name = "unsignedInt")
        protected Long referencedClasses;
        @XmlAttribute(name = "total_bugs", required = true)
        @XmlSchemaType(name = "unsignedInt")
        protected long totalBugs;
        @XmlAttribute(name = "total_size", required = true)
        @XmlSchemaType(name = "unsignedInt")
        protected long totalSize;
        @XmlAttribute(name = "num_packages", required = true)
        @XmlSchemaType(name = "unsignedInt")
        protected long numPackages;
        @XmlAttribute(name = "vm_version")
        protected java.lang.String vmVersion;
        @XmlAttribute(name = "cpu_seconds")
        protected Float cpuSeconds;
        @XmlAttribute(name = "clock_seconds")
        protected Float clockSeconds;
        @XmlAttribute(name = "peak_mbytes")
        protected Float peakMbytes;
        @XmlAttribute(name = "alloc_mbytes")
        protected Float allocMbytes;
        @XmlAttribute(name = "gc_seconds")
        protected Float gcSeconds;
        @XmlAttribute(name = "priority_1")
        @XmlSchemaType(name = "unsignedInt")
        protected Long priority1;
        @XmlAttribute(name = "priority_2")
        @XmlSchemaType(name = "unsignedInt")
        protected Long priority2;
        @XmlAttribute(name = "priority_3")
        @XmlSchemaType(name = "unsignedInt")
        protected Long priority3;

        /**
         * Gets the value of the fileStats property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the fileStats property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFileStats().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BugCollection.FindBugsSummary.FileStats }
         * 
         * 
         */
        public List<BugCollection.FindBugsSummary.FileStats> getFileStats() {
            if (fileStats == null) {
                fileStats = new ArrayList<BugCollection.FindBugsSummary.FileStats>();
            }
            return this.fileStats;
        }

        /**
         * Gets the value of the packageStats property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the packageStats property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPackageStats().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BugCollection.FindBugsSummary.PackageStats }
         * 
         * 
         */
        public List<BugCollection.FindBugsSummary.PackageStats> getPackageStats() {
            if (packageStats == null) {
                packageStats = new ArrayList<BugCollection.FindBugsSummary.PackageStats>();
            }
            return this.packageStats;
        }

        /**
         * Ruft den Wert der findBugsProfile-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BugCollection.FindBugsSummary.FindBugsProfile }
         *     
         */
        public BugCollection.FindBugsSummary.FindBugsProfile getFindBugsProfile() {
            return findBugsProfile;
        }

        /**
         * Legt den Wert der findBugsProfile-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BugCollection.FindBugsSummary.FindBugsProfile }
         *     
         */
        public void setFindBugsProfile(BugCollection.FindBugsSummary.FindBugsProfile value) {
            this.findBugsProfile = value;
        }

        /**
         * Ruft den Wert der timestamp-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getTimestamp() {
            return timestamp;
        }

        /**
         * Legt den Wert der timestamp-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setTimestamp(java.lang.String value) {
            this.timestamp = value;
        }

        /**
         * Ruft den Wert der totalClasses-Eigenschaft ab.
         * 
         */
        public long getTotalClasses() {
            return totalClasses;
        }

        /**
         * Legt den Wert der totalClasses-Eigenschaft fest.
         * 
         */
        public void setTotalClasses(long value) {
            this.totalClasses = value;
        }

        /**
         * Ruft den Wert der referencedClasses-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getReferencedClasses() {
            return referencedClasses;
        }

        /**
         * Legt den Wert der referencedClasses-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setReferencedClasses(Long value) {
            this.referencedClasses = value;
        }

        /**
         * Ruft den Wert der totalBugs-Eigenschaft ab.
         * 
         */
        public long getTotalBugs() {
            return totalBugs;
        }

        /**
         * Legt den Wert der totalBugs-Eigenschaft fest.
         * 
         */
        public void setTotalBugs(long value) {
            this.totalBugs = value;
        }

        /**
         * Ruft den Wert der totalSize-Eigenschaft ab.
         * 
         */
        public long getTotalSize() {
            return totalSize;
        }

        /**
         * Legt den Wert der totalSize-Eigenschaft fest.
         * 
         */
        public void setTotalSize(long value) {
            this.totalSize = value;
        }

        /**
         * Ruft den Wert der numPackages-Eigenschaft ab.
         * 
         */
        public long getNumPackages() {
            return numPackages;
        }

        /**
         * Legt den Wert der numPackages-Eigenschaft fest.
         * 
         */
        public void setNumPackages(long value) {
            this.numPackages = value;
        }

        /**
         * Ruft den Wert der vmVersion-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getVmVersion() {
            return vmVersion;
        }

        /**
         * Legt den Wert der vmVersion-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setVmVersion(java.lang.String value) {
            this.vmVersion = value;
        }

        /**
         * Ruft den Wert der cpuSeconds-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getCpuSeconds() {
            return cpuSeconds;
        }

        /**
         * Legt den Wert der cpuSeconds-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setCpuSeconds(Float value) {
            this.cpuSeconds = value;
        }

        /**
         * Ruft den Wert der clockSeconds-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getClockSeconds() {
            return clockSeconds;
        }

        /**
         * Legt den Wert der clockSeconds-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setClockSeconds(Float value) {
            this.clockSeconds = value;
        }

        /**
         * Ruft den Wert der peakMbytes-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getPeakMbytes() {
            return peakMbytes;
        }

        /**
         * Legt den Wert der peakMbytes-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setPeakMbytes(Float value) {
            this.peakMbytes = value;
        }

        /**
         * Ruft den Wert der allocMbytes-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getAllocMbytes() {
            return allocMbytes;
        }

        /**
         * Legt den Wert der allocMbytes-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setAllocMbytes(Float value) {
            this.allocMbytes = value;
        }

        /**
         * Ruft den Wert der gcSeconds-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Float }
         *     
         */
        public Float getGcSeconds() {
            return gcSeconds;
        }

        /**
         * Legt den Wert der gcSeconds-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Float }
         *     
         */
        public void setGcSeconds(Float value) {
            this.gcSeconds = value;
        }

        /**
         * Ruft den Wert der priority1-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getPriority1() {
            return priority1;
        }

        /**
         * Legt den Wert der priority1-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setPriority1(Long value) {
            this.priority1 = value;
        }

        /**
         * Ruft den Wert der priority2-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getPriority2() {
            return priority2;
        }

        /**
         * Legt den Wert der priority2-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setPriority2(Long value) {
            this.priority2 = value;
        }

        /**
         * Ruft den Wert der priority3-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getPriority3() {
            return priority3;
        }

        /**
         * Legt den Wert der priority3-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setPriority3(Long value) {
            this.priority3 = value;
        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="bugCount" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *       &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *       &lt;attribute name="bugHash" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class FileStats {

            @XmlAttribute(name = "path", required = true)
            protected java.lang.String path;
            @XmlAttribute(name = "bugCount", required = true)
            @XmlSchemaType(name = "unsignedInt")
            protected long bugCount;
            @XmlAttribute(name = "size")
            @XmlSchemaType(name = "unsignedInt")
            protected Long size;
            @XmlAttribute(name = "bugHash")
            protected java.lang.String bugHash;

            /**
             * Ruft den Wert der path-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getPath() {
                return path;
            }

            /**
             * Legt den Wert der path-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setPath(java.lang.String value) {
                this.path = value;
            }

            /**
             * Ruft den Wert der bugCount-Eigenschaft ab.
             * 
             */
            public long getBugCount() {
                return bugCount;
            }

            /**
             * Legt den Wert der bugCount-Eigenschaft fest.
             * 
             */
            public void setBugCount(long value) {
                this.bugCount = value;
            }

            /**
             * Ruft den Wert der size-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Long }
             *     
             */
            public Long getSize() {
                return size;
            }

            /**
             * Legt den Wert der size-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Long }
             *     
             */
            public void setSize(Long value) {
                this.size = value;
            }

            /**
             * Ruft den Wert der bugHash-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getBugHash() {
                return bugHash;
            }

            /**
             * Legt den Wert der bugHash-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setBugHash(java.lang.String value) {
                this.bugHash = value;
            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="ClassProfile" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *                 &lt;attribute name="totalMilliseconds" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *                 &lt;attribute name="invocations" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *                 &lt;attribute name="avgMicrosecondsPerInvocation" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *                 &lt;attribute name="maxMicrosecondsPerInvocation" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *                 &lt;attribute name="maxContext" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *                 &lt;attribute name="standardDeviationMircosecondsPerInvocation" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "classProfile"
        })
        public static class FindBugsProfile {

            @XmlElement(name = "ClassProfile")
            protected List<BugCollection.FindBugsSummary.FindBugsProfile.ClassProfile> classProfile;

            /**
             * Gets the value of the classProfile property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the classProfile property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getClassProfile().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link BugCollection.FindBugsSummary.FindBugsProfile.ClassProfile }
             * 
             * 
             */
            public List<BugCollection.FindBugsSummary.FindBugsProfile.ClassProfile> getClassProfile() {
                if (classProfile == null) {
                    classProfile = new ArrayList<BugCollection.FindBugsSummary.FindBugsProfile.ClassProfile>();
                }
                return this.classProfile;
            }


            /**
             * <p>Java-Klasse für anonymous complex type.
             * 
             * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *       &lt;attribute name="totalMilliseconds" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
             *       &lt;attribute name="invocations" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
             *       &lt;attribute name="avgMicrosecondsPerInvocation" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
             *       &lt;attribute name="maxMicrosecondsPerInvocation" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
             *       &lt;attribute name="maxContext" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *       &lt;attribute name="standardDeviationMircosecondsPerInvocation" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class ClassProfile {

                @XmlAttribute(name = "name", required = true)
                protected java.lang.String name;
                @XmlAttribute(name = "totalMilliseconds", required = true)
                @XmlSchemaType(name = "unsignedInt")
                protected long totalMilliseconds;
                @XmlAttribute(name = "invocations", required = true)
                @XmlSchemaType(name = "unsignedInt")
                protected long invocations;
                @XmlAttribute(name = "avgMicrosecondsPerInvocation", required = true)
                @XmlSchemaType(name = "unsignedInt")
                protected long avgMicrosecondsPerInvocation;
                @XmlAttribute(name = "maxMicrosecondsPerInvocation")
                @XmlSchemaType(name = "unsignedInt")
                protected Long maxMicrosecondsPerInvocation;
                @XmlAttribute(name = "maxContext")
                protected java.lang.String maxContext;
                @XmlAttribute(name = "standardDeviationMircosecondsPerInvocation")
                @XmlSchemaType(name = "unsignedInt")
                protected Long standardDeviationMircosecondsPerInvocation;

                /**
                 * Ruft den Wert der name-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link java.lang.String }
                 *     
                 */
                public java.lang.String getName() {
                    return name;
                }

                /**
                 * Legt den Wert der name-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link java.lang.String }
                 *     
                 */
                public void setName(java.lang.String value) {
                    this.name = value;
                }

                /**
                 * Ruft den Wert der totalMilliseconds-Eigenschaft ab.
                 * 
                 */
                public long getTotalMilliseconds() {
                    return totalMilliseconds;
                }

                /**
                 * Legt den Wert der totalMilliseconds-Eigenschaft fest.
                 * 
                 */
                public void setTotalMilliseconds(long value) {
                    this.totalMilliseconds = value;
                }

                /**
                 * Ruft den Wert der invocations-Eigenschaft ab.
                 * 
                 */
                public long getInvocations() {
                    return invocations;
                }

                /**
                 * Legt den Wert der invocations-Eigenschaft fest.
                 * 
                 */
                public void setInvocations(long value) {
                    this.invocations = value;
                }

                /**
                 * Ruft den Wert der avgMicrosecondsPerInvocation-Eigenschaft ab.
                 * 
                 */
                public long getAvgMicrosecondsPerInvocation() {
                    return avgMicrosecondsPerInvocation;
                }

                /**
                 * Legt den Wert der avgMicrosecondsPerInvocation-Eigenschaft fest.
                 * 
                 */
                public void setAvgMicrosecondsPerInvocation(long value) {
                    this.avgMicrosecondsPerInvocation = value;
                }

                /**
                 * Ruft den Wert der maxMicrosecondsPerInvocation-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getMaxMicrosecondsPerInvocation() {
                    return maxMicrosecondsPerInvocation;
                }

                /**
                 * Legt den Wert der maxMicrosecondsPerInvocation-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setMaxMicrosecondsPerInvocation(Long value) {
                    this.maxMicrosecondsPerInvocation = value;
                }

                /**
                 * Ruft den Wert der maxContext-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link java.lang.String }
                 *     
                 */
                public java.lang.String getMaxContext() {
                    return maxContext;
                }

                /**
                 * Legt den Wert der maxContext-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link java.lang.String }
                 *     
                 */
                public void setMaxContext(java.lang.String value) {
                    this.maxContext = value;
                }

                /**
                 * Ruft den Wert der standardDeviationMircosecondsPerInvocation-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getStandardDeviationMircosecondsPerInvocation() {
                    return standardDeviationMircosecondsPerInvocation;
                }

                /**
                 * Legt den Wert der standardDeviationMircosecondsPerInvocation-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setStandardDeviationMircosecondsPerInvocation(Long value) {
                    this.standardDeviationMircosecondsPerInvocation = value;
                }

            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="ClassStats" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *                 &lt;attribute name="sourceFile" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *                 &lt;attribute name="interface" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
         *                 &lt;attribute name="size" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
         *                 &lt;attribute name="bugs" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *                 &lt;attribute name="priority_1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *                 &lt;attribute name="priority_2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *                 &lt;attribute name="priority_3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="package" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="total_bugs" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *       &lt;attribute name="total_types" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *       &lt;attribute name="total_size" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
         *       &lt;attribute name="priority_1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *       &lt;attribute name="priority_2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *       &lt;attribute name="priority_3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "classStats"
        })
        public static class PackageStats {

            @XmlElement(name = "ClassStats")
            protected List<BugCollection.FindBugsSummary.PackageStats.ClassStats> classStats;
            @XmlAttribute(name = "package", required = true)
            protected java.lang.String _package;
            @XmlAttribute(name = "total_bugs", required = true)
            @XmlSchemaType(name = "unsignedInt")
            protected long totalBugs;
            @XmlAttribute(name = "total_types", required = true)
            @XmlSchemaType(name = "unsignedInt")
            protected long totalTypes;
            @XmlAttribute(name = "total_size", required = true)
            @XmlSchemaType(name = "unsignedLong")
            protected BigInteger totalSize;
            @XmlAttribute(name = "priority_1")
            @XmlSchemaType(name = "unsignedInt")
            protected Long priority1;
            @XmlAttribute(name = "priority_2")
            @XmlSchemaType(name = "unsignedInt")
            protected Long priority2;
            @XmlAttribute(name = "priority_3")
            @XmlSchemaType(name = "unsignedInt")
            protected Long priority3;

            /**
             * Gets the value of the classStats property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the classStats property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getClassStats().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link BugCollection.FindBugsSummary.PackageStats.ClassStats }
             * 
             * 
             */
            public List<BugCollection.FindBugsSummary.PackageStats.ClassStats> getClassStats() {
                if (classStats == null) {
                    classStats = new ArrayList<BugCollection.FindBugsSummary.PackageStats.ClassStats>();
                }
                return this.classStats;
            }

            /**
             * Ruft den Wert der package-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getPackage() {
                return _package;
            }

            /**
             * Legt den Wert der package-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setPackage(java.lang.String value) {
                this._package = value;
            }

            /**
             * Ruft den Wert der totalBugs-Eigenschaft ab.
             * 
             */
            public long getTotalBugs() {
                return totalBugs;
            }

            /**
             * Legt den Wert der totalBugs-Eigenschaft fest.
             * 
             */
            public void setTotalBugs(long value) {
                this.totalBugs = value;
            }

            /**
             * Ruft den Wert der totalTypes-Eigenschaft ab.
             * 
             */
            public long getTotalTypes() {
                return totalTypes;
            }

            /**
             * Legt den Wert der totalTypes-Eigenschaft fest.
             * 
             */
            public void setTotalTypes(long value) {
                this.totalTypes = value;
            }

            /**
             * Ruft den Wert der totalSize-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getTotalSize() {
                return totalSize;
            }

            /**
             * Legt den Wert der totalSize-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setTotalSize(BigInteger value) {
                this.totalSize = value;
            }

            /**
             * Ruft den Wert der priority1-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Long }
             *     
             */
            public Long getPriority1() {
                return priority1;
            }

            /**
             * Legt den Wert der priority1-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Long }
             *     
             */
            public void setPriority1(Long value) {
                this.priority1 = value;
            }

            /**
             * Ruft den Wert der priority2-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Long }
             *     
             */
            public Long getPriority2() {
                return priority2;
            }

            /**
             * Legt den Wert der priority2-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Long }
             *     
             */
            public void setPriority2(Long value) {
                this.priority2 = value;
            }

            /**
             * Ruft den Wert der priority3-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Long }
             *     
             */
            public Long getPriority3() {
                return priority3;
            }

            /**
             * Legt den Wert der priority3-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Long }
             *     
             */
            public void setPriority3(Long value) {
                this.priority3 = value;
            }


            /**
             * <p>Java-Klasse für anonymous complex type.
             * 
             * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *       &lt;attribute name="sourceFile" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *       &lt;attribute name="interface" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
             *       &lt;attribute name="size" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
             *       &lt;attribute name="bugs" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
             *       &lt;attribute name="priority_1" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
             *       &lt;attribute name="priority_2" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
             *       &lt;attribute name="priority_3" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class ClassStats {

                @XmlAttribute(name = "class", required = true)
                protected java.lang.String clazz;
                @XmlAttribute(name = "sourceFile")
                protected java.lang.String sourceFile;
                @XmlAttribute(name = "interface", required = true)
                protected boolean _interface;
                @XmlAttribute(name = "size", required = true)
                @XmlSchemaType(name = "unsignedLong")
                protected BigInteger size;
                @XmlAttribute(name = "bugs", required = true)
                @XmlSchemaType(name = "unsignedInt")
                protected long bugs;
                @XmlAttribute(name = "priority_1")
                @XmlSchemaType(name = "unsignedInt")
                protected Long priority1;
                @XmlAttribute(name = "priority_2")
                @XmlSchemaType(name = "unsignedInt")
                protected Long priority2;
                @XmlAttribute(name = "priority_3")
                @XmlSchemaType(name = "unsignedInt")
                protected Long priority3;

                /**
                 * Ruft den Wert der clazz-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link java.lang.String }
                 *     
                 */
                public java.lang.String getClazz() {
                    return clazz;
                }

                /**
                 * Legt den Wert der clazz-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link java.lang.String }
                 *     
                 */
                public void setClazz(java.lang.String value) {
                    this.clazz = value;
                }

                /**
                 * Ruft den Wert der sourceFile-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link java.lang.String }
                 *     
                 */
                public java.lang.String getSourceFile() {
                    return sourceFile;
                }

                /**
                 * Legt den Wert der sourceFile-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link java.lang.String }
                 *     
                 */
                public void setSourceFile(java.lang.String value) {
                    this.sourceFile = value;
                }

                /**
                 * Ruft den Wert der interface-Eigenschaft ab.
                 * 
                 */
                public boolean isInterface() {
                    return _interface;
                }

                /**
                 * Legt den Wert der interface-Eigenschaft fest.
                 * 
                 */
                public void setInterface(boolean value) {
                    this._interface = value;
                }

                /**
                 * Ruft den Wert der size-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getSize() {
                    return size;
                }

                /**
                 * Legt den Wert der size-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setSize(BigInteger value) {
                    this.size = value;
                }

                /**
                 * Ruft den Wert der bugs-Eigenschaft ab.
                 * 
                 */
                public long getBugs() {
                    return bugs;
                }

                /**
                 * Legt den Wert der bugs-Eigenschaft fest.
                 * 
                 */
                public void setBugs(long value) {
                    this.bugs = value;
                }

                /**
                 * Ruft den Wert der priority1-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getPriority1() {
                    return priority1;
                }

                /**
                 * Legt den Wert der priority1-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setPriority1(Long value) {
                    this.priority1 = value;
                }

                /**
                 * Ruft den Wert der priority2-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getPriority2() {
                    return priority2;
                }

                /**
                 * Legt den Wert der priority2-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setPriority2(Long value) {
                    this.priority2 = value;
                }

                /**
                 * Ruft den Wert der priority3-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Long }
                 *     
                 */
                public Long getPriority3() {
                    return priority3;
                }

                /**
                 * Legt den Wert der priority3-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Long }
                 *     
                 */
                public void setPriority3(Long value) {
                    this.priority3 = value;
                }

            }

        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="AppVersion" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="sequence" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                 &lt;attribute name="timestamp" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
     *                 &lt;attribute name="release" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="codeSize" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *                 &lt;attribute name="numClasses" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "appVersion"
    })
    public static class History {

        @XmlElement(name = "AppVersion")
        protected List<BugCollection.History.AppVersion> appVersion;

        /**
         * Gets the value of the appVersion property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the appVersion property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAppVersion().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BugCollection.History.AppVersion }
         * 
         * 
         */
        public List<BugCollection.History.AppVersion> getAppVersion() {
            if (appVersion == null) {
                appVersion = new ArrayList<BugCollection.History.AppVersion>();
            }
            return this.appVersion;
        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="sequence" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *       &lt;attribute name="timestamp" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
         *       &lt;attribute name="release" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="codeSize" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *       &lt;attribute name="numClasses" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class AppVersion {

            @XmlAttribute(name = "sequence", required = true)
            @XmlSchemaType(name = "unsignedInt")
            protected long sequence;
            @XmlAttribute(name = "timestamp", required = true)
            @XmlSchemaType(name = "unsignedLong")
            protected BigInteger timestamp;
            @XmlAttribute(name = "release", required = true)
            protected java.lang.String release;
            @XmlAttribute(name = "codeSize", required = true)
            @XmlSchemaType(name = "unsignedInt")
            protected long codeSize;
            @XmlAttribute(name = "numClasses", required = true)
            @XmlSchemaType(name = "unsignedInt")
            protected long numClasses;

            /**
             * Ruft den Wert der sequence-Eigenschaft ab.
             * 
             */
            public long getSequence() {
                return sequence;
            }

            /**
             * Legt den Wert der sequence-Eigenschaft fest.
             * 
             */
            public void setSequence(long value) {
                this.sequence = value;
            }

            /**
             * Ruft den Wert der timestamp-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getTimestamp() {
                return timestamp;
            }

            /**
             * Legt den Wert der timestamp-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setTimestamp(BigInteger value) {
                this.timestamp = value;
            }

            /**
             * Ruft den Wert der release-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getRelease() {
                return release;
            }

            /**
             * Legt den Wert der release-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setRelease(java.lang.String value) {
                this.release = value;
            }

            /**
             * Ruft den Wert der codeSize-Eigenschaft ab.
             * 
             */
            public long getCodeSize() {
                return codeSize;
            }

            /**
             * Legt den Wert der codeSize-Eigenschaft fest.
             * 
             */
            public void setCodeSize(long value) {
                this.codeSize = value;
            }

            /**
             * Ruft den Wert der numClasses-Eigenschaft ab.
             * 
             */
            public long getNumClasses() {
                return numClasses;
            }

            /**
             * Legt den Wert der numClasses-Eigenschaft fest.
             * 
             */
            public void setNumClasses(long value) {
                this.numClasses = value;
            }

        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Jar" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="AuxClasspathEntry" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="SrcDir" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="WrkDir" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="Plugin" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="SuppressionFilter" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element ref="{}Matcher" maxOccurs="unbounded"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="Cloud" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="Property" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;simpleContent&gt;
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *                           &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                         &lt;/extension&gt;
     *                       &lt;/simpleContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="online" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *                 &lt;attribute name="synced" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *                 &lt;attribute name="detailsUrl" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="filepath" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="projectName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "jar",
        "auxClasspathEntry",
        "srcDir",
        "wrkDir",
        "plugin",
        "suppressionFilter",
        "cloud"
    })
    public static class Project {

        @XmlElement(name = "Jar")
        protected List<java.lang.String> jar;
        @XmlElement(name = "AuxClasspathEntry")
        protected List<java.lang.String> auxClasspathEntry;
        @XmlElement(name = "SrcDir")
        protected List<java.lang.String> srcDir;
        @XmlElement(name = "WrkDir")
        protected java.lang.String wrkDir;
        @XmlElement(name = "Plugin")
        protected List<BugCollection.Project.Plugin> plugin;
        @XmlElement(name = "SuppressionFilter")
        protected BugCollection.Project.SuppressionFilter suppressionFilter;
        @XmlElement(name = "Cloud")
        protected BugCollection.Project.Cloud cloud;
        @XmlAttribute(name = "filepath")
        protected java.lang.String filepath;
        @XmlAttribute(name = "projectName")
        protected java.lang.String projectName;

        /**
         * Gets the value of the jar property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the jar property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getJar().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link java.lang.String }
         * 
         * 
         */
        public List<java.lang.String> getJar() {
            if (jar == null) {
                jar = new ArrayList<java.lang.String>();
            }
            return this.jar;
        }

        /**
         * Gets the value of the auxClasspathEntry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the auxClasspathEntry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAuxClasspathEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link java.lang.String }
         * 
         * 
         */
        public List<java.lang.String> getAuxClasspathEntry() {
            if (auxClasspathEntry == null) {
                auxClasspathEntry = new ArrayList<java.lang.String>();
            }
            return this.auxClasspathEntry;
        }

        /**
         * Gets the value of the srcDir property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the srcDir property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSrcDir().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link java.lang.String }
         * 
         * 
         */
        public List<java.lang.String> getSrcDir() {
            if (srcDir == null) {
                srcDir = new ArrayList<java.lang.String>();
            }
            return this.srcDir;
        }

        /**
         * Ruft den Wert der wrkDir-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getWrkDir() {
            return wrkDir;
        }

        /**
         * Legt den Wert der wrkDir-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setWrkDir(java.lang.String value) {
            this.wrkDir = value;
        }

        /**
         * Gets the value of the plugin property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the plugin property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPlugin().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BugCollection.Project.Plugin }
         * 
         * 
         */
        public List<BugCollection.Project.Plugin> getPlugin() {
            if (plugin == null) {
                plugin = new ArrayList<BugCollection.Project.Plugin>();
            }
            return this.plugin;
        }

        /**
         * Ruft den Wert der suppressionFilter-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BugCollection.Project.SuppressionFilter }
         *     
         */
        public BugCollection.Project.SuppressionFilter getSuppressionFilter() {
            return suppressionFilter;
        }

        /**
         * Legt den Wert der suppressionFilter-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BugCollection.Project.SuppressionFilter }
         *     
         */
        public void setSuppressionFilter(BugCollection.Project.SuppressionFilter value) {
            this.suppressionFilter = value;
        }

        /**
         * Ruft den Wert der cloud-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link BugCollection.Project.Cloud }
         *     
         */
        public BugCollection.Project.Cloud getCloud() {
            return cloud;
        }

        /**
         * Legt den Wert der cloud-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link BugCollection.Project.Cloud }
         *     
         */
        public void setCloud(BugCollection.Project.Cloud value) {
            this.cloud = value;
        }

        /**
         * Ruft den Wert der filepath-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getFilepath() {
            return filepath;
        }

        /**
         * Legt den Wert der filepath-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setFilepath(java.lang.String value) {
            this.filepath = value;
        }

        /**
         * Ruft den Wert der projectName-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String }
         *     
         */
        public java.lang.String getProjectName() {
            return projectName;
        }

        /**
         * Legt den Wert der projectName-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String }
         *     
         */
        public void setProjectName(java.lang.String value) {
            this.projectName = value;
        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="Property" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;simpleContent&gt;
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
         *                 &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *               &lt;/extension&gt;
         *             &lt;/simpleContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="online" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
         *       &lt;attribute name="synced" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
         *       &lt;attribute name="detailsUrl" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "property"
        })
        public static class Cloud {

            @XmlElement(name = "Property")
            protected List<BugCollection.Project.Cloud.Property> property;
            @XmlAttribute(name = "id")
            protected java.lang.String id;
            @XmlAttribute(name = "online")
            protected Boolean online;
            @XmlAttribute(name = "synced")
            protected Boolean synced;
            @XmlAttribute(name = "detailsUrl")
            protected java.lang.String detailsUrl;

            /**
             * Gets the value of the property property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the property property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getProperty().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link BugCollection.Project.Cloud.Property }
             * 
             * 
             */
            public List<BugCollection.Project.Cloud.Property> getProperty() {
                if (property == null) {
                    property = new ArrayList<BugCollection.Project.Cloud.Property>();
                }
                return this.property;
            }

            /**
             * Ruft den Wert der id-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getId() {
                return id;
            }

            /**
             * Legt den Wert der id-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setId(java.lang.String value) {
                this.id = value;
            }

            /**
             * Ruft den Wert der online-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isOnline() {
                return online;
            }

            /**
             * Legt den Wert der online-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setOnline(Boolean value) {
                this.online = value;
            }

            /**
             * Ruft den Wert der synced-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isSynced() {
                return synced;
            }

            /**
             * Legt den Wert der synced-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setSynced(Boolean value) {
                this.synced = value;
            }

            /**
             * Ruft den Wert der detailsUrl-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getDetailsUrl() {
                return detailsUrl;
            }

            /**
             * Legt den Wert der detailsUrl-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setDetailsUrl(java.lang.String value) {
                this.detailsUrl = value;
            }


            /**
             * <p>Java-Klasse für anonymous complex type.
             * 
             * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;simpleContent&gt;
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
             *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *     &lt;/extension&gt;
             *   &lt;/simpleContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Property {

                @XmlValue
                protected java.lang.String value;
                @XmlAttribute(name = "key")
                protected java.lang.String key;

                /**
                 * Ruft den Wert der value-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link java.lang.String }
                 *     
                 */
                public java.lang.String getValue() {
                    return value;
                }

                /**
                 * Legt den Wert der value-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link java.lang.String }
                 *     
                 */
                public void setValue(java.lang.String value) {
                    this.value = value;
                }

                /**
                 * Ruft den Wert der key-Eigenschaft ab.
                 * 
                 * @return
                 *     possible object is
                 *     {@link java.lang.String }
                 *     
                 */
                public java.lang.String getKey() {
                    return key;
                }

                /**
                 * Legt den Wert der key-Eigenschaft fest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link java.lang.String }
                 *     
                 */
                public void setKey(java.lang.String value) {
                    this.key = value;
                }

            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Plugin {

            @XmlAttribute(name = "id")
            protected java.lang.String id;
            @XmlAttribute(name = "enabled")
            protected java.lang.String enabled;

            /**
             * Ruft den Wert der id-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getId() {
                return id;
            }

            /**
             * Legt den Wert der id-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setId(java.lang.String value) {
                this.id = value;
            }

            /**
             * Ruft den Wert der enabled-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String }
             *     
             */
            public java.lang.String getEnabled() {
                return enabled;
            }

            /**
             * Legt den Wert der enabled-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String }
             *     
             */
            public void setEnabled(java.lang.String value) {
                this.enabled = value;
            }

        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element ref="{}Matcher" maxOccurs="unbounded"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "matcher"
        })
        public static class SuppressionFilter {

            @XmlElementRef(name = "Matcher", type = JAXBElement.class)
            protected List<JAXBElement<? extends MatcherType>> matcher;

            /**
             * Gets the value of the matcher property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the matcher property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getMatcher().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link JAXBElement }{@code <}{@link OrMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link ClassMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link PackageMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link RankMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link AndMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link BugCodeMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link BugMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link FieldMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link NotMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link LastVersionMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link LocalMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link DesignationMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link FirstVersionMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link MatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link MatchMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link PriorityMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link MethodMatcherType }{@code >}
             * {@link JAXBElement }{@code <}{@link BugPatternMatcherType }{@code >}
             * 
             * 
             */
            public List<JAXBElement<? extends MatcherType>> getMatcher() {
                if (matcher == null) {
                    matcher = new ArrayList<JAXBElement<? extends MatcherType>>();
                }
                return this.matcher;
            }

        }

    }

}
