# UDP Chat Application (Java) 📡

![Java](https://img.shields.io/badge/Java-JDK_1.8%2B-orange) ![Protocol](https://img.shields.io/badge/Protocol-UDP-blue) ![Course](https://img.shields.io/badge/Course-Distributed_Applications-green)

## 📄 Description

This project is a **Client-Server Chat Application** developed as part of the **"Développement d'applications réparties" (TP4)** coursework.

Unlike traditional TCP-based chat systems, this application utilizes **UDP (User Datagram Protocol)** in a non-connected mode. It demonstrates how to handle real-time message exchange without persistent connections, managing asynchronous communication via multi-threading.

### Key Objectives
- Master `DatagramSocket` and `DatagramPacket` manipulation.
- Implement a **Broadcast** system (Publisher-Subscriber pattern) over UDP.
- Manage concurrent Input/Output using Java **Threads**.

---

## 🚀 Features

- **Real-Time Messaging:** Instant text exchange between multiple clients.
- **Broadcast System:** The server acts as a relay; when one client sends a message, the server broadcasts it to all other active participants.
- **Dynamic Registration:** Clients are automatically registered by the server upon their first message sent (Implicit Registration).
- **Asynchronous Client:** The client uses a separate worker thread (`Receiver`) to listen for incoming messages while simultaneously allowing user input on the main thread.
- **Console Interface:** Simple and lightweight CLI (Command Line Interface).

---

## 📂 Project Structure

The source code is organized into two main packages:

```bash
src/
├── client/
│   ├── Client.java       # Main entry point for the user
│   └── (Inner Class) Receiver  # Thread handling incoming UDP packets
└── server/
    └── Server.java       # Handles packet reception and broadcasting logic
```

---

## ⚙️ How It Works

### 1. Server Side (`Server.java`)
- Listens on port `1234`.
- Maintains a `Set<SocketAddress>` of all active clients.
- When a packet is received:
    1. It extracts the sender's IP and Port.
    2. Adds the sender to the client list (if new).
    3. Iterates through the list and forwards the message to everyone **except** the sender.

### 2. Client Side (`Client.java`)
- **Main Thread:** Handles user input (`Scanner`) and sends `DatagramPacket` to the server.
- **Receiver Thread:** Runs in the background, blocking on `socket.receive()`. When a packet arrives, it immediately prints the message to the console to ensure the chat feels "live."

---

## 🛠️ Installation & Usage

### Prerequisites
- **Java Development Kit (JDK):** Version 8 or higher.
- A terminal or an IDE (Eclipse, IntelliJ, VS Code).

### Steps to Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Med-Gh-TN/TP4_DAR2025.git
   cd TP4_DAR2025
   ```

2. **Compile the code:**
   ```bash
   javac -d bin src/server/Server.java src/client/Client.java
   ```

3. **Start the Server:**
   Open a terminal and run:
   ```bash
   java -cp bin server.Server
   ```
   *You should see: `UDP Chat Server started on port 1234`*

4. **Start Clients:**
   Open **multiple new terminal windows** (one for each user) and run:
   ```bash
   java -cp bin client.Client
   ```
   *Enter a username when prompted and start chatting!*

---

## 👤 Author

**Mouhamed Gharsallah**
- **Group:** LSI 3 - G2.1
- **Module:** Développement d’applications réparties (DAR)

---
```
