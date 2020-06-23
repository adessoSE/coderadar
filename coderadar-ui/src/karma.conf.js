// Karma configuration file, see link for more information
// https://karma-runner.github.io/1.0/config/configuration-file.html

module.exports = function (config) {
  config.set({
    autoWatch: true,
    basePath: "",
    browsers: ["FirefoxHeadless"],
    client: {
      clearContext: false, // leave Jasmine Spec Runner output visible in browser
    },
    customLaunchers: {
      'FirefoxHeadless': {
        base: 'Firefox',
        flags: ['-headless']
      }
    },
    colors: true,
    coverageIstanbulReporter: {
      dir: require("path").join(__dirname, "../coverage/coderadar"),
      fixWebpackSourcePaths: true,
      reports: ["html", "lcovonly", "text-summary"],
    },
    frameworks: ["jasmine", "@angular-devkit/build-angular"],
    logLevel: config.LOG_INFO,
    plugins: [
      require("karma-jasmine"),
      require("karma-firefox-launcher"),
      require("karma-jasmine-html-reporter"),
      require("karma-coverage-istanbul-reporter"),
      require("@angular-devkit/build-angular/plugins/karma"),
    ],
    port: 9876,
    reporters: ["progress", "kjhtml"],
    singleRun: false,

  });
};
