# Mitblick
The main repository for the Mitblick app

## Learning materials: 
[Beginner Angular Tutorial](https://www.freecodecamp.org/news/beaucarnes/angular-tutorial-course--OHbjepWjQ)

[Git beginner Tutorial](https://medium.freecodecamp.org/how-not-to-be-afraid-of-git-anymore-fe1da7415286)

## How to install the dev environment required to work on this app?

#### What software is required? 

- IntelliJ (IDE, register for the student version)

- Glassfish Payara 5 (our JavaEE server)

- MySQL Workbench (self explanatory)

- XAMPP (mysql server, you can choose others too, but this one is the easiest imo)

- Postman (for REST development)

- Node.js (for frontend, eg Angular and co.)

Google them and you will find them.

#### How do I download the code from here on my PC? What is GIT?
Git is a version control system (VCS) which allows more poeple to work on the same project without encountering many problems.

Normally, you would have to know commands to use it, but IntelliJ has your back. 

Just choose 'New ->Project from version control->Git' 
   
   -> Put in the link of this repo and click ok 

#### How to install the current project on your machine?

1.) Download and install all the software I listed above

2.) Add a poweruser to the db server (eg username: admin, password: admin)

**It needs a password** 

3.) Open the DB server, create a new DB, name it whatever, I used **mitblick**
##### This is how the glassfish config has to go, it's the hardest part:  
   - [Add it to IntelliJ](https://www.youtube.com/watch?v=7hNq06II-UM)
   - Once added, run it and go to it's admin console (by default [localhost:4848](http://localhost:4848/))  
   - follow [this tutorial](https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-glassfish-config.html)
        - you have to download the mysql jdbc connector jar from [here](https://dev.mysql.com/downloads/connector/j/8.0.html)
        - dont't put it in `glassfish-install-path\domains\domain-name\lib`, put it directly in the lib folder under the glassfish install (`glassfish-install-path\lib`)
        - When you create the JDBC Resource the JNDI name **has to be** "jdbc/mitblick-persistence"
    
4.) Run maven! 


Normally you would need to know commands to do this but again, IntelliJ has you back! 

Look at the sides of the IDE (left, right and down) and you will see something called  `Maven Projects`

Click on it. 

Choose `MitBlick Parent`

Choose `Lifecycle`

Choose `install`

#### What is maven?
Maven is a dependency management tool and helps us keep all our dependencies organized. 

It is already configured, so you don't need to worry to much about it unless you need to use other libraries.

**PLEASE ASK IF YOU NEED TO USE OTHER LIBRARIES**

