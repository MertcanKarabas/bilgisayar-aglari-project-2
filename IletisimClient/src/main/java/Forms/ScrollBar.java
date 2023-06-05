/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Forms;

import java.awt.Dimension;
import javax.swing.JScrollBar;

/**
 *
 * @author mertcankarabas
 */
public class ScrollBar extends JScrollBar{
    
    public ScrollBar() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(5, 5));
    }
}
