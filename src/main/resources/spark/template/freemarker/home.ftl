<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl">

  <div class="body">

    <#if currentUser??>
      <!-- Provide a message to the user, if supplied. -->
          <#include "message.ftl">
      <p>
        <h1>Players Online</h1>
        <br> There are no other players available to play at this time
      </p>
    <#else>
        <!-- Provide a message to the user, if supplied. -->
            <#include "message.ftl">


    </#if>

    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

  </div>

</div>
</body>

</html>
