package ru.yandex.two_test_package;


public class Note {
    private String timeWork;
    private String valueBattery;
    private String valueBatteryPower;
    private String cells;
    private String typeBattery;

    public Note(String timeWork, String valueBattery, String valueBatteryPower, String cells, String typeBattery) {
        this.timeWork = timeWork;
        this.valueBattery = valueBattery;
        this.valueBatteryPower = valueBatteryPower;
        this.cells = cells;
        this.typeBattery = typeBattery;
    }

    public Note() {
    }

    @Override
    public String toString(){
        return timeWork + "\n" + valueBattery + "\n" + valueBatteryPower + "\n" + cells + "\n" + typeBattery;
    }

    public void setTimeWork(String timeWork) {
        if (timeWork != null) {
            this.timeWork = timeWork;
        } else {
            System.out.println("Ошибка! Значение timeWork не может быть пустым.");
        }
    }

    public String getTimeWork() {
        return timeWork;
    }

    public void setValueBattery(String valueBattery) {
        if (valueBattery != null) {
            this.valueBattery = valueBattery;
        } else {
            System.out.println("Ошибка! Значение valueBattery не может быть пустым.");
        }
    }

    public String getValueBattery() {
        return valueBattery;
    }

    public void setValueBatteryPower(String valueBatteryPower) {
        if (valueBatteryPower != null) {
            this.valueBatteryPower = valueBatteryPower;
        } else {
            System.out.println("Ошибка! Значение valueBatteryPower не может быть пустым.");
        }
    }

    public String getValueBatteryPower() {
        return valueBatteryPower;
    }

    public void setCells(String cells) {
        if (cells != null) {
            this.cells = cells;
        } else {
            System.out.println("Ошибка! Значение cells не может быть пустым.");
        }
    }

    public String getCells() {
        return cells;
    }

    public void setTypeBattery(String typeBattery) {
        if (typeBattery != null) {
            this.typeBattery = typeBattery;
        } else {
            System.out.println("Ошибка! Значение typeBattery не может быть пустым.");
        }
    }

    public String getTypeBattery() {
        return typeBattery;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Note note = (Note) obj;
        return timeWork.equals(note.getTimeWork())
                && valueBattery.equals(note.getValueBattery())
                && valueBatteryPower.equals(note.getValueBatteryPower())
                && cells.equals(note.getCells())
                && typeBattery.equals(note.getTypeBattery());
    }

}


