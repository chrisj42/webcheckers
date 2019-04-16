<!DOCTYPE html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<meta http-equiv="refresh" content="5">
	<title>Web Checkers | Welcome!</title>
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

	<h1>Web Checkers | Welcome!</h1>

	<!-- Provide a navigation bar -->
	<#include "nav-bar.ftl">

	<div class="body">

		<!-- Provide a message to the user, if supplied. -->
		<#include "message.ftl">

		<#if currentUser??>
			<h2>Players Online</h2>
			<ul>
				<!-- check if there are players available; 1 player, the current one, will not be shown, so for there to be players available there has to be more than one. -->
				<#if lobby.playerCount < 2>
					<li>There are no other players available to play at this time.</li>
				<#else>
					<#--
					 This is where there needs to be a <#list> tag just like the ones in game.ftl; just use lobby instead of board, and only use one set of <#list> </#list> instead of nesting two of them.
					 Inside, just put the html for a single available player.
					 
					 Remember not to include the player if it matches the name of the current user.
						- This can be done by enclosing the player html in between the <#list> and </#list> tags inside a pair of <#if> and </#if> tags, where the if statement just compares the current player's name (in the iteration) to "currentUser.name".
					 -->
					<#list lobby.iterator() as player>
						<#if player.name != currentUser.name>
							<li>
								<form id="${player.name}" action="/" method="post">
									<#-- to make the input look like plain text, but still get sent in the form post, I'm making an input element with the data to send but display:none, and then a link element to display to the user and act as a submit button like nav-bar.ftl. -->
									<input name="viewMode" value="PLAY" style="display:none"/>
									<input name="opponent" value="${player.name}" style="display:none"/>
									<a href="#" onclick="event.preventDefault(); document.getElementById('${player.name}').submit();">${player.name} (${player.status})</a>
								</form>
							</li>
						</#if>
					</#list>
				</#if>
			</ul>
			
			<h2>Finished Games Available to Replay</h2>
			<#if archive.gameCount == 0>
				<ul>
					<li>No games have been completed yet.</li>
				</ul>
			<#else>
				<ol>
					<#list archive.iterator() as replay>
						<li>
							<form id="${replay.id}" action="/" method="post">
								<input name="viewMode" value="REPLAY" style="display:none"/>
								<input name="replay" value="${replay.id}" style="display:none"/>
								<a href="#" onclick="event.preventDefault(); document.getElementById('${replay.id}').submit();">${replay.title}</a>
							</form>
						</li>
					</#list>
				</ol>
			</#if>
		<#else>
			<#-- Here, display how many players are currently signed in. -->
			<li>Number of players logged in: ${lobby.playerCount}</li>
		</#if>

		<!-- TODO: future content on the Home:
				to start games, (done, more or less)
				spectating active games,
				or replay archived games
		-->


	</div>

</div>
</body>

</html>
