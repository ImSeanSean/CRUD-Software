//Case Study - CRUD Software

import java.util.Scanner;
import java.time.Year;

public class CRUD {
    //Universal Variables
        //Limit
        static int limit = 3;
        static boolean first = true;
        static String unassigned = "Unassigned";
        //Main Array
    static String[][] peripheral = new String[9999][4];
        //Scanner
    static Scanner in = new Scanner(System.in);
        //Code
    static int code = Year.now().getValue() * 10000 + 1;
    static String password = "2023";
        //PeripheralAmount
    static int peripheralno = 0;
    static int availableperipheral = 0 ;
    static int assignedperipheral = 0;
    static int inputdevice = 0;
    static int outputdevice = 0;
    static int ipodevice = 0;
        //Employee
    static String[] employee = new String[9999];
    static int[] assignedemployee = new int[9999];
        //EmployeeAmount
    static int employeeno = 0;
        //Delete index
    static int[] deleted = new int[9999];
    static int deleteno = 0;
    //A - Add Record
    static void A(){
        String option = "100";
        //Switch
        do{
            System.out.printf("------------------------------------------------------\n");
            System.out.println("Add Record");
            System.out.println("  [1] Add Peripheral");
            System.out.println("  [2] Add Employee");
            System.out.println("  [3] Add Peripheral to Employee");
            System.out.println("  [0] Main Menu");
            System.out.printf("------------------------------------------------------\n");
            //Option
            System.out.print("Enter your choice: ");
            option = in.nextLine();
            switch (option) {
                case "1":
                    AddPeripheral();
                    break;
                case "2":
                    AddEmployee();
                    break;
                case "3":
                    Assign();
                    break;
                case "0":
                    //Go Back to Main Menu
                    return;
                default:
                System.out.printf("------------------------------------------------------\n");
                    System.out.println("Invalid Input");
                    break;
            }
        }
        while (option != "0");
    }
    //AddPeripheral
    static void AddPeripheral(){
        //Check if Stack is full
        if(deleteno > 0){
        }
        else if(peripheralno == limit){
            System.out.println("------------------------------------------------------");
            System.out.println("Record is full");
            return;
        }
        String name;
        String typee = "0";
        System.out.println("------------------------------------------------------");
        System.out.print("[0] Return\nEnter PC Peripheral: ");
        name = in.nextLine();
        name = name.toUpperCase();
        if(name.equals("0")){
            return;
        }
        int index = 0;
        //If there is a deleted code, use that old index
        if(deleteno > 0){
                index = deleted[deleteno-1];
                first = false;
        }
        else{
            index = peripheralno;
        }
        peripheral[index][1] = name;
        //Classify Peripheral Type
        char type;
        do{
            System.out.println("------------------------------------------------------");
            System.out.print("[A] Input | [B] Output | [C] Input/Output | [0] Cancel\nPeripheral Type: ");
            type = Character.toUpperCase(in.next().charAt(0));
            in.nextLine();
            switch (type) {
                //Input
                case 'A':
                    typee = "Input";
                    inputdevice++;
                    break;
                //Output
                case 'B':
                    typee = "Output";
                    outputdevice++;
                    break;
                //IO
                case 'C':
                    typee = "Input/Output";
                    ipodevice++;
                    break;
                //Back
                case '0':
                    return;
                //Invalid
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }
        while(type != 'A' && type != 'B' && type != 'C' && type != '0');
        peripheral[index][2] = typee;
        //If used old code
        if(deleteno > 0){
            peripheral[index][0] = Year.now().getValue() * 10000 + deleted[deleteno-1] + 1 + "";
            deleteno--;
            availableperipheral++;
        }
        //If didnt use old code
        else{
            peripheral[index][0] = code+"";
            code++;
            peripheralno++;
            availableperipheral++;
        }
        System.out.println("------------------------------------------------------");
        //Show PC Components Table
        System.out.println("-------------------Hardware List----------------------");
        System.out.println("-----Code---|-----Name-----|-----Type-----|--Employee-");
        int number = 0;
        for(int i = 0; i < peripheralno; i++){
            if(peripheral[i][0] == null){
                continue;
            }
            System.out.printf("%d. %-2s | %-12s | %-12s", number+1, peripheral[i][0], peripheral[i][1], peripheral[i][2]);
            // System.out.print(number+1 + ". " + peripheral[i][0] + " | " + peripheral[i][1] + " | " + peripheral[i][2]);
            number++;
            if(peripheral[i][3] != null){
                System.out.printf(" | %-12s\n", peripheral[i][3]);
                // System.out.println(" | " + peripheral[i][3]);
            }
            else{
                System.out.printf(" | %-12s\n", unassigned);
                // System.out.println(" | Unassigned");
            }
        }
        return;
    }
    //Add Employee
    static void AddEmployee(){
        System.out.println("------------------------------------------------------");
        System.out.println("[0] Return");
        System.out.print("Employee: ");
        String x = in.nextLine();
        if(x.equals("0")){
            return;
        }
        x = x.toUpperCase();
        for(int i = 0; i < employeeno; i++){
            if(employee[i].equals(x)){
                System.out.println("------------------------------------------------------");
                System.out.println("Invalid Input: Employee already exists");
                return;
            }
        }
        employee[employeeno] = x;
        employeeno++;
        System.out.println("------------------------------------------------------");
        //Show Employee List
        System.out.println("--------------------Employee List---------------------");
        int number = 0;
        for(int i = 0; i < employeeno; i++){
            if(employee[i] == null){
                continue;
            }
            System.out.println(number+1 + ". " + employee[i]);
            number++;
        }
        return;
    }
    //Assign Employee to Peripheral
    static void Assign(){
        //Show Components List
        System.out.println("---------------Available Hardware List----------------");
        System.out.println("-----Code---|-----Name-----|-----Type-----|--Employee-");
        int number = 0;
        for(int i = 0; i < peripheralno; i++){
            if(peripheral[i][0] == null){
                continue;
            }
            if(peripheral[i][3] == null){
                System.out.printf("%d. %-2s | %-12s | %-12s | %-12s\n", number+1, peripheral[i][0], peripheral[i][1], peripheral[i][2], unassigned);
                number++;
            }
        }
        System.out.println("------------------------------------------------------");
        int checker = 0;
        int codeinput = 0;
        //Get Peripheral
        do{
            System.out.println("------------------------------------------------------");
            System.out.print("[0] Return\nEnter Peripheral Code: ");
            String input = in.nextLine();
            System.out.println("------------------------------------------------------");
            //Check if there is any letter
            if (!input.matches("[0-9]+")) {
                System.out.println("Invalid input: Non-numeric Character Detected");
                continue;
            }
            codeinput = Integer.parseInt(input);
            //Go Back to Main Menu
            if(codeinput == 0){
                return;
            }
            //Convert code to index
            codeinput = codeinput - Year.now().getValue()*10000-1;
            //Assign if available
            if(codeinput < 0 || codeinput > 9999){
                System.out.println("Invalid input: Code does not Exist");
            }
            else if(peripheral[codeinput][0] == null){
                System.out.println(codeinput);
                System.out.println("Invalid Input: Peripheral does not Exist");
            }
            else if(peripheral[codeinput][3] != null){
                System.out.println("Invalid Input: Peripheral already assigned");
            }
            else{
                System.out.println("Peripheral Found: " + peripheral[codeinput][0] + " | " + peripheral[codeinput][1] + " | " + peripheral[codeinput][2]);
                checker = 1;
            }    
        }
        while(checker != 1);
        //Show Employee List
        int number1 = 0;
        System.out.println("------------------------------------------------------");
        System.out.println("------------------Employee List-----------------------");
        for(int i = 0; i < employeeno; i++){
            if(employee[i] == null){
                continue;
            }
            System.out.println(number1+1 + ". " + employee[i]);
            number1++;
        }
        System.out.println("------------------------------------------------------");
        //Get Employee
        String employeename;
        do{
            System.out.println("------------------------------------------------------");
            System.out.print("[0] Return\nEnter Employee Name: ");
            employeename = in.nextLine();
            employeename = employeename.toUpperCase();
            //Out
            if(employeename.equals("0")){
                return;
            }
            //Check if EmployeeName exists then assign
            boolean exists = false;
            for(int i = 0; i < employeeno; i++){
                if(employee[i].equals(employeename)){
                    exists = true;
                    assignedemployee[i]++;
                    break;
                }
            }
            if(exists == true){
                System.out.println("Employee Assigned");
                peripheral[codeinput][3] = employeename;
                availableperipheral--;
                assignedperipheral++;
                checker = 0;
            }
            else{
                System.out.println("Invalid Input: Employee not Found");
                System.out.println("------------------------------------------------------");
            }
        }
        while(checker != 0);
        int number2 = 0;
        System.out.println("-------------------Hardware List----------------------");
        System.out.println("-----Code---|-----Name-----|-----Type-----|--Employee-");
        for(int i = 0; i < peripheralno; i++){
            if(peripheral[i][0] == null){
                continue;
            }
            System.out.printf("%d. %-2s | %-12s | %-12s", number2+1, peripheral[i][0], peripheral[i][1], peripheral[i][2]);
            // System.out.print(number+1 + ". " + peripheral[i][0] + " | " + peripheral[i][1] + " | " + peripheral[i][2]);
            number++;
            if(peripheral[i][3] != null){
                System.out.printf(" | %-12s\n", peripheral[i][3]);
                // System.out.println(" | " + peripheral[i][3]);
            }
            else{
                System.out.printf(" | %-12s\n", unassigned);
                // System.out.println(" | Unassigned");
            }
        }
    }
    //B
    static void B(){
        String option = "100";
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("B. View Record");
            System.out.println("  [1] View all Peripherals");
            System.out.println("  [2] View available Peripherals");
            System.out.println("  [3] View not available Peripherals");
            System.out.println("  [4] View all Input Devices");
            System.out.println("  [5] View all Output Devices");
            System.out.println("  [6] View all Input/Output Devices");
            System.out.println("  [7] View all Employees");
            System.out.println("  [0] Main Menu");
            System.out.println("------------------------------------------------------");
            //Option
            System.out.print("Enter your choice: ");
            option = in.nextLine();
            switch (option) {
                case "1":
                    //View All Peripherals
                    viewperipherals();
                    break;
                case "2":
                    //View Assigned Peripherals
                    availableperipherals();
                    break;
                case "3":
                    //View Unavailable Peripherals
                    unavailableperipherals();
                    break;
                case "4":
                    //View all Input Devices
                    inputdevices();
                    break;
                case "5":
                    //View all Output Devices
                    outputdevices();
                    break;
                case "6":
                    //View all Input/Output Devices
                    inputoutput();
                    break;
                case "7":
                    //View All Employees
                    viewemployees();
                    break;
                case "0":
                    return;
                default:
                System.out.println("------------------------------------------------------");
                System.out.println("Invalid Input");
                    break;
            }
        }
        while(option != "0");
    }
    //View Peripherals
    static void viewperipherals(){
        System.out.println("-------------------Hardware List----------------------");
        System.out.println("-----Code---|-----Name-----|-----Type-----|--Employee-");
        int number = 0;
        for(int i = 0; i < peripheralno; i++){
            if(peripheral[i][0] == null){
                continue;
            }
            System.out.printf("%d. %-2s | %-12s | %-12s", number+1, peripheral[i][0], peripheral[i][1], peripheral[i][2]);
            // System.out.print(number+1 + ". " + peripheral[i][0] + " | " + peripheral[i][1] + " | " + peripheral[i][2]);
            number++;
            if(peripheral[i][3] != null){
                System.out.printf(" | %-12s\n", peripheral[i][3]);
                // System.out.println(" | " + peripheral[i][3]);
            }
            else{
                System.out.printf(" | %-12s\n", unassigned);
                // System.out.println(" | Unassigned");
            }
        }
    }
    static void availableperipherals(){
        System.out.println("----------------Available Hardware List---------------");
        System.out.println("-----Code---|-----Name-----|-----Type-----|--Employee-");
        int number = 0;
        for(int i = 0; i < peripheralno; i++){
            if(peripheral[i][0] == null){
                continue;
            }
            if(peripheral[i][3] == null){
                System.out.printf("%d. %-2s | %-12s | %-12s | %-12s\n", number+1, peripheral[i][0], peripheral[i][1], peripheral[i][2], unassigned);                
                number++;
            }
        }
    }
    static void unavailableperipherals(){
        System.out.println("--------------Unavailable Hardware List---------------");
        System.out.println("-----Code---|-----Name-----|-----Type-----|--Employee-");
        int number = 0;
        for(int i = 0; i < peripheralno; i++){
            if(peripheral[i][3] != null){
                System.out.printf("%d. %-2s | %-12s | %-12s | %-12s\n", number+1, peripheral[i][0], peripheral[i][1], peripheral[i][2], peripheral[i][3]);                
                number++;
            }
        }
    }
    static void inputdevices(){
        System.out.println("-------------------Input Device List------------------");
        System.out.println("-----Code---|-----Name-----|-----Type-----|--Employee-");
        int number = 0;
        for(int i = 0; i < peripheralno; i++){
            if(peripheral[i][2] == "Input"){
                System.out.printf("%d. %-2s | %-12s | %-12s", number+1, peripheral[i][0], peripheral[i][1], peripheral[i][2]);
                number++;
                if(peripheral[i][3] != null){
                    System.out.printf(" | %-12s\n", peripheral[i][3]);
                }
                else{
                    System.out.printf(" | %-12s\n", unassigned);
                }
            }
        }
    }
    static void outputdevices(){
        System.out.println("------------------Output Device List------------------");
        System.out.println("-----Code---|-----Name-----|-----Type-----|--Employee-");
        int number = 0;
        for(int i = 0; i < peripheralno; i++){
            if(peripheral[i][2] == "Output"){
                System.out.printf("%d. %-2s | %-12s | %-12s", number+1, peripheral[i][0], peripheral[i][1], peripheral[i][2]);
                number++;
                if(peripheral[i][3] != null){
                    System.out.printf(" | %-12s\n", peripheral[i][3]);
                }
                else{
                    System.out.printf(" | %-12s\n", unassigned);
                }
            }
        }
    }
    static void inputoutput(){
        System.out.println("----------------Input/Output Device List--------------");
        System.out.println("-----Code---|-----Name-----|-----Type-----|--Employee-");
        int number = 0;
        for(int i = 0; i < peripheralno; i++){
            if(peripheral[i][2] == "Input/Output"){
                System.out.printf("%d. %-2s | %-12s | %-12s", number+1, peripheral[i][0], peripheral[i][1], peripheral[i][2]);
                number++;
                if(peripheral[i][3] != null){
                    System.out.printf(" | %-12s\n", peripheral[i][3]);
                }
                else{
                    System.out.printf(" | %-12s\n", unassigned);
                }
            }
        }
    }
    static void viewemployees(){
        System.out.println("------------------Employee List-----------------------");
        int number = 0;
        for(int i = 0; i < employeeno; i++){
            if(employee[i] == null){
                continue;
            }
            System.out.println(number+1 + ". " + employee[i]);
            number++;
        }
    }
    static void C(){
       String option = "100";
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("C. Delete/Edit Record");
            System.out.println("   [1] Edit Peripheral");
            System.out.println("   [2] Delete Peripheral");
            System.out.println("   [3] Unassign Employee");
            System.out.println("   [0] Main Menu");
            System.out.println("------------------------------------------------------");
            System.out.print("Enter your choice: ");
            option = in.nextLine();
            switch (option) {
                case "1":
                    //Edit Peripheral
                    edit();
                    break;
                case "2":
                    //Delete Peripheral
                    delete();
                    break;
                case "3":
                    //Unassign Employee
                    unassign();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }
        while(option != "0");  
    }
    static void edit(){
        //Show Hardware List
        System.out.println("-------------------Hardware List----------------------");
        System.out.println("-----Code---|-----Name-----|-----Type-----|--Employee-");
        int number = 0;
        for(int i = 0; i < peripheralno; i++){
            if(peripheral[i][0] == null){
                continue;
            }
            System.out.printf("%d. %-2s | %-12s | %-12s", number+1, peripheral[i][0], peripheral[i][1], peripheral[i][2]);
            // System.out.print(number+1 + ". " + peripheral[i][0] + " | " + peripheral[i][1] + " | " + peripheral[i][2]);
            number++;
            if(peripheral[i][3] != null){
                System.out.printf(" | %-12s\n", peripheral[i][3]);
                // System.out.println(" | " + peripheral[i][3]);
            }
            else{
                System.out.printf(" | %-12s\n", unassigned);
                // System.out.println(" | Unassigned");
            }
        }

        //Check if Editable
        int codeinput =  0;
        int checker = 0;
        do{
            System.out.println("------------------------------------------------------");
            System.out.print("[0] Return\nEnter Peripheral Code: ");
            String input = in.nextLine();
            System.out.println("------------------------------------------------------");
            //Check if there is any letter
            if (!input.matches("[0-9]+")) {
                System.out.println("Invalid input: Non-numeric Character Detected");
                continue;
            }
            codeinput = Integer.parseInt(input);
            //Go Back to Main Menu
            if(codeinput == 0){
                return;
            }
            //Convert code to index
            codeinput = codeinput - Year.now().getValue()*10000-1;
            //Check if Correct
            if(codeinput < 0 || codeinput > 9999){
                System.out.println("Invalid input: Code does not Exist");
            }
            else if(peripheral[codeinput][0] == null){
                System.out.println(codeinput);
                System.out.println("Invalid Input: Peripheral does not Exist");
            }
            else if(peripheral[codeinput][3] != null){
                System.out.println("Invalid Input: Peripheral already assigned");
            }
            else{
                System.out.println("Peripheral Found: " + peripheral[codeinput][0] + " | " + peripheral[codeinput][1] + " | " + peripheral[codeinput][2]);
                checker = 1;
            }    
        }
        while(checker != 1);
        //Edit
        int choice = 100;
        do{
            System.out.println("------------------------------------------------------");
            String input = "100";
            int check = 0;
            //Choose what part to edit
            System.out.print("[1]Edit Name\n[2]Edit Type\n[0]Finish Editing\nEnter your choice: ");
            choice = in.nextInt();
            in.nextLine();
            System.out.println("------------------------------------------------------");
            //Edit
            switch (choice) {
                case 1:
                    //Edit Name
                    System.out.print("[0]Cancel\nProvide New Peripheral Name: ");
                    String name = in.nextLine();
                    name = name.toUpperCase();
                    if(name.equals("0")){
                        break;
                    }
                    do{
                        System.out.println("------------------------------------------------------");
                        System.out.print("[0] Cancel\nEnter Password: ");
                        input = in.nextLine();
                        if(input.equals("0")){
                            return;
                        }
                        if(input.equals(password)){
                            check = 1;
                        }
                        else if(input != password){
                            System.out.println("------------------------------------------------------");
                            System.out.println("Wrong Password");
                        }
                    }
                    while(check != 1);
                    peripheral[codeinput][1] = name;
                    System.out.println("------------------------------------------------------");
                    System.out.println(peripheral[codeinput][0] + " | " + peripheral[codeinput][1] + " | " + peripheral[codeinput][2]);
                    break;
                case 2:
                    //Edit Type
                    char type;
                    String typee = "0";
                    do{
                        System.out.println("------------------------------------------------------");
                        System.out.print("[A] Input | [B] Output | [C] Input/Output | [0] Cancel\nPeripheral Type: ");
                        type = Character.toUpperCase(in.next().charAt(0));
                        in.nextLine();
                        System.out.println("------------------------------------------------------");
                        switch (type) {
                            //Input
                            case 'A':
                                if(peripheral[codeinput][2] == "Input"){
                                    inputdevice--;
                                }
                                else if(peripheral[codeinput][2] == "Output"){
                                    outputdevice--;
                                }
                                else if(peripheral[codeinput][2] == "Input/Output"){
                                    ipodevice--;
                                }
                                typee = "Input";
                                inputdevice++;
                                break;
                            //Output
                            case 'B':
                                if(peripheral[codeinput][2] == "Input"){
                                    inputdevice--;
                                }
                                else if(peripheral[codeinput][2] == "Output"){
                                    outputdevice--;
                                }
                                else if(peripheral[codeinput][2] == "Input/Output"){
                                    ipodevice--;
                                }
                                typee = "Output";
                                outputdevice++;
                                break;
                            //IO
                            case 'C':
                                if(peripheral[codeinput][2] == "Input"){
                                    inputdevice--;
                                }
                                else if(peripheral[codeinput][2] == "Output"){
                                    outputdevice--;
                                }
                                else if(peripheral[codeinput][2] == "Input/Output"){
                                    ipodevice--;
                                }
                                typee = "Input/Output";
                                ipodevice++;
                                break;
                            //Back
                            case '0':
                                break;
                            //Invalid
                            default:
                                System.out.println("Invalid Input");
                                break;
                        }
                    }
                    while(type != 'A' && type != 'B' && type != 'C' && type != '0');
                    do{
                        System.out.println("------------------------------------------------------");
                        System.out.print("[0] Cancel\nEnter Password: ");
                        input = in.nextLine();
                        if(input.equals("0")){
                            return;
                        }
                        if(input.equals(password)){
                            check = 1;
                        }
                        else if(input != password){
                            System.out.println("------------------------------------------------------");
                            System.out.println("Wrong Password");
                        }
                    }
                    while(check != 1);
                    peripheral[codeinput][2] = typee;
                    System.out.println(peripheral[codeinput][0] + " | " + peripheral[codeinput][1] + " | " + peripheral[codeinput][2]);
                    break;
                case 0:
                    //Stop Editing
                    return;
                default:
                    break;
            }
        }
        while(choice != 0);
    }
    //Delete Peripheral
    static void delete(){
        //Show Hardware List
        System.out.println("-------------------Hardware List----------------------");
        System.out.println("-----Code---|-----Name-----|-----Type-----|--Employee-");
        int number = 0;
        for(int i = 0; i < peripheralno; i++){
            if(peripheral[i][0] == null){
                continue;
            }
            System.out.printf("%d. %-2s | %-12s | %-12s", number+1, peripheral[i][0], peripheral[i][1], peripheral[i][2]);
            // System.out.print(number+1 + ". " + peripheral[i][0] + " | " + peripheral[i][1] + " | " + peripheral[i][2]);
            number++;
            if(peripheral[i][3] != null){
                System.out.printf(" | %-12s\n", peripheral[i][3]);
                // System.out.println(" | " + peripheral[i][3]);
            }
            else{
                System.out.printf(" | %-12s\n", unassigned);
                // System.out.println(" | Unassigned");
            }
        }

        System.out.println("------------------------------------------------------");
        //Check if Deletable
        int codeinput = 0;
        int checker = 0;
        do{
            System.out.println("------------------------------------------------------");
            System.out.print("[0] Return\nEnter Peripheral Code: ");
            String input = in.nextLine();
            System.out.println("------------------------------------------------------");
            //Check if there is any letter
            if (!input.matches("[0-9]+")) {
                System.out.println("Invalid input: Non-numeric Character Detected");
                continue;
            }
            codeinput = Integer.parseInt(input);
            //Go Back to Main Menu
            if(codeinput == 0){
                return;
            }
            //Convert code to index
            codeinput = codeinput - Year.now().getValue()*10000-1;
            //Check if Correct
            if(codeinput < 0 || codeinput > 9999){
                System.out.println("Invalid input: Code does not Exist");
            }
            else if(peripheral[codeinput][0] == null){
                System.out.println(codeinput);
                System.out.println("Invalid Input: Peripheral does not Exist");
            }
            else if(peripheral[codeinput][3] != null){
                System.out.println("Invalid Input: Peripheral already assigned");
            }
            else{
                System.out.println("Peripheral Found: " + peripheral[codeinput][0] + " | " + peripheral[codeinput][1] + " | " + peripheral[codeinput][2]);
                checker = 1;
            }    
        }
        while(checker != 1);
        //Confirm if want to Delete
        int choice;
        String input;
        int check = 0;
        do{
            System.out.println("------------------------------------------------------");
            System.out.print("[0] Cancel\n[1] Delete Permanently\nEnter your choice: ");
            choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1:
                do{
                    System.out.println("------------------------------------------------------");
                    System.out.print("[0] Cancel\nEnter Password: ");
                    input = in.nextLine();
                    if(input.equals("0")){
                        return;
                    }
                    if(input.equals(password)){
                        check = 1;
                    }
                    else if(input != password){
                        System.out.println("------------------------------------------------------");
                        System.out.println("Wrong Password");
                    }
                }
                while(check != 1);
                    if(peripheral[codeinput][2] == "Input"){
                        inputdevice--;
                    }
                    else if(peripheral[codeinput][2] == "Output"){
                        outputdevice--;
                    }
                    else if(peripheral[codeinput][2] == "Input/Output"){
                        ipodevice--;
                    }
                    //Add to Deleted Code
                    deleted[deleteno]= codeinput;
                    deleteno++;
                    for(int i = 0; i < deleteno; i++){
                        System.out.print(deleted[i] + " ");
                    }
                    for(int i = 0; i < 3; i++){
                        peripheral[codeinput][i] = null;
                    }
                    System.out.println("Peripheral is successfully deleted");
                    availableperipheral--;
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }
        while(choice != 0 && choice != 1);
    }
    //Unassign
    static void unassign(){
        System.out.println("--------------Unavailable Hardware List---------------");
        System.out.println("-----Code---|-----Name-----|-----Type-----|--Employee-");
        int number = 0;
        for(int i = 0; i < peripheralno; i++){
            if(peripheral[i][3] != null){
                System.out.printf("%d. %-2s | %-12s | %-12s | %-12s\n", number+1, peripheral[i][0], peripheral[i][1], peripheral[i][2], peripheral[i][3]);                
                number++;
            }
        }
        //Unassign
        //Check if Unassignable
        int codeinput = 0;
        int checker = 0;
        do{
            System.out.println("------------------------------------------------------");
            System.out.print("[0] Return\nEnter Peripheral Code: ");
            String input = in.nextLine();
            System.out.println("------------------------------------------------------");
            //Check if there is any letter
            if (!input.matches("[0-9]+")) {
                System.out.println("Invalid input: Non-numeric Character Detected");
                continue;
            }
            codeinput = Integer.parseInt(input);
            //Go Back to Main Menu
            if(codeinput == 0){
                return;
            }
            //Convert code to index
            codeinput = codeinput - Year.now().getValue()*10000-1;
            //Check if Correct
            if(codeinput < 0 || codeinput > 9999){
                System.out.println("Invalid input: Code does not Exist");
            }
            else if(peripheral[codeinput][0] == null){
                System.out.println(codeinput);
                System.out.println("Invalid Input: Peripheral does not Exist");
            }
            else if(peripheral[codeinput][3] == null){
                System.out.println("Invalid Input: Peripheral is not assigned");
            }
            else{
                System.out.println("Peripheral Found: " + peripheral[codeinput][0] + " | " + peripheral[codeinput][1] + " | " + peripheral[codeinput][2] + " | " + peripheral[codeinput][3]);
                checker = 1;
            }
        }
        while(checker != 1);
        //Confirm 
        int choice;
        String input;
        int check = 0;
        do{
            System.out.println("------------------------------------------------------");
            System.out.print("[0] Cancel\n[1] Unassign\nEnter your choice: ");
            choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1:
                do{
                    System.out.println("------------------------------------------------------");
                    System.out.print("[0] Cancel\nEnter Password: ");
                    input = in.nextLine();
                    if(input.equals("0")){
                        return;
                    }
                    if(input.equals(password)){
                        check = 1;
                    }
                    else if(input != password){
                        System.out.println("------------------------------------------------------");
                        System.out.println("Wrong Password");
                    }
                }
                while(check != 1);
                    for(int i = 0; i < employeeno; i++){
                        if(employee[i].equals(peripheral[codeinput][3])){
                            assignedemployee[i]--;
                        }
                    }
                    peripheral[codeinput][3] = null;
                    System.out.println("Peripheral is successfully unassigned");
                    availableperipheral++;
                    assignedperipheral--;
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }
        while(choice != 0 && choice != 1);

        
    }
    //D 
    static void D(){
        String option = "100";
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("D. Report");
            System.out.println("   [1] Number of Available Peripherals");
            System.out.println("   [2] Number of Assigned Peripherals");
            System.out.println("   [3] Number of Input Device");
            System.out.println("   [4] Number of Output Device");
            System.out.println("   [5] Number of Input/Output Device");
            System.out.println("   [6] Number of Employees with Peripheral Assigned");
            System.out.println("   [0] Main Menu");
            System.out.println("------------------------------------------------------");
            System.out.print("Enter your choice: ");
            option = in.nextLine();
            switch (option) {
                case "1":
                    //Available Peripheral
                    System.out.println("------------------------------------------------------");
                    System.out.println("Number of Available Peripherals: " + availableperipheral);
                    break;
                case "2":
                    //Assigned Peripheral
                    System.out.println("------------------------------------------------------");
                    System.out.println("Number of Assigned Peripherals: " + assignedperipheral);
                    break;
                case "3":
                    //Number of Input Devices
                    System.out.println("------------------------------------------------------");
                    System.out.println("Number of Input Devices: " + inputdevice);
                    break;
                case "4":
                    //Number of Output Devices
                    System.out.println("------------------------------------------------------");
                    System.out.println("Number of Output Devices: " + outputdevice);
                    break;
                case "5":
                    //Number of Input/Output Devices
                    System.out.println("------------------------------------------------------");
                    System.out.println("Number of Input/Output Devices: " + ipodevice);
                    break;
                case "6":
                    //Number of Employees with Peripherals Assigned
                    int counter = 0;
                    System.out.println("------------------------------------------------------");
                    for(int i = 0; i < employeeno; i++){
                        if(assignedemployee[i] > 0){
                            counter++;
                        }
                    }
                    System.out.println("Number of Employees with Peripherals Assigned: " + counter);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }
        while(option != "0");  
    }
    //Main
    public static void main(String[] args){
        //Variables
            //Option
        String option;
        //Main Menu Loop
        do{  
            System.out.printf("-------------------ABC CRUD Machine-------------------\n");
            System.out.println("A. Add Record");
            System.out.println("B. View Record");
            System.out.println("C. Delete/Edit Record");
            System.out.println("D. Report");
            System.out.println("0. Exit Program");
            System.out.println("------------------------------------------------------");
            System.out.print("Enter your choice: ");
            option = in.nextLine();
            option = option.toUpperCase();
            //Switch Case for Options
            switch (option) {
                case "A":
                    //Run Method
                    A();
                    break;
                case "B":
                    //Run Method
                    B();
                    break;
                case "C":
                    //Run Method
                    C();
                    break;
                case "D":
                    //Run Method
                    D();
                    break;
                case "0":
                    return;
                //Invalid Input
                default:
                System.out.println("------------------------------------------------------");
                System.out.println("Invalid Input");
                    break;
            }
        }
        while (option != "0");
    }
}
