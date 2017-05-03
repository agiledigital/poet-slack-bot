# POET-SLACK-BOT: Integrating SLACK with JIRA
---
## **POET is an intelligent Slack-Bot that knows more about your JIRA project than anybody else on the team!**

### Check out this [video](https://www.youtube.com/watch?v=Q3kgCzHBq7M&ab_channel=QinTian) to get an idea of the POET project. 

### Example
 
<img src="https://lh3.googleusercontent.com/AgAYYDDBw6IYN0CtirUlxy2IrlH4hVsyc3PIRudDBUya2xcwBx5mRj4ImO61DPcNlskP69syoi2afsdvbTlE9cua0n6r29fzUQMYbSpWR-_adCYpqfF0bGkrIM9f5OFsdAVru8qS0XwrIDERwirzf6mmbrhs4V3UEjNIricf4s75Ro4tMTgSOXneBn3XxcQLVMquPDcgIvzfkxhkzXOFD2Zd90xnuFZc8tFWBTzTzDIerTAUW8ubl7lvXLcQnEDv1pjZgDkODUsSzdlCHBWBMoDdOAslP60xXS8T8gfb2aRB1dlqOQciU9_tOTdI_CauqZEZeLE4_XIKLRl1lvq50zjmRypard4GVsFXstZIdtkgmcKsy5pDQbyGusAkuozjXPSoRP_xGdvWES9E9zPF4kY1FBpWO720yo6on_1IqFHSOtZq-KGhv_Pcy1lQLyHAy_kCImK2F2GACTsF-qjKFjS7PMdpw9oGM85T5xMsl2GtAsh9dweqwo_g34ol9QqeBEY6jYrGqD6FZIgRzCeJPuw4pmGS5XJCpmzE8AD5R2tbiMjixJy3WzySPE1Pb9P09EiNdaHTVCZKU0PTVuNF5W8KsFW3P0KD4k4O1xY4jICsbyI=w1459-h781-no">

### Motivation
This project was proposed and supported by [Agile Digital](https://agiledigital.com.au/) as part of the Australian National University's TechLauncher initiative.

### Preparation
---
#### Install the following packages
* Download [Activator](https://www.lightbend.com/activator/download) and add it to your PATH.

* Donwload and install [Node.js](https://nodejs.org/en/)

* Download and install [Docker](https://www.docker.com/).

#### Get a hubot API token from Slack for your team 
First, go to [the Slack Apps&Configurations for Hubot](https://slack.com/apps/A0F7XDU93-hubot) and sign to the Slack team you want our bot to be added to.

 <img width="500" alt="hubotconfiguration" src="https://cloud.githubusercontent.com/assets/20938140/25310144/e6efaf34-2820-11e7-807e-dea0a4dce7f4.jpg"> 
 
Then creat a new hubot configuration by giving the bot a name, then your own hubot-slack API token is generated.

 <img width="500" alt="addhubotconfiguration" src="https://cloud.githubusercontent.com/assets/20938140/25310179/0566bac4-2822-11e7-98c7-53d3b162f452.jpg"> 
 
Now you can invite the bot to the channel you want, but the bot is inactive and cannot speak at the moment. Follow the next steps to make it alive!

#### Set up a database to store questions
1. To host PSQL on local host using Docker(required to do only the first time)

   `docker run -p 5432:5432 --name postgres -e POSTGRES_PASSWORD=password -d postgres`

2. Then start the postgres before running POET

   `docker start postgres`

3. To alter the database

   `docker exec -it postgres bash`

4. To enter psql using -U postgres

   `psql -U postgres`

5. To create a database

   `postgres=# CREATE DATABASE database_name_here;`

6. To create a user

   `postgres=# Create USER user_name_here WITH PASSWORD 'user_password_here';`

7. To set or change the owner of the database
 
    `postgres=# ALTER DATABASE database_name_here OWNER TO user_name_here;`

#### Import POET to LUIS 

This part is for people who want to develop future features based on POET. If you just want to try the current features of POET, please skip it.

Import POET into [LUIS](https://www.luis.ai/home/index) and train it. A good tutorial can be found on [LUIS's homepage](https://www.luis.ai/home/index).

### Configuration
--- 
Set-up environment variables for your Atlassian JIRA instance:

1. JIRA\_USERNAME="JIRA username"
2. JIRA\_PASSWORD="JIRA password" (Your password will not be visible to other users or developers).
3. JIRA\_BASE\_URL="JIRA server domain"

Set-up environment variables for Postgress Database:

1. DB_HOST="database host"
2. DB_NAME="database name"
3. DB_USERNAME="database username"
4. DB_PASSWORD="database password"

Set-up environment variables for LUIS:

1. LUIS_URL="LUIS app url"
2. LUIS_APPID="LUIS app id"
3. LUIS_SUBSCRIPTION_KEY="LUIS subscription key"

### Installation
---
1. Create a new directory and do a git clone using command-line.

    ``git clone https://github.com/agiledigital/poet-slack-bot``

2. From your command-line, go to the server directory from the directory where you have cloned the project and run the activator by using the following line.

    ``activator run``

3. Go back the client directory of the directory where you have cloned the project and run the hubot using the following code. Make sure to replace YOUR_HUBOT_SLACK_API_TOKEN_HERE with the API token obtained in the previous step.

    `HUBOT_SLACK_TOKEN=YOUR_HUBOT_SLACK_API_TOKEN_HERE ./bin/hubot --adapter slack`

### Getting Started
---
After successfully running the client side and server side, the bot is now active in your channel. Let's have a conversation with it!

Here are some examples questions you can try asking the bot:

  - To Ask the bot for the description of a ticket in your project
    <img width="600" alt="ticketdescription" src="https://cloud.githubusercontent.com/assets/20938140/25305111/e08a5328-27b8-11e7-8534-9d3e3f4b32b3.jpg"> 
    <img width="600" alt="ticketdescription2" src="https://cloud.githubusercontent.com/assets/20938140/25305133/2cb627e0-27b9-11e7-9471-d6de63b19319.jpg"> 
    <img width="600" alt="ticketdescription3" src="https://cloud.githubusercontent.com/assets/20938140/25305163/a3d3d5de-27b9-11e7-8cef-ce841eda383a.jpg"> 
    <img width="600" alt="ticketdescription4" src="https://cloud.githubusercontent.com/assets/20938140/25305212/8af5b5fe-27ba-11e7-85eb-9eb2f0a48c68.jpg"> 
    <img width="600" alt="ticketdescription5" src="https://cloud.githubusercontent.com/assets/20938140/25305213/8bf8905c-27ba-11e7-91b4-cbf61dce9fef.jpg"> 
    <img width="600" alt="ticketdescription6" src="https://cloud.githubusercontent.com/assets/20938140/25305214/8d4f0558-27ba-11e7-98de-c607626334c8.jpg"> 
         
  - To Ask the bot for the assignee of a ticket in your project
    <img width="600" alt="assignee" src="https://cloud.githubusercontent.com/assets/20938140/25305260/4fa100a2-27bb-11e7-98e8-4ba8de615462.jpg"> 
    <img width="600" alt="assignee1" src="https://cloud.githubusercontent.com/assets/20938140/25305261/4fa4dc90-27bb-11e7-91b2-9ce128a7cb93.jpg"> 
    <img width="600" alt="assignee2" src="https://cloud.githubusercontent.com/assets/20938140/25305262/4fa8c616-27bb-11e7-8f02-4de26eae51af.jpg"> 
    <img width="600" alt="assignee3" src="https://cloud.githubusercontent.com/assets/20938140/25305263/4fb47768-27bb-11e7-8ed6-cf8948bfa979.jpg"> 
    <img width="600" alt="assignee4" src="https://cloud.githubusercontent.com/assets/20938140/25305264/4fb802fc-27bb-11e7-8c12-2547403f1b0d.jpg"> 

### Current Features
---

Here is a list of features that were already developed.

   1. Say hi to POET, then it will introduce itself to you.
   
<img width="400" alt="sayhi" src="https://cloud.githubusercontent.com/assets/20938140/25305387/a01f953c-27bd-11e7-97d3-e881e8b76f55.png">

   2. POET is now able to fetch three types of information of project from JIRA:
   
   
       1. To know the description of a ticket, there are various question forms the users can ask the bot. The example below shows the query about the description of a ticket, for example, POET-45, which is a ticket in POET project.
       
       
       <img width="400" alt="questionformsdescription1" src="https://cloud.githubusercontent.com/assets/20938140/25305389/aacc3526-27bd-11e7-88d2-4381f1af8a6b.png">
       
       
       <img width="400" alt="questionsformsdescription2" src="https://cloud.githubusercontent.com/assets/20938140/25305391/b0e5ac12-27bd-11e7-9911-ea64e67ce85d.png">
       
       
       <img width="400" alt="questionsformsdescription3" src="https://cloud.githubusercontent.com/assets/20938140/25305392/b11813d2-27bd-11e7-93b7-0ca7d506fc07.png"> 
       
        
       2. When it comes to the assignee of a ticket, there are also various forms of questions the users can ask the bot. The snapshot below demonstrates the query about the assignee of a ticket, for example, POET-5, which is aslo a ticket in POET project.
       
       
        <img width="400" alt="questionsformsassignee" src="https://cloud.githubusercontent.com/assets/20938140/25305401/c9bc9480-27bd-11e7-8baa-d52f4d3657bc.png">
        
        
       3. POET can also understand the query about the status of a ticket in various forms and fetch the status information about the ticket.
       
       <img width="400" alt="ticketstatus" src="https://cloud.githubusercontent.com/assets/20938140/25305402/d2d1c464-27bd-11e7-9e67-1f5083751442.png">
  
  
   3. POET is also able to fetch the tickets a person is currently working on from JIRA:
   
   <img width="400" alt="image" src="https://cloud.githubusercontent.com/assets/20938140/25660992/0efa1904-3052-11e7-80f9-1086c2f5cab6.png">
   
   4. When POET cannot understand a question or cannot find the information, it will reply the users.
   
   
   <img width="400" alt="cannotfindissu" src="https://cloud.githubusercontent.com/assets/20938140/25305408/e82b15c2-27bd-11e7-9b1c-01e3c12e6acb.png">
   
   <img width="400" alt="cannotunderstandissue" src="https://cloud.githubusercontent.com/assets/20938140/25305412/f14f655e-27bd-11e7-9c64-853bbde02ed1.png">
   
   
   5. POET is able to store all the questions the users asked in database, which will help improve it’s performance through later analysing and training with the dataset.
   
   
   <img width="400" alt="questionlist" src="https://cloud.githubusercontent.com/assets/20938140/25312816/a66086d8-2865-11e7-84a0-695218e8e8ef.jpg">


### Future Features
---

There are two steps to improve the intelligence of the bot:

1. Explore the JIRA Rest API to find out the information that can be fetched, e.g. the status of a ticket

2. Train the bot to make it understand various types of questions, e.g. what are the tickets in progress? A quick way to make the bot understand that is to import our POET-SLACK-BOT to [LUIS](https://www.luis.ai/home/index) and use LUIS to train it. Or you can update the decision trees for the bot.

After collecting the feedback, we sort out a list of features which are worth to develop in the future as shown below. Basically the features can be divided into two groups:

1. Incorporate more questions for fetching information from JIRA:

   i. _What are the tickets in progress/stalled/completed?_
 
   ii. _What tickets need testing?_
 
   iii. _What are the tickets I'm working on?_
 
   iv. _How long has the issue has been stalled?_
 
   v. _Show me the changes since yesterday._
 

2. Incorporate questions for modifying information on JIRA:

   i. _Please stall YOUR_TICKET_KEY_HERE_
 
   ii. _Please move YOUR_TICKET_KEY_HERE to completed_
 
   iii. _Can you resolve all sub-tasks of POET-1?_
 
   iv. _Please assign YOUR_TICKET_KEY_HERE to YOUR_TEAM_MEMBER_NAME_HERE._


### Contributors
We are a team of students from the Australian National University (ANU). We're passionate about harnessing the potential of artificial intelligence to make work and life better.

Original authors of the code in this package include (in alphabetical order):
Team Members:
- [Dongxu Li](https://www.linkedin.com/in/dongxu-li-a8a035110/)
- [Qin Tian](https://www.linkedin.com/in/qin-tian-38a655141/)
- [Sabina Pokhrel](https://np.linkedin.com/in/sabinapokhrel)
- Xu Wang

### Support
Contact poet-dev@hotmail.com.

### Acknowledgements
The authors would like to thank [Agile Digital](http://www.agiledigital.com.au/) for the proposal and guidance.

### Licence
This work is licensed under the Apache License Version 2.0 (APLv2).
