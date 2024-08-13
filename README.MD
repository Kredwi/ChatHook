# ChatHook - Sending Chat Messages to an External Server

## Description
- A simple plug-in that allows you to send messages to players in chat, to an external (back-end) server. It is well suited for creating a chat on a website or for Discord WebHooks.

## WARNING
**FOR THE PLUGIN, YOU NEED A BACKEND SERVER TO ACCEPT REQUESTS FROM THE PLUGIN**

## Features
- Available from version `1.12.2` to `1.21`
- The plug-in has the ability to extend the information sent to the server (thanks to *papi*).
- The list of players whose messages will not be sent to the back-end server can be configured.

## External Issues
- Streams do not finish their work when the reload command is used.
- If there is an infinite loop on the back-end, the stream will never close.
- Every new message in chat sends a request to the back-end server, which can overload it.
- In Spigot, when creating config.yml, for some reason most comments are deleted
- The plug-in does not differentiate between global and local chats.
- The plugin can't accept any data, it knows how to send it, so you won't be able to create a full-fledged chat

### Config Example
```yaml
# Author: Kredwi
# Issues: https://github.com/kredwi/chathook/issues
debug: false # Enable debug mode (true - enabled, false - disabled).
async: true # Use asynchronous requests, if disabled, chat messages can be sent with a significant delay
timeout: 5000 # Timeout in milliseconds, after which the connection will be closed.
request_url: "http://localhost:8439" # The address of the server to which requests will be sent.
authorization: "" # Authorization token, if not needed, leave the field empty
config-version: 1 # Config version

# Message sector
message_sector:
 error_invalid_command: "§cThis command does not exist"
 config_is_reloaded: "§aConfiguration successfully reloaded"
 no_arguments: "§cNot enough arguments"
 no_permissions: "§cYou do not have enough permissions"

# List of player nicknames whose messages will not be sent to the server
disabled_players:
 - Kredwi
 - igor

# Other information that will be sent in the request
other_information:
 key_1: "%player_ip%"
 key_2: "%player_name%"
```

### Data Example
With the configuration above, the request to the server will be:
```json
{
  "key_1": "127.0.0.1",
  "key_2": "Player Name",
  "uuid": "Player UUID",
  "name": "Player NAME",
  "content": "Test message",
  "worldName": "World"
}
```

**Note**: `key_1` and `key_2` it's custom keys from `config.yml`