//size can be changed as you wish because this size is fine for me
//Dont forget about text size. they are all need to be changed

package ie.tudublin;

import java.util.ArrayList;

import processing.core.PApplet;

import processing.data.Table;

import processing.data.TableRow;


public class Gantt extends PApplet
{	

float formX,formY,taskX,taskY,blank,border_left,border_right,header;

float marginleft,curline;

int textsize = 30;

int totaldata,choosentask; 

boolean set = true;
  
ArrayList<Task> Task = new ArrayList<Task>();

public void settings()
{
    size(1800, 1000);
    formX = width/8;
    formY = height/16;
    taskX = formX/4;
    taskY = formY/4;
    totaldata = 30;
}
  
  
public void setup() 
{   
    colorMode(HSB);
    loadTasks();
    printTasks();
}


public void loadTasks()
{
    Table table = loadTable("tasks.csv","header");
    
    for(TableRow row:table.rows())

        {         
            Task t = new Task(row);

            Task.add(t);
        }   
}

  
public void printTasks()
{
    for(Task t:Task)
    {
            System.out.println("print task");
            System.out.println(t);
    }
}
  
  
public void displayTasks()
{  
   blank = (width - 2 * formX)/(totaldata-1);
  
   textSize(textsize);
   textAlign(CENTER,CENTER);
   
   //to draw a empty line sheet with number but without task name
   for(int i = 1; i <= totaldata; i++)
   {
     float lineX = map(i,1,30,formX,width - formX);
     stroke(255);
     line(lineX,formY,lineX,height);
     text(i,lineX,formY - textsize);
   }
   
   //to draw a task name a rectangel
   textAlign(LEFT,CENTER);
   for(int j = 0; j < Task.size(); j++)
   {
     
     Task t = Task.get(j);
     
     float taskY = map(j,0,Task.size(),formY+textsize,height);
     float colors = map(j,0,Task.size()*2,0,360);
     fill(255);
     text(t.getTask(),taskX,taskY);
     
     noStroke();
     fill(colors,255,360);
     float rectwidth = (t.getEnd()-t.getStart()) * blank;
     rect((t.getStart() - 1) * blank + formX,taskY- textsize,rectwidth,2 * textsize,7);
     fill(255);
   }
}
  
public boolean inform()
{
  boolean inform = false;
  
  if(mouseX >= formX && mouseX <= width - formX && mouseY >= formY)
  {
    inform = true;
  }
  else
  {
    inform = false;
  }
  
  return inform;
}

//set hitbox as 20 px in total
//margin left means left border of form

public void mousePressed()
{
    if(inform())
    {
      marginleft = mouseX - formX;
      for(int i = 0; i < Task.size(); i++)
     {
       
       Task t = Task.get(i);
       
       float taskY = map(i,0,Task.size(),formY+textsize,height);
       float rectwidth = (t.getEnd()-t.getStart()) * blank;
       
       if(marginleft >= (t.getStart() - 1) * blank - 20 && marginleft <= (t.getStart() - 1) * blank + rectwidth  && mouseY >= taskY- textsize && mouseY <= taskY + textsize)
       {
         System.out.println("hit box of "+t.getTask()+ " has been set");
         
         //now we know the rectangel we click with its start and end data
         border_left = (t.getStart() * blank) + formX;
         
         border_right = (t.getEnd() * blank) + formX;
         
         header = taskY - textsize;
         
         choosentask = i;
       }
     }
    }
    
}


public void mouseDragged()
{
  if(inform())
      {
        Task t = Task.get(choosentask);
        curline =  (mouseX - formX) / blank;
        int targetline = (int)curline + 1;
        
        //set start date
        if(mouseX >= formX && mouseX <= border_left+blank/4 && mouseY >= header && mouseY <= header + 2 * textsize)
        {
          System.out.println("start date choosen");
          
          if(mouseX < border_right - blank)
          {
            t.setStart(targetline);
            border_left = (t.getStart() * blank) + formX;
          }
          else
          {
            System.out.println("Error");
          }
        }
        
        //set end date
        if(mouseX >= formX && mouseX <= border_right + blank/4 && mouseY >= header && mouseY <= header + 2 * textsize)
        {
          System.out.println("end date choosen");

          if(mouseX > border_left + blank)
          {
            t.setEnd(targetline);
            border_right = (t.getEnd() * blank) + formX;
          }
          else
          {
            System.out.println("Error");
          }
        }
              
      }  // to know if we are still in form
}

public void draw()
{      
    background(0);
    displayTasks();
}

}
