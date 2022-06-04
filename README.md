# Ceng453_TermProject_GroupMerge_frontend

**TODO:**

* Create leaderboards page(s) and fill it with information from the API
* Create the game main page
* Create game logic
* Create the cheat code
* When game over, calculate the results and save the results via the API
* Create a "how to play" page

**NOTES (Copied from the backend repo):**

* Change the /game/save function to save only one instance of the game. If we save two instances, who'll do the saving operation in multiplayer? Instead, in singleplayer, when game over, call /game/save twice (don't even need to do that if we don't intend to show AI on leaderboards, since leaderboards only checks "player" and not "opponent")
* While doing front-end, we need to handle the "you're logged in but your session token wasn't found on the server,
  bring up session timed out message" event.
* During front-end work, iterate over the Iterable fields via forEach to extract only the (player, player_score) fields.
* The EmailConfiguration.java file doesn't really add anything, however, it makes the sendEmail debug messages appear in
  the IntelliJ console while running from localhost. It should remain, for now, for debugging purposes, but we can
  remove it once we're done. (Also make sure that it's absolutely okay to remove that file, THEN remove.)
* Currently, username is between 4 and 20 letters, and email is between 10 and 30 letters. We could bump those up, an
  @gmail.com already takes up 10 letters. NOTE: Do not forget to also increment the username field in the Game table.

**DONE:**

* Create a login page and handle user authentication via the API
* Create a forgot password page
* Create the main menu
