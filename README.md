# CarLog

A Java Spring Boot web application which helps users to track their car maintenance data. 
Allows users to: 
 - store receipts and maintenance documents
 - get reminders for future car maintenance
 - generate maintenance reports. 
 
## Run the App
#### Install Java if it is not present in your mac
* Install Brew, if it is not present

`/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
`
* Brew tap

`# brew tap AdoptOpenJDK/openjdk`

* Install OpenJDK 10 Mac

`# brew cask install adoptopenjdk10`

* Check JDK Version

`# java -version`

* Java Location

`# which java`


#### The App can be build and run using gradle, with the following command in the CLI, from the root project directory

`./gradlew clean bootRun`
