import java.sql.*;
import java.util.Scanner;

public class EmployeeDBApp {
    private static final String URL = "jdbc:mysql://localhost:3306/ employeedb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345"; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to database successfully!");
            
            while (true) {
                System.out.println("\n=== Employee Management System ===");
                System.out.println("1. Add Employee");
                System.out.println("2. View All Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); 
                
                switch (choice) {
                    case 1:
                        addEmployee(connection, scanner);
                        break;
                    case 2:
                        viewEmployees(connection);
                        break;
                    case 3:
                        updateEmployee(connection, scanner);
                        break;
                    case 4:
                        deleteEmployee(connection, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting!");
                        connection.close();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option! Please try again.");
                }
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
    
    private static void addEmployee(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\n--- Add New Employee ---");
        
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        
        System.out.print("Enter salary: ");
        double salary = scanner.nextDouble();
        
        System.out.print("Enter hire date (YYYY-MM-DD): ");
        String hireDate = scanner.next();
        
        String sql = "INSERT INTO employees (name, email, department, salary, hire_date) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, department);
            statement.setDouble(4, salary);
            statement.setDate(5, Date.valueOf(hireDate));
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Employee added successfully!");
            }
        }
    }
    
    private static void viewEmployees(Connection connection) throws SQLException {
        System.out.println("\n--- All Employees ---");
        
        String sql = "SELECT * FROM employees";
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            
            System.out.printf("%-5s %-20s %-25s %-15s %-10s %-12s%n", 
                "ID", "Name", "Email", "Department", "Salary", "Hire Date");
            System.out.println("-----------------------------------------------------------------------------");
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String department = resultSet.getString("department");
                double salary = resultSet.getDouble("salary");
                Date hireDate = resultSet.getDate("hire_date");
                
                System.out.printf("%-5d %-20s %-25s %-15s %-10.2f %-12s%n", 
                    id, name, email, department, salary, hireDate);
            }
        }
    }
    
    private static void updateEmployee(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\n--- Update Employee ---");
        
        System.out.print("Enter employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter new department: ");
        String department = scanner.nextLine();
        
        System.out.print("Enter new salary: ");
        double salary = scanner.nextDouble();
        
        System.out.print("Enter new hire date (YYYY-MM-DD): ");
        String hireDate = scanner.next();
        
        String sql = "UPDATE employees SET name = ?, email = ?, department = ?, salary = ?, hire_date = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, department);
            statement.setDouble(4, salary);
            statement.setDate(5, Date.valueOf(hireDate));
            statement.setInt(6, id);
            
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("No employee found with ID: " + id);
            }
        }
    }
    
    private static void deleteEmployee(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\n--- Delete Employee ---");
        
        System.out.print("Enter employee ID to delete: ");
        int id = scanner.nextInt();
        
        String sql = "DELETE FROM employees WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("No employee found with ID: " + id);
            }
        }
    }
}