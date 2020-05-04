//JAVA CODE:
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

class PrintDemo {
    static int i=0;
    //Print file number
   public void printCount() {
      try {
         for( i = 5; i > 0; i--) {
            Thread.sleep(1000);
            System.out.println("File "  + i +"shared "+Thread.currentThread().getName());
 
         }
      } catch (Exception e) {
         System.out.println("Thread  interrupted.");
      }
   }
}

class ThreadDemo extends Thread {
   private Thread t;
   private String threadName;
   PrintDemo  PD;
   private static int counter=1;
   public static int port=8765;
   ThreadDemo( String name,  PrintDemo pd) {
      threadName = name;
      PD = pd;
   }
   
   public void run() {
          if(counter==1){
              System.out.println(">>SERVER:"+t.getName());//Highest priority thread enters as server
              counter++;
              try { 
                  ServerSocket server = new ServerSocket(port);//Establish a communication channel
                  System.out.println("Waiting for a client ...");
                   Socket socket = server.accept();//Connect to client
                   //Thread.currentThread().notify();
                   
                   System.out.println("Client found");
              } catch (IOException ex) {
                  Logger.getLogger(ThreadDemo.class.getName()).log(Level.SEVERE, null, ex);
              } 
			System.out.println("Server started"); 
              
          }
          else{
              System.out.println(">>CLient"+t.getName());
              try {
                  InetAddress host = InetAddress.getLocalHost();//get clinet local address
                  Socket socket = null;
                    ObjectOutputStream oos = null;
                    ObjectInputStream ois = null;
                    socket = new Socket(host.getHostName(),port);
                   // Thread.currentThread().notify();
                    Thread.sleep(1000);
              } catch (UnknownHostException ex) {
                  Logger.getLogger(ThreadDemo.class.getName()).log(Level.SEVERE, null, ex);
              } catch (IOException ex) {
                  Logger.getLogger(ThreadDemo.class.getName()).log(Level.SEVERE, null, ex);
              } catch (InterruptedException ex) {
                  Logger.getLogger(ThreadDemo.class.getName()).log(Level.SEVERE, null, ex);
              }
          }    
         PD.printCount();//Print shared file numbers      
      System.out.println("Thread " +  threadName + " exiting.");
      
   }
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}
 public class DemoThread {
   public static void main(String args[]) throws InterruptedException {
      PrintDemo PD = new PrintDemo();
      int r1,r2 = 0,r3 = 0;int seq;
      //Initiate thread
      ThreadDemo T1 = new ThreadDemo( "Thread - 1 ", PD );
      ThreadDemo T2 = new ThreadDemo( "Thread - 2 ", PD );
      ThreadDemo T3 = new ThreadDemo( "Thread - 3 ", PD );
      r1=(int)(Math.random()*10)%3;//generate random number
      if(r1==1){
          r2=2;r3=3;
      }
      else if(r1==2){
          r2=3;r3=1;
      }
      else if(r1==0){
          r1=3;r2=1;r3=2;
      }
      //Set Priority to threads`
      T1.setPriority(r1);
      T2.setPriority(r2);
      T3.setPriority(r3);
      //Start thread based on priority as server and clients
      if(T1.getPriority()>T2.getPriority() || T1.getPriority()>T3.getPriority()){
          T1.start();
          T2.start();T3.start();seq=1;
       }
      else if(T3.getPriority()>T2.getPriority() || T3.getPriority()>T1.getPriority()){
          T3.start();
          T1.start();
          T2.start();seq=3;
      }
      else{
          T2.start();T1.start();T3.start();seq=2;
      }
      // wait for threads to end
      try {
          if(seq==1){
            T1.join();
            T2.join();
            T3.join();   
          }
          else if(seq==2){
              T2.join();
              T1.join();
              T3.join();
          }
          else{
              T3.join();
              T1.join();
              T2.join();
          }
         
      } catch ( Exception e) {
         System.out.println("Interrupted");
      }
   }
}