package ru.bdm.tinex.logic;

import java.util.*;

public abstract class Map {
    private static Random rand = new Random();
    protected int width, height;


    public abstract Element getElement(Pos pos);

    public abstract Pos getPosition(Element element);

    public abstract Collection<Animal> getAnimals();

    public abstract Collection<Element> getAllElements();

    /**
     * put element in position
     * if map contain element, then move it
     * if there is someone, then delete it
     */
    public abstract void put(Element element, Pos pos);

    public abstract void remove(Element element);

    public abstract void remove(Pos pos);
    public abstract boolean containOf(Pos pos);

    public Element[] getSeeArea(Animal animal) {
        Pos pos = getPosition(animal);
        Pos[] positions = SeeAreaGenerator.generate(animal.getWay(), pos);
        Element[] result = new Element[positions.length];
        for (int i = 0; i < positions.length; i++) {
            result[i] = getElement(positions[i]);
        }
        return result;
    }

    public void randomEmptyPut(Element e) {
        Pos pos;
        int repeat = 50;
        do {
            pos = Pos.of(rand.nextInt(width - 2) + 1, rand.nextInt(height - 2) + 1);

        } while (containOf(pos) && repeat-- > 0);
        if(repeat > 0)
            put(e, pos);
    }

    public void randomPutGrass(int number){
        for (int i = 0; i < number; i++) {
            randomEmptyPut(ElementFactory.grass());
        }
    }



    public void setSize(int w, int h) {
        width = w;
        height = h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract boolean containOf(Element element);

    public abstract int getNumberGrass();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Element e:getAllElements()){
            sb.append("  ").append(e).append(" -> ").append(getPosition(e)).append("\n");
        }

        return "MapSimple{" +
                "\n" + sb.toString() +
                "} " ;
    }

    public abstract Map copy();

    protected Map getCopyMap(Map map) {
        map.setSize(getWidth(), getHeight());
        for(Element element: getAllElements()){
            Element copyElement = element.copy();
            map.put(copyElement, getPosition(element));
        }
        return map;
    }
}
