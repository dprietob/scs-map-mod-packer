package com.scsmmp;

public class Main
{
    public static void main(String[] args)
    {
        Packer packer = new Packer(new UpdaterListener()
        {
            @Override
            public void updateProgress(Integer progress)
            {
                System.out.println(progress);
            }

            @Override
            public void notifyError()
            {
                System.out.println("FAIL");
            }
        });

        packer.wrap(new Mod("landscape", "C:\\Users\\Dani\\Desktop\\map", "C:\\Users\\Dani\\Desktop\\"));
    }
}
