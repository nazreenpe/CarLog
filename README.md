# CarLog

_Checkout the app demo video [here](https://github.com/nazreenpe/CarLog/raw/master/demo/CarLog.Nasreen.mp4)._

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

 The app can be viewed at https://getcarlog.herokuapp.com/
## Setup instructions
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

#### Set up the database with Postgres:

* createdb carlog

* Setup the following environmental variables:
  * DB_URL
  * DB_USER
  * DB_PASSWORD
  * AWS_SECRET_ACCESS_KEY
  * AWS_ACCESS_KEY_ID

#### Install frontend dependencies and build

* cd frontend

* npm install

* npm run-script build 


#### The backend of App can be build using gradle. Then run the app with the following command in the CLI, from the root project directory

* cd ..

* export the enivronmental variables (DB_URL, DB_USER, DB_PASSWORD, AWS_SECRET_ACCESS_KEY, AWS_ACCESS_KEY_ID)

* `./gradlew clean bootRun`

The app will be run in production mode at http://localhost:8080/
