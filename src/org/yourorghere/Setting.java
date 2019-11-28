package org.yourorghere;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Setting extends JFrame {

    private static JButton ok;
    private static JButton Credit;

    private static JSlider slider1;
    private static JSlider slider2;
    private static JSlider slider3;
    private static JSlider slider4;
    private static JSlider slider5;

    private static JLabel label1;
    private static JLabel label2;
    private static JLabel label3;
    private static JLabel label4;
    private static JLabel label5;

    private final static Dimension SLIDER_SIZE = new Dimension(300, 20);
    private final static Dimension LABEL_SIZE = new Dimension(100, 20);

    public Setting() {
        initComponent();
    }

    public static void readData() {
        slider1.setValue((int) Data.tempValue1);
        slider2.setValue((int) Data.tempValue2);
        slider3.setValue((int) Data.tempValue3);
        slider4.setValue((int) Data.tempValue4);
        slider5.setValue((int) Data.tempValue5);
        setText();
    }

    private static void setText() {
        label1.setText(Texts.valueText1 + " " + Data.valueInWork1);
        label2.setText(Texts.valueText2 + " " + Data.valueInWork2);
        label3.setText(Texts.valueText3 + " " + Data.valueInWork3);
        label4.setText(Texts.valueText4 + " " + Data.valueInWork4);
        label5.setText(Texts.valueText5 + " " + Data.valueInWork5);
    }

    private void initComponent() {
        setType(Type.UTILITY);
        setTitle("Settings");
        setLayout(null);
        setSize(500, 400);
        setVisible(false);
        setResizable(false);
        setLocationRelativeTo(null);

        ok = new JButton("OK");
        ok.setSize(90, 25);
        ok.setLocation((getWidth() - ok.getWidth()) / 2, getHeight() - ok.getHeight() - 30);
        ok.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
                hide();
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        Credit = new JButton("Credits");
        Credit.setSize(90, 25);
        Credit.setLocation((getWidth() - Credit.getWidth()) / 2, ok.getY() - Credit.getHeight());
        Credit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
                JOptionPane.showMessageDialog(null, Texts.controls, "Credits", 1);
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });

        add(Credit);
        add(ok);

        slider1 = new JSlider();
        slider1.setSize(SLIDER_SIZE);
        slider1.setLocation((getWidth() - slider1.getWidth()) / 2, slider1.getHeight() + 20);
        slider1.setMinimum(Data.min1);
        slider1.setMaximum(Data.max1);
        slider1.addChangeListener((ChangeEvent e) -> {
            Data.tempValue1 = slider1.getValue();
            label1.setText(Texts.valueText1 + " " + Data.valueInWork1);
            Data.valueChanged1 = true;
        });
        label1 = new JLabel();
        label1.setSize(LABEL_SIZE);
        label1.setLocation((getWidth() - label1.getWidth()) / 2, slider1.getY() - 25);

        slider2 = new JSlider();
        slider2.setSize(SLIDER_SIZE);
        slider2.setLocation((getWidth() - slider2.getWidth()) / 2, slider1.getY() + slider1.getHeight() + slider2.getHeight() + 20);
        slider2.setMinimum(Data.min2);
        slider2.setMaximum(Data.max2);
        slider2.addChangeListener((ChangeEvent e) -> {
            Data.tempValue2 = slider2.getValue();
            label2.setText(Texts.valueText2 + " " + Data.valueInWork2);
            Data.valueChanged2 = true;
        });
        label2 = new JLabel();
        label2.setSize(LABEL_SIZE);
        label2.setLocation((getWidth() - label2.getWidth()) / 2, slider2.getY() - 25);

        slider3 = new JSlider();
        slider3.setSize(SLIDER_SIZE);
        slider3.setLocation((getWidth() - slider3.getWidth()) / 2, slider2.getY() + slider2.getHeight() + slider3.getHeight() + 20);
        slider3.setMinimum(Data.min3);
        slider3.setMaximum(Data.max3);
        slider3.addChangeListener((ChangeEvent e) -> {
            Data.tempValue3 = slider3.getValue();
            label3.setText(Texts.valueText3 + " " + Data.valueInWork3);
            Data.valueChanged3 = true;
        });
        label3 = new JLabel();
        label3.setSize(LABEL_SIZE);
        label3.setLocation((getWidth() - label3.getWidth()) / 2, slider3.getY() - 25);

        slider4 = new JSlider();
        slider4.setSize(SLIDER_SIZE);
        slider4.setLocation((getWidth() - slider4.getWidth()) / 2, slider3.getY() + slider3.getHeight() + slider4.getHeight() + 20);
        slider4.setMinimum(Data.min4);
        slider4.setMaximum(Data.max4);
        slider4.addChangeListener((ChangeEvent e) -> {
            Data.tempValue4 = slider4.getValue();
            label4.setText(Texts.valueText4 + " " + Data.valueInWork4);
            Data.valueChanged4 = true;
        });
        label4 = new JLabel();
        label4.setSize(LABEL_SIZE);
        label4.setLocation((getWidth() - label4.getWidth()) / 2, slider4.getY() - 25);

        slider5 = new JSlider();
        slider5.setSize(SLIDER_SIZE);
        slider5.setLocation((getWidth() - slider5.getWidth()) / 2, slider4.getY() + slider4.getHeight() + slider5.getHeight() + 20);
        slider5.setMinimum(Data.min5);
        slider5.setMaximum(Data.max5);
        slider5.addChangeListener((ChangeEvent e) -> {
            Data.tempValue5 = slider5.getValue();
            label5.setText(Texts.valueText5 + " " + Data.valueInWork5);
            Data.valueChanged5 = true;
        });
        label5 = new JLabel();
        label5.setSize(LABEL_SIZE);
        label5.setLocation((getWidth() - label5.getWidth()) / 2, slider5.getY() - 25);

        add(slider1);
        add(slider2);
        add(slider3);
        add(slider4);
        add(slider5);

        add(label1);
        add(label2);
        add(label3);
        add(label4);
        add(label5);

        repaint();
    }
}
