package com.sxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWin extends JFrame { //Java图形化界面设计——容器（JFrame）
    //游戏默认状态
    static int state=0;
    //解决文字闪动
    Image offSceenImage;
    //宽高
    int width = 1440;
    int height = 900;


    double random;
    Bg bg=new Bg(); //背景

    //计数器
    int time=0;

    //敌方鱼类
    Enamy enamy;

    //boss
    Enamy boss;
    boolean isboss=false;//初始为未生成

    //我方鱼类
    MyFish myFish=new MyFish();

    public void launch(){
        this.setVisible(true);  //窗口可见
        this.setSize(width, height); //窗口大小
        this.setLocationRelativeTo(null);//窗口在屏幕居中位置
        this.setResizable(true);//窗口大小可改变
        this.setTitle("EatFish");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //可关闭窗口

        //添加鼠标事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton()==1&&state==0){ //鼠标左键对应的值为1
                    state=1;
                    repaint(); //再次调用paint方法
                }
                if(e.getButton()==1&&(state==2||state==3)){
                    reGame();
                    state=1;
                }
            }
        });

        //键盘移动   wasd
        this.addKeyListener(new KeyAdapter() {
            @Override//按压
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode()==87){
                    GameUtils.UP=true;
                }
                if(e.getKeyCode()==83){
                    GameUtils.DOWN=true;
                }
                if(e.getKeyCode()==65){
                    GameUtils.LEFT=true;
                }
                if(e.getKeyCode()==68){
                    GameUtils.RIGHT=true;
                }
                if(e.getKeyCode()==32){  //空格为32
                    switch(state){
                        case 1:
                            state=4;
                            GameUtils.drawWord(getGraphics(),"游戏暂停",Color.red,50,600,400);
                            break;
                        case 4:
                            state=1;
                            break;
                    }
                }
            }


            @Override//抬起
            public void keyReleased(KeyEvent e){
                super.keyReleased(e);
                if(e.getKeyCode()==87){
                    GameUtils.UP=false;
                }
                if(e.getKeyCode()==83){
                    GameUtils.DOWN=false;
                }
                if(e.getKeyCode()==65){
                    GameUtils.LEFT=false;
                }
                if(e.getKeyCode()==68){
                    GameUtils.RIGHT=false;
                }
            }
        });


        while(true){ //保证了背景图片的循环使用
            repaint();
            time++;
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        //加载模式初始化对象
        offSceenImage=createImage(width,height);
        Graphics gImage=offSceenImage.getGraphics();
        bg.paintself(gImage, myFish.level);

       // g.drawImage(GameUtils.bgimg,0,0,null);

        switch(state){
            case 0:
//                gImage.drawImage(GameUtils.bgimg,0,0,null);
//                gImage.setColor(Color.pink);
//                gImage.setFont(new Font("仿宋",Font.BOLD,60));
//                gImage.drawString("开始",700,500);
                break;
            case 1:
//                bg.paintself(gImage);
//                gImage.setColor(Color.ORANGE);
//                gImage.setFont(new Font("仿宋",Font.BOLD,50));
//                gImage.drawString("积分"+GameUtils.count,200,120);
                myFish.painSelf(gImage);
                logic();
                for(Enamy enamy:GameUtils.EnamyList){
                    enamy.paintSelf(gImage);
                }
                if(isboss){
                    boss.x=boss.x+ boss.dir*boss.speed;
                    boss.paintSelf(gImage);
                    if(boss.x<0){
                        gImage.setColor(Color.red);
                        gImage.fillRect(boss.x,boss.y,2400,2400);
                    }
                }
                break;
            case 2:
                for(Enamy enamy:GameUtils.EnamyList) {
                    enamy.paintSelf(gImage);
                }
                if(isboss){
                    boss.paintSelf(gImage);
                }
                break;
            case 3:
                myFish.painSelf(gImage);
//                gImage.setColor(Color.ORANGE);
//                gImage.setFont(new Font("仿宋",Font.BOLD,80));
//                gImage.drawString("积分"+GameUtils.count,200,120);
//                gImage.drawString("胜利",400,500);
                break;
            case 4:
                return;
                default:
        }
        g.drawImage(offSceenImage,0,0,null);
    }




    void logic(){
        //关卡难度
        if(GameUtils.count<5){
            GameUtils.level=0;
            myFish.level=1;
        }else if(GameUtils.count<=15){
            GameUtils.level=1;
        }else if(GameUtils.count<=50){
            GameUtils.level=2;
            myFish.level=2;
        }else if(GameUtils.count<=150){
            GameUtils.level=3;
            myFish.level=3;
        }else if(GameUtils.count<=300){
            GameUtils.level=4;
            myFish.level=3;
        }else if(GameUtils.count>300){
            state=3;
        }


        random=Math.random();
        switch (GameUtils.level){
            case 4:
                if(time%60==0){
                    if(random>0){
                        boss=new Enamy_Boss();
                        isboss=true;
                    }
                }
            case 3:
            case 2:
                if(time%30==0){
                    if(random>0.5){
                        enamy=new Enamy_3_L();
                    }else{
                        enamy=new Enamy_3_R();
                    }
                    GameUtils.EnamyList.add(enamy);
                }
            case 1:
                if(time%20==0){
                    if(random>0.5){
                        enamy=new Enamy_2_L();
                    }else{
                        enamy=new Enamy_2_R();
                    }
                    GameUtils.EnamyList.add(enamy);
                }
            case 0:
            if(time%10==0){ //敌方鱼的生成
                if(random>0.5){
                    enamy=new Enamy_1_L();

                } else{
                    enamy=new Enamy_1_R();
                }
                GameUtils.EnamyList.add(enamy);
            }
            break;
            default:
        }



        //移动方向
        for(Enamy enamy:GameUtils.EnamyList){
            enamy.x=enamy.x+enamy.dir*enamy.speed;

            if(isboss){
                if(boss.getRec().intersects(enamy.getRec())){
                    enamy.x=-200;
                    enamy.y=-200;
                }
                if(boss.getRec().intersects(myFish.getRec())){
                    state=2;
                }
            }
            //我方鱼与敌方鱼碰撞检测
            if(myFish.getRec().intersects(enamy.getRec())){
                if(myFish.level>=enamy.type) {
                    System.out.println("碰撞了");
                    enamy.x=-200;
                    enamy.y=-200;
                    GameUtils.count=GameUtils.count+enamy.count;
                }else{
                    state = 2;
                }

            }
        }

    }
    //重新开始，敌方鱼清零，各种配置清零
    void reGame(){
        GameUtils.EnamyList.clear();
        time=0;
        myFish.level=1;
        GameUtils.count=0;
        myFish.x=700;
        myFish.y=500;
        myFish.width=50;
        myFish.height=50;
        boss=null;
        isboss=false;
    }




    public static void main(String[] args) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}
