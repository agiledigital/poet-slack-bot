#!/bin/bash

slack_token=$1
ignore_users=$2

HUBOT_SLACK_TOKEN=$slack_token HUBOT_JIRA_ISSUES_IGNORE_USERS=$ignore_users ./bin/hubot --adapter slack
