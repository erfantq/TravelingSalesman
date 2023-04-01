import javax.swing.*;

public class MovementButtons {
    private JButton Right,Down,Left,Up;
    private ImageIcon right=new ImageIcon("images\\RightButton.png");
    private ImageIcon left=new ImageIcon("images\\LeftButton.png");
    private ImageIcon up=new ImageIcon("images\\UpButton.png");
    private ImageIcon down=new ImageIcon("images\\DownButton.png");
    MovementButtons(JFrame gamewindow){
        Right=new JButton(right);
        Down=new JButton(down);
        Left=new JButton(left);
        Up=new JButton(up);
        Right.setBounds(1148,620,60,60);
        Up.setBounds(1088,559,60,60);
        Down.setBounds(1088,681,60,60);
        Left.setBounds(1027,620,60,60);
        gamewindow.add(Right);
        gamewindow.add(Left);
        gamewindow.add(Up);
        gamewindow.add(Down);

    }

}
