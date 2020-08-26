package com.scsmmp;

import com.scsmmp.interfaces.ProcessUpdaterListener;

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

    public Terminal(String[] arrArgs, String sName, String sVersion)
    {
        args = arrArgs;
        name = sName;
        version = sVersion;
    }

    public void start()
    {
        showMessage(MSG_DEFAULT, name + " v" + version);

        if (args.length == 1) {
            recognizeCommand();

        } else if (args.length == 3) {
            mod = new Mod(args[0], args[1], args[2]);

        } else if (args.length == 4) {
            if (args[0].equals("--o")) {
                mod = new Mod(args[1], args[2], args[3]);
                mod.setOverwrite(true);
            } else {
                showMessage(MSG_ERROR, "'" + args[0] + "' command is not available. Please, see 'scsmmp.jar --help'.");
            }

        } else {
            showMessage(MSG_ERROR, "Argument list is wrong. Please, see 'scsmmp.jar --help'.");
        }

        if (mod != null) {
            initWrappingProcess();
        }
    }

    private void initWrappingProcess()
    {
        showMessage(MSG_INFO, "\nWrapping process init:");

        Packer packer = new Packer(new ProcessUpdaterListener()
        {
            @Override
            public void onUpdateProgress(int progress)
            {
                updateProgressASCIIBar(progress);

                if (progress == 100) {
                    showMessage(MSG_SUCCESS, "\nProcess finished!");
                }
            }

            @Override
            public void onNotifyError()
            {
                showMessage(MSG_ERROR, "Has been an error while wrapping process was executing.");
            }
        });

        packer.wrap(mod);
    }

    private void updateProgressASCIIBar(int progress)
    {
        int reducedProgress = progress / 5;
        System.out.print("\u001b[0m\r|");

        for (int i = 0; i < reducedProgress; i++) {
            System.out.print("=");
        }

        for (int i = reducedProgress; i < 20; i++) {
            System.out.print(" ");
        }

        System.out.print("| " + progress + "%");
    }

    private void recognizeCommand()
    {
        if (args.length == 1) {
            if (args[0].equals("--help")) {
                showHelp();
            } else if (args[0].equals("--version")) {
                showVersion();
            } else {
                showMessage(MSG_ERROR, "'" + args[0] + "' command is not available. Please, see 'scsmmp.jar --help'.");
            }
        }
    }

    private void showHelp()
    {
        showMessage(MSG_DEFAULT, "Usage: scsmmp.jar [--version] [--help] [--o] [<args>]");

        showMessage(MSG_DEFAULT, "\n Commands:");
        showMessage(MSG_DEFAULT, "\t --version: shows SCSMMP version.");
        showMessage(MSG_DEFAULT, "\t --help: shows this commands help list.");
        showMessage(MSG_DEFAULT, "\t --o: overwrites the .scs output file in case there is one in the output directory. If this command is ommited, a backup file will be created for the old .scs file with the same name as the current placed in the same output directory.");

        showMessage(MSG_DEFAULT, "\n To wrap map mods, is needed 3 arguments as minimum:");
        showMessage(MSG_DEFAULT, "\t Map mod name: it's the map mod name, it's the same that the \"map/map_name.mbd\" filename.");
        showMessage(MSG_DEFAULT, "\t Map mod directory: it's the edited map directory. By default is \"C:\\Program Files\\Steam\\steamapps\\common\\Euro Truck Simulator 2\\base_map\\map\" at Windows OS.");
        showMessage(MSG_DEFAULT, "\t Output directory: it's the .scs wrapped file output directory. Can be any directory, but can be setted like \"C:\\Users\\<user>\\Documents\\Euro Truck Simulator 2\\mod\" to can be loaded automatically.");

        showMessage(MSG_DEFAULT, "\n Examples list:");
        showMessage(MSG_DEFAULT, "\t scsmmp.jar --version");
        showMessage(MSG_DEFAULT, "\t scsmmp.jar --help");
        showMessage(MSG_DEFAULT, "\t scsmmp.jar \"map_mod_name\" \"map_mod_directory\" \"output_directory\"");
        showMessage(MSG_DEFAULT, "\t scsmmp.jar --o \"map_mod_name\" \"map_mod_directory\" \"output_directory\"");
    }

    private void showVersion()
    {
        showMessage(MSG_INFO, name + " version " + version);
    }

    private void showMessage(int type, String message)
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

        System.out.println(color + message);
    }
}
