# Distributed Systems Exercise Answers

This file answers the given exercises experiment-wise. The answers are written for viva and exam preparation, and they also connect the theory with the Java programs in this repository.

## Experiment 1: RPC

### Exercise 1

**Question:** Explain why most RPC systems do not use acknowledgement messages. Differentiate among R, RR, and RRA protocols for RPCs. Give an example of an application in which each type protocol may be the most suitable one to use.

#### Why most RPC systems do not use separate acknowledgement messages

Most RPC systems avoid separate acknowledgement messages because the reply itself works as an acknowledgement.

In a normal RPC:

```text
Client -> Server: Request
Server -> Client: Reply
```

When the client receives the reply, it knows that:

- the request reached the server,
- the server processed the request,
- the result was sent back.

So, sending an extra acknowledgement after every message increases network traffic and delay. Also, if RPC is implemented over TCP, TCP already provides transport-level acknowledgements for delivered bytes. Therefore, most RPC systems rely on request-reply messages, timeouts, retransmission, and duplicate detection instead of using separate acknowledgement messages for every RPC.

In our RPC code, the client sends the operation and operands, and the server sends the result. There is no separate acknowledgement message. The result message itself confirms that the request was processed.

#### R protocol

R means Request protocol.

Message pattern:

```text
Client -> Server: Request
```

There is no reply expected at the application level.

This is suitable when:

- the operation does not return a value,
- the operation is not very critical,
- occasional loss can be tolerated or handled elsewhere,
- the request is idempotent.

Example:

A client sends a simple log message or monitoring update to a server. If one update is lost, the system can continue.

#### RR protocol

RR means Request-Reply protocol.

Message pattern:

```text
Client -> Server: Request
Server -> Client: Reply
```

This is the most common RPC protocol. The reply acts as an acknowledgement of the request.

Example:

Our RPC calculator is an RR-style program:

```text
Client sends: add, 10, 5
Server replies: Addition = 15
```

This is suitable for:

- database queries,
- arithmetic services,
- file read requests,
- remote information lookup.

#### RRA protocol

RRA means Request-Reply-Acknowledge protocol.

Message pattern:

```text
Client -> Server: Request
Server -> Client: Reply
Client -> Server: Acknowledgement
```

The acknowledgement tells the server that the client received the reply. This is useful when the server keeps reply history for duplicate suppression and wants to know when it can safely delete that saved state.

Example:

A banking transaction or order placement system may use RRA. The server may keep the transaction result until it knows the client has received it. This helps avoid executing the same transaction again if the client retransmits the request.

#### Difference between R, RR, and RRA

| Protocol | Messages | Reply expected? | Client acknowledgement? | Suitable example |
| --- | --- | --- | --- | --- |
| R | Request only | No | No | Logging, monitoring update |
| RR | Request + Reply | Yes | No | Calculator RPC, file read, database query |
| RRA | Request + Reply + Ack | Yes | Yes | Banking, order placement, exactly-once style operations |

### Exercise 2

**Question:** In a client-server model implemented using a simple RPC mechanism, after making an RPC request, a client waits until a reply is received. It would be more efficient to allow the client to perform other jobs while the server is processing its request. Describe three mechanisms that may be used in this case.

#### Mechanism 1: Asynchronous RPC with callback

In asynchronous RPC, the client sends the request and continues doing other work. When the server finishes processing, it calls back the client or sends a separate response message.

Flow:

```text
Client -> Server: Request
Client continues other work
Server -> Client: Callback/result
```

This is useful when the server operation takes a long time, such as report generation, file processing, or remote computation.

#### Mechanism 2: Future or promise object

The RPC call immediately returns a future object instead of the actual result. The client can continue doing other work and later check the future.

Example flow:

```text
Future f = remoteCall();
doOtherWork();
result = f.get();
```

If the result is ready, `get()` returns immediately. If not, the client waits only at that point.

This is useful for parallel remote calls where the client sends multiple requests and collects results later.

#### Mechanism 3: Separate thread for RPC

The client can create a separate thread to perform the blocking RPC call. The main thread continues with other work.

Flow:

```text
Main thread: creates RPC worker thread
Worker thread: waits for server reply
Main thread: continues other tasks
```

This is simple to implement in Java using `Thread`, `Runnable`, or executor services.

#### Mechanism 4: Polling or event-driven I/O

Another option is polling. The client sends the RPC request and later checks whether the reply has arrived.

In event-driven systems, the client registers a handler that runs when the reply arrives. This avoids blocking the whole client process.

#### Connection with our code

Our RPC client currently uses blocking RPC:

```java
System.out.println(in.readLine());
```

The client waits at `readLine()` until the server sends the result. To make it non-blocking, we could move this waiting part into a separate thread or implement an asynchronous callback mechanism.

## Experiment 2: IPC

### Exercise 1

**Question:** Suggest a suitable mechanism for implementing reliable IPC with exactly-once semantics in each case.

Exactly-once semantics means the receiver should execute a request exactly one time, even if messages are lost, duplicated, or processes fail.

In practice, exactly-once semantics is difficult. It usually requires:

- unique request IDs,
- acknowledgements,
- retransmission,
- duplicate detection,
- stable storage or logs,
- recovery after crash,
- idempotent or transactional operations.

#### Case (a): Sender and receiver computers are reliable, but communication links are unreliable

Suitable mechanism:

- Use sequence numbers or unique request IDs.
- Use acknowledgement messages.
- Use timeout and retransmission.
- Receiver stores recently processed request IDs.
- If a duplicate request arrives, receiver does not execute it again; it resends the old reply.

Why this works:

The computers do not crash, so the sender and receiver can keep their state in memory. The only problem is message loss or duplication on the link. Retransmission and duplicate suppression solve this.

Example:

```text
Client sends request #10
Reply is lost
Client retransmits request #10
Server sees #10 already processed
Server resends old reply without executing again
```

#### Case (b): Sender and receiver computers are unreliable, but communication links are reliable

Suitable mechanism:

- Use stable storage on both sender and receiver.
- Sender logs outgoing requests before sending.
- Receiver logs request ID and result before replying.
- On recovery, both processes reload their logs.
- Duplicate requests are detected using persistent request IDs.

Why this is needed:

The links are reliable, so messages are not lost in transit. The problem is process or machine crash. If a computer crashes and loses memory, it may forget whether a request was sent or executed. Stable storage is required to remember this information.

Without stable storage, true exactly-once semantics cannot be guaranteed after crashes.

#### Case (c): Sender computer and communication links are reliable, but receiver computer is unreliable

Suitable mechanism:

- Receiver must use stable storage.
- Receiver records the request ID before or atomically with execution.
- Receiver stores the result after execution.
- If it crashes and recovers, it checks the log.
- If the same request arrives again, it resends the stored result instead of executing again.

Why this works:

The sender and link are reliable, but the receiver may crash during request processing. The receiver must be able to determine after recovery whether the operation was already performed.

For critical operations, the operation and log update should be done as an atomic transaction.

#### Case (d): Receiver computer and communication links are reliable, but sender computer is unreliable

Suitable mechanism:

- Sender logs request ID and request data before sending.
- Receiver stores processed request IDs and replies.
- If the sender crashes and later recovers, it checks its log and retransmits unfinished requests.
- Receiver uses duplicate detection to avoid re-execution.

Why this works:

The receiver is reliable, so it can remember executed requests. The sender may crash after sending a request but before receiving the reply. When it recovers, it may send the same request again. The receiver must identify the duplicate and return the previous reply.

#### Summary table

| Case | Main problem | Suitable mechanism |
| --- | --- | --- |
| (a) Reliable computers, unreliable links | Message loss/duplication | ACK, timeout, retransmission, sequence numbers |
| (b) Unreliable computers, reliable links | Crash and lost memory | Stable logs on both sender and receiver |
| (c) Receiver unreliable | Receiver may forget execution | Receiver-side stable log and atomic execution |
| (d) Sender unreliable | Sender may resend after crash | Sender log plus receiver duplicate suppression |

### Exercise 2

**Question:** In client-server IPC, is it useful for a process to behave both as a client and a server? If not, explain why. If yes, give an example.

Yes, it is useful for a process to behave both as a client and a server.

A process behaves as a client when it requests a service from another process. It behaves as a server when it provides a service to another process. In many distributed systems, the same process does both.

#### Example 1: Mutual exclusion code in this repository

In the mutual exclusion experiment:

- `mutual_client1` behaves as a client when it connects to `mutual_server` on port `7000`.
- The same `mutual_client1` also behaves as a server because it opens a `ServerSocket` on port `7001` and waits for `mutual_client2` to connect for token passing.

So, one process can be both client and server.

#### Example 2: Peer-to-peer file sharing

In a peer-to-peer system, a node downloads files from other nodes, so it is a client. The same node also uploads files to other nodes, so it is a server.

#### Example 3: Web application server

A web application server acts as a server for browser clients, but it may act as a client when it sends requests to a database server or another microservice.

#### Conclusion

It is useful and common for a process to behave both as a client and a server. Distributed systems are often built as chains of services where each component may request services and provide services.

## Experiment 3: Group Communication

### Exercise 1

**Question:** In the context of a group communication service, provide example message exchanges that illustrate the difference between causal and total ordering.

#### Causal ordering

Causal ordering preserves cause-effect relationships between messages.

If message `m1` causes message `m2`, then every process must deliver `m1` before `m2`.

Example:

```text
P1 sends m1: "Update X = 10"
P2 receives m1
P2 sends m2: "Use X for calculation"
```

Here, `m2` depends on `m1`. Therefore, every process must deliver:

```text
m1 before m2
```

Allowed delivery:

```text
P3: m1, m2
P4: m1, m2
```

Not allowed in causal ordering:

```text
P3: m2, m1
```

because `m2` was caused by `m1`.

#### Concurrent messages under causal ordering

If two messages are concurrent, causal ordering does not force the same order everywhere.

Example:

```text
P1 sends A
P2 sends B at the same time
```

`A` and `B` are concurrent. So causal ordering allows:

```text
P3: A, B
P4: B, A
```

This is allowed because neither message caused the other.

#### Total ordering

Total ordering requires all processes to deliver all messages in the same order.

For concurrent messages:

```text
P1 sends A
P2 sends B at the same time
```

Total ordering allows either:

```text
P3: A, B
P4: A, B
```

or:

```text
P3: B, A
P4: B, A
```

But it does not allow:

```text
P3: A, B
P4: B, A
```

#### Main difference

| Ordering | Rule |
| --- | --- |
| Causal ordering | Preserves cause-effect order only |
| Total ordering | All processes deliver messages in the same order |

#### Connection with our group communication code

Our group communication server broadcasts messages to all connected clients. However, it does not implement causal ordering or total ordering explicitly. It simply forwards messages in the order the server receives them.

Because the server is centralized, messages may appear ordered by server receive order, but this is not a full distributed total-order multicast algorithm.

### Exercise 2

**Question:** In publish-subscribe systems, explain how channel-based approaches can trivially be implemented using a group communication service. Why is this a less optimal strategy for implementing a content-based approach?

#### Channel-based publish-subscribe using group communication

In channel-based publish-subscribe, messages are published to named channels or topics.

Example:

```text
Channel: sports
Channel: news
Channel: music
```

This can be implemented using group communication very easily:

- Create one group for each channel.
- A subscriber joins the group for the channel it wants.
- A publisher sends messages to the channel group.
- Group communication delivers the message to all members of that group.

Example:

```text
Subscribers of "news" join group NEWS
Publisher sends a message to group NEWS
All NEWS subscribers receive it
```

This is similar to our group communication code, where the server broadcasts messages to all connected members.

#### Why this is less optimal for content-based publish-subscribe

In content-based publish-subscribe, subscribers do not simply subscribe to a channel name. They subscribe using conditions on the message content.

Example:

```text
temperature > 40
city = Mumbai and category = weather
price < 1000 and product = laptop
```

Using group communication for this is less optimal because:

- There may be too many possible content filters.
- Creating one group for every possible filter is not practical.
- A subscriber may have complex conditions.
- A message may match many different filters.
- Broadcasting all messages to all subscribers wastes bandwidth.
- Filtering at the client side causes unnecessary message delivery.

#### Conclusion

Group communication fits channel-based publish-subscribe because channel membership maps naturally to group membership. It is less suitable for content-based publish-subscribe because content matching requires intelligent filtering, indexing, and routing based on message attributes.

## Experiment 4: Clock Synchronization

### Exercise 1

**Question:** A distributed system has three nodes N1, N2, and N3, each having its own clock. The clocks of nodes N1, N2, and N3 tick 800, 810, and 795 times per millisecond. The system uses external synchronization, in which all three nodes receive the real time every 30 seconds from an external time source and readjust their clocks. What is the maximum clock skew?

The maximum skew occurs just before the next synchronization, because clocks drift for the full 30 seconds.

Given:

```text
N1 = 800 ticks/ms
N2 = 810 ticks/ms
N3 = 795 ticks/ms
Synchronization interval = 30 seconds = 30000 ms
```

Fastest clock:

```text
N2 = 810 ticks/ms
```

Slowest clock:

```text
N3 = 795 ticks/ms
```

Difference in clock rates:

```text
810 - 795 = 15 ticks/ms
```

Maximum skew in ticks:

```text
15 ticks/ms * 30000 ms = 450000 ticks
```

If we take `800 ticks/ms` as the nominal clock rate, then:

```text
450000 ticks / 800 ticks per ms = 562.5 ms
```

#### Final answer

The maximum clock skew is:

```text
450000 ticks
```

or approximately:

```text
562.5 ms
```

This skew occurs between N2, the fastest clock, and N3, the slowest clock, just before the next 30-second synchronization.

### Exercise 2

**Question:** An important issue in clock synchronization is that time must never run backward. Give two examples to show why this issue is important. How can a fast clock be readjusted to take care of this issue?

#### Why time must never run backward

If time moves backward, applications can behave incorrectly because many systems assume that timestamps always increase.

#### Example 1: File timestamps and build systems

Suppose a source file is modified at 10:05, but the system clock is adjusted backward to 10:03. A build tool may think the file is older than the compiled object file and may skip recompilation.

This can produce incorrect software builds.

#### Example 2: Transactions and logs

Suppose a bank transaction occurs at 12:00:10 and another transaction occurs after it. If the clock is moved backward to 12:00:05, logs may show the later transaction as if it happened earlier.

This can create incorrect audit records, wrong event ordering, and debugging problems.

Other examples include security token expiry, database timestamps, timeout calculation, and distributed event ordering.

#### How to readjust a fast clock

A fast clock should not be set backward suddenly. Instead, it should be slowed down gradually until real time catches up.

This is called clock slewing.

Example:

```text
Clock is 5 seconds fast.
Do not subtract 5 seconds.
Instead, make the clock tick slightly slower for some time.
When correct time catches up, return to normal tick rate.
```

This ensures that time keeps moving forward, but at a slower rate.

#### Connection with Berkeley algorithm

In our Berkeley clock synchronization code, adjusted times are calculated and printed directly. In a real operating system, the adjustment should usually be applied gradually so that the clock never moves backward.

## Experiment 5: Bully Election Algorithm

### Exercise 1

**Question:** Initiation of an election is actually needed only when the current coordinator process fails. However, in the Bully algorithm, an election is also initiated whenever a failed process recovers. Is this really necessary? If yes, explain why. If not, suggest a modification in which an election is initiated only when the current coordinator fails. Is it necessary for a recovered process to initiate an election in the Bully Algorithm?

#### Short answer

It depends on the system requirement.

If the system requires that the highest-numbered active process must always be the coordinator, then a recovered process must initiate an election when it has a higher ID than the current coordinator.

If the system only requires that some active process should be coordinator, then a recovered process does not need to initiate an election.

#### Why the original Bully algorithm starts election on recovery

In the Bully algorithm, the process with the highest ID has the highest priority. If a failed high-priority process recovers, it may have a higher ID than the current coordinator.

Example:

```text
Processes: P1, P2, P3, P4
P4 was coordinator
P4 failed
P3 became coordinator
P4 recovered
```

In the original Bully algorithm, P4 starts an election or announces itself because P4 has a higher ID than P3. P4 bullies the lower-priority coordinator and becomes the new coordinator.

So, recovery election is necessary only if we want to preserve the rule:

```text
Highest active process must be coordinator.
```

#### Modification: election only when current coordinator fails

We can modify the algorithm as follows:

1. A recovered process does not start an election.
2. It sends a `RECOVERED` or `ALIVE` message to the current coordinator.
3. The current coordinator replies with a `COORDINATOR` message.
4. The recovered process accepts the current coordinator, even if the recovered process has a higher ID.
5. An election is started only when a process detects that the current coordinator has failed.

Modified rule:

```text
A recovered process joins as a normal process.
It does not replace the current coordinator.
Only coordinator failure triggers election.
```

#### Advantage

This reduces unnecessary elections and network messages.

#### Disadvantage

The coordinator may no longer be the highest-numbered active process. Therefore, this is not the strict original Bully algorithm.

#### Connection with our code

The drive version `BullyAlg.java` supports process recovery. If a recovered process has a higher ID than the current coordinator, it becomes the coordinator. This follows the original Bully idea.

The simpler root version focuses on the case where the coordinator has failed and an initiator starts the election.

### Exercise 2

**Question:** In the ring-based election algorithm, two or more processes may almost simultaneously discover that the coordinator has crashed and each may circulate an election message. This wastes bandwidth. Modify the algorithm so that only one election message circulates completely around the ring and others are detected and killed as soon as possible.

#### Problem

In a ring election algorithm, multiple processes may detect coordinator failure at almost the same time.

Example:

```text
P2 starts election message E2
P5 starts election message E5
P7 starts election message E7
```

All of these messages may circulate around the ring, even though only one election is needed.

#### Modified algorithm

Add an election identifier to every election message.

Use the initiator process ID as the election ID:

```text
ELECTION(electionId, candidateList)
```

Each process stores:

```text
currentElectionId
electionInProgress
```

Rules:

1. When a process detects coordinator failure, it starts an election only if it is not already participating in one.
2. When a process receives an election message for the first time, it records that election ID and forwards the message.
3. If it later receives another election message:
   - If the new election ID is lower priority than the recorded one, discard it.
   - If the new election ID is higher priority, replace the recorded ID and forward only the higher-priority election.
4. Only the highest-priority election message is allowed to continue.
5. When the surviving election message returns to its initiator, the coordinator is selected.
6. A `COORDINATOR` message is circulated to announce the result and clear election state.

#### Example

Suppose P2 and P5 both start elections.

```text
ELECTION(2)
ELECTION(5)
```

When a process that has seen `ELECTION(5)` later receives `ELECTION(2)`, it discards `ELECTION(2)`.

Only `ELECTION(5)` continues completely around the ring.

#### Why this works

Duplicate election messages are killed when they reach a process that is already participating in a better election. This reduces unnecessary network traffic and still elects one coordinator.

## Experiment 6: Mutual Exclusion

### Exercise 1

**Question:** In the centralized approach to mutual exclusion, the coordinator grants permission for critical section entry to the first process in the queue. In some systems, it may be desirable to grant permission to higher-priority jobs before lower-priority jobs. Modify the algorithm to take care of this and show how the algorithm satisfies the no-starvation property.

#### Centralized mutual exclusion

In centralized mutual exclusion:

1. A process sends a `REQUEST` message to the coordinator.
2. The coordinator grants permission to one process at a time.
3. The selected process enters the critical section.
4. After leaving, it sends a `RELEASE` message.
5. The coordinator grants permission to the next process.

#### Modification for priority

Instead of using a simple FIFO queue, the coordinator uses a priority queue.

Each request contains:

```text
processId
basePriority
arrivalTime
waitingTime
```

The coordinator grants access to the process with the highest effective priority.

Effective priority can be:

```text
effectivePriority = basePriority + agingValue
```

The `agingValue` increases as the process waits.

#### Why aging is needed

If priority alone is used, a low-priority process may wait forever because high-priority processes keep arriving.

This is starvation.

Aging prevents starvation by gradually increasing the priority of waiting processes.

#### Modified algorithm

```text
When REQUEST arrives:
    add request to priority queue

When critical section is free:
    increase waiting score of all waiting requests
    select request with highest effective priority
    send GRANT to that process

When RELEASE arrives:
    mark critical section as free
    repeat selection
```

#### Why mutual exclusion is satisfied

The coordinator sends a `GRANT` message to only one process at a time. Until that process sends `RELEASE`, no other process receives permission.

Therefore, only one process can enter the critical section.

#### Why no-starvation is satisfied

Every waiting process gains priority over time because of aging. Even a low-priority process eventually gets enough effective priority to be selected.

Therefore, no process waits forever.

#### Connection with our code

The file `drive/ex-mutual-exclusion/CoordinatorME.java` demonstrates centralized mutual exclusion with priorities using a `PriorityQueue`.

The file `drive/ex-mutual-exclusion/PriorityME.java` demonstrates priority plus aging. It increases priority for waiting processes so that starvation is avoided.

### Exercise 2

**Question:** Implement a Java program to demonstrate Priority-Based Distributed Mutual Exclusion using a centralized coordinator. Higher-priority processes are allowed to enter the critical section before lower-priority processes.

#### Idea

The centralized coordinator receives requests from processes. It stores them according to priority. Higher-priority processes are granted critical section access before lower-priority processes.

#### Simple Java implementation

```java
import java.util.*;

class Process {
    int id;
    int priority;
    int waitTime;

    Process(int id, int priority) {
        this.id = id;
        this.priority = priority;
        this.waitTime = 0;
    }
}

class Coordinator {
    List<Process> queue = new ArrayList<>();

    void requestAccess(Process p) {
        System.out.println("Process " + p.id + " sends REQUEST");
        queue.add(p);
    }

    void grantAccess() throws InterruptedException {
        while (!queue.isEmpty()) {
            for (Process p : queue) {
                p.waitTime++;
                p.priority++;
            }

            queue.sort((a, b) -> b.priority - a.priority);

            Process current = queue.remove(0);
            System.out.println("Coordinator GRANTS access to Process " + current.id);
            System.out.println("Process " + current.id + " ENTERING Critical Section");

            Thread.sleep(1000);

            System.out.println("Process " + current.id + " LEAVING Critical Section");
        }
    }
}

public class PriorityBasedME {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Coordinator coordinator = new Coordinator();

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.print("Enter priority for Process " + i + ": ");
            int priority = sc.nextInt();
            coordinator.requestAccess(new Process(i, priority));
        }

        coordinator.grantAccess();
        System.out.println("All processes executed.");
    }
}
```

#### Explanation

- `Process` stores process ID, priority, and waiting time.
- `Coordinator` stores all requests.
- Before granting access, waiting processes get increased priority.
- The queue is sorted in descending priority.
- The highest-priority process enters the critical section first.
- Only one process is removed and executed at a time.

#### Connection with repository code

This is the same concept as:

- `drive/ex-mutual-exclusion/CoordinatorME.java`
- `drive/ex-mutual-exclusion/PriorityME.java`

The main `mutual-exclusion` folder demonstrates token-based mutual exclusion, while these extra files demonstrate centralized priority-based mutual exclusion.

## Experiment 7: Deadlock Management

### Exercise 1

**Question:** Suppose all processes in the system are assigned priorities which can be used to totally order the processes. Modify Chandy et al.'s algorithm for the AND model so that when a process detects a deadlock, it also knows the lowest priority deadlocked process.

#### Basic idea of Chandy-Misra-Haas algorithm

In the AND model, a process may be waiting for one or more other processes. Deadlock is detected by sending probe messages along wait-for graph edges.

A probe message normally contains:

```text
(initiator, sender, receiver)
```

If the initiator receives a probe back, it detects a cycle and hence a deadlock.

#### Modification

Add priority information to the probe message.

Modified probe:

```text
(initiator, sender, receiver, lowestPriority, lowestPriorityProcess)
```

Here:

- `lowestPriority` stores the lowest priority seen so far on the probe path.
- `lowestPriorityProcess` stores the process ID of that lowest-priority process.

Assumption:

```text
Lower priority value means lower priority.
```

If the system uses higher value as lower priority, the comparison can be reversed.

#### Modified algorithm

1. When initiator `Pi` starts detection, it sends a probe on its outgoing wait-for edges.
2. The probe initially contains `Pi` as the lowest-priority process.
3. When a process `Pk` receives the probe:
   - It compares its priority with the current `lowestPriority`.
   - If `Pk` has lower priority, it updates the probe.
   - It forwards the probe along its wait-for edges.
4. If the probe returns to the initiator, a deadlock cycle is detected.
5. The probe already contains the lowest-priority process in that deadlocked cycle.

#### Example

Suppose:

```text
P1 waits for P2
P2 waits for P3
P3 waits for P1
```

Priorities:

```text
P1 = 5
P2 = 2
P3 = 8
```

The lowest priority value is `2`, so P2 is the lowest-priority deadlocked process.

Probe path:

```text
P1 -> P2 -> P3 -> P1
```

The probe updates the lowest-priority field when it reaches P2:

```text
lowestPriorityProcess = P2
```

When P1 receives the probe back, it knows:

- deadlock exists,
- P2 is the lowest-priority process in the deadlock.

#### Why this is useful

The lowest-priority process can be selected as the victim for rollback, abort, or resource preemption. This helps break the deadlock with minimum priority loss.

#### Connection with our code

Our code uses a `Message` class to represent probes:

```java
Message(int i, int j, int k)
```

To implement this modification, we would add:

```java
int lowestPriority;
int lowestPriorityProcess;
```

and update those fields whenever a probe is forwarded.

### Exercise 2

**Question:** Explain the shortcomings of using time-outs to handle deadlocks.

Using timeouts means a process assumes deadlock if it waits too long.

This approach is simple, but it has many shortcomings.

#### 1. Timeout value is hard to choose

If the timeout is too short, the system may falsely assume deadlock.

If the timeout is too long, real deadlocks are detected very late.

#### 2. False deadlock detection

A process may be slow because of high load, network delay, or temporary blocking. A timeout may incorrectly treat this as deadlock.

#### 3. Wasted work

When a timeout occurs, the system may abort or roll back a process. If there was no real deadlock, useful work is wasted.

#### 4. Poor performance in distributed systems

Distributed systems have variable network delays. A timeout that works in one situation may fail in another.

#### 5. No information about the deadlock cycle

Timeouts do not identify which processes are actually involved in the deadlock. Therefore, choosing the correct victim becomes difficult.

#### 6. Starvation possibility

A process may repeatedly time out and be aborted even when it is not part of a real deadlock.

#### Conclusion

Timeouts are easy to implement but unreliable. Algorithms like Chandy-Misra-Haas are better because they detect actual cycles in the wait-for graph.

## Experiment 8: Load Balancing

### Exercise 1

**Question:** Load balancing in the strictest sense is not achievable in distributed systems. Discuss. What are the main differences between load-balancing and load-sharing approaches for process scheduling in distributed systems? Which policies are different and which are the same?

#### Why strict load balancing is not achievable

Strict load balancing means every processor should have exactly equal load at all times.

This is not practically achievable in distributed systems because:

- Load changes continuously.
- Processes arrive and finish at unpredictable times.
- No node has perfectly current global knowledge.
- Communication delays make load information stale.
- Process migration has overhead.
- Process execution times are often unknown.
- Processors may be heterogeneous.
- Network cost may be higher than the benefit of migration.

So, real systems try to improve load distribution, not maintain perfect equality.

#### Load balancing

Load balancing tries to distribute work as evenly as possible among processors.

Goal:

```text
Minimize load difference among processors.
```

It is more aggressive and often requires more information about system state.

#### Load sharing

Load sharing has a simpler goal: make sure no processor is idle while another processor is heavily loaded.

Goal:

```text
Avoid idle processors and overloaded processors.
```

It does not require perfectly equal load.

#### Difference between load balancing and load sharing

| Point | Load balancing | Load sharing |
| --- | --- | --- |
| Goal | Equalize load | Avoid overload/idle state |
| Strictness | More strict | Less strict |
| Information needed | More global load information | Less information |
| Migration frequency | More frequent | Less frequent |
| Overhead | Higher | Lower |
| Decision style | Find best or lightest node | Find any suitable node |
| Practicality | Harder | Easier |

#### Policies used in both approaches

Both approaches use similar policy categories:

- Transfer policy: decides when a process should be transferred.
- Selection policy: decides which process should be transferred.
- Location policy: decides where the process should be transferred.
- Information policy: decides when and how load information is collected.

#### Which policies differ?

The policies are conceptually the same, but their strictness differs.

Transfer policy:

- Load balancing transfers to reduce imbalance.
- Load sharing transfers mainly when a node is overloaded or another is idle.

Location policy:

- Load balancing tries to find the best or least-loaded node.
- Load sharing only needs to find an acceptable underloaded node.

Information policy:

- Load balancing needs more accurate and frequent load information.
- Load sharing can work with approximate or threshold-based information.

Selection policy:

- Both need to choose which process to move.
- Load balancing may choose based on expected balancing benefit.
- Load sharing may choose based on migration cost and simplicity.

#### Connection with our code

Our `LoadBalancer.java` demonstrates static load balancing. It divides processes evenly among servers using:

```text
base = processes / servers
extra = processes % servers
```

It does not measure CPU load, queue length, memory, or network cost. Therefore, it is a simple static assignment scheme.

### Exercise 2

**Question:** A system has two processors P1 and P2. At a particular time, P1 has one process with remaining service time of 200 seconds, and P2 has 100 processes each with remaining service time of 1 second. A new process enters the system. Calculate the response time if: (a) the new process is a 1-second process and is allocated to P1, (b) the new process is a 1-second process and is allocated to P2, (c) the new process is a 200-second process and is allocated to P1, (d) the new process is a 200-second process and is allocated to P2. What can be concluded about load estimation policies?

The question text appears to contain an OCR error in parts (a) and (b). I assume it means a `1-second process`, because parts (c) and (d) explicitly mention a `200-second process`.

Assumption:

```text
Each processor uses FCFS scheduling.
No other new processes arrive.
Response time = waiting time + service time of new process.
```

#### Initial load

For P1:

```text
One process with remaining time = 200 seconds
Total waiting work = 200 seconds
```

For P2:

```text
100 processes, each 1 second
Total waiting work = 100 seconds
```

#### (a) New process is 1 second and allocated to P1

It waits for the existing 200-second process.

```text
Response time = 200 + 1 = 201 seconds
```

#### (b) New process is 1 second and allocated to P2

It waits for 100 existing 1-second processes.

```text
Response time = 100 + 1 = 101 seconds
```

#### (c) New process is 200 seconds and allocated to P1

It waits for the existing 200-second process.

```text
Response time = 200 + 200 = 400 seconds
```

#### (d) New process is 200 seconds and allocated to P2

It waits for 100 existing 1-second processes.

```text
Response time = 100 + 200 = 300 seconds
```

#### Results

| Case | Allocation | New process time | Response time |
| --- | --- | --- | --- |
| (a) | P1 | 1 second | 201 seconds |
| (b) | P2 | 1 second | 101 seconds |
| (c) | P1 | 200 seconds | 400 seconds |
| (d) | P2 | 200 seconds | 300 seconds |

#### Conclusion

The number of processes alone is not a good load estimate.

P1 has only one process, but it has 200 seconds of remaining work. P2 has 100 processes, but total remaining work is only 100 seconds.

Therefore, load-balancing algorithms should estimate load using total remaining service time, CPU demand, queue length, process size, and expected execution time, not just the number of processes.

## Experiment 9: Byzantine Failure

### Exercise 1

**Question:** For each of the six ordered pairs of problems among the Byzantine agreement problem, the Consensus problem, and the Interactive consistency problem, demonstrate a reduction from the former to the latter. Solve with an example.

#### Definitions

Byzantine Agreement, Consensus, and Interactive Consistency are closely related agreement problems.

#### Byzantine Agreement problem

There is one special source process, sometimes called the commander. The source has a value.

Requirements:

- All non-faulty processes decide the same value.
- If the source is non-faulty, all non-faulty processes decide the source's value.

#### Consensus problem

Every process has an input value.

Requirements:

- All non-faulty processes decide the same value.
- If all non-faulty processes propose the same value, that value must be decided.

#### Interactive Consistency problem

Every process has an input value, and all non-faulty processes must agree on the same vector of values.

Requirements:

- All non-faulty processes obtain the same vector.
- For every non-faulty process `Pi`, the vector entry for `Pi` must contain `Pi`'s real input.

Example vector:

```text
[value from P1, value from P2, value from P3, value from P4]
```

#### Meaning of reduction

When we say:

```text
A reduces to B
```

we mean:

```text
If we have a solution for B, we can use it to solve A.
```

There are three problems, so there are six ordered reductions.

### 1. Byzantine Agreement reduces to Consensus

Goal: Solve Byzantine Agreement using Consensus.

Method:

1. The source sends its value to all processes.
2. Each process uses the value it received from the source as its input to a consensus algorithm.
3. The consensus decision becomes the Byzantine Agreement decision.

Example:

```text
P1 is source and sends value 1.
P2, P3, P4 receive 1.
They run consensus with input 1.
Decision = 1.
```

If the source is faulty and sends different values, consensus still ensures all non-faulty processes decide the same value.

### 2. Consensus reduces to Byzantine Agreement

Goal: Solve Consensus using Byzantine Agreement.

Method:

1. Run one Byzantine Agreement instance for each process.
2. In instance `i`, process `Pi` acts as the source and broadcasts its input.
3. All non-faulty processes get the same decided value for each source.
4. This creates a common vector of proposed values.
5. Apply a deterministic rule, such as majority, to decide the consensus value.

Example:

```text
P1 input = 1
P2 input = 1
P3 input = 0
P4 faulty
```

Run BA for P1, P2, P3, P4. All correct processes obtain the same vector, for example:

```text
[1, 1, 0, 1]
```

Majority value is `1`, so consensus decision is `1`.

If all non-faulty processes propose `v`, then at least a majority of correct entries are `v`, so the majority rule decides `v`.

### 3. Byzantine Agreement reduces to Interactive Consistency

Goal: Solve Byzantine Agreement using Interactive Consistency.

Method:

1. Run Interactive Consistency for all processes.
2. Every non-faulty process obtains the same vector.
3. The Byzantine Agreement decision is the vector entry of the source process.

Example:

```text
P1 is source and has value 1.
Interactive consistency returns vector [1, 0, 1, 1].
All processes decide vector[P1] = 1.
```

If P1 is non-faulty, interactive consistency guarantees that the P1 entry is P1's true value.

### 4. Interactive Consistency reduces to Byzantine Agreement

Goal: Solve Interactive Consistency using Byzantine Agreement.

Method:

1. Run one Byzantine Agreement instance for each process.
2. In BA instance `i`, process `Pi` is the source.
3. The output of instance `i` becomes vector entry `i`.
4. Combining all BA outputs gives the interactive consistency vector.

Example:

```text
P1 broadcasts 1 using BA
P2 broadcasts 0 using BA
P3 broadcasts 1 using BA
P4 broadcasts faulty value using BA
```

All correct processes may obtain:

```text
[1, 0, 1, 0]
```

The value for a faulty process may be arbitrary, but all correct processes agree on the same vector.

### 5. Consensus reduces to Interactive Consistency

Goal: Solve Consensus using Interactive Consistency.

Method:

1. Run Interactive Consistency using each process's consensus proposal as its input.
2. Every correct process obtains the same vector.
3. Apply a deterministic decision function, such as majority.

Example:

```text
P1 input = 1
P2 input = 1
P3 input = 0
P4 faulty
```

Interactive consistency returns the same vector to all correct processes:

```text
[1, 1, 0, 1]
```

Majority is `1`, so all decide `1`.

If all correct processes propose the same value, majority chooses that value.

### 6. Interactive Consistency reduces to Consensus

Goal: Solve Interactive Consistency using Consensus.

Method:

1. Every process sends its input value to every other process.
2. For each vector position `i`, run a separate consensus instance.
3. In consensus instance `i`, each process proposes the value it received from `Pi`.
4. The decision of consensus instance `i` becomes vector entry `i`.

Example:

For 4 processes, run 4 consensus instances:

```text
C1 decides value for P1
C2 decides value for P2
C3 decides value for P3
C4 decides value for P4
```

Suppose the decisions are:

```text
C1 = 1
C2 = 0
C3 = 1
C4 = 0
```

Then every non-faulty process constructs the same vector:

```text
[1, 0, 1, 0]
```

For a non-faulty process `Pi`, all correct processes receive the same true value from `Pi`, so consensus validity preserves that value.

#### Summary table

| Reduction | How it is done |
| --- | --- |
| BA to Consensus | Source sends value; processes run consensus on received value |
| Consensus to BA | Run BA once per process; decide by majority |
| BA to IC | Run IC and take source's vector entry |
| IC to BA | Run BA once per process to build vector |
| Consensus to IC | Run IC and decide deterministic function of vector |
| IC to Consensus | Run one consensus instance per vector entry |

### Exercise 2

**Question:** Explain consensus with examples.

#### What is consensus?

Consensus is the problem of getting a group of distributed processes to agree on a single value, even when some processes fail.

Each process starts with an input value. At the end, all non-faulty processes must decide the same value.

#### Properties of consensus

Consensus usually requires:

- Agreement: No two non-faulty processes decide different values.
- Validity: If all non-faulty processes propose the same value, that value must be decided.
- Termination: Every non-faulty process eventually decides.
- Integrity: A process decides only once.

#### Example 1: Commit or abort transaction

Suppose a distributed database transaction runs on three servers.

Each server votes:

```text
P1: commit
P2: commit
P3: commit
```

Consensus result:

```text
commit
```

If one server votes abort:

```text
P1: commit
P2: abort
P3: commit
```

The consensus protocol ensures all servers still decide the same final result.

#### Example 2: Leader election

Processes may use consensus to agree on a leader.

```text
P1 proposes P2
P2 proposes P2
P3 proposes P1
```

Consensus ensures all correct processes decide the same leader, for example:

```text
Leader = P2
```

#### Example 3: Byzantine replicas

In our Byzantine code, the client sends the same request to multiple replicas.

Honest replicas return:

```text
Processed: OperationX
```

The Byzantine replica may return:

```text
Fake: OperationX
```

The client counts replies and chooses the majority result. This is a simplified form of consensus or quorum-based agreement.

#### Important point

Consensus is easy when all processes are reliable and messages are reliable. It becomes difficult when processes can crash, messages are delayed, or processes behave maliciously.

## Experiment 10: AFS and GFS

### Exercise 1

**Question:** How does AFS deal with the risk that callback messages may be lost?

#### What is a callback in AFS?

In AFS, clients cache files locally. When a client caches a file, the server gives it a callback promise.

The callback means:

```text
The server promises to notify the client if another client modifies the file.
```

If another client changes the file, the server sends a callback break message to cached clients. After that, those clients must discard or revalidate their cached copy.

#### Risk

If a callback break message is lost, a client may continue using a stale cached copy.

#### How AFS handles this risk

AFS treats callbacks as promises that must be validated under certain conditions. It reduces the risk of lost callback messages using these methods:

1. Callback state is maintained by the server.
2. Clients revalidate cached files when callback promises expire.
3. If a client loses contact with the server, it must revalidate cached data when communication returns.
4. If the server restarts or loses callback state, clients must revalidate their cached files.
5. Callback break messages are retried or failure to contact a client causes the server to discard that client's callback state.

So, a callback is not treated as a permanent guarantee. It is a cache validity promise that can expire or be checked again.

#### Connection with our AFS code

Our AFS code does not implement caching or callbacks. Every read request goes directly to the server:

```text
Client -> Server: read filename
Server -> Client: file content
```

Therefore, our code does not face the lost-callback problem. It demonstrates the basic remote file server idea only. Real AFS adds client caching and callback invalidation for scalability.

### Exercise 2

**Question:** Which features of the AFS design make it more scalable than NFS? What are the limits on its scalability, assuming that servers can be added as required? Which recent developments offer greater scalability? Also remember the code written.

#### Features that make AFS more scalable than NFS

AFS is more scalable than traditional NFS because it reduces server load and network traffic.

Important features:

#### 1. Client-side caching

AFS caches files on the client machine. After a file is cached, repeated reads can be served locally.

This reduces server requests.

#### 2. Whole-file caching

Classic AFS often caches whole files rather than repeatedly fetching small blocks from the server.

This is efficient for read-heavy workloads.

#### 3. Callback mechanism

Instead of asking the server again and again whether cached data is valid, the client relies on callbacks.

The server notifies clients only when a cached file changes.

This reduces validation traffic compared to NFS-style frequent checking.

#### 4. Session semantics

AFS commonly updates the server when a file is closed. This reduces continuous server communication during file access.

#### 5. Location transparency and volumes

AFS organizes files into volumes. Volumes can be moved or replicated more easily, helping administration and scalability.

#### Limits on AFS scalability

Even if more servers are added, AFS still has limits:

- Frequently written shared files cause many callback breaks.
- Hot files can create server bottlenecks.
- Server callback state grows with the number of clients.
- Client caches may become stale and need revalidation.
- Write-heavy workloads scale poorly compared to read-heavy workloads.
- Metadata and directory operations may still become bottlenecks.
- Network partitions and server failures complicate consistency.

#### Recent developments that offer greater scalability

Newer distributed storage systems scale better using different designs:

- GFS and HDFS use large chunks and chunk servers.
- Cloud object stores use massive object distribution.
- Distributed metadata systems avoid a single metadata bottleneck.
- Content delivery networks replicate content near users.
- Consistent hashing spreads objects across many nodes.
- Peer-to-peer systems distribute both storage and serving load.
- Modern replicated databases use sharding and consensus protocols.

#### Connection with our code

Our AFS code:

- uses a socket server,
- stores files in the `afs_files` directory,
- supports `read` and `write` commands,
- handles basic remote file access.

It does not implement caching, callbacks, or volumes.

Our GFS code demonstrates a more scalable idea:

- `Chunk` splits a file into pieces,
- `ChunkServer` stores chunks,
- `Master` or `MasterNode` stores metadata,
- chunks are replicated on multiple servers.

So, in the code:

```text
AFS simulation = remote file read/write server
GFS simulation = chunking + metadata + replication
```

This matches the theory: AFS improves scalability mainly through caching and callbacks, while GFS-style systems improve scalability through chunking, replication, and distributing data across many chunk servers.

