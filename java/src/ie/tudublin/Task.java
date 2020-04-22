package ie.tudublin;

import processing.data.TableRow;
    
public class Task
{
    private String Task;
    private int Start,End;
    
public Task(String Task,int Start,int End)
{
    this.Task = Task;
    this.Start = Start;
    this.End = End;
}
    
    
//get correct data type from file
public Task(TableRow tr)
{
    this(tr.getString("Task"),tr.getInt("Start"),tr.getInt("End"));
}
    
//setter and getter
public String getTask()
{
    return Task;
}
    
public void setTask(String Task)
{
    this.Task = Task;
}
    
public int getStart()
{
    return Start;
}
    
public void setStart(int Start)
{
    this.Start = Start;
}
    
public int getEnd()
{
    return End;
}
    
public void setEnd(int End)
{
    this.End = End;
}
    
@Override

public String toString() 
{
	return "Task [Task: " + Task + ", Start at: " + Start + ", End at: " +End+ "]";
}
    }  //class Task closed