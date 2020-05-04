PROBLEM STATEMENT:
The goal of your programming assignment is to build a very simple peer-to-peer system
involving three nodes using sockets. You need to write programs that run on three nodes
and achieve the following task.

>>Elect a node among the three as the leader. The leader node is a peer but keeps
track of the files available on the p2p network together with the IP addresses and
port numbers from where they could be obtained.

Leader Election: Assign identification numbers to each node, setup TCP connections
among the three nodes, exchange the identification numbers and choose the node with the
lowest identity as the leader. The other two peers should now remove any connections
they have between themselves. In setting up the TCP connections you might have to
order the server and client functionality across the three nodes.

IMPLEMENTATION:
 Initially 3 threads were intiated. 
	ThreadDemo class was made which extended the thread class.
	run() and start() methods were overridden.

 Random numbers were generated.
	The random() method of Math class was called.The random methods returns a double value.
	The value was typecasted to int.
 The priority of threads was set based on the random numbers generated.
	The random int values were assigned as piority to the threads.
 The thread with lowest priority serves as SERVER and rest all are CLIENTS.
	ex:
	if(T1.getPriority()>T2.getPriority() || T1.getPriority()>T3.getPriority()){
          T1.start();
          T2.start();T3.start();seq=1;
       }
	If T1 has priority less than both T2 and T3 then it will start first and serve as server.
	seq is a variable to keep track of start of sequence of the threads so that the thread server thread should not exit first. 
 The data files are shared between server and clients.
	A class PrintDemo was created to keep track of files shared between server and client.

OUTPUT:
The output shows initiation of the three nodes(threads).
Server and respectives clients are choosen.
Files(simulation) are shared between them.
All nodes exit with server leaving in the end.
