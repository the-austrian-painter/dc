# Distributed Systems Viva Preparation

This file explains each experiment in question-answer form. The focus is on the aim, required theory, what the code is doing, key concepts, important Java classes/functions, and likely viva points.

## Experiment 1

**Aim:** Analyze the process communication in a distributed system using Remote Procedure Call.

**Files covered:** `rpc/rpc_server.java`, `rpc/rpc_client.java`, `drive/rpc/Server.java`, `drive/rpc/Client.java`

### Q1. What is Remote Procedure Call?

Remote Procedure Call, or RPC, is a communication technique where a program calls a procedure or function that is executed on another machine or another process. To the client, it looks like a normal function call, but internally the request travels through the network to a server.

In this experiment, the client asks the server to perform arithmetic operations such as addition, subtraction, multiplication, and division. The actual calculation happens on the server, so the client is only requesting a remote service.

### Q2. Why is RPC used?

RPC is used to hide network communication details from the programmer. Instead of manually thinking about packets, ports, and message formats every time, the client thinks in terms of remote operations.

It is useful because:

- It separates client logic from server logic.
- It allows one machine to use services provided by another machine.
- It supports distributed applications where computation is divided across systems.
- It makes remote services easier to call.

### Q3. What are applications of RPC?

RPC is used in:

- Distributed file systems.
- Microservices.
- Client-server applications.
- Database servers.
- Remote administration tools.
- Cloud services and APIs.

Modern examples of RPC-like systems include Java RMI, gRPC, XML-RPC, JSON-RPC, and some internal service-to-service communication frameworks.

### Q4. What does this RPC code demonstrate?

Both RPC versions in the repository demonstrate the same idea as a whole:

1. The server starts on port `3000`.
2. The client connects to the server using IP address `127.0.0.1` or localhost.
3. The client sends three values:
   - operation name: `add`, `sub`, `mul`, or `div`
   - first number
   - second number
4. The server reads the request.
5. The server performs the selected arithmetic operation.
6. The server sends the result back to the client.

This is not Java RMI. It is a manual socket-based RPC simulation. The "remote procedure" is represented by the operation name sent from client to server.

### Q5. Why do we not explain the root RPC and drive RPC separately?

Both versions perform the same RPC-style calculator task. The differences are only in coding style:

- The root version uses class names `rpc_server` and `rpc_client`.
- The drive version uses class names `Server` and `Client`.
- Both use `ServerSocket`, `Socket`, `BufferedReader`, and `PrintWriter`.
- The drive server explicitly checks division by zero.
- The root server uses a compact ternary expression to format the reply.

So, for viva, understand the concept as one client-server RPC calculator.

### Q6. What is the role of the server?

The server waits for a client connection and performs the requested function.

Important server steps:

- `new ServerSocket(3000)` opens the server on port `3000`.
- `accept()` waits until a client connects.
- `getInputStream()` receives data from the client.
- `getOutputStream()` sends data back to the client.
- `readLine()` reads the operation and operands.
- `Integer.parseInt()` converts received strings into integers.
- `if` or `compareTo()` checks which operation was requested.
- `println()` sends the result back.

### Q7. What is the role of the client?

The client takes user input and sends it to the server.

Important client steps:

- `new Socket("127.0.0.1", 3000)` connects to the server.
- `BufferedReader` reads input from keyboard and server.
- `PrintWriter` sends operation and numbers to the server.
- The client waits for the server's result and prints it.

### Q8. What are key Java classes used?

- `ServerSocket`: Creates a server that listens on a port.
- `Socket`: Creates a connection between client and server.
- `BufferedReader`: Reads text line by line.
- `InputStreamReader`: Converts byte stream into character stream.
- `PrintWriter`: Sends text output to the socket.
- `Integer.parseInt()`: Converts string data into integer data.

### Q9. What is marshalling and unmarshalling in this experiment?

Marshalling means converting procedure arguments into a format that can be sent over the network. Here, the client converts operation and numbers into text lines.

Unmarshalling means reading that data at the server side and converting it back into usable values. Here, the server reads strings using `readLine()` and converts numbers using `Integer.parseInt()`.

### Q10. What is the main limitation of this RPC implementation?

This is a simple classroom simulation. It does not support:

- Multiple clients at the same time.
- Formal RPC stubs.
- Object serialization.
- Authentication.
- Exception handling for invalid operations in all cases.
- Network failure handling.

The root version can also fail if division by zero is entered, while the drive version handles that case.

### Q11. What should be said in viva if asked "How does RPC happen here?"

The client sends the name of the function and its parameters to the server through a socket. The server receives the request, executes the matching arithmetic function locally, and sends the result back. This simulates RPC because the client is requesting execution of a procedure on a remote process.

## Experiment 2

**Aim:** Demonstrate Inter-process communication in Client-Server Environment using Java.

**Files covered:** `ipc/IPCServer.java`, `ipc/IPCClient.java`, `drive/ipc/IPCServer.java`, `drive/ipc/IPCClient.java`

### Q1. What is IPC?

IPC stands for Inter-process Communication. It is a mechanism that allows two or more processes to exchange data. These processes may be running on the same machine or on different machines connected through a network.

In this experiment, IPC is implemented using Java sockets. The client sends two integers to the server, and the server sends back their sum.

### Q2. Why is IPC needed?

IPC is needed because processes normally have separate memory spaces. One process cannot directly access the variables of another process. IPC provides a controlled way for processes to communicate.

IPC is used for:

- Client-server systems.
- Distributed systems.
- Operating system services.
- Chat applications.
- Database communication.
- Sharing data between independent programs.

### Q3. What does the IPC code do?

The code demonstrates a simple client-server communication flow:

1. The server starts on port `1200`.
2. The client connects to the server.
3. The client reads two numbers from the user.
4. The client sends both numbers to the server.
5. The server reads the numbers.
6. The server calculates the sum.
7. The server sends the sum back to the client.
8. The client prints the result.

### Q4. How is this different from Experiment 1 RPC?

Experiment 1 sends an operation name and parameters, so it looks like calling a remote function.

Experiment 2 focuses on direct data exchange between two processes. The operation is fixed: the server always adds two numbers. It demonstrates basic IPC rather than a general remote procedure interface.

### Q5. Which Java classes are important in the IPC code?

- `ServerSocket`: Used by the server to listen on port `1200`.
- `Socket`: Used to create a connection between server and client.
- `DataInputStream`: Reads primitive data types like `int`.
- `DataOutputStream`: Writes primitive data types like `int`.
- `BufferedReader`: Reads keyboard input on the client side.
- `Integer.parseInt()`: Converts keyboard text into integers.

### Q6. Why are `DataInputStream` and `DataOutputStream` used?

They are used because this program sends integers directly using `writeInt()` and `readInt()`. This is different from the RPC code, where numbers are sent as text lines.

In this IPC program:

- Client uses `writeInt(a)` and `writeInt(b)`.
- Server uses `readInt()` twice.
- Server sends result using `writeInt(c)`.
- Client receives result using `readInt()`.

### Q7. What is the server's responsibility?

The server:

- Opens port `1200`.
- Waits for the client using `accept()`.
- Receives two integers.
- Adds the integers.
- Sends the result.
- Closes the connection.

### Q8. What is the client's responsibility?

The client:

- Connects to `localhost` on port `1200`.
- Reads two numbers from the user.
- Sends the numbers to the server.
- Waits for the result.
- Displays the result.

### Q9. Is this connection-oriented or connectionless communication?

This is connection-oriented communication because it uses TCP sockets. TCP establishes a reliable connection before data transfer.

### Q10. What is the main limitation of this IPC code?

The root version handles one client and then exits. It is good for demonstrating IPC, but it is not a full production server. A real server would usually handle multiple clients, validate input, and manage errors more carefully.

## Experiment 3

**Aim:** Program to demonstrate Group Communication using Java.

**Files covered:** `group-com/group_server.java`, `group-com/group_master.java`, `group-com/group_slave.java`, `drive/group-com/Server.java`, `drive/group-com/master.java`, `drive/group-com/slave1.java`, `drive/group-com/slave2.java`

### Q1. What is group communication?

Group communication is a communication model where one message can be delivered to multiple processes that belong to a group. Instead of one sender talking to one receiver, a sender can communicate with many members.

It is useful in distributed systems because many applications require coordination among multiple nodes.

### Q2. Why is group communication used?

Group communication is used for:

- Chat systems.
- Replicated servers.
- Distributed databases.
- Multiplayer games.
- Cluster coordination.
- Distributed monitoring.
- Broadcasting updates to many nodes.

### Q3. What does this code demonstrate?

The code demonstrates a simple group chat system.

The server listens on port `9001`. Multiple clients can connect to it. When one client sends a message, the server broadcasts that message to all connected clients.

The root version has:

- `group_server`: central broadcast server.
- `group_master`: a client that can send and receive messages.
- `group_slave`: a client that mainly receives and displays broadcast messages.

The drive version has the same idea with:

- `Server`
- `master`
- `slave1`
- `slave2`

### Q4. What is the role of the group server?

The server:

- Opens a `ServerSocket` on port `9001`.
- Accepts multiple client connections.
- Creates a new `Handler` thread for every client.
- Stores each client's `PrintWriter` in a `Vector`.
- Broadcasts messages to all connected clients.

### Q5. Why is a thread used for each client?

Each client should be able to send messages independently. If the server handled only one client at a time, other clients would have to wait.

Using `new Handler(ss.accept()).start()` allows each connected client to run in a separate thread. This makes the server concurrent.

### Q6. Why is `Vector<PrintWriter>` used?

The server needs to remember the output stream of every connected client. `Vector<PrintWriter>` stores these output streams.

When a message arrives, the server loops through the vector:

```java
for (PrintWriter p : clients) p.println(msg);
```

This sends the same message to every group member.

### Q7. What are the key functions/classes used?

- `ServerSocket(9001)`: Starts the group server.
- `accept()`: Accepts a new client.
- `Thread.start()`: Runs each client handler concurrently.
- `Socket`: Represents each client connection.
- `BufferedReader.readLine()`: Reads client name and messages.
- `PrintWriter.println()`: Sends messages to clients.
- `Vector`: Stores all connected clients' writers.

### Q8. What is the flow of group communication?

1. Start the server.
2. Start master and slave clients.
3. Each client enters a name.
4. Server stores each client's output stream.
5. When a client sends a message, the server receives it.
6. Server broadcasts the message to all clients.
7. Clients display the message.

### Q9. What is the difference between master and slave in this code?

In the root version, `group_master` can actively send messages and also receive messages. `group_slave` connects to the same server and displays messages.

In the drive version, `master` sends a message when the name starts with `master`, while `slave1` and `slave2` mainly receive messages.

This is a naming convention for the experiment. The actual communication still happens through the central server.

### Q10. What is a limitation of this implementation?

This program does not implement advanced group communication features such as:

- Reliable multicast.
- Message ordering.
- Membership failure detection.
- Removing disconnected clients from the vector.
- Authentication.

It is a simple broadcast-based group communication demonstration.

## Experiment 4

**Aim:** Program to demonstrate Berkeley Clock Synchronization algorithm.

**Files covered:** `berkley/Berkley.java`, `drive/berkley/Berkley.java`

### Q1. What is clock synchronization?

Clock synchronization is the process of making the clocks of different machines in a distributed system show approximately the same time.

It is important because distributed systems do not have a single global clock. Each machine has its own local clock, and these clocks can drift over time.

### Q2. Why is clock synchronization needed?

Clock synchronization is needed for:

- Ordering events.
- Logging events correctly.
- Distributed databases.
- File timestamps.
- Mutual exclusion algorithms.
- Security protocols.
- Debugging distributed systems.

### Q3. What is the Berkeley algorithm?

The Berkeley clock synchronization algorithm is a centralized clock synchronization algorithm.

One machine acts as the time master or coordinator. The master asks other nodes for their times, calculates the time differences, finds the average offset, and then tells each node how much to adjust its clock.

Unlike Cristian's algorithm, Berkeley does not depend on an external time server. It synchronizes all machines in a group to their average time.

### Q4. What does this code demonstrate?

The code simulates Berkeley clock synchronization using user-entered times.

The root version:

- Converts hours, minutes, and seconds into seconds using `toSec()`.
- Finds each node's difference from the master time.
- Calculates the average offset.
- Prints adjusted times.

The drive version:

- Uses the system time as the time server's time.
- Reads node times from the user.
- Calculates differences using `diff()`.
- Calculates average using `average()`.
- Adjusts node times using `sync()`.

Both versions demonstrate the same concept: nodes adjust their clocks based on the average difference.

### Q5. What is the main formula in Berkeley algorithm?

Conceptually:

- Find offset for each node.
- Include master offset as `0`.
- Calculate average offset.
- Adjust each clock toward the average time.

A useful viva formula is:

```text
adjustment for node = average offset - node offset
```

This means nodes that are ahead are slowed down and nodes that are behind are moved forward.

### Q6. What are the key functions in the root code?

- `toSec(int h, int m, int s)`: Converts time into total seconds.
- `toTime(int t)`: Converts seconds back into `hour:minute:second` format.
- `main()`: Reads input, calculates differences, average, and adjusted times.

### Q7. What are the key functions in the drive code?

- `diff(...)`: Finds time difference between the time server and a node.
- `average(...)`: Calculates average time difference.
- `sync(...)`: Applies correction to node clocks.
- `Date`: Used to get current server time.
- `BufferedReader`: Used to read input.

### Q8. What is an offset?

Offset is the difference between two clocks.

Example:

```text
node time = 10:05:00
master time = 10:00:00
offset = +300 seconds
```

The node is 300 seconds ahead of the master.

### Q9. Does this code use actual network communication?

No. This is a simulation. The node times are entered by the user. A real Berkeley implementation would send messages between machines, estimate message delays, and then send clock adjustment commands.

### Q10. What is the main limitation?

The code demonstrates the calculation part only. It does not implement real network polling, message delay compensation, or gradual clock adjustment.

In real systems, clocks are usually adjusted gradually instead of being changed suddenly, because sudden clock jumps can affect running applications.

## Experiment 5

**Aim:** Implement a non-token-based Bully Election algorithm.

**Files covered:** `bully-election/Bully.java`, `drive/bully-election/BullyAlg.java`

### Q1. What is leader election?

Leader election is the process of selecting one process as the coordinator or leader in a distributed system.

A coordinator may be responsible for:

- Managing shared resources.
- Coordinating distributed transactions.
- Handling synchronization.
- Controlling access to a critical section.

### Q2. What is the Bully Election algorithm?

The Bully algorithm is a leader election algorithm where each process has a unique priority or process ID. The process with the highest active ID becomes the coordinator.

It is called "bully" because a higher-priority process can take over leadership from lower-priority processes.

### Q3. Why is it called non-token-based?

It is non-token-based because no special token is passed around to decide leadership. Instead, processes exchange election messages and decide the coordinator based on process IDs and availability.

### Q4. When is the Bully algorithm started?

The algorithm starts when a process detects that the current coordinator has failed.

The detecting process becomes the initiator and sends election messages to all processes with higher IDs.

### Q5. What is the basic Bully algorithm flow?

1. A process detects coordinator failure.
2. It sends election messages to all processes with higher IDs.
3. If no higher process replies, the initiator becomes coordinator.
4. If a higher process replies, that higher process takes over the election.
5. Finally, the highest active process announces itself as coordinator.

### Q6. What does the root code do?

The root code is a simple simulation.

It asks for:

- Number of processes.
- Failed coordinator.
- Initiator process.

Then it:

- Shows that the initiator detects coordinator failure.
- Sends election messages to higher-numbered processes.
- Marks the failed process.
- Chooses the highest responding process as new coordinator.

### Q7. What does the drive code add?

The drive version is more interactive.

It allows the user to:

- Crash a process.
- Recover a process.
- Display the current coordinator.
- Exit.

It stores process status in an array `p[]`, where a process is active or crashed. If the coordinator crashes, it runs the election and selects the highest active process.

### Q8. What are the key variables and functions?

- `n`: Number of processes.
- `failed`: Failed coordinator in root code.
- `init`: Initiator process.
- `coord` or `cood`: Current coordinator.
- `p[]`: Process status array in drive code.
- `election()`: Starts the election in drive code.
- `bully()`: Provides the menu-driven simulation in drive code.

### Q9. Why does the highest process become coordinator?

In the Bully algorithm, higher process ID means higher priority. Therefore, the highest active process has the strongest claim to become coordinator.

### Q10. What are the limitations of this code?

The code is a simulation. It does not use real network messages, timeouts, or process failure detection. It prints the election steps on the console.

For viva, explain the real algorithm using messages such as:

- `ELECTION`
- `OK`
- `COORDINATOR`

The code represents those messages using printed statements.

## Experiment 6

**Aim:** Implement a program to demonstrate Mutual Exclusion using java.

**Files covered:** `mutual-exclusion/mutual_server.java`, `mutual-exclusion/mutual_client1.java`, `mutual-exclusion/mutual_client2.java`, `drive/mutual-exclusion/MutualServer.java`, `drive/mutual-exclusion/ClientOne.java`, `drive/mutual-exclusion/ClientTwo.java`, `drive/ex-mutual-exclusion/CoordinatorME.java`, `drive/ex-mutual-exclusion/PriorityME.java`

### Q1. What is mutual exclusion?

Mutual exclusion is a property that ensures only one process can enter a critical section at a time.

A critical section is a part of code where a shared resource is accessed, such as:

- Shared file.
- Shared database record.
- Printer.
- Common memory variable.
- Central server resource.

### Q2. Why is mutual exclusion needed in distributed systems?

In distributed systems, multiple processes may try to access the same resource. Without mutual exclusion, race conditions can occur.

Mutual exclusion ensures:

- Only one process uses the resource at a time.
- Data remains consistent.
- Concurrent access does not corrupt shared state.

### Q3. What type of mutual exclusion is mainly demonstrated here?

The main `mutual-exclusion` code demonstrates token-based mutual exclusion.

A token is a special message. Only the process holding the token is allowed to enter the critical section. After finishing, it passes the token to another process.

### Q4. What is the role of `mutual_server`?

`mutual_server` listens on port `7000`. It accepts client connections and prints the data sent by clients.

In this experiment, sending data to the server represents entering the critical section.

Important parts:

- `new ServerSocket(7000)`: Starts the server.
- `accept()`: Accepts client connection.
- `new Thread(...)`: Handles each client separately.
- `readLine()`: Reads data sent by a client.

### Q5. What is the role of `mutual_client1`?

`mutual_client1`:

- Connects to the main server on port `7000`.
- Opens a `ServerSocket` on port `7001`.
- Waits for `mutual_client2` to connect.
- Starts with the token.
- If it has the token, it may send data to the server.
- Then it passes the token to client 2.

### Q6. What is the role of `mutual_client2`?

`mutual_client2`:

- Connects to the main server on port `7000`.
- Connects to client 1 on port `7001`.
- Waits to receive the token.
- If it receives the token, it may send data to the server.
- Then it sends the token back to client 1.

### Q7. How does the token provide mutual exclusion?

Only one client has the string `"Token"` at a time. A client can send data to the server only after receiving the token.

Because the token is passed between the two clients, both clients cannot enter the critical section simultaneously.

### Q8. What are the key Java classes/functions used?

- `ServerSocket`: Used by the server and by client 1 for token connection.
- `Socket`: Used for client-server and client-client communication.
- `BufferedReader`: Reads incoming token or data.
- `PrintWriter` / `PrintStream`: Sends token or data.
- `Thread`: Allows the server to handle multiple clients.
- `Scanner` / `BufferedReader`: Reads user input.

### Q9. What do the drive mutual-exclusion files do?

The drive files `MutualServer`, `ClientOne`, and `ClientTwo` implement the same token-passing idea with different class names and slightly different I/O classes.

The token flow is:

```text
ClientOne -> ClientTwo -> ClientOne -> ...
```

The process holding the token gets permission to send data.

### Q10. What do `CoordinatorME` and `PriorityME` demonstrate?

These files are additional mutual exclusion simulations.

`CoordinatorME` demonstrates centralized mutual exclusion:

- Processes send requests to a coordinator.
- The coordinator stores requests in a priority queue.
- The coordinator grants access to one process at a time.

`PriorityME` demonstrates priority scheduling with aging:

- Processes have priorities.
- Waiting processes gradually get increased priority.
- Aging helps prevent starvation.

### Q11. What is starvation?

Starvation happens when a process waits forever because other processes keep getting access before it.

In priority-based mutual exclusion, low-priority processes may starve. Aging prevents starvation by increasing the priority of waiting processes.

### Q12. What are limitations of this implementation?

The token-passing implementation is limited to two clients. It does not handle token loss, client crash, timeout, or dynamic joining of new processes.

The coordinator examples are simulations and do not use actual network communication.

## Experiment 7

**Aim:** Implement a deadlock management system using the Chandy-Misra-Haas algorithm.

**Files covered:** `chanday-misra/deadlock.java`, `drive/chanday-misra/ChandyMisraHaas.java`

### Q1. What is deadlock?

Deadlock is a condition where a set of processes are blocked forever because each process is waiting for a resource held by another process in the same set.

Example:

```text
P1 waits for P2
P2 waits for P3
P3 waits for P1
```

This forms a cycle, so no process can continue.

### Q2. What are the necessary conditions for deadlock?

The four Coffman conditions are:

- Mutual exclusion: A resource is held by only one process at a time.
- Hold and wait: A process holds one resource and waits for another.
- No preemption: Resources cannot be forcibly taken.
- Circular wait: Processes form a waiting cycle.

### Q3. What is a wait-for graph?

A wait-for graph is a directed graph where:

- Each node represents a process.
- An edge from `Pi` to `Pj` means `Pi` is waiting for `Pj`.

If the wait-for graph contains a cycle, deadlock may exist.

### Q4. What is the Chandy-Misra-Haas algorithm?

Chandy-Misra-Haas is a distributed deadlock detection algorithm. It uses probe messages to detect cycles in a wait-for graph.

A probe message is usually written as:

```text
(initiator, sender, receiver)
```

If a probe sent by an initiator eventually returns to the initiator, a cycle exists and deadlock is detected.

### Q5. What does the code do?

The code takes a wait-for graph as an adjacency matrix.

The root version:

- Reads number of processes.
- Reads the wait-for graph.
- Reads the initiator process.
- Creates probe messages from the initiator to processes it is waiting for.
- Checks if a probe points back to the initiator.
- Prints whether deadlock is detected.

The drive version:

- Reads and displays the wait-for graph.
- Creates message objects for wait-for edges.
- Checks whether any message destination matches the initiator.
- Prints the detection result.

### Q6. What is the `Message` class?

The `Message` class represents a probe.

In the root code:

- `i`: initiator
- `j`: sender
- `k`: receiver

In the drive code:

- `initiator`: process that started detection
- `from`: sender process
- `to`: receiver process

The `toString()` method prints the probe in readable form.

### Q7. What are the key Java concepts used?

- `int[][] graph`: Stores the wait-for graph as an adjacency matrix.
- `Scanner`: Reads user input.
- `ArrayList<Message>`: Stores probe messages.
- Nested loops: Traverse graph edges.
- Boolean flag `deadlock` or `isDeadlock`: Stores detection result.

### Q8. How should this be explained in viva?

Say that each blocked process sends probe messages along dependency edges. If the initiator receives its own probe back, it means there is a cycle in the wait-for graph. A cycle means deadlock.

The code simulates this by creating probe objects from the wait-for graph and checking whether the probe path reaches back to the initiator.

### Q9. What is the limitation of the code?

The code is a simplified simulation. A full Chandy-Misra-Haas algorithm would forward probes step by step through dependent processes and handle multiple sites in a real distributed system.

This code focuses on the concept of probe generation and cycle detection using a wait-for graph.

## Experiment 8

**Aim:** Demonstrate a program that uses a static load-balancing scheme to assign tasks across multiple servers.

**Files covered:** `load-balancer/LoadBalancer.java`, `drive/load-balancer/LoadBalancer.java`

### Q1. What is load balancing?

Load balancing is the process of distributing work among multiple servers so that no single server is overloaded.

It improves:

- Performance.
- Resource utilization.
- Availability.
- Response time.

### Q2. What is static load balancing?

Static load balancing assigns tasks based on a fixed rule, without checking the current load or health of servers at runtime.

In this experiment, tasks are distributed almost equally among servers using simple arithmetic.

### Q3. How does the code distribute processes?

The code reads:

- Number of servers.
- Number of processes or tasks.

Then it calculates:

```text
base load = processes / servers
extra load = processes % servers
```

Every server gets `base load` processes. The first few servers get one extra process if there is a remainder.

Example:

```text
processes = 10
servers = 3
base = 10 / 3 = 3
extra = 10 % 3 = 1
```

So:

```text
Server A = 4
Server B = 3
Server C = 3
```

### Q4. Which function performs the distribution?

In the root code:

- `show(int s, int p)` prints the load distribution.

In the drive code:

- `printLoad(int servers, int Processes)` prints the load distribution.

Both functions use integer division and modulus.

### Q5. What menu options are provided?

The program allows the user to:

- Add servers.
- Remove servers.
- Add processes.
- Remove processes.
- Exit.

After every change, the load is recalculated and displayed.

### Q6. Why is this called a static scheme?

It is static because the assignment depends only on the number of servers and processes. It does not check:

- CPU usage.
- Memory usage.
- Network delay.
- Server health.
- Queue length.

### Q7. What are key Java concepts used?

- `Scanner`: Reads input.
- `switch`: Handles menu choices.
- `Math.max()`: Used in the root code to avoid invalid negative or zero values.
- Integer division `/`: Calculates base load.
- Modulus `%`: Calculates remaining extra processes.
- Character arithmetic `(char)('A' + i)`: Names servers as A, B, C, and so on.

### Q8. What are applications of load balancing?

Load balancing is used in:

- Web servers.
- Cloud platforms.
- Distributed databases.
- Microservices.
- Parallel computing.
- Content delivery networks.

### Q9. What is the limitation of this program?

This program does not execute real tasks on real servers. It only calculates and prints assignment counts.

It also does not use dynamic load information. A real load balancer may use algorithms such as round robin, least connections, weighted round robin, least response time, or consistent hashing.

## Experiment 9

**Aim:** Implement a program to demonstrate a byzantine failure in a distributed system using Java.

**Files covered:** `byzantine/Byzantine.java`, `drive/byzantine/Byzantine.java`

### Q1. What is a Byzantine failure?

A Byzantine failure is a failure where a process behaves unpredictably or maliciously. It may send wrong, inconsistent, or fake responses.

This is more difficult than a crash failure because a crashed node simply stops responding, but a Byzantine node may continue responding with incorrect data.

### Q2. Why are Byzantine failures important?

Byzantine failures are important in systems where correctness and trust are critical.

Examples:

- Banking systems.
- Blockchain networks.
- Military systems.
- Distributed ledgers.
- Replicated databases.
- Safety-critical systems.

### Q3. What does this code demonstrate?

The code creates a group of replicas:

- Honest replicas return the correct response.
- One faulty or Byzantine replica returns random fake or error responses.

The client sends the same request to all replicas. Then it counts the responses and chooses the majority result as the final decision.

### Q4. What is the role of the `Replica` interface?

The `Replica` interface defines a common method that all replica types must implement.

In the root code:

```java
String process(String req);
```

In the drive code:

```java
String processRequest(String request);
```

This allows the client to treat honest and faulty replicas uniformly.

### Q5. What is an honest replica?

An honest replica always returns the correct response.

For example:

```text
Processed: OperationX
```

This represents a server that follows the protocol correctly.

### Q6. What is a faulty or Byzantine replica?

A faulty replica returns incorrect output randomly.

Examples:

```text
Error in OperationX
Fake: OperationX
```

This simulates malicious or arbitrary behavior.

### Q7. How does the client reach a decision?

The client sends the request to every replica and stores response counts in a `HashMap`.

The response with the highest count, or the response that reaches quorum in the drive version, is chosen as the final decision.

### Q8. What is quorum?

A quorum is the minimum number of matching responses needed to accept a result.

The drive version calculates simple majority quorum as:

```java
int quorumSize = (replicas.size() / 2) + 1;
```

For 4 replicas, quorum is 3.

### Q9. What is the `3f + 1` rule?

In Byzantine Fault Tolerance, to tolerate `f` Byzantine faulty nodes, a system usually needs at least:

```text
3f + 1 replicas
```

For `f = 1`, at least `4` replicas are needed. This experiment uses 3 honest replicas and 1 faulty replica, so it matches the simple demonstration of tolerating one faulty node.

### Q10. What Java concepts are used?

- `interface`: Defines common replica behavior.
- `implements`: Used by honest and faulty replicas.
- `ArrayList`: Stores replicas.
- `HashMap`: Counts responses.
- `Math.random()`: Generates random faulty behavior.
- `AtomicInteger`: Used in the drive code for response counts.
- Polymorphism: The client calls replicas through the `Replica` interface.

### Q11. What is the limitation of this code?

This is a simplified Byzantine failure demonstration. It does not implement a full Byzantine Fault Tolerance protocol like PBFT.

A real BFT system would include:

- Digital signatures or message authentication.
- Request ordering.
- Multiple communication phases.
- View change or leader replacement.
- Strong quorum rules.
- Network fault handling.

## Experiment 10

**Aim:** Compare Distributed File Systems- AFS and GFS.

**Files covered:** `afs-gfs/AFS_Server.java`, `afs-gfs/AFS_Client.java`, `afs-gfs/GFS.java`, `drive/afs-gfs/AFS_Server.java`, `drive/afs-gfs/AFS_Client1.java`, `drive/afs-gfs/AFS_Client2.java`, `drive/afs-gfs/GFS_Simulation.java`

### Q1. What is a distributed file system?

A distributed file system stores and manages files across multiple machines, but presents them to users as if they are part of one file system.

It allows users and applications to access remote files over a network.

### Q2. What is AFS?

AFS stands for Andrew File System. It is a distributed file system designed for sharing files among many clients.

Important AFS ideas:

- Client-server file access.
- File caching at client side.
- Whole-file caching in classic AFS.
- Callback mechanism to inform clients when cached data becomes invalid.
- Good scalability for read-heavy workloads.

### Q3. What does the AFS code demonstrate?

The AFS code implements a simple socket-based file server.

The server:

- Creates a directory named `afs_files`.
- Listens on port `5555`.
- Accepts client connections.
- Handles `write` and `read` commands.
- Stores file contents using Java file APIs.

The client:

- Connects to the server.
- Displays a menu.
- Sends write or read commands.
- Prints the server response.

This is a simplified AFS-style file service. It demonstrates remote file read/write, not the complete real AFS architecture.

### Q4. What are the key functions/classes in AFS code?

- `ServerSocket(5555)`: Starts the file server.
- `Socket`: Connects client and server.
- `BufferedReader.readLine()`: Reads commands.
- `PrintWriter.println()`: Sends commands and responses.
- `File.mkdirs()`: Creates the storage directory.
- `Files.write()`: Writes file content.
- `Files.readAllBytes()`: Reads file content.
- `Files.exists()`: Checks whether a file exists.
- `Paths.get()`: Creates a file path.
- `Thread` or `ClientHandler`: Handles multiple clients in the drive version.

### Q5. What commands are used in the AFS simulation?

The client sends text commands:

```text
write filename content
read filename
exit
```

The server parses these commands using `startsWith()` and `split()`.

### Q6. What is GFS?

GFS stands for Google File System. It is a distributed file system designed by Google for very large files and large-scale data processing.

Important GFS ideas:

- Files are split into large chunks.
- A master node stores metadata.
- Chunk servers store actual data chunks.
- Chunks are replicated for fault tolerance.
- Clients ask the master for metadata, then communicate with chunk servers.

### Q7. What does the GFS code demonstrate?

The GFS code simulates:

- Splitting a file into chunks.
- Giving each chunk a unique `UUID`.
- Storing chunks on chunk servers.
- Maintaining metadata in a master node.
- Reading a file by collecting chunks in order.

The root version splits data into chunks of 5 characters. The drive version uses chunks of 10 characters and prints which chunk server stores each chunk.

### Q8. What are the important classes in the GFS simulation?

- `Chunk`: Represents a piece of file data and has a unique ID.
- `ChunkServer`: Stores chunks in a map.
- `Master` or `MasterNode`: Stores metadata about file-to-chunk mapping and chunk locations.
- `Client`: Present in drive code, used to write and read files through the master.
- `UUID`: Generates unique chunk identifiers.
- `Map`: Stores file metadata and chunk storage.
- `List`: Maintains ordered chunk IDs and replica locations.

### Q9. What is metadata in GFS?

Metadata is data about data.

In GFS, metadata includes:

- File name.
- List of chunk IDs.
- Chunk locations.
- Replica information.

In the code, metadata is stored using maps such as:

```java
Map<String, List<UUID>> fileToChunks
Map<UUID, List<ChunkServer>> chunkReplicas
```

### Q10. What is chunking?

Chunking means dividing a large file into smaller fixed-size pieces called chunks.

In the code, a string is split into smaller substrings. Each substring becomes a `Chunk`.

Chunking helps large distributed file systems store and process huge files across many servers.

### Q11. What is replication?

Replication means storing copies of data on multiple servers.

In the GFS simulation, the same chunk is stored on multiple `ChunkServer` objects. If one server fails, another replica can still provide the chunk.

### Q12. Compare AFS and GFS.

| Point | AFS | GFS |
| --- | --- | --- |
| Full form | Andrew File System | Google File System |
| Main goal | Shared remote file access | Large-scale data storage and processing |
| Typical file size | General user files | Very large files |
| Architecture | Clients and file servers | Master and chunk servers |
| Caching | Client-side caching is important | Chunk replication is important |
| Data unit | Whole file or file blocks depending on design | Large chunks |
| Best suited for | University or enterprise shared file access | Big data workloads |
| Fault tolerance | Cache callbacks and server management | Replicated chunks |
| Code simulation | Socket file read/write server | Chunking, metadata, and replication simulation |

### Q13. How should the code comparison be explained in viva?

Say that the AFS code demonstrates a remote file server where clients can read and write files using socket commands. The server stores complete files in a local directory.

The GFS code demonstrates the internal idea of a large distributed file system. It splits a file into chunks, stores chunks on chunk servers, and keeps file-to-chunk metadata in a master node.

So, AFS is shown as remote file access, while GFS is shown as chunk-based distributed storage.

### Q14. What are limitations of these file system simulations?

The AFS code does not implement real AFS caching, callbacks, permissions, or consistency rules.

The GFS code does not use real networking, real disks, fault recovery, leases, checksums, or master replication.

Both programs are simplified to demonstrate the core ideas for academic understanding.

## Quick Common Viva Questions

### Q1. What is the difference between client and server?

A client requests a service. A server provides a service.

In these experiments, clients send requests such as arithmetic operations, numbers, messages, file commands, or data. Servers listen on ports, process requests, and send responses.

### Q2. What is a socket?

A socket is an endpoint for communication between two processes over a network.

In Java:

- `ServerSocket` is used by the server to listen.
- `Socket` is used by the client and accepted server connection to communicate.

### Q3. What is a port number?

A port number identifies a specific service on a machine.

Examples from these experiments:

- `3000`: RPC calculator.
- `1200`: IPC sum program.
- `9001`: Group communication.
- `7000`: Mutual exclusion server.
- `7001`: Token passing between clients.
- `5555`: AFS file server.

### Q4. What is the difference between `BufferedReader` and `DataInputStream`?

`BufferedReader` is mainly used to read text line by line using `readLine()`.

`DataInputStream` is used to read primitive data types such as `int`, `double`, and `boolean` using methods like `readInt()`.

### Q5. What is the difference between `PrintWriter` and `DataOutputStream`?

`PrintWriter` sends text data using methods like `println()`.

`DataOutputStream` sends primitive data types using methods like `writeInt()`.

### Q6. What is the difference between token-based and non-token-based algorithms?

In token-based algorithms, a process must hold a token before entering the critical section or performing a special action.

In non-token-based algorithms, permission is decided using messages, priorities, voting, timestamps, or coordinator decisions.

Examples:

- Token-based: mutual exclusion token passing experiment.
- Non-token-based: Bully election algorithm.

### Q7. What is the difference between crash failure and Byzantine failure?

In crash failure, a process stops working and gives no response.

In Byzantine failure, a process may continue running but gives wrong, fake, or inconsistent responses.

Byzantine failure is harder to handle.

### Q8. What is the main purpose of distributed systems?

The main purpose of distributed systems is to allow multiple independent machines to work together as a single system.

Benefits include:

- Resource sharing.
- Scalability.
- Fault tolerance.
- Better performance.
- Geographic distribution.

