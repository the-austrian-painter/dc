# Java Run Guide

This guide explains:

- which Java files are paired
- which file to run first
- the exact commands to use
- sample input flows that work
- the expected output
- the concept each program demonstrates

## Prerequisite

Install a Java JDK so `javac` and `java` are available.

## Compile and Run

Each topic has its own folder. To compile and run a program, `cd` into the folder first:

```bash
cd rpc
javac *.java
java rpc_server
# in another terminal
cd rpc
java rpc_client
```

## Project Structure

```
dc/
├── rpc/                  # Remote Procedure Call
├── ipc/                  # Inter Process Communication
├── group-com/            # Group Communication
├── berkley/              # Berkeley Clock Synchronization
├── bully-election/       # Bully Election Algorithm
├── ex-mutual-exclusion/  # (placeholder)
├── mutual-exclusion/     # Mutual Exclusion via Token Passing
├── chanday-misra/            # Chandy-Misra Deadlock Detection
├── load-balancer/            # Load Balancer
├── byzantine/                # Byzantine Fault Tolerance
├── afs-gfs/                  # AFS and Google File System
```

## Paired / Network Programs

These programs depend on each other and must be started in the correct order.

### 1. RPC Calculator

**Folder:** `rpc/`

**Files**

- `rpc/rpc_server.java`
- `rpc/rpc_client.java`

**Run Order**

1. `java rpc_server`
2. `java rpc_client`

**Commands**

Server terminal:

```bash
cd rpc
javac *.java
java rpc_server
```

Client terminal:

```bash
cd rpc
java rpc_client
```

**Input Flow**

Client:

```text
add
10
5
```

**Desired Output**

Server:

```text
Server ready
Operation : add
Result = 15
```

Client:

```text
Client ready, type and press Enter key
Addition = 15
```

**Concept**

This is a simple Remote Procedure Call style program. The client sends an operation name and two numbers, and the server performs the calculation and returns the result.

**Notes**

- Allowed operations: `add`, `sub`, `mul`, `div`
- Do not use `div` with second number `0`

---

### 2. IPC Sum Program

**Folder:** `ipc/`

**Files**

- `ipc/IPCServer.java`
- `ipc/IPCClient.java`

**Run Order**

1. `java IPCServer`
2. `java IPCClient`

**Commands**

Server terminal:

```bash
cd ipc
javac *.java
java IPCServer
```

Client terminal:

```bash
cd ipc
java IPCClient
```

**Input Flow**

Client:

```text
8
12
```

**Desired Output**

Server:

```text
Server is waiting for the client...
Client connected!
Client says: 8 12
```

Client:

```text
Server reply: Message received: 20
```

**Concept**

This demonstrates Inter Process Communication using sockets. The client sends two integers and the server returns their sum.

**Notes**

- This server handles one client and then exits

---

### 3. AFS File Service

**Folder:** `afs-gfs/`

**Files**

- `afs-gfs/AFS_Server.java`
- `afs-gfs/AFS_Client.java`

**Run Order**

1. `java AFS_Server`
2. `java AFS_Client`

**Commands**

Server terminal:

```bash
cd afs-gfs
javac *.java
java AFS_Server
```

Client terminal:

```bash
cd afs-gfs
java AFS_Client
```

**Input Flow**

Client:

```text
1
notes.txt
Hello Distributed Systems
2
notes.txt
3
```

**Desired Output**

Server:

```text
Server started...
```

Client:

```text
1.Write 2.Read 3.Exit
File: notes.txt
Data: Hello Distributed Systems
Written
1.Write 2.Read 3.Exit
File: notes.txt
Hello Distributed Systems
1.Write 2.Read 3.Exit
```

**Concept**

This is a small distributed file service simulation. The client can request file write and read operations, and the server stores files inside the `afs_files/` folder.

**Notes**

- Written files are saved in `afs-gfs/afs_files/`
- Keep file names simple, for example `notes.txt`

---

### 4. Group Communication

**Folder:** `group-com/`

**Files**

- `group-com/group_server.java`
- `group-com/group_master.java`
- `group-com/group_slave.java`

**Run Order**

1. `java group_server`
2. `java group_master`
3. `java group_slave`

**Commands**

Server terminal:

```bash
cd group-com
javac *.java
java group_server
```

Master terminal:

```bash
cd group-com
java group_master
```

Slave terminal:

```bash
cd group-com
java group_slave
```

**Input Flow**

Master:

```text
Alice
Hello everyone
How are you?
```

Slave:

```text
Bob
```

**Desired Output**

Server:

```text
Server running...
```

Master:

```text
Enter your name: Alice
Enter message: Hello everyone
You: Hello everyone
Enter message: How are you?
You: How are you?
```

Slave:

```text
Enter your name: Bob
Alice joined
Bob joined
Alice: Hello everyone
Alice: How are you?
```

**Concept**

This demonstrates group communication or multicast style messaging. The server accepts multiple clients and broadcasts join messages and chat messages to everyone.

**Notes**

- `group_master` can send messages
- `group_slave` only receives messages after sending its name
- You can open multiple `group_master` terminals if you want multiple senders

---

### 5. Mutual Exclusion Using Token Passing

**Folder:** `mutual-exclusion/`

**Files**

- `mutual-exclusion/mutual_server.java`
- `mutual-exclusion/mutual_client1.java`
- `mutual-exclusion/mutual_client2.java`

**Run Order**

1. `java mutual_server`
2. `java mutual_client1`
3. `java mutual_client2`

**Commands**

Server terminal:

```bash
cd mutual-exclusion
javac *.java
java mutual_server
```

Client 1 terminal:

```bash
cd mutual-exclusion
java mutual_client1
```

Client 2 terminal:

```bash
cd mutual-exclusion
java mutual_client2
```

**Input Flow**

Client 1:

```text
yes
Message from C1
no
```

Client 2:

```text
yes
Message from C2
no
```

**Desired Output**

Server:

```text
Server Started
Message from C1
Message from C2
```

Client 1:

```text
Send data? (yes/no): yes
Enter data: Message from C1
Waiting for token...
Send data? (yes/no): no
Waiting for token...
```

Client 2:

```text
Waiting for token...
Send data? (yes/no): yes
Enter data: Message from C2
Waiting for token...
Send data? (yes/no): no
Waiting for token...
```

**Concept**

This program demonstrates token-based mutual exclusion. Only the client holding the token can enter the critical section and send data to the server. After that, the token is passed to the other client.

**Notes**

- `mutual_client1` starts with the token
- `mutual_client1` must be started before `mutual_client2`
- The server only prints received messages

## Independent / Standalone Programs

These programs do not need a paired client or server.

### 6. Berkeley Clock Synchronization

**Folder:** `berkley/`

**File**

- `berkley/Berkley.java`

**Command**

```bash
cd berkley
javac *.java
java Berkley
```

**Input Flow**

```text
3
10 0 0
10 0 5
9 59 55
10 0 10
```

**Desired Output**

```text
Time differences (in sec):
Node 1: 5
Node 2: -5
Node 3: 10
Average offset = 3
Adjusted times:
Node 1: 9:59:57
Node 2: 9:59:57
Node 3: 9:59:57
All clocks synchronized successfully.
```

**Concept**

This simulates the Berkeley clock synchronization algorithm. A master compares node times, computes average offset, and adjusts all clocks toward a common synchronized value.

---

### 7. Bully Election Algorithm

**Folder:** `bully-election/`

**File**

- `bully-election/Bully.java`

**Command**

```bash
cd bully-election
javac *.java
java Bully
```

**Input Flow**

```text
5
5
2
```

**Desired Output**

```text
Process 2 detects failure of 5
Election messages sent to:
3 4 5
Process 3 OK
Process 4 OK
Process 5 failed
New Coordinator: Process 4
```

**Concept**

This simulates the Bully election algorithm. When the coordinator fails, a process starts an election and the highest active process becomes the new coordinator.

---

### 8. Byzantine Fault Tolerance Demo

**Folder:** `byzantine/`

**File**

- `byzantine/Byzantine.java`

**Command**

```bash
cd byzantine
javac *.java
java Byzantine
```

**Input Flow**

```text
No input needed
```

**Desired Output**

One run may look like this:

```text
Sending request...

Processed: OperationX
Processed: OperationX
Processed: OperationX
Fake: OperationX

Final Decision: Processed: OperationX
```

**Concept**

This demonstrates Byzantine fault tolerance in a very small form. Some nodes behave correctly and one node may behave incorrectly, but the system chooses the majority response.

**Notes**

- The faulty node output is random, so exact output may change each run

---

### 9. Google File System Style Simulation

**Folder:** `afs-gfs/`

**File**

- `afs-gfs/GFS.java`

**Command**

```bash
cd afs-gfs
javac *.java
java GFS
```

**Input Flow**

```text
No input needed
```

**Desired Output**

```text
Reading file
HelloDistributedSystem
```

**Concept**

This is a simplified model of Google File System. A file is split into chunks, each chunk is stored on multiple chunk servers, and the master keeps metadata about chunk locations.

---

### 10. Load Balancer

**Folder:** `load-balancer/`

**File**

- `load-balancer/LoadBalancer.java`

**Command**

```bash
cd load-balancer
javac *.java
java LoadBalancer
```

**Input Flow**

```text
3 10
1
2
3
5
5
```

**Meaning of Sample Flow**

- `3 10` means 3 servers and 10 processes
- `1` means add servers
- `2` means add 2 servers
- `3` means add processes
- `5` means add 5 processes
- `5` means exit

**Desired Output**

At the beginning:

```text
Server A has 4 Processes
Server B has 3 Processes
Server C has 3 Processes
```

After adding 2 servers:

```text
Server A has 2 Processes
Server B has 2 Processes
Server C has 2 Processes
Server D has 2 Processes
Server E has 2 Processes
```

After adding 5 more processes:

```text
Server A has 3 Processes
Server B has 3 Processes
Server C has 3 Processes
Server D has 3 Processes
Server E has 3 Processes
```

**Concept**

This simulates load balancing by distributing processes evenly across available servers. As servers or processes change, the allocation is recalculated.

---

### 11. Deadlock Detection Demo (Chandy-Misra)

**Folder:** `chanday-misra/`

**File**

- `chanday-misra/deadlock.java`

**Command**

```bash
cd chanday-misra
javac *.java
java deadlock
```

**Input Flow**

```text
3
1 0 0
0 0 1
0 0 0
0
```

**Desired Output**

```text
Probes:
[(0,0,0)]
Deadlock Detected
```

**Concept**

This is a small probe-based deadlock detection demo using a wait-for graph. It builds probe messages from the initiator and checks whether a cycle returns to that initiator.

**Notes**

- In the current code, deadlock is detected only when the initiator has a direct edge to itself in the graph
- The sample above is chosen to match how the code actually works

## Quick Run Summary

### Paired Sets

| Program Set | Folder | Start First | Start Next | Start Last |
|---|---|---|---|---|
| RPC | `rpc/` | `rpc_server` | `rpc_client` | - |
| IPC | `ipc/` | `IPCServer` | `IPCClient` | - |
| AFS | `afs-gfs/` | `AFS_Server` | `AFS_Client` | - |
| Group Chat | `group-com/` | `group_server` | `group_master` | `group_slave` |
| Mutual Exclusion | `mutual-exclusion/` | `mutual_server` | `mutual_client1` | `mutual_client2` |

### Standalone Sets

| Folder | File | Command |
|---|---|---|
| `berkley/` | `Berkley.java` | `java Berkley` |
| `bully-election/` | `Bully.java` | `java Bully` |
| `byzantine/` | `Byzantine.java` | `java Byzantine` |
| `afs-gfs/` | `GFS.java` | `java GFS` |
| `load-balancer/` | `LoadBalancer.java` | `java LoadBalancer` |
| `chanday-misra/` | `deadlock.java` | `java deadlock` |
