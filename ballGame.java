//This is the Game Client
//By Ethan Emerson

import javafx.event.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.stage.*;

public class ballGame extends Application
{   
   //Make the instance game variables
   private GamePane[][] game = new GamePane[4][4];
   private int move = 0;
   private int ballsLeft;
   
   //Make the instance variable for the scenes
   private BorderPane root = new BorderPane();
   private GridPane gp = new GridPane();;
   private Label title  = new Label();
   
   //Overide the Start method
   public void start(Stage stage)
   {
      //Set the margin/padding of the borderPane
      root.setMargin(title, new Insets(10,10,10,10));
      Label leftPadding = new Label();
      root.setLeft(leftPadding);
      root.setMargin(leftPadding, new Insets(20,20,20,20));
      Label bottomPadding = new Label();
      root.setBottom(bottomPadding);
      root.setMargin(bottomPadding, new Insets(10,10,10,10));
      
      //Add the HGap and the VGap for the gridpane and borderpane
      gp.setVgap(10);
      gp.setHgap(10);
      
      //Add the Game pane to the grid pane
      for(int i=0;i<4;i++)
      {
         for(int j=0;j<4;j++)
         {
            GamePane tempGame = new GamePane();
            game[i][j] = tempGame;
            gp.add(game[i][j],i,j);
            
            //Make the starting ball invisible
            if(i==0&&j==2)
               game[i][j].setBallVis(false);
         }
      }
      
      //Check the moves and Draw the first version of the game
      moveCheck(game);
      for(int i=0;i<4;i++)
      {
         for(int j=0;j<4;j++)
            game[i][j].draw();
      }
           
      //Add the GridPane and the label to the BorderPane
      root.setAlignment(title,Pos.TOP_CENTER);
      root.setTop(title);
      root.setAlignment(gp,Pos.TOP_RIGHT);
      root.setCenter(gp);
      
      //Create the scene
      Scene scene = new Scene(root,600,600);
      stage.setScene(scene);
      stage.setTitle("Ball Game");
      stage.show();
   }
   
   //Create the method to determine if moves can be made (This method also counts the number of balls left)
   public void moveCheck(GamePane[][] game)
   {      
      this.game = game;
      
      //For loop over the values of the gamePane to determine if a move can be made      
      ballsLeft = 0;
      move = 0;
      for(int i=0;i<4;i++)
      {
         for(int j=0;j<4;j++)
         {            
            //Check to see how many balls are left
            if(game[i][j].getBallVis()==true)
               ballsLeft++;
            
            if(j-2>-1) //Check to see if a move can be made above/check to see if the bottom button can be clicked
            {
               if((game[i][j-1].getBallVis()==true)&&(game[i][j-2].getBallVis()==false)&&(game[i][j].getBallVis()==true))
               {
                  game[i][j].setButtonVis('b',true);
                  move++;
               }
               else
                  game[i][j].setButtonVis('b',false);
            }
            if(j+2<4) //Check to see if a move can be made below/check to see if the top button can be clicked
            {
               if((game[i][j+1].getBallVis()==true)&&(game[i][j+2].getBallVis()==false)&&(game[i][j].getBallVis()==true))
               {
                  game[i][j].setButtonVis('t',true);
                  move++;
               }
               else
                  game[i][j].setButtonVis('t',false);
            }
            if(i-2>-1) //check to see if a ball can be moved to the right
            {
               if((game[i-1][j].getBallVis()==true)&&(game[i-2][j].getBallVis()==false)&&(game[i][j].getBallVis()==true))
               {
                  game[i][j].setButtonVis('r',true);
                  move++;
               }
               else
                 game[i][j].setButtonVis('r',false);
            }
            if(i+2<4) //Check to see if a ball can be moved to the left
            {
               if((game[i+1][j].getBallVis()==true)&&(game[i+2][j].getBallVis()==false)&&(game[i][j].getBallVis()==true))
               {
                  game[i][j].setButtonVis('l',true);
                  move++;
               }
                else
                  game[i][j].setButtonVis('l',false);
            }
         }
      }
   }
   
   //Create the button Listener for for the GamePane
   public class ButtonListener implements EventHandler<ActionEvent>
   {
      public void handle(ActionEvent e)
      {
         //For loop over the buttons to see which one got pressed
         for(int i=0;i<4;i++)
         {
            for(int j=0;j<4;j++)
            {
               if(e.getSource()==game[i][j].getButtonSelected('t')) //top
                  click(i,j,'t');
               else if(e.getSource()==game[i][j].getButtonSelected('r')) //right
                  click(i,j,'r');
               else if(e.getSource()==game[i][j].getButtonSelected('b')) //bottom
                  click(i,j,'b');
               else if(e.getSource()==game[i][j].getButtonSelected('l')) //left
                  click(i,j,'l');
            }
         }
         
         //Check the moves and update the game
         moveCheck(game);
         for(int i=0;i<4;i++)
         {
            for(int j=0;j<4;j++)
            {
               game[i][j].draw();
            }
         }         
      }
   }
   
   //Create the click method for the application class
   public void click(int i, int j, char name)
   {
      if(name=='t') //do the calculations for the top button selected
      {
         game[i][j].setButtonVis('t',false);
         game[i][j].setBallVis(false);
         game[i][j+1].setBallVis(false);
         game[i][j+2].setBallVis(true);
      }
      else if(name=='r') //do the calculations for the right selected button
      {
         game[i][j].setButtonVis('r',false);
         game[i][j].setBallVis(false);
         game[i-1][j].setBallVis(false);
         game[i-2][j].setBallVis(true);
      }
      else if(name=='b') //do the calculations for the bottom selected button
      {
         game[i][j].setButtonVis('b',false);
         game[i][j].setBallVis(false);
         game[i][j-1].setBallVis(false);
         game[i][j-2].setBallVis(true);
      }
      else if(name=='l') //do the calulations for the left selected button
      {
         game[i][j].setButtonVis('l',false);
         game[i][j].setBallVis(false);
         game[i+1][j].setBallVis(false);
         game[i+2][j].setBallVis(true);
      }
   }
      
   //Create the GamePane Class
   public class GamePane extends GridPane
   {
      //Create the instance buttons
      private Button bTop = new Button();
      private Button bRight = new Button();
      private Button bBottom = new Button();
      private Button bLeft = new Button();
      
      //Create the instance boolean for button visabiltiy
      private boolean topVis = false;
      private boolean rightVis = false; 
      private boolean bottomVis = false;
      private boolean leftVis = false;
      
      //Create the boolean instance variable for ball visability
      private boolean ballVis = true;
      
      //Create the instance canvas
      private Canvas canvas = new Canvas(80,80);
      private GraphicsContext gc = canvas.getGraphicsContext2D();
      
      //Create the constructor
      public GamePane()
      {
         //set the button sizes and draw the circle
         bTop.setPrefSize(80,20);
         bRight.setPrefSize(20,80);
         bBottom.setPrefSize(80,20);
         bLeft.setPrefSize(20,80);
         
         gc.fillOval(0,0,80,80);
         
         //Add the buttons and the canvas to the grid pane
         add(bTop,1,0);
         add(bRight,2,1);
         add(bBottom,1,2);
         add(bLeft,0,1);  
         add(canvas,1,1);
         
         //Set the buttons on action
         bTop.setOnAction(new ButtonListener());
         bRight.setOnAction(new ButtonListener());
         bBottom.setOnAction(new ButtonListener());
         bLeft.setOnAction(new ButtonListener());
      }
      
      //Start creating the accessors and mutators
      public boolean getBallVis() //return the visability of the ball
      {
         return ballVis;
      }
      
      public void setBallVis(boolean ballVis) //set the visability of the ball
      {
         this.ballVis = ballVis;
      }
      
      public boolean getButtonVis(char name) //get the visibility of a button
      {
         if(name=='t') //top
            return topVis;
         else if(name=='r') //right
            return rightVis;
         else if(name=='b') //bottom
            return bottomVis;
         else //left
            return leftVis;
      }
      
      public void setButtonVis(char name, boolean vis) //set the visibility of the button
      {
         if(name=='t') //top
            topVis = vis;
         else if(name=='r') //right
            rightVis = vis;
         else if(name=='b') //bottom
            bottomVis = vis;
         else //left
            leftVis = vis; 
      }
            
      public void draw() //update the game
      {
         //Draw the ball
         if(ballVis)
            gc.fillOval(0,0,80,80);
         else
            gc.clearRect(0,0,80,80);
         
         //Make the buttons visible or invisible
         bTop.setVisible(topVis);
         bRight.setVisible(rightVis);
         bBottom.setVisible(bottomVis);
         bLeft.setVisible(leftVis);
         
         //Update the game
         String labelText;
         if(move==0 && ballsLeft!=1) //loser
            labelText = "You have Zero Possible Moves You Lose";
         else if(ballsLeft==1) //winer
            labelText = "You won the game!! Congrats!";
         else //normal update
            labelText = "Balls Left: "+ballsLeft+" Moves Available: "+move;
         title.setText(labelText);
      }
      
      public Button getButtonSelected(char name) //Get which button is selected
      {
         if(name=='t') //top
            return bTop;
         else if(name=='r') //right
            return bRight;
         else if(name=='b') //bottom
            return bBottom;
         else //left
            return bLeft;
      }      
   }
   
   //Launch the program
   public static void main(String []args)
   {
      launch(args);
   }
}
