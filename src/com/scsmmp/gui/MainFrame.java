package com.scsmmp.gui;

import com.scsmmp.Mod;
import com.scsmmp.Packer;
import com.scsmmp.interfaces.ProcessUpdaterListener;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
    private final int MSG_ERROR = 1;
    private final int MSG_INFO = 2;

    private JTextField tfName;
    private JTextField tfInput;
    private JTextField tfOutput;
    private JProgressBar pbProgress;
    private JButton bInput;
    private JButton bOutput;
    private JButton bStart;
    private JButton bAbort;
    private JButton bAbout;
    private Packer packer;

    public MainFrame()
    {
        tfName = new JTextField();
        tfInput = new JTextField();
        tfOutput = new JTextField();
        pbProgress = new JProgressBar();
        bInput = new JButton("...");
        bOutput = new JButton("...");
        bStart = new JButton("Start");
        bAbort = new JButton("Abort");
        bAbout = new JButton();

        tfInput.setEnabled(false);
        tfOutput.setEnabled(false);

        pbProgress.setStringPainted(true);
        pbProgress.setValue(0);
        pbProgress.setMinimum(0);
        pbProgress.setMaximum(100);

        bStart.setIcon(new ImageIcon(getClass().getResource("icons/start.png")));
        bAbort.setIcon(new ImageIcon(getClass().getResource("icons/abort.png")));
        bAbout.setIcon(new ImageIcon(getClass().getResource("icons/about.png")));

        bAbort.setEnabled(false);
    }

    public void build()
    {
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new GridBagLayout());

        JPanel modPane = new JPanel();
        modPane.setLayout(new GridBagLayout());
        modPane.setBorder(BorderFactory.createTitledBorder("Mod"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridheight = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        modPane.add(new JLabel("Name"), gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 3;
        modPane.add(tfName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        modPane.add(new JLabel("Input"), gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        modPane.add(tfInput, gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        modPane.add(bInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        modPane.add(new JLabel("Output"), gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        modPane.add(tfOutput, gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        modPane.add(bOutput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        mainPane.add(modPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        mainPane.add(pbProgress, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPane.add(bAbout, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        mainPane.add(Box.createHorizontalGlue(), gbc);

        gbc.gridx = 2;
        mainPane.add(bAbort, gbc);

        gbc.gridx = 3;
        mainPane.add(bStart, gbc);

        getContentPane().add(mainPane);
        setButtonsListeners();
        pack();
        setSize(500, getHeight());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SCS Map Mod Packer");
        setResizable(false);
        setVisible(true);
    }

    private void setButtonsListeners()
    {
        bInput.addActionListener(e -> FileChooserDialog.show(directoryPath -> tfInput.setText(directoryPath)));
        bOutput.addActionListener(e -> FileChooserDialog.show(directoryPath -> tfOutput.setText(directoryPath)));
        bAbout.addActionListener(e -> AboutDialog.show(this));
        bAbort.addActionListener(e -> finishProcess());
        bStart.addActionListener(e -> {
            packer = new Packer(new ProcessUpdaterListener()
            {
                @Override
                public void onUpdateProgress(int progress)
                {
                    pbProgress.setValue(progress);

                    if (progress == 100) {
                        showMessage(MSG_INFO, "Process finished!");
                        finishProcess();
                    }
                }

                @Override
                public void onNotifyError()
                {
                    showMessage(MSG_ERROR, "Has been an error while wrapping process was executing.");
                    finishProcess();
                }
            });
            Mod mod = getMod();

            if (mod != null) {
                packer.wrap(getMod());
                bAbort.setEnabled(true);
            } else {
                showMessage(MSG_ERROR, "Please, fill all fields before start.");
            }
        });
    }

    private void finishProcess()
    {
        if (packer != null) {
            packer.abort();
            packer = null;
            bAbort.setEnabled(false);
            pbProgress.setValue(0);
        }
    }

    private Mod getMod()
    {
        String name = tfName.getText();
        String input = tfInput.getText();
        String output = tfOutput.getText();

        if (!name.equals("") && !input.equals("") && !output.equals("")) {
            return new Mod(name, input, output);
        }
        return null;
    }

    private void showMessage(int type, String message)
    {
        JOptionPane.showMessageDialog(this, message,
                (type == MSG_ERROR) ? "Error" : "Info",
                (type == MSG_ERROR) ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE
        );
    }
}
