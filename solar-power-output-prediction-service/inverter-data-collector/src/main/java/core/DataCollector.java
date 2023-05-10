package core;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.procimg.Register;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DataCollector {
    private static final List<String> ADDRESSES = List.of("192.168.66.127", "192.168.66.128", "192.168.66.129", "192.168.66.130");
    private static final int PORT = 502;
    private static final int START_REGISTER_ADDRESS = 40092;
    private static final int REGISTER_COUNT = 2;

    private final List<TCPMasterConnection> connections;

    public DataCollector() {
        this.connections = new ArrayList<>();
        addShutdownHook();

        for (String address : ADDRESSES) {
            try {
                TCPMasterConnection connection = new TCPMasterConnection(InetAddress.getByName(address));
                connection.setPort(PORT);
                connection.connect();
                connection.setTimeout(1000);
                connections.add(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public float getCurrentACPower() throws ModbusException {
        List<Float> ACPowers = new ArrayList<>();

        for (TCPMasterConnection connection : connections) {
            ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
            transaction.setRetries(0);
            transaction.setReconnecting(false);

            ReadMultipleRegistersRequest request = new ReadMultipleRegistersRequest(START_REGISTER_ADDRESS, REGISTER_COUNT);
            request.setUnitID(1);
            transaction.setRequest(request);
            transaction.execute();

            ReadMultipleRegistersResponse response = (ReadMultipleRegistersResponse) transaction.getResponse();

            Register[] registers = response.getRegisters();

            float ACPower = parseSwFloat(registers[0], registers[1]);

            ACPowers.add(ACPower);
        }

        return ACPowers.stream().reduce(0.0f, Float::sum);
    }

    private float parseSwFloat(Register register0, Register register1) {
        int mostSignificantByte = register0.toUnsignedShort();
        int leastSignificantByte = register1.toUnsignedShort();

        int combinedValue = (mostSignificantByte << 16) | leastSignificantByte;

        return ByteBuffer.allocate(4).putInt(combinedValue).flip().getFloat();
    }

    private void closeConnections() {
        for (TCPMasterConnection connection : connections) {
            connection.close();
        }
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::closeConnections));
    }
}
