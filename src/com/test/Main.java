package com.test;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static  List<Book> LIST ;

    public static void main(String[] args) {
        System.out.println("正在对系统进行初始化.....");
        load();
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("==================================");
            System.out.println("1.录入书籍信息");
            System.out.println("2.删除书籍信息");
            System.out.println("3.修改书籍信息");
            System.out.println("4.查找书籍信息");
            System.out.println("5.预览所有图书信息");
            System.out.println("6.退出系统");
            System.out.println("==================================");
            switch (sc.nextInt()) {
                case 1:
                    insert(sc);
                    break;
                case 2:
                    delete(sc);
                    break;
                case 3:
                    motify(sc);
                    break;
                case 4:
                    search(sc);
                    break;
                case 5:
                    list(sc);
                    break;
                case 6:
                    System.out.println("正在保存");
                    save();
                    System.out.println("感谢你的使用 再见");
                    return;
            }
        }
    }

    public static void save() {
        try(ObjectOutputStream stream= new ObjectOutputStream(new FileOutputStream("list.ser"))){
            stream.writeObject(LIST);
            stream.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void load(){
        File file = new File("list.ser");
        if(file.exists()){
            try(ObjectInputStream stream= new ObjectInputStream(new FileInputStream(file))){
                LIST = (List<Book>) stream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            LIST = new LinkedList<>();
        }

    }

    public static void insert(Scanner sc){
        sc.nextLine();
        Book book = new Book();
        System.out.print("请输入书籍的名称：");
        book.setTitle(sc.nextLine());
        System.out.print("请输入书籍的作者：");
        book.setAuthor(sc.nextLine());
        System.out.print("请输入书籍的价格：");
        book.setPrice(sc.nextInt());
        sc.nextLine();
        LIST.add(book);
        System.out.println("书籍添加成功"+ book);
        save();
    }

    public static void delete(Scanner sc){
        sc.nextLine();
        System.out.println("请输入你要删除的书本编号");
        int i= sc.nextInt();
        while(i<0||i>LIST.size()-1){
            System.out.println("输入的编号错误 输入编号的范围是0-"+(LIST.size()-1)+"请重新输入");
            i=sc.nextInt();
            sc.nextLine();
        }
        Book book = LIST.get(i);
        LIST.remove(i);
        System.out.println("你已成功删除书籍"+book);
        save();
    }

    public static void motify(Scanner sc){
        sc.nextLine();
        System.out.println("请输入你要修改的书本编号");
        int i= sc.nextInt();
        sc.nextLine();
        while(i<0||i>LIST.size()-1){
            System.out.println("输入的编号错误 输入编号的范围是0-"+(LIST.size()-1)+"请重新输入");
            i=sc.nextInt();
            sc.nextLine();
        }
        Book book = LIST.get(i);
        System.out.println("你修改的书籍为 "+book+" 请开始你的修改");
        System.out.print("请输入你要修改的书名：");
        book.setTitle(sc.nextLine());
        System.out.print("请输入你要修改的作者：");
        book.setAuthor(sc.nextLine());
        System.out.print("请输入你要修改的价格：");
        book.setPrice(sc.nextInt());
        System.out.print("书籍信息就该成功："+book);
        save();
    }

    public static void search(Scanner sc){
        sc.nextLine();
        if(LIST.isEmpty()){
            System.out.println("书库暂时为空，请添加书籍或稍后进行查询");
            return;
        }

        System.out.println("请输入你要查找的书籍编号");
        int i = sc.nextInt();
        sc.nextLine();
        while(i<0||i>LIST.size()-1){
            System.out.println("输入的编号错误 输入编号的范围是0-"+(LIST.size()-1)+"请重新输入");
            i=sc.nextInt();
            sc.nextLine();
        }
        System.out.println(LIST.get(i));
    }

    public static void list(Scanner sc){
        sc.nextLine();
        if(LIST.isEmpty()){
            System.out.println("数据库为空，请稍后查询");
            return;
        }
        for(int i=0; i<LIST.size(); i++){
            System.out.println(i+"."+LIST.get(i));
        }
    }
}