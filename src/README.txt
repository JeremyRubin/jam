README:

Start the server by running "WhiteboardServer --port <PORT>" with the desired port. (if running from eclipse, default is 4444, which is recommended)

Start as many clients as you wish by running ClientGUI, putting in your IP and selected server port.

Now you can click to draw, change whiteboards, colors, brush widths, erase, usernames, etc. To create a new board, switch whiteboards with blank input.


External Libraries:
We used the JSON.simple library https://code.google.com/p/json-simple/ for message passing.
We decided to use JSON as it simplified message passing and allowed us to communicate without
worrying about grammar specifically.