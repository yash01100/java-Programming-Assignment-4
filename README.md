# 📚 Library Management System – Java (Collections + File Handling)
### *Menu-Driven • Collections Framework • File Handling • Comparable & Comparator*

**Author:** Yash Raj  
**Roll No:** 2401010008  
**Program:** B.Tech CSE Core  
**Section:** D  
**Faculty:** Lucky Verma  

---

## 📝 Project Overview

This Java project is a **Digital Library Management System** that allows:

- Managing Books  
- Managing Members  
- Issuing & Returning Books  
- Searching & Sorting books  
- Storing all data using **file handling**  
- Using **Collections Framework** (Map, Set, List, Queue)  

The system is fully **menu-driven**, uses **Comparable & Comparator**, and stores persistent data in:  
- `books.txt`  
- `members.txt`

---

## 📌 Key Features

### 📕 1. Book Management
- Add new books with:
  - Title  
  - Author  
  - Category  
- Auto-generated Book ID  
- Stored persistently in file  
- Track issued/available status

### 👤 2. Member Management
- Add members with:
  - Name  
  - Email  
- Email validation  
- Auto-generated Member ID  
- Issue history stored in file  

### 📙 3. Issue / Return Books
- Checks availability  
- Issues to valid members  
- Manages issued books list  
- Automatically updates file  
- Maintains a **waiting list queue** for unavailable books

### 🔍 4. Search Books
Search by:
- Title  
- Author  
- Category  

Case-insensitive search.

### 🔃 5. Sort Books  
Sort books using:
- Title (Comparable)  
- Author (Comparator)  
- Category (Comparator)  

### 📝 6. File Handling (Persistent Storage)
Files used:

