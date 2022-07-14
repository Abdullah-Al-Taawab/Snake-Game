/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;

/**
 *
 * @author Asus
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Asus
 */
public class gamepanel extends JPanel implements ActionListener{
    
    static final int SCREEN_WIDTH=650;
    static final int SCREEN_HEIGHT=650; 
    static final int UNIT_SIZE=18; //object size
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=60; // higher the value slower the game
    final int x[]=new int[GAME_UNITS]; //ARRAY WILL HOLD THE X CORDINATES OF SNAKES BODY PART
    final int y[]=new int[GAME_UNITS]; //ARRAY WILL HOLD THE Y CORDINATES OF SNAKES BODY PART
    int bodyParts=6;
    int applesEaten;//
    int appleX; //this will be the X coordinate of where apple is located
    int appleY;
    char direction='R';
    boolean running=false;
    Timer timer;
    Random random;
    
    
    
    
    
    gamepanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        
    }
    
    public void startGame(){
        newApple(); //after starting the game it will give new apple
        running=true;
        timer= new Timer(DELAY,this);
        timer.start();
        
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
        
    }
    public void draw(Graphics g){
        if(running){
          /*for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
           g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); //draw line in the game view
           g.drawLine(0,i*UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE); //draw line in the game view
        }
*/
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); //apple looks like circle 
        
        for(int i=0;i<bodyParts;i++){
            if(i==0){//for  head of snake
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            else{//rest body part of snake
                g.setColor(Color.red);
                g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
        
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score"+applesEaten))/2, g.getFont().getSize());
        
    }
        else{
            gameOver(g);
        }
        
    }
    public void newApple(){
        appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        
    }
    public void move(){
        for(int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
            
        }
        switch(direction){
            case'U': //upper direction
                y[0]=y[0]-UNIT_SIZE;
                break;
            case'D'://down direction
                y[0]=y[0]+UNIT_SIZE;
                break;
            case'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
                
            
        }
    }
    
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
        bodyParts++;
        applesEaten++;
        newApple();
        
    }
        
    }
    public void checkCollision(){
        //checks if head collides with body
        for(int i=bodyParts;i>0;i--){
            if(x[0] == x[i] && (y[0]==y[i])){
                running=false;
            }
        }
    //check if head touches left border
    if(x[0]<0){
        running=false;
    }
    //check if head touches right border
    if(x[0]>SCREEN_WIDTH){
        running=false;
    }
    //check if head touches Top border
    if(y[0]<0){
        running=false;
    }
    //check if head touches bottom border
    if(y[0]>SCREEN_HEIGHT){
        running=false;
    }
    if(!running){
        timer.stop();
    }
     
    }
    public void gameOver(Graphics g){
        //Game Over Prompt
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
           
    }
     

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollision();
           
            
        }
        repaint();
        
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public  void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction='L';
                    }
            
             break;
             case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction='R';
                    }
            
            break;
            
            case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction='U';
                    }
            
            break;
            case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction='D';
                    }
            
            break;
           
            
        }
        
    }

    
    
}
}
