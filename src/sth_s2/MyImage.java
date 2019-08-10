/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sth_s2;

import java.awt.image.BufferedImage;

/**
 *
 * @author Nezz
 */
public class MyImage {
    private BufferedImage img;
    private int x,y,rot;
    public MyImage (BufferedImage imgPath,int x,int y,int rot){
        this.img=imgPath;
        this.x=x;
        this.y=y;  
        this.rot=rot;
    }

    public BufferedImage getImg() {
        return img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getRot() {
        return Math.toRadians (rot);
    }
    
    
    
}
