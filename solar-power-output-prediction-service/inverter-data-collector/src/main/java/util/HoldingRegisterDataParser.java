package util;

import net.wimpi.modbus.procimg.Register;

import java.nio.ByteBuffer;

public class HoldingRegisterDataParser {
    public static float parseSwFloat(Register register0, Register register1) {
        int mostSignificantByte = register0.toUnsignedShort();
        int leastSignificantByte = register1.toUnsignedShort();

        int combinedValue = (mostSignificantByte << 16) | leastSignificantByte;

        return ByteBuffer.allocate(4).putInt(combinedValue).flip().getFloat();
    }
}
