package frc.robot;

public enum ArmEnum 
{
    STORE(3),
    PICKUP(2),
    STOP(1),
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
