package org.wickedsource.coderadar.factories.databases;

public class DbUnitFactory {

  public static final String EMPTY = "/dbunit/empty.xml";

  public static class Projects {
    public static final String SINGLE_PROJECT = "/dbunit/project/singleProject.xml";
    public static final String SINGLE_PROJECT_2 = "/dbunit/project/singleProject2.xml";
    public static final String PROJECT_LIST = "/dbunit/project/projectList.xml";
  }

  public static class FilePatterns {
    public static final String SINGLE_PROJECT_WITH_FILEPATTERNS =
        "/dbunit/filepattern/singleProjectWithFilepatterns.xml";
  }

  public static class Commits {
    public static final String SINGLE_PROJECT_WITH_COMMITS =
        "/dbunit/commit/singleProjectWithCommits.xml";
  }

  public static class AnalyzingStrategies {
    public static final String SINGLE_PROJECT_WITH_ANALYZING_STRATEGY =
        "/dbunit/analyzingjob/singleProjectWithAnalyzingJob.xml";
  }

  public static class AnalyzerConfiguration {
    public static final String SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION =
        "/dbunit/analyzerconfiguration/singleProjectWithAnalyzerConfiguration.xml";
    public static final String SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION2 =
        "/dbunit/analyzerconfiguration/singleProjectWithAnalyzerConfiguration2.xml";
    public static final String SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION_LIST =
        "/dbunit/analyzerconfiguration/singleProjectWithAnalyzerConfigurationList.xml";
    public static final String SINGLE_PROJECT_WITH_ANALYZER_CONFIGURATION_FILE =
        "/dbunit/analyzerconfiguration/singleProjectWithAnalyzerConfigurationFile.xml";
  }

  public static class MetricValues {
    public static final String SINGLE_PROJECT_WITH_METRICS =
        "/dbunit/metric/singleProjectWithMetrics.xml";
    public static final String SINGLE_PROJECT_WITH_METRICS_AND_QUALITY_PROFILES =
        "/dbunit/metric/singleProjectWithMetricsAndQualityProfiles.xml";
  }

  public static class Modules {
    public static final String SINGLE_PROJECT_WITH_MODULE =
        "/dbunit/module/singleProjectWithModule.xml";
    public static final String SINGLE_PROJECT_WITH_MODULE2 =
        "/dbunit/module/singleProjectWithModule2.xml";
    public static final String SINGLE_PROJECT_WITH_MODULES =
        "/dbunit/module/singleProjectWithModules.xml";
    public static final String SINGLE_PROJECT_WITH_METRICS_AND_MODULES =
        "/dbunit/module/singleProjectWithMetricsAndModules.xml";
    public static final String MODULE_ASSOCIATION = "/dbunit/module/moduleAssociation.xml";
  }

  public static class QualityProfiles {
    public static final String SINGLE_PROJECT_WITH_QUALITY_PROFILE =
        "/dbunit/qualityprofile/singleProjectWithQualityProfile.xml";
    public static final String SINGLE_PROJECT_WITH_QUALITY_PROFILE2 =
        "/dbunit/qualityprofile/singleProjectWithQualityProfile2.xml";
    public static final String SINGLE_PROJECT_WITH_QUALITY_PROFILE3 =
        "/dbunit/qualityprofile/singleProjectWithQualityProfile3.xml";
    public static final String SINGLE_PROJECT_WITH_QUALITY_PROFILES =
        "/dbunit/qualityprofile/singleProjectWithQualityProfiles.xml";
  }

  public static class Users {

    public static final String USERS = "/dbunit/user/userList.xml";
  }

  public static class RefreshTokens {

    public static final String REFRESH_TOKENS = "/dbunit/refreshtoken/refreshTokenList.xml";
    public static final String REFRESH_STATIC_TOKENS =
        "/dbunit/refreshtoken/refreshTokenStaticList.xml";
  }

  public static class Jobs {
    public static final String SINGLE_PROJECT_WITH_WAITING_JOB =
        "/dbunit/job/singleProjectWithWaitingJob.xml";
  }

}
