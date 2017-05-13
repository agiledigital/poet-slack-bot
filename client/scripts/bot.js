// listen to the slack channel

(function () {

  var jiraIgnoreUsers = process.env.HUBOT_JIRA_ISSUES_IGNORE_USERS;

  module.exports = function (robot) {

    return robot.hear(/poet-slack-bot (.*)/i, function (res) {

      if (jiraIgnoreUsers && res.envelope.user.name.match(new RegExp(jiraIgnoreUsers, "gi"))) {
        return;
      }

      var message = res.match[1];

      httpRequest().doGET(message).then(function (response) {
        /*
        The response type is not checked here as we want the BOT
        to respond back to the user whenever a question is asked.
        */
        if (response) {
          if (response.status == "success") {
            // Wrap the message into 'attachments' so that slack channel could process it.
            msg =
            {
              "attachments": [
              {
                'title' : "@" + res.envelope.user.name,
                "text": response.message
              }
            ]
            }

            return res.send(msg);
          }
        }
      });

    });
  };

}).call(this);


// Connect to JIRA through httprequest

var httpRequest = function () {

  var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

  function doGET(question) {

    //Promise start
    return new Promise(function (resolve, reject) {

      var url = getBaseUrl() + '/question=' + question;
      var xhr = new XMLHttpRequest(url);
      xhr.open("GET", url, true);

      xhr.timeout = getTimeout();
      xhr.ontimeout = function () {
        console.log("Request to ", url, "timed out.")
      };

      xhr.onload = function (e) {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            resolve(JSON.parse(xhr.responseText));
          } else {
            reject(xhr.statusText);
          }
        }
      };

      xhr.onerror = function (e) {
        console.error(xhr.statusText);
      };
      xhr.send();

    });
    //Promise end

  }

  function getBaseUrl() {
    return 'http://localhost:9000';
  }

  function getTimeout() {
    return 15000;
  }

  return {
    doGET: doGET
  }
};

