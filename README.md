<img src="http://wcm.io/images/favicon-16@2x.png"/> wcm.io Samples
======
[![Build Status](https://travis-ci.org/wcm-io/wcm-io-samples.png?branch=develop)](https://travis-ci.org/wcm-io/wcm-io-samples)

Sample projects and applications to demonstrate and test wcm.io features.

Documentation: http://wcm.io/samples/<br/>
Issues: https://wcm-io.atlassian.net/browse/WSAM<br/>
Wiki: https://wcm-io.atlassian.net/wiki/<br/>
Continuous Integration: https://travis-ci.org/wcm-io/wcm-io-samples/


### Build and deploy samples from source

If you want to build wcm.io from sources make sure you have configured all [Maven Repositories](http://wcm.io/maven.html) in your settings.xml.

See [Travis Maven settings.xml](https://github.com/wcm-io/wcm-io-samples/blob/master/.travis.maven-settings.xml) for an example with a full configuration.

- Clone the github repository https://github.com/wcm-io/wcm-io-samples
- Start an AEM author instance at http://localhost:4502
- Execute deploy script `clean_install_deploy_package.cmd` (on Windows) or `clean_install_deploy_package.sh` (on Mac/Linux) to deploy the application and sample content and configuration to the instance.
- Open http://localhost:4502/editor.html/content/wcm-io-samples/en.html in your browser


### System requirements

- AEM 6.0 SP1 or AEM 6.1
- JDK 1.8 (compile time), JDK 1.7 (run time)
