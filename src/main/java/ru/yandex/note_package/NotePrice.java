package ru.yandex.note_package;

public class NotePrice {
    public int price;
    public String name;
    public NotePrice(String name, int price){
        this.price = price;
        this.name = name;
    }
    public NotePrice(){}
    public void setPrice(int price){
        this.price = price;
    }
    public int getPrice(){return  price;}

    public void setName(String name){
        this.name = name;
    }
    public String getName(){return  name;}

    @Override
    public String toString(){
        return "Название: "+name+ "\n" +"Цена: "+price+" р.";
    }
}