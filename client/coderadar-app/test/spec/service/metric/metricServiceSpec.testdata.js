MetricServiceSpec = {};

MetricServiceSpec.metrics = [
    {
        "id": "javaLoc",
        "displayName": "Java Lines of Code",
        "valuationType": "unvalued"
    },
    {
        "id": "xmlLoc",
        "displayName": "XML Lines of Code",
        "valuationType": "unvalued"
    },
    {
        "id": "htmlLoc",
        "displayName": "Html Lines of Code",
        "valuationType": "unvalued"
    },
    {
        "id": "javaCoverage",
        "displayName": "Java Test Coverage",
        "valuationType": "positive"
    },
    {
        "id": "javaViolations",
        "displayName": "Java Coding Violations",
        "valuationType": "negative"
    }
];

MetricServiceSpec.baselineCommitScore = {
    "javaLoc": 115000,
    "xmlLoc": 550,
    "htmlLoc": 400,
    "javaCoverage": 70.5,
    "javaViolations": 90
};

MetricServiceSpec.deltaCommitScore = {
    "javaLoc": 105000,
    "xmlLoc": 450,
    "htmlLoc": 800,
    "javaCoverage": 75.5,
    "javaViolations": 150
};