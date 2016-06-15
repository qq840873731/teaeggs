package org.sauceggplant;

import org.sauceggplant.thread.MainThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class App {

    private static MainThread thread;// = new MainThread();

    private OutputStream os;
    private static JLabel startTime = new JLabel("开始时间:");
    private static JLabel stopTime = new JLabel("终止时间:");
    private static JLabel allTime = new JLabel("持续时间:");
    static JButton bStart;
    static JButton bPause;
    static JButton bContinue;

    public App() {
//        try{
//            BeautyEyeLNFHelper.frameBorderStyle=BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
//            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//        }catch (Exception e){
//            System.out.println("不支持Swing皮肤");
//            e.printStackTrace();
//        }
        JFrame frame = new JFrame("多线程测试");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        JToolBar toolBar = new JToolBar();
        bStart = new JButton("开始");
        bStart.setBackground(Color.cyan);
        bStart.setForeground(Color.orange);
        toolBar.add(bStart);
        bPause = new JButton("停止");
        bPause.setBackground(Color.cyan);
        bStart.setForeground(Color.orange);
        toolBar.add(bPause);
        bContinue = new JButton("继续");
        bContinue.setBackground(Color.cyan);
        bStart.setForeground(Color.orange);
        toolBar.add(bContinue);

        bStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doStart(e);
            }
        });
        bPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doPause(e);
            }
        });
        bContinue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doContinue(e);
            }
        });

        northPanel.add(toolBar);
        JToolBar toolBar1 = new JToolBar();
        toolBar1.add(startTime);
        toolBar1.add(stopTime);
        toolBar1.add(allTime);
        northPanel.add(toolBar1);
        northPanel.setBackground(Color.darkGray);

        panel.add(northPanel, BorderLayout.NORTH);

        final JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.darkGray);
        textArea.setForeground(Color.green);
        os = new OutputStream() {
            final int BUFFER_LENGTH = 1024;
            byte buf[] = new byte[BUFFER_LENGTH];
            int pos = 0;

            public void write(int b) throws IOException {
                buf[pos++] = (byte) b;
                if (pos >= BUFFER_LENGTH) {
                    flush();
                }
            }

            public void flush() throws IOException {
                int length = textArea.getText().length();
                if (length > 2000000000) {
                    textArea.setText("");
                }
                textArea.setCaretPosition(length);
                if (pos >= BUFFER_LENGTH) {
                    textArea.append(new String(buf));
                } else {
                    textArea.append(new String(buf, 0, pos));
                }
                pos = 0;
            }
        };

        System.setOut(new PrintStream(os, true));
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.getContentPane().add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        this.thread = new MainThread(true);
    }

    public static void doStart(ActionEvent e) {
        if ("持续时间:".equals(allTime.getText())) {
            allTime.setText("持续时间:" + System.currentTimeMillis());
        }
        startTime.setText("开始时间:" + System.currentTimeMillis());
        thread.start();
        bStart.setEnabled(false);
    }

    public static void doPause(ActionEvent e) {
        stopTime.setText("终止时间:" + System.currentTimeMillis());
        thread.setFlag(false);
    }

    public static void doContinue(ActionEvent e) {
        startTime.setText("开始时间:" + System.currentTimeMillis());
        thread.setFlag(true);
    }

    public void doStart() {
        thread.start();
        startTime.setEnabled(false);
        bStart.setEnabled(false);
    }

    public void doPause() {
        thread.setFlag(false);
    }

    public void doContinue() {
        thread.setFlag(true);
    }

    public static void main(String[] args) {
        new App();
    }
}

