# Ceng453_TermProject_GroupMerge_frontend

**TODO:**

* Create a login page and handle user authentication via the API
* Create a forgot password page
* Create the main menu
* Create the game main page
* Create game logic
* When game over, calculate the results and save the results via the API

**NOTES (Copied from the backend repo):**

* While doing front-end, we need to handle the "you're logged in but your session token wasn't found on the server,
  bring up session timed out message" event.
* During front-end work, iterate over the Iterable fields via forEach to extract only the (player, player_score) fields.
* The EmailConfiguration.java file doesn't really add anything, however, it makes the sendEmail debug messages appear in
  the IntelliJ console while running from localhost. It should remain, for now, for debugging purposes, but we can
  remove it once we're done. (Also make sure that it's absolutely okay to remove that file, THEN remove.)
* Currently, username is between 4 and 20 letters, and email is between 10 and 30 letters. We could bump those up, an
  @gmail.com already takes up 10 letters. NOTE: Do not forget to also increment the username field in the Game table.

**DONE:**
