public class Sword extends Weapon
{
    private static final int defaultDamage = 39;

    public Sword(final Player pl)
    {
        super(pl, defaultDamage);
    }

    public Sword(final Vector2 pos)
    {
        super(pos, defaultDamage);
    }

    public void onPickup(Player player) // перегрузка без вызова метода баз. класса
    {
        this.owner = player;
        this.pos = null;
        System.out.println(player + " picked up a sword " + this);
    }

    @Override
    public String toString() // перегрузка с вызовом метода баз. класса
    {
        return super.toString().replace("Weapon", "Sword");
    }

    Sword shallowCopy()
    {
        return this; // поверхностное
    }
}
