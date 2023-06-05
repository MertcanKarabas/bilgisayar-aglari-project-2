/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Forms;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author mertcankarabas
 */
public class People extends javax.swing.JPanel {

    /**
     * Creates new form People
     */
    public People(String name) {
        initComponents();
        lbl_name.setText(name);
        init();
    }

    private void init() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                setBackground(new Color(249, 249, 249));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                
            }
            
            

        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_avatar = new Forms.MyButton();
        lbl_name = new javax.swing.JLabel();

        setBackground(new java.awt.Color(249, 249, 249));

        btn_avatar.setIcon(new javax.swing.ImageIcon("/Users/mertcankarabas/NetBeansProjects/MesajlasmaApp/src/main/java/Icons/Avatar_Icon (40 × 40 px).png")); // NOI18N

        lbl_name.setBackground(new java.awt.Color(249, 249, 249));
        lbl_name.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        lbl_name.setText("Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btn_avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_name, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_avatar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Forms.MyButton btn_avatar;
    private javax.swing.JLabel lbl_name;
    // End of variables declaration//GEN-END:variables
}