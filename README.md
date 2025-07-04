# Modded Health Display

A Minecraft mod for version 1.20.1 using Forge 47.4.0 that displays entity health above each entity in the world.

## Features

- **Real-time Health Display**: Shows current health above all living entities
- **Color-coded Health**: Health text changes color based on health percentage (red/orange/yellow/green)
- **Configurable Settings**: Customize what entities to show health for
- **Distance-based Scaling**: Health text scales based on distance from player
- **Toggle Keybinding**: Press 'H' to toggle health display on/off
- **Flexible Display Options**: Show health as numbers or percentages

## Configuration Options

The mod includes several configuration options accessible through the Forge config:

- **Enable/Disable**: Toggle the entire health display system
- **Maximum Distance**: Set how far away you can see entity health (1-256 blocks)
- **Show Players**: Toggle health display for other players
- **Show Hostile Mobs**: Toggle health display for monsters and hostile mobs
- **Show Passive Mobs**: Toggle health display for passive animals and creatures
- **Show Percentage**: Display health as percentage instead of numbers

## Controls

- **H Key**: Toggle health display on/off (can be changed in controls settings)

## Installation

1. Make sure you have Minecraft 1.20.1 and Forge 47.4.0 installed
2. Build the mod by running `./gradlew build` in the project directory
3. The built mod file will be in `build/libs/`
4. Copy the mod file to your Minecraft mods folder
5. Launch Minecraft with Forge

## Building from Source

1. Clone this repository
2. Open a command prompt in the project directory
3. Run `./gradlew build`
4. The built mod will be in `build/libs/moddedhealth-1.0.0.jar`

## Requirements

- Minecraft 1.20.1
- Forge 47.4.0 or higher
- Java 17 or higher (for development)

## License

This mod is released under the MIT License.