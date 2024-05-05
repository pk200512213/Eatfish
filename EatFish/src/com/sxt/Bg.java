package com.sxt;

import java.awt.*;

public class Bg {
    void paintself(Graphics g,int fishLevel){
        g.drawImage(GameUtils.bgimg,0,0,null);
        switch (GameWin.state){
            case 0:
                GameUtils.drawWord(g,"start",Color.pink,60,600,500);//80,700,500
                break;
            case 1:
                GameUtils.drawWord(g,"积分"+GameUtils.count,Color.ORANGE,50,200,120);
                GameUtils.drawWord(g,"难度"+GameUtils.level,Color.ORANGE,50,600,120);
                GameUtils.drawWord(g,"等级"+fishLevel,Color.ORANGE,50,1000,120);

                break;
            case 2:
                GameUtils.drawWord(g,"积分"+GameUtils.count,Color.ORANGE,50,200,120);
                GameUtils.drawWord(g,"难度"+GameUtils.level,Color.ORANGE,50,600,120);
                GameUtils.drawWord(g,"等级"+fishLevel,Color.ORANGE,50,1000,120);
                GameUtils.drawWord(g,"defeat",Color.pink,80,600,500);


                break;
            case 3:
                GameUtils.drawWord(g,"积分"+GameUtils.count,Color.ORANGE,50,200,120);
                GameUtils.drawWord(g,"难度"+GameUtils.level,Color.ORANGE,50,600,120);
                GameUtils.drawWord(g,"等级"+fishLevel,Color.ORANGE,50,1000,120);
                GameUtils.drawWord(g,"victory",Color.pink,80,700,500);


                break;
            case 4:
                break;
            default:
        }
    }
}
