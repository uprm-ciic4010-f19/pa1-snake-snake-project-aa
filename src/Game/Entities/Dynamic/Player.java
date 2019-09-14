package Game.Entities.Dynamic;

import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

// import Game.Entities.Static.Apple;
import Game.GameStates.State;
/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {


	public int lenght;
    public boolean justAte;
    private Handler handler;

    public int xCoord;
    public int yCoord;

    public int moveCounter;
    
    public int speed = 7;
    public double currScore;
    public double score;
    public Tail tail;
    public int steps;


    public String direction;//is your first name one?

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        direction= "Right";
        justAte = false;
        lenght= 1;
        steps = 0;
        currScore = 1;

    }

    public void tick(){
        moveCounter++;
        steps++;
        if(moveCounter>=speed) {
            checkCollisionAndMove();
            moveCounter=0;
        } if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) && direction != "Down"){
            direction="Up";
        }
         if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN) && direction != "Up"){
        	direction="Down";
            
         } 
         if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT) && direction != "Right"){
            direction="Left";
            
         }
         if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT) && direction != "Left"){
            direction="Right";

         }  
         if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
        	lenght++;
        	
        	switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }

                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        } System.out.println("Tu biscochito");
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
        }
        handler.getWorld().body.addLast(tail);
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
    
   }

        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
    	   State.setState(handler.getGame().pauseState); 
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) {
   		 this.speed -= 1;
   }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)) {
   		 this.speed += 1; 
        }
        
        if (steps >= 500 && justAte == false) {
        	handler.getWorld().apple.isGood = false;
        }
        }
        
    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
        switch (direction){
            case "Left":
                if(xCoord==0){
                	handler.getWorld().player.xCoord = handler.getWorld().GridWidthHeightPixelCount-1;
                }else{
                    xCoord--;
                }
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                	handler.getWorld().player.xCoord = 0;
                }else{
                    xCoord++;
                }
                break;
            case "Up":
                if(yCoord==0){
                	handler.getWorld().player.yCoord = handler.getWorld().GridWidthHeightPixelCount-1;
                }else{
                    yCoord--;
                }
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                	handler.getWorld().player.yCoord = 0;
                }else{
                    yCoord++;
                }
                break;
            	}
       
        handler.getWorld().playerLocation[xCoord][yCoord]=true;


        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            Eat();
            }
        
        
        for(int i = 0; i < handler.getWorld().body.size(); i++) {
       		if (handler.getWorld().player.xCoord == handler.getWorld().body.get(i).x && handler.getWorld().player.yCoord == handler.getWorld().body.get(i).y){
       				kill();
       				
        	}
        }

        if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
        }
        }
   
    public void render(Graphics g,Boolean[][] playeLocation){
        Random r = new Random();
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                g.setColor(Color.green);

                if(playeLocation[i][j]||handler.getWorld().appleLocation[i][j]){
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                }

            }
        }
        for ( int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                 if (handler.getWorld().apple.isGood) {
                 g.setColor(Color.red);
         }
                 else {
                 g.setColor(new Color(102, 51, 51));
                 }
                 
                 		if(handler.getWorld().appleLocation[i][j]){
                 			g.fillRect((i*handler.getWorld().GridPixelsize),
                 					(j*handler.getWorld().GridPixelsize),
                 						handler.getWorld().GridPixelsize,
                                          handler.getWorld().GridPixelsize);

                          }
                      }
        }
  }
    
	public void Eat() {
		steps = 0;
		
		if (handler.getWorld().apple.isGood = false) {
			score = (Math.sqrt((2*currScore)+1)) - score;
			handler.getWorld().body.getLast();
			handler.getWorld().body.removeLast();
			lenght--;
		  	
		  	 handler.getWorld().appleLocation[xCoord][yCoord]=false;
		        handler.getWorld().appleOnBoard=false;
		        
		        switch (direction){
		            case "Left":
		                if( handler.getWorld().body.isEmpty()){
		                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
		                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
		                    }else{
		                        if(this.yCoord!=0){
		                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
		                        }else{
		                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
		                        }
		                    }
		                }else{
		                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
		                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
		                    }else{
		                        if(handler.getWorld().body.getLast().y!=0){
		                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
		                        }else{
		                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

		                        }
		                    }

		                }
		                break;
		            case "Right":
		                if( handler.getWorld().body.isEmpty()){
		                    if(this.xCoord!=0){
		                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
		                    }else{
		                        if(this.yCoord!=0){
		                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
		                        }else{
		                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
		                        }
		                    }
		                }else{
		                    if(handler.getWorld().body.getLast().x!=0){
		                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
		                    }else{
		                        if(handler.getWorld().body.getLast().y!=0){
		                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
		                        }else{
		                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
		                        }
		                    }

		                }
		                break;
		            case "Up":
		                if( handler.getWorld().body.isEmpty()){
		                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
		                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
		                    }else{
		                        if(this.xCoord!=0){
		                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
		                        }else{
		                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
		                        }
		                    }
		                }else{
		                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
		                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
		                    }else{
		                        if(handler.getWorld().body.getLast().x!=0){
		                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
		                        }else{
		                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
		                        }
		                    }

		                }
		                break;
		            case "Down":
		                if( handler.getWorld().body.isEmpty()){
		                    if(this.yCoord!=0){
		                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
		                    }else{
		                        if(this.xCoord!=0){
		                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
		                        }else{
		                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
		                        } System.out.println("Tu biscochito");
		                    }
		                }else{
		                    if(handler.getWorld().body.getLast().y!=0){
		                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
		                    }else{
		                        if(handler.getWorld().body.getLast().x!=0){
		                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
		                        }else{
		                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
		                        }
		                    }

		                }
		                break;
		        }
		        handler.getWorld().body.addLast(tail);
		        handler.getWorld().playerLocation[tail.x][tail.y] = true;
	}
		
	else 
		lenght++;
    	score += Math.sqrt((2*currScore)+1);
    	speed -= 7;
           
        handler.getWorld().appleLocation[xCoord][yCoord]=false;
        handler.getWorld().appleOnBoard=false;
        
        switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }

                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        } System.out.println("Tu biscochito");
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
        }
        handler.getWorld().body.addLast(tail);
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
    }

	public void kill(){
        lenght = 0;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

              handler.getWorld().playerLocation[xCoord][yCoord]=false;
		        State.setState(handler.getGame().gameOverState);
            }   
        }
	}


    public boolean isJustAte() {
        return justAte;
    }

    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }
}