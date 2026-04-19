# Distributed Systems Module-Wise Viva Questions

This file keeps the important Distributed Systems viva questions module-wise. Repeated questions are merged into the answer that covers more content.

## Module 1: Introduction to Distributed Systems

### Q1. What is a distributed system?

**Answer:**

A distributed system is a collection of independent computers that appear to users as a single coherent system. The computers communicate through a network and coordinate their actions by passing messages.

**Key points:**

- Components are located on different machines.
- Components communicate using a network.
- Users should feel they are using one system.
- Failures can happen independently on different nodes.

**Example:**

Google search, online banking, cloud storage, distributed databases, and web applications are distributed systems.

**Important viva line:**

A distributed system is a group of networked computers that cooperate to provide a common service.

### Q2. What are the types of distributed systems?

**Answer:**

Distributed systems can be classified based on their purpose and architecture.

#### 1. Distributed Computing Systems

These systems are used for high-performance computation. A large task is divided among many machines.

**Examples:**

- Cluster computing
- Grid computing
- Cloud computing

#### 2. Distributed Information Systems

These systems manage, store, and exchange information across multiple machines.

**Examples:**

- Distributed databases
- Banking systems
- Transaction processing systems

#### 3. Distributed Pervasive Systems

These systems contain many small, mobile, or embedded devices.

**Examples:**

- Internet of Things
- Sensor networks
- Smart homes
- Mobile computing systems

#### 4. Client-Server Systems

Clients request services and servers provide services.

**Example:**

A web browser requests a page from a web server.

#### 5. Peer-to-Peer Systems

Each node can act as both client and server.

**Example:**

File sharing systems where every peer can upload and download data.

### Q3. What is the core difference between a Network Operating System and a Distributed Operating System?

**Answer:**

A Network Operating System, or NOS, allows multiple independent computers to communicate over a network, but users are aware that machines are separate.

A Distributed Operating System, or DOS, manages multiple machines and tries to make them appear as one single system.

| Point | Network Operating System | Distributed Operating System |
| --- | --- | --- |
| View to user | User knows machines are separate | System appears as one machine |
| Autonomy | Each machine has its own OS | OS manages all machines together |
| Transparency | Less transparent | More transparent |
| Resource access | User may specify remote machine | System hides resource location |
| Example | Remote login, file sharing over LAN | Amoeba, Sprite, distributed OS designs |

**Example:**

In a NOS, a user may write:

```text
ssh user@server
```

The user knows they are logging into a different machine.

In a DOS, the system may automatically run a process on any machine without the user knowing where it is running.

**Important viva line:**

NOS connects independent systems, while DOS makes multiple systems behave like one system.

### Q4. What is openness in distributed systems?

**Answer:**

Openness means a distributed system is built using standard interfaces, protocols, and rules so that different hardware and software components can work together.

An open distributed system allows:

- New components to be added easily.
- Components from different vendors to interoperate.
- Services to be replaced without changing the whole system.
- Applications to communicate using standard protocols.

**Example:**

A browser on Windows can access a Linux web server because both use standard protocols such as HTTP and TCP/IP.

**Important viva line:**

Openness is achieved through standard interfaces, standard protocols, and well-defined service specifications.

### Q5. What are the different types of transparency in distributed systems, and why are they important?

**Answer:**

Transparency means hiding the complexity of distribution from users and applications. A transparent distributed system appears simple even though it is made of many machines.

#### 1. Access Transparency

Hides differences in data representation and access methods.

**Example:**

A user accesses a remote file using the same operations used for a local file.

#### 2. Location Transparency

Hides the physical location of a resource.

**Example:**

A user accesses `file.txt` without knowing which server stores it.

#### 3. Migration Transparency

Hides movement of resources from one location to another.

**Example:**

A file may move to another server, but the user still accesses it with the same name.

#### 4. Relocation Transparency

Hides movement while a resource is being used.

**Example:**

A mobile user continues a video call while switching networks.

#### 5. Replication Transparency

Hides the fact that multiple copies of a resource exist.

**Example:**

A website may be served from many replicated servers, but the user sees one website.

#### 6. Concurrency Transparency

Hides the fact that multiple users are accessing the same resource at the same time.

**Example:**

Many users book tickets, but the system prevents inconsistent seat allocation.

#### 7. Failure Transparency

Hides failures and recovery from users as much as possible.

**Example:**

If one server fails, another replica serves the request.

#### 8. Persistence Transparency

Hides whether data is in memory or on disk.

**Example:**

The user accesses an object without knowing whether it is stored permanently or temporarily.

**Why transparency is important:**

- It makes distributed systems easier to use.
- It hides network and hardware complexity.
- It improves reliability and user experience.
- It allows resources to be moved, replicated, or recovered without affecting users.

### Q6. Define middleware. What services does it offer to the layers above it?

**Answer:**

Middleware is software that lies between the operating system/network layer and distributed applications. It provides common services that make distributed application development easier.

Middleware hides low-level details such as sockets, data conversion, remote communication, and service discovery.

#### Services offered by middleware

1. **Communication service**

   Provides mechanisms such as RPC, RMI, message queues, and publish-subscribe.

2. **Naming service**

   Helps locate resources and services by name.

   **Example:** Finding a remote object or server by its registered name.

3. **Security service**

   Provides authentication, authorization, encryption, and secure communication.

4. **Transaction service**

   Supports atomic operations across multiple machines.

   **Example:** Banking transaction across multiple databases.

5. **Persistence service**

   Helps store object or application state permanently.

6. **Replication service**

   Manages multiple copies of data or services.

7. **Fault tolerance service**

   Helps detect failures and recover from them.

8. **Concurrency service**

   Controls simultaneous access to shared resources.

9. **Marshalling and unmarshalling**

   Converts data into a network format and back into usable objects or values.

#### Examples of middleware

- RPC systems
- Java RMI
- CORBA
- Message queues
- Web services
- Object request brokers
- Database middleware

**Important viva line:**

Middleware provides a uniform programming interface and hides heterogeneity in distributed systems.

### Q7. Explain 3-tier and n-tier systems.

**Answer:**

A tier is a logical layer of an application. Multi-tier architecture divides an application into layers so that each layer has a specific responsibility.

#### 3-Tier System

A 3-tier system has three layers:

1. Presentation tier
2. Application or business logic tier
3. Data tier

**Presentation tier:**

This is the user interface.

**Example:** Browser, mobile app, desktop app.

**Application tier:**

This contains business logic.

**Example:** Login validation, payment processing, order handling.

**Data tier:**

This stores and manages data.

**Example:** MySQL, Oracle, PostgreSQL.

Flow:

```text
Client browser -> Application server -> Database server
```

#### N-Tier System

An n-tier system has more than three layers.

Example:

```text
Client -> Web server -> Application service -> Authentication service -> Payment service -> Database
```

| Point | 3-tier system | N-tier system |
| --- | --- | --- |
| Layers | Exactly 3 main layers | More than 3 layers |
| Complexity | Simpler | More complex |
| Scalability | Good | Better for large systems |
| Example | Standard web app | Microservices system |

**Important viva line:**

3-tier is a specific layered architecture, while n-tier is a general architecture with many layers or services.

## Module 2: Communication

### Q1. Explain IPC.

**Answer:**

IPC stands for Inter-Process Communication. It allows two or more processes to exchange data.

Processes usually have separate memory spaces, so one process cannot directly access another process's variables. IPC provides a controlled way for processes to communicate.

#### Common IPC mechanisms

- Pipes
- Message queues
- Shared memory
- Semaphores
- Sockets
- RPC

#### IPC in distributed systems

In distributed systems, IPC usually happens through message passing or sockets because processes are on different machines.

### Q2. Explain the steps involved in basic IPC communication.

**Answer:**

Basic client-server IPC usually works as follows:

1. **Server creates a communication endpoint**

   The server opens a port using `ServerSocket`.

2. **Server waits for client**

   The server calls `accept()` and waits for connection.

3. **Client connects to server**

   The client creates a `Socket` using server address and port.

4. **Streams are created**

   Both sides create input and output streams.

5. **Client sends request**

   The client sends data or command to the server.

6. **Server receives and processes request**

   The server reads data, performs the required operation, and prepares a response.

7. **Server sends reply**

   The server sends result back to the client.

8. **Client receives reply**

   The client reads and displays the response.

9. **Connection is closed**

   Sockets and streams are closed if communication is complete.

**Connection with code:**

In the IPC experiment, the client sends two integers to the server. The server adds them and sends the result back.

### Q3. Explain RPC, RMI, and remote object invocation.

**Answer:**

RPC, RMI, and remote object invocation are techniques used to execute code located in another process or machine.

#### RPC

RPC stands for Remote Procedure Call.

In RPC, a client calls a procedure that runs on a remote server.

Example:

```text
Client calls add(10, 5)
Server executes add(10, 5)
Server returns 15
```

The client feels like it is calling a local function, but the function runs remotely.

#### RMI

RMI stands for Remote Method Invocation.

RMI is Java's object-oriented version of RPC. It allows a Java object in one JVM to call methods of an object in another JVM.

Example:

```java
remoteObject.calculateTotal()
```

#### Remote Object Invocation

Remote object invocation means calling methods on an object located in another process or machine.

It usually involves:

- Remote interface
- Remote object implementation
- Client-side stub
- Server-side skeleton or dispatcher
- Registry or naming service

### Q4. How does RPC differ from RMI?

**Answer:**

| Point | RPC | RMI |
| --- | --- | --- |
| Full form | Remote Procedure Call | Remote Method Invocation |
| Style | Procedure-oriented | Object-oriented |
| Called unit | Function or procedure | Method on remote object |
| Language | Can be language-independent | Mainly Java-specific |
| Data passed | Parameters and return values | Objects, methods, parameters |
| Object support | Not naturally object-oriented | Supports remote objects |

#### Stubs and skeletons

**Stub:**

A stub is a client-side proxy. It looks like the remote procedure or remote object to the client. It converts the call into a network message.

**Skeleton or dispatcher:**

A skeleton is server-side code that receives the request, unmarshals parameters, calls the actual method, and sends the result back.

Modern systems may use dispatchers instead of explicitly generated skeletons.

#### Parameter passing

In RPC:

- Simple values are passed by value.
- Pointers cannot be passed directly because the remote machine has a different address space.

In Java RMI:

- Serializable objects are passed by value.
- Remote objects are passed by reference through remote stubs.

**Important viva line:**

RPC calls remote functions, while RMI calls methods on remote objects.

### Q5. Explain synchronous, asynchronous, persistent, and transient communication.

**Answer:**

These terms describe how messages are sent and received.

#### Synchronous Communication

The sender waits until the receiver receives the message or sends a reply.

**Example:**

RPC request-reply communication.

#### Asynchronous Communication

The sender sends the message and continues its work without waiting immediately.

**Example:**

Email or asynchronous API call.

#### Persistent Communication

The communication system stores the message until it can be delivered.

The sender and receiver do not need to be active at the same time.

**Example:**

Message queues and email systems.

#### Transient Communication

The message is delivered only if the receiver is active at that time. If the receiver is unavailable, the message may be lost.

**Example:**

Live audio call or UDP message without storage.

| Type | Meaning | Example |
| --- | --- | --- |
| Synchronous | Sender waits | RPC |
| Asynchronous | Sender does not wait immediately | Email, async calls |
| Persistent | Message is stored until delivered | Message queue |
| Transient | Message is not stored | Live streaming, UDP |

**Important viva line:**

Synchronous/asynchronous describes waiting behavior. Persistent/transient describes whether messages are stored.

### Q6. Differentiate between message-oriented and stream-oriented communication.

**Answer:**

#### Message-Oriented Communication

Data is sent as separate messages. Message boundaries are preserved.

Example:

```text
Message 1: add
Message 2: 10
Message 3: 5
```

**Examples:**

- Message queues
- UDP datagrams
- RPC messages
- Email

#### Stream-Oriented Communication

Data is sent as a continuous stream of bytes. Message boundaries are not automatically preserved.

Example:

```text
Continuous stream: HelloWorld12345
```

The application must decide how to separate data.

**Examples:**

- TCP sockets
- File streams
- Audio streaming
- Video streaming

| Point | Message-oriented | Stream-oriented |
| --- | --- | --- |
| Data form | Separate messages | Continuous byte stream |
| Boundaries | Preserved | Not preserved automatically |
| Suitable for | Request-response messages | Audio, video, file transfer |
| Example | UDP, message queue | TCP |

### Q7. What is group communication?

**Answer:**

Group communication is a method where a message is sent to a group of processes instead of only one process.

Example:

```text
One user sends a group chat message.
All group members receive it.
```

#### Types of group communication

1. **Broadcast**

   Message is sent to all processes in the system.

2. **Multicast**

   Message is sent to selected group members.

3. **Anycast**

   Message is sent to any one member of a group.

#### Uses

- Group chat
- Replicated servers
- Distributed databases
- Multiplayer games
- Cluster management
- Fault tolerance

**Connection with code:**

In the group communication experiment, the server stores connected clients and broadcasts messages to all of them.

### Q8. What are the types of message ordering?

**Answer:**

Ordering is important because messages may arrive in different orders at different processes.

#### 1. FIFO Ordering

If one process sends `m1` before `m2`, receivers deliver `m1` before `m2`.

FIFO preserves order from the same sender.

#### 2. Causal Ordering

Causal ordering preserves cause-effect relationships.

If `m1` causes `m2`, then all processes deliver `m1` before `m2`.

Example:

```text
P1 sends m1: "File updated"
P2 receives m1 and sends m2: "Read updated file"
```

`m1` must be delivered before `m2`.

#### 3. Total Ordering

All processes deliver all messages in the same order.

Example:

```text
P1 delivery order: m1, m2, m3
P2 delivery order: m1, m2, m3
P3 delivery order: m1, m2, m3
```

#### 4. Temporal Ordering

Ordering is based on real physical time. It requires synchronized physical clocks.

| Ordering type | Main rule |
| --- | --- |
| FIFO | Same sender order is preserved |
| Causal | Cause-effect order is preserved |
| Total | Same order at all processes |
| Temporal | Real-time order is preserved |

**Important viva line:**

Causal ordering preserves dependency. Total ordering gives the same order everywhere.

## Module 3: Synchronization

### Q1. Explain clocks, physical clocks, and logical clocks.

**Answer:**

Clocks are used in distributed systems to order events and coordinate actions.

#### Physical Clock

A physical clock measures real-world time.

Examples:

- System clock
- Hardware clock
- Wall clock

Physical clocks are used for:

- Timestamps
- Logs
- Timeouts
- Scheduling

#### Logical Clock

A logical clock does not measure real time. It is used to order events in a distributed system.

Logical clocks are based on counters and messages.

### Q2. Why can't we just rely on physical clocks?

**Answer:**

We cannot fully rely on physical clocks because every machine has its own hardware clock, and these clocks may not show exactly the same time.

Problems:

- Clock drift: Clocks run at slightly different speeds.
- Clock skew: Clocks show different times.
- Network delay: Time messages take time to travel.
- Clock adjustment: Time should not go backward.
- No global clock: There is no single perfect clock shared by all machines.

Example:

```text
Machine A time = 10:00:05
Machine B time = 10:00:09
```

If events happen on both machines, physical timestamps may give the wrong order.

### Q3. How do Lamport logical clocks solve ordering?

**Answer:**

Lamport logical clocks assign numbers to events so that cause-effect order is preserved.

Rules:

1. Each process keeps a counter.
2. Before each local event, increment the counter.
3. When sending a message, attach the counter value.
4. When receiving a message, update the clock:

```text
local clock = max(local clock, received timestamp) + 1
```

If event A happened before event B, Lamport clock ensures:

```text
timestamp(A) < timestamp(B)
```

**Limitation:**

If `timestamp(A) < timestamp(B)`, it does not always mean A caused B. Lamport clocks preserve causality but do not detect concurrency perfectly.

**Important viva line:**

Physical clocks tell real time. Logical clocks tell event order.

### Q4. Explain the core concept of election algorithms. When are they triggered?

**Answer:**

Election algorithms select one process as the coordinator or leader in a distributed system.

A coordinator may manage:

- Shared resources
- Mutual exclusion
- Transactions
- Failure recovery
- Group decisions

#### When are election algorithms triggered?

They are triggered when:

- The current coordinator fails.
- A process detects that the coordinator is not responding.
- A higher-priority process recovers, depending on the algorithm.
- The system starts and no coordinator exists.

### Q5. Explain the Bully election algorithm.

**Answer:**

In the Bully algorithm, each process has a unique ID or priority. The highest active process becomes the coordinator.

Basic steps:

1. A process detects coordinator failure.
2. It sends `ELECTION` messages to all processes with higher IDs.
3. If no higher process replies, it becomes coordinator.
4. If a higher process replies `OK`, that higher process takes over the election.
5. The highest active process announces itself using a `COORDINATOR` message.

**Why it is called bully:**

A higher-priority process can take over from lower-priority processes.

### Q6. Explain the Ring election algorithm.

**Answer:**

In the Ring algorithm, processes are arranged in a logical ring.

Basic steps:

1. A process detects coordinator failure.
2. It sends an election message to the next process in the ring.
3. Each process adds its ID to the message and forwards it.
4. When the message returns to the initiator, the highest ID is selected.
5. A coordinator message is circulated to announce the result.

**Difference from Bully:**

Bully sends messages to higher-ID processes directly. Ring passes messages around a logical ring.

### Q7. What is mutual exclusion?

**Answer:**

Mutual exclusion ensures that only one process can enter a critical section at a time.

A critical section is a part of a program where a shared resource is accessed.

Examples of shared resources:

- File
- Printer
- Database record
- Shared variable

### Q8. What is the difference between token-based and non-token-based mutual exclusion algorithms?

**Answer:**

| Point | Token-based | Non-token-based |
| --- | --- | --- |
| Main idea | Process needs a token to enter CS | Process needs permission or voting |
| Message type | Token is passed | Request/reply messages |
| Example | Token ring, Raymond's Tree | Ricart-Agrawala |
| Risk | Token loss | More messages and waiting |
| Entry rule | Holder of token enters CS | Process enters after permissions |

### Q9. Briefly explain Ricart-Agrawala algorithm.

**Answer:**

Ricart-Agrawala is a non-token-based distributed mutual exclusion algorithm.

Basic idea:

1. A process that wants to enter the critical section sends a `REQUEST` message to all other processes.
2. The request contains timestamp and process ID.
3. A process replies immediately if it is not interested in the critical section.
4. If it is also interested, it compares timestamps.
5. Lower timestamp gets priority.
6. The requesting process enters the critical section only after receiving replies from all other processes.

**Important point:**

It uses logical timestamps to decide priority.

### Q10. Briefly explain Raymond's Tree algorithm.

**Answer:**

Raymond's Tree algorithm is a token-based mutual exclusion algorithm.

Processes are arranged as a logical tree. The token is held by one process. A process must get the token before entering the critical section.

Basic idea:

1. Each process knows the direction toward the token holder.
2. A process sends a request along the tree path toward the token.
3. Requests are queued.
4. The token moves along the tree to the requesting process.
5. After using the critical section, the token is passed to the next requester.

**Important point:**

Only the token holder can enter the critical section.

### Q11. How does the Chandy-Misra-Haas algorithm detect deadlocks in a distributed environment?

**Answer:**

Chandy-Misra-Haas is a distributed deadlock detection algorithm. It uses probe messages to detect cycles in the wait-for graph.

#### Wait-for graph

A wait-for graph represents which process is waiting for which other process.

Example:

```text
P1 -> P2 means P1 is waiting for P2
```

#### Probe message

A probe is usually written as:

```text
(initiator, sender, receiver)
```

#### Detection steps

1. A blocked process starts deadlock detection.
2. It sends probe messages to processes it is waiting for.
3. Each blocked receiver forwards the probe to processes it is waiting for.
4. If the probe returns to the initiator, a cycle exists.
5. A cycle in the wait-for graph means deadlock.

Example:

```text
P1 waits for P2
P2 waits for P3
P3 waits for P1
```

If P1 receives its own probe back, deadlock is detected.

**Connection with code:**

The Chandy-Misra-Haas code stores the wait-for graph as an adjacency matrix and represents probes using a `Message` class.

## Module 4: Resource and Process Management

### Q1. What is the exact difference between load balancing and load sharing?

**Answer:**

Load balancing and load sharing both try to improve resource utilization, but their goals are different.

#### Load Balancing

Load balancing tries to distribute load evenly among all processors.

Goal:

```text
Make processor loads as equal as possible.
```

It is stricter and needs more load information.

#### Load Sharing

Load sharing tries to make sure no processor is idle while another processor is heavily loaded.

Goal:

```text
Avoid idle processors and overloaded processors.
```

It does not require perfect balance.

| Point | Load balancing | Load sharing |
| --- | --- | --- |
| Goal | Equalize load | Avoid overload and idle processors |
| Strictness | More strict | Less strict |
| Information needed | More global information | Less information |
| Overhead | Higher | Lower |
| Migration | More frequent | Less frequent |

**Important viva line:**

Load balancing aims for equal load, while load sharing aims for acceptable load distribution.

### Q2. Why is strict load balancing difficult in distributed systems?

**Answer:**

Strict load balancing is difficult because:

- Load changes continuously.
- Process execution times are unknown.
- Communication delays make load information outdated.
- Process migration has overhead.
- Machines may have different speeds.
- Global state is hard to maintain.
- Moving a process may cost more than executing it locally.

So, practical systems usually perform approximate load balancing or load sharing.

### Q3. Why would a system trigger process migration or code migration?

**Answer:**

Migration means moving a process, code, or computation from one machine to another.

#### Reasons for process migration

1. **Load balancing**

   Move processes from overloaded machines to lightly loaded machines.

2. **Improved performance**

   Move computation closer to required data.

3. **Fault tolerance**

   Move processes away from a failing machine.

4. **Resource access**

   Move a process to a machine that has required hardware or software.

5. **System maintenance**

   Move processes before shutting down a machine.

6. **Reduced communication cost**

   Place related processes closer to each other.

#### Code migration

Code migration means moving code to another machine for execution.

Example:

A mobile agent moves to a server, executes near the data, and returns the result.

### Q4. What are the overheads involved in migration?

**Answer:**

Migration can be useful, but it has costs.

#### Overheads

1. **Transfer overhead**

   Code, data, stack, and process state must be moved over the network.

2. **Suspension overhead**

   The process may need to pause during migration.

3. **State capture overhead**

   The system must save registers, memory, open files, and execution state.

4. **Restart overhead**

   The process must be restored on the destination machine.

5. **Communication overhead**

   Existing connections may need to be redirected.

6. **Security overhead**

   The system must verify that migrated code is safe.

7. **Consistency overhead**

   File handles, locks, and shared resources must remain consistent.

**Important viva line:**

Migration is useful only when the benefit is greater than the cost of moving and restarting the process.

## Module 5: Replication, Consistency, and Fault Tolerance

### Q1. What is replication?

**Answer:**

Replication means maintaining multiple copies of data or services on different machines.

#### Benefits

- Improved availability
- Better performance
- Fault tolerance
- Reduced access latency

#### Problem

Replication creates consistency issues. If one copy is updated, other copies must also be updated or eventually synchronized.

### Q2. What are consistency models?

**Answer:**

A consistency model defines the rules that determine what value a read operation may return when data is replicated or shared.

It tells programmers what behavior to expect when multiple clients read and write shared data.

### Q3. Differentiate between data-centric and client-centric consistency models.

**Answer:**

#### Data-Centric Consistency

Data-centric consistency focuses on the consistency of the shared data store as seen by all processes.

It defines rules for the order of reads and writes on shared data.

Examples:

- Strict consistency
- Sequential consistency
- Causal consistency
- FIFO consistency

#### Client-Centric Consistency

Client-centric consistency focuses on what an individual client observes over time, especially when the client moves between replicas.

It is useful in systems with eventual consistency.

Examples:

- Monotonic reads
- Monotonic writes
- Read-your-writes
- Writes-follow-reads

| Point | Data-centric consistency | Client-centric consistency |
| --- | --- | --- |
| Focus | Global view of shared data | Individual client's view |
| Concern | How all processes see reads/writes | How one client sees its own operations |
| Used in | Replicated databases, DSM | Mobile clients, web apps, replicated stores |
| Examples | Strict, sequential, causal | Read-your-writes, monotonic reads |

### Q4. Explain strict, sequential, causal, and eventual consistency.

**Answer:**

#### Strict Consistency

Any read returns the value of the most recent write according to absolute global time.

This is the strongest model, but it is almost impossible to implement in real distributed systems because there is no perfect global clock.

#### Sequential Consistency

The result is as if all operations were executed in some single sequential order, and each process's program order is preserved.

All processes see the same order, but that order may not be real-time order.

#### Causal Consistency

Causally related writes must be seen by all processes in the same order.

Concurrent writes may be seen in different orders.

Example:

If `write A` causes `write B`, all processes must see A before B.

#### Eventual Consistency

If no new updates are made, all replicas eventually become consistent.

This is used in large-scale systems where availability and performance are more important than immediate consistency.

**Example:**

DNS and many cloud storage systems use eventual consistency ideas.

### Q5. Explain client-centric consistency models with examples.

**Answer:**

#### 1. Monotonic Reads

If a client reads a value, later reads by the same client should not return an older value.

Example:

If you see email message M once, it should not disappear when you refresh from another replica.

#### 2. Monotonic Writes

A client's writes must be applied in the order issued by that client.

Example:

If you update profile photo and then update bio, replicas should not apply bio first and photo later in a way that breaks order.

#### 3. Read-Your-Writes

After a client writes data, the same client should be able to read its own update.

Example:

After posting a comment, you should see your comment when you refresh.

#### 4. Writes-Follow-Reads

If a client reads a value and then writes based on it, the write should be applied after the read value.

Example:

If you read version 5 of a document and edit it, your write should not be applied to an older version.

### Q6. What are the design issues in implementing Distributed Shared Memory?

**Answer:**

Distributed Shared Memory, or DSM, gives processes on different machines the illusion of sharing one common memory.

#### Design issues

1. **Granularity**

   Decide the unit of sharing.

   Examples: byte, object, page, block.

2. **Consistency model**

   Decide when updates become visible to other processes.

3. **Replication**

   Decide whether copies of memory blocks can exist on multiple machines.

4. **Coherence**

   Ensure that multiple copies of the same data do not become inconsistent.

5. **Synchronization**

   Provide locks, semaphores, or barriers for safe access.

6. **False sharing**

   Two unrelated variables on the same page may cause unnecessary communication.

7. **Page fault handling**

   Accessing remote memory may trigger page faults and network fetches.

8. **Replacement policy**

   Decide which memory block to remove from local cache when space is needed.

9. **Thrashing**

   A memory page may move repeatedly between machines, reducing performance.

10. **Heterogeneity**

   Different machines may use different data formats or architectures.

**Important viva line:**

DSM is easy for programmers but difficult to implement efficiently because memory consistency and communication overhead must be handled carefully.

### Q7. How does a distributed system achieve process resilience and recovery after node failure?

**Answer:**

Process resilience means the system continues working even if some processes or nodes fail.

#### Techniques

1. **Replication**

   Run multiple copies of a service on different nodes.

2. **Checkpointing**

   Save process state periodically so it can restart from a recent point.

3. **Logging**

   Record important events or messages so they can be replayed after failure.

4. **Failover**

   If one node fails, another node takes over its work.

5. **Heartbeat and failure detection**

   Nodes send periodic heartbeat messages. Missing heartbeats may indicate failure.

6. **Rollback recovery**

   Restore a process to a previous consistent checkpoint.

7. **Consensus protocols**

   Replicas agree on state changes even if some nodes fail.

8. **Stable storage**

   Important data is stored on reliable storage so it survives crashes.

#### Example

If a server fails, a backup replica can take over using the latest checkpoint and log records.

### Q8. What is Byzantine failure?

**Answer:**

A Byzantine failure is a failure where a process behaves unpredictably or maliciously. It may send wrong, fake, or inconsistent responses.

This is harder than a crash failure because a crashed process stops responding, but a Byzantine process may continue giving incorrect responses.

#### Example

Three honest replicas return:

```text
Processed: OperationX
```

One faulty replica returns:

```text
Fake: OperationX
```

The client can use majority voting to choose the correct result.

**Important viva line:**

To tolerate `f` Byzantine failures, many Byzantine fault-tolerant systems require at least `3f + 1` replicas.

## Module 6: Distributed File Systems

### Q1. What is a distributed file system?

**Answer:**

A distributed file system stores and manages files across multiple machines but presents them to users as if they are part of one file system.

Users can access remote files over a network using normal file operations.

**Examples:**

- NFS
- AFS
- GFS
- HDFS

### Q2. What are different file-caching schemes?

**Answer:**

File caching stores frequently used file data closer to the client to improve performance.

#### 1. Server-side caching

The server caches file data in memory to reduce disk access.

#### 2. Client-side caching

The client stores copies of file data locally.

This reduces network traffic and improves read performance.

#### 3. Whole-file caching

The entire file is cached on the client.

Used in classic AFS.

#### 4. Block caching

Only blocks or chunks of a file are cached.

Used in many file systems for large files.

#### 5. Write-through caching

Every write is immediately sent to the server.

This improves consistency but increases network traffic.

#### 6. Delayed-write or write-back caching

Writes are cached locally and sent to the server later.

This improves performance but creates consistency risk.

### Q3. How do file-caching schemes handle cache consistency?

**Answer:**

Cache consistency ensures that clients do not use stale cached data.

#### Techniques

1. **Validation on access**

   Client checks with server before using cached data.

2. **Time-to-live**

   Cached data is valid only for a fixed time.

3. **Callback mechanism**

   Server promises to inform clients if cached data changes.

   Used in AFS.

4. **Write-through**

   Updates are immediately sent to the server.

5. **Close-to-open consistency**

   Updates become visible when a file is closed and reopened.

   Often discussed with NFS-style behavior.

6. **Leases**

   Server grants a client permission to cache data for a limited time.

### Q4. Explain the basic architecture of NFS.

**Answer:**

NFS stands for Network File System. It allows clients to access files on a remote server as if they were local files.

#### NFS architecture

1. **NFS Client**

   Machine that accesses remote files.

2. **NFS Server**

   Machine that stores and exports directories.

3. **Virtual File System layer**

   Provides a common interface for local and remote file systems.

4. **RPC mechanism**

   NFS operations are implemented using remote procedure calls.

5. **Mount protocol**

   Allows a remote directory to be attached to the client's local file hierarchy.

Flow:

```text
Application -> VFS -> NFS Client -> RPC -> NFS Server -> Disk
```

### Q5. Explain the file accessing model of NFS.

**Answer:**

In NFS, a client mounts a remote directory from the server. After mounting, the remote directory appears as part of the local file system.

Basic steps:

1. Server exports a directory.
2. Client mounts the remote directory.
3. Application uses normal file operations such as `open`, `read`, `write`, and `close`.
4. VFS detects that the file is remote.
5. NFS client sends RPC requests to the NFS server.
6. Server performs file operation and sends reply.

#### Important features

- NFS is largely stateless in older versions.
- File operations are done using RPC.
- Client caching improves performance.
- Consistency is weaker than local file systems.

### Q6. What are the key takeaways from Google's large-scale distributed systems case study?

**Answer:**

Google's systems were designed for massive scale, fault tolerance, and commodity hardware failures.

The main ideas are commonly explained using GFS and MapReduce.

### Q7. Explain GFS and how it handles massive scale and fault tolerance.

**Answer:**

GFS stands for Google File System. It is designed to store very large files across many machines.

#### Main components

1. **Master**

   Stores metadata such as file names, chunk IDs, and chunk locations.

2. **Chunk servers**

   Store actual data chunks.

3. **Clients**

   Contact the master for metadata and then communicate with chunk servers for data.

#### Key ideas

1. **Large chunks**

   Files are divided into large chunks.

2. **Replication**

   Each chunk is stored on multiple chunk servers.

3. **Fault tolerance**

   If one chunk server fails, data is still available from replicas.

4. **Master metadata**

   The master keeps metadata, not all file data.

5. **Commodity hardware**

   The system assumes machines will fail and handles failure in software.

**Connection with code:**

In the GFS simulation code, the file is split into chunks, each chunk gets a `UUID`, chunks are stored on chunk servers, and the master stores file-to-chunk mapping.

### Q8. Explain MapReduce and why it is scalable.

**Answer:**

MapReduce is a programming model for processing large data sets across many machines.

#### Map phase

Input data is divided into parts. Map tasks process each part and produce key-value pairs.

Example:

```text
word -> count
```

#### Shuffle phase

The system groups all values with the same key.

#### Reduce phase

Reduce tasks combine grouped values to produce final output.

Example:

```text
word -> total count
```

#### Why MapReduce is scalable

- Work is divided among many machines.
- Failed tasks can be restarted.
- Computation is moved near the data.
- The programmer does not handle low-level parallelism.
- The framework handles scheduling and fault tolerance.

### Q9. Compare AFS, NFS, and GFS briefly.

**Answer:**

| Point | AFS | NFS | GFS |
| --- | --- | --- | --- |
| Full form | Andrew File System | Network File System | Google File System |
| Main goal | Scalable shared file access | Remote file access | Large-scale data storage |
| Caching | Client caching with callbacks | Client caching with validation | Chunk replication |
| Architecture | Clients and file servers | Clients and NFS servers | Master and chunk servers |
| Best for | Shared user files | Network file access | Big data workloads |
| Fault tolerance | Callback and server mechanisms | Depends on server setup | Replicated chunks |

**Important viva line:**

NFS focuses on transparent remote file access, AFS improves scalability using caching and callbacks, and GFS scales for huge files using chunking and replication.

