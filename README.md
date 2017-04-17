# POET-SLACK-BOT: Integrating SLACK with JIRA
---
## **POET is an intelligent Slack-Bot that knows more about your JIRA project than anybody else on the team!**

### Example

<img src="https://lh3.googleusercontent.com/AgAYYDDBw6IYN0CtirUlxy2IrlH4hVsyc3PIRudDBUya2xcwBx5mRj4ImO61DPcNlskP69syoi2afsdvbTlE9cua0n6r29fzUQMYbSpWR-_adCYpqfF0bGkrIM9f5OFsdAVru8qS0XwrIDERwirzf6mmbrhs4V3UEjNIricf4s75Ro4tMTgSOXneBn3XxcQLVMquPDcgIvzfkxhkzXOFD2Zd90xnuFZc8tFWBTzTzDIerTAUW8ubl7lvXLcQnEDv1pjZgDkODUsSzdlCHBWBMoDdOAslP60xXS8T8gfb2aRB1dlqOQciU9_tOTdI_CauqZEZeLE4_XIKLRl1lvq50zjmRypard4GVsFXstZIdtkgmcKsy5pDQbyGusAkuozjXPSoRP_xGdvWES9E9zPF4kY1FBpWO720yo6on_1IqFHSOtZq-KGhv_Pcy1lQLyHAy_kCImK2F2GACTsF-qjKFjS7PMdpw9oGM85T5xMsl2GtAsh9dweqwo_g34ol9QqeBEY6jYrGqD6FZIgRzCeJPuw4pmGS5XJCpmzE8AD5R2tbiMjixJy3WzySPE1Pb9P09EiNdaHTVCZKU0PTVuNF5W8KsFW3P0KD4k4O1xY4jICsbyI=w1459-h781-no">

### Motivation
This project was proposed and supported by [Agile Digital](https://agiledigital.com.au/) as part of the Australian National University's TechLauncher initiative.

### Preparation
---
#### Install the following packages
- Download Activator from [Lightbend](https://www.lightbend.com/activator/download) and add Activator to your PATH.
For MAC, add this line to your .bash_profile file ``PATH=$PATH:/Users/yourname/~/ACTIVATOR_PACKAGE_NAME_HERE/bin``
- Donwload and install Node.js from [Node.js](https://nodejs.org/en/)

#### Get a hubot API token from Slack for your team 
1. Go to https://slack.com/apps/A0F7XDU93-hubot and sign to the Slack team you want our bot to be added to.
2. Creat a new hubot configuration, then your own hubot-slack API token is generated. You can give the bot a name.
3. Now you can invite the bot to the channel you want, but the bot is inactive and cannot speak at the moment. Follow the steps below to make it alive!

### Installation
---
1. Create a new directory and do a git clone 
``git clone https://github.com/agiledigital/poet-slack-bot``
2. Enter into the server directory of poet-slack-bot and run the activator 
``activator run``
3. Go back to poet-slack-bot directory, enter into the client directory and run the hubot
`HUBOT_SLACK_TOKEN=YOUR_HUBOT_SLACK_API_TOKEN_HERE ./bin/hubot --adapter slack`

### Configuration
--- 
Set-up environment variables for your Atlassian JIRA instance:

1. JIRA\_USERNAME = "JIRA username"
2. JIRA\_PASSWORD = "JIRA password" (Your password will not be visible to other users or developers).
3. JIRA\_BASE\_URL = "JIRA server domain"
4. JIRA\_ENDPOINT\_TICKET = "/rest/api/latest/issue/"

### Getting Started
---
After successfully running the client side and server side, the bot is now active in your channel. Let's have a conversation with it!

Here are some examples you can play with:

* To Ask the bot for the description of a ticket in your project 
            ``@your_bot_name What is the description of YOUR_TICKET_ID_HERE?`` 
            ``@your_bot_name What is YOUR_TICKET_ID_HERE?``
            ``@your_bot_name I want to know about YOUR_TICKET_ID_HERE?``
            ``@your_bot_name Tell me about YOUR_TICKET_ID_HERE.``
            ``@your_bot_name Description of YOUR_TICKET_ID_HERE.``
            ``@your_bot_name Describe YOUR_TICKET_ID_HERE?``
        
         
* To Ask the bot for the assignee of a ticket in your project
            ``@your_bot_name Who is the assignee of YOUR_TICKET_ID_HERE?`` 
            ``@your_bot_name Tell me th assignee of YOUR_TICKET_ID_HERE?``
            ``@your_bot_name Person working on YOUR_TICKET_ID_HERE?``
            ``@your_bot_name The assignee of YOUR_TICKET_ID_HERE.``
            ``@your_bot_name Who is working on YOUR_TICKET_ID_HERE.``

### Next Steps

There are two steps to improve the intelligence of the bot:

1. Explore the JIRA Rest API to find out the information that can be fetched, e.g. the status of a ticket

2. Train the bot to make it understand various types of questions, e.g. what are the tickets in progress? A quick way to make the bot understand that is to import our POET-SLACK-BOT to [LUIS](https://www.luis.ai/home/index) and use LUIS to train it. Or you can update the decision trees for the bot.

After collecting the feedback, we sort out a list of features which are worth to develop in the future as shown below. Basically the features can be divided into two groups:

1. Incorporate more questions for fetching information from JIRA:
    * ``What are the tickets in progress/stalled/completed?``   List all the tickets with the relevant status in JIRA.
    * ``What tickets need testing?``                            Simply list all the tickets whose status is 'Completed'.
    * ``What are the tickets I'm working on?``                  List all the in-progress tickets assgined to the asker
    * ``How long has the issue has been stalled?``              Calculate the duration according to the timeline of the transition activity
    * ``Show me the changes since yesterday.``                  List all the tickets whose status have changed since yesterday

2. Incorporate questions for modifying information on JIRA:
    * ``Please stall YOUR_TICKET_KEY_HERE``
    * ``Please move YOUR_TICKET_KEY_HERE to completed``
    * ``Can you resolve all sub-tasks of POET-1?``
    * ``Please assign YOUR_TICKET_KEY_HERE to YOUR_TEAM_MEMBER_NAME_HERE.``
   
        *There is a two-way communication function in Slack, which allows the users to make a further modification of their command.


### Contributors
We are a team of students from the Australian National University (ANU). We're passionate about harnessing the potential of artificial intelligence to make work and life better.

Original authors of the code in this package include (in alphabetical order):
Team Members:
- [Dongxu Li](https://www.linkedin.com/in/dongxu-li-a8a035110/)
- [Qin Tian](https://www.linkedin.com/in/qin-tian-38a655141/)
- [Sabina Pokhrel](https://np.linkedin.com/in/sabinapokhrel)
- Xu Wang

### Acknowledgements
The authors would like to thank [Agile Digital](http://www.agiledigital.com.au/) for the proposal and guidance.

To ask questions or report issues please post on Stack Overflow with the tag syntaxnet or open an issue on the tensorflow/models issues tracker. Please assign SyntaxNet issues to @calberti or @andorardo.

### Licence
This work is licensed under the Apache License Version 2.0 (APLv2).