# Pottullo - A Minecraft Privatization Plugin

## Overview

Pottullo is a Minecraft plugin that enables server players to privatize their land using lapis lazuli blocks. This plugin is useful for server owners who want to give ability to players to claim territory and control who can interact within their private zones.

Once a player has set up their private zone (PZ), they have exclusive access rights to build, destroy, and interact with. They can also add or remove other players as residents in their zone, giving these players the same access rights.

This plugin is built with the Paper library and should be compatible with any server running Paper.

## Usage

### Commands

The plugin adds several commands to the game, all starting with `/pz`. Here is a list of available commands:

- `/pz accept`: Accepts the currently selected area as your private zone (PZ). Place the lapis lazuli ore block (or another privatization block) in the center of the zone.

- `/pz help`: Provides a list of all Pottullo commands.

- `/pz info`: Provides information about the private zone you're currently in. The message includes the name of the zone owner and the coordinates of the zone.

- `/pz ares <player>`: Adds a player as a resident in your private zone. The player's name is case-sensitive and must be exact. Residents have the same rights as the owner in the private zone.

- `/pz rres <player>`: Removes a player from the list of residents in your private zone.

- `/pz remove`: Removes your private zone. Be careful! Once removed, a private zone cannot be restored.

- `/pz residents`: Lists all residents of your private zone.

## Installation

To install the plugin, download the latest release and place the `.jar` file into your server's `plugins` folder. Be careful to choose the correct version of Minecraft. Then restart your server, or load the plugin with a plugin manager.

## Configuring

Server administrators have the file `config.yml` in the plugin's directory. This file provides configurations for plugin language (see [Localization section](#localization)), privatization blocks (names as in the Material), and the radius of the private zone from the privatization block.
```
# Choose a language for plugin messages
# en - English
# uk - Ukrainian
language: 'en'

# Choose blocks to make them privatization blocks.
blocks:
  - LAPIS_ORE
  - DEEPSLATE_LAPIS_ORE

# Set the radius of private from the privatization block.
radius: 7
```

## Localization

Pottullo supports localization and comes with English and Ukrainian language files by default. You can set `language: 'en'` to use English or set `language: 'uk'` to use Ukrainian. If you want to add new languages - contribute the project! I will be happy to add new languages.

## Contributing

Contributions are welcome! If you want to help improve Pottullo, feel free to fork the repository and submit a pull request.
