package a2_1901040226.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClosingEventListener extends WindowAdapter implements ActionListener {
    public void exitProgram() {
        System.out.println("Program is going to be terminated");
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Exit")) {
            exitProgram();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        exitProgram();
    }
}
