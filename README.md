# SCS Map Mod Packer
`SCS Map Mod Packer` (SCSMMP) is an application for packaging map mods created with ETS2 (and probably ATS, but not tested) map editor. Allows to generate `.scs` mod 
files that can be activated in the game to test or release a map mod.

## How to use
SCSMMP can be used in 2 differents ways:

### Via GUI
When running the application in a normal way, the following user interface will appear. In it, you only have to indicate the 3 required fields and click on `Start`. 
Every time you want to test or launch the map mod, you must run the packaging process by pressing the `Start` button so that it updates the `.scs` file generating a 
new one.

![SCSMMP main view](https://github.com/dprietob/SCS-Map-Mod-Packer/blob/master/screenshots/SCSMMP.png "SCSMMP main view")

#### GUI fields:
- **Name:** it's the map mod name. It's the same that the `map/map_name.mbd` filename.
- **Input:** it's the edited map directory. By default is `C:\Program Files\Steam\steamapps\common\Euro Truck Simulator 2\base_map\map` at Windows OS.
- **Output:** it's the `.scs` wrapped file output directory. Can be any directory, but can be setted like `C:\Users\<user>\Documents\Euro Truck Simulator 2\mod` to can be loaded automatically.
- **Create old .scs backup:** if it's checked, a backup file will be created for the old .scs file with the same name as the current placed in the same output directory.

### Via CLI
With `.jar` option, you can also run SCSMMP via CLI to automate the packaging process using macros or another method that allows you to run console commands. You can use some basic 
commands line like `--help` or `--version`, but the main use only requires three required arguments.

#### CLI examples:

- `java -jar scs-map-mod-packer<version>.jar "map_mod_name" "map_mod_directory" "output_directory"`
- `java -jar scs-map-mod-packer<version>.jar --o "map_mod_name" "map_mod_directory" "output_directory"`

#### CLI arguments:

- **map_mod_name:** it's the map mod name. It's the same that the `map/map_name.mbd` filename.
- **map_mod_directory:** it's the edited map directory. By default is `C:\Program Files\Steam\steamapps\common\Euro Truck Simulator 2\base_map\map` at Windows OS.
- **output_directory:** it's the `.scs` wrapped file output directory. Can be any directory, but can be setted like `C:\Users\<user>\Documents\Euro Truck Simulator 2\mod` to can be loaded automatically.
- **--o:** overwrites the .scs output file in case there is one in the output directory. If this command is ommited, a backup file will be created for the old .scs file with the same name as the current placed in the same output directory.

Finally, you can configure the CLASSPATH environment variable to include the SCSMMP ubication directory to use it globally.

