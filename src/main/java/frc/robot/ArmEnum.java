package frc.robot;

public enum ArmEnum 
{
    FASTPICKUP(4),
    PICKUP(3),
    STOP(2),
    FASTSTOW(1),
    STOW(0);

    private final int value;

    private ArmEnum(final int v) 
    {
        value = v;
    }
    public int getValue() 
    {
        return value;
    }
}
