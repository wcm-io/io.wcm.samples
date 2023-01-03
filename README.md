<img src="https://wcm.io/images/favicon-16@2x.png"/> wcm.io Samples
======
[![Build](https://github.com/wcm-io/io.wcm.samples/workflows/Build/badge.svg?branch=develop)](https://github.com/wcm-io/io.wcm.samples/actions?query=workflow%3ABuild+branch%3Adevelop)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=wcm-io_io.wcm.samples&metric=coverage)](https://sonarcloud.io/summary/new_code?id=wcm-io_io.wcm.samples)

Sample projects and applications to demonstrate and test wcm.io features.

Documentation: https://wcm.io/samples/<br/>
Issues: https://github.com/wcm-io/io.wcm.samples/issues<br/>
Wiki: https://wcm-io.atlassian.net/wiki/<br/>
Continuous Integration: https://github.com/wcm-io/io.wcm.samples/actions<br/>
Commercial support: https://wcm.io/commercial-support.html


### Build and deploy samples from source

If you want to build wcm.io from sources make sure you have configured all [Maven Repositories](https://wcm.io/maven.html) in your settings.xml.

See [Maven Settings](https://github.com/wcm-io/io.wcm.samples/blob/develop/.maven-settings.xml) for an example with a full configuration.

- Clone the github repository https://github.com/wcm-io/io.wcm.samples
- Start an AEM author instance at http://localhost:4502
- Execute deploy script `build-deploy.sh` to deploy the application and sample content and configuration to the instance.
- Open http://localhost:4502/editor.html/content/wcm-io-samples/en.html in your browser


### System requirements

- AEM 6.5.7+ or AEMaaCS SDK
- Java 11
