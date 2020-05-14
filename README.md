# CarLog

A Java Spring Boot and React JS web application which helps users to track their car maintenance data. 
Allows users to: 
 - Add and track cars
 - Keep maintenance records
 - Record specific maintenance activities
 - Store and view documents (Receipts, photos etc)
 - Generate PDF report of the full maintenance history of cars
 
 ## Stacks Used
 - Java Spring Boot on BackEnd
 - React JS on Frontend
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
