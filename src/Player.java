import java.util.Objects;

public class Player extends Client
{
    private static final float maxPickupDistance = 3;

    public static final int inventorySize = 4;

    private int hitpoints;

    private Weapon weapon;

    public final Weapon[] inventory = new Weapon[inventorySize];

    private final Vector2 position;
    public Player(final String username, final int hp, final Vector2 pos)
    {
        super(username);
        this.hitpoints = hp;
        this.position = pos;
    }

    private void kill()
    {
        for (int i = 0; i < inventorySize; ++i)
        {
            if (this.inventory[i] != null) this.dropWeapon(i);
        }
    }

    public void selectWeapon(final int slot)
    {
        Objects.requireNonNull(inventory[slot]);
        this.weapon = inventory[slot];
    }

    public void attack(final Player target)
    {
        Objects.requireNonNull(weapon);
        weapon.onAttack();
        target.hitpoints -= weapon.getDamage();
        if (target.hitpoints <= 0) target.kill();
    }

    public void pickupWeapon(Weapon weapon)
    {
        if (weapon.owner != null)
            throw new GameException("Cannot pickup weapon: weapon is in inventory.");
        if (Vector2.distanceSquared(this.position, weapon.pos) > (maxPickupDistance * maxPickupDistance))
            throw new GameException("Cannot pickup weapon: weapon is too far away.");
        int slot = 0;
        while (slot < inventorySize && inventory[slot] != null) ++slot;
        if (slot == 4) throw new GameException("Cannot pickup weapon: inventory is full.");
        inventory[slot] = weapon;
        weapon.onPickup(this);
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void dropWeapon(final int slot)
    {
        if (this.inventory[slot] == null)
            throw new GameException("Cannot drop weapon: slot is empty.");
        this.inventory[slot].onDrop(this);
    }

    @Override
    public String toString()
    {
        return "Player: {hp: " + this.hitpoints + "; position: " + this.position + "}";
    }
}
