package ru.yandex;

public class NotePrice {
    int price;
    String name;
    public NotePrice(String name, int price){
        this.price = price;
        this.name = name;
    }
    NotePrice(){}
    void setPrice(int price){
        this.price = price;
    }
    int getPrice(){return  price;}

    void setName(String name){
        this.name = name;
    }
    String getName(){return  name;}

    @Override
    public String toString(){
        return "Название: "+name+ "\n" +"Цена: "+price+" р.";
    }
}
