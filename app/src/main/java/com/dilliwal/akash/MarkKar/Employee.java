package com.dilliwal.akash.MarkKar;

import android.util.Log;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by akash on 25/2/17.
 */




class Employee{

    int id;
    String name;
    int salary;
    String companyName;
    static ArrayList al = new ArrayList();
    Employee (){

    }
    Employee(int id, String name, int salary, String companyName){

        this.id = id;
        this.name = name;
        this.salary = salary;
        this.companyName = companyName;
    }

   public void putDetails(Employee e){
       al.add(e);
   }


    public Employee getDetails(int id ){
        Employee emp = (Employee)al.get(id);
        return emp;



    }
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        ArrayList<Employee> empl = new ArrayList<Employee>();
        System.out.println("Enter 10  employee details");
        for(int i = 0;i<10;i++){
            System.out.println("Employee detail "+(i+1));
            System.out.println();
            System.out.println("Enter employee id");
            int id = in.nextInt();
            System.out.println("Enter employee name");
            String name = in.next();
            System.out.println("Enter employee salary");
            int salary = in.nextInt();
            System.out.println("Enter employee Company name");
            String compname = in.next();
            empl.add(new Employee(id,name,salary,compname));
        }
        System.out.println("Enter employee id to fetch details");
        int fetchId = in.nextInt();
        for (Employee curemp : empl){
            if(curemp.id == fetchId){
                System.out.println("Employee ID : "+curemp.id);
                System.out.println("Employee Name : "+curemp.name);
                System.out.println("Employee Salary : "+curemp.salary);
                System.out.print("Employee Comany Name : "+curemp.companyName);
            }
            else {
                System.out.print(" Sorry no Data found for id = "+ fetchId);
            }
        }



    }
}

