Expense-Splitter 💸
A sleek, modern desktop application for splitting group expenses, built entirely from the ground up with Java Swing. This project challenges the notion of outdated Java UIs by demonstrating how Swing can be leveraged to create a beautiful, minimalist, and highly functional application with a contemporary, dark-themed aesthetic.

✨ Live Demo / Screenshot
(Tip: To add a real screenshot, take a picture of your running app, upload it to a site like Imgur, get the "Direct Link", and paste it here.)

🚀 Core Features
Dynamic Group Creation: Start a new session by naming your group on launch.

Effortless Member Management: Seamlessly add members to your group. The UI updates instantly.

Intuitive Expense Logging: Log expenses with descriptions, amounts, and specify who paid.

Flexible & Fair Splitting: Choose to split costs among all members or select specific individuals for each expense.

Instant Settlement Plan: The app's core logic automatically calculates the most efficient set of transactions required for everyone to settle their debts.

Data Persistence: Save your entire group session—members and all expenses—to a custom .esg file and load it back anytime.

Modern UX: Enjoy a fluid user experience with features like "Enter-to-Add" for quick data entry.

🎨 A Modern Take on Java Swing
Minimalist Dark Theme: A professional dark theme with a vibrant green accent color provides a clean, focused, and visually appealing workspace.

Fully Custom Components: Features custom-painted JPanels, JTextFields, and JScrollBars with rounded corners for a soft, premium feel.

Interactive Feedback: Buttons include subtle hover effects, providing visual feedback that makes the application feel responsive and alive.

Custom Vector Icons: Minimalist, hand-coded icons for menu items that match the aesthetic.

Clean Typography: Utilizes modern, highly readable fonts (Inter and JetBrains Mono) for all text elements.

Styled Dialogs: All pop-up dialogs (JOptionPane) are styled to match the dark theme, ensuring a consistent experience throughout the application.

🛠️ Technology & Architecture
Language: Java

UI Framework: Java Swing

Architecture:

Model-View-Controller (MVC) Pattern: The project is structured with a clear separation of concerns.

Modular Design: The code is organized into distinct packages for UI, data models, business logic, and utilities.

Core Concepts:

Object-Oriented Programming (OOP)

Custom Swing Component Painting

Java Serialization for File I/O (Saving/Loading)

📁 Project Structure
Expense-Splitter/
└── src/
    └── com/
        └── expensesplitter/
            ├── gui/
            ├── main/
            ├── model/
            ├── service/
            ├── ui/
            │   └── components/
            └── util/

⚙️ Getting Started
To run this project locally, you will need Java Development Kit (JDK) 8 or newer.

Clone the repository:

git clone [https://github.com/your-username/Expense-Splitter.git](https://github.com/your-username/Expense-Splitter.git)

Open the project in any Java IDE (like VS Code or IntelliJ).

Run the main method in src/com/expensesplitter/main/ExpenseSplitter.java.
