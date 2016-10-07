// listen to the slack channel

(function () {

  var jiraIgnoreUsers = process.env.HUBOT_JIRA_ISSUES_IGNORE_USERS;

  module.exports = function (robot) {

    return robot.hear(/[A-Za-z] (.*)/i, function (res) {

      if (jiraIgnoreUsers && res.envelope.user.name.match(new RegExp(jiraIgnoreUsers, "gi"))) {
        return;
      }

      var message = res.match[1];

      httpRequest().doGET(message).then(function (response) {
        if (response) {
          if (response.status == "success") {
            return res.send("@" + res.envelope.user.name + " " + response.message);
          }

        }
      });

    });
  };

}).call(this);

///


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

