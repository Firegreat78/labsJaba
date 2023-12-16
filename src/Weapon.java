import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class Weapon implements Cloneable
{
    private final int damage;

    protected Player owner;

    protected Vector2 pos;

    public Weapon(Vector2 pos, int damage)
    {
        this.pos = pos;
        this.damage = damage;
        this.owner = null;
    }

    public Weapon(Player owner, int damage)
    {
        int slot = 0;
        while (slot < Player.inventorySize && owner.inventory[slot] != null) ++slot;
        if (slot == 4) throw new GameException("Cannot add weapon to player: inventory is full");
        owner.inventory[slot] = this;
        this.owner = owner;
        this.damage = damage;
        this.pos = null;
    }

    public void onPickup(Player player)
    {
        this.owner = player;
        this.pos = null;
    }

    public void onDrop(Player player)
    {
        this.owner = null;
        this.pos = player.getPosition();
    }

    public int getDamage()
    {
        return this.damage;
    }

    public void onAttack()
    {
        System.out.println("Weapon attack!");
    }

    @Override
    public String toString()
    {
        return "Weapon: {damage: " + this.damage + "}";
    }
}
