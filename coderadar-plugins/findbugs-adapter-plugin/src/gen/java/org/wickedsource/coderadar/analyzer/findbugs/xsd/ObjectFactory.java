//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.01.24 um 09:29:42 AM CET 
//


package org.wickedsource.coderadar.analyzer.findbugs.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.wickedsource.coderadar.analyzer.findbugs.xsd package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Matcher_QNAME = new QName("", "Matcher");
    private final static QName _Message_QNAME = new QName("", "Message");
    private final static QName _Bug_QNAME = new QName("", "Bug");
    private final static QName _Class_QNAME = new QName("", "Class");
    private final static QName _FirstVersion_QNAME = new QName("", "FirstVersion");
    private final static QName _LastVersion_QNAME = new QName("", "LastVersion");
    private final static QName _Designation_QNAME = new QName("", "Designation");
    private final static QName _BugCode_QNAME = new QName("", "BugCode");
    private final static QName _Local_QNAME = new QName("", "Local");
    private final static QName _BugPattern_QNAME = new QName("", "BugPattern");
    private final static QName _Priority_QNAME = new QName("", "Priority");
    private final static QName _Rank_QNAME = new QName("", "Rank");
    private final static QName _Package_QNAME = new QName("", "Package");
    private final static QName _Method_QNAME = new QName("", "Method");
    private final static QName _Field_QNAME = new QName("", "Field");
    private final static QName _Or_QNAME = new QName("", "Or");
    private final static QName _And_QNAME = new QName("", "And");
    private final static QName _Match_QNAME = new QName("", "Match");
    private final static QName _Not_QNAME = new QName("", "Not");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.wickedsource.coderadar.analyzer.findbugs.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BugCollection }
     * 
     */
    public BugCollection createBugCollection() {
        return new BugCollection();
    }

    /**
     * Create an instance of {@link BugCollection.History }
     * 
     */
    public BugCollection.History createBugCollectionHistory() {
        return new BugCollection.History();
    }

    /**
     * Create an instance of {@link BugCollection.ClassFeatures }
     * 
     */
    public BugCollection.ClassFeatures createBugCollectionClassFeatures() {
        return new BugCollection.ClassFeatures();
    }

    /**
     * Create an instance of {@link BugCollection.ClassFeatures.ClassFeatureSet }
     * 
     */
    public BugCollection.ClassFeatures.ClassFeatureSet createBugCollectionClassFeaturesClassFeatureSet() {
        return new BugCollection.ClassFeatures.ClassFeatureSet();
    }

    /**
     * Create an instance of {@link BugCollection.FindBugsSummary }
     * 
     */
    public BugCollection.FindBugsSummary createBugCollectionFindBugsSummary() {
        return new BugCollection.FindBugsSummary();
    }

    /**
     * Create an instance of {@link BugCollection.FindBugsSummary.FindBugsProfile }
     * 
     */
    public BugCollection.FindBugsSummary.FindBugsProfile createBugCollectionFindBugsSummaryFindBugsProfile() {
        return new BugCollection.FindBugsSummary.FindBugsProfile();
    }

    /**
     * Create an instance of {@link BugCollection.FindBugsSummary.PackageStats }
     * 
     */
    public BugCollection.FindBugsSummary.PackageStats createBugCollectionFindBugsSummaryPackageStats() {
        return new BugCollection.FindBugsSummary.PackageStats();
    }

    /**
     * Create an instance of {@link BugCollection.BugInstance }
     * 
     */
    public BugCollection.BugInstance createBugCollectionBugInstance() {
        return new BugCollection.BugInstance();
    }

    /**
     * Create an instance of {@link BugCollection.Project }
     * 
     */
    public BugCollection.Project createBugCollectionProject() {
        return new BugCollection.Project();
    }

    /**
     * Create an instance of {@link BugCollection.Project.Cloud }
     * 
     */
    public BugCollection.Project.Cloud createBugCollectionProjectCloud() {
        return new BugCollection.Project.Cloud();
    }

    /**
     * Create an instance of {@link FindBugsFilter }
     * 
     */
    public FindBugsFilter createFindBugsFilter() {
        return new FindBugsFilter();
    }

    /**
     * Create an instance of {@link BugCollection.BugCategory }
     * 
     */
    public BugCollection.BugCategory createBugCollectionBugCategory() {
        return new BugCollection.BugCategory();
    }

    /**
     * Create an instance of {@link BugCollection.BugPattern }
     * 
     */
    public BugCollection.BugPattern createBugCollectionBugPattern() {
        return new BugCollection.BugPattern();
    }

    /**
     * Create an instance of {@link BugCollection.BugCode }
     * 
     */
    public BugCollection.BugCode createBugCollectionBugCode() {
        return new BugCollection.BugCode();
    }

    /**
     * Create an instance of {@link BugCollection.Errors }
     * 
     */
    public BugCollection.Errors createBugCollectionErrors() {
        return new BugCollection.Errors();
    }

    /**
     * Create an instance of {@link SourceLine }
     * 
     */
    public SourceLine createSourceLine() {
        return new SourceLine();
    }

    /**
     * Create an instance of {@link BugMatcherType }
     * 
     */
    public BugMatcherType createBugMatcherType() {
        return new BugMatcherType();
    }

    /**
     * Create an instance of {@link ClassMatcherType }
     * 
     */
    public ClassMatcherType createClassMatcherType() {
        return new ClassMatcherType();
    }

    /**
     * Create an instance of {@link FirstVersionMatcherType }
     * 
     */
    public FirstVersionMatcherType createFirstVersionMatcherType() {
        return new FirstVersionMatcherType();
    }

    /**
     * Create an instance of {@link LastVersionMatcherType }
     * 
     */
    public LastVersionMatcherType createLastVersionMatcherType() {
        return new LastVersionMatcherType();
    }

    /**
     * Create an instance of {@link DesignationMatcherType }
     * 
     */
    public DesignationMatcherType createDesignationMatcherType() {
        return new DesignationMatcherType();
    }

    /**
     * Create an instance of {@link BugCodeMatcherType }
     * 
     */
    public BugCodeMatcherType createBugCodeMatcherType() {
        return new BugCodeMatcherType();
    }

    /**
     * Create an instance of {@link LocalMatcherType }
     * 
     */
    public LocalMatcherType createLocalMatcherType() {
        return new LocalMatcherType();
    }

    /**
     * Create an instance of {@link BugPatternMatcherType }
     * 
     */
    public BugPatternMatcherType createBugPatternMatcherType() {
        return new BugPatternMatcherType();
    }

    /**
     * Create an instance of {@link PriorityMatcherType }
     * 
     */
    public PriorityMatcherType createPriorityMatcherType() {
        return new PriorityMatcherType();
    }

    /**
     * Create an instance of {@link RankMatcherType }
     * 
     */
    public RankMatcherType createRankMatcherType() {
        return new RankMatcherType();
    }

    /**
     * Create an instance of {@link PackageMatcherType }
     * 
     */
    public PackageMatcherType createPackageMatcherType() {
        return new PackageMatcherType();
    }

    /**
     * Create an instance of {@link MethodMatcherType }
     * 
     */
    public MethodMatcherType createMethodMatcherType() {
        return new MethodMatcherType();
    }

    /**
     * Create an instance of {@link FieldMatcherType }
     * 
     */
    public FieldMatcherType createFieldMatcherType() {
        return new FieldMatcherType();
    }

    /**
     * Create an instance of {@link OrMatcherType }
     * 
     */
    public OrMatcherType createOrMatcherType() {
        return new OrMatcherType();
    }

    /**
     * Create an instance of {@link AndMatcherType }
     * 
     */
    public AndMatcherType createAndMatcherType() {
        return new AndMatcherType();
    }

    /**
     * Create an instance of {@link MatchMatcherType }
     * 
     */
    public MatchMatcherType createMatchMatcherType() {
        return new MatchMatcherType();
    }

    /**
     * Create an instance of {@link NotMatcherType }
     * 
     */
    public NotMatcherType createNotMatcherType() {
        return new NotMatcherType();
    }

    /**
     * Create an instance of {@link BugCollection.History.AppVersion }
     * 
     */
    public BugCollection.History.AppVersion createBugCollectionHistoryAppVersion() {
        return new BugCollection.History.AppVersion();
    }

    /**
     * Create an instance of {@link BugCollection.ClassFeatures.ClassFeatureSet.Feature }
     * 
     */
    public BugCollection.ClassFeatures.ClassFeatureSet.Feature createBugCollectionClassFeaturesClassFeatureSetFeature() {
        return new BugCollection.ClassFeatures.ClassFeatureSet.Feature();
    }

    /**
     * Create an instance of {@link BugCollection.FindBugsSummary.FileStats }
     * 
     */
    public BugCollection.FindBugsSummary.FileStats createBugCollectionFindBugsSummaryFileStats() {
        return new BugCollection.FindBugsSummary.FileStats();
    }

    /**
     * Create an instance of {@link BugCollection.FindBugsSummary.FindBugsProfile.ClassProfile }
     * 
     */
    public BugCollection.FindBugsSummary.FindBugsProfile.ClassProfile createBugCollectionFindBugsSummaryFindBugsProfileClassProfile() {
        return new BugCollection.FindBugsSummary.FindBugsProfile.ClassProfile();
    }

    /**
     * Create an instance of {@link BugCollection.FindBugsSummary.PackageStats.ClassStats }
     * 
     */
    public BugCollection.FindBugsSummary.PackageStats.ClassStats createBugCollectionFindBugsSummaryPackageStatsClassStats() {
        return new BugCollection.FindBugsSummary.PackageStats.ClassStats();
    }

    /**
     * Create an instance of {@link BugCollection.BugInstance.Class }
     * 
     */
    public BugCollection.BugInstance.Class createBugCollectionBugInstanceClass() {
        return new BugCollection.BugInstance.Class();
    }

    /**
     * Create an instance of {@link BugCollection.BugInstance.Type }
     * 
     */
    public BugCollection.BugInstance.Type createBugCollectionBugInstanceType() {
        return new BugCollection.BugInstance.Type();
    }

    /**
     * Create an instance of {@link BugCollection.BugInstance.Method }
     * 
     */
    public BugCollection.BugInstance.Method createBugCollectionBugInstanceMethod() {
        return new BugCollection.BugInstance.Method();
    }

    /**
     * Create an instance of {@link BugCollection.BugInstance.LocalVariable }
     * 
     */
    public BugCollection.BugInstance.LocalVariable createBugCollectionBugInstanceLocalVariable() {
        return new BugCollection.BugInstance.LocalVariable();
    }

    /**
     * Create an instance of {@link BugCollection.BugInstance.Field }
     * 
     */
    public BugCollection.BugInstance.Field createBugCollectionBugInstanceField() {
        return new BugCollection.BugInstance.Field();
    }

    /**
     * Create an instance of {@link BugCollection.BugInstance.Int }
     * 
     */
    public BugCollection.BugInstance.Int createBugCollectionBugInstanceInt() {
        return new BugCollection.BugInstance.Int();
    }

    /**
     * Create an instance of {@link BugCollection.BugInstance.String }
     * 
     */
    public BugCollection.BugInstance.String createBugCollectionBugInstanceString() {
        return new BugCollection.BugInstance.String();
    }

    /**
     * Create an instance of {@link BugCollection.BugInstance.Property }
     * 
     */
    public BugCollection.BugInstance.Property createBugCollectionBugInstanceProperty() {
        return new BugCollection.BugInstance.Property();
    }

    /**
     * Create an instance of {@link BugCollection.BugInstance.UserAnnotation }
     * 
     */
    public BugCollection.BugInstance.UserAnnotation createBugCollectionBugInstanceUserAnnotation() {
        return new BugCollection.BugInstance.UserAnnotation();
    }

    /**
     * Create an instance of {@link BugCollection.Project.Plugin }
     * 
     */
    public BugCollection.Project.Plugin createBugCollectionProjectPlugin() {
        return new BugCollection.Project.Plugin();
    }

    /**
     * Create an instance of {@link BugCollection.Project.SuppressionFilter }
     * 
     */
    public BugCollection.Project.SuppressionFilter createBugCollectionProjectSuppressionFilter() {
        return new BugCollection.Project.SuppressionFilter();
    }

    /**
     * Create an instance of {@link BugCollection.Project.Cloud.Property }
     * 
     */
    public BugCollection.Project.Cloud.Property createBugCollectionProjectCloudProperty() {
        return new BugCollection.Project.Cloud.Property();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Matcher")
    public JAXBElement<MatcherType> createMatcher(MatcherType value) {
        return new JAXBElement<MatcherType>(_Matcher_QNAME, MatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link java.lang.String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Message")
    public JAXBElement<java.lang.String> createMessage(java.lang.String value) {
        return new JAXBElement<java.lang.String>(_Message_QNAME, java.lang.String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BugMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Bug", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<BugMatcherType> createBug(BugMatcherType value) {
        return new JAXBElement<BugMatcherType>(_Bug_QNAME, BugMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClassMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Class", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<ClassMatcherType> createClass(ClassMatcherType value) {
        return new JAXBElement<ClassMatcherType>(_Class_QNAME, ClassMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FirstVersionMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FirstVersion", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<FirstVersionMatcherType> createFirstVersion(FirstVersionMatcherType value) {
        return new JAXBElement<FirstVersionMatcherType>(_FirstVersion_QNAME, FirstVersionMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LastVersionMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "LastVersion", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<LastVersionMatcherType> createLastVersion(LastVersionMatcherType value) {
        return new JAXBElement<LastVersionMatcherType>(_LastVersion_QNAME, LastVersionMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DesignationMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Designation", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<DesignationMatcherType> createDesignation(DesignationMatcherType value) {
        return new JAXBElement<DesignationMatcherType>(_Designation_QNAME, DesignationMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BugCodeMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "BugCode", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<BugCodeMatcherType> createBugCode(BugCodeMatcherType value) {
        return new JAXBElement<BugCodeMatcherType>(_BugCode_QNAME, BugCodeMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LocalMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Local", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<LocalMatcherType> createLocal(LocalMatcherType value) {
        return new JAXBElement<LocalMatcherType>(_Local_QNAME, LocalMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BugPatternMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "BugPattern", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<BugPatternMatcherType> createBugPattern(BugPatternMatcherType value) {
        return new JAXBElement<BugPatternMatcherType>(_BugPattern_QNAME, BugPatternMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PriorityMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Priority", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<PriorityMatcherType> createPriority(PriorityMatcherType value) {
        return new JAXBElement<PriorityMatcherType>(_Priority_QNAME, PriorityMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RankMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Rank", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<RankMatcherType> createRank(RankMatcherType value) {
        return new JAXBElement<RankMatcherType>(_Rank_QNAME, RankMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PackageMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Package", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<PackageMatcherType> createPackage(PackageMatcherType value) {
        return new JAXBElement<PackageMatcherType>(_Package_QNAME, PackageMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethodMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Method", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<MethodMatcherType> createMethod(MethodMatcherType value) {
        return new JAXBElement<MethodMatcherType>(_Method_QNAME, MethodMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FieldMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Field", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<FieldMatcherType> createField(FieldMatcherType value) {
        return new JAXBElement<FieldMatcherType>(_Field_QNAME, FieldMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Or", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<OrMatcherType> createOr(OrMatcherType value) {
        return new JAXBElement<OrMatcherType>(_Or_QNAME, OrMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AndMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "And", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<AndMatcherType> createAnd(AndMatcherType value) {
        return new JAXBElement<AndMatcherType>(_And_QNAME, AndMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Match", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<MatchMatcherType> createMatch(MatchMatcherType value) {
        return new JAXBElement<MatchMatcherType>(_Match_QNAME, MatchMatcherType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Not", substitutionHeadNamespace = "", substitutionHeadName = "Matcher")
    public JAXBElement<NotMatcherType> createNot(NotMatcherType value) {
        return new JAXBElement<NotMatcherType>(_Not_QNAME, NotMatcherType.class, null, value);
    }

}
