# Ceng453_TermProject_GroupMerge_frontend

**TODO:**

* Print the status messages, property owners, player balances on screen
* Create an endgame screen
* Maybe implement the "how to play" page into the game UI itself

**NOTES:**

* Change the /game/save function to save only one instance of the game. If we save two instances, who'll do the saving
  operation in multiplayer? Instead, in singleplayer, when game over, call /game/save twice (don't even need to do that
  if we don't intend to show AI on leaderboards, since leaderboards only checks "player" and not "opponent")
* While doing front-end, we need to handle the "you're logged in but your session token wasn't found on the server,
  bring up session timed out message" event.
* Currently, username is between 4 and 20 letters, and email is between 10 and 30 letters. We could bump those up, an
  @gmail.com already takes up 10 letters. NOTE: Do not forget to also increment the username field in the Game table.

**DONE:**

* Create game logic
* Create a login page and handle user authentication via the API
* Create a forgot password page
* Create the main menu
* Create a "how to play" page
* Create leaderboards page(s) and fill it with information from the API
* Create the game main page
* When game over, calculate the results and save the results via the API
