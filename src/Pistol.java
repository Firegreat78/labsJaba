public class Pistol extends Weapon implements Cloneable
{
    private static final int pistolDmg = 18;

    public Pistol(Player pl)
    {
        super(pl, pistolDmg);
    }

    public Pistol(Vector2 pos)
    {
        super(pos, pistolDmg);
    }

    public void onPickup(Player player) // перегрузка без вызова метода баз. класса
    {
        this.owner = player;
        this.pos = null;
        System.out.println(player + " picked up a pistol " + this);
    }

    @Override
    public String toString() // перегрузка с вызовом метода баз. класса
    {
        return super.toString().replace("Weapon", "Pistol");
    }

    @Override
    public Pistol clone() throws CloneNotSupportedException
    {
        return (Pistol)super.clone(); // глубокое
    }
}
