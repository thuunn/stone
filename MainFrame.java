package com.itheima.stonepuzzle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MainFrame extends JFrame implements KeyListener {

    int[][] data = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    int row;        // 0号元素行坐标位置
    int column;     // 0号元素列坐标位置
    int count;      // 统计步数
    //构造方法：：
    public MainFrame() {


        // 窗体对象.addKeyListener(KeyListener实现类对象);
        this.addKeyListener(this);
        // this : 当前类对象
        // 1) 窗体对象
        // 2) KeyListener实现类对象

        // 初始化窗体
        initFrame();
        // 初始化数据
        initData();
        // 绘制游戏界面
        paintView();
        // 设置窗体可见
        setVisible(true);
    }

    /**
     * 初始化数据 (打乱二维数组)
     */
    public void initData() {
        // 准备Random对象
        Random r = new Random();
        // 遍历二维数组, 获取到每一个元素
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                int randomX = r.nextInt(4);
                int randomY = r.nextInt(4);
                // data[i][j]
                // data[randomX][randomY]
                int temp = data[i][j];
                data[i][j] = data[randomX][randomY];
                data[randomX][randomY] = temp;
            }
        }
//        用于确定0格子的位置
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 0) {
                    row = i;
                    column = j;
                }
            }
        }

    }

    /**
     * 此方法用于初始化窗体
     */
    public void initFrame() {
        // 设置窗体大小
        setSize(514, 595);
        // 设置窗体关闭模式
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 设置窗体标题
        setTitle("石头迷阵单机版V1.0");
        // 设置窗体置顶
        setAlwaysOnTop(true);
        // 设置窗体居中
        setLocationRelativeTo(null);
        // 取消默认布局
        setLayout(null);
    }

    /**
     * 此方法用于绘制游戏界面
     */
    public void paintView() {


        getContentPane().removeAll();

        if(victory()){
            // 加载胜利图片资源, 添加到窗体中
            JLabel winLabel = new JLabel(new ImageIcon("D:\\image\\win.png"));
            winLabel.setBounds(124,230,266, 88);
            getContentPane().add(winLabel);
        }

        JButton btn = new JButton("重新游戏");
        btn.setBounds(350,20,100,20);
        getContentPane().add(btn);
        btn.setFocusable(false);
        btn.addActionListener(e -> {
            count = 0;
            initData();
            paintView();
        });

        JLabel scoreLabel = new JLabel("步数为:" + count);
        scoreLabel.setBounds(50,20,100,20);
        getContentPane().add(scoreLabel);

        for (int i = 0; i < 4; i++) {
            // i = 0 1 2 3
            for (int j = 0; j < 4; j++) {
                // j = 0 1 2 3
                JLabel imageLabel = new JLabel(new ImageIcon("D:\\image\\" + data[i][j] + ".png"));
                imageLabel.setBounds(50 + 100 * j, 90 + 100 * i, 100, 100);
                getContentPane().add(imageLabel);
            }
        }


        JLabel background = new JLabel(new ImageIcon("D:\\image\\background.png"));
        background.setBounds(26, 30, 450, 484);
        getContentPane().add(background);

        getContentPane().repaint();
    }

    /**
     * 判断游戏是否胜利
     */
    public boolean victory() {


        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }

        return true;

    }


    /**
     * 此方法用于处理移动业务
     */
    private void move(int keyCode) {

        if(victory()){
            return;
        }
//左移动：
        if (keyCode == 37) {

            if (column == 3) {
                return;
            }

            // 空白块和右侧数据交换
            // data[row][column]  data[row][column+1]
            int temp = data[row][column];
            data[row][column] = data[row][column + 1];
            data[row][column + 1] = temp;
            column++;
            count++;
//            上移动：
        } else if (keyCode == 38) {

            if (row == 3) {
                return;
            }

            // 空白块和下面的数据交换
            // data[row][column] data[row+1][column]
            int temp = data[row][column];
            data[row][column] = data[row + 1][column];
            data[row + 1][column] = temp;
            row++;
            count++;
//            右移动
        } else if (keyCode == 39) {

            if (column == 0) {
                return;
            }

            // 空白块和左侧的数据交换
            // data[row][column] data[row][column-1]
            int temp = data[row][column];
            data[row][column] = data[row][column - 1];
            data[row][column - 1] = temp;
            column--;
            count++;
//            下移动：
        } else if (keyCode == 40) {

            if (row == 0) {
                return;
            }

            // 空白块和上面的数据交换
            // data[row][column] data[row-1][column]
            int temp = data[row][column];
            data[row][column] = data[row - 1][column];
            data[row - 1][column] = temp;
            row--;
            count++;
        } else if (keyCode == 90) {
            // 触发作弊器
            data = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
        }


    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
//        System.out.println("键值：");
//        System.out.println(keyCode);
        move(keyCode);
        // 每一次移动之后, 都重新绘制游戏界面
        paintView();
    }

    // ----------------------------------------------------
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    // ----------------------------------------------------
}
