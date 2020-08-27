/*
 * Copyright (C) 2020 The SCSMMP Author
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.scsmmp;

import com.scsmmp.interfaces.ProcessUpdaterListener;

/**
 * Class that defines the Terminal entity, in charge to execute
 * the application via command line.
 *
 * @author Daniel Prieto
 */
public class Terminal
{
    private final int MSG_DEFAULT = 1;
    private final int MSG_ERROR = 2;
    private final int MSG_INFO = 3;
    private final int MSG_SUCCESS = 4;

    private String[] args;
    private String name;
    private String version;
    private Mod mod;

    /**
     * Instantiates a new Terminal object with the receiving params.
     *
     * @param arrArgs
     * @param sName
     * @param sVersion
     */
    public Terminal(String[] arrArgs, String sName, String sVersion)
    {
        args = arrArgs;
        name = sName;
        version = sVersion;
    }

    /**
     * Starts the CLI execution.
     */
    public void start()
    {
        showMessage(MSG_DEFAULT, false, name + " v" + version);

        if (args.length == 1) {
            recognizeCommand();

        } else if (args.length == 3) {
            mod = new Mod(args[0], args[1], args[2]);

        } else if (args.length == 4) {
            if (args[0].equals("--o")) {
                mod = new Mod(args[1], args[2], args[3]);
                mod.setCreateBackup(false);
            } else {
                showMessage(MSG_ERROR, true, "'" + args[0] + "' command is not available. Please, see 'java -jar scs-map-mod-packer<version>.jar --help'.");
                showMessage(MSG_ERROR, false, "Process finished with errors!");
            }
        } else {
            showMessage(MSG_ERROR, true, "Argument list is wrong. Please, see 'java -jar scs-map-mod-packer<version>.jar --help'.");
            showMessage(MSG_ERROR, false, "Process finished with errors!");
        }

        if (mod != null) {
            initWrappingProcess();
        }
    }

    /**
     * Initializes the wrapping process through CLI.
     */
    private void initWrappingProcess()
    {
        showMessage(MSG_INFO, false, "\nWrapping process init:");

        Packer packer = new Packer(new ProcessUpdaterListener()
        {
            @Override
            public void onUpdateProgress(int progress)
            {
                updateProgressASCIIBar(progress);

                if (progress == 100) {
                    showMessage(MSG_SUCCESS, false, "\nProcess finished!");
                }
            }

            @Override
            public void onNotifyError(String reason)
            {
                if (reason != null) {
                    showMessage(MSG_ERROR, true, reason);
                } else {
                    showMessage(MSG_ERROR, true, "Has been an error while wrapping process was executing.");
                }
                showMessage(MSG_ERROR, false, "Process finished with errors!");
            }
        });

        packer.wrap(mod);
    }

    /**
     * Updates an ASCII progress bar and draws it in the terminal.
     * @param progress
     */
    private void updateProgressASCIIBar(int progress)
    {
        int reducedProgress = progress / 2;
        System.out.print("\u001b[0m\r|");

        for (int i = 0; i < reducedProgress; i++) {
            System.out.print("=");
        }

        for (int i = reducedProgress; i < 50; i++) {
            System.out.print(" ");
        }

        System.out.print("| " + progress + "%");
    }

    /**
     * Recognizes receiving commands.
     */
    private void recognizeCommand()
    {
        if (args.length == 1) {
            if (args[0].equals("--help")) {
                showHelp();
            } else if (args[0].equals("--version")) {
                showVersion();
            } else {
                showMessage(MSG_ERROR, true, "'" + args[0] + "' command is not available. Please, see 'java -jar scs-map-mod-packer<version>.jar --help'.");
            }
        }
    }

    /**
     * Shows commands help list.
     */
    private void showHelp()
    {
        showMessage(MSG_DEFAULT, false, "Usage: java -jar scs-map-mod-packer<version>.jar [--version] [--help] [--o] [<args>]");

        showMessage(MSG_DEFAULT, false, "\n Commands:");
        showMessage(MSG_DEFAULT, false, "\t --version: shows SCSMMP version.");
        showMessage(MSG_DEFAULT, false, "\t --help: shows this commands help list.");
        showMessage(MSG_DEFAULT, false, "\t --o: overwrites the .scs output file in case there is one in the output directory. If this command is ommited, a backup file will be created for the old .scs file with the same name as the current placed in the same output directory.");

        showMessage(MSG_DEFAULT, false, "\n To wrap map mods, is needed 3 arguments as minimum:");
        showMessage(MSG_DEFAULT, false, "\t Map mod name: it's the map mod name, it's the same that the \"map/map_name.mbd\" filename.");
        showMessage(MSG_DEFAULT, false, "\t Map mod directory: it's the edited map directory. By default is \"C:\\Program Files\\Steam\\steamapps\\common\\Euro Truck Simulator 2\\base_map\\map\" at Windows OS.");
        showMessage(MSG_DEFAULT, false, "\t Output directory: it's the .scs wrapped file output directory. Can be any directory, but can be setted like \"C:\\Users\\<user>\\Documents\\Euro Truck Simulator 2\\mod\" to can be loaded automatically.");

        showMessage(MSG_DEFAULT, false, "\n Examples list:");
        showMessage(MSG_DEFAULT, false, "\t java -jar scs-map-mod-packer<version>.jar --version");
        showMessage(MSG_DEFAULT, false, "\t java -jar scs-map-mod-packer<version>.jar --help");
        showMessage(MSG_DEFAULT, false, "\t java -jar scs-map-mod-packer<version>.jar \"map_mod_name\" \"map_mod_directory\" \"output_directory\"");
        showMessage(MSG_DEFAULT, false, "\t java -jar scs-map-mod-packer<version>.jar --o \"map_mod_name\" \"map_mod_directory\" \"output_directory\"");
    }

    /**
     * Shows application version.
     */
    private void showVersion()
    {
        showMessage(MSG_INFO, false, name + " version " + version);
    }

    /**
     * Shows a message through terminal.
     *
     * @param type
     * @param tabulated
     * @param message
     */
    private void showMessage(int type, boolean tabulated, String message)
    {
        String color = "\u001b[0m";

        switch (type) {
            case MSG_ERROR:
                color = "\u001b[31m";
                break;

            case MSG_INFO:
                color = "\u001b[34m";
                break;

            case MSG_SUCCESS:
                color = "\u001b[32m";
                break;
        }

        if (tabulated) {
            message = "\t>> " + message;
        }

        System.out.println(color + message);
    }
}
